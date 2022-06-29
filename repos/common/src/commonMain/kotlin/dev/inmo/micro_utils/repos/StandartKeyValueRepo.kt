package dev.inmo.micro_utils.repos

import dev.inmo.micro_utils.pagination.*
import dev.inmo.micro_utils.pagination.utils.doAllWithCurrentPaging
import kotlinx.coroutines.flow.Flow

interface ReadKeyValueRepo<Key, Value> : Repo {
    suspend fun get(k: Key): Value?
    suspend fun values(pagination: Pagination, reversed: Boolean = false): PaginationResult<Value>
    suspend fun keys(pagination: Pagination, reversed: Boolean = false): PaginationResult<Key>
    suspend fun keys(v: Value, pagination: Pagination, reversed: Boolean = false): PaginationResult<Key>
    suspend fun contains(key: Key): Boolean
    suspend fun count(): Long
}
typealias ReadStandardKeyValueRepo<Key,Value> = ReadKeyValueRepo<Key, Value>

interface WriteKeyValueRepo<Key, Value> : Repo {
    val onNewValue: Flow<Pair<Key, Value>>
    val onValueRemoved: Flow<Key>

    suspend fun set(toSet: Map<Key, Value>)
    suspend fun unset(toUnset: List<Key>)
    suspend fun unsetWithValues(toUnset: List<Value>)
}
typealias WriteStandardKeyValueRepo<Key,Value> = WriteKeyValueRepo<Key, Value>

suspend inline fun <Key, Value> WriteKeyValueRepo<Key, Value>.set(
    vararg toSet: Pair<Key, Value>
) = set(toSet.toMap())

suspend inline fun <Key, Value> WriteKeyValueRepo<Key, Value>.set(
    k: Key, v: Value
) = set(k to v)

suspend inline fun <Key, Value> WriteKeyValueRepo<Key, Value>.unset(
    vararg k: Key
) = unset(k.toList())

suspend inline fun <Key, Value> WriteKeyValueRepo<Key, Value>.unsetWithValues(
    vararg v: Value
) = unsetWithValues(v.toList())

interface KeyValueRepo<Key, Value> : ReadKeyValueRepo<Key, Value>, WriteKeyValueRepo<Key, Value> {
    override suspend fun unsetWithValues(toUnset: List<Value>) = toUnset.forEach { v ->
        doAllWithCurrentPaging {
            keys(v, it).also {
                unset(it.results)
            }
        }
    }

    suspend fun clear() {
        doAllWithCurrentPaging { keys(it).also { unset(it.results) } }
    }
}
typealias StandardKeyValueRepo<Key,Value> = KeyValueRepo<Key, Value>

class DelegateBasedKeyValueRepo<Key, Value>(
    readDelegate: ReadKeyValueRepo<Key, Value>,
    writeDelegate: WriteKeyValueRepo<Key, Value>
) : KeyValueRepo<Key, Value>,
    ReadKeyValueRepo<Key, Value> by readDelegate,
    WriteKeyValueRepo<Key, Value> by writeDelegate
