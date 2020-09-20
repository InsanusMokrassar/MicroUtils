package dev.inmo.micro_utils.repos

import dev.inmo.micro_utils.pagination.Pagination
import dev.inmo.micro_utils.pagination.PaginationResult

interface StandardReadKeyValueRepo<Key, Value> : Repo {
    suspend fun get(k: Key): Value?
    suspend fun values(pagination: Pagination, reversed: Boolean): PaginationResult<Value>
    suspend fun keys(pagination: Pagination, reversed: Boolean): PaginationResult<Key>
    suspend fun contains(key: Key): Boolean
    suspend fun count(): Long
}

interface StandardWriteKeyValueRepo<Key, Value> : Repo {
    val onNewValue: Flow<Pair<Key, Value>>
    val onValueRemoved: Flow<Key>

    suspend fun set(k: Key, v: Value)
    suspend fun unset(k: Key)
}

interface StandardKeyValueRepo<Key, Value> : StandardReadKeyValueRepo<Key, Value>,
    StandardWriteKeyValueRepo<Key, Value>