package dev.inmo.micro_utils.koin

import com.benasher44.uuid.uuid4
import org.koin.core.definition.Definition
import org.koin.core.instance.InstanceFactory
import org.koin.core.module.Module
import org.koin.core.qualifier.StringQualifier

/**
 * Will be useful in case you need to declare some singles with one type several types, but need to separate them and do
 * not care about how :)
 */
inline fun <reified T : Any> Module.singleWithRandomQualifier(
    createdAtStart: Boolean = false,
    noinline definition: Definition<T>
) = single(uuid4().toString(), createdAtStart, definition)
