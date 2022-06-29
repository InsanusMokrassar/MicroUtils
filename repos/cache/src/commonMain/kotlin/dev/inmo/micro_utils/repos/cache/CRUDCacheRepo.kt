package dev.inmo.micro_utils.repos.cache

import dev.inmo.micro_utils.repos.*
import dev.inmo.micro_utils.repos.cache.cache.KVCache
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

open class ReadCRUDCacheRepo<ObjectType, IdType>(
    protected open val parentRepo: ReadCRUDRepo<ObjectType, IdType>,
    protected open val kvCache: KVCache<IdType, ObjectType>,
    protected open val idGetter: (ObjectType) -> IdType
) : ReadCRUDRepo<ObjectType, IdType> by parentRepo {
    override suspend fun getById(id: IdType): ObjectType? = kvCache.get(id) ?: (parentRepo.getById(id) ?.also {
        kvCache.set(id, it)
    })

    override suspend fun contains(id: IdType): Boolean = kvCache.contains(id) || parentRepo.contains(id)
}

open class WriteCRUDCacheRepo<ObjectType, IdType, InputValueType>(
    protected open val parentRepo: CRUDRepo<ObjectType, IdType, InputValueType>,
    protected open val kvCache: KVCache<IdType, ObjectType>,
    protected open val scope: CoroutineScope = CoroutineScope(Dispatchers.Default),
    protected open val idGetter: (ObjectType) -> IdType
) : WriteCRUDRepo<ObjectType, IdType, InputValueType> {
    override val newObjectsFlow: Flow<ObjectType> by parentRepo::newObjectsFlow
    override val updatedObjectsFlow: Flow<ObjectType> by parentRepo::updatedObjectsFlow
    override val deletedObjectsIdsFlow: Flow<IdType> by parentRepo::deletedObjectsIdsFlow

    val deletedObjectsFlowJob = parentRepo.deletedObjectsIdsFlow.onEach {
        kvCache.unset(it)
    }.launchIn(scope)

    override suspend fun deleteById(ids: List<IdType>) = parentRepo.deleteById(ids)

    override suspend fun update(values: List<UpdatedValuePair<IdType, InputValueType>>): List<ObjectType> {
        val updated = parentRepo.update(values)

        kvCache.unset(values.map { it.id })
        kvCache.set(updated.associateBy { idGetter(it) })

        return updated
    }

    override suspend fun update(id: IdType, value: InputValueType): ObjectType? {
        return parentRepo.update(id, value) ?.also {
            kvCache.unset(id)
            kvCache.set(idGetter(it), it)
        }
    }

    override suspend fun create(values: List<InputValueType>): List<ObjectType> {
        val created = parentRepo.create(values)

        kvCache.set(
            created.associateBy { idGetter(it) }
        )

        return created
    }
}


open class CRUDCacheRepo<ObjectType, IdType, InputValueType>(
    override val parentRepo: CRUDRepo<ObjectType, IdType, InputValueType>,
    kvCache: KVCache<IdType, ObjectType>,
    scope: CoroutineScope = CoroutineScope(Dispatchers.Default),
    idGetter: (ObjectType) -> IdType
) : ReadCRUDCacheRepo<ObjectType, IdType>(
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
