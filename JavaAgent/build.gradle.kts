plugins {
    id("dev.lynith.multiversion.base")
}

group = "dev.lynith"
version = "1.0.0"

dependencies {
    compileOnly(libs.asm)
    compileOnly(libs.guava)
    compileOnly(libs.gson)
    compileOnly(libs.commons.io)

    bundle(libs.mixin) {
        exclude(module = "launchwrapper")
        exclude(module = "guava")
        exclude(module = "gson")
        exclude(module = "commons-io")
    }
}

tasks.withType(Jar::class.java) {
    manifest {
        attributes(
            "Agent-Class" to "${rootProject.group}.javaagent.AgentMain",
            "Premain-Class" to "${rootProject.group}.javaagent.AgentMain",
            "Can-Redefine-Classes" to "true",
            "Can-Retransform-Classes" to "true"
        )
    }
}