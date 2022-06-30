package dev.inmo.micro_utils.repos.cache.full

import dev.inmo.micro_utils.common.*
import dev.inmo.micro_utils.pagination.Pagination
import dev.inmo.micro_utils.pagination.PaginationResult
import dev.inmo.micro_utils.pagination.utils.doForAllWithNextPaging
import dev.inmo.micro_utils.repos.*
import dev.inmo.micro_utils.repos.cache.*
import dev.inmo.micro_utils.repos.cache.cache.FullKVCache
import dev.inmo.micro_utils.repos.cache.cache.KVCache
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

open class FullReadCRUDCacheRepo<ObjectType, IdType>(
    protected open val parentRepo: ReadCRUDRepo<ObjectType, IdType>,
    protected open val kvCache: FullKVCache<IdType, ObjectType>,
    protected open val idGetter: (ObjectType) -> IdType
) : ReadCRUDRepo<ObjectType, IdType>, FullCacheRepo {
    protected inline fun <T> doOrTakeAndActualize(
        action: FullKVCache<IdType, ObjectType>.() -> Optional<T>,
        actionElse: ReadCRUDRepo<ObjectType, IdType>.() -> T,
        actualize: FullKVCache<IdType, ObjectType>.(T) -> Unit
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
        doForAllWithNextPaging {
            parentRepo.getByPagination(it).also {
                kvCache.set(it.results.associateBy { idGetter(it) })
            }
        }
    }

    override suspend fun getByPagination(pagination: Pagination): PaginationResult<ObjectType> = doOrTakeAndActualize(
        { values(pagination).takeIf { it.results.isNotEmpty() }.optionalOrAbsentIfNull },
        { getByPagination(pagination) },
        { if (it.results.isNotEmpty()) actualizeAll() }
    )

    override suspend fun count(): Long = doOrTakeAndActualize(
        { count().takeIf { it != 0L }.optionalOrAbsentIfNull },
        { count() },
        { if (it != 0L) actualizeAll() }
    )

    override suspend fun contains(id: IdType): Boolean = doOrTakeAndActualize(
        { contains(id).takeIf { it }.optionalOrAbsentIfNull },
        { contains(id) },
        { if (it) parentRepo.getById(id) ?.let { set(id, it) } }
    )

    override suspend fun getById(id: IdType): ObjectType? = doOrTakeAndActualize(
        { get(id) ?.optional ?: Optional.absent() },
        { getById(id) },
        { it ?.let { set(idGetter(it), it) } }
    )
}

fun <ObjectType, IdType> ReadCRUDRepo<ObjectType, IdType>.cached(
    kvCache: FullKVCache<IdType, ObjectType>,
    idGetter: (ObjectType) -> IdType
) = FullReadCRUDCacheRepo(this, kvCache, idGetter)

open class FullCRUDCacheRepo<ObjectType, IdType, InputValueType>(
    override val parentRepo: CRUDRepo<ObjectType, IdType, InputValueType>,
    kvCache: FullKVCache<IdType, ObjectType>,
    scope: CoroutineScope = CoroutineScope(Dispatchers.Default),
    idGetter: (ObjectType) -> IdType
) : FullReadCRUDCacheRepo<ObjectType, IdType>(
    parentRepo,
    kvCache,
    idGetter
),
    WriteCRUDRepo<ObjectType, IdType, InputValueType> by WriteCRUDCacheRepo(
        parentRepo,
        kvCache,
        scope,
        idGetter
    ),
    CRUDRepo<ObjectType, IdType, InputValueType>

fun <ObjectType, IdType, InputType> CRUDRepo<ObjectType, IdType, InputType>.cached(
    kvCache: FullKVCache<IdType, ObjectType>,
    scope: CoroutineScope = CoroutineScope(Dispatchers.Default),
    idGetter: (ObjectType) -> IdType
) = FullCRUDCacheRepo(this, kvCache, scope, idGetter)
