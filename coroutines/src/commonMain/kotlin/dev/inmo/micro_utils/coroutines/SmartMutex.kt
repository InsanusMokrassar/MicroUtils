package dev.inmo.micro_utils.coroutines

import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.isActive
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * It is interface which will work like classic [Mutex], but in difference have [lockStateFlow] for listening of the
 * [SmartMutex] state.
 *
 * There is [Mutable] and [Immutable] realizations. In case you are owner and manager current state of lock, you need
 * [Mutable] [SmartMutex]. Otherwise, [Immutable].
 *
 * Any [Mutable] [SmartMutex] may produce its [Immutable] variant which will contains [lockStateFlow] equal to its
 * [Mutable] creator
 */
sealed interface SmartMutex {
    val lockStateFlow: StateFlow<Boolean>

    /**
     * * True - locked
     * * False - unlocked
     */
    val isLocked: Boolean
        get() = lockStateFlow.value

    /**
     * Immutable variant of [SmartMutex]. In fact will depend on the owner of [lockStateFlow]
     */
    class Immutable(override val lockStateFlow: StateFlow<Boolean>) : SmartMutex

    /**
     * Mutable variant of [SmartMutex]. With that variant you may [lock] and [unlock]. Besides, you may create
     * [Immutable] variant of [this] instance with [immutable] factory
     *
     * @param locked Preset state of [isLocked] and its internal [_lockStateFlow]
     */
    class Mutable(locked: Boolean = false) : SmartMutex {
        private val _lockStateFlow = MutableRedeliverStateFlow<Boolean>(locked)
        override val lockStateFlow: StateFlow<Boolean> = _lockStateFlow.asStateFlow()

        private val internalChangesMutex = Mutex()
        private val backingMutex = Mutex()

        fun immutable() = Immutable(lockStateFlow)

        suspend fun lock() {
            backingMutex.lock()
            internalChangesMutex.withLock {
                _lockStateFlow.value = true
            }
        }

        suspend fun tryLock(): Boolean {
            val result = backingMutex.tryLock()
            if (result) internalChangesMutex.withLock {
                _lockStateFlow.value = true
            }
            return result
        }

        suspend fun unlock(): Boolean {
            val tryUnlock = internalChangesMutex.withLock {
                _lockStateFlow.value.also { if (it) _lockStateFlow.value = false }
            }
            if (tryUnlock) backingMutex.unlock()
            return tryUnlock
        }
    }
}

/**
 * Will call [SmartMutex.Mutable.lock], then execute [action] and return the result after [SmartMutex.Mutable.unlock]
 */
@OptIn(ExperimentalContracts::class)
suspend inline fun <T> SmartMutex.Mutable.withLock(action: () -> T): T {
    contract {
        callsInPlace(action, InvocationKind.EXACTLY_ONCE)
    }

    lock()
    try {
        return action()
    } finally {
        unlock()
    }
}

/**
 * Will wait until the [SmartMutex.lockStateFlow] of [this] instance will be false.
 *
 * Anyway, after the end of this block there are no any guaranties that [SmartMutex.isLocked] == false due to the fact
 * that some other parties may lock it again
 */
suspend fun SmartMutex.waitUnlock(): Boolean {
    currentCoroutineContext().ensureActive()
    return if (!lockStateFlow.value) {
        false
    } else {
        lockStateFlow.first { !it }
    }
}
