plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    namespace = "com.yuehai.stone.core.network.fake"
    compileSdk = libs.versions.compileSdk.get().toInt()
    defaultConfig { minSdk = libs.versions.minSdk.get().toInt() }
}

dependencies {
    api(project(":core:network-api"))
    implementation(libs.ktor.serialization.gson)
}