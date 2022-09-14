package dev.inmo.micro_utils.koin

import org.koin.core.definition.Definition
import org.koin.core.module.Module
import org.koin.core.qualifier.StringQualifier

inline fun <reified T : Any> Module.single(
    qualifier: String,
    createdAtStart: Boolean = false,
    noinline definition: Definition<T>
) = single(StringQualifier(qualifier), createdAtStart, definition)

