# Multi Version Minecraft Client
Multi version Minecraft client starter. 
Still got a lot left to do such as run configurations + a lot of code refactoring

## Roadmap
- [x] Multi version support (Tested 1.8.9 and 1.19.4)
- [x] Mixins support
- [x] Run Configurations
- [x] Code Refactoring (This includes the god damn gradle files)
- [ ] Test more versions

## How to add support for a new version
1. Copy a pre-existing version folder
    - `1.19.4` folder for versions 1.13 and onwards
    - `1.8.9` folder for versions 1.7 (Maybe lower?) to 1.12.2
2. Rename the folder to the version you want to add support for
3. Change the `version` variable in `build.gradle` to the version you want to add support for
4. Add the version to the `versions` array in `settings.gradle`
5. Make sure the mappings match up, you can always use your own mappings as long as loom supports it
6. The folder structure should look like this:
```
├── build.gradle
└── src/
    └── main/
        ├── java/
        │   └── <your package>/
        │       ├── <the version name in word form (e.g oneeightnine)>/
        │       │   ├── mixins/
        │       │   │   └── <Your mixins go here>
        │       │   └── <any interfaces in order to share the correct methods to the Core>
        │       └── start/
        │           └── VersionMain.java
        └── resources/
            └── client.mixins.json
```

## Running
1. Run the `start_<version>` run configuration

## Building and using in a production environment
1. Run the `merge_<version>` run configuration
2. The required file is in `build/<version>_merged.jar`
3. Add the jar as a javaagent when Minecraft is launching
    - `-javaagent:<path to jar>`

## Adding new methods for the core to use
1. Create a new interface in the version package
2. Add the methods you want to share with the core
3. Make sure the interface is implemented in the versions that support it
