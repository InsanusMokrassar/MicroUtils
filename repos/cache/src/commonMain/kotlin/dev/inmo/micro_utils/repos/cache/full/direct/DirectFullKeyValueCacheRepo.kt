package dev.inmo.micro_utils.repos.cache.full.direct

import dev.inmo.micro_utils.coroutines.SmartRWLocker
import dev.inmo.micro_utils.coroutines.launchLoggingDropExceptions
import dev.inmo.micro_utils.coroutines.withReadAcquire
import dev.inmo.micro_utils.coroutines.withWriteLock
import dev.inmo.micro_utils.pagination.Pagination
import dev.inmo.micro_utils.pagination.PaginationResult
import dev.inmo.micro_utils.repos.*
import dev.inmo.micro_utils.repos.cache.full.FullKeyValueCacheRepo
import dev.inmo.micro_utils.repos.cache.full.FullReadKeyValueCacheRepo
import dev.inmo.micro_utils.repos.cache.full.FullWriteKeyValueCacheRepo
import dev.inmo.micro_utils.repos.cache.util.actualizeAll
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

open class DirectFullReadKeyValueCacheRepo<Key, Value>(
    protected open val parentRepo: ReadKeyValueRepo<Key, Value>,
    protected open val kvCache: KeyValueRepo<Key, Value>,
    protected val locker: SmartRWLocker = SmartRWLocker()
) : DirectFullCacheRepo, ReadKeyValueRepo<Key, Value> {
    protected open suspend fun actualizeAll() {
        kvCache.actualizeAll(parentRepo, locker)
    }

    override suspend fun get(k: Key): Value? = locker.withReadAcquire {
        kvCache.get(k)
    }

    override suspend fun values(pagination: Pagination, reversed: Boolean): PaginationResult<Value> = locker.withReadAcquire {
        kvCache.values(pagination, reversed)
    }

    override suspend fun count(): Long = locker.withReadAcquire {
        kvCache.count()
    }

    override suspend fun contains(key: Key): Boolean = locker.withReadAcquire {
        kvCache.contains(key)
    }

    override suspend fun getAll(): Map<Key, Value> = locker.withReadAcquire {
        kvCache.getAll()
    }

    override suspend fun keys(pagination: Pagination, reversed: Boolean): PaginationResult<Key> = locker.withReadAcquire {
        kvCache.keys(pagination, reversed)
    }

    override suspend fun keys(v: Value, pagination: Pagination, reversed: Boolean): PaginationResult<Key> = locker.withReadAcquire {
        kvCache.keys(v, pagination, reversed)
    }

    override suspend fun invalidate() {
        actualizeAll()
    }
}

fun <Key, Value> ReadKeyValueRepo<Key, Value>.directlyCached(
    kvCache: KeyValueRepo<Key, Value>,
    locker: SmartRWLocker = SmartRWLocker()
) = DirectFullReadKeyValueCacheRepo(this, kvCache, locker)

open class DirectFullWriteKeyValueCacheRepo<Key, Value>(
    protected open val parentRepo: WriteKeyValueRepo<Key, Value>,
    protected open val kvCache: KeyValueRepo<Key, Value>,
    protected val locker: SmartRWLocker = SmartRWLocker(),
    scope: CoroutineScope = CoroutineScope(Dispatchers.Default),
) : DirectFullCacheRepo, WriteKeyValueRepo<Key, Value> by parentRepo {
    override val onNewValue: Flow<Pair<Key, Value>>
        get() = parentRepo.onNewValue
    override val onValueRemoved: Flow<Key>
        get() = parentRepo.onValueRemoved

    protected val onNewJob = parentRepo.onNewValue.onEach {
        locker.withWriteLock {
            kvCache.set(it.first, it.second)
        }
    }.launchIn(scope)
    protected val onRemoveJob = parentRepo.onValueRemoved.onEach {
        locker.withWriteLock {
            kvCache.unset(it)
        }
    }.launchIn(scope)

    override suspend fun invalidate() {
        locker.withWriteLock {
            kvCache.clear()
        }
    }

    override suspend fun unsetWithValues(toUnset: List<Value>) = parentRepo.unsetWithValues(toUnset)
}

fun <Key, Value> WriteKeyValueRepo<Key, Value>.directlyCached(
    kvCache: KeyValueRepo<Key, Value>,
    scope: CoroutineScope = CoroutineScope(Dispatchers.Default)
) = DirectFullWriteKeyValueCacheRepo(this, kvCache, scope = scope)

open class DirectFullKeyValueCacheRepo<Key, Value>(
    override val parentRepo: KeyValueRepo<Key, Value>,
    kvCache: KeyValueRepo<Key, Value>,
    scope: CoroutineScope = CoroutineScope(Dispatchers.Default),
    skipStartInvalidate: Boolean = false,
    locker: SmartRWLocker = SmartRWLocker(writeIsLocked = !skipStartInvalidate),
) : DirectFullCacheRepo,
    KeyValueRepo<Key, Value> ,
    WriteKeyValueRepo<Key, Value> by DirectFullWriteKeyValueCacheRepo(
        parentRepo,
        kvCache,
        locker,
        scope
    ),
    DirectFullReadKeyValueCacheRepo<Key, Value>(parentRepo, kvCache, locker) {
    init {
        if (!skipStartInvalidate) {
            scope.launchLoggingDropExceptions {
                if (locker.writeMutex.isLocked) {
                    initialInvalidate()
                } else {
                    invalidate()
                }
            }
        }
    }


    protected open suspend fun initialInvalidate() {
        try {
            kvCache.actualizeAll(parentRepo, locker = null)
        } finally {
            locker.unlockWrite()
        }
    }
    override suspend fun invalidate() {
        kvCache.actualizeAll(parentRepo, locker)
    }

    override suspend fun clear() {
        parentRepo.clear()
        kvCache.clear()
    }

    override suspend fun unsetWithValues(toUnset: List<Value>) = parentRepo.unsetWithValues(toUnset)

    override suspend fun set(toSet: Map<Key, Value>) {
        locker.withWriteLock {
            parentRepo.set(toSet)
            kvCache.set(
                toSet.filter {
                    parentRepo.contains(it.key)
                }
            )
        }
    }

    override suspend fun unset(toUnset: List<Key>) {
        locker.withWriteLock {
            parentRepo.unset(toUnset)
            kvCache.unset(
                toUnset.filter {
                    !parentRepo.contains(it)
                }
            )
        }
    }
}

fun <Key, Value> KeyValueRepo<Key, Value>.directlyFullyCached(
    kvCache: KeyValueRepo<Key, Value> = MapKeyValueRepo(),
    scope: CoroutineScope = CoroutineScope(Dispatchers.Default),
    skipStartInvalidate: Boolean = false,
    locker: SmartRWLocker = SmartRWLocker()
) = DirectFullKeyValueCacheRepo(this, kvCache, scope, skipStartInvalidate, locker)
