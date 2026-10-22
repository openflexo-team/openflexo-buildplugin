#!/usr/bin/env python3
"""Dependency drift report of the OpenFlexo workspace (BUILD-F-1, "Tracking").

For every third-party library that the workspace declares, tells
  * the version used and the latest stable one on Maven Central (how far behind, and by how much),
  * the known vulnerabilities of the version used (OSV database, https://osv.dev).

Where the versions come from:
  * the OpenFlexoThirdParty class of the build plugin, through the helpers that use each constant
    (the way a build.gradle is supposed to declare a shared library);
  * the notations written directly in the build.gradle files (group:name:version, or group: ..., name: ..., version: ...).
  Libraries of the org.openflexo group, versions with a variable (${...}) and ranges are not reported.

Only public coordinates (group, artifact, version) leave the machine, towards repo1.maven.org and api.osv.dev.
The report is informative: the exit code is 0 unless --fail-on-vulnerability is given.

Usage:
    tools/dependency-report.py [--root <workspace>] [--output report.md] [--fail-on-vulnerability]
"""
import argparse
import concurrent.futures as cf
import json
import os
import re
import sys
import urllib.error
import urllib.request

CENTRAL = "https://repo1.maven.org/maven2"
OSV_BATCH = "https://api.osv.dev/v1/querybatch"
OSV_VULN = "https://api.osv.dev/v1/vulns/"

PRE_RELEASE = re.compile(r"(?i)(alpha|beta|[-.]rc|[-.]m\d|[-.]cr\d|snapshot|preview|dev|ea\b|b\d{3,})")
SKIP_DIRS = {"build", "node_modules", ".git", ".gradle", "bin", "out"}
# Files that are not part of the build of the workspace (examples, the website)
SKIP_FILES = {os.path.join("website", "build.gradle"),
              # A sample application that no settings.gradle includes: never built (Spring Boot 2.3, 2020)
              os.path.join("pamela", "pamela-spring-security-uc", "build.gradle")}


def read(path):
    with open(path, encoding="utf-8", errors="replace") as f:
        return f.read()


def collect(root):
    """Returns {(group, artifact): {version: [where, ...]}}."""
    found = {}

    def add(group, artifact, version, where):
        if group == "org.openflexo" or "$" in version or any(c in version for c in "[](),") or not version:
            return
        found.setdefault((group, artifact), {}).setdefault(version, []).append(where)

    # 1. The shared versions of the build plugin
    plugin = os.path.join(root, "openflexo-buildplugin", "buildplugin", "src", "main", "groovy", "org", "openflexo",
                          "buildplugin", "plugin.groovy")
    if os.path.isfile(plugin):
        text = read(plugin)
        constants = dict(re.findall(r'static final String (\w+) = "([^"]+)"', text))
        for group, artifact, const in re.findall(r'return "([\w.\-]+):([\w.\-]+):\$\{OpenFlexoThirdParty\.(\w+)\}"', text):
            if const in constants:
                add(group, artifact, constants[const], "OpenFlexoThirdParty." + const)

    # 2. The notations written in the build.gradle files
    for dirpath, dirnames, filenames in os.walk(root):
        dirnames[:] = [d for d in dirnames if d not in SKIP_DIRS]
        for name in filenames:
            if name != "build.gradle":
                continue
            path = os.path.join(dirpath, name)
            rel = os.path.relpath(path, root)
            if rel in SKIP_FILES:
                continue
            text = re.sub(r"(?m)^\s*//.*$", "", read(path))
            text = re.sub(r"(?s)/\*.*?\*/", "", text)
            for g, a, v in re.findall(r"""group:\s*['"]([^'"]+)['"]\s*,\s*name:\s*['"]([^'"]+)['"]\s*,\s*version:\s*['"]([^'"]+)['"]""",
                                      text):
                add(g, a, v, rel)
            for g, a, v in re.findall(r"""['"]([\w.\-]+):([\w.\-]+):([^'"@:]+)(?:@\w+)?['"]""", text):
                add(g, a, v, rel)
    return found


def http_get(url):
    try:
        with urllib.request.urlopen(url, timeout=40) as r:
            return r.read().decode("utf-8", errors="replace")
    except (urllib.error.URLError, TimeoutError, OSError):
        return None


def latest_stable(group, artifact):
    """Latest version of Maven Central that is not a pre-release, or None."""
    text = http_get("%s/%s/%s/maven-metadata.xml" % (CENTRAL, group.replace(".", "/"), artifact))
    if not text:
        return None
    versions = [v for v in re.findall(r"<version>([^<]+)</version>", text) if not PRE_RELEASE.search(v)]
    return versions[-1] if versions else None


def numbers(version):
    return [int(x) for x in re.findall(r"\d+", version)[:3]] + [0, 0, 0]


