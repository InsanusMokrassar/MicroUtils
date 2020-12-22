package dev.inmo.micro_utils.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

fun <T> CoroutineScope.actor(
    channelCapacity: Int = Channel.UNLIMITED,
    block: suspend (T) -> Unit
): Channel<T> {
    val channel = Channel<T>(channelCapacity)
    launch {
        for (data in channel) {
            block(data)
        }
    }
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

