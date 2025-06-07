pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
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

rootProject.name = "MovieFlix"
include(":app")
include(":core:data")
include(":core:domain")
include(":core:presentation:ui")
include(":core:presentation:designsystem")
include(":auth:data")
include(":auth:domain")
include(":auth:presentation")
include(":home:data")
include(":home:domain")
include(":home:presentation")
include(":movie-details:data")
include(":movie-details:presentation")
include(":movie-details:domain")
include(":core:database")
