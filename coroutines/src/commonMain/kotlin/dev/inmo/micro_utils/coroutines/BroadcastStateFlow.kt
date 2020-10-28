package dev.inmo.micro_utils.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

const val defaultBroadcastStateFlowReplayCacheSize = 1

class BroadcastStateFlow<T> internal constructor(
    parentFlow: Flow<T>,
    initial: T,
    replayCacheSize: Int = defaultBroadcastStateFlowReplayCacheSize,
    replayScope: CoroutineScope
) : StateFlow<T>, Flow<T> by parentFlow {
    private val deque = ArrayDeque<T>(1).also {
        it.add(initial)
    }
    override val replayCache: List<T>
        get() = deque.toList()
    override val value: T
        get() = deque.last()

    init {
        if (replayCacheSize < 1) {
            error("Replay cache size can't be less than 1, but was $replayCacheSize")
        }
        parentFlow.onEach {
            deque.addLast(it)
            if (deque.size > replayCacheSize) {
                deque.removeFirst()
            }
        }.launchIn(replayScope)
    }
}

fun <T> BroadcastChannel<T>.asStateFlow(
    value: T,
    scope: CoroutineScope,
    replayCacheSize: Int = defaultBroadcastStateFlowReplayCacheSize
): StateFlow<T> = BroadcastStateFlow(asFlow(), value, replayCacheSize, scope)

fun <T> BroadcastChannel<T?>.asStateFlow(
    scope: CoroutineScope,
    replayCacheSize: Int = defaultBroadcastStateFlowReplayCacheSize
): StateFlow<T?> = asStateFlow(null, scope, replayCacheSize)

fun <T> broadcastStateFlow(
    initial: T, scope: CoroutineScope,
    channelSize: Int = Channel.BUFFERED,
    replayCacheSize: Int = defaultBroadcastStateFlowReplayCacheSize
) = BroadcastChannel<T>(
    channelSize
).let {
    it to it.asStateFlow(initial, scope, replayCacheSize)
}

fun <T> broadcastStateFlow(
    scope: CoroutineScope,
    channelSize: Int = Channel.BUFFERED,
    replayCacheSize: Int = defaultBroadcastStateFlowReplayCacheSize
) = broadcastStateFlow<T?>(null, scope, channelSize, replayCacheSize)

