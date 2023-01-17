plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    namespace = "com.yuehai.stone.core.repository"
    compileSdk = libs.versions.compileSdk.get().toInt()
    defaultConfig { minSdk = libs.versions.minSdk.get().toInt() }
    flavorDimensions.add("version")
    productFlavors {
        create("fake")
        create("dev")
        create("prod")
    }
}

dependencies {
    api(project(":core:model"))
    implementation(libs.kotlinx.coroutines.core.jvm)
//    implementation(project(":core:network-ktor"))
    implementation(project(":core:network-fake"))
    implementation(project(":core:database"))
    implementation(project(":core:datastore"))
}