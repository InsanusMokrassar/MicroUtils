package dev.inmo.micro_utils.koin

import org.koin.core.definition.Definition
import org.koin.core.module.Module
import org.koin.core.qualifier.StringQualifier

/**
 * Declares a single instance with a string qualifier in the Koin module.
 * This is a convenience function that wraps the string qualifier in a [StringQualifier].
 *
 * @param T The type of instance to be created
 * @param qualifier The string value to be used as a qualifier
 * @param createdAtStart Whether the instance should be created when the Koin module starts (default: false)
 * @param definition The definition function that creates the instance
 * @return A Koin definition for the single instance with the specified string qualifier
 */
inline fun <reified T : Any> Module.single(
    qualifier: String,
    createdAtStart: Boolean = false,
    noinline definition: Definition<T>
) = single(StringQualifier(qualifier), createdAtStart, definition)

