package dev.inmo.micro_utils.coroutines

import kotlinx.coroutines.*
import kotlin.coroutines.*

/**
 * Trying to [Job.join] on all [this] [Job]s. The first [Job] completed its work will interrupt all others joins
 * and, if [cancelOthers] passed as true (**by default**), will also cancel all the others [Job]s
 *
 * @param scope Will be used to create [CoroutineScope.LinkedSupervisorScope] and launch joining of all [Job]s in it
 */
suspend fun Iterable<Job>.joinFirst(
    scope: CoroutineScope,
    cancelOthers: Boolean = true
): Job {
    val resultDeferred = CompletableDeferred<Job>()
    val scope = scope.LinkedSupervisorScope()
    forEach {
        scope.launch {
            it.join()
            resultDeferred.complete(it)
            scope.cancel()
        }
    }
    return resultDeferred.await().also {
        if (cancelOthers) {
            forEach {
                runCatchingSafely { it.cancel() }
            }
        }
    }
}
/**
 * Trying to [Job.join] on all [this] [Job]s. The first [Job] completed its work will interrupt all others joins
 * and, if [cancelOthers] passed as true (**by default**), will also cancel all the others [Job]s
 *
 * Creates [CoroutineScope] using [coroutineContext] for internal purposes
 */
suspend fun Iterable<Job>.joinFirst(
    cancelOthers: Boolean = true
): Job = joinFirst(CoroutineScope(coroutineContext), cancelOthers)

/**
 * Trying to [Job.join] on all [jobs]. The first [Job] completed its work will interrupt all others joins
 * and, if [cancelOthers] passed as true (**by default**), will also cancel all the others [Job]s
 *
 * @param scope Will be used to create [CoroutineScope.LinkedSupervisorScope] and launch joining of all [Job]s in it
 */
suspend fun joinFirst(
    vararg jobs: Job,
    scope: CoroutineScope,
    cancelOthers: Boolean = true
): Job = jobs.toList().joinFirst(scope, cancelOthers)

/**
 * Trying to [Job.join] on all [jobs]. The first [Job] completed its work will interrupt all others joins
 * and, if [cancelOthers] passed as true (**by default**), will also cancel all the others [Job]s
 *
 * Creates [CoroutineScope] using [coroutineContext] for internal purposes
 */
suspend fun joinFirst(
    vararg jobs: Job,
    cancelOthers: Boolean = true
): Job = joinFirst(*jobs, scope = CoroutineScope(coroutineContext), cancelOthers = cancelOthers)
