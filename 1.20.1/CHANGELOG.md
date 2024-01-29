# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog].

## [v7.0.1-1.20] - 2023-06-17
### Changed
- Don't require enum values field to be final, some mixin accessor might have removed that flag
### Fixed
- Fixed `RaiderType` factory having incorrect entity type bounds
- Fixed `IllagerSpell` factory not updating an internal enum values by id function

## [v7.0.0-1.20] - 2023-06-10
- Ported to Minecraft 1.20

[Keep a Changelog]: https://keepachangelog.com/en/1.0.0/
