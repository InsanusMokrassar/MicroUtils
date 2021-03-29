package dev.inmo.micro_utils.repos.cache

import dev.inmo.micro_utils.repos.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

open class KeyValueCacheRepo<Key,Value>(
    protected val parentRepo: KeyValueRepo<Key, Value>,
    protected val kvCache: KVCache<Key, Value>,
    scope: CoroutineScope = CoroutineScope(Dispatchers.Default)
) : KeyValueRepo<Key,Value> by parentRepo {
    protected val onNewJob = parentRepo.onNewValue.onEach { kvCache.set(it.first, it.second) }.launchIn(scope)
    protected val onRemoveJob = parentRepo.onValueRemoved.onEach { kvCache.unset(it) }.launchIn(scope)

    override suspend fun get(k: Key): Value? = kvCache.get(k) ?: parentRepo.get(k) ?.also { kvCache.set(k, it) }
    override suspend fun contains(key: Key): Boolean = kvCache.contains(key) || parentRepo.contains(key)
}
