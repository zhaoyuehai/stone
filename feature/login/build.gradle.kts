plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    namespace = "com.yuehai.stone.feature.login"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
    }
    flavorDimensions.add("version")
    productFlavors {
        create("fake")
        create("dev")
        create("prod")
    }
    buildFeatures { compose = true }
    composeOptions { kotlinCompilerExtensionVersion = libs.versions.androidxComposeComplier.get() }
}

dependencies {
    implementation(project(":core:ui"))
    implementation(project(":core:repository"))
}