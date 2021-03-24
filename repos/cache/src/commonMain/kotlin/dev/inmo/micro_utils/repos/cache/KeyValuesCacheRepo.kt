package dev.inmo.micro_utils.repos.cache

import dev.inmo.micro_utils.pagination.Pagination
import dev.inmo.micro_utils.pagination.PaginationResult
import dev.inmo.micro_utils.pagination.utils.paginate
import dev.inmo.micro_utils.pagination.utils.reverse
import dev.inmo.micro_utils.repos.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

open class KeyValuesCacheRepo<Key,Value>(
    protected val parentRepo: KeyValuesRepo<Key, Value>,
    protected val cachedValuesCount: Int,
    scope: CoroutineScope = CoroutineScope(Dispatchers.Default)
) : KeyValuesRepo<Key,Value> by parentRepo {
    protected open val cache = mutableMapOf<Key, List<Value>>()
    protected open val cacheStack = ArrayList<Key>(cachedValuesCount)
    protected val syncMutex = Mutex()
    protected val onNewJob = parentRepo.onNewValue.onEach { putCacheValue(it.first, it.second) }.launchIn(scope)
    protected val onRemoveJob = parentRepo.onValueRemoved.onEach { removeCacheValue(it.first, it.second) }.launchIn(scope)
    protected val onDataClearedJob = parentRepo.onDataCleared.onEach { clearCacheValues(it) }.launchIn(scope)

    protected suspend fun putCacheValues(k: Key, v: List<Value>) = syncMutex.withLock {
        if (cache.size >= cachedValuesCount) {
            val key = cacheStack.removeAt(0)
            cache.remove(key)
        }
        cacheStack.add(k)
        cache[k] = v
    }
    protected suspend fun putCacheValue(k: Key, v: Value) = syncMutex.withLock {
        cache[k] ?.let {
            cache[k] = it + v
        }
    } ?: putCacheValues(k, listOf(v))

    protected suspend fun removeCacheValue(k: Key, v: Value) = syncMutex.withLock {
        cache[k] ?.let {
            val newList = it - v
            if (newList.isEmpty()) {
                cache.remove(k)
                cacheStack.remove(k)
            } else {
                cache[k] = newList
            }
        }
    }

    protected suspend fun clearCacheValues(k: Key) = syncMutex.withLock {
        val i = cacheStack.indexOf(k)
        if (i >= 0) {
            val key = cacheStack.removeAt(i)
            cache.remove(key)
        }
    }

    override suspend fun get(k: Key, pagination: Pagination, reversed: Boolean): PaginationResult<Value> {
        return cache[k] ?.paginate(
            pagination.let { if (reversed) it.reverse(count(k)) else it }
        ) ?.let {
            if (reversed) it.copy(results = it.results.reversed()) else it
        } ?: parentRepo.get(k, pagination, reversed)
    }
    override suspend fun getAll(k: Key, reversed: Boolean): List<Value> {
        return cache[k] ?.let {
            if (reversed) it.reversed() else it
        } ?: parentRepo.getAll(k, reversed)
    }
    override suspend fun contains(k: Key, v: Value): Boolean = cache[k] ?.contains(v) ?: parentRepo.contains(k, v)
    override suspend fun contains(k: Key): Boolean = cache.containsKey(k) || parentRepo.contains(k)
}
