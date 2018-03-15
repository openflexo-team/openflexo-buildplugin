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
    classpath group: 'org.openflexo', name: 'buildplugin', version: '+'
    classpath group: 'org.openflexo', name: 'buildconfig', version: '2.0.0-SNAPSHOT'
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

It's composed of two plugins, one for the build, the other for the configuration.
The `buildplugin` defines how the project needs to be built (using `java` and `maven` plugins for instance).
The `buildconfig` defines for a given version (`1.8.SNAPSHOT` in this example) all the corresponding versions of all OpenFlexo components (`connie:1.4-SNAPSHOT`, `pamela:1.4-SNAPSHOT`, `gina:2.1-SNAPSHOT`, ...).


The property `openflexo.version` defines the current project version used by the `buildplugin`
You can also change the version for each component like so:
- `openflexo.connieVersion = 1.3`,
- `openflexo.dianaVersion = 2.0` ...