def gap(used, latest):
    if latest is None:
        return "unknown"
    if used == latest:
        return "up to date"
    u, l = numbers(used), numbers(latest)
    if l[0] > u[0]:
        return "major"
    if l[1] > u[1]:
        return "minor"
    if l[:3] > u[:3]:
        return "patch"
    return "newer than Central?"


def vulnerabilities(coordinates):
    """coordinates: list of (group, artifact, version). Returns {coordinate: [ids]} from OSV."""
    out = {}
    for i in range(0, len(coordinates), 200):
        chunk = coordinates[i:i + 200]
        body = json.dumps({"queries": [{"package": {"name": "%s:%s" % (g, a), "ecosystem": "Maven"}, "version": v}
                                       for g, a, v in chunk]}).encode()
        req = urllib.request.Request(OSV_BATCH, data=body, headers={"Content-Type": "application/json"})
        try:
            with urllib.request.urlopen(req, timeout=60) as r:
                results = json.loads(r.read().decode())["results"]
        except (urllib.error.URLError, TimeoutError, OSError, ValueError):
            for c in chunk:
                out[c] = None
            continue
        for c, res in zip(chunk, results):
            out[c] = [v["id"] for v in res.get("vulns", [])]
    return out


def vuln_summary(vuln_id):
    text = http_get(OSV_VULN + vuln_id)
    if not text:
        return vuln_id, "", ""
    d = json.loads(text)
    aliases = [a for a in d.get("aliases", []) if a.startswith("CVE-")]
    sev = ""
    for s in d.get("severity", []):
        sev = s.get("score", "")
        break
    dbs = d.get("database_specific", {}).get("severity", "")
    return vuln_id, (aliases[0] if aliases else ""), (dbs or sev)


def main():
    ap = argparse.ArgumentParser(description=__doc__, formatter_class=argparse.RawDescriptionHelpFormatter)
    ap.add_argument("--root", default=os.path.abspath(os.path.join(os.path.dirname(__file__), "..", "..")),
                    help="the workspace (default: the parent of the openflexo-buildplugin repository)")
    ap.add_argument("--output", help="write the report to this file instead of the standard output")
    ap.add_argument("--fail-on-vulnerability", action="store_true", help="exit with 1 if a version in use has a known vulnerability")
    args = ap.parse_args()

    found = collect(args.root)
    libs = sorted(found)
    with cf.ThreadPoolExecutor(12) as ex:
        latest = dict(zip(libs, ex.map(lambda k: latest_stable(*k), libs)))
    coords = [(g, a, v) for (g, a) in libs for v in sorted(found[(g, a)])]
    vulns = vulnerabilities(coords)
    all_ids = sorted({i for ids in vulns.values() if ids for i in ids})
    with cf.ThreadPoolExecutor(8) as ex:
        details = {i: (c, s) for i, c, s in ex.map(vuln_summary, all_ids)}

    lines = ["# Dependency drift report", "",
             "%d libraries, %d versions in use. Latest stable version according to Maven Central; vulnerabilities according to OSV."
             % (len(libs), len(coords)), "",
             "| Library | Version used | Latest stable | Gap | Vulnerabilities | Declared in |", "|---|---|---|---|---|---|"]
    rank = {"major": 0, "minor": 1, "patch": 2, "unknown": 3, "newer than Central?": 4, "up to date": 5}
    rows = []
    for g, a, v in coords:
        lat = latest[(g, a)]
        gp = gap(v, lat)
        ids = vulns.get((g, a, v))
        if ids is None:
            vtxt, vcount = "OSV unreachable", 0
        elif not ids:
            vtxt, vcount = "none", 0
        else:
            vtxt = ", ".join(details.get(i, ("", ""))[0] or i for i in ids[:4]) + (" (+%d)" % (len(ids) - 4) if len(ids) > 4 else "")
            vcount = len(ids)
        where = sorted(set(found[(g, a)][v]))
        wtxt = ", ".join(where[:2]) + (" (+%d)" % (len(where) - 2) if len(where) > 2 else "")
        rows.append((-vcount, rank.get(gp, 5), g, a, v, lat or "?", gp, vtxt, wtxt))
    for _, _, g, a, v, lat, gp, vtxt, wtxt in sorted(rows):
        lines.append("| %s:%s | %s | %s | %s | %s | %s |" % (g, a, v, lat, gp, vtxt, wtxt))
    with_vuln = sum(1 for r in rows if r[0] < 0)
    behind = sum(1 for r in rows if r[6] in ("major", "minor", "patch"))
    lines += ["", "Versions with a known vulnerability: %d. Versions behind the latest stable one: %d of %d." % (with_vuln, behind, len(rows))]
    report = "\n".join(lines) + "\n"
    if args.output:
        with open(args.output, "w", encoding="utf-8") as f:
            f.write(report)
        print("Report written to " + args.output)
    else:
        sys.stdout.write(report)
    return 1 if (args.fail_on_vulnerability and with_vuln) else 0


if __name__ == "__main__":
    sys.exit(main())
