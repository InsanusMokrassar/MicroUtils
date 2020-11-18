package dev.inmo.micro_utils.repos

import dev.inmo.micro_utils.pagination.*
import kotlinx.coroutines.flow.Flow

interface ReadOneToManyKeyValueRepo<Key, Value> : Repo {
    suspend fun get(k: Key, pagination: Pagination, reversed: Boolean = false): PaginationResult<Value>
    suspend fun keys(pagination: Pagination, reversed: Boolean = false): PaginationResult<Key>
    suspend fun keys(v: Value, pagination: Pagination, reversed: Boolean = false): PaginationResult<Key>
    suspend fun contains(k: Key): Boolean
    suspend fun contains(k: Key, v: Value): Boolean
    suspend fun count(k: Key): Long
    suspend fun count(): Long

    suspend fun getAll(k: Key, reversed: Boolean = false): List<Value> = mutableListOf<Value>().also { list ->
        doWithPagination {
            get(k, it).also {
                list.addAll(it.results)
            }.nextPageIfNotEmpty()
        }
        if (reversed) {
            list.reverse()
        }
    }

    /**
     * WARNING!!! THIS METHOD PROBABLY IS NOT EFFICIENT, USE WITH CAUTION
     */
    suspend fun getAll(reverseLists: Boolean = false): Map<Key, List<Value>> = mutableMapOf<Key, List<Value>>().also { map ->
        doWithPagination {
            keys(it).also { paginationResult ->
                paginationResult.results.forEach { k ->
                    map[k] = getAll(k, reverseLists)
                }
            }.nextPageIfNotEmpty()
        }
    }
}

interface WriteOneToManyKeyValueRepo<Key, Value> : Repo {
    val onNewValue: Flow<Pair<Key, Value>>
    val onValueRemoved: Flow<Pair<Key, Value>>
    val onDataCleared: Flow<Key>

    suspend fun add(toAdd: Map<Key, List<Value>>)

    suspend fun remove(toRemove: Map<Key, List<Value>>)

    suspend fun clear(k: Key)

    suspend fun set(toSet: Map<Key, List<Value>>) {
        toSet.keys.forEach { key -> clear(key) }
        add(toSet)
    }
}

suspend inline fun <Key, Value, REPO : WriteOneToManyKeyValueRepo<Key, Value>> REPO.add(
    keysAndValues: List<Pair<Key, List<Value>>>
) = add(keysAndValues.toMap())

suspend inline fun <Key, Value, REPO : WriteOneToManyKeyValueRepo<Key, Value>> REPO.add(
    vararg keysAndValues: Pair<Key, List<Value>>
) = add(keysAndValues.toMap())

suspend inline fun <Key, Value> WriteOneToManyKeyValueRepo<Key, Value>.add(
    k: Key, v: List<Value>
) = add(mapOf(k to v))

suspend inline fun <Key, Value> WriteOneToManyKeyValueRepo<Key, Value>.add(
    k: Key, vararg v: Value
) = add(k, v.toList())

suspend inline fun <Key, Value, REPO : WriteOneToManyKeyValueRepo<Key, Value>> REPO.set(
    keysAndValues: List<Pair<Key, List<Value>>>
) = set(keysAndValues.toMap())

suspend inline fun <Key, Value, REPO : WriteOneToManyKeyValueRepo<Key, Value>> REPO.set(
    vararg keysAndValues: Pair<Key, List<Value>>
) = set(keysAndValues.toMap())

suspend inline fun <Key, Value> WriteOneToManyKeyValueRepo<Key, Value>.set(
    k: Key, v: List<Value>
) = set(mapOf(k to v))

suspend inline fun <Key, Value> WriteOneToManyKeyValueRepo<Key, Value>.set(
    k: Key, vararg v: Value
) = set(k, v.toList())

interface OneToManyKeyValueRepo<Key, Value> : ReadOneToManyKeyValueRepo<Key, Value>, WriteOneToManyKeyValueRepo<Key, Value>

suspend inline fun <Key, Value> WriteOneToManyKeyValueRepo<Key, Value>.remove(
    keysAndValues: List<Pair<Key, List<Value>>>
) = remove(keysAndValues.toMap())

suspend inline fun <Key, Value> WriteOneToManyKeyValueRepo<Key, Value>.remove(
    vararg keysAndValues: Pair<Key, List<Value>>
) = remove(keysAndValues.toMap())

suspend inline fun <Key, Value> WriteOneToManyKeyValueRepo<Key, Value>.remove(
    k: Key,
    v: List<Value>
) = remove(mapOf(k to v))

suspend inline fun <Key, Value> WriteOneToManyKeyValueRepo<Key, Value>.remove(
    k: Key,
    vararg v: Value
) = remove(k, v.toList())
