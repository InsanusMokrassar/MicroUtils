package dev.inmo.micro_utils.koin

import org.koin.core.definition.Definition
import org.koin.core.module.Module

/**
 * Declares a factory with a random qualifier in the Koin module.
 * This is useful when you need to declare multiple factory definitions of the same type
 * but want them to be uniquely identifiable without manually specifying qualifiers.
 * Unlike singles, factories create a new instance each time they are requested.
 *
 * @param T The type of instance to be created by the factory
 * @param definition The definition function that creates new instances
 * @return A Koin definition for the factory with a random qualifier
 */
inline fun <reified T : Any> Module.factoryWithRandomQualifier(
    noinline definition: Definition<T>
) = factory(RandomQualifier(), definition)
