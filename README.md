# MicroUtils

This is a library with collection of tools for working in Kotlin environment. First of all, this library collection is oriented to use next technologies:

* [`Kotlin Coroutines`](https://github.com/Kotlin/kotlinx.coroutines)
* [`Kotlin Serialization`](https://github.com/Kotlin/kotlinx.serialization)
* [`Kotlin Exposed`](https://github.com/JetBrains/Exposed)
* [`Ktor`](https://ktor.io)

<details>
  <summary> <b>Android environment</b> </summary>

You always can look at the [properties file](https://github.com/InsanusMokrassar/MicroUtils/blob/master/gradle.properties#L24-L34) to get information about current project dependencies, compile and build tools for `Android` target.

</details>

## Projects

* `common` contains common tools for platform which usually are absent out-of-the-box when you starting project
* `coroutines` is a module for `Kotlin Coroutines` with different things like subscribing on flows (`onEach` + `launchIn` shortcut :) )
* `ktor` is a set of modules for `client`s and `server`s
* `mime_types` is NOT lightweight set of `MimeType`s with a lot of different objected and serializable (with `Kotlin Serialization`) mime types
* `pagination` is a complex of modules (explanation in [Complex modules structure](#complex-modules-structure) section) for lightweight pagination
* `repos` is a complex of modules (explanation in [Complex modules structure](#complex-modules-structure) section) for `KeyValue`/`OneToMany`/`CRUD` repos created to be able to exclude some heavy dependencies when you need some simple and lightweight typical repositories

## Complex modules structure

Most of complex modules are built with next hierarchy:

* `common` submodule for `API` things which are common for all platforms
* `exposed` submodule contains realizations for exposed tables
* `ktor` submodule is usually unavailable directly, because it contains its own submodules for clients and servers
    * `common` part contains routes which are common for clients and servers
    * `client` submodule contains clients which are usually using `UnifiedRequester` to make requests using routes from `ktor/common` module and some internal logic of requests
    * `server` submodule (in most cases `JVM`-only) contains some extensions for `Route` instances which usually will give opportunity to proxy internet requests from `ktor/client` realization to some proxy object
