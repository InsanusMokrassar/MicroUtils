package dev.inmo.micro_utils.repos

import dev.inmo.micro_utils.coroutines.SmartRWLocker
import dev.inmo.micro_utils.coroutines.withReadAcquire
import dev.inmo.micro_utils.coroutines.withWriteLock
import dev.inmo.micro_utils.pagination.*
import dev.inmo.micro_utils.pagination.utils.paginate
import dev.inmo.micro_utils.pagination.utils.reverse
import kotlinx.coroutines.flow.*

/**
 * [Map]-based [ReadKeyValuesRepo]. All internal operations will be locked with [locker] (mostly with
 * [SmartRWLocker.withReadAcquire])
 *
 * **Warning**: It is not recommended to use constructor with both [Map] and [SmartRWLocker]. Besides, in case
 * you are using your own [Map] as a [map] you should be careful with operations on this [map]
 */
class MapReadKeyValuesRepo<Key, Value>(
    private val map: Map<Key, List<Value>> = emptyMap(),
    private val locker: SmartRWLocker = SmartRWLocker()
) : ReadKeyValuesRepo<Key, Value> {
    override suspend fun get(k: Key, pagination: Pagination, reversed: Boolean): PaginationResult<Value> {
        val list = locker.withReadAcquire {
            map[k] ?: return emptyPaginationResult()
        }

        return list.paginate(
            if (reversed) {
                pagination.reverse(list.size)
            } else {
                pagination
            }
        )
    }

    override suspend fun keys(pagination: Pagination, reversed: Boolean): PaginationResult<Key> {
        val keys = locker.withReadAcquire {
            map.keys
        }
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
        val keys = locker.withReadAcquire {
            map.keys.filter { map[it] ?.contains(v) == true }
        }
        val actualPagination = if (reversed) pagination.reverse(keys.size) else pagination
        return keys.paginate(actualPagination).let {
            if (reversed) {
                it.copy(results = it.results.reversed())
            } else {
                it
            }
        }
    }

    override suspend fun contains(k: Key): Boolean = locker.withReadAcquire { map.containsKey(k) }

    override suspend fun contains(k: Key, v: Value): Boolean = locker.withReadAcquire { map[k] ?.contains(v) } == true

    override suspend fun count(k: Key): Long = locker.withReadAcquire { map[k] ?.size } ?.toLong() ?: 0L

    override suspend fun count(): Long = locker.withReadAcquire { map.size }.toLong()
}

/**
 * [MutableMap]-based [WriteKeyValuesRepo]. All internal operations will be locked with [locker] (mostly with
 * [SmartRWLocker.withWriteLock])
 *
 * **Warning**: It is not recommended to use constructor with both [MutableMap] and [SmartRWLocker]. Besides, in case
 * you are using your own [MutableMap] as a [map] you should be careful with operations on this [map]
 */
class MapWriteKeyValuesRepo<Key, Value>(
    private val map: MutableMap<Key, MutableList<Value>> = mutableMapOf(),
    private val locker: SmartRWLocker = SmartRWLocker()
) : WriteKeyValuesRepo<Key, Value> {
    private val _onNewValue: MutableSharedFlow<Pair<Key, Value>> = MapsReposDefaultMutableSharedFlow()
    override val onNewValue: Flow<Pair<Key, Value>> by lazy {
        _onNewValue.asSharedFlow()
    }
    private val _onValueRemoved: MutableSharedFlow<Pair<Key, Value>> = MapsReposDefaultMutableSharedFlow()
    override val onValueRemoved: Flow<Pair<Key, Value>> by lazy {
        _onValueRemoved.asSharedFlow()
    }
    private val _onDataCleared: MutableSharedFlow<Key> = MapsReposDefaultMutableSharedFlow()
    override val onDataCleared: Flow<Key> by lazy {
        _onDataCleared.asSharedFlow()
    }

    override suspend fun add(toAdd: Map<Key, List<Value>>) {
        locker.withWriteLock {
            toAdd.keys.mapNotNull { k ->
                (k to toAdd[k]).takeIf {
                    map.getOrPut(k) { mutableListOf() }.addAll(toAdd[k] ?: return@mapNotNull null)
                }
            }
        }.forEach { (k, vs) ->
            vs ?.forEach { v ->
                _onNewValue.emit(k to v)
            }
        }
    }

    override suspend fun set(toSet: Map<Key, List<Value>>) {
        locker.withWriteLock {
            toSet.forEach {
                map[it.key] = it.value.toMutableList()
            }
        }
        toSet.forEach { (k, v) ->
            v.forEach {
                _onNewValue.emit(k to it)
            }
        }
    }

    override suspend fun remove(toRemove: Map<Key, List<Value>>) {
        val removed = mutableListOf<Pair<Key, Value>>()
        val cleared = mutableListOf<Key>()
        locker.withWriteLock {
            toRemove.keys.forEach { k ->
                if (map[k]?.removeAll(toRemove[k] ?: return@forEach) == true) {
                    toRemove[k]?.forEach { v ->
                        removed.add(k to v)
                    }
                }
                if (map[k]?.isEmpty() == true) {
                    map.remove(k)
                    cleared.add(k)
                }
            }
        }
        removed.forEach {
            _onValueRemoved.emit(it)
        }
        cleared.forEach {
            _onDataCleared.emit(it)
        }
    }

    override suspend fun removeWithValue(v: Value) {
        locker.withWriteLock {
            map.mapNotNull { (k, values) ->
                if (values.remove(v)) {
                    k to v
                } else {
                    null
                }
            }
        }.forEach {
            _onValueRemoved.emit(it)
        }
    }

    override suspend fun clear(k: Key) {
        locker.withWriteLock {
            map.remove(k)
        } ?.also { _onDataCleared.emit(k) }
    }

    override suspend fun clearWithValue(v: Value) {
        locker.withWriteLock {
            map.filter { (_, values) ->
                values.contains(v)
            }.mapNotNull {
                if (map.remove(it.key) != null) {
                    it.toPair()
                } else {
                    null
                }
            }
        }.forEach {
            it.second.onEach { v ->
                _onValueRemoved.emit(it.first to v)
            }.also { _ ->
                _onDataCleared.emit(it.first)
            }
        }
    }
}

/**
 * [MutableMap]-based [KeyValuesRepo]. All internal operations will be locked with [locker]
 *
 * **Warning**: It is not recommended to use constructor with both [MutableMap] and [SmartRWLocker]. Besides, in case
 * you are using your own [MutableMap] as a [map] you should be careful with operations on this [map]
 */
@Suppress("DELEGATED_MEMBER_HIDES_SUPERTYPE_OVERRIDE")
class MapKeyValuesRepo<Key, Value>(
    private val map: MutableMap<Key, MutableList<Value>> = mutableMapOf(),
    private val locker: SmartRWLocker = SmartRWLocker()
) : KeyValuesRepo<Key, Value>,
    ReadKeyValuesRepo<Key, Value> by MapReadKeyValuesRepo(map, locker),
    WriteKeyValuesRepo<Key, Value> by MapWriteKeyValuesRepo(map, locker)

/**
 * [MutableMap]-based [KeyValuesRepo]. All internal operations will be locked with [locker]
 *
 * **Warning**: In case you are using your own [MutableMap] as [this] receiver you should be careful with operations on [this] map
 */
fun <K, V> MutableMap<K, List<V>>.asKeyValuesRepo(locker: SmartRWLocker = SmartRWLocker()): KeyValuesRepo<K, V> = MapKeyValuesRepo(
    map { (k, v) -> k to v.toMutableList() }.toMap().toMutableMap(),
    locker
)
