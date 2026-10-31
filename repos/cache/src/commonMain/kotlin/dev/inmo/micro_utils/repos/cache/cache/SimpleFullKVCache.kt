package dev.inmo.micro_utils.repos.cache.cache

import dev.inmo.micro_utils.repos.KeyValueRepo
import dev.inmo.micro_utils.repos.MapKeyValueRepo
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

@Deprecated("This type of KV repos is obsolete and will be removed soon", ReplaceWith("MapKeyValueRepo<K, V>()", "dev.inmo.micro_utils.repos.MapKeyValueRepo"))

class SimpleFullKVCache<K, V>(
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

    override suspend fun clear() {
        syncMutex.withLock {
            kvParent.clear()
        }
    }
}

@Deprecated("This type of KV repos is obsolete and will be removed soon", ReplaceWith("kvParent", "dev.inmo.micro_utils.repos.MapKeyValueRepo"))
inline fun <K, V> FullKVCache(
    kvParent: KeyValueRepo<K, V> = MapKeyValueRepo<K, V>()
) = SimpleFullKVCache<K, V>(kvParent)
