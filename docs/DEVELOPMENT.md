# hAnnounce — Development Guide

How to set up a working environment for hAnnounce (Free) and run the build locally.

## Prerequisites

- **JDK 21** (e.g. [Eclipse Temurin](https://adoptium.net), Microsoft OpenJDK 21, or any
  other JDK 21 distribution). Gradle toolchains locate the JDK automatically; a JDK 21
  must simply be installed or resolvable.
- **IntelliJ IDEA** (any recent 2024+ version, Community is fine). You can also build
  entirely from the command line.
- No Gradle installation is required — the Gradle wrapper is checked in.
- **Git 2.30+**.

## Opening the project in IntelliJ IDEA

1. `File → Open…` and select the **repository root** (the folder containing
   `settings.gradle.kts`). Choose *Open as Project* — do not import it as a Maven project.
2. Let IDEA import the Gradle project and finish syncing. On first sync the wrapper
   downloads the Gradle distribution and dependencies, which can take a few minutes.
3. Verify the project SDK: `Project Structure → Project → SDK` should point to JDK 21
   (e.g. an entry downloaded via `Add SDK → Download JDK` if you have none).
4. Recommended checks after sync:
   - `Settings → Editor → File Encodings`: encoding UTF-8 everywhere (files, properties,
     and default).
   - `Settings → Editor → Code Style`: line separator LF (this is also enforced by
     `.editorconfig` and `.gitattributes`).

## Build and test

```bash
./gradlew build        # full build: compile, package, test (Windows: gradlew.bat)
./gradlew test         # tests only
./gradlew clean build  # clean rebuild
```

Windows PowerShell: `.\gradlew.bat build`.

Gradle features (parallel execution, configuration cache, build cache) are already enabled
in `gradle.properties`.

### Artifacts

| Path | Content |
| --- | --- |
| `paper/build/libs/hAnnounce-Paper-<version>.jar` | **Distribution jar** — bundles internal modules (put this in a server) |
| `paper/build/libs/paper-<version>-plain.jar` | Thin jar (not for distribution) |
| `api/build/libs/…` / `core/build/libs/…` | Internal module jars (no runtime use standalone) |

## Running a local Paper development server

1. Create a local folder, e.g. `run/` (already ignored by `.gitignore`):
   ```
   ▸ run/
   ```
2. Download a [Paper 1.21.11](https://papermc.io/downloads) build jar and place it in
   `run/`. Paper requires Java 21 — the same JDK used by the build works.
3. Build the plugin and copy the distribution jar:
   ```bash
   ./gradlew build
   cp paper/build/libs/hAnnounce-Paper-0.1.0-SNAPSHOT.jar run/plugins/
   ```
4. Start the server (accept the EULA on first run):
   ```bash
   java -jar paper.jar --nogui
   ```

Because the current build is scaffolding only, the server will log
`hAnnounce loaded (foundation build - no features enabled).` and nothing else will happen.
That is expected.

## Coding conventions

- Java 21 toolchain, UTF-8, enforced by `buildSrc/src/main/kotlin/hauch.jvm-conventions.gradle.kts`.
- Package namespace `dev.hauch.announce.*` (`api`, `core`, `paper` for their modules).
- Dependency versions only via `gradle/libs.versions.toml`.
- Keep `api` and `core` free of platform dependencies (see [ARCHITECTURE.md](ARCHITECTURE.md)).
- Conventional commits (see [CONTRIBUTING.md](../CONTRIBUTING.md)).

## CI

GitHub Actions (`.github/workflows/build.yml`) builds and tests on every pull request and
push to `main`, and uploads the distribution jar on pushes. No publishing happens in CI.

## Remaining setup checklist (explicitly pending)

The following are **not** part of the current foundation and are listed so they are not
forgotten later:

- [ ] Maven publication configuration for `api`/`core` (repository + credentials, e.g.
      GitHub Packages under `hauchdev`). Not configured anywhere; no fake credentials in
      the repo.
- [ ] Pro consumption wiring in `hauchdev/hAnnounce-Pro` (depends on the above).
- [ ] Release pipeline (no automatic production releases exist in CI during bootstrap).
