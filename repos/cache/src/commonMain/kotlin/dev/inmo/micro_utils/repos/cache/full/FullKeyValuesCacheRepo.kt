package dev.inmo.micro_utils.repos.cache.full

import dev.inmo.micro_utils.common.*
import dev.inmo.micro_utils.pagination.*
import dev.inmo.micro_utils.pagination.utils.*
import dev.inmo.micro_utils.repos.*
import dev.inmo.micro_utils.repos.cache.cache.FullKVCache
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

open class FullReadKeyValuesCacheRepo<Key,Value>(
    protected open val parentRepo: ReadKeyValuesRepo<Key, Value>,
    protected open val kvCache: FullKVCache<Key, List<Value>>,
) : ReadKeyValuesRepo<Key, Value>, FullCacheRepo {
    protected inline fun <T> doOrTakeAndActualize(
        action: FullKVCache<Key, List<Value>>.() -> Optional<T>,
        actionElse: ReadKeyValuesRepo<Key, Value>.() -> T,
        actualize: FullKVCache<Key, List<Value>>.(T) -> Unit
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

    protected open suspend fun actualizeKey(k: Key) {
        kvCache.set(k, parentRepo.getAll(k))
    }

    protected open suspend fun actualizeAll() {
        doAllWithCurrentPaging { kvCache.keys(it).also { kvCache.unset(it.results) } }
        kvCache.set(parentRepo.getAll())
    }

    override suspend fun get(k: Key, pagination: Pagination, reversed: Boolean): PaginationResult<Value> {
        return doOrTakeAndActualize(
            {
                get(k) ?.paginate(
                    pagination.let { if (reversed) it.reverse(count(k)) else it }
                ) ?.let {
                    if (reversed) it.copy(results = it.results.reversed()) else it
                }.optionalOrAbsentIfNull
            },
            { get(k, pagination, reversed) },
            { actualizeKey(k) }
        )
    }

    override suspend fun keys(pagination: Pagination, reversed: Boolean): PaginationResult<Key> {
        return doOrTakeAndActualize(
            {
                kvCache.keys(pagination, reversed).takeIf { it.results.isNotEmpty() }.optionalOrAbsentIfNull
            },
            { parentRepo.keys(pagination, reversed) },
            { actualizeAll() }
        )
    }

    override suspend fun count(): Long = doOrTakeAndActualize(
        { count().takeIf { it != 0L }.optionalOrAbsentIfNull },
        { count() },
        { if (it != 0L) actualizeAll() }
    )

    override suspend fun count(k: Key): Long = doOrTakeAndActualize(
        { count().takeIf { it != 0L }.optionalOrAbsentIfNull },
        { count() },
        { if (it != 0L) actualizeKey(k) }
    )

    override suspend fun contains(k: Key, v: Value): Boolean = doOrTakeAndActualize(
        { get(k) ?.contains(v).takeIf { it == true }.optionalOrAbsentIfNull },
        { contains(k, v) },
        { if (it) actualizeKey(k) }
    )

    override suspend fun contains(k: Key): Boolean = doOrTakeAndActualize(
        { contains(k).takeIf { it }.optionalOrAbsentIfNull },
        { contains(k) },
        { if (it) actualizeKey(k) }
    )

    override suspend fun keys(
        v: Value,
        pagination: Pagination,
        reversed: Boolean
    ): PaginationResult<Key> = doOrTakeAndActualize(
        {
            val keys = getAllWithNextPaging { keys(it) }.filter { get(it) ?.contains(v) == true }.optionallyReverse(reversed)
            if (keys.isNotEmpty()) {
                keys.paginate(pagination.optionallyReverse(keys.size, reversed)).takeIf { it.results.isNotEmpty() }.optionalOrAbsentIfNull
            } else {
                Optional.absent()
            }
        },
        { parentRepo.keys(v, pagination, reversed) },
        { if (it.results.isNotEmpty()) actualizeAll() }
    )

    override suspend fun invalidate() {
        actualizeAll()
    }
}

fun <Key, Value> ReadKeyValuesRepo<Key, Value>.cached(
    kvCache: FullKVCache<Key, List<Value>>
) = FullReadKeyValuesCacheRepo(this, kvCache)

open class FullWriteKeyValuesCacheRepo<Key,Value>(
    protected open val parentRepo: WriteKeyValuesRepo<Key, Value>,
    protected open val kvCache: FullKVCache<Key, List<Value>>,
    scope: CoroutineScope = CoroutineScope(Dispatchers.Default)
) : WriteKeyValuesRepo<Key, Value> by parentRepo, FullCacheRepo {
    protected val onNewJob = parentRepo.onNewValue.onEach {
        kvCache.set(
            it.first,
            kvCache.get(it.first) ?.plus(it.second) ?: listOf(it.second)
        )
    }.launchIn(scope)
    protected val onRemoveJob = parentRepo.onValueRemoved.onEach {
        kvCache.set(
            it.first,
            kvCache.get(it.first) ?.minus(it.second) ?: return@onEach
        )
    }.launchIn(scope)

    override suspend fun invalidate() {
        kvCache.clear()
    }
}

fun <Key, Value> WriteKeyValuesRepo<Key, Value>.caching(
    kvCache: FullKVCache<Key, List<Value>>,
    scope: CoroutineScope = CoroutineScope(Dispatchers.Default)
) = FullWriteKeyValuesCacheRepo(this, kvCache, scope)

open class FullKeyValuesCacheRepo<Key,Value>(
    parentRepo: KeyValuesRepo<Key, Value>,
    kvCache: FullKVCache<Key, List<Value>>,
    scope: CoroutineScope = CoroutineScope(Dispatchers.Default)
) : FullWriteKeyValuesCacheRepo<Key, Value>(parentRepo, kvCache, scope),
    KeyValuesRepo<Key, Value>,
    ReadKeyValuesRepo<Key, Value> by FullReadKeyValuesCacheRepo(parentRepo, kvCache) {
    override suspend fun clearWithValue(v: Value) {
        doAllWithCurrentPaging {
            keys(v, it).also {
                remove(it.results.associateWith { listOf(v) })
            }
        }
    }

    override suspend fun invalidate() {
        super<ReadKeyValuesRepo>.invalidate()
    }
}

fun <Key, Value> KeyValuesRepo<Key, Value>.caching(
    kvCache: FullKVCache<Key, List<Value>>,
    scope: CoroutineScope = CoroutineScope(Dispatchers.Default)
) = FullKeyValuesCacheRepo(this, kvCache, scope)
