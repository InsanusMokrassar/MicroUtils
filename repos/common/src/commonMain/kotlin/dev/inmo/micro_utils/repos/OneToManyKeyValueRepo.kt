package dev.inmo.micro_utils.repos

import dev.inmo.micro_utils.pagination.Pagination
import dev.inmo.micro_utils.pagination.PaginationResult
import kotlinx.coroutines.flow.Flow

interface OneToManyReadKeyValueRepo<Key, Value> : Repo {
    suspend fun get(k: Key, pagination: Pagination, reversed: Boolean = false): PaginationResult<Value>
    suspend fun keys(pagination: Pagination, reversed: Boolean = false): PaginationResult<Key>
    suspend fun contains(k: Key): Boolean
    suspend fun contains(k: Key, v: Value): Boolean
    suspend fun count(k: Key): Long
    suspend fun count(): Long
}

interface OneToManyWriteKeyValueRepo<Key, Value> : Repo {
    val onNewValue: Flow<Pair<Key, Value>>
    val onValueRemoved: Flow<Pair<Key, Value>>
    val onDataCleared: Flow<Key>

    suspend fun add(k: Key, v: Value)
    suspend fun remove(k: Key, v: Value)
    suspend fun clear(k: Key)
}

interface OneToManyKeyValueRepo<Key, Value> : OneToManyReadKeyValueRepo<Key, Value>, OneToManyWriteKeyValueRepo<Key, Value>