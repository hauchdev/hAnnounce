# hAnnounce — Architecture

This document describes the module structure of the hAnnounce (Free) repository, the
packaging strategy, and how hAnnounce Pro will relate to this repository. It reflects the
**foundation stage**: no product features are implemented yet, and everything structural
below is scaffolding that development will grow into.

## Module overview

```
hAnnounce (Gradle multi-project build)
├── api     → public API surface            (pure Java, no platform deps)
├── core    → shared internal implementation (pure Java, no platform deps)
├── paper   → Paper platform module          (Paper/Spigot API compile-only)
└── buildSrc → shared Gradle convention plugins (hauch.jvm-conventions)
```

| Module | Group / artifact | May depend on | Purpose |
| --- | --- | --- | --- |
| `:api` | `dev.hauch.announce:api` | — | The public API. The only package third-party plugins and Pro may depend on. |
| `:core` | `dev.hauch.announce:core` | `:api` | Shared internal implementation across platform modules. Never exposed to third parties. |
| `:paper` | `dev.hauch.announce:paper` | `:core`, Paper API | Plugin entry point (`HAnnouncePlugin`), descriptor, and the distributable plugin jar. |

Dependency direction is strictly downward: `paper → core → api`. Nothing depends upward
or sideways, and platform APIs never leak into `api` or `core`.

## Public API discipline

- Only `dev.hauch.announce.api.*` is public.
- Everything else is internal and may change at any moment.
- The `api` module uses the `java-library` plugin so that `api`/`implementation`
  separation is enforced by the build.
- The current `api` module is intentionally **empty of product code**. Public announcement
  programming interfaces will be designed there only once the actual consumer-facing
  behavior is implemented.

## Platform strategy

- **Paper is the primary platform.** The plugin compiles against Paper API
  (`io.papermc.paper:paper-api`), declared `compileOnly` and therefore **never shaded**
  into the plugin jar.
- **Spigot compatibility** is kept where appropriate: the plugin descriptor
  (`plugin.yml`) is the standard Spigot/Paper format, and platform modules are expected
  to avoid Paper-only API unless the feature needs it.
- **Java 21** is the initial minimum. Note: Minecraft/Paper 26.x requires **Java 25**; a
  future toolchain bump and Paper API bump are planned together (both are managed in
  `gradle/libs.versions.toml`).

## Packaging

The distributable artifact is `paper:shadowJar`:

- Base name `hAnnounce-Paper`, produced at `paper/build/libs/hAnnounce-Paper-<version>.jar`.
- Bundles internal modules (`:core`, which transports `:api`).
- Excludes all platform-provided dependencies (`compileOnly`), so nothing shared with the
  server is duplicated inside the jar.
- Archives are reproducible (stable timestamps and entry order).

The plain thin jar (`paper-0.1.0-SNAPSHOT.jar`) remains enabled because Shadow's runtime
resolution depends on it; it is not the distribution artifact.

## Version management

- All dependency versions live in `gradle/libs.versions.toml`. Modules never pin versions
  inline. No dynamic versions are used.
- Build conventions (toolchain, encoding, JUnit 5 platform, reproducible archives) live in
  the `hauch.jvm-conventions` convention plugin in `buildSrc`, applied by every module.
- Repositories are declared once, centrally, in `settings.gradle.kts` with a
  fail-on-project-repos mode.

## How hAnnounce Pro will consume this repository (planned)

hAnnounce Pro (private repo `hauchdev/hAnnounce-Pro`) is a separate Gradle project. The
planned relationship:

1. Free publishes **versioned artifacts** `dev.hauch.announce:api` (and `dev.hauch.announce:core`
   if useful) through a Maven repository.
2. Pro declares compile-time dependencies on those versioned artifacts and is distributed
   as its own plugin jar.
3. Pro never forks Free source code.

**Not configured yet.** The publication repository (e.g. GitHub Packages under the
`hauchdev` account) and its credentials do not exist during bootstrap; neither the Free
publication configuration nor the Pro consumption configuration is in place, and no fake
credentials are shipped. This remains explicit pending setup (see
[DEVELOPMENT.md](DEVELOPMENT.md) for the remaining-setup checklist).

## Not implemented (planned)

- Announcement engine, message rendering, scheduling
- Commands and permission integration
- Configuration system
- External integrations
- Maven publication of `api`/`core` artifacts
- License validation for Pro (Pro-side)
