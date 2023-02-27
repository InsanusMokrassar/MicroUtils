# Koin generator

It is Kotlin Symbol Processing generator for simple creating of typical models: `New` and `Registered`.

1. [What may do this generator](#what-may-do-this-generator)
2. [How to add generator](#how-to-add-generator)

## What may do this generator

So, you have several known things related to models:

* Interface with all necessary properties
* Id class or some registered marker

Minimal sample will be next:

```kotlin
@GenerateCRUDModel
interface Sample {
   val property1: String
   val property2: Int
}
```

And generator will create:

```kotlin
@Serializable
@SerialName("NewSample")
data class NewSample(
    override val property1: String,
    override val property2: Int,
) : Sample

@Serializable
@SerialName("RegisteredSample")
data class RegisteredSample(
    override val property1: String,
    override val property2: Int,
) : Sample

fun Sample.asNew(): NewSample = NewSample(property1, property2)

fun Sample.asRegistered(): RegisteredSample = RegisteredSample(property1, property2)
```

But in most cases you will need to create some id class and registered interface:

```kotlin
@Serializable
@JvmInline
value class SampleId(
   val long: Long
)

sealed interface IRegisteredSample : Sample {
   val id: SampleId

   @GenerateCRUDModelExcludeOverride
   val excludedProperty2: Boolean
      get() = false
}
```

As you may see, we have added `GenerateCRUDModelExcludeOverride` annotation. Properties marked with this annotation
WILL NOT be inclued into overriding in registered class (or your base interface if used there). So, if you will wish to
create model with id, use next form:

```kotlin
@GenerateCRUDModel(IRegisteredSample::class)
interface Sample {
   val property1: String
   val property2: Int
}
```

And generated registered class will be changed:

```kotlin
@Serializable
@SerialName(value = "NewSample")
data class NewSample(
  override val property1: String,
  override val property2: Int,
) : Sample

@Serializable
@SerialName(value = "RegisteredSample")
data class RegisteredSample(
  override val id: SampleId,
  override val property1: String,
  override val property2: Int,
) : Sample, IRegisteredSample

fun Sample.asNew(): NewSample = NewSample(property1, property2)

fun Sample.asRegistered(id: SampleId): RegisteredSample = RegisteredSample(id, property1, property2)
```

So, full sample will look like:

```kotlin
/**
 * Your id value class. In fact, but it is not necessary
 */
@Serializable
@JvmInline
value class SampleId(
   val long: Long
)

@GenerateCRUDModel(IRegisteredSample::class)
sealed interface Sample {
   val property1: String
   val property2: Int

   @GenerateCRUDModelExcludeOverride
   val excludedProperty: String
      get() = "excluded"
}

sealed interface IRegisteredSample : Sample {
   val id: SampleId

   @GenerateCRUDModelExcludeOverride
   val excludedProperty2: Boolean
      get() = false
}
```

You always may:

* Use any number of registered classes
* Disable serialization for models
* Disable serial names generation

## How to add generator

**Note: $ksp_version in the samples above is equal to supported `ksp` version presented in `/gradle/libs.versions.toml` of project**

**Note: $microutils_version in the version of MicroUtils library in your project**

1. Add `classpath` in `build.gradle` (`classpath "com.google.devtools.ksp:symbol-processing-gradle-plugin:$ksp_version"`)
2. Add plugin to the plugins list of your module: `id "com.google.devtools.ksp"`
3. In `dependencies` block add to the required target/compile the dependency `dev.inmo:micro_utils.repos.generator:$microutils_version`:
   ```groovy
    dependencies {
        add("kspCommonMainMetadata", "dev.inmo:micro_utils.repos.generator:$microutils_version") // will work in commonMain of your multiplatform module
        add("kspJvm", "dev.inmo:micro_utils.repos.generator:$microutils_version") // will work in main of your JVM module
    }
    
    ksp { // this generator do not require any arguments and we should left `ksp` empty
    }
    ```
