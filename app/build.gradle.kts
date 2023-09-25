plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    defaultConfig {
        applicationId = Configs.applicationId
    }

}
dependencies {
    implementation(Dependencies.splashScreen)
    testImplementation(Dependencies.junit)
    androidTestImplementation(Dependencies.ext_junit)
    androidTestImplementation(Dependencies.espresso)

    implementation(project(":uicomponent"))
}