buildscript {
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.10")
        classpath("org.jlleitschuh.gradle:ktlint-gradle:11.0.0")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.5.2")
        classpath("com.google.gms:google-services:4.3.14")
    }
}

plugins {
    id("com.android.application") version "7.2.2" apply false
    id("com.android.library") version "7.2.2" apply false
    id("org.jetbrains.kotlin.android") version "1.7.10" apply false
    id("com.google.devtools.ksp") version "1.7.10-1.0.6" apply false
    id("org.jlleitschuh.gradle.ktlint") version "11.0.0"
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
