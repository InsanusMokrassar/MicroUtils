package dev.inmo.micro_utils.coroutines

import kotlinx.coroutines.channels.*
import kotlinx.coroutines.flow.*

@Suppress("FunctionName")
fun <T> BroadcastFlow(
    internalChannelSize: Int = Channel.BUFFERED
): BroadcastFlow<T> {
    val channel = BroadcastChannel<T>(internalChannelSize)

    return BroadcastFlow(
        channel,
        channel.asFlow()
    )
}

class BroadcastFlow<T> internal constructor(
    private val channel: BroadcastChannel<T>,
    private val flow: Flow<T>
): Flow<T> by flow, SendChannel<T> by channel
