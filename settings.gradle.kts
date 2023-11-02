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
include(":data:detail")
include(":domain")
include(":domain:posts")
include(":domain:common")
include(":domain:detail")
include(":feature:detail")
include(":kotlinmultiplatformsharedmodule")
