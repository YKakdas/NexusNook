object Dependencies {
    const val splashScreen = "androidx.core:core-splashscreen:${Versions.splashScreen}"
    const val core_ktx = "androidx.core:core-ktx:${Versions.core_ktx}"
    const val fragment_ktx = "androidx.fragment:fragment-ktx:${Versions.fragment_ktx}"
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val constrainlayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintlayout}"
    const val junit = "junit:junit:${Versions.junit}"
    const val ext_junit = "androidx.test.ext:junit:${Versions.ext_junit}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"

    object Arch {
        private const val archLifecycle = "androidx.lifecycle:lifecycle-"

        const val viewModel = "${archLifecycle}viewmodel-ktx:${Versions.lifeCycle}"
        const val liveData = "${archLifecycle}livedata-ktx:${Versions.lifeCycle}"
    }
}