plugins {
    id("com.android.library")
    id("kotlin-kapt")
    kotlin("android")
    kotlin("plugin.serialization")
}

android {
    namespace = "moadgara.uicomponent"
}

dependencies {
    implementation(Dependencies.Coil.core)
    implementation(Dependencies.Coil.gif)
    implementation(Dependencies.Paging.pagingAndroid)

    implementation(project(":base"))
}