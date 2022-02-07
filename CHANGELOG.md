# Changelog

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
