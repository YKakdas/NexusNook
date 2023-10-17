plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
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

    api(Dependencies.kotlinxSerialization)
}