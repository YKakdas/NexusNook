plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.serialization")
}

android {
    namespace = "moadgara.uicomponent"
}

dependencies {
    implementation(Dependencies.Coil.core)
    implementation(Dependencies.Coil.gif)

    implementation(project(":base"))
}