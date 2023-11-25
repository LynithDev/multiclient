plugins {
    id("java")
    id("dev.lynith.multiversion.root")
    kotlin("jvm") version "1.9.10"
}

group = "dev.lynith"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {

}

kotlin {
    jvmToolchain(8)
}