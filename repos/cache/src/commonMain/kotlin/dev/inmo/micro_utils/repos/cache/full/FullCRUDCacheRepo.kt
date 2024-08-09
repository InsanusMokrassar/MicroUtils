package dev.inmo.micro_utils.repos.cache.full

import dev.inmo.micro_utils.common.*
import dev.inmo.micro_utils.coroutines.SmartRWLocker
import dev.inmo.micro_utils.coroutines.launchSafelyWithoutExceptions
import dev.inmo.micro_utils.coroutines.withReadAcquire
import dev.inmo.micro_utils.coroutines.withWriteLock
import dev.inmo.micro_utils.pagination.Pagination
import dev.inmo.micro_utils.pagination.PaginationResult
import dev.inmo.micro_utils.repos.*
import dev.inmo.micro_utils.repos.cache.*
import dev.inmo.micro_utils.repos.cache.util.ActualizeAllClearMode
import dev.inmo.micro_utils.repos.cache.util.actualizeAll
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

open class FullReadCRUDCacheRepo<ObjectType, IdType>(
    protected open val parentRepo: ReadCRUDRepo<ObjectType, IdType>,
    protected open val kvCache: KeyValueRepo<IdType, ObjectType>,
    protected val locker: SmartRWLocker = SmartRWLocker(),
    protected open val idGetter: (ObjectType) -> IdType
) : ReadCRUDRepo<ObjectType, IdType>, FullCacheRepo {
    protected suspend inline fun <T> doOrTakeAndActualize(
        action: KeyValueRepo<IdType, ObjectType>.() -> Optional<T>,
        actionElse: ReadCRUDRepo<ObjectType, IdType>.() -> T,
        actualize: KeyValueRepo<IdType, ObjectType>.(T) -> Unit
    ): T {
        locker.withReadAcquire {
            kvCache.action().onPresented { return it }
        }
        return parentRepo.actionElse().also {
            kvCache.actualize(it)
        }
    }
    protected suspend inline fun <T> doOrTakeAndActualizeWithWriteLock(
        action: KeyValueRepo<IdType, ObjectType>.() -> Optional<T>,
        actionElse: ReadCRUDRepo<ObjectType, IdType>.() -> T,
        actualize: KeyValueRepo<IdType, ObjectType>.(T) -> Unit
    ): T = doOrTakeAndActualize(
        action = action,
        actionElse = actionElse,
        actualize = { locker.withWriteLock { actualize(it) } }
    )

    protected open suspend fun actualizeAll() {
        kvCache.actualizeAll(parentRepo, locker = locker)
    }

    override suspend fun getByPagination(pagination: Pagination): PaginationResult<ObjectType> = doOrTakeAndActualize(
        { values(pagination).takeIf { it.results.isNotEmpty() }.optionalOrAbsentIfNull },
        { getByPagination(pagination) },
        { if (it.results.isNotEmpty()) actualizeAll() }
    )

    override suspend fun getIdsByPagination(pagination: Pagination): PaginationResult<IdType> = doOrTakeAndActualize(
        { keys(pagination).takeIf { it.results.isNotEmpty() }.optionalOrAbsentIfNull },
        { getIdsByPagination(pagination) },
        { if (it.results.isNotEmpty()) actualizeAll() }
    )

    override suspend fun count(): Long = doOrTakeAndActualize(
        { count().takeIf { it != 0L }.optionalOrAbsentIfNull },
        { count() },
        { if (it != 0L) actualizeAll() }
    )

    override suspend fun contains(id: IdType): Boolean = doOrTakeAndActualizeWithWriteLock(
        { contains(id).takeIf { it }.optionalOrAbsentIfNull },
        { contains(id) },
        { if (it) parentRepo.getById(id) ?.let { kvCache.set(id, it) } }
    )

    override suspend fun getAll(): Map<IdType, ObjectType> = doOrTakeAndActualizeWithWriteLock(
        { getAll().takeIf { it.isNotEmpty() }.optionalOrAbsentIfNull },
        { getAll() },
        { kvCache.actualizeAll(clearMode = ActualizeAllClearMode.BeforeSet) { it } }
    )

    override suspend fun getById(id: IdType): ObjectType? = doOrTakeAndActualizeWithWriteLock(
        { get(id) ?.optional ?: Optional.absent() },
        { getById(id) },
        { it ?.let { kvCache.set(idGetter(it), it) } }
    )

    override suspend fun invalidate() {
        actualizeAll()
    }
}

fun <ObjectType, IdType> ReadCRUDRepo<ObjectType, IdType>.cached(
    kvCache: KeyValueRepo<IdType, ObjectType>,
    locker: SmartRWLocker = SmartRWLocker(),
    idGetter: (ObjectType) -> IdType
) = FullReadCRUDCacheRepo(this, kvCache, locker, idGetter)

open class FullCRUDCacheRepo<ObjectType, IdType, InputValueType>(
    override val parentRepo: CRUDRepo<ObjectType, IdType, InputValueType>,
    kvCache: KeyValueRepo<IdType, ObjectType>,
    scope: CoroutineScope = CoroutineScope(Dispatchers.Default),
    skipStartInvalidate: Boolean = false,
    locker: SmartRWLocker = SmartRWLocker(writeIsLocked = !skipStartInvalidate),
    idGetter: (ObjectType) -> IdType
) : FullReadCRUDCacheRepo<ObjectType, IdType>(
    parentRepo,
    kvCache,
    locker,
    idGetter
),
    WriteCRUDRepo<ObjectType, IdType, InputValueType> by WriteCRUDCacheRepo(
        parentRepo,
        kvCache,
        scope,
        locker,
        idGetter
    ),
    CRUDRepo<ObjectType, IdType, InputValueType> {
    init {
        if (!skipStartInvalidate) {
            scope.launchSafelyWithoutExceptions {
                if (locker.writeMutex.isLocked) {
                    initialInvalidate()
                } else {
                    invalidate()
                }
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
    override suspend fun invalidate() {
        actualizeAll()
    }
}

fun <ObjectType, IdType, InputType> CRUDRepo<ObjectType, IdType, InputType>.fullyCached(
    kvCache: KeyValueRepo<IdType, ObjectType> = MapKeyValueRepo(),
    scope: CoroutineScope = CoroutineScope(Dispatchers.Default),
    skipStartInvalidate: Boolean = false,
    locker: SmartRWLocker = SmartRWLocker(),
    idGetter: (ObjectType) -> IdType
) = FullCRUDCacheRepo(this, kvCache, scope, skipStartInvalidate, locker, idGetter)

@Deprecated("Renamed", ReplaceWith("this.fullyCached(kvCache, scope, idGetter)", "dev.inmo.micro_utils.repos.cache.full.fullyCached"))
fun <ObjectType, IdType, InputType> CRUDRepo<ObjectType, IdType, InputType>.cached(
    kvCache: KeyValueRepo<IdType, ObjectType>,
    scope: CoroutineScope = CoroutineScope(Dispatchers.Default),
    skipStartInvalidate: Boolean = false,
    locker: SmartRWLocker = SmartRWLocker(),
    idGetter: (ObjectType) -> IdType
) = fullyCached(kvCache, scope, skipStartInvalidate, locker, idGetter)
