plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    namespace = "com.yuehai.stone.core.network.ktor"
    compileSdk = libs.versions.compileSdk.get().toInt()
    defaultConfig { minSdk = libs.versions.minSdk.get().toInt() }
    flavorDimensions.add("version")
    productFlavors {
        create("dev") {
            dimension = "version"
            buildConfigField("String", "BASE_URL", "\"http://192.168.0.106\"")
        }
        create("prod") {
            dimension = "version"
            buildConfigField("String", "BASE_URL", "\"http://192.168.109.9\"")
        }
    }
}

dependencies {
    api(project(":core:network-api"))
    implementation(libs.ktor.client.android)
    implementation(libs.ktor.client.okhttp)
    implementation(libs.ktor.client.auth)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.gson)
}