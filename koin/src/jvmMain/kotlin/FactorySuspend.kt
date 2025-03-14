package dev.inmo.micro_utils.koin

import dev.inmo.micro_utils.coroutines.doSynchronously
import kotlinx.coroutines.CoroutineScope
import org.koin.core.module.Module
import org.koin.core.parameter.ParametersHolder
import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.StringQualifier
import org.koin.core.scope.Scope
import kotlin.reflect.KClass

inline fun <reified T : Any> Module.factorySuspend(
    qualifier: Qualifier? = null,
    coroutineScope: CoroutineScope? = null,
    noinline definition: suspend Scope.(ParametersHolder) -> T
) = factory(
    qualifier,
    if (coroutineScope == null) {
        {
            doSynchronously {
                definition(it)
            }
        }
    } else {
        {
            coroutineScope.doSynchronously {
                definition(it)
            }
        }
    }
)

