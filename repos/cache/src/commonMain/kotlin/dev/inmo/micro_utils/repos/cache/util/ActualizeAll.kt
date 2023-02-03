package dev.inmo.micro_utils.repos.cache.util

import dev.inmo.micro_utils.pagination.FirstPagePagination
import dev.inmo.micro_utils.pagination.utils.doForAllWithNextPaging
import dev.inmo.micro_utils.pagination.utils.getAllByWithNextPaging
import dev.inmo.micro_utils.repos.ReadCRUDRepo
import dev.inmo.micro_utils.repos.ReadKeyValueRepo
import dev.inmo.micro_utils.repos.ReadKeyValuesRepo
import dev.inmo.micro_utils.repos.cache.cache.KVCache
import dev.inmo.micro_utils.repos.pagination.getAll
import dev.inmo.micro_utils.repos.set

suspend inline fun <K, V> KVCache<K, V>.actualizeAll(
    clear: Boolean = true,
    getAll: () -> Map<K, V>
) {
    set(
        getAll().also {
            if (clear) {
                clear()
            }
        }
    )
}

suspend inline fun <K, V> KVCache<K, V>.actualizeAll(
    repo: ReadKeyValueRepo<K, V>,
    clear: Boolean = true,
) {
    actualizeAll(clear) {
        repo.getAll { keys(it) }.toMap()
    }
}

suspend inline fun <K, V> KVCache<K, List<V>>.actualizeAll(
    repo: ReadKeyValuesRepo<K, V>,
    clear: Boolean = true,
) {
    actualizeAll(clear) {
        repo.getAll { keys(it) }.toMap()
    }
}

suspend inline fun <K, V> KVCache<K, V>.actualizeAll(
    repo: ReadCRUDRepo<V, K>,
    clear: Boolean = true,
) {
    actualizeAll(clear) {
        repo.getAllByWithNextPaging {
            getIdsByPagination(it)
        }.mapNotNull { it to (repo.getById(it) ?: return@mapNotNull null) }.toMap()
    }
}
