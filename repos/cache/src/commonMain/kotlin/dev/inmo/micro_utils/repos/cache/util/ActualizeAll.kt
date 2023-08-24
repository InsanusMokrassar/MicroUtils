package dev.inmo.micro_utils.repos.cache.util

import dev.inmo.micro_utils.repos.*

suspend inline fun <K, V> KeyValueRepo<K, V>.actualizeAll(
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

suspend inline fun <K, V> KeyValueRepo<K, V>.actualizeAll(
    repo: ReadKeyValueRepo<K, V>,
    clear: Boolean = true,
) {
    actualizeAll(clear) {
        repo.getAll()
    }
}

suspend inline fun <K, V> KeyValueRepo<K, List<V>>.actualizeAll(
    repo: ReadKeyValuesRepo<K, V>,
    clear: Boolean = true,
) {
    actualizeAll(clear) {
        repo.getAll()
    }
}

suspend inline fun <K, V> KeyValueRepo<K, V>.actualizeAll(
    repo: ReadCRUDRepo<V, K>,
    clear: Boolean = true,
) {
    actualizeAll(clear) {
        repo.getAll()
    }
}
