package dev.lynith.multiversion

import dev.lynith.multiversion.tasks.ExportTask
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.DuplicatesStrategy
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.jvm.tasks.Jar
import org.gradle.kotlin.dsl.maven
import org.gradle.kotlin.dsl.project

class MultiBasePlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.plugins.apply {
            apply(JavaPlugin::class.java)
        }

        target.tasks.apply {
            val name = target.name.lowercase()

            if (withType(ExportTask::class.java).isEmpty()) {
                register("export-${name}", ExportTask::class.java) {
                    group = "client-${name}"

                    val buildTask = target.tasks.getByName("build")
                    dependsOn(buildTask)
                }
            }
        }

        target.configurations.apply {
            create("bundle")
            getByName("compileOnly").extendsFrom(getByName("bundle"))
        }

        target.repositories.apply {
            mavenCentral()
            maven("https://maven.fabricmc.net/")
        }

        target.dependencies.apply {
            if (target.project.name != "Core") {
                add("compileOnly", project(":Core"))
            }

            add("compileOnly", "org.projectlombok:lombok:1.18.30")
            add("annotationProcessor", "org.projectlombok:lombok:1.18.30")
        }

        val java = target.extensions.getByType(JavaPluginExtension::class.java)
        java.apply {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }

        val jar = target.tasks.withType(Jar::class.java)
        jar.configureEach {
            duplicatesStrategy = DuplicatesStrategy.EXCLUDE
            archiveFileName.set("${target.name}.jar")

            from (
                target.configurations.getByName("bundle").map {
                    if (it.isDirectory) it
                    else project.zipTree(it)
                }
            ) {
                exclude("META-INF/*.SF", "META-INF/*.DSA", "META-INF/*.RSA")
            }
        }

    }

}