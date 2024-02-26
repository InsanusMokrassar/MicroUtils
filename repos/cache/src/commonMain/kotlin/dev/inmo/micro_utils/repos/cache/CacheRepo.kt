package dev.inmo.micro_utils.repos.cache

interface InvalidatableRepo {
    /**
     * Invalidates its internal data. It __may__ lead to autoreload of data. In case when repo makes autoreload,
     * it must do loading of data __before__ clear
     */
    suspend fun invalidate()
}

typealias CacheRepo = InvalidatableRepo
