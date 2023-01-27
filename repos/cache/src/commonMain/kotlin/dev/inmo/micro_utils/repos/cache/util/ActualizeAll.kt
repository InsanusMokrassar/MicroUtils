package dev.inmo.micro_utils.repos.cache.util

import dev.inmo.micro_utils.pagination.FirstPagePagination
import dev.inmo.micro_utils.pagination.utils.doForAllWithNextPaging
import dev.inmo.micro_utils.repos.ReadCRUDRepo
import dev.inmo.micro_utils.repos.ReadKeyValueRepo
import dev.inmo.micro_utils.repos.ReadKeyValuesRepo
import dev.inmo.micro_utils.repos.cache.cache.KVCache
import dev.inmo.micro_utils.repos.set

suspend inline fun <K, V> KVCache<K, V>.actualizeAll(
    getAll: () -> Map<K, V>
) {
    clear()
    set(getAll())
}

suspend inline fun <K, V> KVCache<K, V>.actualizeAll(
    repo: ReadKeyValueRepo<K, V>
) {
    clear()
    val count = repo.count().takeIf { it < Int.MAX_VALUE } ?.toInt() ?: Int.MAX_VALUE
    val initPagination = FirstPagePagination(count)
    doForAllWithNextPaging(initPagination) {
        keys(it).also {
            set(
                it.results.mapNotNull { k -> repo.get(k) ?.let { k to it } }
            )
        }
    }
}

suspend inline fun <K, V> KVCache<K, List<V>>.actualizeAll(
    repo: ReadKeyValuesRepo<K, V>
) {
    clear()
    val count = repo.count().takeIf { it < Int.MAX_VALUE } ?.toInt() ?: Int.MAX_VALUE
    val initPagination = FirstPagePagination(count)
    doForAllWithNextPaging(initPagination) {
        keys(it).also {
            set(
                it.results.associateWith { k -> repo.getAll(k) }
            )
        }
    }
}

suspend inline fun <K, V> KVCache<K, V>.actualizeAll(
    repo: ReadCRUDRepo<V, K>
) {
    clear()
    val count = repo.count().takeIf { it < Int.MAX_VALUE } ?.toInt() ?: Int.MAX_VALUE
    val initPagination = FirstPagePagination(count)
    doForAllWithNextPaging(initPagination) {
        keys(it).also {
            set(
                it.results.mapNotNull { k -> repo.getById(k) ?.let { k to it } }
            )
        }
    }
}
