package dev.inmo.micro_utils.repos.cache.full.direct

import dev.inmo.micro_utils.coroutines.SmartRWLocker
import dev.inmo.micro_utils.coroutines.launchLoggingDropExceptions
import dev.inmo.micro_utils.coroutines.withReadAcquire
import dev.inmo.micro_utils.coroutines.withWriteLock
import dev.inmo.micro_utils.pagination.*
import dev.inmo.micro_utils.pagination.utils.*
import dev.inmo.micro_utils.repos.*
import dev.inmo.micro_utils.repos.annotations.OverrideRequireManualInvalidation
import dev.inmo.micro_utils.repos.cache.util.ActualizeAllClearMode
import dev.inmo.micro_utils.repos.cache.util.actualizeAll
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

open class DirectFullReadKeyValuesCacheRepo<Key,Value>(
    protected val parentRepo: ReadKeyValuesRepo<Key, Value>,
    protected val kvCache: KeyValueRepo<Key, List<Value>>,
    protected val locker: SmartRWLocker = SmartRWLocker(),
) : ReadKeyValuesRepo<Key, Value>, DirectFullCacheRepo {
    protected open suspend fun actualizeKey(k: Key) {
        kvCache.actualizeAll(locker = locker, clearMode = ActualizeAllClearMode.Never) {
            mapOf(k to parentRepo.getAll(k))
        }
    }

    protected open suspend fun actualizeAll() {
        kvCache.actualizeAll(parentRepo, locker = locker)
    }

    override suspend fun get(k: Key, pagination: Pagination, reversed: Boolean): PaginationResult<Value> {
        return locker.withReadAcquire {
            kvCache.get(k) ?.paginate(
                pagination.let { if (reversed) it.reverse(count(k)) else it }
            ) ?.let {
                if (reversed) it.copy(results = it.results.reversed()) else it
            }
        } ?: emptyPaginationResult()
    }

    override suspend fun getAll(k: Key, reversed: Boolean): List<Value> {
        return locker.withReadAcquire {
            kvCache.get(k) ?.optionallyReverse(reversed)
        } ?: emptyList()
    }

    override suspend fun getAll(reverseLists: Boolean): Map<Key, List<Value>> {
        return locker.withReadAcquire {
            kvCache.getAll().takeIf { it.isNotEmpty() } ?.let {
                if (reverseLists) {
                    it.mapValues { it.value.reversed() }
                } else {
                    it
                }
            }
        } ?: emptyMap()
    }
    override suspend fun keys(pagination: Pagination, reversed: Boolean): PaginationResult<Key> {
        return locker.withReadAcquire {
            kvCache.keys(pagination, reversed).takeIf { it.results.isNotEmpty() }
        } ?: emptyPaginationResult()
    }

    override suspend fun count(): Long = locker.withReadAcquire { kvCache.count() }

    override suspend fun count(k: Key): Long = locker.withReadAcquire { kvCache.get(k) ?.size } ?.toLong() ?: 0L

    override suspend fun contains(k: Key, v: Value): Boolean = locker.withReadAcquire { kvCache.get(k) ?.contains(v) } == true

    override suspend fun contains(k: Key): Boolean = locker.withReadAcquire { kvCache.contains(k) }

    override suspend fun keys(
        v: Value,
        pagination: Pagination,
        reversed: Boolean
    ): PaginationResult<Key> {
        val keys = locker.withReadAcquire {
            getAllWithNextPaging { kvCache.keys(it) }.filter { kvCache.get(it)?.contains(v) == true }
                .optionallyReverse(reversed)
        }
        val result = if (keys.isNotEmpty()) {
            keys.paginate(pagination.optionallyReverse(keys.size, reversed)).takeIf { it.results.isNotEmpty() }
        } else {
            null
        }

        return result ?: emptyPaginationResult()
    }

    @OverrideRequireManualInvalidation
    override suspend fun invalidate() {
        actualizeAll()
    }
}

fun <Key, Value> ReadKeyValuesRepo<Key, Value>.directlyCached(
    kvCache: KeyValueRepo<Key, List<Value>>,
    locker: SmartRWLocker = SmartRWLocker(),
) = DirectFullReadKeyValuesCacheRepo(this, kvCache, locker)

open class DirectFullWriteKeyValuesCacheRepo<Key,Value>(
    parentRepo: WriteKeyValuesRepo<Key, Value>,
    protected val kvCache: KeyValueRepo<Key, List<Value>>,
    scope: CoroutineScope = CoroutineScope(Dispatchers.Default),
    protected val locker: SmartRWLocker = SmartRWLocker(),
) : WriteKeyValuesRepo<Key, Value> by parentRepo, DirectFullCacheRepo {
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

    @OverrideRequireManualInvalidation
    override suspend fun invalidate() {
        locker.withWriteLock {
            kvCache.clear()
        }
    }
}

fun <Key, Value> WriteKeyValuesRepo<Key, Value>.directlyCached(
    kvCache: KeyValueRepo<Key, List<Value>>,
    scope: CoroutineScope = CoroutineScope(Dispatchers.Default),
    locker: SmartRWLocker = SmartRWLocker(),
) = DirectFullWriteKeyValuesCacheRepo(this, kvCache, scope, locker)

open class DirectFullKeyValuesCacheRepo<Key,Value>(
    protected val kvsRepo: KeyValuesRepo<Key, Value>,
    kvCache: KeyValueRepo<Key, List<Value>>,
    scope: CoroutineScope = CoroutineScope(Dispatchers.Default),
    skipStartInvalidate: Boolean = false,
    locker: SmartRWLocker = SmartRWLocker(writeIsLocked = !skipStartInvalidate),
) : KeyValuesRepo<Key, Value>,
    DirectFullReadKeyValuesCacheRepo<Key, Value>(kvsRepo, kvCache, locker),
    WriteKeyValuesRepo<Key, Value> by kvsRepo {
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

    override suspend fun clearWithValue(v: Value) {
        doAllWithCurrentPaging {
            keys(v, it).also {
                remove(it.results.associateWith { listOf(v) })
            }
        }
    }

    protected open suspend fun initialInvalidate() {
        try {
            kvCache.actualizeAll(parentRepo, locker = null)
        } finally {
            locker.unlockWrite()
        }
    }
    @OverrideRequireManualInvalidation
    override suspend fun invalidate() {
        kvCache.actualizeAll(parentRepo, locker = locker)
    }

    override suspend fun set(toSet: Map<Key, List<Value>>) {
        locker.withWriteLock {
            kvsRepo.set(toSet)
            kvCache.set(
                toSet.filter {
                    parentRepo.contains(it.key)
                }
            )
        }
    }

    override suspend fun add(toAdd: Map<Key, List<Value>>) {
        locker.withWriteLock {
            kvsRepo.add(toAdd)
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
            kvsRepo.remove(toRemove)
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
            kvsRepo.clear(k)
            if (parentRepo.contains(k)) {
                return@withWriteLock
            }
            kvCache.unset(k)
        }
    }
}

fun <Key, Value> KeyValuesRepo<Key, Value>.directlyFullyCached(
    kvCache: KeyValueRepo<Key, List<Value>> = MapKeyValueRepo(),
    scope: CoroutineScope = CoroutineScope(Dispatchers.Default),
    skipStartInvalidate: Boolean = false,
    locker: SmartRWLocker = SmartRWLocker(),
) = DirectFullKeyValuesCacheRepo(this, kvCache, scope, skipStartInvalidate, locker)
