# Changelog

All notable changes to hAnnounce (Free) are documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/), and this
project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html). No release
has been published yet — entries under `Unreleased` describe work on `main`.

## [Unreleased]

### Added

- Gradle multi-project build (`api`, `core`, `paper`) with Kotlin DSL, Gradle wrapper, and
  reproducible archives.
- Shared JVM conventions: Java 21 toolchain, UTF-8 encoding, compiler linting, JUnit 5
  test platform with centrally managed versions.
- Minimal loadable Paper plugin entry point with a validated plugin descriptor
  (`plugin.yml`).
- Plugin jar packaging that bundles internal modules and never shades platform APIs.
- Maven publishing configuration (GitHub Packages) for the public `api` and `core`
  artifacts, plus a dispatch-only publishing workflow. Manual publishing; nothing is
  pushed to any registry automatically.
- Automated build workflow for GitHub Actions.
- Project documentation: architecture notes and development guide.

_Note: No announcement functionality, commands, or product features are implemented yet —
see the README for the planned feature list._
