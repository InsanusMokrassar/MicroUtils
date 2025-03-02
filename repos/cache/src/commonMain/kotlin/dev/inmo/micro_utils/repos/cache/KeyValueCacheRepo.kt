package dev.inmo.micro_utils.repos.cache

import dev.inmo.micro_utils.coroutines.SmartRWLocker
import dev.inmo.micro_utils.coroutines.withReadAcquire
import dev.inmo.micro_utils.coroutines.withWriteLock
import dev.inmo.micro_utils.pagination.*
import dev.inmo.micro_utils.repos.*
import dev.inmo.micro_utils.repos.cache.cache.KVCache
import dev.inmo.micro_utils.repos.cache.util.actualizeAll
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

open class ReadKeyValueCacheRepo<Key,Value>(
    protected val parentRepo: ReadKeyValueRepo<Key, Value>,
    protected val kvCache: KVCache<Key, Value>,
    protected val locker: SmartRWLocker = SmartRWLocker(),
) : ReadKeyValueRepo<Key,Value> by parentRepo, CommonCacheRepo {
    override suspend fun get(k: Key): Value? = locker.withReadAcquire {
        kvCache.get(k)
    } ?: parentRepo.get(k) ?.also {
        locker.withWriteLock {
            kvCache.set(k, it)
        }
    }
    override suspend fun contains(key: Key): Boolean = locker.withReadAcquire {
        kvCache.contains(key)
    } || parentRepo.contains(key)

    override suspend fun values(pagination: Pagination, reversed: Boolean): PaginationResult<Value> {
        return locker.withReadAcquire {
            keys(pagination, reversed).let {
                it.changeResultsUnchecked(
                    it.results.mapNotNull {
                        get(it)
                    }
                )
            }
        }
    }

    override suspend fun getAll(): Map<Key, Value> = locker.withReadAcquire {
        kvCache.getAll()
    }.takeIf {
        it.size.toLong() == count()
    } ?: parentRepo.getAll().also {
        locker.withWriteLock {
            kvCache.set(it)
        }
    }

    override suspend fun invalidate() = kvCache.actualizeAll(parentRepo, locker = locker)
}

fun <Key, Value> ReadKeyValueRepo<Key, Value>.cached(
    kvCache: KVCache<Key, Value>,
    locker: SmartRWLocker = SmartRWLocker(),
) = ReadKeyValueCacheRepo(this, kvCache, locker)

open class KeyValueCacheRepo<Key,Value>(
    protected val kvRepo: KeyValueRepo<Key, Value>,
    kvCache: KVCache<Key, Value>,
    scope: CoroutineScope = CoroutineScope(Dispatchers.Default),
    locker: SmartRWLocker = SmartRWLocker(),
) : ReadKeyValueCacheRepo<Key,Value>(kvRepo, kvCache, locker), KeyValueRepo<Key,Value>, WriteKeyValueRepo<Key, Value> by parentRepo, CommonCacheRepo {
    protected val onNewJob = kvRepo.onNewValue.onEach {
        locker.withWriteLock {
            kvCache.set(it.first, it.second)
        }
    }.launchIn(scope)
    protected val onRemoveJob = kvRepo.onValueRemoved.onEach {
        locker.withWriteLock {
            kvCache.unset(it)
        }
    }.launchIn(scope)

    override suspend fun clear() {
        kvRepo.clear()
        locker.withWriteLock {
            kvCache.clear()
        }
    }
}

fun <Key, Value> KeyValueRepo<Key, Value>.cached(
    kvCache: KVCache<Key, Value>,
    scope: CoroutineScope = CoroutineScope(Dispatchers.Default),
    locker: SmartRWLocker = SmartRWLocker(),
) = KeyValueCacheRepo(this, kvCache, scope, locker)
