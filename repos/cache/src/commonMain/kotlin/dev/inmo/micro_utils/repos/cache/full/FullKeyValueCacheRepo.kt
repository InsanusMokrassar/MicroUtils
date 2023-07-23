package dev.inmo.micro_utils.repos.cache.full

import dev.inmo.micro_utils.common.*
import dev.inmo.micro_utils.pagination.Pagination
import dev.inmo.micro_utils.pagination.PaginationResult
import dev.inmo.micro_utils.repos.*
import dev.inmo.micro_utils.repos.cache.cache.FullKVCache
import dev.inmo.micro_utils.repos.cache.util.actualizeAll
import dev.inmo.micro_utils.repos.pagination.getAll
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

open class FullReadKeyValueCacheRepo<Key,Value>(
    protected open val parentRepo: ReadKeyValueRepo<Key, Value>,
    protected open val kvCache: FullKVCache<Key, Value>,
) : ReadKeyValueRepo<Key, Value>, FullCacheRepo {
    protected inline fun <T> doOrTakeAndActualize(
        action: FullKVCache<Key, Value>.() -> Optional<T>,
        actionElse: ReadKeyValueRepo<Key, Value>.() -> T,
        actualize: FullKVCache<Key, Value>.(T) -> Unit
    ): T {
        kvCache.action().onPresented {
            return it
        }.onAbsent {
            return parentRepo.actionElse().also {
                kvCache.actualize(it)
            }
        }
        error("The result should be returned above")
    }
    protected open suspend fun actualizeAll() {
        kvCache.clear()
        kvCache.set(parentRepo.getAll { keys(it) }.toMap())
    }

    override suspend fun get(k: Key): Value? = doOrTakeAndActualize(
        { get(k) ?.optional ?: Optional.absent() },
        { get(k) },
        { set(k, it ?: return@doOrTakeAndActualize) }
    )

    override suspend fun values(pagination: Pagination, reversed: Boolean): PaginationResult<Value> = doOrTakeAndActualize(
        { values(pagination, reversed).takeIf { it.results.isNotEmpty() }.optionalOrAbsentIfNull },
        { values(pagination, reversed) },
        { if (it.results.isNotEmpty()) actualizeAll() }
    )

    override suspend fun count(): Long = doOrTakeAndActualize(
        { count().takeIf { it != 0L }.optionalOrAbsentIfNull },
        { count() },
        { if (it != 0L) actualizeAll() }
    )

    override suspend fun contains(key: Key): Boolean = doOrTakeAndActualize(
        { contains(key).takeIf { it }.optionalOrAbsentIfNull },
        { contains(key) },
        { if (it) parentRepo.get(key) ?.also { kvCache.set(key, it) } }
    )

    override suspend fun getAll(): Map<Key, Value> = doOrTakeAndActualize(
        { getAll().takeIf { it.isNotEmpty() }.optionalOrAbsentIfNull },
        { getAll() },
        { kvCache.actualizeAll(clear = true) { it } }
    )

    override suspend fun keys(pagination: Pagination, reversed: Boolean): PaginationResult<Key> = doOrTakeAndActualize(
        { keys(pagination, reversed).takeIf { it.results.isNotEmpty() }.optionalOrAbsentIfNull },
        { keys(pagination, reversed) },
        { if (it.results.isNotEmpty()) actualizeAll() }
    )

    override suspend fun keys(v: Value, pagination: Pagination, reversed: Boolean): PaginationResult<Key> = doOrTakeAndActualize(
        { keys(v, pagination, reversed).takeIf { it.results.isNotEmpty() }.optionalOrAbsentIfNull },
        { parentRepo.keys(v, pagination, reversed) },
        { if (it.results.isNotEmpty()) actualizeAll() }
    )

    override suspend fun invalidate() {
        actualizeAll()
    }
}

fun <Key, Value> ReadKeyValueRepo<Key, Value>.cached(
    kvCache: FullKVCache<Key, Value>
) = FullReadKeyValueCacheRepo(this, kvCache)

open class FullWriteKeyValueCacheRepo<Key,Value>(
    parentRepo: WriteKeyValueRepo<Key, Value>,
    protected open val kvCache: FullKVCache<Key, Value>,
    scope: CoroutineScope = CoroutineScope(Dispatchers.Default)
) : WriteKeyValueRepo<Key, Value> by parentRepo, FullCacheRepo {
    protected val onNewJob = parentRepo.onNewValue.onEach { kvCache.set(it.first, it.second) }.launchIn(scope)
    protected val onRemoveJob = parentRepo.onValueRemoved.onEach { kvCache.unset(it) }.launchIn(scope)

    override suspend fun invalidate() {
        kvCache.clear()
    }
}

fun <Key, Value> WriteKeyValueRepo<Key, Value>.caching(
    kvCache: FullKVCache<Key, Value>,
    scope: CoroutineScope = CoroutineScope(Dispatchers.Default)
) = FullWriteKeyValueCacheRepo(this, kvCache, scope)

open class FullKeyValueCacheRepo<Key,Value>(
    protected open val parentRepo: KeyValueRepo<Key, Value>,
    kvCache: FullKVCache<Key, Value>,
    scope: CoroutineScope = CoroutineScope(Dispatchers.Default)
) : FullWriteKeyValueCacheRepo<Key,Value>(parentRepo, kvCache, scope),
    KeyValueRepo<Key,Value>,
    ReadKeyValueRepo<Key, Value> by FullReadKeyValueCacheRepo(parentRepo, kvCache) {
    override suspend fun unsetWithValues(toUnset: List<Value>) = parentRepo.unsetWithValues(toUnset)

    override suspend fun invalidate() {
        kvCache.actualizeAll(parentRepo)
    }

    override suspend fun clear() {
        parentRepo.clear()
        kvCache.clear()
    }
}

fun <Key, Value> KeyValueRepo<Key, Value>.fullyCached(
    kvCache: FullKVCache<Key, Value> = FullKVCache(),
    scope: CoroutineScope = CoroutineScope(Dispatchers.Default)
) = FullKeyValueCacheRepo(this, kvCache, scope)

@Deprecated("Renamed", ReplaceWith("this.fullyCached(kvCache, scope)", "dev.inmo.micro_utils.repos.cache.full.fullyCached"))
fun <Key, Value> KeyValueRepo<Key, Value>.cached(
    kvCache: FullKVCache<Key, Value>,
    scope: CoroutineScope = CoroutineScope(Dispatchers.Default)
) = fullyCached(kvCache, scope)
