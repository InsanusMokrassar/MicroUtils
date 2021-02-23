# Changelog

## 0.4.27

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
