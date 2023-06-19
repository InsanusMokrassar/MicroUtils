// THIS CODE HAVE BEEN GENERATED AUTOMATICALLY
// TO REGENERATE IT JUST DELETE FILE
// ORIGINAL FILE: Test.kt
package dev.inmo.micro_utils.koin.generator.test

import kotlin.Any
import kotlin.Boolean
import kotlin.Deprecated
import kotlin.String
import org.koin.core.Koin
import org.koin.core.definition.Definition
import org.koin.core.definition.KoinDefinition
import org.koin.core.module.Module
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope

/**
 * @return Definition by key "sampleInfo"
 */
public val Scope.sampleInfo: Test<String>
  get() = get(named("sampleInfo"))

/**
 * @return Definition by key "sampleInfo"
 */
public val Koin.sampleInfo: Test<String>
  get() = get(named("sampleInfo"))

/**
 * @return Definition by key "sampleInfo" with [parameters]
 */
public inline fun Scope.sampleInfo(noinline parameters: ParametersDefinition): Test<String> =
    get(named("sampleInfo"), parameters)

/**
 * @return Definition by key "sampleInfo" with [parameters]
 */
public inline fun Koin.sampleInfo(noinline parameters: ParametersDefinition): Test<String> =
    get(named("sampleInfo"), parameters)

/**
 * Will register [definition] with [org.koin.core.module.Module.single] and key "sampleInfo"
 */
@Deprecated(
  "This definition is old style and should not be used anymore. Use singleSampleInfo instead",
  ReplaceWith("singleSampleInfo"),
)
public fun Module.sampleInfoSingle(createdAtStart: Boolean = false,
    definition: Definition<Test<String>>): KoinDefinition<Test<String>> =
    single(named("sampleInfo"), createdAtStart = createdAtStart, definition = definition)

/**
 * Will register [definition] with [org.koin.core.module.Module.single] and key "sampleInfo"
 */
public fun Module.singleSampleInfo(createdAtStart: Boolean = false,
    definition: Definition<Test<String>>): KoinDefinition<Test<String>> =
    single(named("sampleInfo"), createdAtStart = createdAtStart, definition = definition)

/**
 * Will register [definition] with [org.koin.core.module.Module.factory] and key "sampleInfo"
 */
@Deprecated(
  "This definition is old style and should not be used anymore. Use factorySampleInfo instead",
  ReplaceWith("factorySampleInfo"),
)
public fun Module.sampleInfoFactory(definition: Definition<Test<String>>):
    KoinDefinition<Test<String>> = factory(named("sampleInfo"), definition = definition)

/**
 * Will register [definition] with [org.koin.core.module.Module.factory] and key "sampleInfo"
 */
public fun Module.factorySampleInfo(definition: Definition<Test<String>>):
    KoinDefinition<Test<String>> = factory(named("sampleInfo"), definition = definition)

/**
 * @return Definition by key "test" with [parameters]
 */
public inline fun <reified T : Any> Scope.test(noinline parameters: ParametersDefinition? = null): T
    = get(named("test"), parameters)

/**
 * @return Definition by key "test" with [parameters]
 */
public inline fun <reified T : Any> Koin.test(noinline parameters: ParametersDefinition? = null): T
    = get(named("test"), parameters)

/**
 * Will register [definition] with [org.koin.core.module.Module.single] and key "test"
 */
public inline fun <reified T : Any> Module.singleTest(createdAtStart: Boolean = false, noinline
    definition: Definition<T>): KoinDefinition<T> = single(named("test"), createdAtStart =
    createdAtStart, definition = definition)

/**
 * Will register [definition] with [org.koin.core.module.Module.factory] and key "test"
 */
public inline fun <reified T : Any> Module.factoryTest(noinline definition: Definition<T>):
    KoinDefinition<T> = factory(named("test"), definition = definition)

/**
 * @return Definition by key "testNullable" with [parameters]
 */
public inline fun <reified T : Any> Scope.testNullable(noinline parameters: ParametersDefinition? =
    null): T? = getOrNull(named("testNullable"), parameters)

/**
 * @return Definition by key "testNullable" with [parameters]
 */
public inline fun <reified T : Any> Koin.testNullable(noinline parameters: ParametersDefinition? =
    null): T? = getOrNull(named("testNullable"), parameters)

/**
 * Will register [definition] with [org.koin.core.module.Module.single] and key "testNullable"
 */
public inline fun <reified T : Any> Module.singleTestNullable(createdAtStart: Boolean = false,
    noinline definition: Definition<T>): KoinDefinition<T> = single(named("testNullable"),
    createdAtStart = createdAtStart, definition = definition)

/**
 * Will register [definition] with [org.koin.core.module.Module.factory] and key "testNullable"
 */
public inline fun <reified T : Any> Module.factoryTestNullable(noinline definition: Definition<T>):
    KoinDefinition<T> = factory(named("testNullable"), definition = definition)
