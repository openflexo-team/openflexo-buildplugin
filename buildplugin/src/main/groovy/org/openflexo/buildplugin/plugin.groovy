package org.openflexo.buildplugin

import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.api.tasks.javadoc.Javadoc
import org.gradle.api.publish.maven.MavenPublication

/**
 * OpenFlexo build plugin configuration
 */
class OpenFlexoExtension {
    String utilsVersion = ""
    String connieVersion = ""
    String pamelaVersion = ""
    String ginaVersion = ""
    String dianaVersion = ""
    String openflexoVersion = ""
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

    String connieUtils() {
        return "org.openflexo:connie-utils:${project.openflexo.connieVersion}"
    }

    String flexoUtils() {
        return "org.openflexo:flexo-utils:${project.openflexo.utilsVersion}"
    }

    String flexoP2PP() {
        return "org.openflexo:flexo-p2pp:${project.openflexo.utilsVersion}"
    }

    String testUtils() {
        return "org.openflexo:testutils:${project.openflexo.connieVersion}"
    }

    String connie() {
        return "org.openflexo:connie-core:${project.openflexo.connieVersion}"
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

    String fmlCli() {
        return "org.openflexo:fml-cli:${project.openflexo.openflexoVersion}"
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
        return "org.openflexo:docxconnector:${project.openflexo.openflexoVersion}"
    }

    String emfConnector() {
        return "org.openflexo:emf-ta:${project.openflexo.openflexoVersion}"
    }

    String excelConnector() {
        return "org.openflexo:excelconnector:${project.openflexo.openflexoVersion}"
    }

    String diagramConnector() {
        return "org.openflexo:diagram-ta:${project.openflexo.openflexoVersion}"
    }

    String ginaConnector() {
        return "org.openflexo:ginaconnector:${project.openflexo.openflexoVersion}"
    }

    String jdbcConnector() {
        return "org.openflexo:jdbcconnector:${project.openflexo.openflexoVersion}"
    }
    String jdbcConnectorTest() {
        return "org.openflexo:jdbcconnector-test:${project.openflexo.openflexoVersion}"
    }

    String odtConnector() {
        return "org.openflexo:odtconnector:${project.openflexo.openflexoVersion}"
    }

    String oslcConnector() {
        return "org.openflexo:oslcconnector:${project.openflexo.openflexoVersion}"
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
        return "org.openflexo:powerpointconnector:${project.openflexo.openflexoVersion}"
    }

    String httpConnector() {
        return "org.openflexo:http-connector:${project.openflexo.openflexoVersion}"
    }

    String kafkaConnector() {
        return "org.openflexo:kafkaconnector:${project.openflexo.openflexoVersion}"
    }

    String httpConnectorUi() {
        return "org.openflexo:http-connector-ui:${project.openflexo.openflexoVersion}"
    }

    String httpConnectorRc() {
        return "org.openflexo:http-connector-rc:${project.openflexo.openflexoVersion}"
    }

    String kafkaConnectorUi() {
        return "org.openflexo:kafkaconnector-ui:${project.openflexo.openflexoVersion}"
    }

    String xmlConnector() {
        return "org.openflexo:xmlconnector:${project.openflexo.openflexoVersion}"
    }

    String docxConnectorUi() {
        return "org.openflexo:docxconnector-ui:${project.openflexo.openflexoVersion}"
    }

    String emfConnectorUi() {
        return "org.openflexo:emf-ta-ui:${project.openflexo.openflexoVersion}"
    }

    String excelConnectorUi() {
        return "org.openflexo:excelconnector-ui:${project.openflexo.openflexoVersion}"
    }

    String diagramConnectorUi() {
        return "org.openflexo:diagram-ta-ui:${project.openflexo.openflexoVersion}"
    }

    String ginaConnectorUi() {
        return "org.openflexo:ginaconnector-ui:${project.openflexo.openflexoVersion}"
    }

    String jdbcConnectorUi() {
        return "org.openflexo:jdbcconnector-ui:${project.openflexo.openflexoVersion}"
    }

    String odtConnectorUi() {
        return "org.openflexo:odtconnector-ui:${project.openflexo.openflexoVersion}"
    }

    String oslcConnectorUi() {
        return "org.openflexo:oslcconnector-ui:${project.openflexo.openflexoVersion}"
    }

    String owlConnectorUi() {
        return "org.openflexo:owl-ta-ui:${project.openflexo.openflexoVersion}"
    }

    String pdfConnectorUi() {
        return "org.openflexo:pdf-ta-ui:${project.openflexo.openflexoVersion}"
    }

    String powerpointConnectorUi() {
        return "org.openflexo:powerpointconnector-ui:${project.openflexo.openflexoVersion}"
    }

    String xmlConnectorUi() {
        return "org.openflexo:xmlconnector-ui:${project.openflexo.openflexoVersion}"
    }

    String openflexo(String name) {
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


}

/**
 * Gradle build plugin for OpenFlexo projects
 *
 * Created by charlie on 06/01/2017.
 */
class OpenFlexoBuild implements Plugin<Project> {

    void apply(Project project) {

        project.extensions.create("openflexo", OpenFlexoExtension)

        project.convention.plugins.put("openflexo", new OpenFlexoConvention(project))

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

            // Sets Java compile option to use UTF-8 encoding
            compileJava.options.encoding = 'UTF-8'
            tasks.withType(JavaCompile) {
                options.encoding = 'UTF-8'
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
            // Alls tests depends on junit 4 and testUtils
            dependencies {
                //println("====> [${project.name}]")
                if (project.name != 'connie') {
                    testImplementation "org.openflexo:testutils:${project.ext.connieVersion}"
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

            def uiTest = container.create('uiTest', Test, {
                description = "UI test task."
                group = 'Verification'
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
                resolve {
                    repoKey = 'maven'
                }
            }
        }
    }
}

