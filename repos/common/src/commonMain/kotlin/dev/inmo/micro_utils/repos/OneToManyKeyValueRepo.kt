package dev.inmo.micro_utils.repos

import dev.inmo.micro_utils.pagination.Pagination
import dev.inmo.micro_utils.pagination.PaginationResult
import kotlinx.coroutines.flow.Flow
import kotlin.js.JsExport

@JsExport
interface ReadOneToManyKeyValueRepo<Key, Value> : Repo {
    suspend fun get(k: Key, pagination: Pagination, reversed: Boolean = false): PaginationResult<Value>
    suspend fun keys(pagination: Pagination, reversed: Boolean = false): PaginationResult<Key>
    suspend fun contains(k: Key): Boolean
    suspend fun contains(k: Key, v: Value): Boolean
    suspend fun count(k: Key): Long
    suspend fun count(): Long
}
@Deprecated("Renamed", ReplaceWith("ReadOneToManyKeyValueRepo", "dev.inmo.micro_utils.repos.ReadOneToManyKeyValueRepo"))
typealias OneToManyReadKeyValueRepo<Key, Value> = ReadOneToManyKeyValueRepo<Key, Value>

@JsExport
interface WriteOneToManyKeyValueRepo<Key, Value> : Repo {
    val onNewValue: Flow<Pair<Key, Value>>
    val onValueRemoved: Flow<Pair<Key, Value>>
    val onDataCleared: Flow<Key>

    suspend fun add(k: Key, v: Value)
    suspend fun remove(k: Key, v: Value)
    suspend fun clear(k: Key)
}
@Deprecated("Renamed", ReplaceWith("WriteOneToManyKeyValueRepo", "dev.inmo.micro_utils.repos.WriteOneToManyKeyValueRepo"))
typealias OneToManyWriteKeyValueRepo<Key, Value> = WriteOneToManyKeyValueRepo<Key, Value>

@JsExport
interface OneToManyKeyValueRepo<Key, Value> : ReadOneToManyKeyValueRepo<Key, Value>, WriteOneToManyKeyValueRepo<Key, Value>