plugins {
    id("com.android.library")
    id("kotlin-kapt")
    kotlin("android")
}

android {
    namespace = "moadgara.base"
}

dependencies {
    implementation(Dependencies.Ktor.clientCore)
    implementation(Dependencies.Ktor.clientAndroid)
    implementation(Dependencies.Ktor.clientSerialization)
    implementation(Dependencies.Ktor.contentNegotiation)
    implementation(Dependencies.Ktor.kotlinxSerializationJson)
    implementation(Dependencies.Ktor.clientMock)

    api(Dependencies.Coil.core)
    implementation(Dependencies.Coil.gif)

    api(Dependencies.kotlinxSerialization)
    implementation(Dependencies.gson)

    implementation(project(":common-model"))
}