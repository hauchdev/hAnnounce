# Contributing to hAnnounce

Thanks for taking an interest in hAnnounce. This document describes how to set up a
development environment and submit contributions.

## Project state

hAnnounce is currently in its **foundation stage**: the codebase contains build
infrastructure and a minimal plugin shell. Announcement features are being developed.
Please read [README.md](README.md) and [docs/ARCHITECTURE.md](docs/ARCHITECTURE.md)
first to understand what exists today versus what is planned.

## Getting started

1. Fork and clone this repository.
2. Install a JDK 21 (e.g. [Eclipse Temurin](https://adoptium.net) or Microsoft OpenJDK 21).
   No Gradle installation is required — use the checked-in wrapper.
3. Open the repository root in IntelliJ IDEA and let Gradle sync.
4. Build and test: `./gradlew build`.

Full environment details: [docs/DEVELOPMENT.md](docs/DEVELOPMENT.md).

## Ground rules

- **Java 21 is the toolchain target.** Do not add JavaTools or features that demand a newer
  language level without discussing the platform compatibility implications first.
- **Platform APIs stay out of internal modules.** `api` and `core` must not depend on Paper,
  Spigot, or any other platform API. Platform glue belongs in `paper`.
- **Dependencies are versioned centrally** in `gradle/libs.versions.toml`. Never pin a
  dependency inline in a module build file. Prefer zero new dependencies.
- **No dynamic versions.** Every used version must be explicitly stated in the catalog.
- **Never shade platform APIs** into the plugin jar.

## Commit style

Commits follow the [Conventional Commits](https://www.conventionalcommits.org) format:

```
<type>: <short imperative description>

<optional body>
```

Common types: `feat`, `fix`, `build`, `test`, `docs`, `ci`, `refactor`, `chore`, `perf`.

## Pull requests

1. Branch from `main` with a short, descriptive branch name (`feat/announcement-engine`).
2. Keep PRs focused — one change per PR.
3. Run `./gradlew build` locally before pushing; the CI must pass.
4. Describe what changed and why; link related issues.
5. Update [CHANGELOG.md](CHANGELOG.md) under `## [Unreleased]` for user-visible changes.

## Reporting bugs

Open a GitHub issue with:
- The hAnnounce version (from `plugin.yml`, once releases exist).
- The server platform and version (e.g. Paper 1.21.11).
- Steps to reproduce and any relevant console output.

For vulnerabilities use the process in [SECURITY.md](SECURITY.md).
