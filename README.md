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
    classpath group: 'org.openflexo', name: 'buildplugin', version: '0.+'
    classpath group: 'org.openflexo', name: 'buildconfig', version: '1.9.0-SNAPSHOT'
  }
}

apply plugin: 'org.openflexo.buildconfig'

openflexo.version = "1.4-SNAPSHOT"

dependencies {
  compile flexoUtils()
  compile connie()
  compile pamela()

  testCompile testUtils()
}
```
