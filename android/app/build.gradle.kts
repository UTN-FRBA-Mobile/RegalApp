import com.utn.frba.mobile.regalapp.AppConfig
import com.utn.frba.mobile.regalapp.D

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("org.jlleitschuh.gradle.ktlint")
    id("com.squareup.anvil") version "2.4.2"
    kotlin("kapt")
    id("androidx.navigation.safeargs")
    id("com.google.gms.google-services")
}

android {
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        applicationId = AppConfig.applicationId
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName

        testInstrumentationRunner = AppConfig.androidTestInstrumentation
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        named("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            signingConfig = null
        }
        named("debug") {
            isMinifyEnabled = false
            isShrinkResources = false
            isDebuggable = true
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.1"
    }

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Android
    implementation(D.Android.appcompat)
    implementation(D.Android.androidXCore)
    implementation(D.Android.lifecycleRuntime)
    implementation(D.Android.fragments)
    implementation(D.Android.ktx)

    // Navigation
    implementation(D.Navigation.navigationFragment)
    implementation(D.Navigation.navigationUi)

    // Arch
    implementation(D.Arch.core)

    // Compose
    implementation(D.Compose.core)
    implementation(D.Compose.runtime)
    implementation(D.Compose.material)
    implementation(D.Compose.activityCompose)
    implementation(project(mapOf("path" to ":domain")))
    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("com.google.android.material:material:1.4.+")
    implementation("androidx.constraintlayout:constraintlayout:2.1.3")
    debugImplementation(D.Compose.uiTooling)
    debugImplementation(D.Compose.uiTestManifest)
    implementation(D.Compose.constraintLayout)

    // DataStore
    implementation(D.DataStore.core)

    // Firebase
    implementation(platform(D.Firebase.firebaseBom))
    implementation(D.Firebase.authentication)
    implementation(D.Firebase.authenticationKtx)
    implementation(D.Firebase.firestore)

    // Networking
    implementation(D.Retrofit.core)
    implementation(D.Retrofit.gsonConverter)
    implementation(D.Retrofit.networkResponseAdapter)

    // Dagger
    implementation(D.Dagger.dagger2)
    kapt(D.Dagger.compiler)

    // Logging
    implementation(D.Logger.timber)

    // Image loading and caching
    implementation(D.Landscapist.glide)

    // GoogleMaps
    implementation(D.GoogleMaps.core)
    implementation(D.GoogleMaps.utils)
    implementation(D.GoogleMaps.location)
}
