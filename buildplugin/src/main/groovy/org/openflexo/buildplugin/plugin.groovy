package org.openflexo.buildplugin

import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.api.tasks.javadoc.Javadoc

/**
 * OpenFlexo build plugin configuration
 */
class OpenFlexoExtension {
    String version = "";

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

    String flexoUtils() {
        return "org.openflexo:flexoutils:${project.openflexo.connieVersion}"
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

    String fmlTechnologyadapterUi() {
        return "org.openflexo:fml-technologyadapter-ui:${project.openflexo.openflexoVersion}"
    }

    String fmlRtTechnologyadapterUi() {
        return "org.openflexo:fml-rt-technologyadapter-ui:${project.openflexo.openflexoVersion}"
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

        project.subprojects {
            apply plugin: 'maven'
            apply plugin: 'java'

            //  apply plugin: 'checkstyle'
            //  apply plugin: 'pmd'
            //  apply plugin: 'findbugs'
            //  apply plugin: 'Site'

            // Sets Java compile option to use UTF-8 encoding
            compileJava.options.encoding = 'UTF-8'
            tasks.withType(JavaCompile) {
                options.encoding = 'UTF-8'
            }

            // Declares repositories to refer to
            repositories {
                maven { url "https://maven.openflexo.org/artifactory/openflexo-deps/" }
            }

            // Alls tests depends on junit 4
            dependencies {
                testCompile group: 'junit', name: 'junit', version: '4.+'
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

            test {
                maxParallelForks = 1;
            }

            // Sets SNAPSHOT publication to OpenFlexo artifactory
            uploadArchives {
                repositories {
                    mavenDeployer {
                        repository(url: "https://maven.openflexo.org/artifactory/openflexo-snapshot/")
                    }
                }
            }
        }

        project.allprojects {
            group='org.openflexo'
            version = project.openflexo.version

            apply plugin: 'project-report'

            htmlDependencyReport {
                projects = project.allprojects
            }

        }

    }
}

