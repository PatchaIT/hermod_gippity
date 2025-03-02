<!-- omit from toc -->
# Changelog

All notable changes to this project will be documented in this file.

The format is based on
  [Keep a Changelog](https://keepachangelog.com/en/1.1.0/).  
This project adheres to
  [Semantic Versioning](https://semver.org/spec/v2.0.0.html).  
This document adheres to
  [MarkdownLint Rules](https://github.com/DavidAnson/markdownlint).  

[Go to Table of Contents](#table-of-contents)  
[Return to ReadMe](../../README.md)  
[Return to Docs Index](../index.md)  

## [Unreleased]

### Added - unreleased

* Markdown documentation under `docs`

## [0.0.1] - 2025-02-05

### Added - 0.0.1

* Fully (untested) implementation of a message send
* Fully defined folder structure
* Code and classes implemented adopting a clear Separation of Concerns
* Implemented maven-shade-plugin to generate a custom named executable fat jar
* Implemented a logger
* Implemented Lombok (deprecated use of @Autowire from Snapshot)
* Implemented SonarCloud to check code quality:
  [SonarCloud Project](https://sonarcloud.io/summary/overall?id=PatchaIT_hermod_gippity)
* Full tests coverage on SonarCloud with jUnit5 + Mockito
* A-Class rating on SonarCloud for Reliability, Maintainability and Quality
* Jacoco repots saved in HTML in Dark Mode colors (`target/site/jacoco`)
* Adopted the most recent versions of all dependencies
* GitHub Workflow to share the artifact on Release

### Security - 0.0.1

* A-Class rating on SonarCloud for Security

## [Planned]

### Adding

* Response message features
* (Maybe) A modality simulating a dummy JMS message receiver answering only Ack
* Automated integration tests
* (Dream) Graphic UI mode

## Links

[unreleased](https://github.com/PatchaIT/hermod_gippity/compare/main...hermod_gippity_v0.0.1)  
[0.0.1](https://github.com/PatchaIT/hermod_gippity/releases/tag/v0.0.1)  

<!-- omit from toc -->
## Table of Contents

* [\[Unreleased\]](#unreleased)
  * [Added - unreleased](#added---unreleased)
* [\[0.0.1\] - 2025-02-05](#001---2025-02-05)
  * [Added - 0.0.1](#added---001)
  * [Security - 0.0.1](#security---001)
* [\[Planned\]](#planned)
  * [Adding](#adding)
* [Links](#links)
