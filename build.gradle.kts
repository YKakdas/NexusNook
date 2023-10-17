import com.android.build.gradle.BaseExtension

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.1" apply false
    id("com.android.library") version "8.1.1" apply false
    kotlin("android") version "1.9.0" apply false
    kotlin("kapt") version "1.9.10" apply false
    kotlin("plugin.serialization") version "1.9.0" apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.1.2")
    }
}

allprojects {
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }
}

/*
    The following block reduces duplicate codes. Defines common properties that all Android modules
    need under their corresponding gradle file.
 */
subprojects {
    afterEvaluate {
        if (hasProperty("android")) {
            configure<BaseExtension> {
                compileSdkVersion(Configs.compileSdk)

                defaultConfig {
                    minSdk = Configs.minSdk
                    targetSdk = Configs.targetSdk
                    versionCode = Configs.versionCode
                    versionName = Configs.versionName

                    testInstrumentationRunner = Configs.testInstrumentationRunner
                }

                buildTypes {
                    getByName("release") {
                        isMinifyEnabled = false
                        proguardFiles(
                            getDefaultProguardFile("proguard-android-optimize.txt"),
                            "proguard-rules.pro"
                        )
                    }
                }

                compileOptions {
                    sourceCompatibility = Configs.sourceCompatibility
                    targetCompatibility = Configs.targetCompatibility
                }

                dataBinding.enable = true

                dependencies {
                    add("implementation", Dependencies.core_ktx)
                    add("implementation", Dependencies.fragment_ktx)
                    add("implementation", Dependencies.appcompat)
                    add("implementation", Dependencies.material)
                    add("implementation", Dependencies.constrainlayout)

                    add("implementation", Dependencies.Arch.viewModel)
                    add("implementation", Dependencies.Arch.liveData)

                    add("testImplementation", Dependencies.junit)

                    add("androidTestImplementation", Dependencies.ext_junit)
                    add("androidTestImplementation", Dependencies.espresso)

                    add("implementation", Dependencies.Koin.koinAndroid)

                    add("implementation", Dependencies.timber)
                }

                buildFeatures.buildConfig = true

                val gradleProperty = project.property("API_KEY")
                val API_KEY = if (gradleProperty is String) gradleProperty else ""
                buildTypes.forEach { it.buildConfigField("String", "API_KEY", API_KEY) }
            }

        }

    }

}