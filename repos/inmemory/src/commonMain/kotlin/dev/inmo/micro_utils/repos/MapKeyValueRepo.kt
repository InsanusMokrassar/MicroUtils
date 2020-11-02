package dev.inmo.micro_utils.repos

import dev.inmo.micro_utils.coroutines.BroadcastFlow
import dev.inmo.micro_utils.pagination.Pagination
import dev.inmo.micro_utils.pagination.PaginationResult
import dev.inmo.micro_utils.pagination.utils.paginate
import dev.inmo.micro_utils.pagination.utils.reverse
import kotlinx.coroutines.flow.Flow

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

    override suspend fun contains(key: Key): Boolean = map.containsKey(key)

    override suspend fun count(): Long = map.size.toLong()
}

class WriteMapKeyValueRepo<Key, Value>(
    private val map: MutableMap<Key, Value> = mutableMapOf()
) : WriteStandardKeyValueRepo<Key, Value> {
    private val _onNewValue: BroadcastFlow<Pair<Key, Value>> = BroadcastFlow()
    override val onNewValue: Flow<Pair<Key, Value>>
        get() = _onNewValue
    private val _onValueRemoved: BroadcastFlow<Key> = BroadcastFlow()
    override val onValueRemoved: Flow<Key>
        get() = _onValueRemoved

    override suspend fun set(k: Key, v: Value) {
        map[k] = v
        _onNewValue.send(k to v)
    }

    override suspend fun unset(k: Key) {
        map.remove(k) ?.also { _onValueRemoved.send(k) }
    }
}

class MapKeyValueRepo<Key, Value>(
    private val map: MutableMap<Key, Value> = mutableMapOf()
) : StandardKeyValueRepo<Key, Value>,
    ReadStandardKeyValueRepo<Key, Value> by ReadMapKeyValueRepo(map),
    WriteStandardKeyValueRepo<Key, Value> by WriteMapKeyValueRepo(map)

fun <K, V> MutableMap<K, V>.asKeyValueRepo(): StandardKeyValueRepo<K, V> = MapKeyValueRepo(this)
