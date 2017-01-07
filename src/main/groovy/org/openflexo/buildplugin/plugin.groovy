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
    String version = "1.4-SNAPSHOT";

    String connieVersion = "1.4-SNAPSHOT";
    String pamelaVersion = "1.4-SNAPSHOT";

    def String ginaVersion = "2.1-SNAPSHOT"
    def String dianaVersion = "1.4-SNAPSHOT"
}

/**
 * OpenFlexoConvention to declare dependencies
 */
class OpenFlexoConvention {

    Project project;

    OpenFlexoConvention(project) {
        this.project = project;
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

