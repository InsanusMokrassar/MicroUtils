# Changelog

## 0.4.1

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