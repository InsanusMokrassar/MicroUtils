package dev.inmo.micro_utils.coroutines

import dev.inmo.kslog.common.KSLog
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow

/**
 * Creates an actor-style channel that processes messages asynchronously based on markers.
 * Messages with the same marker will be processed sequentially, while messages with different markers can be processed concurrently.
 *
 * @param T The type of messages to process
 * @param channelCapacity The capacity of the underlying channel. Defaults to [Channel.UNLIMITED]
 * @param markerFactory A factory function that produces a marker for each message. Messages with the same marker
 * will be processed sequentially. Defaults to returning null, meaning all messages will be processed sequentially
 * @param logger The logger instance used for logging exceptions. Defaults to [KSLog]
 * @param block The suspending function that processes each message
 * @return A [Channel] that accepts messages to be processed
 */
fun <T> CoroutineScope.actorAsync(
    channelCapacity: Int = Channel.UNLIMITED,
    markerFactory: suspend (T) -> Any? = { null },
    logger: KSLog = KSLog,
    block: suspend (T) -> Unit
): Channel<T> {
    val channel = Channel<T>(channelCapacity)
    channel.consumeAsFlow().subscribeAsync(this, markerFactory, logger, block)
    return channel
}

@Deprecated("Use standard actosAsync instead", ReplaceWith("actorAsync(channelCapacity, markerFactory, block = block)", "dev.inmo.micro_utils.coroutines.actorAsync"))
inline fun <T> CoroutineScope.safeActorAsync(
    channelCapacity: Int = Channel.UNLIMITED,
    noinline onException: ExceptionHandler<Unit> = defaultSafelyExceptionHandler,
    noinline markerFactory: suspend (T) -> Any? = { null },
    crossinline block: suspend (T) -> Unit
): Channel<T> = actorAsync(
    channelCapacity,
    markerFactory
) {
    safely(onException) {
        block(it)
    }
}

