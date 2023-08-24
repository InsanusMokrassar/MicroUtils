package dev.inmo.micro_utils.repos.cache.fallback.keyvalues

import dev.inmo.micro_utils.coroutines.runCatchingSafely
import dev.inmo.micro_utils.pagination.Pagination
import dev.inmo.micro_utils.pagination.PaginationResult
import dev.inmo.micro_utils.pagination.changeResultsUnchecked
import dev.inmo.micro_utils.pagination.createPaginationResult
import dev.inmo.micro_utils.pagination.emptyPaginationResult
import dev.inmo.micro_utils.pagination.firstIndex
import dev.inmo.micro_utils.pagination.utils.doForAllWithNextPaging
import dev.inmo.micro_utils.pagination.utils.optionallyReverse
import dev.inmo.micro_utils.pagination.utils.paginate
import dev.inmo.micro_utils.repos.KeyValueRepo
import dev.inmo.micro_utils.repos.MapKeyValueRepo
import dev.inmo.micro_utils.repos.ReadKeyValuesRepo
import dev.inmo.micro_utils.repos.cache.fallback.ActionWrapper
import dev.inmo.micro_utils.repos.cache.util.actualizeAll
import dev.inmo.micro_utils.repos.cache.FallbackCacheRepo
import dev.inmo.micro_utils.repos.set
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

open class AutoRecacheReadKeyValuesRepo<Id, RegisteredObject>(
    protected open val originalRepo: ReadKeyValuesRepo<Id, RegisteredObject>,
    protected val scope: CoroutineScope,
    protected val kvCache: KeyValueRepo<Id, List<RegisteredObject>> = MapKeyValueRepo(),
    protected val recacheDelay: Long = 60.seconds.inWholeMilliseconds,
    protected val actionWrapper: ActionWrapper = ActionWrapper.Direct
) : ReadKeyValuesRepo<Id, RegisteredObject>, FallbackCacheRepo {
    val autoUpdateJob = scope.launch {
        while (isActive) {
            actualizeAll()

            delay(recacheDelay)
        }
    }

    constructor(
        originalRepo: ReadKeyValuesRepo<Id, RegisteredObject>,
        scope: CoroutineScope,
        originalCallTimeoutMillis: Long,
        kvCache: KeyValueRepo<Id, List<RegisteredObject>> = MapKeyValueRepo(),
        recacheDelay: Long = 60.seconds.inWholeMilliseconds
    ) : this(originalRepo, scope, kvCache, recacheDelay, ActionWrapper.Timeouted(originalCallTimeoutMillis))

    protected open suspend fun actualizeAll(): Result<Unit> {
        return runCatchingSafely {
            kvCache.actualizeAll(originalRepo)
        }
    }

    override suspend fun contains(k: Id): Boolean = actionWrapper.wrap {
        originalRepo.contains(k)
    }.getOrElse {
        kvCache.contains(k)
    }

    override suspend fun count(): Long = actionWrapper.wrap {
        originalRepo.count()
    }.getOrElse {
        kvCache.count()
    }

    override suspend fun keys(
        v: RegisteredObject,
        pagination: Pagination,
        reversed: Boolean
    ): PaginationResult<Id> = actionWrapper.wrap {
        originalRepo.keys(v, pagination, reversed)
    }.getOrElse {
        val results = mutableListOf<Id>()

        val toSkip = pagination.firstIndex
        var count = 0

        doForAllWithNextPaging {
            kvCache.keys(pagination, reversed).also {
                it.results.forEach {
                    if (kvCache.get(it) ?.contains(v) == true) {
                        count++
                        if (count < toSkip || results.size >= pagination.size) {
                            return@forEach
                        } else {
                            results.add(it)
                        }
                    }
                }
            }
        }

        return@getOrElse results.createPaginationResult(
            pagination,
            count.toLong()
        )
    }

    override suspend fun keys(
        pagination: Pagination,
        reversed: Boolean
    ): PaginationResult<Id> = actionWrapper.wrap {
        originalRepo.keys(pagination, reversed)
    }.getOrElse { kvCache.keys(pagination, reversed) }

    override suspend fun get(
        k: Id,
        pagination: Pagination,
        reversed: Boolean
    ): PaginationResult<RegisteredObject> = actionWrapper.wrap {
        originalRepo.get(k, pagination, reversed)
    }.getOrNull() ?.also {
        it.results.forEach {
            kvCache.set(k, ((kvCache.get(k) ?: return@also) + it).distinct())
        }
    } ?: kvCache.get(k) ?.run {
        paginate(pagination.optionallyReverse(size, reversed)).let {
            if (reversed) {
                it.changeResultsUnchecked(
                    it.results.reversed()
                )
            } else {
                it
            }
        }
    } ?: emptyPaginationResult()

    override suspend fun count(k: Id): Long = actionWrapper.wrap {
        originalRepo.count(k)
    }.getOrElse {
        kvCache.get(k) ?.size ?.toLong() ?: 0L
    }

    override suspend fun contains(k: Id, v: RegisteredObject): Boolean {
        return (actionWrapper.wrap {
            originalRepo.contains(k, v)
        }.getOrNull() ?.also {
            kvCache.set(k, ((kvCache.get(k) ?: return@also) + v).distinct())
        }) ?: (kvCache.get(k) ?.contains(v) == true)
    }

    override suspend fun invalidate() {
        actualizeAll()
    }
}
