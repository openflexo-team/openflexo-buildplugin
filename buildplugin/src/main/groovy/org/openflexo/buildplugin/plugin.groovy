package org.openflexo.buildplugin

import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.api.tasks.testing.Test
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.api.tasks.javadoc.Javadoc
import org.gradle.api.publish.maven.MavenPublication

/**
 * OpenFlexo build plugin configuration
 */
class OpenFlexoExtension {
    String utilsVersion = ""
    String p2ppVersion = ""
    String connieVersion = ""
    String pamelaVersion = ""
    String ginaVersion = ""
    String dianaVersion = ""
    String openflexoVersion = ""
}

/**
 * Versions of the third-party libraries shared by several OpenFlexo projects
 */
/**
 * Settings of the <code>jpackage</code> task, available as <code>jpackage { ... }</code> in any project that applies the
 * <code>application</code> plugin. Everything is optional: the application name defaults to the project name.
 */
class OpenFlexoJPackage {
    /** Name of the application (and of the installer). */
    String appName
    String vendor = 'Openflexo'
    String description
    /** Icons, one per platform (.icns on macOS, .ico on Windows, .png on Linux); `icon` is the fallback of the three. */
    File icon
    File iconMac
    File iconWindows
    File iconLinux
    /** A command line application: no window on Windows. */
    boolean console = false
    /** Installer type (dmg, pkg, msi, exe, deb, rpm) or app-image; null for the default one of the platform. */
    String type
    /** Options of the JVM of the installed application. */
    List<String> jvmOptions = [
            '-Xms1g', '-Xmx2g', '-Xss2m',
            '--add-opens=java.base/java.util=ALL-UNNAMED',
            '--add-opens=java.base/java.lang=ALL-UNNAMED',
            '--add-opens=java.base/java.lang.reflect=ALL-UNNAMED',
            '--add-opens=java.base/java.text=ALL-UNNAMED',
            '--add-opens=java.desktop/java.awt.font=ALL-UNNAMED'
    ]
    /** Modules of the embedded runtime. jdeps cannot see what the application loads by reflection, so the platform is taken whole. */
    List<String> addModules = ['java.se', 'jdk.unsupported', 'jdk.charsets', 'jdk.localedata', 'jdk.crypto.ec', 'jdk.zipfs']
    /** Further arguments passed as is to jpackage. */
    List<String> extraArgs = []
}

class OpenFlexoThirdParty {
    static final String JACKSON = "2.22.4"
    static final String GUAVA = "33.7.2-jre"
    static final String GSON = "2.14.0"
    static final String HTTPCLIENT = "4.5.14"
    static final String JSR305 = "3.0.2"
    static final String POI = "5.5.1"
    static final String HIBERNATE = "6.6.59.Final"
    static final String DOCX4J = "17.3.4"
    static final String JAKARTA_XML_BIND = "4.0.5"
    static final String JAXB_RUNTIME = "4.0.9"
    static final String COMMONS_IO = "2.22.0"
    static final String COMMONS_LANG3 = "3.21.0"
    static final String COMMONS_COMPRESS = "1.28.0"
    static final String COMMONS_CODEC = "1.23.0"
    static final String COMMONS_CSV = "1.14.1"
    static final String COMMONS_DBUTILS = "1.8.1"
    static final String HSQLDB = "2.7.4"
    static final String JGIT = "7.8.0.202609011348-r"
    static final String JUNG = "2.1.1"
    static final String BATIK = "1.20"
    static final String XERCES = "2.12.2"
    static final String SLF4J = "2.0.20"
}

/**
 * OpenFlexoConvention to declare dependencies
 */
class OpenFlexoConvention {

    Project project;

    OpenFlexoConvention(project) {
        this.project = project;
    }

    String docx4all() {
        return "org.openflexo:docx4all:${project.openflexo.utilsVersion}"
    }

    String flexoHelp() {
        return "org.openflexo:flexohelp:${project.openflexo.utilsVersion}"
    }

    String flexoUtils() {
        return "org.openflexo:flexo-utils:${project.openflexo.utilsVersion}"
    }

    String p2pp() {
        return "org.openflexo:p2pp-core:${project.openflexo.p2ppVersion}"
    }

    String testUtils() {
        return "org.openflexo:testutils:${project.openflexo.utilsVersion}"
    }

    String connie() {
        return "org.openflexo:connie-core:${project.openflexo.connieVersion}"
    }

