plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
    kotlin("plugin.serialization")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(Dependencies.kotlinxSerialization)
    implementation(Dependencies.kotlinCoroutine)

    implementation(project(":common-model"))
}