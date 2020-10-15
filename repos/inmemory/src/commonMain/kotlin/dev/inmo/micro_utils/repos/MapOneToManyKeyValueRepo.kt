package dev.inmo.micro_utils.repos

import dev.inmo.micro_utils.coroutines.BroadcastFlow
import dev.inmo.micro_utils.pagination.*
import dev.inmo.micro_utils.pagination.utils.paginate
import kotlinx.coroutines.flow.Flow
import kotlin.js.JsExport

class MapReadOneToManyKeyValueRepo<Key, Value>(
    private val map: Map<Key, List<Value>> = emptyMap()
) : ReadOneToManyKeyValueRepo<Key, Value> {
    override suspend fun get(k: Key, pagination: Pagination, reversed: Boolean): PaginationResult<Value> {
        val list = map[k] ?: return emptyPaginationResult()

        return list.paginate(
            if (reversed) {
                val firstIndex = (map.size - pagination.lastIndex).let { if (it < 0) 0 else it }
                SimplePagination(firstIndex, pagination.size)
            } else {
                pagination
            }
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

    override suspend fun contains(k: Key): Boolean = map.containsKey(k)

    override suspend fun contains(k: Key, v: Value): Boolean = map[k] ?.contains(v) == true

    override suspend fun count(k: Key): Long = map[k] ?.size ?.toLong() ?: 0L

    override suspend fun count(): Long = map.size.toLong()
}

class MapWriteOneToManyKeyValueRepo<Key, Value>(
    private val map: MutableMap<Key, MutableList<Value>> = mutableMapOf()
) : WriteOneToManyKeyValueRepo<Key, Value> {
    private val _onNewValue: BroadcastFlow<Pair<Key, Value>> = BroadcastFlow()
    override val onNewValue: Flow<Pair<Key, Value>>
        get() = _onNewValue
    private val _onValueRemoved: BroadcastFlow<Pair<Key, Value>> = BroadcastFlow()
    override val onValueRemoved: Flow<Pair<Key, Value>>
        get() = _onValueRemoved
    private val _onDataCleared: BroadcastFlow<Key> = BroadcastFlow()
    override val onDataCleared: Flow<Key>
        get() = _onDataCleared

    override suspend fun add(k: Key, v: Value) {
        map.getOrPut(k) { mutableListOf() }.add(v)
        _onNewValue.send(k to v)
    }

    override suspend fun remove(k: Key, v: Value) {
        map[k] ?.remove(v) ?.also { _onValueRemoved.send(k to v) }
    }

    override suspend fun clear(k: Key) {
        map.remove(k) ?.also { _onDataCleared.send(k) }
    }
}

@JsExport
class MapOneToManyKeyValueRepo<Key, Value>(
    private val map: MutableMap<Key, MutableList<Value>> = mutableMapOf()
) : OneToManyKeyValueRepo<Key, Value>,
    ReadOneToManyKeyValueRepo<Key, Value> by MapReadOneToManyKeyValueRepo(map),
    WriteOneToManyKeyValueRepo<Key, Value> by MapWriteOneToManyKeyValueRepo(map)

@JsExport
fun <K, V> MutableMap<K, List<V>>.asOneToManyKeyValueRepo(): OneToManyKeyValueRepo<K, V> = MapOneToManyKeyValueRepo(
    map { (k, v) -> k to v.toMutableList() }.toMap().toMutableMap()
)
