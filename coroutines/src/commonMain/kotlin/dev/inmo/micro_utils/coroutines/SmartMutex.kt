package dev.inmo.micro_utils.coroutines

import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.isActive
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
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
        private val _lockStateFlow = SpecialMutableStateFlow<Boolean>(locked)
        override val lockStateFlow: StateFlow<Boolean> = _lockStateFlow.asStateFlow()

        private val internalChangesMutex = Mutex()

        fun immutable() = Immutable(lockStateFlow)

        /**
         * Holds call until this [SmartMutex] will be re-locked. That means that while [isLocked] == true, [holds] will
         * wait for [isLocked] == false and then try to lock
         */
        suspend fun lock() {
            do {
                waitUnlock()
                val shouldContinue = internalChangesMutex.withLock {
                    if (_lockStateFlow.value) {
                        true
                    } else {
                        _lockStateFlow.value = true
                        false
                    }
                }
            } while (shouldContinue && currentCoroutineContext().isActive)
        }

        /**
         * Will try to lock this [SmartMutex] immediataly
         *
         * @return True if lock was successful. False otherwise
         */
        suspend fun tryLock(): Boolean {
            return if (!_lockStateFlow.value) {
                internalChangesMutex.withLock {
                    if (!_lockStateFlow.value) {
                        _lockStateFlow.value = true
                        true
                    } else {
                        false
                    }
                }
            } else {
                false
            }
        }

        /**
         * If [isLocked] == true - will change it to false and return true. If current call will not unlock this
         * [SmartMutex] - false
         */
        suspend fun unlock(): Boolean {
            return if (_lockStateFlow.value) {
                internalChangesMutex.withLock {
                    if (_lockStateFlow.value) {
                        _lockStateFlow.value = false
                        true
                    } else {
                        false
                    }
                }
            } else {
                false
            }
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
suspend fun SmartMutex.waitUnlock() = lockStateFlow.first { !it }
