# Contributing to hAnnounce

Thanks for your interest in the project. Before starting, please read the [README](README.md) and the [architecture notes](docs/ARCHITECTURE.md): the codebase is in its foundation stage and it is important to know what exists and what is still planned.

## Getting started

1. Fork and clone the repository.
2. Install a JDK 21 distribution (for example [Eclipse Temurin](https://adoptium.net) or Microsoft OpenJDK 21). Gradle itself is not required; the wrapper is committed to the repository.
3. Open the repository root in IntelliJ IDEA, or work directly from the command line.
4. Build the project once to confirm the environment works: `./gradlew build`.

Detailed environment setup is covered in the [development guide](docs/DEVELOPMENT.md).

## Development guidelines

- **Java 21 is the toolchain target.** Raising the language level or adding anything that requires a newer Java version must be discussed first, because the plugin has to keep running on Java 21 servers.
- **Platform APIs stay where they belong.** The `api` and `core` modules must not depend on Paper, Spigot or any other platform API. Platform glue is written in the `paper` module.
- **Versions are managed centrally** in `gradle/libs.versions.toml`. Module build files never pin a version inline. Prefer not to add dependencies at all; when one is unavoidable, record it in the catalog with an exact version — dynamic versions are not used.
- **Do not shade platform or server classes into the plugin jar.** The packaging setup already excludes them; keep it that way.

## Commit messages

Commits follow [Conventional Commits](https://www.conventionalcommits.org):

```
<type>: <short imperative description>
```

The types in use are `feat`, `fix`, `build`, `test`, `docs`, `ci`, `refactor`, `perf` and `chore`.

## Pull requests

1. Branch off `main` and use a short, descriptive branch name, for example `feat/settlement-messages`.
2. Keep one change per pull request.
3. Run `./gradlew build` locally before pushing. CI runs the same build and must pass.
4. In the description, state what changed and why, and link any related issues.
5. User-visible changes belong in [CHANGELOG.md](CHANGELOG.md) under `## [Unreleased]`.

## Bug reports

Open a GitHub issue and include the hAnnounce version (from `plugin.yml`, once releases exist), the server platform and version (for example Paper 1.21.11), steps to reproduce and the relevant console output.

Security vulnerabilities follow the process in [SECURITY.md](SECURITY.md) and are not reported through public issues.
