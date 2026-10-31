package dev.inmo.micro_utils.coroutines

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/**
 * Creates a [SupervisorJob] linked to this [CoroutineContext]'s job. The new supervisor job will be a child
 * of the current job, and optionally combined with [additionalContext].
 *
 * @param additionalContext Optional additional context to combine with the supervisor job
 * @return A [CoroutineContext] containing the new supervisor job
 */
fun CoroutineContext.LinkedSupervisorJob(
    additionalContext: CoroutineContext? = null
) = SupervisorJob(job).let { if (additionalContext != null) it + additionalContext else it }

/**
 * Creates a [SupervisorJob] linked to this [CoroutineScope]'s job. The new supervisor job will be a child
 * of the current scope's job, and optionally combined with [additionalContext].
 *
 * @param additionalContext Optional additional context to combine with the supervisor job
 * @return A [CoroutineContext] containing the new supervisor job
 */
fun CoroutineScope.LinkedSupervisorJob(
    additionalContext: CoroutineContext? = null
) = coroutineContext.LinkedSupervisorJob(additionalContext)


/**
 * Creates a new [CoroutineScope] with a [SupervisorJob] linked to this [CoroutineContext]'s job.
 * The new scope's supervisor job will be a child of the current job, and optionally combined with [additionalContext].
 *
 * @param additionalContext Optional additional context to combine with the supervisor job
 * @return A new [CoroutineScope] with a linked supervisor job
 */
fun CoroutineContext.LinkedSupervisorScope(
    additionalContext: CoroutineContext? = null
) = CoroutineScope(
    this + LinkedSupervisorJob(additionalContext)
)

/**
 * Creates a new [CoroutineScope] with a [SupervisorJob] linked to this [CoroutineScope]'s job.
 * The new scope's supervisor job will be a child of the current scope's job, and optionally combined with [additionalContext].
 *
 * @param additionalContext Optional additional context to combine with the supervisor job
 * @return A new [CoroutineScope] with a linked supervisor job
 */
fun CoroutineScope.LinkedSupervisorScope(
    additionalContext: CoroutineContext? = null
) = coroutineContext.LinkedSupervisorScope(additionalContext)
