package dev.inmo.micro_utils.repos

import dev.inmo.micro_utils.pagination.Pagination
import dev.inmo.micro_utils.pagination.PaginationResult
import kotlinx.coroutines.flow.Flow
import kotlin.js.JsExport

@JsExport
interface ReadStandardKeyValueRepo<Key, Value> : Repo {
    suspend fun get(k: Key): Value?
    suspend fun values(pagination: Pagination, reversed: Boolean = false): PaginationResult<Value>
    suspend fun keys(pagination: Pagination, reversed: Boolean = false): PaginationResult<Key>
    suspend fun contains(key: Key): Boolean
    suspend fun count(): Long
}

@JsExport
interface WriteStandardKeyValueRepo<Key, Value> : Repo {
    val onNewValue: Flow<Pair<Key, Value>>
    val onValueRemoved: Flow<Key>

    suspend fun set(k: Key, v: Value)
    suspend fun unset(k: Key)
}

@JsExport
interface StandardKeyValueRepo<Key, Value> : ReadStandardKeyValueRepo<Key, Value>, WriteStandardKeyValueRepo<Key, Value>