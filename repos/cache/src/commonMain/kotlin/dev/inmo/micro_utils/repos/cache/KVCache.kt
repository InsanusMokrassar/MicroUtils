package dev.inmo.micro_utils.repos.cache

import dev.inmo.micro_utils.repos.*
import dev.inmo.micro_utils.repos.pagination.getAllWithNextPaging
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

interface KVCache<K, V> : KeyValueRepo<K, V>

open class SimpleKVCache<K, V>(
    protected val cachedValuesCount: Int,
    private val kvParent: KeyValueRepo<K, V> = MapKeyValueRepo<K, V>()
) : KVCache<K, V>, KeyValueRepo<K, V> by kvParent {
    protected open val cacheStack = ArrayList<K>(cachedValuesCount)
    protected val syncMutex = Mutex()

    protected suspend fun makeUnset(toUnset: List<K>) {
        cacheStack.removeAll(toUnset)
        kvParent.unset(toUnset)
    }

    override suspend fun set(toSet: Map<K, V>) {
        syncMutex.withLock {
            if (toSet.size > cachedValuesCount) {
                cacheStack.clear()

                kvParent.unset(getAllWithNextPaging { kvParent.keys(it) })
                val keysToInclude = toSet.keys.drop(toSet.size - cachedValuesCount)

                cacheStack.addAll(keysToInclude)
                kvParent.set(keysToInclude.associateWith { toSet.getValue(it) })
            } else {
                makeUnset(cacheStack.take(toSet.size))
            }
        }
    }

    override suspend fun unset(toUnset: List<K>) {
        syncMutex.withLock { makeUnset(toUnset) }
    }
}
