package dev.inmo.micro_utils.repos.cache.cache

/**
 * This interface declares that current type of [KVCache] will contains all the data all the time of its life
 */
@Deprecated("This type of KV repos is obsolete and will be removed soon", ReplaceWith("KeyValueRepo<K, V>", "dev.inmo.micro_utils.repos.KeyValueRepo"))
interface FullKVCache<K, V> : KVCache<K, V> {
    companion object
}
