package dev.inmo.micro_utils.repos.cache.fallback.keyvalues

import dev.inmo.micro_utils.repos.KeyValueRepo
import dev.inmo.micro_utils.repos.KeyValuesRepo
import dev.inmo.micro_utils.repos.MapKeyValueRepo
import dev.inmo.micro_utils.repos.WriteKeyValuesRepo
import dev.inmo.micro_utils.repos.cache.fallback.ActionWrapper
import kotlinx.coroutines.CoroutineScope
import kotlin.time.Duration.Companion.seconds

open class AutoRecacheKeyValuesRepo<Id, RegisteredObject>(
    protected val kvsRepo: KeyValuesRepo<Id, RegisteredObject>,
    scope: CoroutineScope,
    kvCache: KeyValueRepo<Id, List<RegisteredObject>> = MapKeyValueRepo(),
    recacheDelay: Long = 60.seconds.inWholeMilliseconds,
    actionWrapper: ActionWrapper = ActionWrapper.Direct
) : AutoRecacheReadKeyValuesRepo<Id, RegisteredObject> (
    kvsRepo,
    scope,
    kvCache,
    recacheDelay,
    actionWrapper
),
    WriteKeyValuesRepo<Id, RegisteredObject> by AutoRecacheWriteKeyValuesRepo(kvsRepo, scope, kvCache),
    KeyValuesRepo<Id, RegisteredObject> {

    constructor(
        originalRepo: KeyValuesRepo<Id, RegisteredObject>,
        scope: CoroutineScope,
        originalCallTimeoutMillis: Long,
        kvCache: KeyValueRepo<Id, List<RegisteredObject>> = MapKeyValueRepo(),
        recacheDelay: Long = 60.seconds.inWholeMilliseconds
    ) : this(originalRepo, scope, kvCache, recacheDelay, ActionWrapper.Timeouted(originalCallTimeoutMillis))

    override suspend fun clearWithValue(v: RegisteredObject) {
        super.clearWithValue(v)
    }
}
