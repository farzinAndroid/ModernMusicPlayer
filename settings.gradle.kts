pluginManagement {
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

rootProject.name = "ModernMusicPlayer"
include(":app")
include(":core")
include(":core:core_ui")
include(":core:core_media_store")
include(":core:core_domain")
include(":core:core_media_service")
include(":features")
include(":features:home")
include(":core:core_model")
include(":core:core_data")
include(":core:core_common")
include(":core:core_media_notifications")
include(":core:core_datastore")
