package dev.inmo.micro_utils.repos.cache

/**
 * Any inheritor of this should work with next logic: try to take data from original repo and,
 * if not exists, try to take from the cache some. In case if original repo have returned result, it should be saved to the internal
 * [dev.inmo.micro_utils.repos.cache.cache.KVCache]
 */
interface FallbackCacheRepo : CacheRepo
