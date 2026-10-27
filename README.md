# hAnnounce

hAnnounce is an announcement plugin for Minecraft servers. Paper is the primary platform and Spigot compatibility is kept wherever no Paper-specific API is required. The project is developed by [hauchdev](https://hauch.dev).

This repository currently contains the **project foundation only**: the Gradle build, the module structure and a minimal, loadable plugin shell. Announcement features, commands and integrations are not implemented yet; development takes place on the `main` branch.

The premium edition is developed separately in the private [hAnnounce-Pro](https://github.com/hauchdev/hAnnounce-Pro) repository. Pro is a **standalone build**: its single plugin jar bundles the public artifacts published from this repository (`api`, `core`) and does not require hAnnounce to be installed on the server.

Because of that packaging, engine code belongs in `core`, with public interfaces in `api`; this repository's `paper` module stays thin glue. See [docs/ARCHITECTURE.md](docs/ARCHITECTURE.md).

## Modules

| Module | Responsibility |
| --- | --- |
| `api` | Public interface of the project. The only package that third-party plugins and hAnnounce Pro may build against. |
| `core` | Shared internal code used by the platform modules. Not part of the public API. |
| `paper` | Paper plugin: entry point, plugin descriptor and the distributable plugin jar. |

## Building

A JDK 21 installation is required. The Gradle wrapper takes care of everything else, so no Gradle installation is needed.

```bash
./gradlew build      # compile, test and package all modules (Windows: .\gradlew.bat build)
./gradlew test       # tests only
```

The plugin jar is written to `paper/build/libs/hAnnounce-Paper-<version>.jar`. The complete environment setup, including the IntelliJ configuration and a local Paper server, is described in the [development guide](docs/DEVELOPMENT.md).

## Project state

Implemented so far:

- Gradle multi-project build with shared conventions: Java 21 toolchain, UTF-8 encoding, JUnit 5, reproducible archives.
- Continuous integration on GitHub Actions for pull requests and pushes to `main`.
- A valid plugin descriptor and a minimal plugin entry point.

Not implemented yet:

- Announcement functionality, commands and scheduling
- Configuration system and message rendering
- Integrations with external services
- A public programming interface for third parties and for Pro
- First publication of the `api` and `core` artifacts (the pipeline is configured, nothing has been published yet)

## License

The project is distributed under the [MIT license](LICENSE).

© 2026 Hauchdev. hAnnounce is not affiliated with Mojang Studios, Microsoft or the Paper project.
