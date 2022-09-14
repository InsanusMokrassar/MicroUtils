package dev.inmo.micro_utils.koin

import com.benasher44.uuid.uuid4
import org.koin.core.definition.Definition
import org.koin.core.instance.InstanceFactory
import org.koin.core.module.Module
import org.koin.dsl.binds
import kotlin.reflect.KClass
import kotlin.reflect.full.allSuperclasses

inline fun <reified T : Any> Module.factoryWithRandomQualifierAndBinds(
    bindFilter: (KClass<*>) -> Boolean = { true },
    noinline definition: Definition<T>
): Pair<Module, InstanceFactory<*>> {
    return factoryWithBinds(uuid4().toString(), bindFilter, definition)
}
