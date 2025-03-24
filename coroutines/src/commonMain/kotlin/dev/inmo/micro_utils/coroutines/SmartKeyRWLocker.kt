package dev.inmo.micro_utils.coroutines

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

class SmartKeyRWLocker<T>(
    globalLockerReadPermits: Int = Int.MAX_VALUE,
    globalLockerWriteIsLocked: Boolean = false,
    private val perKeyReadPermits: Int = Int.MAX_VALUE
) {
    private val globalRWLocker: SmartRWLocker = SmartRWLocker(
        readPermits = globalLockerReadPermits,
        writeIsLocked = globalLockerWriteIsLocked
    )
    private val lockers = mutableMapOf<T, SmartRWLocker>()
    private val lockersMutex = Mutex()
    private val lockersWritingLocker = SmartSemaphore.Mutable(Int.MAX_VALUE)
    private val globalWritingLocker = SmartSemaphore.Mutable(Int.MAX_VALUE)

    private fun allocateLockerWithoutLock(key: T) = lockers.getOrPut(key) {
        SmartRWLocker(perKeyReadPermits)
    }
    private suspend fun allocateLocker(key: T) = lockersMutex.withLock {
        lockers.getOrPut(key) {
            SmartRWLocker(perKeyReadPermits)
        }
    }

    suspend fun writeMutex(key: T): SmartMutex.Immutable = globalRWLocker.withReadAcquire {
        allocateLockerWithoutLock(key).writeMutex
    }
    suspend fun readSemaphore(key: T): SmartSemaphore.Immutable = globalRWLocker.withReadAcquire {
        allocateLockerWithoutLock(key).readSemaphore
    }
    fun writeMutexOrNull(key: T): SmartMutex.Immutable? = lockers[key] ?.writeMutex
    fun readSemaphoreOrNull(key: T): SmartSemaphore.Immutable? = lockers[key] ?.readSemaphore

    fun writeMutex(): SmartMutex.Immutable = globalRWLocker.writeMutex
    fun readSemaphore(): SmartSemaphore.Immutable = globalRWLocker.readSemaphore

    suspend fun acquireRead() {
        globalWritingLocker.acquire()
        try {
            lockersWritingLocker.waitReleaseAll()
            globalRWLocker.acquireRead()
        } catch (e: CancellationException) {
            globalWritingLocker.release()
            throw e
        }
    }
    suspend fun releaseRead(): Boolean {
        globalWritingLocker.release()
        return globalRWLocker.releaseRead()
    }

    suspend fun lockWrite() {
        globalRWLocker.lockWrite()
    }
    suspend fun unlockWrite(): Boolean {
        return globalRWLocker.unlockWrite()
    }
    fun isWriteLocked(): Boolean = globalRWLocker.writeMutex.isLocked == true


    suspend fun acquireRead(key: T) {
        globalRWLocker.acquireRead()
        val locker = allocateLocker(key)
        try {
            locker.acquireRead()
        } catch (e: CancellationException) {
            globalRWLocker.releaseRead()
            throw e
        }
    }
    suspend fun releaseRead(key: T): Boolean {
        val locker = allocateLocker(key)
        return locker.releaseRead() && globalRWLocker.releaseRead()
    }

    suspend fun lockWrite(key: T) {
        globalWritingLocker.withAcquire(globalWritingLocker.maxPermits) {
            lockersWritingLocker.acquire()
        }
        try {
            globalRWLocker.acquireRead()
            try {
                val locker = allocateLocker(key)
                locker.lockWrite()
            } catch (e: CancellationException) {
                globalRWLocker.releaseRead()
                throw e
            }
        } catch (e: CancellationException) {
            lockersWritingLocker.release()
            throw e
        }
    }
    suspend fun unlockWrite(key: T): Boolean {
        val locker = allocateLocker(key)
        return (locker.unlockWrite() && globalRWLocker.releaseRead()).also {
            if (it) {
                lockersWritingLocker.release()
            }
        }
    }
    fun isWriteLocked(key: T): Boolean = lockers[key] ?.writeMutex ?.isLocked == true
}

@OptIn(ExperimentalContracts::class)
suspend inline fun <T, R> SmartKeyRWLocker<T>.withReadAcquire(action: () -> R): R {
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

@OptIn(ExperimentalContracts::class)
suspend inline fun <T, R> SmartKeyRWLocker<T>.withWriteLock(action: () -> R): R {
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
suspend inline fun <T, R> SmartKeyRWLocker<T>.withReadAcquires(keys: Iterable<T>, action: () -> R): R {
    contract {
        callsInPlace(action, InvocationKind.EXACTLY_ONCE)
    }

    val acquired = mutableSetOf<T>()
    try {
        keys.forEach {
            acquireRead(it)
            acquired.add(it)
        }
        return action()
    } finally {
        acquired.forEach {
            releaseRead(it)
        }
    }
}
suspend inline fun <T, R> SmartKeyRWLocker<T>.withReadAcquires(vararg keys: T, action: () -> R): R = withReadAcquires(keys.asIterable(), action)

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

@OptIn(ExperimentalContracts::class)
suspend inline fun <T, R> SmartKeyRWLocker<T>.withWriteLocks(keys: Iterable<T>, action: () -> R): R {
    contract {
        callsInPlace(action, InvocationKind.EXACTLY_ONCE)
    }

    val locked = mutableSetOf<T>()
    try {
        keys.forEach {
            lockWrite(it)
            locked.add(it)
        }
        return action()
    } finally {
        locked.forEach {
            unlockWrite(it)
        }
    }
}