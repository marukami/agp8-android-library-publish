pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        mavenLocal()
        google()
        mavenCentral()
    }
}

rootProject.name = "JPublish"
include(":app")
include(":multilibrary")
include(":multikotlinlibrary")
include(":singlelibrary")
include(":singlekotlinlibrary")
