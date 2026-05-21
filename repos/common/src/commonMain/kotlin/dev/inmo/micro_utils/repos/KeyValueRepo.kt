package dev.inmo.micro_utils.repos

import dev.inmo.micro_utils.pagination.*
import dev.inmo.micro_utils.pagination.utils.doAllWithCurrentPaging
import dev.inmo.micro_utils.pagination.utils.getAllByWithNextPaging
import dev.inmo.micro_utils.pagination.utils.getAllWithNextPaging
import dev.inmo.micro_utils.pagination.utils.paginate
import dev.inmo.micro_utils.repos.pagination.maxPagePagination
import kotlinx.coroutines.flow.Flow

/**
 * Read part of [KeyValueRepo]
 *
 * @param Key This type will be used as key in all operations related to searches of data
 * @param Value This type will be used as returning data in most "get" operations
 */
interface ReadKeyValueRepo<Key, Value> : Repo {
    /**
     * @return Result [Value] in case when it is presented in repo by its [k] or null otherwise
     */
    suspend fun get(k: Key): Value?

    /**
     * This method should use sorted by [Key]s search and return the [PaginationResult]. By default, it should use
     * ascending sort for [Key]s
     */
    suspend fun values(pagination: Pagination, reversed: Boolean = false): PaginationResult<Value>

    /**
     * This method should use sorted by [Key]s search and return the [PaginationResult]. By default, it should use
     * ascending sort for [Key]s
     */
    suspend fun keys(pagination: Pagination, reversed: Boolean = false): PaginationResult<Key>

    /**
     * This method should use sorted by [Key]s search and return the [PaginationResult]. By default, it should use
     * ascending sort for [Key]s.
     *
     * **DEFAULT REALIZATION IS NOT OPTIMAL AND HAS BEEN ADDED TO COVER CASES OF DIFFERENT COMMON MAPPINGS AND TRANSFORMATIONS**
     *
     * @param v This value should be used to exclude from search the items with different [Value]s
     */
    suspend fun keys(v: Value, pagination: Pagination, reversed: Boolean = false): PaginationResult<Key> {
        return getAllWithNextPaging {
            keys(it)
        }.filter {
            get(it) == v
        }.paginate(pagination, reversed)
    }

    /**
     * @return true if [key] is presented in current collection or false otherwise
     */
    suspend fun contains(key: Key): Boolean

    /**
     * Returns all key-value pairs in the repository as a [Map].
     * Default implementation iterates all pages using [keys] and [get].
     *
     * @return Map of all [Key] to [Value] entries in the repository
     */
    suspend fun getAll(): Map<Key, Value> = getAllByWithNextPaging(maxPagePagination()) {
        keys(it).let {
            it.changeResultsUnchecked(
                it.results.mapNotNull {
                    it to (get(it) ?: return@mapNotNull null)
                }
            )
        }
    }.toMap()

    /**
     * @return count of all collection objects
     */
    suspend fun count(): Long
}
typealias ReadStandardKeyValueRepo<Key,Value> = ReadKeyValueRepo<Key, Value>

/**
 * Write part of [KeyValueRepo]
 *
 * @param Key This type will be used as key in all operations related to changes of data
 * @param Value This type will be used as incoming data in most operations
 */
interface WriteKeyValueRepo<Key, Value> : Repo {
    /**
     * This flow must emit data each time when data by [Key] has been changed with [set] method or in any other way
     * excluding cases of data removing
     *
     * @see onValueRemoved
     */
    val onNewValue: Flow<Pair<Key, Value>>

    /**
     * This flow must emit data each time when data by [Key] has been removed with [unset]/[unsetWithValues] methods or
     * in any other way
     *
     * @see onNewValue
     */
    val onValueRemoved: Flow<Key>

