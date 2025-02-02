# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [v21.3.0-1.21] - 2024-10-28
- Port to Minecraft 1.21.3
### Removed
- Remove `BuiltInEnumFactories::createMinecartType` as the enum class has been replaced by an `EntityType` field and therefore no longer exists
- Remove `ClientBuiltInEnumFactories::createRecipeBookCategory` as the recipe system has been reworked and consequently the enum class no longer exists