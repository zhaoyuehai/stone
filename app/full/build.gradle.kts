import com.android.build.gradle.internal.api.ApkVariantOutputImpl
import java.io.FileInputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

plugins {
    id("com.android.application")
    id("kotlin-android")
}

android {
    namespace = "com.yuehai.stone.app.full"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.yuehai.stone.app"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "0.0.1"// X.Y.Z; X = Major, Y = minor, Z = Patch level
    }
    signingConfigs {
        val properties = Properties().apply {
            load(FileInputStream(rootProject.file("keystore.properties")))
        }
        create("release") {
            storeFile = file(properties["storeFile"].toString())
            storePassword = properties["storePassword"].toString()
            keyAlias = properties["keyAlias"].toString()
            keyPassword = properties["keyPassword"].toString()
        }
    }
    buildTypes {
        release { signingConfig = signingConfigs.getByName("release") }
    }
    flavorDimensions.add("version")
    productFlavors {
        create("fake")
        create("dev")
        create("prod")
    }
    applicationVariants.all {
        outputs.map { it as ApkVariantOutputImpl }.forEach {
            it.outputFileName = "stone_v${this.versionName}_${
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmm"))
            }.apk"
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions { jvmTarget = "17" }
    buildFeatures { compose = true }
    composeOptions { kotlinCompilerExtensionVersion = libs.versions.androidxComposeComplier.get() }
    packagingOptions { resources { excludes += "/META-INF/{AL2.0,LGPL2.1}" } }
}

dependencies {
    implementation(project(":core:ui"))
    implementation(project(":core:repository"))
    implementation(project(":feature:home"))
    implementation(project(":feature:login"))
    implementation(project(":feature:profile"))
//    implementation(project(":feature:upgrade"))
}