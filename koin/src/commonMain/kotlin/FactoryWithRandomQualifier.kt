package dev.inmo.micro_utils.koin

import org.koin.core.definition.Definition
import org.koin.core.module.Module

/**
 * Will be useful in case you need to declare some singles with one type several types, but need to separate them and do
 * not care about how :)
 */
inline fun <reified T : Any> Module.factoryWithRandomQualifier(
    noinline definition: Definition<T>
) = factory(RandomQualifier(), definition)
