import com.utn.frba.mobile.regalapp.AppConfig
import com.utn.frba.mobile.regalapp.D
import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("org.jlleitschuh.gradle.ktlint")
    id("kotlin-parcelize")
    id("com.squareup.anvil") version "2.4.2"
    kotlin("kapt")
}

android {
    namespace = "com.utn.frba.mobile.regalapp.domain"
    compileSdk = AppConfig.compileSdk
    defaultConfig {
        minSdk = AppConfig.minSdk
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.5.1")

    // Dagger
    implementation(D.Dagger.dagger2)
    kapt(D.Dagger.compiler)

    // DataStore
    implementation(D.DataStore.core)

    // Firebase
    implementation(platform(D.Firebase.firebaseBom))
    implementation(D.Firebase.authentication)
    implementation(D.Firebase.authenticationKtx)
    implementation(D.Firebase.firestore)

    // Networking
    implementation(D.Retrofit.networkResponseAdapter)

    // Logging
    implementation(D.Logger.timber)

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.1")
}
