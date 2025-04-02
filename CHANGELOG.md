# Changelog

## 0.25.4

* `Versions`:
  * `Ktor`: `3.1.1` -> `3.1.2`
  * `Koin`: `4.0.2` -> `4.0.4`
* `Coroutines`:
  * Add `SmartKeyRWLocker.withWriteLocks` extension with vararg keys
* `Transactions`:
  * Fix order of rollback actions calling

## 0.25.3

* `Coroutines`:
  * Add extensions `SmartRWLocker.alsoWithUnlockingOnSuccessAsync` and `SmartRWLocker.alsoWithUnlockingOnSuccess`
  * Fix of `SmartRWLocker.lockWrite` issue when it could lock write mutex without unlocking
  * Add tool `SmartKeyRWLocker`
  * `SmartSemaphore` got new property `maxPermits`
  * New extension `SmartSemaphore.waitReleaseAll()`
* `Transactions`:
  * Add `TransactionsDSL`

## 0.25.2

* `Versions`:
  * `Exposed`: `0.59.0` -> `0.60.0`
* `Repo`:
  * `Cache`:
    * Add extensions `alsoInvalidate`, `alsoInvalidateAsync`, `alsoInvalidateSync` and `alsoInvalidateSyncLogging`
* `Koin`:
  * Add extensions `singleSuspend` and `factorySuspend` for defining of dependencies with suspendable blocks

## 0.25.1

* `Coroutines`:
  * Add `SortedMapLikeBinaryTreeNode`
* `Pagination`:
  * `Compose`:
    * One more rework of `InfinityPagedComponent` and `PagedComponent`

## 0.25.0

* `Repos`:
  * `Cache`:
    * All cache repos now do not have `open` vals - to avoid collisions in runtime

## 0.24.9

* `Pagination`:
  * Make alternative constructor parameter `size` of `PaginationResult` with default value
  * Add `Pagination.previousPage` extension
  * `Compose`:
    * Rework of `InfinityPagedComponentContext`
    * Rework of `PagedComponent`

## 0.24.8

* `Versions`:
  * `Ktor`: `3.1.0` -> `3.1.1`
  * `KSP`: `2.1.10-1.0.30` -> `2.1.10-1.0.31`
* `Common`:
  * `Compose`:
    * Add component `LoadableComponent`
* `Coroutines`:
  * Add `SortedBinaryTreeNode`
* `Pagination`:
  * `Compose`:
    * Add components `PagedComponent` and `InfinityPagedComponent`

## 0.24.7

* `Versions`:
  * `SQLite`: `3.49.0.0` -> `3.49.1.0`
* `Common`:
  * Add `retryOnFailure` utility for simple retries code writing
* `Repos`:
  * `Cache`:
    * Fix of `FullKeyValueCacheRepo` fields usage
  * `Exposed`:
    * `AbstractExposedKeyValuesRepo` will produce `onValueRemoved` event on `set` if some data has been removed

## 0.24.6

* `Versions`:
  * `KSLog`: `1.4.0` -> `1.4.1`
  * `Exposed`: `0.58.0` -> `0.59.0`
  * `SQLite`: `3.48.0.0` -> `3.49.0.0`
  * `AndroidFragment`: `1.8.5` -> `1.8.6`
