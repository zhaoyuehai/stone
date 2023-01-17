pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "stone"
include(":app:full")
include(":core:ui")
include(":core:model")
include(":core:database")
include(":core:datastore")
include(":core:network-api")
include(":core:network-ktor")
include(":core:network-fake")
include(":core:repository")
include(":feature:home")
include(":feature:login")
include(":feature:profile")
