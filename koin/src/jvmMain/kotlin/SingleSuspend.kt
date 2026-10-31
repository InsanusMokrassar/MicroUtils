package dev.inmo.micro_utils.koin

import dev.inmo.micro_utils.coroutines.doSynchronously
import kotlinx.coroutines.CoroutineScope
import org.koin.core.module.Module
import org.koin.core.parameter.ParametersHolder
import org.koin.core.qualifier.StringQualifier
import org.koin.core.scope.Scope

inline fun <reified T : Any> Module.singleSuspend(
    qualifier: StringQualifier,
    createdAtStart: Boolean = false,
    coroutineScope: CoroutineScope? = null,
    noinline definition: suspend Scope.(ParametersHolder) -> T
) = single(
    qualifier,
    createdAtStart,
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

