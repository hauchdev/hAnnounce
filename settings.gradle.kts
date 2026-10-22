rootProject.name = "hAnnounce"

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    // All repositories are declared once, centrally. Modules must not add their own.
    repositoriesMode = RepositoriesMode.FAIL_ON_PROJECT_REPOS
    repositories {
        mavenCentral()
        // PaperMC repository (hosts paper-api and proxies the Spigot artifacts).
        maven("https://repo.papermc.io/repository/maven-public/") {
            name = "Papermc"
        }
    }
}

// Public API shared by Free itself and (as published artifacts) by hAnnounce Pro.
include(":api")

// Shared internal implementation code. Never exposed to third parties directly.
include(":core")

// Paper plugin packaging and entry point. Requires the Paper API.
include(":paper")
