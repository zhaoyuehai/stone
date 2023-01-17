plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    namespace = "com.yuehai.stone.feature.profile"
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
    implementation(libs.coil.compose)
}