    String connieDEL() {
        return "org.openflexo:default-expression-language:${project.openflexo.connieVersion}"
    }

    String connieJava() {
        return "org.openflexo:java-expression-language:${project.openflexo.connieVersion}"
    }

    String pamela() {
        return "org.openflexo:pamela-core:${project.openflexo.pamelaVersion}"
    }

    String flexoGraphicUtils() {
        return "org.openflexo:flexographicutils:${project.openflexo.ginaVersion}"
    }

    String flexoLocalization() {
        return "org.openflexo:flexolocalization:${project.openflexo.ginaVersion}"
    }

    String ginaApi() {
        return "org.openflexo:gina-api:${project.openflexo.ginaVersion}"
    }

    String gina() {
        return "org.openflexo:gina-core:${project.openflexo.ginaVersion}"
    }

    String ginaEvents() {
        return "org.openflexo:gina-events:${project.openflexo.ginaVersion}"
    }

    String ginaReplay() {
        return "org.openflexo:gina-replay:${project.openflexo.ginaVersion}"
    }

    String ginaReplayTest() {
        return "org.openflexo:gina-replay-test:${project.openflexo.ginaVersion}"
    }

    String ginaSwing() {
        return "org.openflexo:gina-swing:${project.openflexo.ginaVersion}"
    }

    String ginaSwingEditor() {
        return "org.openflexo:gina-swing-editor:${project.openflexo.ginaVersion}"
    }

    String ginaSwingEditorTest() {
        return "org.openflexo:gina-swing-editor-test:${project.openflexo.ginaVersion}"
    }

    String ginaTest() {
        return "org.openflexo:gina-test:${project.openflexo.ginaVersion}"
    }

    String dianaApi() {
        return "org.openflexo:diana-api:${project.openflexo.dianaVersion}"
    }

    String diana() {
        return "org.openflexo:diana-core:${project.openflexo.dianaVersion}"
    }

    String dianaTest() {
        return "org.openflexo:diana-core-test:${project.openflexo.dianaVersion}"
    }

    String dianaDrawingEditor() {
        return "org.openflexo:diana-drawing-editor:${project.openflexo.dianaVersion}"
    }

    String dianaGeom() {
        return "org.openflexo:diana-geom:${project.openflexo.dianaVersion}"
    }

    String dianaPalettes() {
        return "org.openflexo:diana-palettes:${project.openflexo.dianaVersion}"
    }

    String dianaPptEditor() {
        return "org.openflexo:diana-ppt-editor:${project.openflexo.dianaVersion}"
    }

    String dianaSwing() {
        return "org.openflexo:diana-swing:${project.openflexo.dianaVersion}"
    }

    String flexoDocumentation() {
        return "org.openflexo:flexo-documentation:${project.openflexo.openflexoVersion}"
    }

    String flexoDocumentationUi() {
        return "org.openflexo:flexo-documentation-ui:${project.openflexo.openflexoVersion}"
    }

    String flexoFoundation() {
        return "org.openflexo:flexo-foundation:${project.openflexo.openflexoVersion}"
    }

    String flexoFoundationRm() {
        return "org.openflexo:flexo-foundation-rm:${project.openflexo.openflexoVersion}"
    }

    String fmlCli() {
        return "org.openflexo:fml-cli:${project.openflexo.openflexoVersion}"
    }

    String fmlCliTest() {
        return "org.openflexo:fml-cli-test:${project.openflexo.openflexoVersion}"
    }

    String fmlDiagrammingExtension() {
        return "org.openflexo:fml-diagramming-extension:${project.openflexo.openflexoVersion}"
    }

    String fmlGinaExtension() {
        return "org.openflexo:fml-gina-extension:${project.openflexo.openflexoVersion}"
    }

    String flexoFoundationTest() {
        return "org.openflexo:flexo-foundation-test:${project.openflexo.openflexoVersion}"
    }

    String flexoOntology() {
        return "org.openflexo:flexo-ontology:${project.openflexo.openflexoVersion}"
    }

    String flexoOntologyUi() {
        return "org.openflexo:flexo-ontology-ui:${project.openflexo.openflexoVersion}"
    }

    String flexoTestResources() {
        return "org.openflexo:flexo-test-resources:${project.openflexo.openflexoVersion}"
    }

    String bugReporting() {
        return "org.openflexo:bug-reporting:${project.openflexo.openflexoVersion}"
    }

    String flexoUi() {
        return "org.openflexo:flexo-ui:${project.openflexo.openflexoVersion}"
    }

