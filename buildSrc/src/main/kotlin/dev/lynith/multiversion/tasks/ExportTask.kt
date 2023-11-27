package dev.lynith.multiversion.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.Task
import org.gradle.api.file.FileCollection
import org.gradle.api.tasks.Copy
import org.gradle.api.tasks.TaskAction
import java.io.File

open class ExportTask : DefaultTask() {

    private var inFile: String = "${project.projectDir}/build/libs/${project.name}.jar"
    private var inFileAll: String = "${project.projectDir}/build/libs/${project.name}-all.jar"

    @TaskAction
    fun export() {
        if (!project.file(inFile).exists() && !project.file(inFileAll).exists()) {
            println("[MultiVersion] '${inFile.substringAfter(project.rootDir.absolutePath)}' does not exist. Cancelling export")
            return
        }

        if (project.file(inFileAll).exists()) {
            inFile = inFileAll
        }

        var outFilePath = "${project.rootDir}/build/"

        if (project.parent != null && project.parent!!.name == "Versions") {
            outFilePath += "Versions/${project.name}/"
        }

        outFilePath += "${project.name}.jar"

        println("[MultiVersion] Copying '${inFile.substringAfter(project.rootDir.absolutePath)}' to '${outFilePath.substringAfter(project.rootDir.absolutePath)}'")

        val outFile = project.file(outFilePath)
        project.file(inFile).copyTo(outFile, overwrite = true)
    }

}