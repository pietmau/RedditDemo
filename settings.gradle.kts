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

rootProject.name = "RedditDemo"
include(":app")
include(":feature:posts")
include(":core:network")
include(":core:common")
include(":core:analytics")
include(":core:persistence")
include(":data:posts")
include(":domain")
