package dev.lynith.multiversion

import org.gradle.api.JavaVersion

open class MultiVersionExtension {
    var minecraftVersion: String = "1.8.9"
    var fabricVersion: String? = null
    var forgeVersion: String? = null

    var legacy: Boolean = true
    var javaVersion: JavaVersion = JavaVersion.VERSION_1_8
}