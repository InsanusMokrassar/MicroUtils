package dev.inmo.micro_utils.coroutines

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred

/**
 * Wraps this value in a completed [Deferred]. The resulting [Deferred] is immediately completed with this value.
 * Useful for converting synchronous values to [Deferred] in contexts that expect deferred values.
 *
 * @param T The type of the value
 * @return A [Deferred] that is already completed with this value
 */
val <T> T.asDeferred: Deferred<T>
    get() = CompletableDeferred(this)
