package dev.inmo.micro_utils.koin

import org.koin.core.definition.Definition
import org.koin.core.module.Module
import org.koin.core.qualifier.StringQualifier

/**
 * Declares a factory with a string qualifier in the Koin module.
 * This is a convenience function that wraps the string qualifier in a [StringQualifier].
 * Unlike singles, factories create a new instance each time they are requested.
 *
 * @param T The type of instance to be created by the factory
 * @param qualifier The string value to be used as a qualifier
 * @param definition The definition function that creates new instances
 * @return A Koin definition for the factory with the specified string qualifier
 */
inline fun <reified T : Any> Module.factory(
    qualifier: String,
    noinline definition: Definition<T>
) = factory(StringQualifier(qualifier), definition)


