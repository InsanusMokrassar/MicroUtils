package dev.inmo.micro_utils.repos

import dev.inmo.micro_utils.pagination.Pagination
import dev.inmo.micro_utils.pagination.PaginationResult
import kotlinx.coroutines.flow.Flow

interface ReadStandardKeyValueRepo<Key, Value> : Repo {
    suspend fun get(k: Key): Value?
    suspend fun values(pagination: Pagination, reversed: Boolean = false): PaginationResult<Value>
    suspend fun keys(pagination: Pagination, reversed: Boolean = false): PaginationResult<Key>
    suspend fun contains(key: Key): Boolean
    suspend fun count(): Long
}

interface WriteStandardKeyValueRepo<Key, Value> : Repo {
    val onNewValue: Flow<Pair<Key, Value>>
    val onValueRemoved: Flow<Key>

    suspend fun set(toSet: Map<Key, Value>)
    suspend fun unset(toUnset: List<Key>)
}

suspend inline fun <Key, Value> WriteStandardKeyValueRepo<Key, Value>.set(
    vararg toSet: Pair<Key, Value>
) = set(toSet.toMap())

suspend inline fun <Key, Value> WriteStandardKeyValueRepo<Key, Value>.set(
    k: Key, v: Value
) = set(k to v)

suspend inline fun <Key, Value> WriteStandardKeyValueRepo<Key, Value>.unset(
    vararg k: Key
) = unset(k.toList())

interface StandardKeyValueRepo<Key, Value> : ReadStandardKeyValueRepo<Key, Value>, WriteStandardKeyValueRepo<Key, Value>