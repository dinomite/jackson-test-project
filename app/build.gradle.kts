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
}

application {
    mainClass.set("net.dinomite.jackson.test.AppKt")
}
