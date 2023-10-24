plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "moadgara.main"
}
dependencies {
    implementation(project(":uicomponent"))
}
