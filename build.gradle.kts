plugins {
    kotlin("jvm") version "1.8.0"
    application
}

group = "com.codesai.katas"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.apache.commons:commons-lang3:3.12.0")

    testImplementation(kotlin("test"))

    testImplementation("io.kotest:kotest-runner-junit5:5.6.2")
    testImplementation("io.mockk:mockk:1.13.5")
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass.set("MainKt")
}
