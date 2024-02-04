plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
    namespace = "moadgara.main"
}

dependencies {
    implementation(project(":base"))
    implementation(project(":common-model"))
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(project(":uicomponent"))

    implementation(Dependencies.Media3.exoplayer)
    implementation(Dependencies.Media3.ui)
    implementation(Dependencies.Paging.pagingAndroid)
    implementation(Dependencies.palette)
}
