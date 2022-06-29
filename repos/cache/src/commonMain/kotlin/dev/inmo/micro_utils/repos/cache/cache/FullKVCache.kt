package dev.inmo.micro_utils.repos.cache.cache

/**
 * This interface declares that current type of [KVCache] will contains all the data all the time of its life
 */
interface FullKVCache<K, V> : KVCache<K, V> {
    companion object
}
