package dev.inmo.micro_utils.repos

import dev.inmo.micro_utils.coroutines.BroadcastFlow
import dev.inmo.micro_utils.pagination.*
import dev.inmo.micro_utils.pagination.utils.paginate
import dev.inmo.micro_utils.pagination.utils.reverse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class MapReadOneToManyKeyValueRepo<Key, Value>(
    private val map: Map<Key, List<Value>> = emptyMap()
) : ReadOneToManyKeyValueRepo<Key, Value> {
    override suspend fun get(k: Key, pagination: Pagination, reversed: Boolean): PaginationResult<Value> {
        val list = map[k] ?: return emptyPaginationResult()

        return list.paginate(
            if (reversed) {
                pagination.reverse(list.size)
            } else {
                pagination
            }
        )
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

    override suspend fun contains(k: Key): Boolean = map.containsKey(k)

    override suspend fun contains(k: Key, v: Value): Boolean = map[k] ?.contains(v) == true

    override suspend fun count(k: Key): Long = map[k] ?.size ?.toLong() ?: 0L

    override suspend fun count(): Long = map.size.toLong()
}

class MapWriteOneToManyKeyValueRepo<Key, Value>(
    private val map: MutableMap<Key, MutableList<Value>> = mutableMapOf()
) : WriteOneToManyKeyValueRepo<Key, Value> {
    private val _onNewValue: MutableSharedFlow<Pair<Key, Value>> = MutableSharedFlow()
    override val onNewValue: Flow<Pair<Key, Value>>
        get() = _onNewValue
    private val _onValueRemoved: MutableSharedFlow<Pair<Key, Value>> = MutableSharedFlow()
    override val onValueRemoved: Flow<Pair<Key, Value>>
        get() = _onValueRemoved
    private val _onDataCleared: MutableSharedFlow<Key> = MutableSharedFlow()
    override val onDataCleared: Flow<Key>
        get() = _onDataCleared

    override suspend fun add(toAdd: Map<Key, List<Value>>) {
        toAdd.keys.forEach { k ->
            if (map.getOrPut(k) { mutableListOf() }.addAll(toAdd[k] ?: return@forEach)) {
                toAdd[k] ?.forEach { v ->
                    _onNewValue.emit(k to v)
                }
            }
        }
    }

    override suspend fun remove(toRemove: Map<Key, List<Value>>) {
        toRemove.keys.forEach { k ->
            if (map[k] ?.removeAll(toRemove[k] ?: return@forEach) == true) {
                toRemove[k] ?.forEach { v ->
                    _onValueRemoved.emit(k to v)
                }
            }
        }
    }

    override suspend fun clear(k: Key) {
        map.remove(k) ?.also { _onDataCleared.emit(k) }
    }
}

class MapOneToManyKeyValueRepo<Key, Value>(
    private val map: MutableMap<Key, MutableList<Value>> = mutableMapOf()
) : OneToManyKeyValueRepo<Key, Value>,
    ReadOneToManyKeyValueRepo<Key, Value> by MapReadOneToManyKeyValueRepo(map),
    WriteOneToManyKeyValueRepo<Key, Value> by MapWriteOneToManyKeyValueRepo(map)

fun <K, V> MutableMap<K, List<V>>.asOneToManyKeyValueRepo(): OneToManyKeyValueRepo<K, V> = MapOneToManyKeyValueRepo(
    map { (k, v) -> k to v.toMutableList() }.toMap().toMutableMap()
)
