// Settings for the buildSrc (convention plugin) build.
// Shares the main version catalog so `hauch.*` convention plugins can use `libs.*` accessors.

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}
