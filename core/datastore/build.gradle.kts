plugins {
    id("com.android.library")
    id("kotlin-android")
    id("com.google.protobuf")
}

android {
    namespace = "com.yuehai.stone.core.datastore"
    compileSdk = libs.versions.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions { jvmTarget = "11" }
}

protobuf {
    protoc { artifact = libs.protobuf.protoc.get().toString() }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                val java by registering { option("lite") }
            }
        }
    }
}

dependencies {
    implementation(project(":core:model"))
    implementation(libs.androidx.datastore)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.protobuf.javalite)
}