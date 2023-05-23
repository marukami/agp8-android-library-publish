pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
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
