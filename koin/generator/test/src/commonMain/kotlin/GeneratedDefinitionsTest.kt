// THIS CODE HAVE BEEN GENERATED AUTOMATICALLY
// TO REGENERATE IT JUST DELETE FILE
// ORIGINAL FILE: Test.kt
package dev.inmo.micro_utils.koin.generator.test

import kotlin.Boolean
import kotlin.String
import org.koin.core.Koin
import org.koin.core.definition.Definition
import org.koin.core.definition.KoinDefinition
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope

public val Scope.sampleInfo: Test<String>
  get() = get(named("sampleInfo"))

public val Koin.sampleInfo: Test<String>
  get() = get(named("sampleInfo"))

public fun Module.sampleInfoSingle(createdAtStart: Boolean = false,
    definition: Definition<Test<String>>): KoinDefinition<Test<String>> =
    single(named("sampleInfo"), createdAtStart = createdAtStart, definition = definition)

public fun Module.sampleInfoFactory(definition: Definition<Test<String>>):
    KoinDefinition<Test<String>> = factory(named("sampleInfo"), definition = definition)
