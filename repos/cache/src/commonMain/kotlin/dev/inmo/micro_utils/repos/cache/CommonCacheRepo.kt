package dev.inmo.micro_utils.repos.cache

/**
 * Any inheritor of this should work with next logic: try to take data from some [dev.inmo.micro_utils.repos.cache.cache.KVCache] and,
 * if not exists, take from origin and save to the cache for future reuse
 */
interface CommonCacheRepo : CacheRepo
