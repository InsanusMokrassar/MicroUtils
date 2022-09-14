package dev.inmo.micro_utils.koin

import org.koin.core.definition.Definition
import org.koin.core.instance.InstanceFactory
import org.koin.core.module.Module
import org.koin.core.qualifier.Qualifier
import org.koin.dsl.binds
import kotlin.reflect.KClass
import kotlin.reflect.full.allSuperclasses


inline fun <reified T : Any> Module.singleWithBinds(
    qualifier: Qualifier? = null,
    createdAtStart: Boolean = false,
    bindFilter: (KClass<*>) -> Boolean = { true },
    noinline definition: Definition<T>
): Pair<Module, InstanceFactory<*>> {
    return single(qualifier, createdAtStart, definition) binds (T::class.allSuperclasses.filter(bindFilter).toTypedArray())
}


inline fun <reified T : Any> Module.singleWithBinds(
    qualifier: String,
    createdAtStart: Boolean = false,
    bindFilter: (KClass<*>) -> Boolean = { true },
    noinline definition: Definition<T>
): Pair<Module, InstanceFactory<*>> {
    return single(qualifier, createdAtStart, definition) binds (T::class.allSuperclasses.filter(bindFilter).toTypedArray())
}

