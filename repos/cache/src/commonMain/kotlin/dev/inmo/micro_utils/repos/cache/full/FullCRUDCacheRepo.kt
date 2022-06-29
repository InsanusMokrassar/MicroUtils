package dev.inmo.micro_utils.repos.cache.full

import dev.inmo.micro_utils.pagination.Pagination
import dev.inmo.micro_utils.pagination.PaginationResult
import dev.inmo.micro_utils.repos.*
import dev.inmo.micro_utils.repos.cache.ReadCRUDCacheRepo
import dev.inmo.micro_utils.repos.cache.cache.KVCache
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


open class FullReadCRUDCacheRepo<ObjectType, IdType>(
    parentRepo: ReadCRUDRepo<ObjectType, IdType>,
    kvCache: KVCache<IdType, ObjectType>,
    idGetter: (ObjectType) -> IdType
) : ReadCRUDRepo<ObjectType, IdType>, ReadCRUDCacheRepo<ObjectType, IdType>(parentRepo, kvCache, idGetter) {
    override suspend fun getByPagination(pagination: Pagination): PaginationResult<ObjectType> {
        return kvCache.values(pagination)
    }

    override suspend fun count(): Long = kvCache.count()
}

open class FullCRUDCacheRepo<ObjectType, IdType, InputValueType>(
    parentRepo: CRUDRepo<ObjectType, IdType, InputValueType>,
    kvCache: KVCache<IdType, ObjectType>,
    scope: CoroutineScope = CoroutineScope(Dispatchers.Default),
    idGetter: (ObjectType) -> IdType
) : FullReadCRUDCacheRepo<ObjectType, IdType>(
    parentRepo,
    kvCache,
    idGetter
),
    CRUDRepo<ObjectType, IdType, InputValueType>,
    WriteCRUDRepo<ObjectType, IdType, InputValueType> by parentRepo {
    protected val onNewJob = parentRepo.newObjectsFlow.onEach { kvCache.set(idGetter(it), it) }.launchIn(scope)
    protected val onUpdatedJob = parentRepo.updatedObjectsFlow.onEach { kvCache.set(idGetter(it), it) }.launchIn(scope)
    protected val onRemoveJob = parentRepo.deletedObjectsIdsFlow.onEach { kvCache.unset(it) }.launchIn(scope)
}
