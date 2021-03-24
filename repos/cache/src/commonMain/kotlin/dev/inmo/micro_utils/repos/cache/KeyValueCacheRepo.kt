package dev.inmo.micro_utils.repos.cache

import dev.inmo.micro_utils.repos.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

open class KeyValueCacheRepo<Key,Value>(
    protected val parentRepo: KeyValueRepo<Key, Value>,
    protected val cachedValuesCount: Int,
    scope: CoroutineScope = CoroutineScope(Dispatchers.Default)
) : KeyValueRepo<Key,Value> by parentRepo {
    protected open val cache = mutableMapOf<Key,Value>()
    protected open val cacheStack = ArrayList<Key>(cachedValuesCount)
    protected val syncMutex = Mutex()
    protected val onNewJob = parentRepo.onNewValue.onEach { putCacheValue(it.first, it.second) }.launchIn(scope)
    protected val onRemoveJob = parentRepo.onValueRemoved.onEach { removeCacheValue(it) }.launchIn(scope)

    protected suspend fun putCacheValue(k: Key, v: Value) = syncMutex.withLock {
        if (cache.size >= cachedValuesCount) {
            val key = cacheStack.removeAt(0)
            cache.remove(key)
        }
        cacheStack.add(k)
        cache[k] = v
    }

    protected suspend fun removeCacheValue(k: Key) = syncMutex.withLock {
        val i = cacheStack.indexOf(k)
        if (i >= 0) {
            val key = cacheStack.removeAt(i)
            cache.remove(key)
        }
    }

    override suspend fun get(k: Key): Value? = syncMutex.withLock {
        cache[k] ?: parentRepo.get(k) ?.also { cache[k] = it }
    }
    override suspend fun contains(key: Key): Boolean = cache.containsKey(key) || parentRepo.contains(key)
}
