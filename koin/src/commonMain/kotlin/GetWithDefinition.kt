package dev.inmo.micro_utils.koin

import org.koin.core.Koin
import org.koin.core.definition.BeanDefinition
import org.koin.core.definition.KoinDefinition
import org.koin.core.instance.InstanceFactory
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.scope.Scope

fun <T> Koin.get(definition: BeanDefinition<T>, parameters: ParametersDefinition? = null): T = get(
    definition.primaryType,
    definition.qualifier,
    parameters
)

fun <T> Koin.get(definition: InstanceFactory<T>, parameters: ParametersDefinition? = null): T = get(
    definition.beanDefinition,
    parameters
)

fun <T> Koin.get(definition: KoinDefinition<T>, parameters: ParametersDefinition? = null): T = get(
    definition.factory,
    parameters
)

fun <T> Scope.get(definition: BeanDefinition<T>, parameters: ParametersDefinition? = null): T = get(
    definition.primaryType,
    definition.qualifier,
    parameters
)

fun <T> Scope.get(definition: InstanceFactory<T>, parameters: ParametersDefinition? = null): T = get(
    definition.beanDefinition,
    parameters
)

fun <T> Scope.get(definition: KoinDefinition<T>, parameters: ParametersDefinition? = null): T = get(
    definition.factory,
    parameters
)