    String flexoUiTest() {
        return "org.openflexo:flexo-ui-test:${project.openflexo.openflexoVersion}"
    }

    String flexoDocResourceCenter() {
        return "org.openflexo:flexodocresourcecenter:${project.openflexo.openflexoVersion}"
    }

    String flexoDocResourceManager() {
        return "org.openflexo:flexodocresourcemanager:${project.openflexo.openflexoVersion}"
    }

    String fmlParser() {
        return "org.openflexo:fml-parser:${project.openflexo.openflexoVersion}"
    }

    String fmlTechnologyAdapterUi() {
        return "org.openflexo:fml-technologyadapter-ui:${project.openflexo.openflexoVersion}"
    }

    String fmlRtTechnologyAdapterUi() {
        return "org.openflexo:fml-rt-technologyadapter-ui:${project.openflexo.openflexoVersion}"
    }

    String docxConnector() {
        return "org.openflexo:docx-ta:${project.openflexo.openflexoVersion}"
    }

    String emfConnector() {
        return "org.openflexo:emf-ta:${project.openflexo.openflexoVersion}"
    }

    String excelConnector() {
        return "org.openflexo:xlsx-ta:${project.openflexo.openflexoVersion}"
    }

    String diagramConnector() {
        return "org.openflexo:diagram-ta:${project.openflexo.openflexoVersion}"
    }

    String ginaConnector() {
        return "org.openflexo:gina-ta:${project.openflexo.openflexoVersion}"
    }

    String jdbcConnector() {
        return "org.openflexo:jdbc-ta:${project.openflexo.openflexoVersion}"
    }
    String jdbcConnectorTest() {
        return "org.openflexo:jdbc-ta-test:${project.openflexo.openflexoVersion}"
    }

    String odtConnector() {
        return "org.openflexo:odt-ta:${project.openflexo.openflexoVersion}"
    }

    String oslcConnector() {
        return "org.openflexo:oslc-ta:${project.openflexo.openflexoVersion}"
    }

    String owlConnector() {
        return "org.openflexo:owl-ta:${project.openflexo.openflexoVersion}"
    }

    String owlConnectorRc() {
        return "org.openflexo:owl-ta-rc:${project.openflexo.openflexoVersion}"
    }

    String pdfConnector() {
        return "org.openflexo:pdf-ta:${project.openflexo.openflexoVersion}"
    }

    String powerpointConnector() {
        return "org.openflexo:pptx-ta:${project.openflexo.openflexoVersion}"
    }

    String httpConnector() {
        return "org.openflexo:http-connector:${project.openflexo.openflexoVersion}"
    }

    String kafkaConnector() {
        return "org.openflexo:kafka-ta:${project.openflexo.openflexoVersion}"
    }

    String javaConnector() {
        return "org.openflexo:java-ta:${project.openflexo.openflexoVersion}"
    }

    String opcUaConnector() {
        return "org.openflexo:opc-ua-ta:${project.openflexo.openflexoVersion}"
    }

    String httpConnectorUi() {
        return "org.openflexo:http-connector-ui:${project.openflexo.openflexoVersion}"
    }

    String httpConnectorRc() {
        return "org.openflexo:http-connector-rc:${project.openflexo.openflexoVersion}"
    }

    String kafkaConnectorUi() {
        return "org.openflexo:kafka-ta-ui:${project.openflexo.openflexoVersion}"
    }

    String javaConnectorUi() {
        return "org.openflexo:java-ta-ui:${project.openflexo.openflexoVersion}"
    }

    String opcUaConnectorUi() {
        return "org.openflexo:opc-ua-ta-ui:${project.openflexo.openflexoVersion}"
    }

    String xmlConnector() {
        return "org.openflexo:xml-ta:${project.openflexo.openflexoVersion}"
    }

    String docxConnectorUi() {
        return "org.openflexo:docx-ta-ui:${project.openflexo.openflexoVersion}"
    }

    String emfConnectorUi() {
        return "org.openflexo:emf-ta-ui:${project.openflexo.openflexoVersion}"
    }

    String excelConnectorUi() {
        return "org.openflexo:xlsx-ta-ui:${project.openflexo.openflexoVersion}"
    }

    String diagramConnectorUi() {
        return "org.openflexo:diagram-ta-ui:${project.openflexo.openflexoVersion}"
    }

