# Changelog

## 0.2.3

* `Common`
    * `K/JS`
        * Add several extensions for `Element` objects to detect that object is on screen viewport
        * Add several extensions for `Element` objects to detect object visibility

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