openflexo-buildplugin
=====================

This project defines a Gradle plugin for OpenFlexo projects. 
It declares all needed plugins and adds methods to simplifies dependencies declaration.
 
 
Here an example to use the plugin: 
```gradle
buildscript {
  repositories {
  
    maven { url uri('https://maven.openflexo.org/artifactory/openflexo-release/') }
  }
  dependencies {
    classpath group: 'org.openflexo', name: 'openflexo-buildplugin', version: '0.+'
  }
}

apply plugin: 'org.openflexo.buildplugin'
openflexo.version = "1.4-SNAPSHOT"

dependencies {
  compile flexoUtils()
  compile connie()
  compile pamela()

  testCompile testUtils()
}
```
