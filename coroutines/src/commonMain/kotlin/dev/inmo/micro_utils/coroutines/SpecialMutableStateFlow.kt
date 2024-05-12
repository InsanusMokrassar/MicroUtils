package dev.inmo.micro_utils.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.internal.SynchronizedObject
import kotlinx.coroutines.internal.synchronized
import kotlin.coroutines.CoroutineContext

/**
 * Works like [StateFlow], but guarantee that latest value update will always be delivered to
 * each active subscriber
 */
open class SpecialMutableStateFlow<T>(
    initialValue: T
) : MutableStateFlow<T>, FlowCollector<T>, MutableSharedFlow<T> {
    @OptIn(InternalCoroutinesApi::class)
    private val syncObject = SynchronizedObject()
    protected val sharingFlow: MutableSharedFlow<T> = MutableSharedFlow(
        replay = 1,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    @OptIn(InternalCoroutinesApi::class)
    override var value: T = initialValue
        set(value) {
            synchronized(syncObject) {
                if (field != value) {
                    field = value
                    sharingFlow.tryEmit(value)
                }
            }
        }

    override val replayCache: List<T>
        get() = sharingFlow.replayCache
    override val subscriptionCount: StateFlow<Int>
        get() = sharingFlow.subscriptionCount

    init {
        sharingFlow.tryEmit(initialValue)
    }

    override fun compareAndSet(expect: T, update: T): Boolean {
        if (expect == value) {
            value = update
        }
        return expect == value
    }

    @ExperimentalCoroutinesApi
    override fun resetReplayCache() = sharingFlow.resetReplayCache()

    override fun tryEmit(value: T): Boolean {
        return compareAndSet(this.value, value)
    }

    override suspend fun emit(value: T) {
        compareAndSet(this.value, value)
    }

    override suspend fun collect(collector: FlowCollector<T>) = sharingFlow.collect(collector)
}
