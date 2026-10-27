// Publishes the public Free artifacts (api, core) to GitHub Packages.
//
// Credentials come from the environment (CI: GITHUB_ACTOR / GITHUB_TOKEN) or from a
// user-level gradle.properties (github.actor / github.token) - never from this
// repository. Publishing is manual: ./gradlew publish. Ordinary builds and tests
// never touch the registry.

plugins {
    `maven-publish`
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/hauchdev/hAnnounce")
            credentials {
                username = providers
                    .environmentVariable("GITHUB_ACTOR")
                    .orElse(providers.gradleProperty("github.actor"))
                    .orElse("")
                    .get()
                password = providers
                    .environmentVariable("GITHUB_TOKEN")
                    .orElse(providers.gradleProperty("github.token"))
                    .orElse("")
                    .get()
            }
        }
    }
    publications {
        create<MavenPublication>("hAnnounce") {
            from(components["java"])
        }
    }
}
