# multiclient
Java + Kotlin multi Minecraft version Java Agent client

## Old
https://github.com/LynithDev/multiclient/tree/old

## How to use

### Creating a new version
1. Create a new module in the `versions` folder
2. Call it whatever you want
3. Create a `build.gradle.kts` file
4. Apply the `dev.lynith.multiclient.version` plugin
5. Optionally configure the plugin:
```kotlin
multiversion {
    minecraftVersion = "1.8.9"
    legacy = false // If the Minecraft version is a legacy version (1.12.2 and below)
    javaVersion = JavaVersion.VERSION_1_8
    
    fabricVersion = "0.11.6+build.200" // Optional
    forgeVersion = "36.1.0" // Optional
}
```

### Implementing version dependent code
1. Create a new interface in `Core` and the `bridge` folder
2. Create a new class in each Version and implement the interface
3. Implement your version dependent code and call it from the Core