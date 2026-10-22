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
- Tier B, lot B1 (cleanup): `javax.xml.ws.Holder` replaced by `AtomicReference` in `FlexoController` and the JAX-WS
  dependencies dropped from `flexo-ui`; unused JAXB declarations removed from `flexo-foundation`, `diana-core` and
  `formod-app`. Found while doing it: the real `javax` users are few and tied to a library that imposes them
  (docx4j for JAXB, Hibernate 5.2 for `javax.persistence`, Lyo for `javax.servlet`), so `javax` to `jakarta` moves
  to tier C with those libraries. Hibernate 5.2 and POI 3.17 still need JAXB at run time (declared in `jdbc-ta`
  and in the tests of `diana-palettes`). `pamela-spring-security-uc` is not part of the build (not in
  `pamela/settings.gradle`): delete or archive it.
- Tier C, docx: docx4j 6.1.2 to 17.3.3 (the single `docx4j` artifact stops at 6.1.2: now `docx4j-core` plus the JAXB
  reference implementation), JAXB on `jakarta.xml.bind` 4. API changes met: `RPrAbstract` is gone (`RPr` and
  `ParaRPr` have no common type any more; the TA only ever used `RPr`), `getStyleTree` and
  `getPropertyResolver().activateStyle` throw checked exceptions. `jdbc-ta` now declares the javax-only JAXB
  artifacts (`javax.xml.bind:jaxb-api`, `com.sun.xml.bind:jaxb-impl`): `jakarta.xml.bind-api` and `jaxb-runtime`
  hold the javax generation up to 2.x and the jakarta one from 3, so an application with both docx4j and Hibernate
  5.2 would otherwise resolve to the jakarta one. The 42 UI tests of `docx-ta-ui` (DOCX-D-1) were not run.
- Tier C, jdbc: Hibernate 5.2.10 to 6.6.59 (`org.hibernate.orm`), `javax.persistence` to `jakarta.persistence`. The TA
  builds the Hibernate mapping programmatically with internal classes, which changed a lot. What 6.x needs and 5.x
  did not (done in `HbnMetadataUtils.complete`, called before validating the metadata): resolve each `BasicValue`;
  a to-one association that references the identifier must reference the primary key, not name the property;
  every column of a table must be bound to a value; a property with nullable columns must be flagged optional.
  Also: `BootstrapContext` and the type configuration scope are created explicitly, `RootClass` needs an
  optimistic lock style, `hibernate.column_ordering_strategy=legacy`. The JAXB (javax) workaround and javassist
  declarations of `jdbc-ta` are gone. A test builder added a unique key to the wrong table (fixed). Hibernate 7
  is the next step if wanted (BUILD-F-5).
- Tier C, POI: 3.17 and 3.12 to 5.5.1 (`poi()`, `poiOoxml()`, `poiScratchpad()`), one version for the whole
  workspace (xlsx, pptx, diagram, diana-ppt-editor, diana-palettes). docx and odt do not use POI. API changes met:
  `getCellTypeEnum` and `getBorderXEnum` are now `getCellType` and `getBorderX`, `HSSFColor.X.index` is
  `HSSFColorPredefined.X.getIndex()`, `XSSFColor` takes an `IndexedColorMap`, `WorkbookFactory.create` no longer
  throws `InvalidFormatException`, the geometry paths are `PathIf`. The importer of binary `.ppt` slides of the
  diagram TA (`AbstractCreateDiagramFromPPTSlide`, 1300 lines) used the `org.apache.poi.hslf.model` classes,
  removed in POI 4: it now uses `HSLF*` (`HSLFShape`, `HSLFSlide`, ...), the `sl.usermodel.ShapeType` enum, the
  `EscherPropertyTypes` enum, text paragraphs and runs, and `getAnchor()` instead of the outline and logical anchor.
  Differences to check by eye on real slides: a rotated shape was matched on its outline, it is matched on its
  anchor now. `TestActions` only checks that the import runs (its assertion on the number of shapes is commented
  out), so it does not prove that the diagram is the same.
- JDK 25: the 96 test modules give the same results as on 17. The applications (formod and Flexo Maintainer) were
  started by hand on JDK 25: the first start died in the FML editor support (UI-D-3, fixed), which no headless test
  reaches. Lesson: a JDK change is validated by starting the applications, not only by the tests.
- Gradle 9 (9.8.2 in the `openflexo-dev` composite): the whole workspace configures, compiles and the SableCC
  grammars regenerate. What Gradle 9 removed and was replaced: the `Convention` API that carried the dependency helpers
  (they are closures of the extra properties now, set by `OpenFlexoBuild.exposeHelpers`; the helper `openflexo(name)` is
  `openflexoModule(name)` because it would hide the extension `openflexo`), `JavaPluginConvention`, `DslObject` and
  `ConfigureUtil` in `gradle-sablecc-plugin` (1.4.0), `mainClassName` (6 applications, now `application { mainClass }`),
  a module declared in a `settings.gradle` without a directory (`diagram-ta-test`). The Artifactory plugin 5.2.0 still
  loads (not upgraded); an upload was not tried. Left for Gradle 10: the `propName value` syntax (48 places), the
  multi-string dependency notation, `Configuration.setVisible`, `afterSuite(Closure)`. The wrappers of the 41 repositories
  moved to 9.8.2 on 2026-10-21 once both plugins were published (build plugin 0.4, SableCC plugin 1.4.0). Checked before the
  change, with the 9.8.2 distribution on each repository alone against the artifacts published by Jenkins: `compile` green for
  39 repositories, `build` green for the two plugin repositories (they have no `compile` task), the tests of `connie` green.
  Not moved: `website` (its `buildWebsite` clones, commits and pushes; not tried on Gradle 9). The Gradle jobs of the 3.0 line
  all run on JAVA 17, which Gradle 9 needs; only the two Maven packaging jobs are on JAVA 11 and do not use the wrapper.
