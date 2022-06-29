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
) : ReadKeyValuesRepo<Key, Value> {
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

    protected suspend fun actualizeKey(k: Key) {
        kvCache.set(k, parentRepo.getAll(k))
    }

    protected suspend fun actualizeAll() {
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

}

open class FullWriteKeyValuesCacheRepo<Key,Value>(
    protected open val parentRepo: WriteKeyValueRepo<Key, Value>,
    protected open val kvCache: FullKVCache<Key, Value>,
    scope: CoroutineScope = CoroutineScope(Dispatchers.Default)
) : WriteKeyValueRepo<Key, Value> by parentRepo {
    protected val onNewJob = parentRepo.onNewValue.onEach { kvCache.set(it.first, it.second) }.launchIn(scope)
    protected val onRemoveJob = parentRepo.onValueRemoved.onEach { kvCache.unset(it) }.launchIn(scope)
}

open class FullKeyValuesCacheRepo<Key,Value>(
    parentRepo: KeyValueRepo<Key, Value>,
    kvCache: FullKVCache<Key, Value>,
    scope: CoroutineScope = CoroutineScope(Dispatchers.Default)
) : FullWriteKeyValueCacheRepo<Key,Value>(parentRepo, kvCache, scope),
    KeyValueRepo<Key,Value>,
    ReadKeyValueRepo<Key, Value> by FullReadKeyValueCacheRepo(parentRepo, kvCache) {
    override suspend fun unsetWithValues(toUnset: List<Value>) = parentRepo.unsetWithValues(toUnset)
}
