package dev.inmo.micro_utils.repos.cache

/**
 * Any inheritor of this should work with next logic: try to take data from their original repo, if successful - save data to internal
 * [dev.inmo.micro_utils.repos.cache.cache.FullKVCache] or try to take data from that internal cache
 */
interface FallbackCacheRepo : CacheRepo
