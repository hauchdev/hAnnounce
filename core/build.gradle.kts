// Shared internal module of hAnnounce.
//
// Intentionally free of product logic; features land here when implemented
// and are bundled into the distributable Paper plugin jar via the paper module.
// hAnnounce Pro bundles this module's published artifact inside its own
// standalone jar, so anything the whole hAnnounce line should share belongs here.

plugins {
    `java-library`
    id("hauch.jvm-conventions")
    id("hauch.publish-conventions")
}

// The public API is part of the compiled surface of this module.
dependencies {
    api(project(":api"))
}
