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
plugins {
    id("de.fayard.refreshVersions") version "0.40.2"
}
rootProject.name = "MoviesApp"
include(":app")
include(":data")
include(":features:home")
include(":features:moviedetail")
include(":domain")
include(":features:shared")
