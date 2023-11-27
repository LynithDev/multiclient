package dev.lynith.multiversion

import net.fabricmc.loom.api.LoomGradleExtensionAPI
import net.fabricmc.loom.bootstrap.LoomGradlePluginBootstrap
import net.fabricmc.loom.task.RemapJarTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.JavaExec
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.jvm.toolchain.JavaToolchainService
import org.gradle.kotlin.dsl.maven
import dev.lynith.multiversion.tasks.ExportTask
import dev.lynith.multiversion.tasks.MergeTask
import dev.lynith.multiversion.tasks.StartTask

class MultiVersionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.plugins.apply(JavaPlugin::class.java)

        val extension = target.project.extensions.create("multiversion", MultiVersionExtension::class.java)

        target.afterEvaluate {
            val toolchainService = target.extensions.getByType(JavaToolchainService::class.java)
            val java = target.extensions.getByType(JavaPluginExtension::class.java)

            java.apply {
                sourceCompatibility = extension.javaVersion
                targetCompatibility = extension.javaVersion
            }

            val buildTargets: MutableMap<String, String> = mutableMapOf(
                "vanilla" to extension.minecraftVersion
            )

            if (extension.fabricVersion != null) {
                buildTargets["fabric"] = extension.fabricVersion!!
            }

            if (extension.forgeVersion != null) {
                buildTargets["forge"] = extension.forgeVersion!!
            }

            target.tasks.apply {
                // Set the compiler target java version
                val compiler = toolchainService.compilerFor { languageVersion.set(JavaLanguageVersion.of(extension.javaVersion.majorVersion)) }
                withType(JavaCompile::class.java).configureEach {
                    javaCompiler.set(compiler.get())
                }

                // Set the java executable to the target java version
                val launcher = toolchainService.launcherFor { languageVersion.set(JavaLanguageVersion.of(extension.javaVersion.majorVersion)) }
                withType(JavaExec::class.java).configureEach {
                    setExecutable(launcher.get().executablePath)
                }

                register("export-${target.name}", ExportTask::class.java) {
                    group = "client-${target.name}"
                    description = "Export the ${target.name} client"

                    val buildTask = target.tasks.getByName("build")
                    dependsOn(buildTask)
                }

                register("merge-${target.name}", MergeTask::class.java) {
                    group = "client-${target.name}"
                    description = "Merge the ${target.name} client"

                    val versionExport = target.tasks.getByName("export-${target.name}")
                    val agentExport = project(":JavaAgent").tasks.getByName("export-javaagent")
                    val coreExport = project(":Core").tasks.getByName("export-core")

                    dependsOn(agentExport, coreExport, versionExport)
                }

                buildTargets.forEach { entry ->
                    register("start-${entry.key}-${entry.value}", StartTask::class.java) {
                        group = "client-${target.name}"
                        description = "Start the ${entry.key} ${entry.value} client"

                        mapping = entry.key
                        version = entry.value

                        dependsOn("merge-${target.name}")
                    }
                }

            }

            target.plugins.apply {
                apply(MultiBasePlugin::class.java)
                apply(LoomGradlePluginBootstrap::class.java)
            }

            target.repositories.apply {
                if (extension.legacy) {
                    maven("https://repo.legacyfabric.net/repository/legacyfabric/")
                }
            }

            target.dependencies.apply {
                add("compileOnly", "net.fabricmc:sponge-mixin:0.12.5+mixin.0.8.5")

                add("minecraft", "com.mojang:minecraft:${extension.minecraftVersion}")
                add("mappings",
                    if (extension.legacy) "net.legacyfabric:yarn:${extension.minecraftVersion}+build.+"
                    else "net.fabricmc:yarn:${extension.minecraftVersion}+build.+"
                )
            }

            val loom = target.extensions.findByName("loom") as LoomGradleExtensionAPI
            val remapJar = target.tasks.getByName("remapJar") as RemapJarTask

            remapJar.apply {
                sourceNamespace.set("named")
                targetNamespace.set("intermediary")
            }

            loom.apply {
                mixin.useLegacyMixinAp.set(false)

                val buildMappings = mutableMapOf(
                    "vanilla" to "official"
                )

                if (extension.fabricVersion != null) {
                    buildMappings["fabric"] = "intermediary"
                }

                buildMappings.forEach { target ->
                    if (target.key == "fabric" && extension.fabricVersion == null) {
                        return@forEach
                    }

                    runConfigs.register("-${target.key}-${extension.minecraftVersion}") {
                        runDir("run")

                        val jarName = "${extension.minecraftVersion}-merged.jar" // For now, we'll do this. once the remap task is done, we'll adjust this
                        vmArgs("-javaagent:${rootDir}/build/Versions/${extension.minecraftVersion}/${jarName}")

                        environment = "client"

                        if (target.key == "fabric") {
                            mainClass.set("net.fabricmc.loader.launch.knot.KnotClient")
                        } else {
                            mainClass.set("net.minecraft.client.main.Main")
                            programArg("--version=${extension.minecraftVersion}")
                            programArg("--accessToken=0")
                        }

                        ideConfigGenerated(true)
                    }
                }
            }

        }
    }

}