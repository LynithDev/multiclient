package dev.lynith.multiversion.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Copy
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.support.unzipTo
import org.gradle.kotlin.dsl.support.zipTo
import java.io.File

open class MergeTask : DefaultTask() {

    private val versionFile = project.file("${project.rootDir}/build/Versions/${project.name}/${project.name}.jar")
    private val coreFile = project.file("${project.rootDir}/build/Core.jar")
    private val agentFile = project.file("${project.rootDir}/build/JavaAgent.jar")

    private val mergedDir = project.file("${project.rootDir}/build/Versions/${project.name}/merged")
    private val mergedFile = project.file("${project.rootDir}/build/Versions/${project.name}/${project.name}-merged.jar")

    @TaskAction
    fun merge() {
        clean()

        var count = 0
        count = check(versionFile, count)
        count = check(coreFile, count)
        count = check(agentFile, count)

        if (count > 0) {
            println("[MultiVersion] Missing $count files, skipping merge")
            return
        }

        unzipTo(mergedDir, project.file(versionFile))
        unzipTo(mergedDir, project.file(coreFile))
        unzipTo(mergedDir, project.file(agentFile))

        zipTo(project.file(mergedFile), mergedDir)

        clean()
    }

    private fun check(file: File, count: Int): Int {
        return if (file.exists()) {
            println("[MultiVersion] ${file.absolutePath.substringAfter(project.rootDir.absolutePath)} exists")
            count
        } else {
            println("[MultiVersion] ${file.absolutePath.substringAfter(project.rootDir.absolutePath)} does not exist")
            count + 1
        }
    }

    private fun clean() {
        mergedDir.deleteRecursively()
    }

}