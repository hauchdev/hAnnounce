import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.tasks.bundling.AbstractArchiveTask
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.api.tasks.testing.Test
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

// Shared JVM conventions applied by every hAnnounce (Free) module:
//  - Java 21 toolchain
//  - UTF-8 source/encoding hygiene and meaningful compiler checks
//  - JUnit 5 test platform with managed dependency versions
//  - reproducible archives

plugins {
    java
}

group = "dev.hauch.announce"
version = "0.1.0-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
    options.compilerArgs.addAll(
        listOf(
            "-Xlint:all,-processing",
            // Retain method parameter names for reflective use by platform code.
            "-parameters",
        ),
    )
}

tasks.withType<Javadoc>().configureEach {
    options.encoding = "UTF-8"
}

// JUnit 5. Versions are managed centrally via the junit-bom (gradle/libs.versions.toml).
// Note: type-safe `libs.*` accessors are not generated for precompiled script plugins,
// so the shared catalog is read programmatically here.
val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

dependencies {
    testImplementation(platform(libs.findLibrary("junit-bom").get()))
    testImplementation(libs.findLibrary("junit-jupiter").get())
    testRuntimeOnly(libs.findLibrary("junit-platform-launcher").get())
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
    testLogging {
        events(TestLogEvent.FAILED, TestLogEvent.SKIPPED)
        exceptionFormat = TestExceptionFormat.SHORT
    }
}

// Reproducible build output: stable timestamps and entry order in produced archives.
tasks.withType<AbstractArchiveTask>().configureEach {
    isPreserveFileTimestamps = false
    isReproducibleFileOrder = true
}
