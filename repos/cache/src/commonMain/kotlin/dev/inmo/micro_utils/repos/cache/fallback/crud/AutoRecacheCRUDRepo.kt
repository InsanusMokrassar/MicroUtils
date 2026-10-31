package dev.inmo.micro_utils.repos.cache.fallback.crud

import dev.inmo.micro_utils.repos.CRUDRepo
import dev.inmo.micro_utils.repos.KeyValueRepo
import dev.inmo.micro_utils.repos.MapKeyValueRepo
import dev.inmo.micro_utils.repos.WriteCRUDRepo
import dev.inmo.micro_utils.repos.cache.fallback.ActionWrapper
import kotlinx.coroutines.CoroutineScope
import kotlin.time.Duration.Companion.seconds

open class AutoRecacheCRUDRepo<RegisteredObject, Id, InputObject>(
    originalRepo: CRUDRepo<RegisteredObject, Id, InputObject>,
    scope: CoroutineScope,
    kvCache: KeyValueRepo<Id, RegisteredObject> = MapKeyValueRepo(),
    recacheDelay: Long = 60.seconds.inWholeMilliseconds,
    actionWrapper: ActionWrapper = ActionWrapper.Direct,
    idGetter: (RegisteredObject) -> Id
) : AutoRecacheReadCRUDRepo<RegisteredObject, Id>(
    originalRepo,
    scope,
    kvCache,
    recacheDelay,
    actionWrapper,
    idGetter
),
    WriteCRUDRepo<RegisteredObject, Id, InputObject> by AutoRecacheWriteCRUDRepo(originalRepo, scope, kvCache, idGetter),
    CRUDRepo<RegisteredObject, Id, InputObject> {

    constructor(
        originalRepo: CRUDRepo<RegisteredObject, Id, InputObject>,
        scope: CoroutineScope,
        originalCallTimeoutMillis: Long,
        kvCache: KeyValueRepo<Id, RegisteredObject> = MapKeyValueRepo(),
        recacheDelay: Long = 60.seconds.inWholeMilliseconds,
        idGetter: (RegisteredObject) -> Id
    ) : this(originalRepo, scope, kvCache, recacheDelay, ActionWrapper.Timeouted(originalCallTimeoutMillis), idGetter)
}
