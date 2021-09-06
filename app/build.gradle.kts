plugins {
    id("org.jetbrains.kotlin.jvm") version "1.5.30"
    application
}

repositories {
    mavenCentral()
}

dependencies {
    kotlin("stdlib-jdk8")
    kotlin("test")
    kotlin("test-junit")

    implementation("com.fasterxml.jackson:jackson-bom:2.12.5")
    implementation("com.fasterxml.jackson.core:jackson-databind")
}

application {
    mainClass.set("net.dinomite.jackson.test.AppKt")
}
