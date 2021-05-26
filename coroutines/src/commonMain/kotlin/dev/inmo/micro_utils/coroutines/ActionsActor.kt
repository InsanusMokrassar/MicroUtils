package dev.inmo.micro_utils.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlin.coroutines.*

interface ActorAction<T> {
    suspend operator fun invoke(): T
}

/**
 * Planned to use with [doWithSuspending]. Will execute incoming lambdas sequentially
 *
 * @see actor
 */
fun CoroutineScope.createActionsActor() = actor<suspend () -> Unit> {
    it()
}

/**
 * Planned to use with [doWithSuspending]. Will execute incoming lambdas sequentially
 *
 * @see safeActor
 */
inline fun CoroutineScope.createSafeActionsActor(
    noinline onException: ExceptionHandler<Unit> = defaultSafelyExceptionHandler
) = safeActor<suspend () -> Unit>(Channel.UNLIMITED, onException) {
    it()
}

/**
 * Must be use with actor created by [createActionsActor] or [createSafeActionsActor]. Will send lambda which will
 * execute [action] and return result.
 *
 * @see suspendCoroutine
 * @see safely
 */
suspend fun <T> Channel<suspend () -> Unit>.doWithSuspending(
    action: ActorAction<T>
) = suspendCoroutine<T> {
    trySend {
        safely({ e -> it.resumeWithException(e) }) {
            it.resume(action())
        }
    }
}
