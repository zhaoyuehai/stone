plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    namespace = "com.yuehai.stone.core.ui"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
    }
    buildFeatures { compose = true }
    composeOptions { kotlinCompilerExtensionVersion = libs.versions.androidxComposeComplier.get() }
}

dependencies {
    implementation(platform("androidx.compose:compose-bom:2022.12.00"))
    api("androidx.compose.material:material")
    api("androidx.compose.ui:ui-tooling")
    api(libs.androidx.navigation.compose)
}