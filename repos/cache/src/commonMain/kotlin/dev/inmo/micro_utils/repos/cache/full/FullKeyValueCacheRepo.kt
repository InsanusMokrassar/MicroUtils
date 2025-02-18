package dev.inmo.micro_utils.repos.cache.full

import dev.inmo.micro_utils.common.*
import dev.inmo.micro_utils.coroutines.SmartRWLocker
import dev.inmo.micro_utils.coroutines.launchLoggingDropExceptions
import dev.inmo.micro_utils.coroutines.withReadAcquire
import dev.inmo.micro_utils.coroutines.withWriteLock
import dev.inmo.micro_utils.pagination.Pagination
import dev.inmo.micro_utils.pagination.PaginationResult
import dev.inmo.micro_utils.repos.*
import dev.inmo.micro_utils.repos.cache.util.ActualizeAllClearMode
import dev.inmo.micro_utils.repos.cache.util.actualizeAll
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

open class FullReadKeyValueCacheRepo<Key,Value>(
    protected open val parentRepo: ReadKeyValueRepo<Key, Value>,
    protected open val kvCache: KeyValueRepo<Key, Value>,
    protected open val locker: SmartRWLocker = SmartRWLocker()
) : ReadKeyValueRepo<Key, Value>, FullCacheRepo {
    protected suspend inline fun <T> doOrTakeAndActualize(
        action: KeyValueRepo<Key, Value>.() -> Optional<T>,
        actionElse: ReadKeyValueRepo<Key, Value>.() -> T,
        actualize: KeyValueRepo<Key, Value>.(T) -> Unit
    ): T {
        locker.withReadAcquire {
            kvCache.action().onPresented { return it }
        }
        return parentRepo.actionElse().also {
            kvCache.actualize(it)
        }
    }
    protected suspend inline fun <T> doOrTakeAndActualizeWithWriteLock(
        action: KeyValueRepo<Key, Value>.() -> Optional<T>,
        actionElse: ReadKeyValueRepo<Key, Value>.() -> T,
        actualize: KeyValueRepo<Key, Value>.(T) -> Unit
    ): T = doOrTakeAndActualize(
        action = action,
        actionElse = actionElse,
        actualize = { locker.withWriteLock { actualize(it) } }
    )
    protected open suspend fun actualizeAll() {
        kvCache.actualizeAll(parentRepo, locker)
    }

    override suspend fun get(k: Key): Value? = doOrTakeAndActualizeWithWriteLock(
        { get(k) ?.optional ?: Optional.absent() },
        { get(k) },
        { kvCache.set(k, it ?: return@doOrTakeAndActualizeWithWriteLock) }
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

    override suspend fun contains(key: Key): Boolean = doOrTakeAndActualizeWithWriteLock(
        { contains(key).takeIf { it }.optionalOrAbsentIfNull },
        { contains(key) },
        { if (it) parentRepo.get(key) ?.also { kvCache.set(key, it) } }
    )

    override suspend fun getAll(): Map<Key, Value> = doOrTakeAndActualizeWithWriteLock(
        { getAll().takeIf { it.isNotEmpty() }.optionalOrAbsentIfNull },
        { getAll() },
        { kvCache.actualizeAll(clearMode = ActualizeAllClearMode.BeforeSet) { it } }
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
    kvCache: KeyValueRepo<Key, Value>,
    locker: SmartRWLocker = SmartRWLocker()
) = FullReadKeyValueCacheRepo(this, kvCache, locker)

open class FullWriteKeyValueCacheRepo<Key,Value>(
    parentRepo: WriteKeyValueRepo<Key, Value>,
    protected open val kvCache: KeyValueRepo<Key, Value>,
    scope: CoroutineScope = CoroutineScope(Dispatchers.Default),
    protected val locker: SmartRWLocker = SmartRWLocker()
) : WriteKeyValueRepo<Key, Value> by parentRepo, FullCacheRepo {
    protected val onNewJob = parentRepo.onNewValue.onEach {
        locker.withWriteLock {
            kvCache.set(it.first, it.second)
        }
    }.launchIn(scope)
    protected val onRemoveJob = parentRepo.onValueRemoved.onEach {
        locker.withWriteLock {
            kvCache.unset(it)
        }
    }.launchIn(scope)

    override suspend fun invalidate() {
        locker.withWriteLock {
            kvCache.clear()
        }
    }
}

fun <Key, Value> WriteKeyValueRepo<Key, Value>.caching(
    kvCache: KeyValueRepo<Key, Value>,
    scope: CoroutineScope = CoroutineScope(Dispatchers.Default)
) = FullWriteKeyValueCacheRepo(this, kvCache, scope)

open class FullKeyValueCacheRepo<Key,Value>(
    override val parentRepo: KeyValueRepo<Key, Value>,
    override val kvCache: KeyValueRepo<Key, Value>,
    scope: CoroutineScope = CoroutineScope(Dispatchers.Default),
    skipStartInvalidate: Boolean = false,
    override val locker: SmartRWLocker = SmartRWLocker(writeIsLocked = !skipStartInvalidate),
) : //FullWriteKeyValueCacheRepo<Key,Value>(parentRepo, kvCache, scope),
    KeyValueRepo<Key,Value>,
    WriteKeyValueRepo<Key,Value> by parentRepo,
    FullReadKeyValueCacheRepo<Key, Value>(
        parentRepo,
        kvCache,
        locker
) {
    init {
        if (!skipStartInvalidate) {
            scope.launchLoggingDropExceptions {
                if (locker.writeMutex.isLocked) {
                    initialInvalidate()
                } else {
                    invalidate()
                }
            }
        }
    }

    override suspend fun unsetWithValues(toUnset: List<Value>) = parentRepo.unsetWithValues(toUnset)

    protected open suspend fun initialInvalidate() {
        try {
            kvCache.actualizeAll(parentRepo, locker = null)
        } finally {
            locker.unlockWrite()
        }
    }
    override suspend fun invalidate() {
        kvCache.actualizeAll(parentRepo, locker)
    }

    override suspend fun clear() {
        parentRepo.clear()
        kvCache.clear()
    }

    override suspend fun set(toSet: Map<Key, Value>) {
        locker.withWriteLock {
            parentRepo.set(toSet)
            kvCache.set(
                toSet.filter {
                    parentRepo.contains(it.key)
                }
            )
        }
    }

    override suspend fun unset(toUnset: List<Key>) {
        locker.withWriteLock {
            parentRepo.unset(toUnset)
            kvCache.unset(
                toUnset.filter {
                    !parentRepo.contains(it)
                }
            )
        }
    }
}

fun <Key, Value> KeyValueRepo<Key, Value>.fullyCached(
    kvCache: KeyValueRepo<Key, Value> = MapKeyValueRepo(),
    scope: CoroutineScope = CoroutineScope(Dispatchers.Default),
    skipStartInvalidate: Boolean = false,
    locker: SmartRWLocker = SmartRWLocker()
) = FullKeyValueCacheRepo(this, kvCache, scope, skipStartInvalidate, locker)

@Deprecated("Renamed", ReplaceWith("this.fullyCached(kvCache, scope)", "dev.inmo.micro_utils.repos.cache.full.fullyCached"))
fun <Key, Value> KeyValueRepo<Key, Value>.cached(
    kvCache: KeyValueRepo<Key, Value>,
    scope: CoroutineScope = CoroutineScope(Dispatchers.Default),
    skipStartInvalidate: Boolean = false,
    locker: SmartRWLocker = SmartRWLocker()
) = fullyCached(kvCache, scope, skipStartInvalidate, locker)
