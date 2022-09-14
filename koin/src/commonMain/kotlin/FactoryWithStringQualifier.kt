package dev.inmo.micro_utils.koin

import org.koin.core.definition.Definition
import org.koin.core.module.Module
import org.koin.core.qualifier.StringQualifier

inline fun <reified T : Any> Module.factory(
    qualifier: String,
    noinline definition: Definition<T>
) = factory(StringQualifier(qualifier), definition)


