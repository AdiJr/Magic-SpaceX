plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    compileSdk = 33

    defaultConfig {
        applicationId = "com.adi.magicspacex"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
        }
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.2"
    }
    packagingOptions {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
    namespace = "com.adi.magicspacex"
}

dependencies {
    implementation(Libs.Kotlin.Coroutines.android)

    implementation(Libs.Accompanist.insets)
    implementation(Libs.Accompanist.pager)
    implementation(Libs.Accompanist.pagerIndicators)

    implementation(Libs.Lottie.lottieCompose)

    implementation(Libs.Material.material)

    implementation(Libs.Retrofit.retrofit)
    implementation(Libs.Retrofit.gson)

    implementation(Libs.OkHttp.loggingInterceptor)

    implementation(Libs.AndroidX.Activity.activityCompose)
    implementation(Libs.AndroidX.appcompat)
    implementation(Libs.AndroidX.Compose.material)
    implementation(Libs.AndroidX.Compose.ui)
    implementation(Libs.AndroidX.Compose.navigation)
    implementation(Libs.AndroidX.Lifecycle.viewModelCompose)
    implementation(Libs.AndroidX.Lifecycle.liveData)
    implementation(Libs.AndroidX.Compose.toolingPreview)
    implementation(Libs.AndroidX.Compose.runtimeLivedata)

    implementation(Libs.Hilt.android)
    implementation(Libs.Hilt.testing)
    implementation(Libs.Hilt.navigationCompose)

    implementation(Libs.Coil.coilCompose)

    implementation(Libs.Timber.timber)

    androidTestImplementation(Libs.JUnit.junit)
    androidTestImplementation(Libs.AndroidX.Test.runner)
    androidTestImplementation(Libs.AndroidX.Test.espressoCore)
    androidTestImplementation(Libs.AndroidX.Test.rules)
    androidTestImplementation(Libs.AndroidX.Test.Ext.junit)
    androidTestImplementation(Libs.Kotlin.Coroutines.test)
    androidTestImplementation(Libs.Hilt.android)
    androidTestImplementation(Libs.Hilt.testing)

    debugImplementation(Libs.AndroidX.Compose.tooling)
}