    String ginaConnectorUi() {
        return "org.openflexo:gina-ta-ui:${project.openflexo.openflexoVersion}"
    }

    String jdbcConnectorUi() {
        return "org.openflexo:jdbc-ta-ui:${project.openflexo.openflexoVersion}"
    }

    String odtConnectorUi() {
        return "org.openflexo:odt-ta-ui:${project.openflexo.openflexoVersion}"
    }

    String oslcConnectorUi() {
        return "org.openflexo:oslc-ta-ui:${project.openflexo.openflexoVersion}"
    }

    String owlConnectorUi() {
        return "org.openflexo:owl-ta-ui:${project.openflexo.openflexoVersion}"
    }

    String pdfConnectorUi() {
        return "org.openflexo:pdf-ta-ui:${project.openflexo.openflexoVersion}"
    }

    String powerpointConnectorUi() {
        return "org.openflexo:pptx-ta-ui:${project.openflexo.openflexoVersion}"
    }

    String xmlConnectorUi() {
        return "org.openflexo:xml-ta-ui:${project.openflexo.openflexoVersion}"
    }

    // Not named openflexo(): it would hide the extension openflexo (versions) once exposed as an extra property
    String openflexoModule(String name) {
        return "org.openflexo:$name:${project.openflexo.openflexoVersion}"
    }

    String obp2Connector() {
        return "org.openflexo:obp2-ta:${project.openflexo.openflexoVersion}"
    }

    String obp2ConnectorRc() {
        return "org.openflexo:obp2-ta-rc:${project.openflexo.openflexoVersion}"
    }

    String obp2ConnectorUi() {
        return "org.openflexo:obp2-ta-ui:${project.openflexo.openflexoVersion}"
    }

    String csvConnector() {
        return "org.openflexo:csv-ta:${project.openflexo.openflexoVersion}"
    }

    String csvConnectorUi() {
        return "org.openflexo:csv-ta-ui:${project.openflexo.openflexoVersion}"
    }
    
    String mcpConnector() {
        return "org.openflexo:mcp-ta:${project.openflexo.openflexoVersion}"
    }

    String mcpConnectorUi() {
        return "org.openflexo:mcp-ta-ui:${project.openflexo.openflexoVersion}"
    }

    String restConnector() {
        return "org.openflexo:rest-ta:${project.openflexo.openflexoVersion}"
    }

    String restConnectorRc() {
        return "org.openflexo:rest-ta-rc:${project.openflexo.openflexoVersion}"
    }

    String restConnectorUi() {
        return "org.openflexo:rest-ta-ui:${project.openflexo.openflexoVersion}"
    }

    String markdownConnector() {
        return "org.openflexo:markdown-ta:${project.openflexo.openflexoVersion}"
    }

    String markdownConnectorUi() {
        return "org.openflexo:markdown-ta-ui:${project.openflexo.openflexoVersion}"
    }

    String jsonConnector() {
        return "org.openflexo:json-ta:${project.openflexo.openflexoVersion}"
    }

    String jsonConnectorUi() {
        return "org.openflexo:json-ta-ui:${project.openflexo.openflexoVersion}"
    }

    String xxConnector() {
        return "org.openflexo:xx-ta:${project.openflexo.openflexoVersion}"
    }

    String xxConnectorUi() {
        return "org.openflexo:xx-ta-ui:${project.openflexo.openflexoVersion}"
    }

    String dslConnector() {
        return "org.openflexo:dsl-ta:${project.openflexo.openflexoVersion}"
    }

    String dslConnectorUi() {
        return "org.openflexo:dsl-ta-ui:${project.openflexo.openflexoVersion}"
    }

    String rhapsodyConnector() {
        return "org.openflexo:rhapsody-ta:${project.openflexo.openflexoVersion}"
    }

    String rhapsodyConnectorUi() {
        return "org.openflexo:rhapsody-ta-ui:${project.openflexo.openflexoVersion}"
    }

    // Third-party libraries: ONE version per library for the whole workspace.
    // Never write the version of one of these in a build.gradle; bump it here.

    String jacksonCore() {
        return "com.fasterxml.jackson.core:jackson-core:${OpenFlexoThirdParty.JACKSON}"
    }

    String jacksonDatabind() {
        return "com.fasterxml.jackson.core:jackson-databind:${OpenFlexoThirdParty.JACKSON}"
    }

    String guava() {
        return "com.google.guava:guava:${OpenFlexoThirdParty.GUAVA}"
    }

