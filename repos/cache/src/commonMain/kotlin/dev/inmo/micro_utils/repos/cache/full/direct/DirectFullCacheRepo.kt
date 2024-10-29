package dev.inmo.micro_utils.repos.cache.full.direct

import dev.inmo.micro_utils.repos.cache.full.FullCacheRepo

/**
 * Repos-inheritors MUST realize their methods via next logic:
 *
 * * Reloading of data in cache must be reactive (e.g. via Flow) or direct mutation methods usage (override set and
 * mutate cache inside, for example)
 * * All reading methods must take data from cache via synchronization with [dev.inmo.micro_utils.coroutines.SmartRWLocker]
 */
interface DirectFullCacheRepo : FullCacheRepo {
}