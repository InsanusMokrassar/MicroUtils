# Startup Plugin Launcher

This module contains tools to start your plugin system.

## Config

Base config is pretty simple:

```json
{
    "plugins": [
        "dev.inmo.micro_utils.startup.launcher.HelloWorldPlugin"
    ]
}
```

So, `"dev.inmo.micro_utils.startup.launcher.HelloWorldPlugin"` is the fully qualified name of plugin you wish to be
included in the server.

> JS note: In JS there are no opportunity to determine object type by its full name. Because of it, in JS developers
> should prefer to use `Config` in their kotlin code directly instead of json config passing. More info see in [JS](#js)
> section

## JVM

For JVM target you may use main class by path: `dev.inmo.micro_utils.startup.launcher.MainKt`

It is expected, that you will pass the main ONE argument with path to the config json. Sample of launching:

```bash
./gradlew run --args="sample.config.json"
```

Content of `sample.config.json` described in [Config](#config) section.

You may build runnable app using:

```bash
./gradlew assembleDist
```

In that case in `build/distributions` folder you will be able to find zip and tar files with all required
tools for application running (via their `bin/app_name` binary). In that case yoy will not need to pass
`--args=...` and launch will look like `./bin/app_name sample.config.json`

## JS

In JS for starting of your plugins app, you should use `PluginsStarter` in your code:

```kotlin
PluginsStarter.startPlugins(
    Config(HelloWorldPlugin)
)
```

`Config` here is deserialized variant from [Config](#config) section. As was said in [Config](#config) section, in JS
there is no way to find classes/objects by their full qualifiers. Because of it you should use some way to register your
plugins in `StartPluginSerializer` or use the code like in the snippet above: there plugins will be registered
automatically.

In case you wish to register your plugins manually and run server from config, you should use one of the ways to register
plugin on start.

Sample with `EagerInitialization`: [Kotlin JS doc about lazy initialization](https://kotlinlang.org/docs/js-ir-compiler.html#incremental-compilation-for-development-binaries),
    [@EagerInitialization docs](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.js/-eager-initialization/):

```kotlin
@ExperimentalStdlibApi
@EagerInitialization
val plugin = createStartupPluginAndRegister("PluginNameToUseInConfig") {
    // Your plugin creation. For example:
    HelloWorldPlugin
}
```

So, in that case you will be able to load plugins list as `JsonObject` from anywhere and start plugins app with it:

```kotlin
PluginsStarter.startPlugins(
    jsonObject
)
```

It will load `HelloWorldPlugin` if `jsonObject` have next content:

```json
{
    "plugins": [
        "PluginNameToUseInConfig"
    ]
}
```
