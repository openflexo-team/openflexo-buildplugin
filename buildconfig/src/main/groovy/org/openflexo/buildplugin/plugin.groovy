package org.openflexo.buildplugin

import org.gradle.api.Plugin
import org.gradle.api.Project
/**
 * Gradle build config plugin for OpenFlexo projects
 *
 * Created by charlie on 24/01/2017.
 */
class OpenFlexoBuildConfig implements Plugin<Project> {

    void apply(Project project) {
        project.apply plugin: 'org.openflexo.buildplugin'

        project.openflexo.utilsVersion = "1.4-SNAPSHOT";
        project.openflexo.connieVersion = "1.4-SNAPSHOT";
        project.openflexo.pamelaVersion = "1.4-SNAPSHOT";

        project.openflexo.ginaVersion = "2.1-SNAPSHOT"
        project.openflexo.dianaVersion = "1.4-SNAPSHOT"

        project.openflexo.openflexoVersion = "1.8.1-SNAPSHOT"
    }
}

