# Development guide

This guide explains how to set up a development environment for hAnnounce, build the project and run it on a local Paper server. Module responsibilities and packaging are covered in the [architecture notes](ARCHITECTURE.md).

## Requirements

- **JDK 21** — e.g. [Eclipse Temurin](https://adoptium.net) or Microsoft OpenJDK 21. The Gradle toolchain detects the installed JDK automatically.
- **IntelliJ IDEA** — a recent Community edition is sufficient. Building from the command line works without any IDE.
- **No Gradle installation is needed.** The wrapper is committed to the repository.
- **Git 2.30 or newer.**

## Importing into IntelliJ IDEA

1. Choose `File → Open…` and select the **repository root** — the folder containing `settings.gradle.kts`. Open it as a project; do not use a Maven or Eclipse import.
2. Wait for the Gradle sync to finish. The first sync downloads the Gradle distribution and all dependencies and can take a few minutes.
3. Verify the project SDK under `File → Project Structure → Project`: it should point to a JDK 21 installation.
4. Two editor settings are worth checking after the import. Under `Settings → Editor → File Encodings`, the encoding for files, properties and the default should all be UTF-8. The default line separator should be LF. Both are also enforced per file by `.editorconfig` and `.gitattributes`.

## Building and testing

```bash
./gradlew build        # compile, package and run tests (Windows: .\gradlew.bat build)
./gradlew test         # tests only
./gradlew clean build  # clean rebuild
```

Parallel execution, the build cache and the configuration cache are enabled through `gradle.properties`.

Artifacts produced by the build:

| Path | Description |
| --- | --- |
| `paper/build/libs/hAnnounce-Paper-<version>.jar` | The plugin jar with the internal modules bundled in. This is the file to install on a server. |
| `paper/build/libs/paper-<version>.jar` | The thin jar produced by the plain `jar` task. Shadow needs it for runtime resolution; it is not for distribution. |
| `api/build/libs/…` and `core/build/libs/…` | Module jars treated as part of the plugin classpath at runtime; not distributed standalone. |

## Running a local Paper server

1. Create a folder such as `run/` inside the repository. It is already ignored by git.
2. Download a Paper 1.21.11 build from [papermc.io/downloads](https://papermc.io/downloads) and place the jar in `run/`. Paper and the plugin both run on Java 21 — the same JDK used by the build is sufficient. In the commands below the server jar is referred to as `paper.jar`.
3. Build the plugin and copy the distribution jar into `run/plugins/`:

```bash
./gradlew build
cp paper/build/libs/hAnnounce-Paper-0.1.0-SNAPSHOT.jar run/plugins/
```

On Windows, copy with `Copy-Item` in PowerShell instead of `cp`.

4. Start the server from `run/` and accept the EULA on the first start:

```bash
cd run
java -jar paper.jar --nogui
```

Because the plugin is still a shell, the server will log `hAnnounce loaded (foundation build - no features enabled).` during startup and nothing further will happen. That is expected until features are implemented.

## Conventions

- The Java 21 toolchain, UTF-8 encoding and the JUnit 5 setup are defined once in `buildSrc/src/main/kotlin/hauch.jvm-conventions.gradle.kts` and applied by every module.
- Dependency versions live only in `gradle/libs.versions.toml`. Module build files never pin versions inline, and dynamic versions are not used.
- The `api` and `core` modules must remain free of Paper, Spigot and any other platform dependency. Reasons and boundaries are described in the [architecture notes](ARCHITECTURE.md).
- Commit messages follow conventional commits; see [CONTRIBUTING.md](../CONTRIBUTING.md).

## Continuous integration

`.github/workflows/build.yml` builds and tests the project on every pull request and on pushes to `main`, and uploads the plugin jar as a workflow artifact on pushes. This workflow does not publish anything and does not create releases.

## Publishing artifacts

The `api` and `core` modules are configured to publish to GitHub Packages under the `hauchdev` account. The publication uses the project coordinates (`dev.hauch.announce:api`, `dev.hauch.announce:core`) and reads credentials from:

- the `GITHUB_ACTOR` and `GITHUB_TOKEN` environment variables, or
- a user-level `~/.gradle/gradle.properties` with `github.actor` and `github.token`. This file is outside the repository and must never be committed.

The publication has never been executed; the first real `publish` happens once those modules carry actual content:

```bash
./gradlew publish
```

In CI there is a dispatch-only `publish` workflow (`.github/workflows/publish.yml`) that publishes with the workflow's own token. It runs only when triggered by hand — nothing is published automatically.

## Pending setup

- [ ] First real publication of the `api` and `core` artifacts once they carry content. Until then hAnnounce Pro builds against the local checkout of this repository.
- [ ] A release pipeline. CI does not publish automatically and does not create releases.
