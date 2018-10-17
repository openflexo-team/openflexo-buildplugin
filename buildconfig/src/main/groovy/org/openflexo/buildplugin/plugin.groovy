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

		//println project.version
		def extension = '-SNAPSHOT'
		if (project.hasProperty('versionSuffix')) {
			extension = project.getProperty('versionSuffix')
		} else if (project.hasProperty('isRelease')) {
			if (project.getProperty('isRelease') == 'true' ||
				project.getProperty('isRelease') == 'yes' ||
				project.getProperty('isRelease') == 'oui' ||
				project.getProperty('isRelease') == 'vrai' ) {
				extension = ''
			}
		}
		project.version = project.version + extension
		project.subprojects.each { Project p ->	p.version = project.version }

		//println project.version

        project.openflexo.utilsVersion = "1.4.1" + extension
        project.openflexo.connieVersion = "1.4.1" + extension
        project.openflexo.pamelaVersion = "1.4.1" + extension

        project.openflexo.ginaVersion = "2.1.1" + extension
        project.openflexo.dianaVersion = "1.4.1" + extension

        project.openflexo.openflexoVersion = "1.9.0" + extension
    }
}

