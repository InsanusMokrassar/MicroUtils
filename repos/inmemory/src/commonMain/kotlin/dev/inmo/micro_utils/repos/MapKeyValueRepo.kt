package dev.inmo.micro_utils.repos

import dev.inmo.micro_utils.pagination.Pagination
import dev.inmo.micro_utils.pagination.PaginationResult
import dev.inmo.micro_utils.pagination.utils.paginate
import dev.inmo.micro_utils.pagination.utils.reverse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class ReadMapKeyValueRepo<Key, Value>(
    private val map: Map<Key, Value> = emptyMap()
) : ReadStandardKeyValueRepo<Key, Value> {
    override suspend fun get(k: Key): Value? = map[k]

    override suspend fun values(
        pagination: Pagination,
        reversed: Boolean
    ): PaginationResult<Value> {
        val values = map.values
        val actualPagination = if (reversed) pagination.reverse(values.size) else pagination
        return values.paginate(actualPagination).let {
            if (reversed) {
                it.copy(results = it.results.reversed())
            } else {
                it
            }
        }
    }

    override suspend fun keys(pagination: Pagination, reversed: Boolean): PaginationResult<Key> {
        val keys = map.keys
        val actualPagination = if (reversed) pagination.reverse(keys.size) else pagination
        return keys.paginate(actualPagination).let {
            if (reversed) {
                it.copy(results = it.results.reversed())
            } else {
                it
            }
        }
    }

    override suspend fun keys(v: Value, pagination: Pagination, reversed: Boolean): PaginationResult<Key> {
        val keys: List<Key> = map.mapNotNull { (k, value) -> if (v == value) k else null }
        val actualPagination = if (reversed) pagination.reverse(keys.size) else pagination
        return keys.paginate(actualPagination).let {
            if (reversed) {
                it.copy(results = it.results.reversed())
            } else {
                it
            }
        }
    }

    override suspend fun contains(key: Key): Boolean = map.containsKey(key)

    override suspend fun count(): Long = map.size.toLong()
}

class WriteMapKeyValueRepo<Key, Value>(
    private val map: MutableMap<Key, Value> = mutableMapOf()
) : WriteStandardKeyValueRepo<Key, Value> {
    private val _onNewValue: MutableSharedFlow<Pair<Key, Value>> = MutableSharedFlow()
    override val onNewValue: Flow<Pair<Key, Value>>
        get() = _onNewValue
    private val _onValueRemoved: MutableSharedFlow<Key> = MutableSharedFlow()
    override val onValueRemoved: Flow<Key>
        get() = _onValueRemoved

    override suspend fun set(toSet: Map<Key, Value>) {
        map.putAll(toSet)
        toSet.forEach { (k, v) -> _onNewValue.emit(k to v) }
    }

    override suspend fun unset(toUnset: List<Key>) {
        toUnset.forEach { k ->
            map.remove(k) ?.also { _ -> _onValueRemoved.emit(k) }
        }
    }

    override suspend fun unsetWithValues(toUnset: List<Value>) {
        map.forEach {
            if (it.value in toUnset) {
                map.remove(it.key)
                _onValueRemoved.emit(it.key)
            }
        }
    }
}

class MapKeyValueRepo<Key, Value>(
    private val map: MutableMap<Key, Value> = mutableMapOf()
) : StandardKeyValueRepo<Key, Value>,
    ReadStandardKeyValueRepo<Key, Value> by ReadMapKeyValueRepo(map),
    WriteStandardKeyValueRepo<Key, Value> by WriteMapKeyValueRepo(map)

fun <K, V> MutableMap<K, V>.asKeyValueRepo(): StandardKeyValueRepo<K, V> = MapKeyValueRepo(this)
