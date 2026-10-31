# MicroUtils

This is a library with collection of tools for working in Kotlin environment. First of all, this library collection is oriented to use next technologies:

* [`Kotlin Coroutines`](https://github.com/Kotlin/kotlinx.coroutines)
* [`Kotlin Serialization`](https://github.com/Kotlin/kotlinx.serialization)
* [`Kotlin Exposed`](https://github.com/JetBrains/Exposed)
* [`Ktor`](https://ktor.io)

<details>
  <summary> <b>Android environment</b> </summary>

You always can look at the <a href="https://github.com/InsanusMokrassar/MicroUtils/blob/master/gradle.properties#L24-L34">properties file</a> to get information about current project dependencies, compile and build tools for `Android` target.

</details>

## Projects

* `common` contains common tools for platform which usually are absent out-of-the-box when you starting project
* `selector` contains tools to use `Selector` interface with things like `RecyclerView` in android or other selection needs
* `coroutines` is a module for `Kotlin Coroutines` with different things like subscribing on flows (`onEach` + `launchIn` shortcut :) )
* `ktor` is a set of modules for `client`s and `server`s
* `mime_types` is NOT lightweight set of `MimeType`s with a lot of different objected and serializable (with `Kotlin Serialization`) mime types
* `pagination` is a complex of modules (explanation in [Complex modules structure](#complex-modules-structure) section) for lightweight pagination
* `serialization` is a collection of projects with serializers for `kotlinx.serialization`
* `repos` is a complex of modules (explanation in [Complex modules structure](#complex-modules-structure) section) for `KeyValue`/`OneToMany`/`CRUD` repos created to be able to exclude some heavy dependencies when you need some simple and lightweight typical repositories

## Complex modules structure

Most of complex modules are built with next hierarchy:

* `common` submodule for `API` things which are common for all platforms
* `exposed` submodule contains realizations for exposed tables
* `ktor` submodule is usually unavailable directly, because it contains its own submodules for clients and servers
    * `common` part contains routes which are common for clients and servers
    * `client` submodule contains clients which are usually using `UnifiedRequester` to make requests using routes from `ktor/common` module and some internal logic of requests
    * `server` submodule (in most cases `JVM`-only) contains some extensions for `Route` instances which usually will give opportunity to proxy internet requests from `ktor/client` realization to some proxy object

## Gradle Templates

All templates can be used by applying them in your project's build.gradle files using the `apply from` directive. For example:

```gradle
apply from: "$defaultProject"
```

In the sample has been used `defaultProject.gradle` as a basic template.

The project includes a collection of Gradle templates to simplify project setup and configuration. These templates are located in the `gradle/templates` directory and can be used to quickly set up different types of projects:

### Project Setup Templates

* `defaultProject.gradle` (usage `apply from: "$defaultProject"`) - Basic project configuration
* `defaultProjectWithSerialization.gradle` (usage `apply from: "$defaultProjectWithSerialization"`) - Project configuration with Kotlin Serialization support
* `mppJavaProject.gradle` (usage `apply from: "$mppJavaProject"`) - Multiplatform project with Java support
* `mppAndroidProject.gradle` (usage `apply from: "$mppAndroidProject"`) - Multiplatform project with Android support

### Multiplatform Configuration Templates

* `enableMPPAndroid.gradle` (usage `apply from: "$enableMPPAndroid"`) - Enable Android target in multiplatform project
* `enableMPPJs.gradle` (usage `apply from: "$enableMPPJs"`) - Enable JavaScript target in multiplatform project
* `enableMPPJvm.gradle` (usage `apply from: "$enableMPPJvm"`) - Enable JVM target in multiplatform project
* `enableMPPNativeArm64.gradle` (usage `apply from: "$enableMPPNativeArm64"`) - Enable ARM64 native target
* `enableMPPNativeX64.gradle` (usage `apply from: "$enableMPPNativeX64"`) - Enable x64 native target
* `enableMPPWasmJs.gradle` (usage `apply from: "$enableMPPWasmJs"`) - Enable WebAssembly JavaScript target

### Compose Integration Templates

* `addCompose.gradle` (usage `apply from: "$addCompose"`) - Basic Compose configuration
* `addComposeForAndroid.gradle` (usage `apply from: "$addComposeForAndroid"`) - Compose configuration for Android
* `addComposeForDesktop.gradle` (usage `apply from: "$addComposeForDesktop"`) - Compose configuration for Desktop
* `addComposeForJs.gradle` (usage `apply from: "$addComposeForJs"`) - Compose configuration for JavaScript

### Publishing Templates

* `publish.gradle` (usage `apply from: "$publish"`) - General publishing configuration
* `publish_jvm.gradle` (usage `apply from: "$publish_jvm"`) - JVM-specific publishing configuration
* `publish.kpsb` and `publish_jvm.kpsb` (usage `apply from: "$publish_kpsb"` and `apply from: "$publish_jvm_kpsb"`) - Publishing configuration for Kotlin Multiplatform and JVM

### Combined Project Templates

* `mppJvmJsWasmJsLinuxMingwProject.gradle` (usage `apply from: "$mppJvmJsWasmJsLinuxMingwProject"`) - Multiplatform project for JVM, JS, Wasm, Linux, and MinGW
* `mppJvmJsWasmJsAndroidLinuxMingwLinuxArm64Project.gradle` (usage `apply from: "$mppJvmJsWasmJsAndroidLinuxMingwLinuxArm64Project"`) - Multiplatform project with additional Android and ARM64 support
* `mppComposeJvmJsWasmJsAndroidLinuxMingwLinuxArm64Project.gradle` (usage `apply from: "$mppComposeJvmJsWasmJsAndroidLinuxMingwLinuxArm64Project"`) - Multiplatform project with Compose support
* `mppProjectWithSerializationAndCompose.gradle` (usage `apply from: "$mppProjectWithSerializationAndCompose"`) - Multiplatform project with both Serialization and Compose support
