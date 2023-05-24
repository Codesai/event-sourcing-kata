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
    testImplementation(kotlin("test"))

    testImplementation("io.kotest:kotest-runner-junit5:5.6.2")
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass.set("MainKt")
}
