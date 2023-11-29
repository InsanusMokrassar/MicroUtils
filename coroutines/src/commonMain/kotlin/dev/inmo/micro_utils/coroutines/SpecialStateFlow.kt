package dev.inmo.micro_utils.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.internal.synchronized

interface SpecialMutableStateFlow<T> : MutableStateFlow<T> {
    class Default<T>(
        initialValue: T,
        internalScope: CoroutineScope = CoroutineScope(Dispatchers.Default)
    ) : SpecialMutableStateFlow<T> {
        private val internalSharedFlow: MutableSharedFlow<T> = MutableSharedFlow(
            replay = 0,
            extraBufferCapacity = 1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST
        )
        private val publicSharedFlow: MutableSharedFlow<T> = MutableSharedFlow(
            replay = 0,
            extraBufferCapacity = 1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST
        )

        private var _value: T = initialValue
        override var value: T
            get() {
                return _value
            }
            set(value) {
                tryEmit(value)
            }
        private val job = internalSharedFlow.subscribe(internalScope) {
            if (_value != it) {
                _value = it
                publicSharedFlow.emit(it)
            }
        }

        override val replayCache: List<T>
            get() = publicSharedFlow.replayCache
        override val subscriptionCount: StateFlow<Int>
            get() = publicSharedFlow.subscriptionCount

        init {
            value = initialValue
        }

        override fun compareAndSet(expect: T, update: T): Boolean {
            return if (value == expect) {
                tryEmit(update)
            } else {
                false
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
}
