package dev.inmo.micro_utils.coroutines

import kotlinx.coroutines.*

/**
 * Convenience property to access [Dispatchers.IO] for I/O-bound operations.
 * This dispatcher is optimized for offloading blocking I/O tasks to a shared pool of threads.
 */
val IO
    get() = Dispatchers.IO

/**
 * Executes the given [block] on the IO dispatcher and returns its result.
 * This is a convenience function for executing I/O-bound operations like reading files,
 * network requests, or database queries.
 *
 * @param T The return type of the block
 * @param block The suspending function to execute on the IO dispatcher
 * @return The result of executing the block
 */
suspend inline fun <T> doInIO(noinline block: suspend CoroutineScope.() -> T) = doIn(
    IO,
    block
)
