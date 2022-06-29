package dev.inmo.micro_utils.repos.cache.cache

import dev.inmo.micro_utils.pagination.utils.getAllWithNextPaging
import dev.inmo.micro_utils.repos.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

open class SimpleKVCache<K, V>(
    protected val cachedValuesCount: Int,
    private val kvParent: KeyValueRepo<K, V> = MapKeyValueRepo<K, V>()
) : KVCache<K, V>, KeyValueRepo<K, V> by kvParent {
    protected open val cacheQueue = ArrayDeque<K>(cachedValuesCount)
    protected val syncMutex = Mutex()

    protected suspend fun makeUnset(toUnset: List<K>) {
        cacheQueue.removeAll(toUnset)
        kvParent.unset(toUnset)
    }

    override suspend fun set(toSet: Map<K, V>) {
        syncMutex.withLock {
            for ((k, v) in toSet) {
                if (cacheQueue.size >= cachedValuesCount) {
                    cacheQueue.removeFirstOrNull() ?.let {
                        kvParent.unset(it)
                    }
                }
                cacheQueue.addLast(k)
                kvParent.set(k, v)
            }
        }
    }

    override suspend fun unset(toUnset: List<K>) {
        syncMutex.withLock { makeUnset(toUnset) }
    }
}

inline fun <K, V> KVCache(
    cachedValuesCount: Int,
    kvParent: KeyValueRepo<K, V> = MapKeyValueRepo<K, V>()
) = SimpleKVCache<K, V>(cachedValuesCount, kvParent)
