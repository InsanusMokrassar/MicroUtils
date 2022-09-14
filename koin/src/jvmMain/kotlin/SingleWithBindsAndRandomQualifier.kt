package dev.inmo.micro_utils.koin

import org.koin.core.definition.Definition
import org.koin.core.instance.InstanceFactory
import org.koin.core.module.Module
import kotlin.reflect.KClass

inline fun <reified T : Any> Module.singleWithRandomQualifierAndBinds(
    createdAtStart: Boolean = false,
    bindFilter: (KClass<*>) -> Boolean = { true },
    noinline definition: Definition<T>
): Pair<Module, InstanceFactory<*>> {
    return singleWithBinds(RandomQualifier(), createdAtStart, bindFilter, definition)
}
