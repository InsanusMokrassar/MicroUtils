package dev.inmo.micro_utils.koin

import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import org.koin.java.KoinJavaComponent
import kotlin.reflect.KClass

fun <T> lazyInject(
    kClassFactory: () -> KClass<*>,
    qualifier: Qualifier? = null,
    parameters: ParametersDefinition? = null
): Lazy<T> {
    return lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        KoinJavaComponent.get(kClassFactory().java, qualifier, parameters)
    }
}

fun <T> lazyInject(
    kClass: KClass<*>,
    qualifier: Qualifier? = null,
    parameters: ParametersDefinition? = null
): Lazy<T> = lazyInject({ kClass }, qualifier, parameters)

inline fun <reified T> lazyInject(
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null
): Lazy<T> = lazyInject(T::class, qualifier, parameters)
