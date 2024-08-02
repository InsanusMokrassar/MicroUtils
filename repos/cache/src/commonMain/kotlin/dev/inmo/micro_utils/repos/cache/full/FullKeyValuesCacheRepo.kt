package dev.inmo.micro_utils.repos.cache.full

import dev.inmo.micro_utils.common.*
import dev.inmo.micro_utils.coroutines.SmartRWLocker
import dev.inmo.micro_utils.coroutines.launchSafelyWithoutExceptions
import dev.inmo.micro_utils.coroutines.withReadAcquire
import dev.inmo.micro_utils.coroutines.withWriteLock
import dev.inmo.micro_utils.pagination.*
import dev.inmo.micro_utils.pagination.utils.*
import dev.inmo.micro_utils.repos.*
import dev.inmo.micro_utils.repos.cache.util.ActualizeAllClearMode
import dev.inmo.micro_utils.repos.cache.util.actualizeAll
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

open class FullReadKeyValuesCacheRepo<Key,Value>(
    protected open val parentRepo: ReadKeyValuesRepo<Key, Value>,
    protected open val kvCache: KeyValueRepo<Key, List<Value>>,
    protected val locker: SmartRWLocker = SmartRWLocker(),
) : ReadKeyValuesRepo<Key, Value>, FullCacheRepo {
    protected suspend inline fun <T> doOrTakeAndActualize(
        action: KeyValueRepo<Key, List<Value>>.() -> Optional<T>,
        actionElse: ReadKeyValuesRepo<Key, Value>.() -> T,
        actualize: KeyValueRepo<Key, List<Value>>.(T) -> Unit
    ): T {
        locker.withReadAcquire {
            kvCache.action().onPresented { return it }
        }
        return parentRepo.actionElse().also {
            kvCache.actualize(it)
        }
    }
    protected suspend inline fun <T> doOrTakeAndActualizeWithWriteLock(
        action: KeyValueRepo<Key, List<Value>>.() -> Optional<T>,
        actionElse: ReadKeyValuesRepo<Key, Value>.() -> T,
        actualize: KeyValueRepo<Key, List<Value>>.(T) -> Unit
    ): T = doOrTakeAndActualize(
        action = action,
        actionElse = actionElse,
        actualize = { locker.withWriteLock { actualize(it) } }
    )

    protected open suspend fun actualizeKey(k: Key) {
        kvCache.actualizeAll(locker = locker, clearMode = ActualizeAllClearMode.Never) {
            mapOf(k to parentRepo.getAll(k))
        }
    }

    protected open suspend fun actualizeAll() {
        kvCache.actualizeAll(parentRepo, locker = locker)
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

    override suspend fun getAll(k: Key, reversed: Boolean): List<Value> {
        return doOrTakeAndActualizeWithWriteLock(
            {
                get(k) ?.optionallyReverse(reversed).optionalOrAbsentIfNull
            },
            { getAll(k, reversed) },
            { kvCache.set(k, it.optionallyReverse(reversed)) }
        )
    }

    override suspend fun getAll(reverseLists: Boolean): Map<Key, List<Value>> {
        return doOrTakeAndActualizeWithWriteLock(
            {
                getAll().takeIf { it.isNotEmpty() } ?.let {
                    if (reverseLists) {
                        it.mapValues { it.value.reversed() }
                    } else {
                        it
                    }
                }.optionalOrAbsentIfNull
            },
            { getAll(reverseLists) },
            {
                kvCache.set(
                    it.let {
                        if (reverseLists) {
                            it.mapValues { it.value.reversed() }
                        } else {
                            it
                        }
                    }
                )
            }
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
    kvCache: KeyValueRepo<Key, List<Value>>,
    locker: SmartRWLocker = SmartRWLocker(),
) = FullReadKeyValuesCacheRepo(this, kvCache, locker)

open class FullWriteKeyValuesCacheRepo<Key,Value>(
    parentRepo: WriteKeyValuesRepo<Key, Value>,
    protected open val kvCache: KeyValueRepo<Key, List<Value>>,
    scope: CoroutineScope = CoroutineScope(Dispatchers.Default),
    protected val locker: SmartRWLocker = SmartRWLocker(),
) : WriteKeyValuesRepo<Key, Value> by parentRepo, FullCacheRepo {
    protected val onNewJob = parentRepo.onNewValue.onEach {
        locker.withWriteLock {
            kvCache.set(
                it.first,
                kvCache.get(it.first) ?.plus(it.second) ?: listOf(it.second)
            )
        }
    }.launchIn(scope)
    protected val onRemoveJob = parentRepo.onValueRemoved.onEach {
        locker.withWriteLock {
            kvCache.set(
                it.first,
                kvCache.get(it.first)?.minus(it.second) ?: return@onEach
            )
        }
    }.launchIn(scope)

    override suspend fun invalidate() {
        locker.withWriteLock {
            kvCache.clear()
        }
    }
}

fun <Key, Value> WriteKeyValuesRepo<Key, Value>.caching(
    kvCache: KeyValueRepo<Key, List<Value>>,
    scope: CoroutineScope = CoroutineScope(Dispatchers.Default),
    locker: SmartRWLocker = SmartRWLocker(),
) = FullWriteKeyValuesCacheRepo(this, kvCache, scope, locker)

open class FullKeyValuesCacheRepo<Key,Value>(
    override val parentRepo: KeyValuesRepo<Key, Value>,
    kvCache: KeyValueRepo<Key, List<Value>>,
    scope: CoroutineScope = CoroutineScope(Dispatchers.Default),
    skipStartInvalidate: Boolean = false,
    locker: SmartRWLocker = SmartRWLocker(),
) : //FullWriteKeyValuesCacheRepo<Key, Value>(parentRepo, kvCache, scope, locker),
    KeyValuesRepo<Key, Value>,
    FullReadKeyValuesCacheRepo<Key, Value>(parentRepo, kvCache, locker),
    WriteKeyValuesRepo<Key, Value> by parentRepo {
    init {
        if (!skipStartInvalidate) {
            scope.launchSafelyWithoutExceptions { invalidate() }
        }
    }

    override suspend fun clearWithValue(v: Value) {
        doAllWithCurrentPaging {
            keys(v, it).also {
                remove(it.results.associateWith { listOf(v) })
            }
        }
    }

    override suspend fun invalidate() {
        kvCache.actualizeAll(parentRepo, locker = locker)
    }

    override suspend fun set(toSet: Map<Key, List<Value>>) {
        locker.withWriteLock {
            parentRepo.set(toSet)
            kvCache.set(
                toSet.filter {
                    parentRepo.contains(it.key)
                }
            )
        }
    }

    override suspend fun add(toAdd: Map<Key, List<Value>>) {
        locker.withWriteLock {
            parentRepo.add(toAdd)
            toAdd.forEach {
                val filtered = it.value.filter { v ->
                    parentRepo.contains(it.key, v)
                }.ifEmpty {
                    return@forEach
                }
                kvCache.set(
                    it.key,
                    (kvCache.get(it.key) ?: emptyList()) + filtered
                )
            }
        }
    }

    override suspend fun remove(toRemove: Map<Key, List<Value>>) {
        locker.withWriteLock {
            parentRepo.remove(toRemove)
            toRemove.forEach {
                val filtered = it.value.filter { v ->
                    !parentRepo.contains(it.key, v)
                }.ifEmpty {
                    return@forEach
                }.toSet()
                val resultList = (kvCache.get(it.key) ?: emptyList()) - filtered
                if (resultList.isEmpty()) {
                    kvCache.unset(it.key)
                } else {
                    kvCache.set(
                        it.key,
                        resultList
                    )
                }
            }
        }
    }

    override suspend fun clear(k: Key) {
        locker.withWriteLock {
            parentRepo.clear(k)
            if (parentRepo.contains(k)) {
                return@withWriteLock
            }
            kvCache.unset(k)
        }
    }
}

fun <Key, Value> KeyValuesRepo<Key, Value>.fullyCached(
    kvCache: KeyValueRepo<Key, List<Value>> = MapKeyValueRepo(),
    scope: CoroutineScope = CoroutineScope(Dispatchers.Default),
    skipStartInvalidate: Boolean = false,
    locker: SmartRWLocker = SmartRWLocker(),
) = FullKeyValuesCacheRepo(this, kvCache, scope, skipStartInvalidate, locker)

@Deprecated("Renamed", ReplaceWith("this.fullyCached(kvCache, scope)", "dev.inmo.micro_utils.repos.cache.full.fullyCached"))
fun <Key, Value> KeyValuesRepo<Key, Value>.caching(
    kvCache: KeyValueRepo<Key, List<Value>>,
    scope: CoroutineScope = CoroutineScope(Dispatchers.Default),
    skipStartInvalidate: Boolean = false,
    locker: SmartRWLocker = SmartRWLocker(),
) = FullKeyValuesCacheRepo(this, kvCache, scope, skipStartInvalidate, locker)
