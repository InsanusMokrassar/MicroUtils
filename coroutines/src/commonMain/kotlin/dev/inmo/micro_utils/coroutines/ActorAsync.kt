package dev.inmo.micro_utils.coroutines

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow

fun <T> CoroutineScope.actorAsync(
    channelCapacity: Int = Channel.UNLIMITED,
    markerFactory: suspend (T) -> Any? = { null },
    block: suspend (T) -> Unit
): Channel<T> {
    val channel = Channel<T>(channelCapacity)
    channel.consumeAsFlow().subscribeAsync(this, markerFactory, block)
    return channel
}

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

