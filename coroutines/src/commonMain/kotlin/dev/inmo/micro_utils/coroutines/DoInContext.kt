package dev.inmo.micro_utils.coroutines

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/**
 * Convenience property to access [Dispatchers.Main] for UI operations.
 */
inline val UI
    get() = Dispatchers.Main

/**
 * Convenience property to access [Dispatchers.Default] for CPU-intensive operations.
 */
inline val Default
    get() = Dispatchers.Default

/**
 * Executes the given [block] in the specified coroutine [context] and returns its result.
 * This is a convenience wrapper around [withContext].
 *
 * @param T The return type of the block
 * @param context The [CoroutineContext] in which to execute the block
 * @param block The suspending function to execute
 * @return The result of executing the block
 */
suspend inline fun <T> doIn(context: CoroutineContext, noinline block: suspend CoroutineScope.() -> T) = withContext(
    context,
    block
)

/**
 * Executes the given [block] on the UI/Main dispatcher and returns its result.
 * This is a convenience function for executing UI operations.
 *
 * @param T The return type of the block
 * @param block The suspending function to execute on the UI thread
 * @return The result of executing the block
 */
suspend inline fun <T> doInUI(noinline block: suspend CoroutineScope.() -> T) = doIn(
    UI,
    block
)

/**
 * Executes the given [block] on the Default dispatcher and returns its result.
 * This is a convenience function for executing CPU-intensive operations.
 *
 * @param T The return type of the block
 * @param block The suspending function to execute on the Default dispatcher
 * @return The result of executing the block
 */
suspend inline fun <T> doInDefault(noinline block: suspend CoroutineScope.() -> T) = doIn(
    Default,
    block
)
