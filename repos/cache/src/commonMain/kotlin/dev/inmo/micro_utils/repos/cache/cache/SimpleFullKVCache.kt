package dev.inmo.micro_utils.repos.cache.cache

import dev.inmo.micro_utils.repos.KeyValueRepo
import dev.inmo.micro_utils.repos.MapKeyValueRepo
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

open class SimpleFullKVCache<K, V>(
    private val kvParent: KeyValueRepo<K, V> = MapKeyValueRepo<K, V>()
) : FullKVCache<K, V>, KeyValueRepo<K, V> by kvParent {
    protected val syncMutex = Mutex()

    override suspend fun set(toSet: Map<K, V>) {
        syncMutex.withLock {
            kvParent.set(toSet)
        }
    }

    override suspend fun unset(toUnset: List<K>) {
        syncMutex.withLock {
            kvParent.unset(toUnset)
        }
    }
}
