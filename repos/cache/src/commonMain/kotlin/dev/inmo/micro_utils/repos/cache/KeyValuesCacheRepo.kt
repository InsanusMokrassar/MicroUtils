package dev.inmo.micro_utils.repos.cache

import dev.inmo.micro_utils.coroutines.SmartRWLocker
import dev.inmo.micro_utils.coroutines.withReadAcquire
import dev.inmo.micro_utils.coroutines.withWriteLock
import dev.inmo.micro_utils.pagination.*
import dev.inmo.micro_utils.pagination.utils.*
import dev.inmo.micro_utils.repos.*
import dev.inmo.micro_utils.repos.annotations.OverrideRequireManualInvalidation
import dev.inmo.micro_utils.repos.cache.cache.KVCache
import dev.inmo.micro_utils.repos.cache.util.actualizeAll
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

open class ReadKeyValuesCacheRepo<Key,Value>(
    protected val parentRepo: ReadKeyValuesRepo<Key, Value>,
    protected val kvCache: KVCache<Key, List<Value>>,
    protected val locker: SmartRWLocker = SmartRWLocker(),
) : ReadKeyValuesRepo<Key,Value> by parentRepo, CommonCacheRepo {
    override suspend fun get(k: Key, pagination: Pagination, reversed: Boolean): PaginationResult<Value> {
        return locker.withReadAcquire {
            getAll(k, reversed)
        }.paginate(
            pagination
        )
    }
    override suspend fun getAll(k: Key, reversed: Boolean): List<Value> {
        return locker.withReadAcquire {
            kvCache.get(k)
        } ?.let {
            if (reversed) it.reversed() else it
        } ?: parentRepo.getAll(k, reversed).also {
            locker.withWriteLock {
                kvCache.set(k, it)
            }
        }
    }
    override suspend fun contains(k: Key, v: Value): Boolean = locker.withReadAcquire {
        kvCache.get(k)
    } ?.contains(v) ?: (parentRepo.contains(k, v).also {
        if (it) {
            locker.withWriteLock {
                kvCache.unset(k) // clear as invalid
            }
        }
    })
    override suspend fun contains(k: Key): Boolean = locker.withReadAcquire {
        kvCache.contains(k)
    } || parentRepo.contains(k)

    @OverrideRequireManualInvalidation
    override suspend fun invalidate() = kvCache.actualizeAll(parentRepo, locker = locker)
}

fun <Key, Value> ReadKeyValuesRepo<Key, Value>.cached(
    kvCache: KVCache<Key, List<Value>>,
    locker: SmartRWLocker = SmartRWLocker(),
) = ReadKeyValuesCacheRepo(this, kvCache, locker)

open class KeyValuesCacheRepo<Key,Value>(
    parentRepo: KeyValuesRepo<Key, Value>,
    kvCache: KVCache<Key, List<Value>>,
    scope: CoroutineScope = CoroutineScope(Dispatchers.Default),
    locker: SmartRWLocker = SmartRWLocker(),
) : ReadKeyValuesCacheRepo<Key,Value>(parentRepo, kvCache, locker), KeyValuesRepo<Key,Value>, WriteKeyValuesRepo<Key,Value> by parentRepo, CommonCacheRepo {
    protected val onNewJob = parentRepo.onNewValue.onEach { (k, v) ->
        locker.withWriteLock {
            kvCache.set(
                k,
                kvCache.get(k) ?.plus(v) ?: return@onEach
            )
        }
    }.launchIn(scope)
    protected val onRemoveJob = parentRepo.onValueRemoved.onEach { (k, v) ->
        locker.withWriteLock {
            kvCache.set(
                k,
                kvCache.get(k)?.minus(v) ?: return@onEach
            )
        }
    }.launchIn(scope)
    protected val onDataClearedJob = parentRepo.onDataCleared.onEach {
        locker.withWriteLock {
            kvCache.unset(it)
        }
    }.launchIn(scope)
}

fun <Key, Value> KeyValuesRepo<Key, Value>.cached(
    kvCache: KVCache<Key, List<Value>>,
    scope: CoroutineScope = CoroutineScope(Dispatchers.Default),
    locker: SmartRWLocker = SmartRWLocker(),
) = KeyValuesCacheRepo(this, kvCache, scope, locker)
