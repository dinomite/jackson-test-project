plugins {
    id("org.jetbrains.kotlin.jvm") version "1.5.30"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))

    implementation("com.fasterxml.jackson.core:jackson-databind:2.12.5")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.12.5")

    testImplementation(kotlin("test-junit5"))
}

tasks.test { useJUnitPlatform() }