- Tier A, left over: `h2` (legacy sample `pamela-spring-security-uc` only), `xerces` (2.12.2 is the last release),
  `jline` 2.11 (the successor `org.jline` has another API: tier C).
- Tier B: `javax` to `jakarta` (JAXB, servlet, annotation, jaxws, persistence with Hibernate 6).
- Tier C, one technology adapter at a time: POI, docx4j, jena, PDFBox, kafka, milo, OSLC/Lyo, spoon, UML2/EMF,
  xtext/lsp4j, velocity, RSyntaxTextArea/SwingX, Spring Boot application, HttpClient 5 (BUILD-F-2).
- Gradle 9: `project.convention` (used to expose the helpers) and the Artifactory plugin must be replaced or
  re-checked; run the deprecation report first.
- Java code flagged by the scan: `SecurityManager` (3 files), `finalize()` (12 files), `com.apple.eawt` (4 files),
  `--add-modules java.xml.bind` in 3 `build.gradle`.
- Tracking, tool done 2026-10-22: `tools/dependency-report.py` (Python 3 standard library, no build dependency) lists every
  third-party library declared in the workspace (the `OpenFlexoThirdParty` constants and the notations left in the
  `build.gradle` files), its latest stable version on Maven Central and its known vulnerabilities in OSV. It is informative
  (exit code 0; `--fail-on-vulnerability` for a gate). First run: 84 versions, 43 behind the latest stable one, 8 with a known
  vulnerability. A periodic Jenkins job running it is still to do (JENKINS-F-5).
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

### BUILD-F-5 — Hibernate 7  ·  `TODO`

**Problem.** `jdbc-ta` is on Hibernate 6.6 (the last 6.x). Hibernate 7 (7.4, Jakarta Persistence 3.2, Java 17) is the
current line. The adapter uses Hibernate internals (`MetadataBuilderImpl`, `InFlightMetadataCollectorImpl`,
`BasicValue`, `HbnMetadataUtils`): expect another round of changes.

**Acceptance.** `jdbc-ta-test` and `jdbc-ta-ui` green (65 tests, the FML script `TestJDBCQuery` included).

### BUILD-F-6 — Version policy of the build plugins  ·  `IN PROGRESS`

**Problem (2026-10-21).** Versions of the plugins changed content without changing number: the build plugin `0.3` is
published by the Jenkins job of each push of the 3.0 branch (and probably of 2.99), into `openflexo-release`, which accepts
overwriting; its `0.4` of September 2025 was never used. The SableCC plugin sat on a branch named `1.3.0` with a Jenkins job
on JAVA 8, and the Artifactory plugin was resolved as `latest.release` in two places and `5.2.0` in a third.

**Decisions.**
- Build plugin: `0.4` (the 40 repositories pin it), republished once. From now on a published number is never published
  again: any change that a repository can see bumps it.
- SableCC plugin: `1.4.0`, built with `--release 17`, branch `3.0`, Jenkins job `gradle-sablecc-plugin-3.0` on JAVA 17. The
  repositories ask for `[1.4.0,)`: 1.3.0 does not work with Gradle 9. The job `gradle-sablecc-plugin-1.3.0` is left as it is.
- Artifactory plugin (`build-info-extractor-gradle`): `5.2.0` everywhere.

**Release order.** SableCC plugin 1.4.0, build plugin 0.4, then the repositories; the wrappers move to Gradle 9 last.

### BUILD-F-7 — Libraries with a known vulnerability  ·  `IN PROGRESS`

**Problem.** The first run of `tools/dependency-report.py` (2026-10-22) found 8 versions with known vulnerabilities in the
build of the workspace (the unbuilt sample `pamela-spring-security-uc` is left out).

| Library | In use | Where | Way out |
|---|---|---|---|
| `pdfbox` | 2.0.2 | `pdf-ta` | latest 2.0.x: same major version, no API change expected |
| `vertx-core`, `vertx-web` (and `vertx-auth-htpasswd`, `-web-client`, `-unit`, `-junit5` for consistency) | 4.3.6 and 4.3.7 | `http-server` | latest 4.x first; 5.x later (138 tests in `http-server` as a net) |
| `guava` | 27.0-jre | `gradle-sablecc-plugin` | only `CaseFormat` is used: replace it and drop the dependency (a new plugin number) |
| `jena-core` | 3.1.0 | `owl-ta` | tier C (Jena 5/6) |
| `kafka-clients` | 0.10.2.0 | `kafka-ta` | tier C (Kafka 3/4) |
| `velocity`, `velocity-tools` | 1.7, 2.0 | `ta-doc-generator` | no fixed version of 1.x: Velocity 2.x or another template engine |

**Done 2026-10-22 (lot A3).** `pdfbox` 2.0.37 and the Vert.x modules 4.5.35, written in the `build.gradle` of the module: each is used by one module only, so
no helper was added (a helper changes the published build plugin, which would need a new number). `pdf-ta` 19 of 19 (the test closed the
`COSDocument` that belongs to the `PDDocument` and read the page afterwards; pdfbox 2.0.x refuses a read after the close, the test was
fixed), `http-server` 138 of 138.

**Acceptance.** `tools/dependency-report.py` reports no known vulnerability for the build of the workspace.
