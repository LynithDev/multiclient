package dev.lynith.multiversion

import org.gradle.api.JavaVersion
import kotlin.properties.Delegates

open class MultiVersionExtension {
    lateinit var minecraftVersion: String
    var fabricVersion: String? = null
    var forgeVersion: String? = null

    var legacy by Delegates.notNull<Boolean>()
    lateinit var javaVersion: JavaVersion
}