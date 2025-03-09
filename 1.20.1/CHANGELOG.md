# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [v20.1.0-1.20.1] - 2025-03-09
- Backport to Minecraft 1.20.1

## [v7.0.1-1.20] - 2023-06-17
### Changed
- Don't require enum values field to be final, some mixin accessor might have removed that flag
### Fixed
- Fixed `RaiderType` factory having incorrect entity type bounds
- Fixed `IllagerSpell` factory not updating an internal enum values by id function

## [v7.0.0-1.20] - 2023-06-10
- Ported to Minecraft 1.20
