import com.android.build.gradle.BaseExtension

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("com.android.library") version "8.1.1" apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.1.1")
        // fill the rest
    }
}

allprojects {
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }
}

subprojects {
    afterEvaluate {
        if (hasProperty("android")) {
            configure<BaseExtension> {
                namespace = Configs.namespace
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

            }

        }
    }

}