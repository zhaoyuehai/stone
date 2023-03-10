plugins {
    id("com.android.application") version "7.4.0" apply false
    id("com.android.library") version "7.4.0" apply false
    id("org.jetbrains.kotlin.android") version "1.7.20" apply false
    id("org.jetbrains.kotlin.jvm") version "1.7.20" apply false
    id("com.google.protobuf") version "0.9.1" apply false
}

task("clean", Delete::class) {
    delete(rootProject.buildDir)
}