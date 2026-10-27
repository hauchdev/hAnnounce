# Architecture

hAnnounce is a Gradle multi-module project with three modules and one shared set of build conventions.

```
api        public API — pure Java, no platform dependencies
core       shared internal code — pure Java, no platform dependencies
paper      Paper plugin: entry point, plugin descriptor, plugin jar
buildSrc   build conventions shared by all modules
```

## Module responsibilities

| Module | Coordinates | Depends on | Responsibility |
| --- | --- | --- | --- |
| `api` | `dev.hauch.announce:api` | — | The public interface of the plugin. |
| `core` | `dev.hauch.announce:core` | `api` | Shared internal implementation. |
| `paper` | `dev.hauch.announce:paper` | `core`, Paper API (`compileOnly`) | Plugin lifecycle, descriptor and distribution jar. |

Dependencies flow strictly downward: `paper → core → api`. No module depends sideways or upward, and no platform API may appear in `api` or `core`.

## API boundaries

- Only `dev.hauch.announce.api` is public. Third-party plugins and hAnnounce Pro must build against this package only.
- Everything else is internal and may change or be removed at any time.
- The `api` module applies the `java-library` plugin, so Gradle enforces the separation between exposed and internal types.
- The `api` module currently contains no product code. Public interfaces are introduced only when an actual implementation exists, so the API never documents behaviour that is not shipped.

## Platform support

- Paper is the primary platform. The Paper API is declared `compileOnly`, so it is provided by the server at runtime and is never bundled into the plugin jar.
- The plugin descriptor is a plain `plugin.yml` in the standard Spigot/Paper format. Features are expected to use the Spigot API unless they genuinely need Paper-specific functionality.
- Java 21 is the initial minimum version. Minecraft 26.x and its Paper API require Java 25; when that upgrade happens, the toolchain and the Paper API version move together — both are referenced in `gradle/libs.versions.toml`, where the pairing is noted.

## Packaging

- The distribution artifact is `paper:shadowJar` with base name `hAnnounce-Paper`, written to `paper/build/libs/hAnnounce-Paper-<version>.jar`.
- The jar bundles the `core` module, which brings `api` with it. All `compileOnly` platform dependencies are excluded from the bundle, so nothing the server already provides gets duplicated.
- The plain jar task remains enabled and produces `paper-<version>.jar`. Shadow's runtime resolution requires it; it is not the distribution artifact.
- Archives are reproducible: file timestamps are normalized and entry order is stable.
- No package relocations are configured. All bundled code lives under the `dev.hauch.announce` namespace, which the project owns.

## Build conventions

- Shared settings (Java 21 toolchain, UTF-8 encoding and compiler checks, the JUnit 5 platform with versions managed through the `junit-bom`, reproducible archives, test logging) live in the `hauch.jvm-conventions` plugin in `buildSrc` and are applied by every module.
- All dependency versions are declared in `gradle/libs.versions.toml`. Dynamic versions are not used.
- Repositories are declared once, centrally, in `settings.gradle.kts`. Projects are prevented from adding their own (`FAIL_ON_PROJECT_REPOS`).

## hAnnounce Pro

Pro's distribution model is **standalone**: its jar bundles the public Free artifacts (`dev.hauch.announce:api`, `dev.hauch.announce:core`) and ships as a single plugin that does not require Free installed on the server. Pro does not fork Free sources; it consumes the compiled artifacts.

Two rules keep this distribution workable:

1. **The `paper` module of this repository stays thin.** Engine and product logic belong in `core`, with public interfaces in `api`. Anything implemented in `paper` would not be carried into the Pro jar.
2. **Do not mix editions on one server.** Pro ships its own copy of Free's classes; installing Free next to Pro duplicates the engine. Pro warns at startup when it detects the free plugin.

Publication is configured (GitHub Packages under the `hauchdev` account; credentials via environment variables or a user-level `gradle.properties`) but has never been executed. While no artifacts are published, Pro builds against the local Free checkout through a Gradle composite build. Publishing is manual — locally with `gradlew publish`, or through the dispatch-only `publish` workflow; see the [development guide](DEVELOPMENT.md).

## Not implemented (planned)

- Announcement engine, message rendering and scheduling
- Commands and permission handling
- Configuration system
- Integrations with external services
- First publication of the `api`/`core` artifacts (the pipeline is configured; nothing published yet)
- License validation (Pro side)
