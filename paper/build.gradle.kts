// Paper platform module of hAnnounce: plugin entry point, plugin descriptor and
// distribution jar production.
//
// DISTRIBUTION RULE for the standalone Pro edition: engine and product logic
// belong in `core` (with public interfaces in `api`) - hAnnounce Pro bundles
// those artifacts into its own single plugin jar. Anything implemented inside
// this module would NOT be carried into Pro. Keep the paper module thin: only
// lifecycle glue, descriptor handling and packaging.

plugins {
    id("hauch.jvm-conventions")
    alias(libs.plugins.shadow)
}

dependencies {
    // Platform API is provided by the server at runtime and must never be shaded
    // into the plugin jar.
    compileOnly(libs.paper.api)

    // Internal modules are bundled into the distributable plugin jar.
    implementation(project(":core"))
}

// Expand the version placeholder in the plugin descriptor at build time.
tasks.processResources {
    val pluginVersion = project.version.toString()
    inputs.property("version", pluginVersion)
    filesMatching("plugin.yml") {
        expand("version" to pluginVersion)
    }
}

// The distributable plugin jar is the shadowJar below: it bundles the internal
// modules but excludes platform-provided dependencies. The plain (thin) jar is
// kept enabled because the shadow runtime resolution depends on it; both jars
// are produced under build/libs with distinct base names.
tasks.shadowJar {
    archiveBaseName = "hAnnounce-Paper"
    archiveClassifier = ""
}