    String gson() {
        return "com.google.code.gson:gson:${OpenFlexoThirdParty.GSON}"
    }

    // javax.annotation.Nonnull / Nullable. Guava used to bring it, it does not anymore.
    String jsr305() {
        return "com.google.code.findbugs:jsr305:${OpenFlexoThirdParty.JSR305}"
    }

    String commonsIo() {
        return "commons-io:commons-io:${OpenFlexoThirdParty.COMMONS_IO}"
    }

    String commonsLang3() {
        return "org.apache.commons:commons-lang3:${OpenFlexoThirdParty.COMMONS_LANG3}"
    }

    String commonsCompress() {
        return "org.apache.commons:commons-compress:${OpenFlexoThirdParty.COMMONS_COMPRESS}"
    }

    String commonsCodec() {
        return "commons-codec:commons-codec:${OpenFlexoThirdParty.COMMONS_CODEC}"
    }

    String commonsCsv() {
        return "org.apache.commons:commons-csv:${OpenFlexoThirdParty.COMMONS_CSV}"
    }

    String commonsDbutils() {
        return "commons-dbutils:commons-dbutils:${OpenFlexoThirdParty.COMMONS_DBUTILS}"
    }

    String hsqldb() {
        return "org.hsqldb:hsqldb:${OpenFlexoThirdParty.HSQLDB}"
    }

    String hsqldbSqltool() {
        return "org.hsqldb:sqltool:${OpenFlexoThirdParty.HSQLDB}"
    }

    String jgit() {
        return "org.eclipse.jgit:org.eclipse.jgit:${OpenFlexoThirdParty.JGIT}"
    }

    String jungAlgorithms() {
        return "net.sf.jung:jung-algorithms:${OpenFlexoThirdParty.JUNG}"
    }

    String jungGraphImpl() {
        return "net.sf.jung:jung-graph-impl:${OpenFlexoThirdParty.JUNG}"
    }

    String batikCodec() {
        return "org.apache.xmlgraphics:batik-codec:${OpenFlexoThirdParty.BATIK}"
    }

    String batikDom() {
        return "org.apache.xmlgraphics:batik-dom:${OpenFlexoThirdParty.BATIK}"
    }

    String batikSvggen() {
        return "org.apache.xmlgraphics:batik-svggen:${OpenFlexoThirdParty.BATIK}"
    }

    String xerces() {
        return "xerces:xercesImpl:${OpenFlexoThirdParty.XERCES}"
    }

    String slf4jJdk14() {
        return "org.slf4j:slf4j-jdk14:${OpenFlexoThirdParty.SLF4J}"
    }

    String jclOverSlf4j() {
        return "org.slf4j:jcl-over-slf4j:${OpenFlexoThirdParty.SLF4J}"
    }

    // docx4j is split in a core and a JAXB implementation since 8.x (the single artifact docx4j stops at 6.1.2)
    String docx4jCore() {
        return "org.docx4j:docx4j-core:${OpenFlexoThirdParty.DOCX4J}"
    }

    String docx4jJaxbReferenceImpl() {
        return "org.docx4j:docx4j-JAXB-ReferenceImpl:${OpenFlexoThirdParty.DOCX4J}"
    }

    String jakartaXmlBindApi() {
        return "jakarta.xml.bind:jakarta.xml.bind-api:${OpenFlexoThirdParty.JAKARTA_XML_BIND}"
    }

    String jaxbRuntime() {
        return "org.glassfish.jaxb:jaxb-runtime:${OpenFlexoThirdParty.JAXB_RUNTIME}"
    }

    // Hibernate ORM (group org.hibernate.orm since 6.0, Jakarta Persistence)
    String hibernateCore() {
        return "org.hibernate.orm:hibernate-core:${OpenFlexoThirdParty.HIBERNATE}"
    }

    // Apache POI: spreadsheets (poi, poi-ooxml), slideshows (poi-scratchpad for the binary .ppt, poi-ooxml for .pptx)
    String poi() {
        return "org.apache.poi:poi:${OpenFlexoThirdParty.POI}"
    }

    String poiOoxml() {
        return "org.apache.poi:poi-ooxml:${OpenFlexoThirdParty.POI}"
    }

    String poiScratchpad() {
        return "org.apache.poi:poi-scratchpad:${OpenFlexoThirdParty.POI}"
    }

    String httpClient() {
        return "org.apache.httpcomponents:httpclient:${OpenFlexoThirdParty.HTTPCLIENT}"
    }
 
}

