package dev.inmo.micro_utils.coroutines

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

fun CoroutineContext.LinkedSupervisorJob(
    additionalContext: CoroutineContext? = null
) = SupervisorJob(job).let { if (additionalContext != null) it + additionalContext else it }
fun CoroutineScope.LinkedSupervisorJob(
    additionalContext: CoroutineContext? = null
) = coroutineContext.LinkedSupervisorJob(additionalContext)

fun CoroutineScope.LinkedSupervisorScope(
    additionalContext: CoroutineContext? = null
) = CoroutineScope(
    coroutineContext + LinkedSupervisorJob(additionalContext)
)
