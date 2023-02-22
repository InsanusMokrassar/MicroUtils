# Koin generator

It is Kotlin Symbol Processing generator for `Koin` module in `MicroUtils`.

1. [What may do this generator](#what-may-do-this-generator)
2. [How to add generator](#how-to-add-generator)

## What may do this generator

Let's imagine you want to have shortcuts in koin, to get something easily:

```kotlin
val koin: Koin// some initialization

val someUrl = koin.serverUrl
```

So, in that case you may mark containing file with next annotation (in the beginning of file):

```kotlin
@file:GenerateKoinDefinition("serverUrl", String::class, nullable = false)
```

If file is called like `Sample.kt`, will be generated file `GeneratedDefinitionsSample.kt` with next content:

```kotlin
public val Scope.serverUrl: String
  get() = get(named("serverUrl"))

public val Koin.serverUrl: String
  get() = get(named("serverUrl"))

public fun Module.serverUrlSingle(createdAtStart: Boolean = false,
    definition: Definition<String>): KoinDefinition<String> =
    single(named("serverUrl"), createdAtStart = createdAtStart, definition = definition)

public fun Module.serverUrlFactory(definition: Definition<String>):
    KoinDefinition<String> = factory(named("serverUrl"), definition = definition)
```

Besides, you may use the generics:

```kotlin
@file:GenerateKoinDefinition("sampleInfo", Sample::class, G1::class, G2::class, nullable = false)
```

Will generate:

```kotlin
public val Scope.sampleInfo: Sample<G1, G2>
  get() = get(named("sampleInfo"))

public val Koin.sampleInfo: Sample<G1, G2>
  get() = get(named("sampleInfo"))

public fun Module.sampleInfoSingle(createdAtStart: Boolean = false,
    definition: Definition<Sample<G1, G2>>): KoinDefinition<Sample<G1, G2>> =
    single(named("sampleInfo"), createdAtStart = createdAtStart, definition = definition)

public fun Module.sampleInfoFactory(definition: Definition<Sample<G1, G2>>):
    KoinDefinition<Sample<G1, G2>> = factory(named("sampleInfo"), definition = definition)
```

In case you wish not to generate single:

```kotlin
@file:GenerateKoinDefinition("sampleInfo", Sample::class, G1::class, G2::class, nullable = false, generateSingle = false)
```

And you will take next code:

```kotlin
public val Scope.sampleInfo: Sample<G1, G2>
  get() = get(named("sampleInfo"))

public val Koin.sampleInfo: Sample<G1, G2>
  get() = get(named("sampleInfo"))

public fun Module.sampleInfoFactory(definition: Definition<Sample<G1, G2>>):
    KoinDefinition<Sample<G1, G2>> = factory(named("sampleInfo"), definition = definition)
```

## How to add generator

**Note: $ksp_version in the samples above is equal to supported `ksp` version presented in `/gradle/libs.versions.toml` of project**

**Note: $microutils_version in the version of MicroUtils library in your project**

1. Add `classpath` in `build.gradle` (`classpath "com.google.devtools.ksp:symbol-processing-gradle-plugin:$ksp_version"`)
2. Add plugin to the plugins list of your module: `id "com.google.devtools.ksp"`
3. In `dependencies` block add to the required target/compile the dependency `dev.inmo:micro_utils.koin.generator:$microutils_version`:
   ```groovy
    dependencies {
        add("kspCommonMainMetadata", "dev.inmo:micro_utils.koin.generator:$microutils_version") // will work in commonMain of your multiplatform module
        add("kspJvm", "dev.inmo:micro_utils.koin.generator:$microutils_version") // will work in main of your JVM module
    }
    
    ksp { // this generator do not require any arguments and we should left `ksp` empty
    }
    ```
