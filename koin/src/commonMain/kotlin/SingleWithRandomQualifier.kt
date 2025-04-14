package dev.inmo.micro_utils.koin

import org.koin.core.definition.Definition
import org.koin.core.module.Module

/**
 * Declares a single instance with a random qualifier in the Koin module.
 * This is useful when you need to declare multiple instances of the same type
 * but want them to be uniquely identifiable without manually specifying qualifiers.
 *
 * @param T The type of instance to be created
 * @param createdAtStart Whether the instance should be created when the Koin module starts (default: false)
 * @param definition The definition function that creates the instance
 * @return A Koin definition for the single instance with a random qualifier
 */
inline fun <reified T : Any> Module.singleWithRandomQualifier(
    createdAtStart: Boolean = false,
    noinline definition: Definition<T>
) = single(RandomQualifier(), createdAtStart, definition)