/**
 * Gradle build plugin for OpenFlexo projects
 *
 * Created by charlie on 06/01/2017.
 */
class OpenFlexoBuild implements Plugin<Project> {

    /**
     * Exposes each public method of the helpers (a method that returns a dependency notation) as a closure of the extra
     * properties of the project and of its subprojects (evaluated later), so that a build script can call it as <code>connie()</code>.
     */
    private static void exposeHelpers(Project project, OpenFlexoConvention helpers) {
        def names = OpenFlexoConvention.declaredMethods.findAll {
            java.lang.reflect.Modifier.isPublic(it.modifiers) && !it.synthetic && it.returnType == String
        }.collect { it.name }.unique()
        def expose = { Project target ->
            names.each { String name ->
                target.ext.set(name, { Object... args -> helpers."${name}"(*args) })
            }
        }
        expose(project)
        project.subprojects { Project sub -> expose(sub) }
    }


    /**
     * Adds to a project that applies <code>application</code> the task <code>jpackage</code>, which builds a native installer
     * (or application image) embedding a Java runtime: the application and its runtime classpath are staged in
     * <code>build/jpackage/input</code>, the result lands in <code>build/jpackage/dist</code>. jpackage builds for the platform
     * it runs on, with the JDK of the toolchain of the project.
     */
    private static void configureJPackage(Project p) {
        def cfg = p.extensions.create('jpackage', OpenFlexoJPackage)
        def jar = p.tasks.named('jar')
        def stagingDir = p.layout.buildDirectory.dir('jpackage/input')
        def distDir = p.layout.buildDirectory.dir('jpackage/dist')

        def stage = p.tasks.register('jpackageInput', org.gradle.api.tasks.Sync) {
            description = "Stages the jars of ${p.name} for jpackage."
            from(jar)
            from(p.configurations.named('runtimeClasspath'))
            into(stagingDir)
        }

        p.tasks.register('jpackage', org.gradle.api.tasks.Exec) {
            group = 'Distribution'
            description = "Builds a native installer of ${p.name} with jpackage."
            dependsOn(stage)
            doFirst {
                def launcher = p.extensions.getByType(org.gradle.jvm.toolchain.JavaToolchainService)
                        .launcherFor(p.extensions.getByType(org.gradle.api.plugins.JavaPluginExtension).toolchain).get()
                def jpackageBin = launcher.metadata.installationPath.file('bin/jpackage').asFile
                def name = cfg.appName ?: p.name
                // jpackage wants a numeric version (major >= 1 on macOS): drop any -SNAPSHOT or other qualifier
                def version = p.version.toString().replaceFirst(/[^0-9.].*$/, '').replaceAll(/\.$/, '') ?: '1.0'
                def dist = distDir.get().asFile
                p.delete(dist)
                dist.mkdirs()

                def cmd = [jpackageBin.absolutePath,
                           '--name', name,
                           '--app-version', version,
                           '--vendor', cfg.vendor,
                           '--input', stagingDir.get().asFile.absolutePath,
                           '--main-jar', jar.get().archiveFileName.get(),
                           '--main-class', p.extensions.getByName('application').mainClass.get(),
                           '--dest', dist.absolutePath,
                           '--add-modules', cfg.addModules.join(',')]
                if (cfg.description) cmd += ['--description', cfg.description]
                def os = System.getProperty('os.name').toLowerCase()
                def isWin = os.contains('win'), isMac = os.contains('mac')
                def icon = (isWin ? cfg.iconWindows : isMac ? cfg.iconMac : cfg.iconLinux) ?: cfg.icon
                if (icon) cmd += ['--icon', icon.absolutePath]
                if (isWin) {
                    cmd += ['--win-menu', '--win-shortcut', '--win-dir-chooser']
                    if (cfg.console) cmd += ['--win-console']
                }
                if (cfg.type) cmd += ['--type', cfg.type]
                cfg.jvmOptions.each { cmd += ['--java-options', it] }
                cmd += cfg.extraArgs
                commandLine cmd
                logger.lifecycle("jpackage: ${cmd.join(' ')}")
            }
            doLast {
                logger.lifecycle("jpackage: result in ${distDir.get().asFile}")
            }
        }
    }

