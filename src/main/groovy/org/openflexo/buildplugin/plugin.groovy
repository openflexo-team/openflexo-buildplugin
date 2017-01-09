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
    String version = "1.9.0-SNAPSHOT";

    String utilsVersion = "1.4-SNAPSHOT";
    String connieVersion = "1.4-SNAPSHOT";
    String pamelaVersion = "1.4-SNAPSHOT";

    String ginaVersion = "2.1-SNAPSHOT"
    String dianaVersion = "1.4-SNAPSHOT"
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

            // Sets SNAPSHOT publication to OpenFlexo artifactory
            uploadArchives {
                repositories {
                    mavenDeployer {
                        repository(url: "https://maven.openflexo.org/artifactory/openflexo-snapshot/")
                    }
                }
            }

            // Declares repositories to refer to
            repositories {
                jcenter()
                maven { url "https://maven.openflexo.org/artifactory/openflexo-snapshot/" }
                maven { url "https://maven.openflexo.org/artifactory/openflexo-release/" }
                maven { url "https://maven.openflexo.org/artifactory/openflexo-deps/" }
                maven { url "https://maven.openflexo.org/artifactory/openflexo-local-deps/" }
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

