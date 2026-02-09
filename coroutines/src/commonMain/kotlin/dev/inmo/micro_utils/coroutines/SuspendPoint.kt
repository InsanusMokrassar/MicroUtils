package dev.inmo.micro_utils.coroutines

import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive

/**
 * Ensures that the current coroutine context is still active and throws a [kotlinx.coroutines.CancellationException]
 * if the coroutine has been canceled.
 *
 * This function provides a convenient way to check the active status of a coroutine, which is useful
 * to identify cancellation points in long-running or suspendable operations.
 *
 * @throws kotlinx.coroutines.CancellationException if the coroutine context is no longer active.
 */
suspend fun suspendPoint() = currentCoroutineContext().ensureActive()
