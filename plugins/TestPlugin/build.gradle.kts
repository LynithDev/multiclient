plugins {
    id("java")
}

group = "dev.lynith"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly(project(":Core"))
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}