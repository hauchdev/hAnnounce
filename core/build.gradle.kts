// Shared internal module of hAnnounce.
//
// Intentionally free of product logic; features land here when implemented
// and are bundled into the distributable Paper plugin jar via the paper module.

plugins {
    `java-library`
    id("hauch.jvm-conventions")
}

// The public API is part of the compiled surface of this module.
dependencies {
    api(project(":api"))
}
