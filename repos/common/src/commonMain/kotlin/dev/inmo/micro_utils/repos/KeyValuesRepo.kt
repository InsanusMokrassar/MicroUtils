package dev.inmo.micro_utils.repos

import dev.inmo.micro_utils.common.diff
import dev.inmo.micro_utils.pagination.*
import dev.inmo.micro_utils.pagination.utils.doForAllWithNextPaging
import dev.inmo.micro_utils.pagination.utils.getAllWithNextPaging
import kotlinx.coroutines.flow.Flow

interface ReadKeyValuesRepo<Key, Value> : Repo {
    suspend fun get(k: Key, pagination: Pagination, reversed: Boolean = false): PaginationResult<Value>
    suspend fun keys(pagination: Pagination, reversed: Boolean = false): PaginationResult<Key>
    suspend fun keys(v: Value, pagination: Pagination, reversed: Boolean = false): PaginationResult<Key>
    suspend fun contains(k: Key): Boolean
    suspend fun contains(k: Key, v: Value): Boolean
    suspend fun count(k: Key): Long
    suspend fun count(): Long

    suspend fun getAll(k: Key, reversed: Boolean = false): List<Value> {
        val results = getAllWithNextPaging { get(k, it) }
        return if (reversed) {
            results.reversed()
        } else {
            results
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
typealias ReadOneToManyKeyValueRepo<Key,Value> = ReadKeyValuesRepo<Key, Value>

interface WriteKeyValuesRepo<Key, Value> : Repo {
    val onNewValue: Flow<Pair<Key, Value>>
    val onValueRemoved: Flow<Pair<Key, Value>>
    val onDataCleared: Flow<Key>

    suspend fun add(toAdd: Map<Key, List<Value>>)

    /**
     * Removes [Value]s by passed [Key]s without full clear of all data by [Key]
     */
    suspend fun remove(toRemove: Map<Key, List<Value>>)

    /**
     * Removes [v] without full clear of all data by [Key]s with [v]
     */
    suspend fun removeWithValue(v: Value)

    /**
     * Fully clear all data by [k]
     */
    suspend fun clear(k: Key)
    /**
     * Clear [v] **with** full clear of all data by [Key]s with [v]
     */
    suspend fun clearWithValue(v: Value)

    suspend fun set(toSet: Map<Key, List<Value>>) {
        toSet.keys.forEach { key -> clear(key) }
        add(toSet)
    }
}
typealias WriteOneToManyKeyValueRepo<Key,Value> = WriteKeyValuesRepo<Key, Value>

suspend inline fun <Key, Value, REPO : WriteKeyValuesRepo<Key, Value>> REPO.add(
    keysAndValues: List<Pair<Key, List<Value>>>
) = add(keysAndValues.toMap())

suspend inline fun <Key, Value, REPO : WriteKeyValuesRepo<Key, Value>> REPO.add(
    vararg keysAndValues: Pair<Key, List<Value>>
) = add(keysAndValues.toMap())

suspend inline fun <Key, Value> WriteKeyValuesRepo<Key, Value>.add(
    k: Key, v: List<Value>
) = add(mapOf(k to v))

suspend inline fun <Key, Value> WriteKeyValuesRepo<Key, Value>.add(
    k: Key, vararg v: Value
) = add(k, v.toList())

suspend inline fun <Key, Value, REPO : WriteKeyValuesRepo<Key, Value>> REPO.set(
    keysAndValues: List<Pair<Key, List<Value>>>
) = set(keysAndValues.toMap())

suspend inline fun <Key, Value, REPO : WriteKeyValuesRepo<Key, Value>> REPO.set(
    vararg keysAndValues: Pair<Key, List<Value>>
) = set(keysAndValues.toMap())

suspend inline fun <Key, Value> WriteKeyValuesRepo<Key, Value>.set(
    k: Key, v: List<Value>
) = set(mapOf(k to v))

suspend inline fun <Key, Value> WriteKeyValuesRepo<Key, Value>.set(
    k: Key, vararg v: Value
) = set(k, v.toList())

interface KeyValuesRepo<Key, Value> : ReadKeyValuesRepo<Key, Value>, WriteKeyValuesRepo<Key, Value> {
    override suspend fun clearWithValue(v: Value) {
        doWithPagination {
            val keysResult = keys(v, it)

            if (keysResult.results.isNotEmpty()) {
                remove(keysResult.results.map { it to listOf(v) })
            }

            keysResult.currentPageIfNotEmpty()
        }
    }
    suspend override fun removeWithValue(v: Value) {
        val toRemove = mutableMapOf<Key, List<Value>>()

        doForAllWithNextPaging {
            keys(it).also {
                it.results.forEach {
                    if (contains(it, v)) {
                        toRemove[it] = listOf(v)
                    }
                }
            }
        }

        remove(toRemove)
    }

    override suspend fun set(toSet: Map<Key, List<Value>>) {
        toSet.forEach { (k, v) ->
            val diff = getAll(k).diff(v)
            remove(k, diff.removed.map { it.value })
            add(k, diff.added.map { it.value })
        }
    }
}
typealias OneToManyKeyValueRepo<Key,Value> = KeyValuesRepo<Key, Value>

class DelegateBasedKeyValuesRepo<Key, Value>(
    readDelegate: ReadKeyValuesRepo<Key, Value>,
    writeDelegate: WriteKeyValuesRepo<Key, Value>
) : KeyValuesRepo<Key, Value>,
    ReadKeyValuesRepo<Key, Value> by readDelegate,
    WriteKeyValuesRepo<Key, Value> by writeDelegate

suspend inline fun <Key, Value> WriteKeyValuesRepo<Key, Value>.remove(
    keysAndValues: List<Pair<Key, List<Value>>>
) = remove(keysAndValues.toMap())

suspend inline fun <Key, Value> WriteKeyValuesRepo<Key, Value>.remove(
    vararg keysAndValues: Pair<Key, List<Value>>
) = remove(keysAndValues.toMap())

suspend inline fun <Key, Value> WriteKeyValuesRepo<Key, Value>.remove(
    k: Key,
    v: List<Value>
) = remove(mapOf(k to v))

suspend inline fun <Key, Value> WriteKeyValuesRepo<Key, Value>.remove(
    k: Key,
    vararg v: Value
) = remove(k, v.toList())
