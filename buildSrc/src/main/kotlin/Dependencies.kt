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
    const val cardView = "androidx.cardview:cardview:${Versions.cardView}"
    const val kotlinxSerialization =
        "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.kotlinxSerialization}"
    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
    const val kotlinCoroutine =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinCoroutine}"

    object Arch {
        private const val archLifecycle = "androidx.lifecycle:lifecycle-"

        const val viewModel = "${archLifecycle}viewmodel-ktx:${Versions.lifeCycle}"
        const val liveData = "${archLifecycle}livedata-ktx:${Versions.lifeCycle}"
    }

    object Ktor {
        const val clientCore = "io.ktor:ktor-client-core:${Versions.ktor}"
        const val clientAndroid = "io.ktor:ktor-client-android:${Versions.ktor}"
        const val clientSerialization = "io.ktor:ktor-client-serialization:${Versions.ktor}"
        const val contentNegotiation = "io.ktor:ktor-client-content-negotiation:${Versions.ktor}"
        const val kotlinxSerializationJson =
            "io.ktor:ktor-serialization-kotlinx-json:${Versions.ktor}"
        const val clientMock = "io.ktor:ktor-client-mock:${Versions.ktor}"
    }

    object Koin {
        const val core = "io.insert-koin:koin-core:${Versions.koin}"
        const val android = "io.insert-koin:koin-android:${Versions.koin}"
    }

    object Coil {
        const val core = "io.coil-kt:coil:${Versions.coil}"
        const val gif = "io.coil-kt:coil-gif:${Versions.coil}"
    }

    object Media3 {
        const val exoplayer = "androidx.media3:media3-exoplayer:${Versions.media3}"
        const val ui = "androidx.media3:media3-ui:${Versions.media3}"
    }

}