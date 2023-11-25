package dev.lynith.multiversion

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin

class MultiRootPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.plugins.apply(JavaPlugin::class.java)

        target.tasks.register("export-all") {
            group = "client"
        }
    }
}