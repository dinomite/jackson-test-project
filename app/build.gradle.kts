plugins {
    id("org.jetbrains.kotlin.jvm") version "1.5.30"
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("test"))

    implementation(platform("com.fasterxml.jackson:jackson-bom:2.12.5"))
    implementation("com.fasterxml.jackson.core:jackson-databind")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    testImplementation(kotlin("test-junit"))
}

application {
    mainClass.set("net.dinomite.jackson.test.AppKt")
}
