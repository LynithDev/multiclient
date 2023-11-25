package dev.lynith.multiversion.tasks

import org.gradle.api.DefaultTask
import dev.lynith.multiversion.MultiVersionExtension

open class StartTask : DefaultTask() {

    private val extension = project.extensions.getByType(MultiVersionExtension::class.java)



}