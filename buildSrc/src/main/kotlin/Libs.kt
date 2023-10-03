sealed class Libs {
    object Accompanist {

        const val insets =
            "com.google.accompanist:accompanist-insets:${Versions.accompanistVersion}"
    }

    object Kotlin {
        object Coroutines {

            const val android =
                "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutinesVersion}"
            const val test =
                "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutinesVersion}"
        }
    }

    object AndroidX {
        object Activity {

            const val activityCompose =
                "androidx.activity:activity-compose:${Versions.activityComposeVersion}"
        }

        const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompatVersion}"

        object Compose {

            const val lifecycle =
                "androidx.lifecycle:lifecycle-runtime-compose:${Versions.lifecycleVersion}"
            const val material3 =
                "androidx.compose.material3:material3:${Versions.composeMaterial3Version}"
            const val toolingPreview =
                "androidx.compose.ui:ui-tooling-preview:${Versions.composeToolingVersion}"
            const val tooling = "androidx.compose.ui:ui-tooling:${Versions.composeToolingVersion}"
            const val ui = "androidx.compose.ui:ui:${Versions.composeUIVersion}"
            const val navigation =
                "androidx.navigation:navigation-compose:${Versions.composeNavigationVersion}"
        }

        object Lifecycle {

            const val viewModelCompose =
                "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.composeViewModelVersion}"
        }

        object Test {

            const val runner = "androidx.test:runner:${Versions.testVersion}"
            const val rules = "androidx.test:rules:${Versions.testVersion}"
            const val espressoCore =
                "androidx.test.espresso:espresso-core:${Versions.espressoCoreVersion}"

            object Ext {

                const val junit = "androidx.test.ext:junit-ktx:${Versions.jUnitKtxVersion}"
            }
        }
    }

    object Hilt {

        const val android = "com.google.dagger:hilt-android:${Versions.hiltVersion}"
        const val androidCompiler =
            "com.google.dagger:hilt-android-compiler:${Versions.hiltVersion}"
        const val testing = "com.google.dagger:hilt-android-testing:${Versions.hiltVersion}"
        const val navigationCompose =
            "androidx.hilt:hilt-navigation-compose:${Versions.hiltNavigationComposeVersion}"
    }

    object JUnit {

        const val junit = "junit:junit:${Versions.jUnitVersion}"
    }

    object Coil {

        const val coilCompose = "io.coil-kt:coil-compose:${Versions.coilVersion}"
    }

    object Lottie {

        const val lottieCompose = "com.airbnb.android:lottie-compose:${Versions.lottieVersion}"
    }

    object Retrofit {

        const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofitVersion}"
        const val gson = "com.squareup.retrofit2:converter-gson:${Versions.retrofitVersion}"
    }

    object OkHttp {

        const val loggingInterceptor =
            "com.squareup.okhttp3:logging-interceptor:${Versions.okhttpLoggingVersion}"
    }

    object Timber {

        const val timber = "com.jakewharton.timber:timber:${Versions.timberVersion}"
    }
}
