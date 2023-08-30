package dev.inmo.micro_utils.repos.cache

import dev.inmo.micro_utils.coroutines.SmartRWLocker
import dev.inmo.micro_utils.coroutines.withReadAcquire
import dev.inmo.micro_utils.coroutines.withWriteLock
import dev.inmo.micro_utils.repos.*
import dev.inmo.micro_utils.repos.cache.cache.KVCache
import dev.inmo.micro_utils.repos.cache.util.actualizeAll
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

open class ReadCRUDCacheRepo<ObjectType, IdType>(
    protected open val parentRepo: ReadCRUDRepo<ObjectType, IdType>,
    protected open val kvCache: KVCache<IdType, ObjectType>,
    protected val locker: SmartRWLocker = SmartRWLocker(),
    protected open val idGetter: (ObjectType) -> IdType
) : ReadCRUDRepo<ObjectType, IdType> by parentRepo, CommonCacheRepo {
    override suspend fun getById(id: IdType): ObjectType? = locker.withReadAcquire {
        kvCache.get(id)
    } ?: (parentRepo.getById(id) ?.also {
        locker.withWriteLock {
            kvCache.set(id, it)
        }
    })

    override suspend fun getAll(): Map<IdType, ObjectType> {
        return locker.withReadAcquire {
            kvCache.getAll()
        }.takeIf { it.size.toLong() == count() } ?: parentRepo.getAll().also {
            locker.withWriteLock {
                kvCache.actualizeAll(true) { it }
            }
        }
    }

    override suspend fun contains(id: IdType): Boolean = locker.withReadAcquire {
        kvCache.contains(id)
    } || parentRepo.contains(id)

    override suspend fun invalidate() = locker.withWriteLock {
        kvCache.clear()
    }
}

fun <ObjectType, IdType> ReadCRUDRepo<ObjectType, IdType>.cached(
    kvCache: KVCache<IdType, ObjectType>,
    locker: SmartRWLocker = SmartRWLocker(),
    idGetter: (ObjectType) -> IdType
) = ReadCRUDCacheRepo(this, kvCache, locker, idGetter)

open class WriteCRUDCacheRepo<ObjectType, IdType, InputValueType>(
    protected open val parentRepo: WriteCRUDRepo<ObjectType, IdType, InputValueType>,
    protected open val kvCache: KeyValueRepo<IdType, ObjectType>,
    protected open val scope: CoroutineScope = CoroutineScope(Dispatchers.Default),
    protected val locker: SmartRWLocker = SmartRWLocker(),
    protected open val idGetter: (ObjectType) -> IdType
) : WriteCRUDRepo<ObjectType, IdType, InputValueType>, CommonCacheRepo {
    override val newObjectsFlow: Flow<ObjectType> by parentRepo::newObjectsFlow
    override val updatedObjectsFlow: Flow<ObjectType> by parentRepo::updatedObjectsFlow
    override val deletedObjectsIdsFlow: Flow<IdType> by parentRepo::deletedObjectsIdsFlow

    val createdObjectsFlowJob = parentRepo.newObjectsFlow.onEach {
        locker.withWriteLock {
            kvCache.set(idGetter(it), it)
        }
    }.launchIn(scope)

    val updatedObjectsFlowJob = parentRepo.updatedObjectsFlow.onEach {
        locker.withWriteLock {
            kvCache.set(idGetter(it), it)
        }
    }.launchIn(scope)

    val deletedObjectsFlowJob = parentRepo.deletedObjectsIdsFlow.onEach {
        locker.withWriteLock {
            kvCache.unset(it)
        }
    }.launchIn(scope)

    override suspend fun deleteById(ids: List<IdType>) = parentRepo.deleteById(ids).also {
        locker.withWriteLock {
            kvCache.unset(ids)
        }
    }

    override suspend fun update(values: List<UpdatedValuePair<IdType, InputValueType>>): List<ObjectType> {
        val updated = parentRepo.update(values)

        locker.withWriteLock {
            kvCache.unset(values.map { it.id })
            kvCache.set(updated.associateBy { idGetter(it) })
        }

        return updated
    }

    override suspend fun update(id: IdType, value: InputValueType): ObjectType? {
        return parentRepo.update(id, value) ?.also {
            locker.withWriteLock {
                kvCache.unset(id)
                kvCache.set(idGetter(it), it)
            }
        }
    }

    override suspend fun create(values: List<InputValueType>): List<ObjectType> {
        val created = parentRepo.create(values)

        locker.withWriteLock {
            kvCache.set(
                created.associateBy { idGetter(it) }
            )
        }

        return created
    }

    override suspend fun invalidate() = locker.withWriteLock {
        kvCache.clear()
    }
}

fun <ObjectType, IdType, InputType> WriteCRUDRepo<ObjectType, IdType, InputType>.caching(
    kvCache: KVCache<IdType, ObjectType>,
    scope: CoroutineScope,
    locker: SmartRWLocker = SmartRWLocker(),
    idGetter: (ObjectType) -> IdType
) = WriteCRUDCacheRepo(this, kvCache, scope, locker, idGetter)


open class CRUDCacheRepo<ObjectType, IdType, InputValueType>(
    override val parentRepo: CRUDRepo<ObjectType, IdType, InputValueType>,
    kvCache: KVCache<IdType, ObjectType>,
    scope: CoroutineScope = CoroutineScope(Dispatchers.Default),
    locker: SmartRWLocker = SmartRWLocker(),
    idGetter: (ObjectType) -> IdType
) : ReadCRUDCacheRepo<ObjectType, IdType>(
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
    CRUDRepo<ObjectType, IdType, InputValueType>

fun <ObjectType, IdType, InputType> CRUDRepo<ObjectType, IdType, InputType>.cached(
    kvCache: KVCache<IdType, ObjectType>,
    scope: CoroutineScope,
    locker: SmartRWLocker = SmartRWLocker(),
    idGetter: (ObjectType) -> IdType
) = CRUDCacheRepo(this, kvCache, scope, locker, idGetter)
