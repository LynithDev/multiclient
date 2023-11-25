import dev.lynith.multiversion.MultiVersionExtension

plugins {
    id("dev.lynith.multiversion.version")
}

extensions.findByType(MultiVersionExtension::class.java)?.apply {
    minecraftVersion = "1.20.2"
    legacy = false
    javaVersion = JavaVersion.VERSION_17
}