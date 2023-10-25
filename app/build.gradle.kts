plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "moadgara.app"

    defaultConfig {
        applicationId = Configs.applicationId
    }

}
dependencies {
    implementation(Dependencies.splashScreen)
    implementation(Dependencies.Koin.android)
    implementation(Dependencies.timber)

    implementation(project(":base"))
    implementation(project(":uicomponent"))
    implementation(project(":main"))
}