package dev.inmo.micro_utils.repos.cache.fallback.keyvalue

import dev.inmo.micro_utils.repos.KeyValueRepo
import dev.inmo.micro_utils.repos.MapKeyValueRepo
import dev.inmo.micro_utils.repos.WriteKeyValueRepo
import dev.inmo.micro_utils.repos.cache.fallback.ActionWrapper
import kotlinx.coroutines.CoroutineScope
import kotlin.time.Duration.Companion.seconds

open class AutoRecacheKeyValueRepo<Id, RegisteredObject>(
    protected val kvRepo: KeyValueRepo<Id, RegisteredObject>,
    scope: CoroutineScope,
    kvCache: KeyValueRepo<Id, RegisteredObject> = MapKeyValueRepo(),
    recacheDelay: Long = 60.seconds.inWholeMilliseconds,
    actionWrapper: ActionWrapper = ActionWrapper.Direct,
    idGetter: (RegisteredObject) -> Id
) : AutoRecacheReadKeyValueRepo<Id, RegisteredObject> (
    kvRepo,
    scope,
    kvCache,
    recacheDelay,
    actionWrapper,
    idGetter
),
    WriteKeyValueRepo<Id, RegisteredObject> by AutoRecacheWriteKeyValueRepo(kvRepo, scope, kvCache),
    KeyValueRepo<Id, RegisteredObject> {

    constructor(
        originalRepo: KeyValueRepo<Id, RegisteredObject>,
        scope: CoroutineScope,
        originalCallTimeoutMillis: Long,
        kvCache: KeyValueRepo<Id, RegisteredObject> = MapKeyValueRepo(),
        recacheDelay: Long = 60.seconds.inWholeMilliseconds,
        idGetter: (RegisteredObject) -> Id
    ) : this(originalRepo, scope, kvCache, recacheDelay, ActionWrapper.Timeouted(originalCallTimeoutMillis), idGetter)

    override suspend fun unsetWithValues(toUnset: List<RegisteredObject>) = kvRepo.unsetWithValues(
        toUnset
    ).also {
        kvCache.unsetWithValues(toUnset)
    }

    override suspend fun clear() {
        kvRepo.clear()
        kvCache.clear()
    }
}
