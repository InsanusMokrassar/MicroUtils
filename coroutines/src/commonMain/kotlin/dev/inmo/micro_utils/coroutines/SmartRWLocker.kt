package dev.inmo.micro_utils.coroutines

import kotlinx.coroutines.CancellationException
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * Composite mutex which works with next rules:
 *
 * * [acquireRead] require to [writeMutex] to be free. Then it will take one lock from [readSemaphore]
 * * [releaseRead] will just free up one permit in [readSemaphore]
 * * [lockWrite] will lock [writeMutex] and then await while all [readSemaphore] will be freed. If coroutine will be
 * cancelled during read semaphore freeing, locking will be cancelled too with [SmartMutex.Mutable.unlock]ing of [writeMutex]
 * * [unlockWrite] will just unlock [writeMutex]
 */
class SmartRWLocker(private val readPermits: Int = Int.MAX_VALUE, writeIsLocked: Boolean = false) {
    private val _readSemaphore = SmartSemaphore.Mutable(permits = readPermits, acquiredPermits = if (writeIsLocked) readPermits else 0)
    private val _writeMutex = SmartMutex.Mutable(locked = writeIsLocked)

    val readSemaphore: SmartSemaphore.Immutable = _readSemaphore.immutable()
    val writeMutex: SmartMutex.Immutable = _writeMutex.immutable()

    /**
     * Do lock in [readSemaphore] inside of [writeMutex] locking
     */
    suspend fun acquireRead() {
        _writeMutex.waitUnlock()
        _readSemaphore.acquire()
    }

    /**
     * Release one read permit in [readSemaphore]
     */
    suspend fun releaseRead(): Boolean {
        return _readSemaphore.release()
    }

    /**
     * Locking [writeMutex] and wait while all [readSemaphore] permits will be freed
     */
    suspend fun lockWrite() {
        _writeMutex.lock()
        try {
            _readSemaphore.acquire(readPermits)
        } catch (e: CancellationException) {
            _writeMutex.unlock()
            throw e
        }
    }

    /**
     * Unlock [writeMutex]
     */
    suspend fun unlockWrite(): Boolean {
        return _writeMutex.unlock().also {
            if (it) {
                _readSemaphore.release(readPermits)
            }
        }
    }
}

/**
 * Will call [SmartSemaphore.Mutable.lock], then execute [action] and return the result after [SmartSemaphore.Mutable.unlock]
 */
@OptIn(ExperimentalContracts::class)
suspend inline fun <T> SmartRWLocker.withReadAcquire(action: () -> T): T {
    contract {
        callsInPlace(action, InvocationKind.EXACTLY_ONCE)
    }

    acquireRead()
    try {
        return action()
    } finally {
        releaseRead()
    }
}

/**
 * Will wait until the [SmartSemaphore.permitsStateFlow] of [this] instance will have [permits] count free permits.
 *
 * Anyway, after the end of this block there are no any guaranties that [SmartSemaphore.freePermits] >= [permits] due to
 * the fact that some other parties may lock it again
 */
suspend fun SmartRWLocker.waitReadRelease(permits: Int = 1) = readSemaphore.waitRelease(permits)

/**
 * Will call [SmartMutex.Mutable.lock], then execute [action] and return the result after [SmartMutex.Mutable.unlock]
 */
@OptIn(ExperimentalContracts::class)
suspend inline fun <T> SmartRWLocker.withWriteLock(action: () -> T): T {
    contract {
        callsInPlace(action, InvocationKind.EXACTLY_ONCE)
    }

    lockWrite()
    try {
        return action()
    } finally {
        unlockWrite()
    }
}

/**
 * Will wait until the [SmartMutex.lockStateFlow] of [this] instance will be false.
 *
 * Anyway, after the end of this block there are no any guaranties that [SmartMutex.isLocked] == false due to the fact
 * that some other parties may lock it again
 */
suspend fun SmartRWLocker.waitWriteUnlock() = writeMutex.waitUnlock()
