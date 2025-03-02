package dev.inmo.micro_utils.repos.cache.fallback.keyvalue

import dev.inmo.micro_utils.coroutines.runCatchingSafely
import dev.inmo.micro_utils.pagination.Pagination
import dev.inmo.micro_utils.pagination.PaginationResult
import dev.inmo.micro_utils.repos.KeyValueRepo
import dev.inmo.micro_utils.repos.MapKeyValueRepo
import dev.inmo.micro_utils.repos.ReadKeyValueRepo
import dev.inmo.micro_utils.repos.cache.fallback.ActionWrapper
import dev.inmo.micro_utils.repos.cache.util.actualizeAll
import dev.inmo.micro_utils.repos.cache.FallbackCacheRepo
import dev.inmo.micro_utils.repos.cache.util.ActualizeAllClearMode
import dev.inmo.micro_utils.repos.set
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

open class AutoRecacheReadKeyValueRepo<Id, RegisteredObject>(
    protected val originalRepo: ReadKeyValueRepo<Id, RegisteredObject>,
    protected val scope: CoroutineScope,
    protected val kvCache: KeyValueRepo<Id, RegisteredObject> = MapKeyValueRepo(),
    protected val recacheDelay: Long = 60.seconds.inWholeMilliseconds,
    protected val actionWrapper: ActionWrapper = ActionWrapper.Direct,
    protected val idGetter: (RegisteredObject) -> Id
) : ReadKeyValueRepo<Id, RegisteredObject>, FallbackCacheRepo {
    val autoUpdateJob = scope.launch {
        while (isActive) {
            actualizeAll()

            delay(recacheDelay)
        }
    }

    constructor(
        originalRepo: ReadKeyValueRepo<Id, RegisteredObject>,
        scope: CoroutineScope,
        originalCallTimeoutMillis: Long,
        kvCache: KeyValueRepo<Id, RegisteredObject> = MapKeyValueRepo(),
        recacheDelay: Long = 60.seconds.inWholeMilliseconds,
        idGetter: (RegisteredObject) -> Id
    ) : this(originalRepo, scope, kvCache, recacheDelay, ActionWrapper.Timeouted(originalCallTimeoutMillis), idGetter)

    protected open suspend fun actualizeAll(): Result<Unit> {
        return runCatchingSafely {
            kvCache.actualizeAll(originalRepo)
        }
    }

    override suspend fun contains(key: Id): Boolean = actionWrapper.wrap {
        originalRepo.contains(key)
    }.getOrElse {
        kvCache.contains(key)
    }

    override suspend fun getAll(): Map<Id, RegisteredObject> = actionWrapper.wrap {
        originalRepo.getAll()
    }.onSuccess {
        kvCache.actualizeAll(clearMode = ActualizeAllClearMode.BeforeSet) { it }
    }.getOrElse {
        kvCache.getAll()
    }

    override suspend fun count(): Long = actionWrapper.wrap {
        originalRepo.count()
    }.getOrElse {
        kvCache.count()
    }

    override suspend fun get(k: Id): RegisteredObject? = actionWrapper.wrap {
        originalRepo.get(k)
    }.getOrNull() ?.also {
        kvCache.set(k, it)
    } ?: kvCache.get(k)

    override suspend fun values(
        pagination: Pagination,
        reversed: Boolean
    ): PaginationResult<RegisteredObject> = actionWrapper.wrap {
        originalRepo.values(pagination, reversed)
    }.getOrNull() ?.also {
        it.results.forEach {
            kvCache.set(idGetter(it), it)
        }
    } ?: kvCache.values(pagination, reversed)

    override suspend fun keys(
        pagination: Pagination,
        reversed: Boolean
    ): PaginationResult<Id> = actionWrapper.wrap {
        originalRepo.keys(pagination, reversed)
    }.getOrElse { kvCache.keys(pagination, reversed) }

    override suspend fun keys(
        v: RegisteredObject,
        pagination: Pagination,
        reversed: Boolean
    ): PaginationResult<Id> = actionWrapper.wrap {
        originalRepo.keys(v, pagination, reversed)
    }.getOrElse { kvCache.keys(v, pagination, reversed) }

    override suspend fun invalidate() {
        actualizeAll()
    }
}