* `Coroutines`:
  * Safely functions has been replaced with `Logging` variations (resolve of [#541](https://github.com/InsanusMokrassar/MicroUtils/issues/541))
* `KSP`:
  * `Variations`:
    * Module has been created

## 0.24.5

* `Versions`:
  * `Kotlin`: `2.1.0` -> `2.1.10`
  * `SQLite`: `3.47.2.0` -> `3.48.0.0`
  * `Koin`: `4.0.1` -> `4.0.2`
  * `Android RecyclerView`: `1.3.2` -> `1.4.0`

## 0.24.4

* `Repos`:
  * `Exposed`:
    * Improve `CommonExposedRepo.selectByIds`
* `FSM`:
  * Fixes and improvements

## 0.24.3

* `Ksp`:
  * `Sealed`:
    * Fixes in processing of `GenerateSealedTypesWorkaround` annotations

## 0.24.2

* `Versions`:
  * `Exposed`: `0.57.0` -> `0.58.0`
* `Ksp`:
  * `Sealed`:
    * Add annotation `GenerateSealedTypesWorkaround` which allow to generate `subtypes` lists

## 0.24.1

* `Versions`:
  * `Serialization`: `1.7.3` -> `1.8.0`
  * `SQLite`: `3.47.1.0` -> `3.47.2.0`
  * `Koin`: `4.0.0` -> `3.10.2`
  * `OKio`: `3.9.1` -> `3.10.2`

## 0.24.0

* `Versions`:
  * `Coroutines`: `1.9.0` -> `1.10.1`
  * `KSLog`: `1.3.6` -> `1.4.0`
  * `Compose`: `1.7.1` -> `1.7.3`
  * `Ktor`: `3.0.2` -> `3.0.3`
* `Common`:
  * Rename `Progress` to more common `Percentage`. `Progress` now is typealias
  * Fix of `Progress.compareTo` extension

## 0.23.2

* `Versions`:
  * `Kotlin`: `2.0.21` -> `2.1.0`
  * `Exposed`: `0.56.0` -> `0.57.0`
  * `Xerial SQLite`: `3.47.0.0` -> `3.47.1.0`
  * `Ktor`: `3.0.1` -> `3.0.2`
* `Coroutines`:
  * Small refactor in `AccumulatorFlow` to use `runCatching` instead of `runCatchingSafely`

## 0.23.1

* `Versions`:
  * `Compose`: `1.7.0` -> `1.7.1`
  * `Exposed`: `0.55.0` -> `0.56.0`
  * `Xerial SQLite`: `3.46.1.3` -> `3.47.0.0`
  * `Android CoreKTX`: `1.13.1` -> `1.15.0`
  * `Android Fragment`: `1.8.4` -> `1.8.5`
* `Coroutines`:
  * `Compose`:
    * Add `StyleSheetsAggregator`

## 0.23.0

**THIS UPDATE MAY CONTAINS SOME BREAKING CHANGES (INCLUDING BREAKING CHANGES IN BYTECODE LAYER) RELATED TO UPDATE OF
KTOR DEPENDENCY**

**THIS UPDATE CONTAINS CHANGES ACCORDING TO MIGRATION [GUIDE FROM KTOR](https://ktor.io/docs/migrating-3.html)**

* `Versions`:
  * `Ktor`: `2.3.12` -> `3.0.1`
* `Ktor`:
  * `Common`:
    * Extension `Input.downloadToTempFile` has changed its receiver to `Source`. Its API can be broken
  * `Client`:
    * Extension `HttpClient.tempUpload` has changed type of `onUpload` argument from `OnUploadCallback` to `ProgressListener`
    * All extensions `HttpClient.uniUpload` have changed type of `onUpload` argument from `OnUploadCallback` to `ProgressListener`
  * `Server`:
    * Remove redundant `ApplicationCall.respond` extension due to its presence in the ktor library

## 0.22.9

* `Repos`:
  * `Cache`:
    * Add direct caching repos

## 0.22.8

* `Common`:
  * Add `List.breakAsPairs` extension
  * Add `Sequence.padWith`/`Sequence.padStart`/`Sequence.padEnd` and `List.padWith`/`List.padStart`/`List.padEnd` extensions

## 0.22.7

* `Versions`:
  * `Kotlin`: `2.0.20` -> `2.0.21`
  * `Compose`: `1.7.0-rc01` -> `1.7.0`
* `KSP`:
  * `Sealed`:
    * Change package of `GenerateSealedWorkaround`. Migration: replace `dev.inmo.microutils.kps.sealed.GenerateSealedWorkaround` -> `dev.inmo.micro_utils.ksp.sealed.GenerateSealedWorkaround`

## 0.22.6

* `KSP`:
  * `Generator`:
    * Add extension `KSClassDeclaration.buildSubFileName`
    * Add extension `KSClassDeclaration.companion`
    * Add extension `KSClassDeclaration.resolveSubclasses`
  * `Sealed`:
    * Improvements

## 0.22.5

* `Versions`:
  * `Compose`: `1.7.0-beta02` -> `1.7.0-rc01`
  * `SQLite`: `3.46.1.2` -> `3.46.1.3`
  * `AndroidXFragment`: `1.8.3` -> `1.8.4`
* `Common`:
  * Add extension `withReplacedAt`/`withReplaced` ([#489](https://github.com/InsanusMokrassar/MicroUtils/issues/489))
* `Coroutines`:
  * Add extension `Flow.debouncedBy`
* `Ktor`:
  * `Server`:
    * Add `KtorApplicationConfigurator.Routing.Static` as solution for [#488](https://github.com/InsanusMokrassar/MicroUtils/issues/488)

## 0.22.4

* `Versions`:
  * `Exposed`: `0.54.0` -> `0.55.0`
  * `SQLite`: `3.46.1.0` -> `3.46.1.2`

## 0.22.3

* `Versions`:
  * `Serialization`: `1.7.2` -> `1.7.3`
  * `Coroutines`: `1.8.1` -> `1.9.0`
  * `Compose`: `1.7.0-alpha03` -> `1.7.0-beta02`
  * `Koin`: `3.5.6` -> `4.0.0`
  * `Okio`: `3.9.0` -> `3.9.1`
  * `AndroidFragment`: `1.8.2` -> `1.8.3`
  * `androidx.compose.material3:material3` has been replaced with `org.jetbrains.compose.material3:material3`
* `Common`:
  * `JS`:
    * Add several useful extensions
  * `Compose`:
    * `JS`:
      * Add several useful extensions

## 0.22.2

* `Versions`:
    * `Exposed`: `0.53.0` -> `0.54.0`
    * `SQLite`: `3.46.0.1` -> `3.46.1.0`

## 0.22.1

* `Versions`:
    * `Kotlin`: `2.0.10` -> `2.0.20`
    * `Serialization`: `1.7.1` -> `1.7.2`
    * `KSLog`: `1.3.5` -> `1.3.6`
    * `Compose`: `1.7.0-alpha02` -> `1.7.0-alpha03`
    * `Ktor`: `2.3.11` -> `2.3.12`

## 0.22.0

**THIS UPDATE CONTAINS BREAKING CHANGES ACCORDING TO UPDATE UP TO KOTLIN 2.0**

* `Versions`:
    * `Kotlin`: `1.9.23` -> `2.0.10`
    * `Serialization`: `1.6.3` -> `1.7.1`
    * `KSLog`: `1.3.4` -> `1.3.5`
    * `Compose`: `1.6.2` -> `1.7.0-alpha02`
    * `Exposed`: `0.50.1` -> `0.53.0`
    * `AndroidAppCompat`: `1.6.1` -> `1.7.0`
    * `AndroidFragment`: `1.7.1` -> `1.8.2`

## 0.21.6

* `KSP`:
    * `Sealed`:
        * Fixes in generation

## 0.21.5

* `KSP`:
    * Add utility functions `KSClassDeclaration.findSubClasses`
    * `Sealed`:
        * Improve generation

## 0.21.4

* `Common`:
    * `Compose`:
        * Add support of mingw, linux, arm64 targets
* `Coroutines`:
    * `Compose`:
        * Add support of mingw, linux, arm64 targets
* `Koin`:
    * Add support of mingw, linux, arm64 targets
* `KSP`:
    * `ClassCasts`:
        * Add support of mingw, linux, arm64 targets
    * `Sealed`:
        * Add support of mingw, linux, arm64 targets

## 0.21.3

* `Colors`:
    * Added as a module. It should be used by default in case you wish all the API currently realized for `HEXAColor`
* `Coroutines`:
    * Fix of [#374](https://github.com/InsanusMokrassar/MicroUtils/issues/374):
        * Add vararg variants of `awaitFirst`
        * Add `joinFirst`

## 0.21.2

* `KSP`:
    * `ClassCasts`:
        * Module has been initialized

## 0.21.1

* `KSP`:
    * Module has been initialized
    * `Generator`:
        * Module has been initialized
    * `Sealed`:
        * Module has been initialized

## 0.21.0

**THIS UPDATE CONTAINS BREAKING CHANGES IN `safely*`-ORIENTED FUNCTIONS**

* `Coroutines`:
    * **All `safely` functions lost their `supervisorScope` in favor to wrapping `runCatching`**
        * `runCatchingSafely` is the main handling function of all `safely` functions
        * `launchSafely*` and `asyncSafely*` blocks lost `CoroutineScope` as their receiver

## 0.20.52

* `Coroutines`:
    * Small rework of weak jobs: add `WeakScope` factory, rename old weal extensions and add kdocs

## 0.20.51

* `Versions`:
    * `Android Fragment`: `1.7.0` -> `1.7.1`
* `Pagination`:
    * Add `Pagination.nextPageIfTrue` and `Pagination.thisPageIftrue` extensions to get the page according to condition
    pass status
    * Add `PaginationResult.nextPageIfNotEmptyOrLastPage` and `PaginationResult.thisPageIfNotEmptyOrLastPage`
    * Change all `doForAll` and `getAll` extensions fo pagination to work basing on `nextPageIfNotEmptyOrLastPage` and
    `thisPageIfNotEmptyOrLastPage`

## 0.20.50

* `Versions`:
    * `Coroutines`: `1.8.0` -> `1.8.1`
    * `KSLog`: `1.3.3` -> `1.3.4`
    * `Exposed`: `0.50.0` -> `0.50.1`
    * `Ktor`: `2.3.10` -> `2.3.11`
* A lot of inline functions became common functions due to inline with only noinline callbacks in arguments leads to
low performance
* `Coroutines`:
    * `SmartMutex`, `SmartSemaphore` and `SmartRWLocker` as their user changed their state flow to `SpecialMutableStateFlow`

## 0.20.49

* `Repos`:
    * `Common`:
        * All `Repo`s get `diff` extensions
        * `KeyValueRepo` and `KeyValuesRepo` get `applyDiff` extension
        * Add new extensions `on*` flows for crud repos

## 0.20.48

* `Versions`:
    * `Android Core KTX`: `1.13.0` -> `1.13.1`
    * `AndroidX Fragment`: `1.6.2` -> `1.7.0`

## 0.20.47

* `Versions`:
    * `Exposed`: `0.49.0` -> `0.50.0`

## 0.20.46

* `Common`:
    * Now this repo depends on `klock`
    * Add new object-serializer `DateTimeSerializer` for `klock` serializer

## 0.20.45

* `Versions`:
    * `Android Core KTX`: `1.12.0` -> `1.13.0`

## 0.20.44

* `Versions`:
    * `Compose`: `1.6.1` -> `1.6.2`
    * `Koin`: `3.5.4` -> `3.5.6`

## 0.20.43

* `Versions`:
    * `Ktor`: `2.3.9` -> `2.3.10`
    * `Koin`: `3.5.3` -> `3.5.4`

## 0.20.42

* `Repos`:
    * `Generator`:
        * Improvements

## 0.20.41

* `Repos`:
    * `Exposed`:
        * `AbstractExposedKeyValueRepo`, `ExposedKeyValueRepo`, `AbstractExposedKeyValuesRepo`, `ExposedKeyValuesRepo` got opportunity to setup some part of their flows

## 0.20.40

* `Versions`:
    * `KSLog`: `1.3.2` -> `1.3.3`
    * `Exposed`: `0.48.0` -> `0.49.0`
    * `UUID`: `0.8.2` -> `0.8.4`

## 0.20.39

* `Versions`:
    * `Kotlin`: `1.9.22` -> `1.9.23`
    * `Korlibs`: `5.3.2` -> `5.4.0`
    * `Okio`: `3.8.0` -> `3.9.0`
    * `Compose`: `1.6.0` -> `1.6.1`
    * `ComposeMaterial3`: `1.2.0` -> `1.2.1`

## 0.20.38

* `Versions`:
    * `Ktor`: `2.3.8` -> `2.3.9`

## 0.20.37

* `Versions`:
    * `Compose`: `1.5.12` -> `1.6.0`
    * `Exposed`: `0.47.0` -> `0.48.0`

## 0.20.36

* `Versions`:
    * `Serialization`: `1.6.2` -> `1.6.3`
    * `Korlibs`: `5.3.1` -> `5.3.2`
* `Repos`:
    * `Cache`:
        * Improve work and functionality of `actualizeAll` and subsequent functions
        * All internal repos `invalidate`/`actualizeAll` now use common `actualizeAll` functions

## 0.20.35

* `Versions`:
    * `Coroutines`: `1.7.3` -> `1.8.0`
    * `Material3`: `1.1.2` -> `1.2.0`

## 0.20.34

* `Repos`:
    * `Common`:
        * Improve default `set` realization of `KeyValuesRepo`

## 0.20.33

* `Colors`
    * `Common`:
        * Add opportunity to use `HEXAColor` with `ahex` colors

## 0.20.32

* `Versions`:
    * `Okio`: `3.7.0` -> `3.8.0`
* `Resources`:
    * Make `StringResource` serializable
    * Add several variants of builder usages

## 0.20.31

* `Versions`:
    * `Ktor`: `2.3.7` -> `2.3.8`

## 0.20.30

* `Versions`:
    * `Exposed`: `0.46.0` -> `0.47.0`

## 0.20.29

* `Versions`:
    * `Kotlin`: `1.9.21` -> `1.9.22`
    * `Compose`: `1.5.11` -> `1.5.12`
    * `Korlibs`: `5.3.0` -> `5.3.1`

## 0.20.28

* `Versions`:
    * `Kotlin`: `1.9.22` -> `1.9.21` (downgrade)
    * `Compose`: `1.6.0-dev13691` -> `1.5.11` (downgrade)

## 0.20.27

* `Versions`:
    * `Kotlin`: `1.9.21` -> `1.9.22`
    * `Compose`: `1.5.11` -> `1.6.0-dev13691`

## 0.20.26

* `Versions`:
    * `Exposed`: `0.45.0` -> `0.46.0`. **This update brinds new api deprecations in exposed**
* `Resources`:
    * Add opportunity to get default translation by passing `null` as `IetfLang` argument
    * Add several useful extensions to get translations in `JS` target

## 0.20.25

* `Colors`:
    * `Common`:
        * Module inited

## 0.20.24

**Since this version depdendencies of klock and krypto replaced with `com.soywiz.korge:korlibs-time` and `com.soywiz.korge:korlibs-crypto`**

* `Versions`:
    * `Klock` (since now `KorlibsTime`): `4.0.10` -> `5.3.0`
    * `Krypto` (since now `KorlibsCrypto`): `4.0.10` -> `5.3.0`
* `Serialization`:
    * `Mapper`:
        * `Mapper` pass decoder into callback of deserialization strategy
        * `Mapper` pass encoder into callback of serialization strategy

## 0.20.23

* `Versions`:
    * `Koin`: `3.5.0` -> `3.5.3`
    * `Okio`: `3.6.0` -> `3.7.0`
* `LanguageCodes`:
    * Fixes in intermediate language codes (like `Chinese.Hans`)
    * Rename `IetfLanguageCode` to `IetfLang`
        * Rename all subsequent functions (including serializer)
    * New lazy properties `knownLanguageCodesMap`, `knownLanguageCodesMapByLowerCasedKeys` and several others

## 0.20.22

* `Common`:
    * Add opportunity to create own `Diff` with base constructor

## 0.20.21

* `Resources`:
    * Inited

## 0.20.20

* `Repos`:
    * `Exposed`:
        * Add opportunity for setup flows in `AbstractExposedCRUDRepo`

## 0.20.19

* `Versions`:
    * `Ktor`: `2.3.6` -> `2.3.7`

## 0.20.18

* `Coroutines`:
    * `SpecialMutableStateFlow` now extends `MutableStateFlow`
    * `Compose`:
        * Deprecate `FlowState` due to its complexity in fixes

## 0.20.17

* `Versions`:
    * `Serialization`: `1.6.1` -> `1.6.2`

## 0.20.16

* `Versions`:
    * `Exposed`: `0.44.1` -> `0.45.0`
* `Coroutines`:
    * Add `SpecialMutableStateFlow`
    * `Compose`:
        * Add `FlowState`

## 0.20.15

* `Versions`:
    * `Kotlin`: `1.9.20` -> `1.9.21`
    * `KSLog`: `1.3.0` -> `1.3.1`
    * `Compose`: `1.5.10` -> `1.5.11`

## 0.20.14

* `Versions`:
    * `Serialization`: `1.6.0` -> `1.6.1`
    * `KSLog`: `1.2.4` -> `1.3.0`

## 0.20.13

* `Versions`:
    * `Ktor`: `2.3.5` -> `2.3.6`
    * `UUID`: `0.8.1` -> `0.8.2`

## 0.20.12

**It is experimental migration onto new gradle version. Be careful in use of this version**

**This update have JDK 17 in `compatibility` and `target` versions**

## 0.20.11

* `Versions`:
    * `Kotlin`: `1.9.20-RC2` -> `1.9.20`
    * `Exposed`: `0.44.0` -> `0.44.1`
    * `Compose`: `1.5.10-rc02` -> `1.5.10`
* `Coroutines`:
    * `SmartRWLocker` now will wait first unlock of write mutex for acquiring read

## 0.20.10

* `Versions`:
    * `Kotlin`: `1.9.20-RC` -> `1.9.20-RC1`
    * `KSLog`: `1.2.1` -> `1.2.2`
    * `Compose`: `1.5.10-rc01` -> `1.5.10-rc02`
    * `RecyclerView`: `1.3.1` -> `1.3.2`

## 0.20.9

* Most of common modules now supports `linuxArm64` target

## 0.20.8

**THIS VERSION CONTAINS UPDATES OF DEPENDENCIES UP TO RC VERSIONS. USE WITH CAUTION**

* `Versions`:
    * `Kotlin`: `1.9.20-Beta2` -> `1.9.20-RC`
    * `Compose`: `1.5.10-beta02` -> `1.5.10-rc01`

## 0.20.7

**THIS VERSION CONTAINS UPDATES OF DEPENDENCIES UP TO BETA VERSIONS. USE WITH CAUTION**

* `Versions`:
    * `Kotlin`: `1.9.10` -> `1.9.20-Beta2`
    * `Compose`: `1.5.1` -> `1.5.10-beta02`
    * `Exposed`: `0.43.0` -> `0.44.0`
    * `Ktor`: `2.3.4` -> `2.3.5`
    * `Koin`: `3.4.3` -> `3.5.0`
    * `Okio`: `3.5.0` -> `3.6.0`
    * `Android Core`: `1.10.1` -> `1.12.0`
    * `Android Compose Material`: `1.1.1` -> `1.1.2`

## 0.20.6

* `Repos`:
    * `Exposed`
        * Fixes in exposed key-values repos

## 0.20.5

* `Coroutines`:
    * Fixes in `SmartRWLocker`

## 0.20.4

* `Versions`:
    * `Kotlin`: `1.9.0` -> `1.9.10`
    * `KSLog`: `1.2.0` -> `1.2.1`
    * `Compose`: `1.5.0` -> `1.5.1`
    * `UUID`: `0.8.0` -> `0.8.1`

## 0.20.3

* `Versions`:
    * `Compose`: `1.4.3` -> `1.5.0`
    * `Exposed`: `0.42.1` -> `0.43.0`
    * `Ktor`: `2.3.3` -> `2.3.4`
* `Repos`:
    * `Cache`:
        * Fixes in locks of caches

## 0.20.2

* All main repos uses `SmartRWLocker`
* `Versions`:
    * `Serialization`: `1.5.1` -> `1.6.0`
    * `Exposed`: `0.42.0` -> `0.42.1`
    * `Korlibs`: `4.0.9` -> `4.0.10`
* `Androis SDK`: `33` -> `34`

## 0.20.1

* `SmallTextField`:
    * Module is initialized
* `Pickers`:
    * Module is initialized
* `Coroutines`:
    * Add `SmartSemaphore`
    * Add `SmartRWLocker`

## 0.20.0

* `Versions`:
    * `Kotlin`: `1.8.22` -> `1.9.0`
    * `KSLog`: `1.1.1` -> `1.2.0`
    * `Exposed`: `0.41.1` -> `0.42.0`
    * `UUID`: `0.7.1` -> `0.8.0`
    * `Korlibs`: `4.0.3` -> `4.0.9`
    * `Ktor`: `2.3.2` -> `2.3.3`
    * `Okio`: `3.4.0` -> `3.5.0`

## 0.19.9

* `Versions`:
    * `Koin`: `3.4.2` -> `3.4.3`
* `Startup`:
    * Now it is possible to start application in synchronous way

## 0.19.8

* `Versions`:
    * `Coroutines`: `1.7.2` -> `1.7.3`
    * `Kotlin`: `1.8.20` -> `1.8.22`
    * `Compose`: `1.4.1` -> `1.4.3`
    * `Okio`: `3.3.0` -> `3.4.0`
    * `RecyclerView`: `1.3.0` -> `1.3.1`
    * `Fragment`: `1.6.0` -> `1.6.1`
* `Repos`:
    * Fixes In `KeyValueRepo.clear()` of almost all inheritors of `KeyValueRepo`
    * `Cache`:
        * All full caches got `skipStartInvalidate` property. By default, this property is `false` and fully caching repos
          will be automatically invalidated on start of their work

## 0.19.7

* `Versions`:
    * `Coroutines`: `1.7.1` -> `1.7.2`

## 0.19.6

* `Versions`:
    * `Coroutines`: `1.6.4` -> `1.7.1`
    * `Ktor`: `2.3.1` -> `2.3.2`
    * `Compose`: `1.4.0` -> `1.4.1`

## 0.19.5

* `Repos`:
    * `Generator`:
        * Fixes in new type generation

## 0.19.4

* `Versions`:
    * `Koin`: `3.4.1` -> `3.4.2`
    * `Android Fragments`: `1.5.7` -> `1.6.0`
* `Koin`
    * `Generator`
        * Fixes in new generic generator part

## 0.19.3

* `Koin`
    * `Generator`
        * New getter methods now available with opportunity to use parameters
        * Old notation `*Single` and `*Factory` is deprecated since this release. With old
          will be generated new `single*` and `factory*` notations for new generations
        * Add opportunity to use generic-oriented koin definitions

## 0.19.2

* `Versions`:
    * `Ktor`: `2.3.0` -> `2.3.1`
    * `Koin`: `3.4.0` -> `3.4.1`
    * `Uuid`: `0.7.0` -> `0.7.1`

## 0.19.1

* `Versions`:
    * `Korlibs`: `4.0.1` -> `4.0.3`
    * `Kotlin Poet`: `1.13.2` -> `1.14.0`

## 0.19.0

* `Versions`:
    * `Korlibs`: `3.4.0` -> `4.0.1`

## 0.18.4

* `Koin`:
    * New extension `lazyInject`

## 0.18.3

* `Versions`:
    * `Serialization`: `1.5.0` -> `1.5.1`
    * `Android Cor Ktx`: `1.10.0` -> `1.10.1`

## 0.18.2

* `Startup`:
    * Now internal `Json` is fully customizable

## 0.18.1

* `Common`:
    * Add `MapDiff`
* `Coroutines`:
    * Add `SmartMutex`

## 0.18.0

**ALL PREVIOUSLY DEPRECATED FUNCTIONALITY HAVE BEEN REMOVED**

* `Versions`:
    * `Android Fragments`: `1.5.6` -> `1.5.7`
* `Ktor`:
    * `Server`:
        * Now it is possible to take query parameters as list
* `Repos`:
    * `Common`:
        * New `WriteKeyValuesRepo.removeWithValue`
    * `Cache`:
        * Rename full caching factories functions to `fullyCached`

## 0.17.8

* `Versions`:
    * `Ktor`: `2.2.4` -> `2.3.0`

## 0.17.7

* `Versions`:
    * `Android CoreKtx`: `1.9.0` -> `1.10.0`
* `Startup`:
    * Add support of `linuxX64` and `mingwX64` platforms

## 0.17.6

* `Versions`:
    * `Kotlin`: `1.8.10` -> `1.8.20`
    * `KSLog`: `1.0.0` -> `1.1.1`
    * `Compose`: `1.3.1` -> `1.4.0`
    * `Koin`: `3.3.2` -> `3.4.0`
    * `RecyclerView`: `1.2.1` -> `1.3.0`
    * `Fragment`: `1.5.5` -> `1.5.6`
* Experimentally (`!!!`) add `linuxX64` and `mingwX64` targets

## 0.17.5

* `Common`:
    * Conversations of number primitives with bounds care
* `Repos`:
    * `Common`:
        * By default, `getAll` for repos will take all the size of repo as page size
        * New extension for all built-in repos `maxPagePagination`
    * All the repos got `getAll` functions

## 0.17.4

* `Serialization`:
    * `Mapper`:
        * Module inited
* `Versions`:
    * `Compose`: `1.3.1-rc02` -> `1.3.1`

## 0.17.3

* `Common`:
    * Add `fixed` extensions for `Float` and `Double`
    * New function `emptyDiff`
    * Now you may pass custom `comparisonFun` to all `diff` functions

## 0.17.2

* `FSM`:
    * `DefaultStatesManager.onUpdateContextsConflictResolver` and `DefaultStatesManager.onStartContextsConflictResolver` now return `false` by default

## 0.17.1

* **Hotfix** for absence of jvm dependencies in android modules
* `Versions`:
    * `Ktor`: `2.2.3` -> `2.2.4`

## 0.17.0

* `Versions`:
    * `Kotlin`: `1.7.20` -> `1.8.10`
    * `Serialization`: `1.4.1` -> `1.5.0`
    * `KSLog`: `0.5.4` -> `1.0.0`
    * `AppCompat`: `1.6.0` -> `1.6.1`

## 0.16.13

* `Repos`:
    * `Generator`:
        * Module has been created

## 0.16.12

* `Repos`:
    * `Exposed`:
        * `CommonExposedRepo.selectByIds` uses `foldRight` by default instead of raw foreach
* `Koin`:
    * `Generator`:
        * Module has been created

## 0.16.11

* `LanguageCodes`:
    * In android and JVM targets now available `toJavaLocale` and from Java `Locale` conversations from/to
      `IetfLanguageCode`

## 0.16.10

* `Repos`:
    * `Cache`:
        * New transformer type: `ReadCRUDFromKeyValueRepo`
        * New transformer type: `ReadKeyValueFromCRUDRepo`
* `Pagination`:
    * New `paginate` extensions with `reversed` support for `List`/`Set`

## 0.16.9

* `Versions`:
    * `Koin`: `3.2.2` -> `3.3.2`
    * `AppCompat`: `1.5.1` -> `1.6.0`
* `Ktor`:
    * `Client`
        * `HttpResponse.bodyOrNull` now retrieve callback to check if body should be received or null
        * New extension `HttpResponse.bodyOrNullOnNoContent`

## 0.16.8

* `Versions`:
    * `Ktor`: `2.2.2` -> `2.2.3`
* `Ktor`:
    * `Client`
        * Fixes in `HttpClient.uniUpload`
    * `Server`
        * Fixes in `PartData.FileItem.download`
* `Repos`:
    * `Cache`:
        * New type of caches: `FallbackCacheRepo`
        * Fixes in `Write*` variants of cached repos
        * New type `ActionWrapper`
        * New `AutoRecache*` classes for all types of repos as `FallbackCacheRepo`s
    * `Common`:
        * New transformations for key-value and key-values vice-verse
        * Fixes in `FileReadKeyValueRepo`

## 0.16.7

* `Common`:
    * New extensions `ifTrue`/`ifFalse`/`alsoIfTrue`/`alsoIfFalse`/`letIfTrue`/`letIfFalse`
    * `Diff` now is serializable
    * Add `IndexedValue` serializer
    * `repeatOnFailure` extending: now you may pass any lambda to check if continue to try/do something
    * `Compose`:
        * New extension `MutableState.asState`
* `Coroutines`:
    * `Compose`:
        * All the `Flow` conversations to compose `State`/`MutableState`/`SnapshotStateList`/`List` got several new
        parameters
        * `Flow.toMutableState` now is deprecated in favor to `asMutableComposeState`
* `Repos`:
    * `Cache`:
        * New type `FullCacheRepo`
        * New type `CommonCacheRepo`
        * `CacheRepo` got `invalidate` method. It will fully reload `FullCacheRepo` and just clear `CommonCacheRepo`
        * New extensions `KVCache.actualizeAll`

## 0.16.6

* `Startup`:
    * `Launcher`:
        * Improvements in `StartLauncherPlugin#start` methods
        * Add opportunity to pass second argument on `JVM` platform as log level
* `Repos`:
    * `Ktor`:
        * `Client`:
            * All clients repos got opportunity to customize their flows
    * `Exposed`:
        * Extensions `eqOrIsNull` and `neqOrIsNotNull` for `Column`

## 0.16.5

* `Versions`:
    * `Ktor`: `2.2.1` -> `2.2.2`

## 0.16.4

* `Coroutines`:
    * Create `launchInCurrentThread`

## 0.16.3

* `Startup`:
    * `Launcher`:
        * All starting API have been moved into `StartLauncherPlugin` and do not require serialize/deserialize cycle for now

## 0.16.2

* `Versions`:
    * `Compose`: `1.2.1` -> `1.2.2`
* `Startup`:
    * Module become available on `JS` target

## 0.16.1

* `Coroutines`:
    * New `runCatchingSafely`/`safelyWithResult` with receivers
* `SafeWrapper`:
    * Module inited

## 0.16.0

* `Versions`:
    * `Ktor`: `2.1.3` -> `2.2.1`
    * `Android Fragment`: `1.5.3` -> `1.5.5`

## 0.15.1

* `Startup`:
    * Inited :)
    * `Plugin`:
        * Inited :)
    * `Launcher`:
        * Inited :)

## 0.15.0

* `Repos`:
    * `CRUD`:
        * `Common`:
            * New method `ReadCRUDRepo#getIdsByPagination`
        * `Android`:
            * `AbstractAndroidCRUDRepo` got new abstract method `toId`
        * `Exposed`:
            * `CommonExposedRepo` new abstract property `asId`
        * `Ktor`:
            * `Client`:
                * `KtorReadCRUDRepoClient` now requires `paginationIdType`
* `LanguageCodes`:
    * Updates and fixes in generation
* `MimeTypes`:
    * Updates and fixes in generation

## 0.14.4

* `Common`:
    * `JVM`:
        * New extension `downloadToTempFile`
* `Ktor`:
    * `Server`:
        * Small fix in `handleUniUpload`
        * `ApplicationCall#uniloadMultipartFile` now uses `uniloadMultipart`
    * `Common`:
        * New extension `downloadToTempFile`
    * `Client`:
        * New extensions on top of `uniUpload`

## 0.14.3

* `Common`:
    * New type `Progress`
* `Ktor`:
    * `Client`:
        * New universal `uniUpload` extension for `HttpClient`
    * `Server`:
        * New universal `handleUniUpload` extension for `ApplicationCall`
        * Add extensions `download` and `downloadToTemporalFile`

## 0.14.2

* `Versions`:
    * `Exposed`: `0.40.1` -> `0.41.1`


## 0.14.1

* `Versions`:
    * `Klock`: `3.3.1` -> `3.4.0`
    * `UUID`: `0.5.0` -> `0.6.0`

## 0.14.0

**ALL DEPRECATIONS HAVE BEEN REMOVED**

* `Versions`:
    * `Kotlin`: `1.7.10` -> `1.7.20`
    * `Klock`: `3.3.0` -> `3.3.1`
    * `Compose`: `1.2.0` -> `1.2.1`
    * `Exposed`: `0.39.2` -> `0.40.1`

## 0.13.2

* `Versions`:
    * `Klock`: `3.1.0` -> `3.3.0`
    * `Ktor`: `2.1.2` -> `2.1.3`

## 0.13.1

* `Repos`:
    * `Exposed`:
      * `AbstractExposedWriteCRUDRepo#createAndInsertId` now is optional and returns nullable value

## 0.13.0

**ALL DEPRECATIONS HAVE BEEN REMOVED**
**A LOT OF KTOR METHODS RELATED TO UnifierRouter/UnifiedRequester HAVE BEEN REMOVED**

* `Repos`:
  * `Exposed`:
    * `AbstractExposedWriteCRUDRepo` got two new methods: `update` with `it` as `UpdateBuilder<Int>` and `createAndInsertId`
      * Old `update` method has been deprecated and not recommended to override anymore in realizations
      * Old `insert` method now is `open` instead of `abstract` and can be omitted
    * `AbstractExposedKeyValueRepo` got two new methods: `update` with `it` as `UpdateBuilder<Int>` and `insertKey`
      * Old `update` method has been deprecated and not recommended to override anymore
      * Old `insert` method now is `open` instead of `abstract` and can be omitted in realizations

## 0.12.17

* `Versions`:
    * `JB Compose`: `1.2.0-alpha01-dev774` -> `1.2.0-beta02`
    * `Ktor`: `2.1.1` -> `2.1.2`
    * `Koin`: `3.2.0` -> `3.2.2`

## 0.12.16

* `Coroutines`:
  * `Android`:
    * Add class `FlowOnHierarchyChangeListener`
    * Add `ViewGroup#setOnHierarchyChangeListenerRecursively(OnHierarchyChangeListener)`

## 0.12.15

* `Common`:
    * `applyDiff` will return `Diff` object since this release
    * `Android`:
      * New functions/extensions `findViewsByTag` and `findViewsByTagInActivity`
* `Coroutines`:
    * Add `Flow` extensions `flatMap`, `flatMapNotNull` and `flatten`
    * Add `Flow` extensions `takeNotNull` and `filterNotNull`

## 0.12.14

* `Versions`:
  * `Android CoreKTX`: `1.8.0` -> `1.9.0`
  * `Android AppCompat`: `1.4.2` -> `1.5.1`
  * Android Compile SDK: 32 -> 33
  * Android Build Tools: 32.0.0 -> 33.0.0
* `Common`:
  * `Android`:
    * Add `argumentOrNull`/`argumentOrThrow` delegates for fragments
* `Coroutines`:
  * Rewrite `awaitFirstWithDeferred` onto `CompletableDeferred` instead of coroutines suspending

## 0.12.13

* `Coroutines`:
  * Add opportunity to use markers in actors (solution of [#160](https://github.com/InsanusMokrassar/MicroUtils/issues/160))
* `Koin`:
  * Module inited :)
* `Repos`:
  * `Android`:
    * Add typealias `KeyValueSPRepo` and opportunity to create shared preferences `KeyValue` repo with `KeyValueStore(...)` (fix of [#155](https://github.com/InsanusMokrassar/MicroUtils/issues/155))

## 0.12.12

* `Common`:
  * `Compose`:
    * `JS`:
      * Add `SkeletonAnimation` stylesheet

## 0.12.11

* `Repos`:
    * `Cache`:
        * Override `KeyValue` cache method `values`

## 0.12.10

* `Repos`:
    * `Cache`:
        * Hotfix in key values `get`

## 0.12.9

* `Versions`:
  * `Klock`: `3.0.0` -> `3.1.0`
* `Repos`:
    * `Cache`:
      * Fixes in key values cache

## 0.12.8

* `Versions`:
    * `Ktor`: `2.1.0` -> `2.1.1`
    * `Compose`: `1.2.0-alpha01-dev764` -> `1.2.0-alpha01-dev774`
* `Ktor`:
  * `Client`:
    * New extension `HttpClient#bodyOrNull` which returns `null` in case when server responded with `No Content` (204)
  * `Server`:
    * New extension `ApplicationCall#respondOrNoContent` which responds `No Content` (204) when passed data is null

## 0.12.7

* `Repos`:
  * `Cache`:
    * Force `WriteCRUDCacheRepo` to subscribe on new and updated objects of parent repo
* `Pagination`:
  * New function `changeResultsUnchecked(Pagination)`

## 0.12.6

* `MimeeTypes>`:
  * Fixed absence of `image/*` in known mime types

## 0.12.5

* `Repos`:
  * `Exposed`:
    * Fixes in `paginate` extensions

## 0.12.4

* `Versions`:
    * `Kotlin`: `1.7.0` -> `1.7.10`
    * `Compose`: `1.2.0-alpha01-dev755` -> `1.2.0-alpha01-dev764`

## 0.12.3

* `Repos`:
  * `Exposed`:
    * Add abstract exposed variants of `KeyValue` and `KeyValues` repos
    * Add new extension `Query#selectPaginated`

## 0.12.2

* `Versions`:
  * `Serialization`: `1.4.0-RC` -> `1.4.0`
  * `Compose`: `1.2.0-alpha01-dev753` -> `1.2.0-alpha01-dev755`

## 0.12.1

* `Versions`:
  * `Ktor`: `2.0.3` -> `2.1.0`

## 0.12.0

**OLD DEPRECATIONS HAVE BEEN REMOVED**

**MINIMAL ANDROID API HAS BEEN ENLARGED UP TO API 21 (Android 5.0)**

* `Versions`
  * `Kotlin`: `1.6.21` -> `1.7.0`
  * `Coroutines`: `1.6.3` -> `1.6.4`
  * `Exposed`: `0.38.2` -> `0.39.2`
  * `Compose`: `1.2.0-alpha01-dev729` -> `1.2.0-alpha01-dev753`
  * `Klock`: `2.7.0` -> `3.0.0`
  * `uuid`: `0.4.1` -> `0.5.0`
  * `Android Core KTX`: `1.7.0` -> `1.8.0`
  * `Android AppCompat`: `1.4.1` -> `1.4.2`
* `Ktor`:
  * All previously standard functions related to work with binary data by default have been deprecated

## 0.11.14

* `Pagination`:
    * `PaginationResult` got new field `objectsNumber` which by default is a times between `pagesNumber` and `size`

## 0.11.13

* `Versions`:
    * `Coroutines`: `1.6.3` -> `1.6.4`
    * `Compose`: `1.2.0-alpha01-dev629` -> `1.2.0-alpha01-dev731`

## 0.11.12

* `Repos`:
  * `Common`:
    * `JVM`:
      * Fixes in `ReadFileKeyValueRepo` methods (`values`/`keys`)

## 0.11.11

* `Crypto`:
  * `hmacSha256` has been deprecated
* `Ktor`:
  * `Client`:
    * `BodyPair` has been deprecated
* `Repos`:
    * `Cache`:
      * New interface `CacheRepo`
      * New interface `FullCacheRepo`
      * `actualize*` methods inside of full cache repos now open for overriding

## 0.11.10

* `Repos`:
  * `Cache`:
    * `KVCache` has been replaced to the package `dev.inmo.micro_utils.repos.cache`
    * `SimpleKVCache` has been replaced to the package `dev.inmo.micro_utils.repos.cache`
    * New `KVCache` subtype - `FullKVCache`
    * Add `Full*` variants of standard repos
    * Add `cached`/`caching` (for write repos) extensions for all standard types of repos

## 0.11.9

* `Versions`
    * `Coroutines`: `1.6.1` -> `1.6.3`
    * `Ktor`: `2.0.2` -> `2.0.3`
    * `Compose`: `1.2.0-alpha01-dev686` -> `1.2.0-alpha01-dev729`

## 0.11.8

* `Repos`:
    * `Common`:
        * Fixes in `FileKeyValueRepo`

## 0.11.7

* `Common`:
  * New abstractions `SimpleMapper` and `SimpleSuspendableMapper`
* `Repos`:
  * `Common`:
    * Add mappers for `CRUDRepo`

## 0.11.6

* `FSM`:
  * `Common`
    * Several fixes related to the jobs handling

## 0.11.5

* `Coroutines`:
  * `Compose`:
    * Add extension `StateFlow#asMutableComposeListState` and `StateFlow#asComposeList`
    * Add extension `StateFlow#asMutableComposeState`/`StateFlow#asComposeState`

## 0.11.4

**THIS VERSION HAS BEEN BROKEN, DO NOT USE IT**

## 0.11.3

* `Ktor`:
    * Support of `WebSockets` has been improved
      * `Client`:
        * New extensions: `HttpClient#openBaseWebSocketFlow`, `HttpClient#openWebSocketFlow`, `HttpClient#openSecureWebSocketFlow`

## 0.11.2

* `Ktor`:
  * Support of `WebSockets` has been improved and added fixes inside of clients

## 0.11.1

* `Repos`
  * `Ktor`
    * In `configureReadKeyValueRepoRoutes` and `configureReadKeyValuesRepoRoutes` configurators fixed requiring of `reversed` property

## 0.11.0

* `Versions`
    * `UUID`: `0.4.0` -> `0.4.1`
* `Ktor`
  * `Client`:
    * New extension fun `HttpResponse#throwOnUnsuccess`
    * All old functions, classes and extensions has been rewritten with new ktor-way with types info and keeping `ContentNegotiation` in mind
  * `Server`:
      * All old functions, classes and extensions has been rewritten with new ktor-way with types info and keeping `ContentNegotiation` in mind
* `Repos`
  * `Ktor`:
    * Fully rewritten work with all declared repositories
    * All old functions, classes and extensions has been rewritten with new ktor-way with types info and keeping `ContentNegotiation` in mind

## 0.10.8

* `Common`
    * Add `Element.isOverflow*` extension properties

## 0.10.7

* `Pagination`:
    * Now it is possible to use `doForAll*` and `getForAll` functions in non suspend places

## 0.10.6

* `Versions`
    * `Ktor`: `2.0.1` -> `2.0.2`
* `Common`
    * `JS`:
        * Add `ResizeObserver` functionality

## 0.10.5

* `Versions`
    * `Compose`: `1.2.0-alpha01-dev683` -> `1.2.0-alpha01-dev686`
* `Repos`
    * `Android`:
        * New function `SharedPreferencesKeyValueRepo`
* `FSM`
    * Add `StateHandlingErrorHandler` and opportunity to handle states handling errors

## 0.10.4

* `Versions`:
    * `Serialization`: `1.3.2` -> `1.3.3`

## 0.10.3

* `Versions`:
    * `Compose`: `1.2.0-alpha01-dev682` -> `1.2.0-alpha01-dev683`
* `Coroutines`:
    * Fixes in `AccumulatorFlow`

## 0.10.2

* `Versions`:
    * `Compose`: `1.2.0-alpha01-dev675` -> `1.2.0-alpha01-dev682`

## 0.10.1

* `Versions`:
    * `Ktor`: `2.0.0` -> `2.0.1`
* `Crypto`:
    * Add `hmacSha256`
    * Add `hex`

## 0.10.0

* `Versions`:
    * `Kotlin`: `1.6.10` -> `1.6.21`
    * `Compose`: `1.1.1` -> `1.2.0-alpha01-dev675`
    * `Exposed`: `0.37.3` -> `0.38.2`
    * `Ktor`: `1.6.8` -> `2.0.0`
    * `Dokka`: `1.6.10` -> `1.6.21`

## 0.9.24

* `Ktor`:
    * `Common`:
        * New extension fun `MPPFile#input`

## 0.9.23

* `Repos`:
    * `Exposed`:
        * New property `ExposedRepo#selectAll` to retrieve all the rows in the table

## 0.9.22

* `Ktor`:
    * `Server`:
        * Now `createKtorServer` fun is fully customizable

## 0.9.21

* `Repos`:
    * `Exposed`:
        * fixes in `AbstractExposedWriteCRUDRepo`

## 0.9.20

* `Repos`:
    * `Common`:
        * Fixes in `OneToManyAndroidRepo`
        * New `CursorIterator`

## 0.9.19

* `Versions`:
    * `Coroutines`: `1.6.0` -> `1.6.1`
* `Repos`:
    * `Exposed`:
        * Fixes in `ExposedStandardVersionsRepoProxy`

## 0.9.18

* `Common`
    * New extensions for `Element`: `Element#onActionOutside` and `Element#onClickOutside`

## 0.9.17

* `Common`:
    * New extensions `Element#onVisibilityChanged`, `Element#onVisible` and `Element#onInvisible`
* `Coroutines`:
    * New extension `Element.visibilityFlow()`
* `FSM`:
    * Now it is possible to resolve conflicts on `startChain`

## 0.9.16

* `Versions`:
    * `Klock`: `2.6.3` -> `2.7.0`
* `Common`:
    * New extension `Node#onRemoved`
    * `Compose`:
        * New extension `Composition#linkWithRoot` for removing of composition with root element
* `Coroutines`:
    * `Compose`:
        * New function `renderComposableAndLinkToContextAndRoot` with linking of composition to root element

## 0.9.15

* `FSM`:
    * Rename `DefaultUpdatableStatesMachine#compare` to `DefaultUpdatableStatesMachine#shouldReplaceJob`
    * `DefaultStatesManager` now is extendable
    * `DefaultStatesMachine` will stop all jobs of states which was removed from `statesManager`

## 0.9.14

* `Versions`:
    * `Klock`: `2.6.2` -> `2.6.3`
    * `Ktor`: `1.6.7` -> `1.6.8`
* `Ktor`:
    * Add temporal files uploading functionality (for clients to upload and for server to receive)

## 0.9.13

* `Versions`:
    * `Compose`: `1.1.0` -> `1.1.1`

## 0.9.12

* `Common`:
    * `JS`:
        * New function `openLink`
        * New function `selectFile`
        * New function `triggerDownloadFile`
    * `Compose`:
        * Created :)
        * `Common`:
            * `DefaultDisposableEffectResult` as a default realization of `DisposableEffectResult`
        * `JS`:
            * `openLink` on top of `openLink` with `String` target from common
* `Coroutines`:
    * `Compose`:
        * `Common`:
            * New extension `Flow.toMutableState`
            * New extension `StateFlow.toMutableState`
        * `JS`:
            * New function `selectFileOrThrow` on top of `selectFile` from `common`
            * New function `selectFileOrNull` on top of `selectFile` from `common`

## 0.9.11

* `Versions`:
    * `Klock`: `2.6.1` -> `2.6.2`
* `Coroutines`:
    * `Compose`:
        * Created :)
        * New extensions and function:
            * `Composition#linkWithJob`
            * `Composition#linkWithContext`
            * `renderComposableAndLinkToContext`

## 0.9.10

* `Versions`:
    * `Klock`: `2.5.2` -> `2.6.1`
* Ktor:
    * Client:
        * New function `UnifiedRequester#createStandardWebsocketFlow` without `checkReconnection` arg
    * Server:
        * Now it is possible to filter data in `Route#includeWebsocketHandling`
        * Callback in `Route#includeWebsocketHandling` and dependent methods is `suspend` since now
        * Add `URLProtocol` support in `Route#includeWebsocketHandling` and dependent methods

## 0.9.9

* `Versions`:
    * `Klock`: `2.5.1` -> `2.5.2`
* `Common`:
    * Add new diff tool - `applyDiff`
    * Implementation of `IntersectionObserver` in JS part (copypaste of [this](https://youtrack.jetbrains.com/issue/KT-43157#focus=Comments-27-4498582.0-0) comment)

## 0.9.8

* `Versions`:
    * `Exposed`: `0.37.2` -> `0.37.3`
    * `Klock`: `2.4.13` -> `2.5.1`
    * `AppCompat`: `1.4.0` -> `1.4.1`

## 0.9.7

* `Repos`:
    * `Exposed`:
        * Fix in `ExposedOneToManyKeyValueRepo` - now it will not use `insertIgnore`
* `Ktor`:
    * `Server`:
        * `Route#includeWebsocketHandling` now will check that `WebSockets` feature and install it if not

## 0.9.6

* `Repos`:
    * `Exposed`:
        * Fix in `ExposedOneToManyKeyValueRepo` - now it will not use `deleteIgnoreWhere`

## 0.9.5

* `Versions`:
    * `Klock`: `2.4.12` -> `2.4.13`

## 0.9.4

* `Pagination`:
    * `Common`:
        * Add several `optionallyReverse` functions
* `Common`:
    * Changes in `Either`:
        * Now `Either` uses `optionalT1` and `optionalT2` as main properties
        * `Either#t1` and `Either#t2` are deprecated
        * New extensions `Either#mapOnFirst` and `Either#mapOnSecond`

## 0.9.3

* `Versions`:
    * `UUID`: `0.3.1` -> `0.4.0`

## 0.9.2

* `Versions`:
    * `Klock`: `2.4.10` -> `2.4.12`

## 0.9.1

* `Repos`:
    * `Exposed`:
        * Default realizations of standard interfaces for exposed DB are using public fields for now:
            * `ExposedReadKeyValueRepo`
            * `ExposedReadOneToManyKeyValueRepo`
            * `ExposedStandardVersionsRepoProxy`
        * New typealiases for one to many exposed realizations:
            * `ExposedReadKeyValuesRepo`
            * `ExposedKeyValuesRepo`

## 0.9.0

* `Versions`:
    * `Kotlin`: `1.5.31` -> `1.6.10`
    * `Coroutines`: `1.5.2` -> `1.6.0`
    * `Serialization`: `1.3.1` -> `1.3.2`
    * `Exposed`: `0.36.2` -> `0.37.2`
    * `Ktor`: `1.6.5` -> `1.6.7`
    * `Klock`: `2.4.8` -> `2.4.10`

## 0.8.9

* `Ktor`:
    * `Server`:
        * Fixes in `uniloadMultipart`
    * `Client`:
        * Fixes in `unimultipart`
* `FSM`:
    * Fixes in `DefaultUpdatableStatesMachine`

## 0.8.8

* `Versions`:
    * `AppCompat`: `1.3.1` -> `1.4.0`
    * Android Compile SDK: `31.0.0` -> `32.0.0`
* `FSM`:
    * `DefaultStatesMachine` now is extendable
    * New type `UpdatableStatesMachine` with default realization`DefaultUpdatableStatesMachine`

## 0.8.7

* `Ktor`:
    * `Client`:
        * `UnifiedRequester` now have no private fields
        * Add preview work with multipart
    * `Server`
        * `UnifiedRouter` now have no private fields
        * Add preview work with multipart

## 0.8.6

* `Common`:
    * `Either` extensions `onFirst` and `onSecond` now accept not `crossinline` callbacks
    * All `joinTo` now accept not `crossinline` callbacks

## 0.8.5

* `Common`:
    * `repeatOnFailure`

## 0.8.4

* `Ktor`:
    * `Server`:
        * Several new `createKtorServer`

## 0.8.3

* `Common`:
    * Ranges intersection functionality
    * New type `Optional`
* `Pagination`:
    * `Pagination` now extends `ClosedRange<Int>`
    * `Pagination` intersection functionality

## 0.8.2

* `Versions`:
    * `Klock`: `2.4.7` -> `2.4.8`
    * `Serialization`: `1.3.0` -> `1.3.1`
* `FSM`:
    * Now it is possible to pass any `CheckableHandlerHolder` in `FSMBuilder`
    * Now `StatesMachine` works with `CheckableHandlerHolder` instead of `CustomizableHandlerHolder`

## 0.8.1

* `Versions`:
    * `Exposed`: `0.36.1` -> `0.36.2`
    * `Core KTX`: `1.6.0` -> `1.7.0`

## 0.8.0

* `Versions`:
    * `Klock`: `2.4.6` -> `2.4.7`
    * `Ktor`: `1.6.4` -> `1.6.5`
    * `Exposed`: `0.35.3` -> `0.36.1`
* `Common`:
    * Type `Either` got its own serializer
* `FSM`:
    * `Common`:
        * Full rework of FSM:
            * Now it is more flexible for checking of handler opportunity to handle state
            * Now machine and states managers are type-oriented
            * `StateHandlerHolder` has been renamed to `CheckableHandlerHolder`
        * Add opportunity for comfortable adding default state handler

## 0.7.4

* `Common`:
    * New type `Either`
* `Serialization`:
    * `TypedSerializer`
        * New factory fun which accept vararg pairs of type and its serializer
* `Repos`:
    * `Common` (`Android`):
        * `AbstractMutableAndroidCRUDRepo` flows now will have extra buffer capacity instead of reply. It means that
          android crud repo _WILL NOT_ send previous events to the 
    * `Exposed`:
        * New parameter `AbstractExposedWriteCRUDRepo#replyCacheInFlows`
        * KeyValue realization `ExposedKeyValueRepo` properties `_onNewValue` and `_onValueRemoved` now are available in
          inheritors
* `Pagination`:
    * `Common`:
        * New types `getAllBy*` for current, next and custom paging

## 0.7.3

* `Versions`:
    * `Exposed`: `0.35.2` -> `0.35.3`

## 0.7.2

* `Versions`:
    * `Klock`: `2.4.5` -> `2.4.6`

## 0.7.1

* `Versions`:
    * `Klock`: `2.4.3` -> `2.4.5`
    * `Exposed`: `0.35.1` -> `0.35.2`
* `Coroutines`:
    * `Common`:
        * New `Flow` - `AccumulatorFlow`
* `FSM`:
    * `Common`:
        * `InMemoryStatesManager` has been replaced
        * `StatesMachine` became an interface
        * New manager `DefaultStatesManager` with `DefaultStatesManagerRepo` for abstraction of manager and storing of
          data info

## 0.7.0

**THIS VERSION HAS MIGRATED FROM KOTLINX DATETIME TO KORLIBS KLOCK. CAREFUL**

* `Versions`
    * `kotlinx.datetime` -> `Klock`

## 0.6.0 DO NOT RECOMMENDED

**THIS VERSION HAS MIGRATED FROM KORLIBS KLOCK TO KOTLINX DATETIME. CAREFUL**
**ALL DEPRECATION HAVE BEEN REMOVED**

* `Versions`
    * `Klock` -> `kotlinx.datetime`

## 0.5.31

* `Versions`:
    * `Klock`: `2.4.2` -> `2.4.3`
    * `Ktor`: `1.6.3` -> `1.6.4`

## 0.5.30

* `Versions`:
    * `Serialization`: `1.2.2` -> `1.3.0`

## 0.5.29

* `Versions`:
    * `Exposed`: `0.34.2` -> `0.35.1`

## 0.5.28

* `Versions`:
    * `Kotlin`: `1.5.30` -> `1.5.31`
    * `Klock`: `2.4.1` -> `2.4.2`

## 0.5.27

* `Versions`:
    * `Exposed`: `0.34.1` -> `0.34.2`

## 0.5.26

* `Repos`:
    * `InMemory`:
        * `MapCRUDRepo`s and `MapKeyValueRepo`s got `protected` methods and properties instead of private

## 0.5.25

* `Versions`:
    * `UUID`: `0.3.0` -> `0.3.1`
* `Common`:
    * New property `MPPFile#withoutSlashAtTheEnd`
    * Extension `clamp` has been deprecated
    * New extension `Iterable#diff`
* `Serialization`:
    * New operators `TypedSerializer#plusAssign` and `TypedSerializer#minusAssign`

## 0.5.24

* `Versions`:
    * `Coroutines`: `1.5.1` -> `1.5.2`
    * `Klock`: `2.3.4` -> `2.4.1`
* `Coroutines`:
    * New function `CoroutineScope` with safely exceptions handler as second parameter

## 0.5.23

* `Versions`:
    * `Exposed`: `0.33.1` -> `0.34.1`
* `Common`:
    * New extensions `Iterable#joinTo` and `Array#joinTo`

## 0.5.22

* `Versions`
    * `Kotlin`: `1.5.21` -> `1.5.30`
    * `Klock`: `2.3.2` -> `2.3.4`
    * `AppCompat`: `1.3.0` -> `1.3.1`
    * `Ktor`: `1.6.2` -> `1.6.3`

## 0.5.21

* `Versions`
    * `Klock`: `2.3.1` -> `2.3.2`
* `Serialization`
    * `Typed Serializer`:
        * `TypedSerializer` Descriptor serial name has been fixed

## 0.5.20

* `Repos`:
    * `Common`
        * `Android`:
            * `*OrNull` analogs of `Cursor.get*(String)` extensions have been added
            * Extensions `Cursor.getFloat` and `Cursor.getFloatOrNull` have been added

## 0.5.19

* `LanguageCode`:
    * `IetfLanguageCode` became as sealed class
    * `IetfLanguageCode` now override `toString` and returns its code

## 0.5.18

* `Versions`
    * `Kotlin Exposed`: `0.32.1` -> `0.33.1`
* `LanguageCode`:
    * Module has been created

## 0.5.17

**SINCE THIS UPDATE JS PARTS WILL BE COMPILED WITH IR COMPILER ONLY**

* `Versions`
    * `Kotlin`: `1.5.20` -> `1.5.21`
    * `Ktor`: `1.6.1` -> `1.6.2`
    * `Klock`: `2.2.0` -> `2.3.1`
    * `CryptoJS`: `4.0.0` -> `4.1.1`

## 0.5.16

* `Versions`
  * `Coroutines`: `1.5.0` -> `1.5.1`
  * `Serialization`: `1.2.1` -> `1.2.2`
  * `Ktor`: `1.6.0` -> `1.6.1`
  * `Klock`: `2.1.2` -> `2.2.0`
  * `Core KTX`: `1.5.0` -> `1.6.0`

## 0.5.15 HOTFIX FOR 0.5.14

* `Coroutines`
    * Fixes in `subscribeAsync`

## 0.5.14 NOT RECOMMENDED

* `Versions`
    * `Kotlin`: `1.5.10` -> `1.5.20`
* `Coroutines`
    * `subscribeSafelyWithoutExceptions` got new parameter `onException` by analogue with `safelyWithoutExceptions`
    * New extensions `Flow#subscribeAsync` and subsequent analogs of `subscribe` with opportunity to set up custom marker

## 0.5.13

* `Common`:
    * Add functionality for multiplatform working with files:
        * Main class for files `MPPFile`
        * Inline class for filenames work encapsulation `FileName`
* `FSM`
    * Module inited and in preview state

## 0.5.12

* `Common`:
    * `Android`
        * Extension `View#changeVisibility` has been fixed
* `Android`
    * `RecyclerView`
        * Default adapter got `dataCountFlow` property
        * New subtype of adapter based on `StateFlow`: `StateFlowBasedRecyclerViewAdapter`

## 0.5.11

* `Repos`:
    * `Common`:
        * Fixes in `WriteOneToManyRepo#add`
    * `Exposed`:
        * Fixes in `ExposedOneToManyKeyValueRepo#add`

## 0.5.10

* `Versions`
    * `Core KTX`: `1.3.2` -> `1.5.0`
    * `AndroidX Recycler`: `1.2.0` -> `1.2.1`
    * `AppCompat`: `1.2.0` -> `1.3.0`
* `Android`
    * `RecyclerView`:
        * `data` of `RecyclerViewAdapter` became an abstract field
            * New function `RecyclerViewAdapter`
    * `Common`:
        * New extension `View#changeVisibility`
    * `Repos`:
        * `Common`:
            * `WriteOneToManyRepo` got new function `clearWithValue`
            * `Android`:
                * New extension `SQLiteDatabase#selectDistinct`
                * Fixes in `OneToManyAndroidRepo`
* `Ktor`
    * `Server`
        * All elements in configurators became a `fun interface`
* `Pagination`
    * New function `doForAllWithCurrentPaging`

## 0.5.9

* `Repos`
    * `Common`
        * `OneToManyAndroidRepo` got new primary constructor

## 0.5.8

* `Common`:
    * New extension `Iterable#firstNotNull`
* `Coroutines`
    * New extension `Flow#firstNotNull`
    * New extensions `CoroutineContext#LinkedSupervisorJob`, `CoroutineScope#LinkedSupervisorJob` and
      `CoroutineScope#LinkedSupervisorScope`

## 0.5.7

* `Pagination`
    * `Ktor`
        * `Server`
            * Fixes in extension `extractPagination`
* `Repos`
    * `Cache`
        * All standard cache repos have been separated to read and read/write repos

## 0.5.6

* `Versions`
    * `Exposed`: `0.31.1` -> `0.32.1`
* `Coroutines`
    * `JVM`
        * `launchSynchronously` and subsequent functions got improved mechanism
    * New method `safelyWithResult`

## 0.5.5

* `Versions`
    * `Ktor`: `1.5.4` -> `1.6.0`

## 0.5.4

* `Versions`:
    * `Klock`: `2.1.0` -> `2.1.2`

## 0.5.3

* `Versions`:
    * `Kotlin`: `1.5.0` -> `1.5.10`
* `Coroutines`:
    * Extensions `doInUI` and `doInDefault` were replaced in common and available on any supported platform
    * Extension `doInIO` replaced into `jvm` and available on any `JVM` platform
    * Old extension `safelyWithouException` without `onException` has been replaced by its copy with `onException` and
    default value
        * New value `defaultSafelyWithoutExceptionHandlerWithNull` which is used in all `*WithoutExceptions` by default
    * Analogs of `launch` and `async` for `safely` and `safelyWithoutExceptions` were added
    * Analogs of `runCatching` for `safely` and `safelyWithoutExceptions` were added

## 0.5.2

* `Ktor`:
    * `Client`:
        * Fixes in `UnifiedRequester`

## 0.5.1

* `Versions`:
    * `Kotlin Serialization`: `1.2.0` -> `1.2.1`

## 0.5.0

**Notice**: This version is still depend on Kotlin
Exposed 0.31.1. That means that this version
may work improperly in modules based on Kotlin
Exposed

* `Versions`:
    * `Kotlin Exposed`: `0.30.2` -> `0.31.1`
    * `Kotlin Coroutines`: `1.4.3` -> `1.5.0`
    * `RecyclerView`: `1.1.0` -> `1.2.0`
    * `Ktor`: `1.5.3` -> `1.5.4`
    * `Klock`: `2.0.7` -> `2.1.0`
    * `UUID`: `0.2.4` -> `0.3.0`
* **ALL DEPRECATIONS WERE REMOVED**
* `Android`:
    * `Alerts`:
        * `RecyclerView`:
            * Classes `ActionViewHolder` and `ActionsRecyclerViewAdapter` became public
* `Coroutines`:
    * New extension and function `doSynchronously` which are the same as `launchSynchronously`
    * New extensions `doInDefault` and `doInIO`

## 0.4.36

* All `Android` targets inside common mpp modules now includes JVM code

## 0.4.35

* `Versions`:
    * `Kotlin Exposed`: `0.30.1` -> `0.30.2`
* `Serialization`:
    * `TypedSerializer`:
        * Project has been inited

## 0.4.34

* `Versions`:
    * `uuid`: `0.2.3` -> `0.2.4`
* `Repos`:
    * `AbstractExposedCRUDRepo` now implements `StandardCRUDRepo`
    * `AbstractMutableAndroidCRUDRepo` now implements `StandardCRUDRepo`

## 0.4.33

* `Versions`:
    * `Ktor`: `1.5.2` -> `1.5.3`
* `Coroutines`
    * Add `WeakJob` workaround:
        * `CoroutineScope#weakLaunch`
        * `CoroutineScope#weakAsync`

## 0.4.32

* `Versions`:
    * `Kotlin Exposed`: `0.29.1` -> `0.30.1`

## 0.4.31

* `Versions`:
    * `Kotlin`: `1.4.31` -> `1.4.32`
* `Pagination`:
    * New extensions `PaginationResult.changeResultsUnchecked` and `PaginationResult.changeResults` for mapping results
    with the same parameters, but different data
    * Extension `PaginationResult.thisPageIfNotEmpty` now is typed and will return `PaginationResult?` with the same
    generic type as income `PaginationResult`
    * New extension `PaginationResult.currentPageIfNotEmpty` - shortcut for `PaginationResult.thisPageIfNotEmpty`
    * New common functions. They were created as replacements for currently available for more comfortable work
    with repos pagination:
        * `doForAll`
        * `doForAllWithNextPaging`
        * `doForAllWithCurrentPaging`
        * `getAll`
        * `getAllWithNextPaging`
        * `getAllWithCurrentPaging`
* `Coroutines`:
    * Rewrite `subscribeSafelyWithoutExceptions`
        * Now `subscribeSafelyWithoutExceptions` will use default handler instead of skipping
    * New extension `subscribeSafelySkippingExceptions`
* `Repos`
    * New subproject `repos.cache` - this subproject will contain repos with data caching mechanisms
    * Most old `doForAll` methods have been deprecated

## 0.4.30

* `Versions`:
    * `Klock`: `2.0.6` -> `2.0.7`
* `Pagination`:
    * New variable `defaultPaginationPageSize` has been added to be able to change default pagination size
    * Add new value `firstPageWithOneElementPagination` 

## 0.4.29

* `Versions`:
    * `Coroutines`: `1.4.2` -> `1.4.3`
* `Repos`:
    * `Common`
        * `Android`:
            * New `blockingReadableTransaction`/`blockingWritableTransaction`
                * Android databases realizations now use blocking transactions where it is possible
            * Several improvements in transactions work

## 0.4.28

* `Versions`:
    * `Kotlin`: `1.4.30` -> `1.4.31`
    * `Ktor`: `1.5.1` -> `1.5.2`
* `Coroutines`
    * Add `createActionsActor`/`createSafeActionsActor` and `doWithSuspending`

## 0.4.27

* `Repos`
    * `Exposed`
        * Fix in `AbstractExposedWriteCRUDRepo`

## 0.4.26

* `Versions`:
    * `Serialization`: `1.0.1` -> `1.1.0`

## 0.4.25

* `Matrix`:
    * Subproject has been created

## 0.4.24

* `Versions`:
    * `Kotlin`: `1.4.21` -> `1.4.30`
    * `Klock`: `2.0.4` -> `2.0.6`
* `Coroutines`:
    * New class `DoWithFirstBuilder`
    * Several new extensions like `firstOf`/`first`/`invokeOnFirstOf`

## 0.4.23

* `Versions`:
    * `Ktor`: `1.5.0` -> `1.5.1`
* `Serialization`
    * `Base64`
        * New serializer `Base64BytesToFromStringSerializer` has been added

## 0.4.22

* `Versions`:
    * `Exposed`: `0.28.1` -> `0.29.1`
    * `Klock`: `2.0.2` -> `2.0.4`

## 0.4.21

* `Common`
    * `JS`
        * Extension `DataView#toByteArray` has been added
        * Extension `ArrayBuffer#toByteArray` has been added
        * Extension `ByteArray#toDataView` has been added
        * Extension `ByteArray#toArrayBuffer` has been added
* `Coroutines`
    * `JS`
        * Extension `Blob#toByteArray` has been added
* `Crypto`
    * Subproject has been created
* `Serialization`
    * `Base64`
        * Currently, it is not depended on `common` project and use `crypto` instead

## 0.4.20

* `Serialization`
    * `Encapsulator`:
        * Has been created

## 0.4.19

* `Coroutines`:
    * New extension `Iterable<Deferred>#awaitFirstWithDeferred` has been added to identify which of `Deferred`s was
      normally completed
    * New extensions `Iterable<Deferred<T>>#invokeOnFirst` and `Iterable<DeferredAction<*, O>>.invokeFirstOf` have been
    added

## 0.4.18

* `Coroutines`:
    * New extension `Iterable<Deferred>#awaitFirst` has been added
* `Serialization`
    * `Base 64`
        * New `Base64ByteArraySerializer` has been added

## 0.4.17

* `Common`
    * Functionality for decode/encode base 64 to/from `ByteArray`/`String` have been added
* `Serialization`
    * `Base 64`
        * Project has been initiated

## 0.4.16

* `Coroutines`:
    * `safely`:
        * New `safelyWithoutExceptions` function may accept `onException` parameter with nullable result
        * Old `safelyWithoutExceptions` now using `defaultSafelyWithoutExceptionHandler` to handle exceptions "like in
          `safely`", but it is expected that `defaultSafelyWithoutExceptionHandler` will not throw any exception

## 0.4.15

* `Coroutines`:
    * `safely`:
        * `SafelyExceptionHandlerKey` has been deprecated
        * `SafelyExceptionHandler` has been deprecated
        * `ContextSafelyExceptionHandlerKey` has been added
        * `ContextSafelyExceptionHandler` has been added
        * `safelyWithContextExceptionHandler` has been added

## 0.4.14

* `Versions`:
    * `Kotlin`: `1.4.20` -> `1.4.21`
    * `Ktor`: `1.4.3` -> `1.5.0`
    * `Klock`: `2.0.1` -> `2.0.2`
* `Coroutines`:
    * Add global variable `defaultSafelyExceptionHandler`
    * Add `SafelyExceptionHandlerKey` and `SafelyExceptionHandler` classes to be able to overwrite
    `defaultSafelyExceptionHandler` using context of coroutine

## 0.4.13

* `Common`
    * `Android`
        * Add expand/collapse functionality for horizontal expand/collapse

## 0.4.12

* `Coroutines`
    * `JVM`
        * Update `launchSynchronously` signature
* `Selector`
    * Project created

## 0.4.11

* `Common`
    * Add `clamp` function

## 0.4.10

* `Versions`:
    * `Klock`: `2.0.0` -> `2.0.1`
* `Repo`
    * Repo `WriteStandardKeyValueRepo` got new method `unsetWithValues`

## 0.4.9

* `Versions`:
    * `Ktor`: `1.4.2` -> `1.4.3`
* `Coroutines`:
    * `launchSynchronously` has been added in JVM
* `Repo`
    * `Common`
        * In repos different usages of `BroadcastChannel`s has been replaced with `MutableSharedFlow`
    * `Exposed`
        * `asObject` open fun has been added in CRUD realization

## 0.4.8

* `Versions`:
    * `Coroutines`: `1.4.1` -> `1.4.2`
    * `UUID`: `0.2.2` -> `0.2.3`
* `Pagination`
    * Add `PaginatedIterable` and `PaginatedIterator`

## 0.4.7

* `Ktor`
    * `Client`
        * New class `UnifiedRequester`
    * `Server`
        * New class `UnifiedRouter`
* `Repos`
    * `Ktor`
        * `Client`
            * Rewriting of all clients on new `UnifiedRequester`
        * `Server`
            * Rewriting of all clients on new `UnifiedRouter`

## 0.4.6

* `Common`
    * New annotation `Warning` has been added
* `Pagination`
    * `Common`
        * `Pagination` got new extension: `Pagination#isFirstPage`
* `Coroutines`:
    * New extension `FlowCollector#invoke` has been added
* `Repos`
    * `Common`
        * `JVM` (and `Android` since `Android API 26`):
            * `FileStandardKeyValueRepo` has been added
        * Add several `typealias`es for each type of repos

## 0.4.5

* `Android`
    * `Alerts`
        * `Common`
            * Project has been created
        * `RecyclerView`
            * Project has been created
* `Common`
    * Annotation `PreviewFeature` has been added
    * `Android`
        * Added tools to work with visibility in more comfortable way
        * Added tools to work with disabled/enabled state in more comfortable way
        * Added tools to work with expanded/collapsed state in more comfortable way (in preview mode)

## 0.4.4

* `Versions`:
    * `Klock`: `1.12.1` -> `2.0.0`
* `Commons`:
    * Update left items functionality to include work with `GridLayoutManager`
* `Repos`:
    * Add interface `VersionsRepo`
        * Add default realization of `VersionsRepo` named `StandardVersionsRepo` which use `StandardVersionsRepoProxy`
        to get access to some end-store
        * Add default realization of `StandardVersionsRepoProxy` based on `KeyValue` repos
        * Add realizations of `StandardVersionsRepoProxy` for exposed and android (`SQL` and `SharedPreferences`)
    * `Commons`:
        * In Android fully reworked transactions functions
        * Now `DatabaseCoroutineContext` is a shortcut for `Dispatchers.IO`

## 0.4.3

* `Versions`:
    * `Kotlin`: `1.4.10` -> `1.4.20`
* `Common`:
    * Two new extensions for Android:
        * `Resources#getSp`
        * `Resources#getDp`

## 0.4.2

* `Repos`:
    * Add `WriteOneToManyKeyValueRepo#set` function and extensions

## 0.4.1

* `Repos`:
    * Fixed error in `ExposedKeyValueRepo` related to negative size of shared flow
    * Fixed error in `ExposedKeyValueRepo` related to avoiding of table initiation

## 0.4.0

* `Repos`:
    * `ReadOneToManyKeyValueRepo` got `keys` method with value parameter
        * All implementations inside of this library has been updated
    * `ReadStandardKeyValueRepo` got `keys` method with value parameter
        * All implementations inside of this library has been updated
    * New extensions `withMapper`

## 0.3.3

* `Coroutines`:
    * New extension `Flow<T>#plus`

## 0.3.2

* `Versions`:
    * `Coroutines`: `1.4.1` -> `1.4.2`
* `Repos`:
    * `Common`:
        * New inline function `mapper` for simplier creating of `MapperRepo` objects
        * Extensions `withMapper` for keyvalue repos and onetomany repos

## 0.3.1

**ANDROID PACKAGES**

* `Android`:
    * `RecyclerView`:
        * Library has been created
* `Common`
    * Now available package `dev.inmo:micro_utils.common-android`
* `Coroutines`
    * Now available package `dev.inmo:micro_utils.coroutines-android`
* `Ktor`
    * `Common`
        * Now available package `dev.inmo:micro_utils.ktor.common-android`
    * `Client`
        * Now available package `dev.inmo:micro_utils.ktor.client-android`
* `MimeTypes`
    * Now available package `dev.inmo:micro_utils.mime_types-android`
* `Pagination`
    * `Common`
        * Now available package `dev.inmo:micro_utils.pagination.common-android`
    * `Ktor`
        * `Common`
            * Now available package `dev.inmo:micro_utils.pagination.ktor.common-android`
* `Repos`
    * `Common`
        * Now available package `dev.inmo:micro_utils.repos.common-android`
        * Now it is possible to use default realizations of repos abstractions natively on android
    * `Inmemory`
        * Now available package `dev.inmo:micro_utils.repos.inmemory-android`
    * `Ktor`
        * `Common`
            * Now available package `dev.inmo:micro_utils.repos.ktor.common-android`
        * `Common`
            * Now available package `dev.inmo:micro_utils.repos.ktor.client-android`

## 0.3.0

All deprecations has been removed

* `Repos`:
    * `Common`:
        * `KeyValue` and `OneToMany` repos lost their deprecated methods
        * `OneToMany` write repos got additional extensions for mutation of repo
        * `KeyValue` write repos got additional extensions for mutation of repo
        * New interface `MapperRepo` and new classes which are using this:
            * `KeyValue`
                * `MapperReadStandardKeyValueRepo`
                * `MapperWriteStandardKeyValueRepo`
                * `MapperStandardKeyValueRepo`
            * `OneToMany`
                * `MapperReadOneToManyKeyValueRepo`
                * `MapperWriteOneToManyKeyValueRepo`
                * `MapperOneToManyKeyValueRepo`
    * `Exposed`:
        * CRUD realizations replaced their channels to shared flows

## 0.2.7

* `Versions`:
    * `Coroutines`: `1.4.0` -> `1.4.1`
* `Repos`:
    * `WriteStandardKeyValueRepo` got new methods `set` and `unset` with collections
    * All standard realizations of repos got collections methods realizations
    * All old usages of `BroadcastFlow` and `BroadcastChannel` has been replaced with `MutableSharedFlow`
    * `Ktor`:
        * `Server`:
            * Fixed incorrect answer for `keyvalue`

## 0.2.6

* `Pagination`
    * Fixes in function `List#paginate`
    * Extension property `Pagination#lastIndexExclusive`

## 0.2.5

* `Coroutines`
    * Function `safelyWithoutExceptions`
    * Extension `CoroutineScope#safeActor`

## 0.2.4

* `Versions`
    * `Serialization`: `1.0.0` -> `1.0.1`
* `Common`
    * Full rework of `DiffUtils`
        * Data class `Diff` has been added
        * Extension `Iterable#calculateDiff` has been added
            * Extension `Iterable#calculateStrictDiff` as replacement for `Iterable#calculateDiff` with
            `strictComparison` mode enabled
            * Functions `Diff` (as analog of `Iterable#calculateDiff`) and `StrictDiff` (as analog of
            `Iterable#calculateStrictDiff`)
* `Coroutines`
    * `BroadcastFlow` now is deprecated
    * `BroadcastStateFlow` now is deprecated
    * New extensions for `Flow`s:
        * `Flow#subscribe`
        * `Flow#subscribeSafely`
        * `Flow#subscribeSafelyWithoutExceptions`

## 0.2.3

* `Versions`
    * `Coroutines`: `1.3.9` -> `1.4.0`
    * `Exposed`: `0.27.1` -> `0.28.1`
* `Common`
    * `K/JS`
        * Add several extensions for `Element` objects to detect that object is on screen viewport
        * Add several extensions for `Element` objects to detect object visibility
* `Coroutines`
    * `BroadcastStateFlow` now use different strategy for getting of state and implements `replayCache`

## 0.2.2

* `Repos`
    * `Common`
        * Several new methods `ReadOneToManyKeyValueRepo#getAll`
        * Several new method `WriteOneToManyKeyValueRepo#add` and several extensions
        * Several new method `WriteOneToManyKeyValueRepo#remove` and several extensions

## 0.2.1

* `Pagination`
    * `Common`:
        * Extension `Pagination#reverse` has been added
        * Factory `PaginationByIndexes`
        * Shortcut `calculatePagesNumber` with reversed parameters
        * Value `emptyPagination` for empty `SimplePagination` cases

## 0.2.0

* `Repos`
    * `Exposed`
        * Now this project depend on `micro_utils.coroutines`
        * Typealias `ColumnAllocator` has been replaced to root exposed package
        * Interface `ExposedRepo` has been added
            * `ExposedCRUDRepo` now extends `ExposedRepo` instead of simple `Repo`
        * New extension `initTable` for classes which are `Table` and `ExposedRepo` at the same time
        * `KeyValue`:
            * `tableName` parameter
            * Class `AbstractExposedReadKeyValueRepo`
                * Renamed to `ExposedReadKeyValueRepo`
                * Changed incoming types to `ColumnAllocator`
                * `open` instead of `abstract`
                * Implements `ExposedRepo`
            * Class `AbstractExposedKeyValueRepo`
                * Renamed to `ExposedKeyValueRepo`
                * Changed incoming types to `ColumnAllocator`
                * `open` instead of `abstract`
        * `OneToMany`:
            * `tableName` parameter
            * Class `AbstractExposedReadOneToManyKeyValueRepo`
                * Renamed to `ExposedReadOneToManyKeyValueRepo`
                * Changed incoming arguments order
                * Implements `ExposedRepo`
            * Class `AbstractExposedOneToManyKeyValueRepo`
                * Renamed to `ExposedKeyValueRepo`
                * Changed incoming arguments order
                * `open` instead of `abstract`
                * Release for every `Flow` in parent interfaces

## 0.1.1

* `Versions`:
    * `kotlinx.serialization`: `1.0.0-RC2` -> `1.0.0`
* `Pagination`
    * `Common`
        * Function `calculatePage` for calculating page based on size of page and index of first element
        * Extension `List#createPaginationResult` which use index of first element to calculate current page and other
        info in `PaginationResult` object
        * Factory `emptyPaginationResult` for empty `PaginationResult`
        * Extensions `paginate` for creating of `PaginationResult` which use as source one of next types:
            * `Iterable`
            * `List`
            * `Set`
* `Repos`
    * `Common`
        * Interfaces related to `OneToManyKeyValueRepo` were renamed with convenience to `Read`/`Write` modifier before name
            * All subclasses were renamed
        * Interfaces related to `StandartKeyValueRepo` were renamed with convenience to `Read`/`Write` modifier before name
            * All subclasses were renamed
        * Extensions `doForAll` and `getAll` were added for all current types of repos:
            * `ReadStandardCRUDRepo`
            * `ReadStandardKeyValueRepo`
            * `ReadOneToManyKeyValueRepo`
        * `ReadStandardKeyValueRepo` methods `values` and `keys` now have default value for `reversed` parameter `false`
        * New `Flow`'s in `WriteOneToManyKeyValueRepo`:
            * `onNewValue`
            * `onValueRemoved`
            * `onDataCleared`
        * New function `ReadStandardCRUDRepo#count`
    * `In Memory`
        * Package has been created:) you can connect it via `implementation "dev.inmo:micro_utils.repos.inmemory"`
        * `MapCRUDRepo` class as implementation of `StandardCRUDRepo` on top of `MutableMap` has been added
        * `MapKeyValueRepo` class as implementation of `StandardKeyValueRepo` on top of `MutableMap` has been added
        * `MapOneToManyKeyValueRepo` class as implementation of `OneToManyKeyValueRepo` on top of `MutableMap` has been added


## 0.1.0

Inited :)
