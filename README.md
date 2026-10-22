# hAnnounce

[![Status: Foundation](https://img.shields.io/badge/status-foundation%20scaffolding-164AA3?style=flat)](https://github.com/hauchdev/hAnnounce) [![Platform: Paper](https://img.shields.io/badge/platform-Paper-0783C9?style=flat)](#) [![Java: 21](https://img.shields.io/badge/Java-21-112288?style=flat)](#) [![Build: Gradle](https://img.shields.io/badge/build-Gradle%20Kotlin%20DSL-164AA3?style=flat)](#)

[![License: MIT](https://img.shields.io/badge/license-MIT-0783C9?style=flat)](LICENSE)

**hAnnounce** (Free) is the free edition of an announcement plugin for Minecraft servers. It is intended to become a server plugin built primarily for modern Paper servers, with Spigot compatibility where appropriate, developed by [Hauchdev](https://hauch.dev).

> **Current state: project foundation only.**
> This repository currently contains the build infrastructure, module structure, and a minimal, loadable plugin shell. **No announcement functionality, commands, schedulers, configuration handling, or integrations are implemented yet.** Everything described below is scaffolding for development that is about to start.

## Why this repository exists

- Provide a clean, reproducible Gradle multi-project foundation for hAnnounce development.
- Establish a stable public API package (`dev.hauch.announce.api`) that other plugins — and the private [hAnnounce Pro](https://github.com/hauchdev/hAnnounce-Pro) edition — can build against in the future.
- Keep platform-specific code (Paper) isolated from shared code (`core`) and from the public API (`api`).

## Modules

| Module | Purpose | Status |
| --- | --- | --- |
| `api` | Public API surface for third-party plugins and hAnnounce Pro. | Scaffolding (empty by design) |
| `core` | Shared internal implementation code used by platform modules. | Scaffolding (empty by design) |
| `paper` | Paper plugin: entry point, plugin descriptor, distributable plugin jar. | Minimal entry point only |

## Build

Requirements: JDK 21 (the build uses the Gradle toolchain and locates a matching JDK automatically).

```bash
./gradlew build      # compile, package and test all modules
./gradlew test       # tests only
```

The distributable Paper plugin jar is produced at `paper/build/libs/hAnnounce-Paper-<version>.jar`.

For the full development guide (IntelliJ setup, running a local Paper server, conventions), see [docs/DEVELOPMENT.md](docs/DEVELOPMENT.md).

## Relationship to hAnnounce Pro

`hauchdev/hAnnounce-Pro` is a private repository for the premium edition. Pro will consume versioned API artifacts published from this repository through a Maven repository. That pipeline is **not configured yet** — see [docs/ARCHITECTURE.md](docs/ARCHITECTURE.md) for the plan.

## Not implemented (planned)

The following are part of the project's direction but **do not exist in the code yet**:

- Announcement functionality (the core feature set)
- Commands
- Scheduled announcements
- Configuration system
- Message rendering
- Integrations with other platforms or services
- A public API for third-party use
- Maven publication of `api`/`core` artifacts

## License

hAnnounce Free is released under the [MIT License](LICENSE).

© 2026 Hauchdev. hAnnounce is not affiliated with, endorsed by, or associated with Mojang Studios, Microsoft, or the Paper project.
