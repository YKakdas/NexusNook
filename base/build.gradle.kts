plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "moadgara.base"
}

// api function was used since all Android related modules need the following dependencies. Those
// are not needed to be redefined again under the modules that include :base.
dependencies {
    api(Dependencies.core_ktx)
    api(Dependencies.appcompat)
    api(Dependencies.material)
    api(Dependencies.constrainlayout)

    testApi(Dependencies.junit)
    androidTestApi(Dependencies.ext_junit)
    androidTestApi(Dependencies.espresso)
}