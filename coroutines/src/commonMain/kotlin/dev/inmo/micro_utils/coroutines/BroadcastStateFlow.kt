package dev.inmo.micro_utils.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

class BroadcastStateFlow<T> internal constructor(
    parentFlow: Flow<T>,
    private val stateGetter: () -> T
) : StateFlow<T>, Flow<T> by parentFlow {
    override val value: T
        get() = stateGetter()
}

fun <T> BroadcastChannel<T>.asStateFlow(value: T, scope: CoroutineScope): StateFlow<T> = asFlow().let {
    var state: T = value
    it.onEach { state = it }.launchIn(scope)
    BroadcastStateFlow(it) {
        state
    }
}

fun <T> BroadcastChannel<T?>.asStateFlow(scope: CoroutineScope): StateFlow<T?> = asStateFlow(null, scope)

fun <T> broadcastStateFlow(initial: T, scope: CoroutineScope, channelSize: Int = Channel.BUFFERED) = BroadcastChannel<T>(
    channelSize
).let {
    it to it.asStateFlow(initial, scope)
}

fun <T> broadcastStateFlow(scope: CoroutineScope, channelSize: Int = Channel.BUFFERED) = broadcastStateFlow<T?>(null, scope, channelSize)

