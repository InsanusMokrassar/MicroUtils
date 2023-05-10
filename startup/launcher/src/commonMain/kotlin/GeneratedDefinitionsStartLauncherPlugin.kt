// THIS CODE HAVE BEEN GENERATED AUTOMATICALLY
// TO REGENERATE IT JUST DELETE FILE
// ORIGINAL FILE: StartLauncherPlugin.kt
package dev.inmo.micro_utils.startup.launcher

import kotlin.Boolean
import kotlinx.serialization.json.Json
import org.koin.core.Koin
import org.koin.core.definition.Definition
import org.koin.core.definition.KoinDefinition
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope

/**
 * @return Definition by key "baseJsonProvider"
 */
public val Scope.baseJsonProvider: Json?
  get() = getOrNull(named("baseJsonProvider"))

/**
 * @return Definition by key "baseJsonProvider"
 */
public val Koin.baseJsonProvider: Json?
  get() = getOrNull(named("baseJsonProvider"))

/**
 * Will register [definition] with [org.koin.core.module.Module.single] and key "baseJsonProvider"
 */
public fun Module.baseJsonProviderSingle(createdAtStart: Boolean = false, definition: Definition<Json>):
    KoinDefinition<Json> = single(named("baseJsonProvider"), createdAtStart = createdAtStart, definition
    = definition)

/**
 * Will register [definition] with [org.koin.core.module.Module.factory] and key "baseJsonProvider"
 */
public fun Module.baseJsonProviderFactory(definition: Definition<Json>): KoinDefinition<Json> =
    factory(named("baseJsonProvider"), definition = definition)
