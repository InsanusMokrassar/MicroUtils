package dev.inmo.micro_utils.koin

import org.koin.core.definition.Definition
import org.koin.core.instance.InstanceFactory
import org.koin.core.module.Module
import org.koin.core.qualifier.Qualifier
import org.koin.dsl.binds
import kotlin.reflect.KClass
import kotlin.reflect.full.allSuperclasses


inline fun <reified T : Any> Module.factoryWithBinds(
    qualifier: Qualifier? = null,
    bindFilter: (KClass<*>) -> Boolean = { true },
    noinline definition: Definition<T>
): Pair<Module, InstanceFactory<*>> {
    return factory(qualifier, definition) binds (T::class.allSuperclasses.filter(bindFilter).toTypedArray())
}

inline fun <reified T : Any> Module.factoryWithBinds(
    qualifier: String,
    bindFilter: (KClass<*>) -> Boolean = { true },
    noinline definition: Definition<T>
): Pair<Module, InstanceFactory<*>> {
    return factory(qualifier, definition) binds (T::class.allSuperclasses.filter(bindFilter).toTypedArray())
}

