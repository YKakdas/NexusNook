plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

dependencies {
    api(Dependencies.core_ktx)
    api(Dependencies.appcompat)
    api(Dependencies.material)
    api(Dependencies.constrainlayout)

    testApi(Dependencies.junit)
    androidTestApi(Dependencies.ext_junit)
    androidTestApi(Dependencies.espresso)
}