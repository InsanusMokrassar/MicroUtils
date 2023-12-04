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

/**
 * Works like [StateFlow], but guarantee that latest value update will always be delivered to
 * each active subscriber
 */
open class SpecialMutableStateFlow<T>(
    initialValue: T,
    internalScope: CoroutineScope = CoroutineScope(Dispatchers.Default)
) : MutableStateFlow<T>, FlowCollector<T>, MutableSharedFlow<T> {
    @OptIn(InternalCoroutinesApi::class)
    private val syncObject = SynchronizedObject()
    protected val internalSharedFlow: MutableSharedFlow<T> = MutableSharedFlow(
        replay = 0,
        extraBufferCapacity = 2,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    protected val publicSharedFlow: MutableSharedFlow<T> = MutableSharedFlow(
        replay = 1,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    protected var _value: T = initialValue
    override var value: T
        get() = _value
        set(value) {
            doOnChangeAction(value)
        }
    protected val job = internalSharedFlow.subscribe(internalScope) {
        doOnChangeAction(it)
    }

    override val replayCache: List<T>
        get() = publicSharedFlow.replayCache
    override val subscriptionCount: StateFlow<Int>
        get() = publicSharedFlow.subscriptionCount

    @OptIn(InternalCoroutinesApi::class)
    override fun compareAndSet(expect: T, update: T): Boolean {
        return synchronized(syncObject) {
            if (expect == _value && update != _value) {
                doOnChangeAction(update)
            }
            expect == _value
        }
    }

    protected open fun onChangeWithoutSync(value: T) {
        _value = value
        publicSharedFlow.tryEmit(value)
    }
    @OptIn(InternalCoroutinesApi::class)
    protected open fun doOnChangeAction(value: T) {
        synchronized(syncObject) {
            if (_value != value) {
                onChangeWithoutSync(value)
            }
        }
    }

    @ExperimentalCoroutinesApi
    override fun resetReplayCache() = publicSharedFlow.resetReplayCache()

    override fun tryEmit(value: T): Boolean {
        return internalSharedFlow.tryEmit(value)
    }

    override suspend fun emit(value: T) {
        internalSharedFlow.emit(value)
    }

    override suspend fun collect(collector: FlowCollector<T>) = publicSharedFlow.collect(collector)
}
