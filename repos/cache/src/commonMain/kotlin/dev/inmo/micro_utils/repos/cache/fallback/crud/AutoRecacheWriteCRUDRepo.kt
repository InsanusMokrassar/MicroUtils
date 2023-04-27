package dev.inmo.micro_utils.repos.cache.fallback.crud

import dev.inmo.micro_utils.coroutines.plus
import dev.inmo.micro_utils.coroutines.subscribeSafelyWithoutExceptions
import dev.inmo.micro_utils.repos.UpdatedValuePair
import dev.inmo.micro_utils.repos.WriteCRUDRepo
import dev.inmo.micro_utils.repos.cache.cache.FullKVCache
import dev.inmo.micro_utils.repos.cache.FallbackCacheRepo
import dev.inmo.micro_utils.repos.set
import dev.inmo.micro_utils.repos.unset
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge

open class AutoRecacheWriteCRUDRepo<RegisteredObject, Id, InputObject>(
    protected val originalRepo: WriteCRUDRepo<RegisteredObject, Id, InputObject>,
    protected val scope: CoroutineScope,
    protected val kvCache: FullKVCache<Id, RegisteredObject> = FullKVCache(),
    protected val idGetter: (RegisteredObject) -> Id
) : WriteCRUDRepo<RegisteredObject, Id, InputObject>, FallbackCacheRepo {
    override val deletedObjectsIdsFlow: Flow<Id>
        get() = (originalRepo.deletedObjectsIdsFlow).distinctUntilChanged()
    override val newObjectsFlow: Flow<RegisteredObject>
        get() = (originalRepo.newObjectsFlow).distinctUntilChanged()
    override val updatedObjectsFlow: Flow<RegisteredObject>
        get() = originalRepo.updatedObjectsFlow

    private val onRemovingUpdatesListeningJob = originalRepo.deletedObjectsIdsFlow.subscribeSafelyWithoutExceptions(scope) {
        kvCache.unset(it)
    }

    private val onNewAndUpdatedObjectsListeningJob = merge(
        originalRepo.newObjectsFlow,
        originalRepo.updatedObjectsFlow,
    ).subscribeSafelyWithoutExceptions(scope) {
        kvCache.set(idGetter(it), it)
    }

    override suspend fun update(
        values: List<UpdatedValuePair<Id, InputObject>>
    ): List<RegisteredObject> = originalRepo.update(values).onEach {
        kvCache.set(idGetter(it), it)
    }

    override suspend fun update(
        id: Id,
        value: InputObject
    ): RegisteredObject? = originalRepo.update(id, value) ?.also {
        kvCache.set(idGetter(it), it)
    }

    override suspend fun deleteById(ids: List<Id>) = originalRepo.deleteById(ids).also {
        kvCache.unset(ids)
    }

    override suspend fun create(values: List<InputObject>): List<RegisteredObject> = originalRepo.create(values).onEach {
        kvCache.set(idGetter(it), it)
    }

    override suspend fun invalidate() {
        kvCache.clear()
    }
}
