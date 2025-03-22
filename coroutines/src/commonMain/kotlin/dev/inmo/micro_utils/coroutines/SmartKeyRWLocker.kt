package dev.inmo.micro_utils.coroutines

import kotlinx.coroutines.sync.withLock
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

class SmartKeyRWLocker<T>(
    private val perKeyReadPermits: Int = Int.MAX_VALUE
) {
    private val internalRWLocker = SmartRWLocker()
    private val lockers = mutableMapOf<T, SmartRWLocker>()

    private fun allocateLockerWithoutLock(key: T) = lockers.getOrPut(key) {
        SmartRWLocker(perKeyReadPermits)
    }

    suspend fun writeMutex(key: T): SmartMutex.Immutable = internalRWLocker.withReadAcquire {
        allocateLockerWithoutLock(key).writeMutex
    }
    suspend fun readSemaphore(key: T): SmartSemaphore.Immutable = internalRWLocker.withReadAcquire {
        allocateLockerWithoutLock(key).readSemaphore
    }
    fun writeMutexOrNull(key: T): SmartMutex.Immutable? = lockers[key] ?.writeMutex
    fun readSemaphoreOrNull(key: T): SmartSemaphore.Immutable? = lockers[key] ?.readSemaphore

    suspend fun acquireRead(key: T) {
        internalRWLocker.withReadAcquire {
            val locker = allocateLockerWithoutLock(key)
            locker.acquireRead()
        }
    }
    suspend fun releaseRead(key: T): Boolean {
        return internalRWLocker.withReadAcquire {
            lockers[key]
        } ?.releaseRead() == true
    }

    suspend fun lockWrite(key: T) {
        internalRWLocker.withWriteLock {
            val locker = allocateLockerWithoutLock(key)
            locker.lockWrite()
        }
    }
    suspend fun unlockWrite(key: T): Boolean {
        return internalRWLocker.withWriteLock {
            lockers[key]
        } ?.unlockWrite() == true
    }
    fun isWriteLocked(key: T): Boolean = lockers[key] ?.writeMutex ?.isLocked == true
}

@OptIn(ExperimentalContracts::class)
suspend inline fun <T, R> SmartKeyRWLocker<T>.withReadAcquire(key: T, action: () -> R): R {
    contract {
        callsInPlace(action, InvocationKind.EXACTLY_ONCE)
    }

    acquireRead(key)
    try {
        return action()
    } finally {
        releaseRead(key)
    }
}

@OptIn(ExperimentalContracts::class)
suspend inline fun <T, R> SmartKeyRWLocker<T>.withWriteLock(key: T, action: () -> R): R {
    contract {
        callsInPlace(action, InvocationKind.EXACTLY_ONCE)
    }

    lockWrite(key)
    try {
        return action()
    } finally {
        unlockWrite(key)
    }
}