    /**
     * Will set as batch [toSet] data in current repo. Must pass the data which were successfully updated in repo to
     * [onNewValue]
     */
    suspend fun set(toSet: Map<Key, Value>)
    /**
     * Will unset as batch data with keys from [toUnset]. Must pass the [Key]s which were successfully removed in repo to
     * [onValueRemoved]
     */
    suspend fun unset(toUnset: List<Key>)
    /**
     * Will unset as batch data with values from [toUnset]. Must pass the [Key]s which were successfully removed in repo
     * to [onValueRemoved]
     */
    suspend fun unsetWithValues(toUnset: List<Value>)
}
typealias WriteStandardKeyValueRepo<Key,Value> = WriteKeyValueRepo<Key, Value>

/**
 * Vararg overload of [WriteKeyValueRepo.set] accepting pairs.
 *
 * @param toSet Key-value pairs to set
 */
suspend inline fun <Key, Value> WriteKeyValueRepo<Key, Value>.set(
    vararg toSet: Pair<Key, Value>
) = set(toSet.toMap())

/**
 * List overload of [WriteKeyValueRepo.set] accepting a list of pairs.
 *
 * @param toSet List of key-value pairs to set
 */
suspend inline fun <Key, Value> WriteKeyValueRepo<Key, Value>.set(
    toSet: List<Pair<Key, Value>>
) = set(toSet.toMap())

/**
 * Single-entry overload of [WriteKeyValueRepo.set].
 *
 * @param k Key to set
 * @param v Value to associate with [k]
 */
suspend inline fun <Key, Value> WriteKeyValueRepo<Key, Value>.set(
    k: Key, v: Value
) = set(k to v)

/**
 * Vararg overload of [WriteKeyValueRepo.unset].
 *
 * @param k Keys to remove
 */
suspend inline fun <Key, Value> WriteKeyValueRepo<Key, Value>.unset(
    vararg k: Key
) = unset(k.toList())

/**
 * Vararg overload of [WriteKeyValueRepo.unsetWithValues].
 *
 * @param v Values whose associated keys should be removed
 */
suspend inline fun <Key, Value> WriteKeyValueRepo<Key, Value>.unsetWithValues(
    vararg v: Value
) = unsetWithValues(v.toList())

/**
 * Full version of standard key-value repository with all set/unset/clear/get methods
 */
interface KeyValueRepo<Key, Value> : ReadKeyValueRepo<Key, Value>, WriteKeyValueRepo<Key, Value> {
    /**
     * By default, will walk throw all the [keys] with [Value]s from [toUnset] and run [doAllWithCurrentPaging] with
     * [unset] of found data [Key]s
     */
    override suspend fun unsetWithValues(toUnset: List<Value>) = toUnset.forEach { v ->
        doAllWithCurrentPaging {
            keys(v, it).also {
                unset(it.results)
            }
        }
    }

    /**
     * By default, will remove all the data of current repo using [doAllWithCurrentPaging], [keys] and [unset]
     */
    suspend fun clear() {
        var count: Int
        do {
            count = count().takeIf { it < Int.MAX_VALUE } ?.toInt() ?: Int.MAX_VALUE
            keys(FirstPagePagination(count)).also { unset(it.results) }
        } while(count > 0)
    }
}
typealias StandardKeyValueRepo<Key,Value> = KeyValueRepo<Key, Value>

/**
 * Delegate-based implementation of [KeyValueRepo] that composes separate read and write delegates.
 *
 * @param Key The type of keys in the repository
 * @param Value The type of values in the repository
 * @param readDelegate Delegate providing all [ReadKeyValueRepo] operations
 * @param writeDelegate Delegate providing all [WriteKeyValueRepo] operations
 */
class DelegateBasedKeyValueRepo<Key, Value>(
    readDelegate: ReadKeyValueRepo<Key, Value>,
    writeDelegate: WriteKeyValueRepo<Key, Value>
) : KeyValueRepo<Key, Value>,
    ReadKeyValueRepo<Key, Value> by readDelegate,
    WriteKeyValueRepo<Key, Value> by writeDelegate
