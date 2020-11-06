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

    @Deprecated("Realize set with map instead")
    suspend fun set(k: Key, v: Value)
    suspend fun set(toSet: Map<Key, Value>) = toSet.forEach { (k, v) ->
        set(k, v)
    }
    @Deprecated("Realize unset with list instead")
    suspend fun unset(k: Key)
    suspend fun unset(toUnset: List<Key>) = toUnset.forEach {
        unset(it)
    }
}

interface StandardKeyValueRepo<Key, Value> : ReadStandardKeyValueRepo<Key, Value>, WriteStandardKeyValueRepo<Key, Value>