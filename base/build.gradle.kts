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
    implementation(Dependencies.Coil.core)
    implementation(Dependencies.Coil.gif)

    api(Dependencies.Glide.glide)
    api(Dependencies.Glide.recyclerViewIntegration) { isTransitive = false }
    kapt(Dependencies.Glide.compiler)

    api(Dependencies.kotlinxSerialization)

    implementation(project(":common-model"))
}