package dev.inmo.micro_utils.coroutines

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow

fun <T> CoroutineScope.actor(
    channelCapacity: Int = Channel.UNLIMITED,
    block: suspend (T) -> Unit
): Channel<T> {
    val channel = Channel<T>(channelCapacity)
    channel.consumeAsFlow().subscribe(this, block)
    return channel
}

inline fun <T> CoroutineScope.safeActor(
    channelCapacity: Int = Channel.UNLIMITED,
    noinline onException: ExceptionHandler<Unit> = defaultSafelyExceptionHandler,
    crossinline block: suspend (T) -> Unit
): Channel<T> = actor(
    channelCapacity
) {
    safely(onException) {
        block(it)
    }
}

