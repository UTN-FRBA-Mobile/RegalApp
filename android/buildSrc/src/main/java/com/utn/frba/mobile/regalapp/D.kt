package com.utn.frba.mobile.regalapp

import io.github.fededri.marvel.V

object D {
    object Android {
        const val androidXCore = "androidx.core:core-ktx:${V.androidXCore}"
        const val lifecycleRuntime =
            "androidx.lifecycle:lifecycle-runtime-ktx:${V.androidxLifecycle}"
        const val fragments = "androidx.fragment:fragment-ktx:${V.fragmentVersion}"
        const val appcompat = "androidx.appcompat:appcompat:${V.appCompat}"
        const val ktx = "androidx.core:core-ktx:${V.androidKtx}"
    }

    object Arch {
        const val core = "io.github.fededri.arch:shared:${V.arch}"
    }

    object Compose {
        const val core = "androidx.compose.ui:ui:${V.compose}"
        const val runtime = "androidx.compose.runtime:runtime:${V.compose}"
        const val activityCompose = "androidx.activity:activity-compose:${V.activityCompose}"
        const val material = "androidx.compose.material:material:${V.compose}"
        const val uiTooling = "androidx.compose.ui:ui-tooling:${V.compose}"
        const val uiTestManifest = "androidx.compose.ui:ui-test-manifest:${V.compose}"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout-compose:${V.composeConstraintLayout}"
    }

    object Dagger {
        const val dagger2 = "com.google.dagger:dagger:${V.dagger}"
        const val compiler = "com.google.dagger:dagger-compiler:${V.dagger}"
    }

    object DataStore {
        const val core = "androidx.datastore:datastore-preferences:${V.dataStore}"
    }

    object Firebase {
        const val firebaseBom = "com.google.firebase:firebase-bom:${V.firebaseBom}"
        const val authentication = "com.google.firebase:firebase-auth"
        const val authenticationKtx = "com.google.firebase:firebase-auth-ktx"
        const val firestore = "com.google.firebase:firebase-firestore-ktx"
    }

    object Landscapist {
        const val glide = "com.github.skydoves:landscapist-glide:${V.landscapist}"
    }

    object Logger {
        const val timber = "com.jakewharton.timber:timber:${V.timber}"
    }

    object Navigation {
        const val navigationFragment =  "androidx.navigation:navigation-fragment-ktx:${V.navigationVersion}"
        const val navigationUi = "androidx.navigation:navigation-ui-ktx:${V.navigationVersion}"
    }

    object Retrofit {
        const val core = "com.squareup.retrofit2:retrofit:${V.retrofit}"
        const val gsonConverter = "com.squareup.retrofit2:converter-gson:${V.retrofit}"
        const val mock = "com.squareup.retrofit2:retrofit-mock:${V.retrofit}"
        const val networkResponseAdapter = "com.github.haroldadmin:networkresponseadapter:${V.networkResponseAdapter}"
        const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${V.loggingInterceptor}"
    }
}