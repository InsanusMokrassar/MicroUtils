package dev.inmo.micro_utils.repos.cache.fallback.keyvalues

import dev.inmo.micro_utils.repos.KeyValuesRepo
import dev.inmo.micro_utils.repos.WriteKeyValuesRepo
import dev.inmo.micro_utils.repos.cache.cache.FullKVCache
import dev.inmo.micro_utils.repos.cache.fallback.ActionWrapper
import kotlinx.coroutines.CoroutineScope
import kotlin.time.Duration.Companion.seconds

open class AutoRecacheKeyValuesRepo<Id, RegisteredObject>(
    override val originalRepo: KeyValuesRepo<Id, RegisteredObject>,
    scope: CoroutineScope,
    kvCache: FullKVCache<Id, List<RegisteredObject>> = FullKVCache(),
    recacheDelay: Long = 60.seconds.inWholeMilliseconds,
    actionWrapper: ActionWrapper = ActionWrapper.Direct,
    idGetter: (RegisteredObject) -> Id
) : AutoRecacheReadKeyValuesRepo<Id, RegisteredObject> (
    originalRepo,
    scope,
    kvCache,
    recacheDelay,
    actionWrapper,
    idGetter
),
    WriteKeyValuesRepo<Id, RegisteredObject> by AutoRecacheWriteKeyValuesRepo(originalRepo, scope, kvCache),
    KeyValuesRepo<Id, RegisteredObject> {

    constructor(
        originalRepo: KeyValuesRepo<Id, RegisteredObject>,
        scope: CoroutineScope,
        originalCallTimeoutMillis: Long,
        kvCache: FullKVCache<Id, List<RegisteredObject>> = FullKVCache(),
        recacheDelay: Long = 60.seconds.inWholeMilliseconds,
        idGetter: (RegisteredObject) -> Id
    ) : this(originalRepo, scope, kvCache, recacheDelay, ActionWrapper.Timeouted(originalCallTimeoutMillis), idGetter)

    override suspend fun clearWithValue(v: RegisteredObject) {
        super.clearWithValue(v)
    }
}
