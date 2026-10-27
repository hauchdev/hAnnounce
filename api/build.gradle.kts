// Public API module of hAnnounce.
//
// Enforces explicit api/implementation separation so that only types intended
// for third-party and Pro consumption leak to dependents.

plugins {
    `java-library`
    id("hauch.jvm-conventions")
    id("hauch.publish-conventions")
}
