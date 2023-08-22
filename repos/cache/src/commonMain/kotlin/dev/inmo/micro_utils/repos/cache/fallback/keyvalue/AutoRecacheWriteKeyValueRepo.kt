package dev.inmo.micro_utils.repos.cache.fallback.keyvalue

import dev.inmo.micro_utils.coroutines.subscribeSafelyWithoutExceptions
import dev.inmo.micro_utils.repos.*
import dev.inmo.micro_utils.repos.cache.FallbackCacheRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

open class AutoRecacheWriteKeyValueRepo<Id, RegisteredObject>(
    protected val originalRepo: WriteKeyValueRepo<Id, RegisteredObject>,
    protected val scope: CoroutineScope,
    protected val kvCache: KeyValueRepo<Id, RegisteredObject> = MapKeyValueRepo()
) : WriteKeyValueRepo<Id, RegisteredObject>, FallbackCacheRepo {
    override val onValueRemoved: Flow<Id>
        get() = (originalRepo.onValueRemoved).distinctUntilChanged()

    override val onNewValue: Flow<Pair<Id, RegisteredObject>>
        get() = (originalRepo.onNewValue).distinctUntilChanged()

    private val onRemovingUpdatesListeningJob = originalRepo.onValueRemoved.subscribeSafelyWithoutExceptions(scope) {
        kvCache.unset(it)
    }

    private val onNewAndUpdatedObjectsListeningJob = originalRepo.onNewValue.subscribeSafelyWithoutExceptions(scope) {
        kvCache.set(it.first, it.second)
    }

    override suspend fun unsetWithValues(toUnset: List<RegisteredObject>) = originalRepo.unsetWithValues(
        toUnset
    ).also {
        kvCache.unsetWithValues(toUnset)
    }

    override suspend fun unset(toUnset: List<Id>) = originalRepo.unset(
        toUnset
    ).also {
        kvCache.unset(toUnset)
    }

    override suspend fun set(toSet: Map<Id, RegisteredObject>) = originalRepo.set(
        toSet
    ).also {
        kvCache.set(toSet)
    }

    override suspend fun invalidate() {
        kvCache.clear()
    }
}
