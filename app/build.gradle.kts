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
    implementation(project(":uicomponent"))
}