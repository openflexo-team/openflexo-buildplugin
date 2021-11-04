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

        // Beware connie version is used by the plugin to add the dependency to testutils
        // In a future version, we could put all versions in ext and use them in the plugin
        project.ext.connieVersion = "2.1.0" + extension
        project.apply plugin: 'org.openflexo.buildplugin'

        project.openflexo.utilsVersion = "1.7" + extension
        project.openflexo.connieVersion = project.ext.connieVersion
        project.openflexo.pamelaVersion = "1.6.1" + extension

        project.openflexo.ginaVersion = "2.4" + extension
        project.openflexo.dianaVersion = "1.7" + extension

        project.openflexo.openflexoVersion = "2.1.1" + extension
    }
}

