# openflexo-buildplugin — Backlog

Features and evolutions of the build plugin and of the build infrastructure of the whole workspace. Known bugs go
to `KNOWN_DEFECTS.md` (identifiers `BUILD-D-<n>`), not here.

Identifiers are stable and never reused: `BUILD-F-<n>`. Reference them from commits and from other entries.

Status legend: `TODO` · `IN PROGRESS` · `BLOCKED` · `DONE` · `DEFERRED`.

---

## Modernization of the infrastructure

### BUILD-F-1 — Recent Java, recent Gradle, tracked third-party dependencies  ·  `IN PROGRESS`

**Problem.** The 3.0 workspace must build and run on recent Java (floor 17, checked on newer JDKs), on a recent
Gradle, with up-to-date third-party libraries and a way to see them drift. API breaks are accepted.

**Decisions (2026-10-19).** Bytecode floor Java 17 (`--release 17`), tested on 21 (LTS) and 25 as a canary.
Gradle 8.14.x everywhere first, then Gradle 9 as a separate step. Third-party versions are centralized as helpers
of `OpenFlexoConvention` (`guava()`, `jacksonDatabind()`, ...) with the versions in `OpenFlexoThirdParty`: one
version per library for the whole workspace, never written in a `build.gradle`.

**Done.**
- Phase 1: the 9 repositories that were behind are on Gradle 8.14.3; `options.release = 17`; `-PjavaVersion=<n>` runs
  the build and the tests on another JDK; `jcenter()` and the Groovy property syntax removed from this plugin and
  from `gradle-sablecc-plugin`.
- Baseline: compile and 96 test modules (connie to formod, TA included) give the same results on Java 17 and 25.
- Tier A, lot A1: Jackson 2.22.4, Guava 33.7.2-jre, Gson 2.14.0, HttpClient 4.5.14, one version each.
- Tier A, lot A2: commons-io 2.22, commons-lang3 3.21, commons-compress 1.28, commons-codec 1.23, commons-dbutils
  1.8.1, hsqldb 2.7.4, jgit 7.8, JUNG 2.1.1, Batik 1.19, slf4j 2.0.20. API changes met: JUNG 2.1 uses Guava
  `Function` (`TreeLayout.apply` instead of `transform`); HSQLDB 2.7 returns an unreadable generated-keys result
  set for an INSERT that gives its key (`SQLHelper.insert` now falls back on the primary key of the line).

**To do.**
- Tier A, left over: `h2` (legacy sample `pamela-spring-security-uc` only), `xerces` (2.12.2 is the last release),
  `jline` 2.11 (the successor `org.jline` has another API: tier C).
- Tier B: `javax` to `jakarta` (JAXB, servlet, annotation, jaxws, persistence with Hibernate 6).
- Tier C, one technology adapter at a time: POI, docx4j, jena, PDFBox, kafka, milo, OSLC/Lyo, spoon, UML2/EMF,
  xtext/lsp4j, velocity, RSyntaxTextArea/SwingX, Spring Boot application, HttpClient 5 (BUILD-F-2).
- Gradle 9: `project.convention` (used to expose the helpers) and the Artifactory plugin must be replaced or
  re-checked; run the deprecation report first.
- Java code flagged by the scan: `SecurityManager` (3 files), `finalize()` (12 files), `com.apple.eawt` (4 files),
  `--add-modules java.xml.bind` in 3 `build.gradle`.
- Tracking: version drift report (versions plugin or Renovate) and a vulnerability scan on Jenkins, non blocking.
- Jenkins: a JDK 21 tool and 3.0 jobs on it, a JDK 25 canary (needs the user's approval).

**Acceptance.** `./gradlew test` gives the same `Results:` on Java 17 and 21 for every module; no third-party
version is written in a `build.gradle`; a report lists the outdated and the vulnerable dependencies.

### BUILD-F-2 — Move from HttpClient 4.x to HttpClient 5  ·  `TODO`

**Problem.** HttpClient 4.5.14 is the end of the 4.x line (no release since December 2022). Its successor
`org.apache.hc.client5` has another package and another API.

**Users.** `rest-ta` (`openflexo-rest`) and `oslc-ta` (`openflexo-oslc`). `oslc-ta` also depends on
`oauth-httpclient4` and on the Lyo client, both built on the 4.x line: the move may force a Lyo upgrade or the
replacement of the OAuth client.

**Options.** Port the two TA to HttpClient 5; or use the JDK `java.net.http.HttpClient` (no dependency, Java 11+)
if the features used allow it.

**Acceptance.** `rest-ta-test` and `oslc-ta` tests green, no `org.apache.httpcomponents:httpclient` left.

### BUILD-F-3 — Replace jsr305 (`javax.annotation.Nonnull` / `Nullable`)  ·  `TODO`

**Problem.** jsr305 3.0.2 has not been released since 2017. Guava 33.7 does not bring it anymore, so it is declared
as `compileOnly jsr305()` in `pamela-core`, `flexographicutils`, `gina-swing` and `flexo-foundation` (8 source
files). It also lives in the `javax.annotation` package.

**Options.** JSpecify 1.0.1 (`org.jspecify.annotations.NonNull` / `Nullable`), which is the current standard; or
remove these annotations, which carry little information here.

**Acceptance.** No `javax.annotation.Nonnull` / `Nullable` import and no `jsr305()` helper left.

### BUILD-F-4 — Jackson 3  ·  `DEFERRED`

**Problem.** Jackson 2.22 is the last line of 2.x. Jackson 3 (3.2.x) moved to the `tools.jackson` packages, with
API breaks.

**Decision.** Stay on 2.x while it is maintained and nothing requires 3. Users: `rest-ta`, `json-ta`,
`pamela-editor`, `http-server`. Re-evaluate when a library of the workspace needs Jackson 3 or 2.x reaches its end
of support.
