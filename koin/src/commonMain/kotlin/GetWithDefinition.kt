package dev.inmo.micro_utils.koin

import org.koin.core.Koin
import org.koin.core.definition.BeanDefinition
import org.koin.core.definition.KoinDefinition
import org.koin.core.instance.InstanceFactory
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.scope.Scope

/**
 * Retrieves an instance of type [T] from the Koin container using a [BeanDefinition].
 *
 * @param T The type of instance to retrieve
 * @param definition The bean definition to use for instance retrieval
 * @param parameters Optional parameters to pass to the instance constructor
 * @return An instance of type [T]
 */
fun <T> Koin.get(definition: BeanDefinition<T>, parameters: ParametersDefinition? = null): T = get(
    definition.primaryType,
    definition.qualifier,
    parameters
)

/**
 * Retrieves an instance of type [T] from the Koin container using an [InstanceFactory].
 *
 * @param T The type of instance to retrieve
 * @param definition The instance factory to use for instance retrieval
 * @param parameters Optional parameters to pass to the instance constructor
 * @return An instance of type [T]
 */
fun <T> Koin.get(definition: InstanceFactory<T>, parameters: ParametersDefinition? = null): T = get(
    definition.beanDefinition,
    parameters
)

/**
 * Retrieves an instance of type [T] from the Koin container using a [KoinDefinition].
 *
 * @param T The type of instance to retrieve
 * @param definition The Koin definition to use for instance retrieval
 * @param parameters Optional parameters to pass to the instance constructor
 * @return An instance of type [T]
 */
fun <T> Koin.get(definition: KoinDefinition<T>, parameters: ParametersDefinition? = null): T = get(
    definition.factory,
    parameters
)

/**
 * Retrieves an instance of type [T] from the current scope using a [BeanDefinition].
 *
 * @param T The type of instance to retrieve
 * @param definition The bean definition to use for instance retrieval
 * @param parameters Optional parameters to pass to the instance constructor
 * @return An instance of type [T]
 */
fun <T> Scope.get(definition: BeanDefinition<T>, parameters: ParametersDefinition? = null): T = get(
    definition.primaryType,
    definition.qualifier,
    parameters
)

/**
 * Retrieves an instance of type [T] from the current scope using an [InstanceFactory].
 *
 * @param T The type of instance to retrieve
 * @param definition The instance factory to use for instance retrieval
 * @param parameters Optional parameters to pass to the instance constructor
 * @return An instance of type [T]
 */
fun <T> Scope.get(definition: InstanceFactory<T>, parameters: ParametersDefinition? = null): T = get(
    definition.beanDefinition,
    parameters
)

/**
 * Retrieves an instance of type [T] from the current scope using a [KoinDefinition].
 *
 * @param T The type of instance to retrieve
 * @param definition The Koin definition to use for instance retrieval
 * @param parameters Optional parameters to pass to the instance constructor
 * @return An instance of type [T]
 */
fun <T> Scope.get(definition: KoinDefinition<T>, parameters: ParametersDefinition? = null): T = get(
    definition.factory,
    parameters
)
