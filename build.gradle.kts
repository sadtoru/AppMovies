

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    //alias("androidx.navigation:navigation-safe-args-gradle-plugin:2.3.2")
}

buildscript {
    repositories {
        google()
    }
    dependencies {
        //val nav_version = "2.7.7"
        classpath(libs.androidx.navigation.safe.args.gradle.plugin)
    }
}