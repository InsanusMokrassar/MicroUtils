package dev.inmo.micro_utils.repos

import dev.inmo.micro_utils.coroutines.BroadcastFlow
import dev.inmo.micro_utils.pagination.*
import kotlinx.coroutines.flow.Flow
import kotlin.js.JsExport

@JsExport
class ReadMapKeyValueRepo<Key, Value>(
    private val map: Map<Key, Value> = emptyMap()
) : ReadStandardKeyValueRepo<Key, Value> {
    override suspend fun get(k: Key): Value? = map[k]

    override suspend fun values(
        pagination: Pagination,
        reversed: Boolean
    ): PaginationResult<Value> {
        val firstIndex: Int = if (reversed) {
            val size = map.size
            (size - pagination.lastIndex).let { if (it < 0) 0 else it }
        } else {
            pagination.firstIndex
        }

        return map.keys.drop(firstIndex).take(pagination.size).mapNotNull { map[it] }.createPaginationResult(
            firstIndex,
            count()
        )
    }

    override suspend fun keys(pagination: Pagination, reversed: Boolean): PaginationResult<Key> {
        val firstIndex: Int = if (reversed) {
            val size = map.size
            (size - pagination.lastIndex).let { if (it < 0) 0 else it }
        } else {
            pagination.firstIndex
        }

        return map.keys.drop(firstIndex).take(pagination.size).createPaginationResult(
            firstIndex,
            count()
        )
    }

    override suspend fun contains(key: Key): Boolean = map.containsKey(key)

    override suspend fun count(): Long = map.size.toLong()
}

@JsExport
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

@JsExport
class MapKeyValueRepo<Key, Value>(
    private val map: MutableMap<Key, Value> = mutableMapOf()
) : StandardKeyValueRepo<Key, Value>,
    ReadStandardKeyValueRepo<Key, Value> by ReadMapKeyValueRepo(map),
    WriteStandardKeyValueRepo<Key, Value> by WriteMapKeyValueRepo(map)

@JsExport
fun <K, V> MutableMap<K, V>.asKeyValueRepo(): StandardKeyValueRepo<K, V> = MapKeyValueRepo(this)
