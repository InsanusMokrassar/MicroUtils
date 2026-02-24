package dev.inmo.micro_utils.repos

import dev.inmo.micro_utils.common.diff
import dev.inmo.micro_utils.pagination.*
import dev.inmo.micro_utils.pagination.utils.doForAllWithNextPaging
import dev.inmo.micro_utils.pagination.utils.getAllWithNextPaging
import kotlinx.coroutines.flow.Flow

/**
 * Read part of [KeyValuesRepo] for one-to-many key-value relationships.
 * This repository type allows multiple values to be associated with a single key.
 *
 * @param Key The type used as the key in all search operations
 * @param Value The type of values associated with keys
 */
interface ReadKeyValuesRepo<Key, Value> : Repo {
    /**
     * Retrieves a paginated list of values associated with the given key.
     *
     * @param k The key to search for
     * @param pagination The pagination parameters
     * @param reversed Whether to reverse the order of results
     * @return A [PaginationResult] containing values associated with the key
     */
    suspend fun get(k: Key, pagination: Pagination, reversed: Boolean = false): PaginationResult<Value>
    
    /**
     * Retrieves a paginated list of keys.
     *
     * @param pagination The pagination parameters
     * @param reversed Whether to reverse the order of results
     * @return A [PaginationResult] containing keys
     */
    suspend fun keys(pagination: Pagination, reversed: Boolean = false): PaginationResult<Key>
    
    /**
     * Retrieves keys that have the specified value associated with them.
     *
     * @param v The value to search for
     * @param pagination The pagination parameters
     * @param reversed Whether to reverse the order of results
     * @return A [PaginationResult] containing keys associated with the value
     */
    suspend fun keys(v: Value, pagination: Pagination, reversed: Boolean = false): PaginationResult<Key>
    
    /**
     * Checks if the specified key exists in the repository.
     *
     * @param k The key to check
     * @return `true` if the key exists, `false` otherwise
     */
    suspend fun contains(k: Key): Boolean
    
    /**
     * Checks if the specified key-value pair exists in the repository.
     *
     * @param k The key to check
     * @param v The value to check
     * @return `true` if the key-value pair exists, `false` otherwise
     */
    suspend fun contains(k: Key, v: Value): Boolean
    
    /**
     * Returns the count of values associated with the specified key.
     *
     * @param k The key to count values for
     * @return The number of values associated with the key
     */
    suspend fun count(k: Key): Long
    
    /**
     * Returns the total count of key-value pairs in the repository.
     *
     * @return The total number of key-value pairs
     */
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

/**
 * Type alias for [ReadKeyValuesRepo] emphasizing one-to-many relationships.
 */
typealias ReadOneToManyKeyValueRepo<Key,Value> = ReadKeyValuesRepo<Key, Value>

/**
 * Write part of [KeyValuesRepo] for one-to-many key-value relationships.
 * Provides methods for adding, removing, and clearing values associated with keys.
 *
 * @param Key The type used as the key in all write operations
 * @param Value The type of values associated with keys
 */
interface WriteKeyValuesRepo<Key, Value> : Repo {
    /**
     * Flow that emits when a new value is added to a key.
     */
    val onNewValue: Flow<Pair<Key, Value>>
    
    /**
     * Flow that emits when a value is removed from a key.
     */
    val onValueRemoved: Flow<Pair<Key, Value>>
    
    /**
     * Flow that emits when all data for a key is cleared.
     */
    val onDataCleared: Flow<Key>

    /**
     * Adds values to the specified keys.
     *
     * @param toAdd A map of keys to lists of values to add
     */
    suspend fun add(toAdd: Map<Key, List<Value>>)

    /**
     * Removes specific values from keys without clearing all data for those keys.
     *
     * @param toRemove A map of keys to lists of values to remove
     */
    suspend fun remove(toRemove: Map<Key, List<Value>>)

    /**
     * Removes a specific value from all keys that contain it, without clearing all data for those keys.
     *
     * @param v The value to remove
     */
    suspend fun removeWithValue(v: Value)

    /**
     * Fully clears all data associated with the specified key.
     *
     * @param k The key to clear
     */
    suspend fun clear(k: Key)
    
    /**
     * Clears a specific value from all keys and removes those keys if they become empty.
     *
     * @param v The value to clear
     */
    suspend fun clearWithValue(v: Value)

    /**
     * Sets the values for specified keys, clearing any existing values first.
     *
     * @param toSet A map of keys to lists of values to set
     */
    suspend fun set(toSet: Map<Key, List<Value>>) {
        toSet.keys.forEach { key -> clear(key) }
        add(toSet)
    }
}

/**
 * Type alias for [WriteKeyValuesRepo] emphasizing one-to-many relationships.
 */
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
