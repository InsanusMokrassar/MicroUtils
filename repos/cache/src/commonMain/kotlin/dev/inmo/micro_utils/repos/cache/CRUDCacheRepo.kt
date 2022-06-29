package dev.inmo.micro_utils.repos.cache

import dev.inmo.micro_utils.repos.*
import dev.inmo.micro_utils.repos.cache.cache.KVCache
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

open class ReadCRUDCacheRepo<ObjectType, IdType>(
    protected val parentRepo: ReadCRUDRepo<ObjectType, IdType>,
    protected val kvCache: KVCache<IdType, ObjectType>,
    protected val idGetter: (ObjectType) -> IdType
) : ReadCRUDRepo<ObjectType, IdType> by parentRepo {
    override suspend fun getById(id: IdType): ObjectType? = kvCache.get(id) ?: (parentRepo.getById(id) ?.also {
        kvCache.set(id, it)
    })

    override suspend fun contains(id: IdType): Boolean = kvCache.contains(id) || parentRepo.contains(id)
}

open class CRUDCacheRepo<ObjectType, IdType, InputValueType>(
    parentRepo: CRUDRepo<ObjectType, IdType, InputValueType>,
    kvCache: KVCache<IdType, ObjectType>,
    scope: CoroutineScope = CoroutineScope(Dispatchers.Default),
    idGetter: (ObjectType) -> IdType
) : ReadCRUDCacheRepo<ObjectType, IdType>(
    parentRepo,
    kvCache,
    idGetter
), CRUDRepo<ObjectType, IdType, InputValueType>, WriteCRUDRepo<ObjectType, IdType, InputValueType> by parentRepo {
    protected val onNewJob = parentRepo.newObjectsFlow.onEach { kvCache.set(idGetter(it), it) }.launchIn(scope)
    protected val onUpdatedJob = parentRepo.updatedObjectsFlow.onEach { kvCache.set(idGetter(it), it) }.launchIn(scope)
    protected val onRemoveJob = parentRepo.deletedObjectsIdsFlow.onEach { kvCache.unset(it) }.launchIn(scope)
}
