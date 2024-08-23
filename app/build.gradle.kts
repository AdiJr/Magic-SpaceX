plugins {
    kotlin("kapt")
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
}

android {
    compileSdk = 34
    namespace = "com.adi.magicspacex"

    defaultConfig {
        applicationId = "com.adi.magicspacex"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    flavorDimensions += "default"

    productFlavors {
        create("stage") {
            applicationIdSuffix = ".stage"
            versionName = "-stage"
        }

        create("prod") {
            // No specific configuration.
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            applicationIdSuffix = ".debug"
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
        jvmTarget = "17"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
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
}

dependencies {
    implementation(Libs.Kotlin.Coroutines.android)

    implementation(Libs.Accompanist.insets)

    implementation(Libs.Retrofit.retrofit)
    implementation(Libs.Retrofit.gson)

    implementation(Libs.OkHttp.loggingInterceptor)

    implementation(Libs.AndroidX.Activity.activityCompose)
    implementation(Libs.AndroidX.appcompat)
    implementation(Libs.AndroidX.Compose.material3)
    implementation(Libs.AndroidX.Compose.ui)
    implementation(Libs.AndroidX.Compose.navigation)
    implementation(Libs.AndroidX.Lifecycle.viewModelCompose)
    implementation(Libs.AndroidX.Compose.toolingPreview)
    implementation(Libs.AndroidX.Compose.lifecycle)

    implementation(Libs.Hilt.android)
    implementation(Libs.Hilt.testing)
    implementation(Libs.Hilt.navigationCompose)
    kapt(Libs.Hilt.androidCompiler)

    implementation(Libs.Coil.coilCompose)

    implementation(Libs.Lottie.lottieCompose)

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

// Allow references to generated code
kapt {
    correctErrorTypes = true
}
