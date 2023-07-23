package dev.inmo.micro_utils.repos.cache

import dev.inmo.micro_utils.pagination.*
import dev.inmo.micro_utils.repos.*
import dev.inmo.micro_utils.repos.cache.cache.KVCache
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

open class ReadKeyValueCacheRepo<Key,Value>(
    protected open val parentRepo: ReadKeyValueRepo<Key, Value>,
    protected open val kvCache: KVCache<Key, Value>,
) : ReadKeyValueRepo<Key,Value> by parentRepo, CommonCacheRepo {
    override suspend fun get(k: Key): Value? = kvCache.get(k) ?: parentRepo.get(k) ?.also { kvCache.set(k, it) }
    override suspend fun contains(key: Key): Boolean = kvCache.contains(key) || parentRepo.contains(key)

    override suspend fun values(pagination: Pagination, reversed: Boolean): PaginationResult<Value> {
        return keys(pagination, reversed).let {
            it.changeResultsUnchecked(
                it.results.mapNotNull {
                    get(it)
                }
            )
        }
    }

    override suspend fun getAll(): Map<Key, Value> = kvCache.getAll().takeIf {
        it.size.toLong() == count()
    } ?: parentRepo.getAll().also {
        kvCache.set(it)
    }

    override suspend fun invalidate() = kvCache.clear()
}

fun <Key, Value> ReadKeyValueRepo<Key, Value>.cached(
    kvCache: KVCache<Key, Value>
) = ReadKeyValueCacheRepo(this, kvCache)

open class KeyValueCacheRepo<Key,Value>(
    override val parentRepo: KeyValueRepo<Key, Value>,
    kvCache: KVCache<Key, Value>,
    scope: CoroutineScope = CoroutineScope(Dispatchers.Default)
) : ReadKeyValueCacheRepo<Key,Value>(parentRepo, kvCache), KeyValueRepo<Key,Value>, WriteKeyValueRepo<Key, Value> by parentRepo, CommonCacheRepo {
    protected val onNewJob = parentRepo.onNewValue.onEach { kvCache.set(it.first, it.second) }.launchIn(scope)
    protected val onRemoveJob = parentRepo.onValueRemoved.onEach { kvCache.unset(it) }.launchIn(scope)

    override suspend fun invalidate() = kvCache.clear()

    override suspend fun clear() {
        parentRepo.clear()
        kvCache.clear()
    }
}

fun <Key, Value> KeyValueRepo<Key, Value>.cached(
    kvCache: KVCache<Key, Value>,
    scope: CoroutineScope = CoroutineScope(Dispatchers.Default)
) = KeyValueCacheRepo(this, kvCache, scope)
