package dev.inmo.micro_utils.coroutines

import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.isActive
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withLock
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * It is interface which will work like classic [Semaphore], but in difference have [permitsStateFlow] for listening of the
 * [SmartSemaphore] state.
 *
 * There is [Mutable] and [Immutable] realizations. In case you are owner and manager current state of lock, you need
 * [Mutable] [SmartSemaphore]. Otherwise, [Immutable].
 *
 * Any [Mutable] [SmartSemaphore] may produce its [Immutable] variant which will contains [permitsStateFlow] equal to its
 * [Mutable] creator
 */
sealed interface SmartSemaphore {
    val permitsStateFlow: StateFlow<Int>

    /**
     * * True - locked
     * * False - unlocked
     */
    val freePermits: Int
        get() = permitsStateFlow.value

    /**
     * Immutable variant of [SmartSemaphore]. In fact will depend on the owner of [permitsStateFlow]
     */
    class Immutable(override val permitsStateFlow: StateFlow<Int>) : SmartSemaphore

    /**
     * Mutable variant of [SmartSemaphore]. With that variant you may [lock] and [unlock]. Besides, you may create
     * [Immutable] variant of [this] instance with [immutable] factory
     *
     * @param locked Preset state of [freePermits] and its internal [_permitsStateFlow]
     */
    class Mutable(private val permits: Int, acquiredPermits: Int = 0) : SmartSemaphore {
        private val _permitsStateFlow = MutableStateFlow<Int>(permits - acquiredPermits)
        override val permitsStateFlow: StateFlow<Int> = _permitsStateFlow.asStateFlow()

        private val internalChangesMutex = Mutex(false)

        fun immutable() = Immutable(permitsStateFlow)

        private fun checkedPermits(permits: Int) = permits.coerceIn(1 .. this.permits)

        /**
         * Holds call until this [SmartSemaphore] will be re-locked. That means that while [freePermits] == true, [holds] will
         * wait for [freePermits] == false and then try to lock
         */
        suspend fun acquire(permits: Int = 1) {
            do {
                val checkedPermits = checkedPermits(permits)
                waitRelease(checkedPermits)
                val shouldContinue = internalChangesMutex.withLock {
                    if (_permitsStateFlow.value < checkedPermits) {
                        true
                    } else {
                        _permitsStateFlow.value -= checkedPermits
                        false
                    }
                }
            } while (shouldContinue && currentCoroutineContext().isActive)
        }

        /**
         * Will try to lock this [SmartSemaphore] immediataly
         *
         * @return True if lock was successful. False otherwise
         */
        suspend fun tryAcquire(permits: Int = 1): Boolean {
            val checkedPermits = checkedPermits(permits)
            return if (_permitsStateFlow.value >= checkedPermits) {
                internalChangesMutex.withLock {
                    if (_permitsStateFlow.value >= checkedPermits) {
                        _permitsStateFlow.value -= checkedPermits
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
         * If [freePermits] == true - will change it to false and return true. If current call will not unlock this
         * [SmartSemaphore] - false
         */
        suspend fun release(permits: Int = 1): Boolean {
            val checkedPermits = checkedPermits(permits)
            return if (this.permits - _permitsStateFlow.value > checkedPermits) {
                internalChangesMutex.withLock {
                    if (this.permits - _permitsStateFlow.value > checkedPermits) {
                        _permitsStateFlow.value += checkedPermits
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
 * Will call [SmartSemaphore.Mutable.lock], then execute [action] and return the result after [SmartSemaphore.Mutable.unlock]
 */
@OptIn(ExperimentalContracts::class)
suspend inline fun <T> SmartSemaphore.Mutable.withAcquire(permits: Int = 1, action: () -> T): T {
    contract {
        callsInPlace(action, InvocationKind.EXACTLY_ONCE)
    }

    acquire(permits)
    try {
        return action()
    } finally {
        release(permits)
    }
}

/**
 * Will wait until the [SmartSemaphore.permitsStateFlow] of [this] instance will have [permits] count free permits.
 *
 * Anyway, after the end of this block there are no any guaranties that [SmartSemaphore.freePermits] >= [permits] due to
 * the fact that some other parties may lock it again
 */
suspend fun SmartSemaphore.waitRelease(permits: Int = 1) = permitsStateFlow.first { it >= permits }
