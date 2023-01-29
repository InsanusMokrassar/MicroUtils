package dev.inmo.micro_utils.repos.cache

import dev.inmo.micro_utils.pagination.*
import dev.inmo.micro_utils.pagination.utils.*
import dev.inmo.micro_utils.repos.*
import dev.inmo.micro_utils.repos.cache.cache.KVCache
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

open class ReadKeyValuesCacheRepo<Key,Value>(
    protected open val parentRepo: ReadKeyValuesRepo<Key, Value>,
    protected open val kvCache: KVCache<Key, List<Value>>
) : ReadKeyValuesRepo<Key,Value> by parentRepo, CommonCacheRepo {
    override suspend fun get(k: Key, pagination: Pagination, reversed: Boolean): PaginationResult<Value> {
        return getAll(k, reversed).paginate(
            pagination
        )
    }
    override suspend fun getAll(k: Key, reversed: Boolean): List<Value> {
        return kvCache.get(k) ?.let {
            if (reversed) it.reversed() else it
        } ?: parentRepo.getAll(k, reversed).also {
            kvCache.set(k, it)
        }
    }
    override suspend fun contains(k: Key, v: Value): Boolean = kvCache.get(k) ?.contains(v) ?: (parentRepo.contains(k, v).also {
        if (it) {
            kvCache.unset(k) // clear as invalid
        }
    })
    override suspend fun contains(k: Key): Boolean = kvCache.contains(k) || parentRepo.contains(k)

    override suspend fun invalidate() = kvCache.clear()
}

fun <Key, Value> ReadKeyValuesRepo<Key, Value>.cached(
    kvCache: KVCache<Key, List<Value>>
) = ReadKeyValuesCacheRepo(this, kvCache)

open class KeyValuesCacheRepo<Key,Value>(
    parentRepo: KeyValuesRepo<Key, Value>,
    kvCache: KVCache<Key, List<Value>>,
    scope: CoroutineScope = CoroutineScope(Dispatchers.Default)
) : ReadKeyValuesCacheRepo<Key,Value>(parentRepo, kvCache), KeyValuesRepo<Key,Value>, WriteKeyValuesRepo<Key,Value> by parentRepo, CommonCacheRepo {
    protected val onNewJob = parentRepo.onNewValue.onEach { (k, v) ->
        kvCache.set(
            k,
            kvCache.get(k) ?.plus(v) ?: return@onEach
        )
    }.launchIn(scope)
    protected val onRemoveJob = parentRepo.onValueRemoved.onEach { (k, v) ->
        kvCache.set(
            k,
            kvCache.get(k) ?.minus(v) ?: return@onEach
        )
    }.launchIn(scope)
    protected val onDataClearedJob = parentRepo.onDataCleared.onEach {
        kvCache.unset(it)
    }.launchIn(scope)

    override suspend fun invalidate() = kvCache.clear()
}

fun <Key, Value> KeyValuesRepo<Key, Value>.cached(
    kvCache: KVCache<Key, List<Value>>,
    scope: CoroutineScope = CoroutineScope(Dispatchers.Default)
) = KeyValuesCacheRepo(this, kvCache, scope)
