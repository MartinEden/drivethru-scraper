plugins {
    kotlin("jvm") version "2.1.21"
    application
    id("org.jetbrains.kotlin.plugin.serialization") version("1.6.10")
}

@Suppress("PropertyName")
val ktor_version: String by project

group = "eden.drivethru"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

application {
    mainClass = "eden.drivethru.MainKt"
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0")
    implementation("io.ktor:ktor-client-core:$ktor_version")
    implementation("io.ktor:ktor-client-cio:$ktor_version")
    implementation("org.slf4j:slf4j-simple:2.0.16")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}

val removeOutput by tasks.registering(Delete::class) {
    delete(layout.projectDirectory.dir("output"))
}
tasks.findByName("clean")!!.dependsOn(removeOutput)

val copyResourcesToOutput by tasks.registering(Copy::class) {
    from(layout.projectDirectory.dir("src/main/resources"))
    into(layout.projectDirectory.dir("output"))
}
tasks.findByName("run")!!.dependsOn(copyResourcesToOutput)

val clearCacheData by tasks.registering(Delete::class) {
    val home = project.gradle.gradleUserHomeDir.parent
    val cache = File(home, ".drivethru/cache")
    val nonImageFiles = cache.listFiles().filter { "image" !in it.name }
    delete(nonImageFiles)
}