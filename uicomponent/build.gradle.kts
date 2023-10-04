plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "moadgara.uicomponent"
}

dependencies {
    implementation(project(":base"))

    testImplementation(Dependencies.junit)
    androidTestImplementation(Dependencies.ext_junit)
    androidTestImplementation(Dependencies.espresso)
}