    void apply(Project project) {

        project.extensions.create("openflexo", OpenFlexoExtension)

        // The dependency helpers (connie(), pamela(), ...) are called as methods of the project in the build scripts. The Convention API
        // that used to carry them is gone in Gradle 9: each helper is exposed as a closure in the extra properties, which Gradle
        // also resolves when a script calls it as a method.
        exposeHelpers(project, new OpenFlexoConvention(project))

        def containerProject = project.getTasks()

        def compileTask = containerProject.create('compile', {
            description = "The global compile task of ${project.name}."
            group = 'Build'
        })
        def testTask = containerProject.create('test', {
            description = "The global nou UI test task of ${project.name}."
            group = 'Verification'
        })
        def testAllTask = containerProject.create('testAll', {
            description = "The global UI test task of ${project.name}."
            group = 'Verification'
        })
        def cleanTask = containerProject.create('clean', {
            description = "The global clean task of ${project.name}."
            group = 'Cleaning'
        })
        def depTask = containerProject.create('dep', {
            description = "The global dep task of ${project.name}."
            group = 'Help'
        })
        def tasksAllTask = containerProject.create('tasksAll', {
            description = "The global tasks task of ${project.name}."
            group = 'Help'
        })

        def doc_version = project.version
        doc_version = doc_version - '-SNAPSHOT'

        String projRepo   = (new File(".").absolutePath) + './' + project.name + '_doc/'
        String docRepo   = projRepo + 'openflexo-documentation/'
        String docPath = docRepo + project.name + '/' + doc_version
        String branch = 'test'

        containerProject.register('buildWebsite', {
            description = "The task for building website."
            group = 'Documentation'
            doLast {
                // println '### Build Started for '+ project.name + ' ### \n'
                if (!new File(projRepo).exists()) {
                    project.exec {
                        commandLine 'mkdir', '-p', projRepo
                    }
                }
                if (!new File(docRepo).exists()) {
                    project.exec {
                        workingDir projRepo
                        commandLine 'git', 'clone', 'git@github.com:openflexo-team/openflexo-documentation.git', '--depth', 1, '--branch', branch//, '--quiet'
                    }
                }
                project.exec {
                    commandLine 'mkdir', '-p', docPath
                }
                project.exec {
                    workingDir docRepo
                    commandLine 'git', 'pull', 'origin', branch, '--no-rebase', '--quiet'
                }
                project.exec {
                    ignoreExitValue true
                    commandLine 'cp', '-r', 'src/site/markdown/.', docPath
                }

                project.subprojects { pr ->
                    project.exec {
                        ignoreExitValue true
                        commandLine 'cp', '-r', pr.name + '/src/site/markdown/.', docPath + '/' + pr.name
                    }
                }
                project.exec {
                    workingDir docRepo
                    commandLine 'git', 'add', docPath
                }
                project.exec {
                    ignoreExitValue true
                    workingDir docRepo
                    commandLine 'git', 'commit', '-m', '"gradle updating docs"'
                }
                project.exec {
                    workingDir docRepo
                    commandLine 'git', 'push', '--quiet'
                }
            }
        })

        project.allprojects {
            group='org.openflexo'
        }

        project.subprojects { pr ->
            compileTask.dependsOn("${pr.path}:compileJava")
            testTask.dependsOn("${pr.path}:test")
            testAllTask.dependsOn("${pr.path}:testAll")
            cleanTask.dependsOn("${pr.path}:clean")
            depTask.dependsOn("${pr.path}:dependencies")
            tasksAllTask.dependsOn("${pr.path}:tasks")
        }

        project.subprojects {
            apply plugin: 'java-library'
            apply plugin: 'maven-publish'
            apply plugin: 'com.jfrog.artifactory'

            // jpackage task for the applications
            def subproject = delegate as Project // 'project' is the root one inside this closure
            pluginManager.withPlugin('application') { OpenFlexoBuild.configureJPackage(subproject) }

            // Bytecode floor is Java 17. Compiles, tests and runs on a Java 17 toolchain by default,
            // whatever JDK launches Gradle; -PjavaVersion=21 (or 25) runs everything on that JDK instead.
            def toolchainVersion = project.hasProperty('javaVersion') ? Integer.parseInt(project.property('javaVersion').toString()) : 17
            java {
                toolchain {
                    languageVersion = JavaLanguageVersion.of(toolchainVersion)
                }
            }

            // Sets Java compile option to use UTF-8 encoding
            compileJava.options.encoding = 'UTF-8'
            tasks.withType(JavaCompile) {
                options.encoding = 'UTF-8'
                options.release = 17
           	}

            // Declares repositories to refer to
            repositories {
                maven {
                    url "https://maven.openflexo.org/artifactory/openflexo-deps/"
                    credentials {
                        username = "$System.env.ARTIFACTORY_USER" // The publisher user name
                        password = "$System.env.ARTIFACTORY_PASSWORD" // The publisher password
                    }
                }
            }

            // Tests configuration
            // Alls tests depends on testUtils (who provides junit 4)
            dependencies {
                //println("====> [${project.name}]")
                if (project.findProject(':testutils') == null) {
                    // Beware here the object project.openfelxo is not initialized
                    testImplementation "org.openflexo:testutils:${project.ext.utilsVersion}"
                } else
                    testImplementation project.project(':testutils')
            }

            def container = getTasks()

            def print_result = { desc, result ->
                if (!desc.parent && result.testCount > 0) { // will match the outermost suite
                    def output = "Results: ${result.resultType} (${result.testCount} tests, ${result.successfulTestCount} successes, ${result.failedTestCount} failures, ${result.skippedTestCount} skipped)"
                    def startItem = '|  ', endItem = '  |'
                    def repeatLength = startItem.length() + output.length() + endItem.length()
                    println('\n' + ('-' * repeatLength) + '\n' + startItem + output + endItem + '\n' + ('-' * repeatLength))
                }
            }

            // Gradle 9 does not give a Test task created by hand the classes and the classpath of the test source set any more
            // (it used to, through a deprecated convention): without them the task has no source and silently runs nothing.
            def testSourceSet = getExtensions().getByType(org.gradle.api.plugins.JavaPluginExtension).sourceSets.getByName('test')

            def uiTest = container.create('uiTest', Test, {
                description = "UI test task."
                group = 'Verification'
                testClassesDirs = testSourceSet.output.classesDirs
                classpath = testSourceSet.runtimeClasspath
                ignoreFailures = true
                maxParallelForks = 1;
                maxHeapSize = "3g"
                useJUnit {
                    includeCategories 'org.openflexo.test.UITest'
                }
                testLogging {
                    afterSuite print_result
                }
             })

            def test = container.getByName('test')
            test.configure {
                ignoreFailures = true
                jvmArgs += ["-Djava.awt.headless=true"]
                maxHeapSize = "3g"
                maxParallelForks = 4;
                useJUnit {
                    excludeCategories 'org.openflexo.test.UITest'
                }
                testLogging {
                    afterSuite print_result
                }
            }

            def testAll = container.create('testAll', {
                description = "Executing all test tasks."
                group = 'Verification'
            })
            testAll.dependsOn(uiTest)
            testAll.dependsOn(test)

            // Jacoco
            apply plugin: 'jacoco'
            apply plugin: 'project-report'

            htmlDependencyReport {
                projects = project.allprojects
            }


            if (JavaVersion.current().isJava8Compatible()) {
                allprojects {
                    tasks.withType(Javadoc) {
                        options.addBooleanOption('Xdoclint:none', true)
                    }
                }
            }

            configurations.all {
                // Check for updates every build
                resolutionStrategy.cacheChangingModulesFor 0, 'seconds'
            }

            publishing {
                publications {
                    mavenJava(MavenPublication) {
                        from components.java
                    }
                }
            }

            artifactory {
                contextUrl = 'https://maven.openflexo.org/artifactory'
                publish {
                    repository {
                    	def repo = (project.version.endsWith('-SNAPSHOT')) ? 'openflexo-snapshot' : 'openflexo-release'
                     	if (project.hasProperty('java10')) {
                     	  repo = 'openflexo-java10'
                     	}
	                	repoKey = repo// The Artifactory repository key to publish to
                        username = "$System.env.ARTIFACTORY_USER" // The publisher user name
                        password = "$System.env.ARTIFACTORY_PASSWORD" // The publisher password
                    }
                    defaults {
                        // Reference to Gradle publications defined in the build script.
                        // This is how we tell the Artifactory Plugin which artifacts should be
                        // published to Artifactory.
                        publications('mavenJava')
                        publishArtifacts = true
                        // Properties to be attached to the published arti facts.
                        //properties = ['qa.level': 'basic', 'dev.team' : 'core']
                        // Publish generated POM files to Artifactory (true by default)
                        publishPom = true
                    }
                }
             // Does not work anymore with gradle version 6.8.1
             //   resolve {
             //      repoKey = 'maven'
             //  }
            }
        }
    }
}

