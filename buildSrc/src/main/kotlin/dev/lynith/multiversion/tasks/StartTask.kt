package dev.lynith.multiversion.tasks

import org.gradle.api.DefaultTask
import dev.lynith.multiversion.MultiVersionExtension
import org.gradle.api.Task
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.TaskDependency

open class StartTask : DefaultTask() {

    @Input
    lateinit var mapping: String

    @Input
    lateinit var version: String

    @TaskAction
    fun start() {
        println("[MultiVersion] Starting $version/$mapping")
    }

    override fun getFinalizedBy(): TaskDependency {
        finalizedBy(project.tasks.named("run-${mapping}-${version}"))

        return super.getFinalizedBy()
    }

}