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

