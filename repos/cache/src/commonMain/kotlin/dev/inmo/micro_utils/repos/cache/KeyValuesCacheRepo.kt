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

open class ReadKeyValuesCacheRepo<Key,Value>(
    protected val parentRepo: ReadKeyValuesRepo<Key, Value>,
    protected val kvCache: KVCache<Key, List<Value>>
) : ReadKeyValuesRepo<Key,Value> by parentRepo {
    override suspend fun get(k: Key, pagination: Pagination, reversed: Boolean): PaginationResult<Value> {
        return kvCache.get(k) ?.paginate(
            pagination.let { if (reversed) it.reverse(count(k)) else it }
        ) ?.let {
            if (reversed) it.copy(results = it.results.reversed()) else it
        } ?: parentRepo.get(k, pagination, reversed)
    }
    override suspend fun getAll(k: Key, reversed: Boolean): List<Value> {
        return kvCache.get(k) ?.let {
            if (reversed) it.reversed() else it
        } ?: parentRepo.getAll(k, reversed)
    }
    override suspend fun contains(k: Key, v: Value): Boolean = kvCache.get(k) ?.contains(v) ?: parentRepo.contains(k, v)
    override suspend fun contains(k: Key): Boolean = kvCache.contains(k) || parentRepo.contains(k)
}

open class KeyValuesCacheRepo<Key,Value>(
    parentRepo: KeyValuesRepo<Key, Value>,
    kvCache: KVCache<Key, List<Value>>,
    scope: CoroutineScope = CoroutineScope(Dispatchers.Default)
) : ReadKeyValuesCacheRepo<Key,Value>(parentRepo, kvCache), KeyValuesRepo<Key,Value>, WriteKeyValuesRepo<Key,Value> by parentRepo {
    protected val onNewJob = parentRepo.onNewValue.onEach { kvCache.set(it.first, kvCache.get(it.first) ?.plus(it.second) ?: listOf(it.second)) }.launchIn(scope)
    protected val onRemoveJob = parentRepo.onValueRemoved.onEach { kvCache.set(it.first, kvCache.get(it.first) ?.minus(it.second) ?: return@onEach) }.launchIn(scope)
    protected val onDataClearedJob = parentRepo.onDataCleared.onEach { kvCache.unset(it) }.launchIn(scope)
}
