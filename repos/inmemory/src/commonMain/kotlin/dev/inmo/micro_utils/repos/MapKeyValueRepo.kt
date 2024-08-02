package dev.inmo.micro_utils.repos

import dev.inmo.micro_utils.coroutines.SmartRWLocker
import dev.inmo.micro_utils.coroutines.withReadAcquire
import dev.inmo.micro_utils.coroutines.withWriteLock
import dev.inmo.micro_utils.pagination.Pagination
import dev.inmo.micro_utils.pagination.PaginationResult
import dev.inmo.micro_utils.pagination.utils.paginate
import dev.inmo.micro_utils.pagination.utils.reverse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

/**
 * [Map]-based [ReadKeyValueRepo]. All internal operations will be locked with [locker] (mostly with
 * [SmartRWLocker.withReadAcquire])
 *
 * **Warning**: It is not recommended to use constructor with both [Map] and [SmartRWLocker]. Besides, in case you are
 * using your own [Map] as a [map] you should be careful with operations on this [map]
 */
class ReadMapKeyValueRepo<Key, Value>(
    protected val map: Map<Key, Value>,
    private val locker: SmartRWLocker
) : ReadKeyValueRepo<Key, Value> {
    constructor(map: Map<Key, Value> = emptyMap()) : this(map, SmartRWLocker())

    override suspend fun get(k: Key): Value? = locker.withReadAcquire {
        map[k]
    }

    override suspend fun values(
        pagination: Pagination,
        reversed: Boolean
    ): PaginationResult<Value> {
        return locker.withReadAcquire {
            val values = map.values
            val actualPagination = if (reversed) pagination.reverse(values.size) else pagination
            values.paginate(actualPagination).let {
                if (reversed) {
                    it.copy(results = it.results.reversed())
                } else {
                    it
                }
            }
        }
    }

    override suspend fun keys(pagination: Pagination, reversed: Boolean): PaginationResult<Key> {
        return locker.withReadAcquire {
            val keys = map.keys
            val actualPagination = if (reversed) pagination.reverse(keys.size) else pagination
            keys.paginate(actualPagination).let {
                if (reversed) {
                    it.copy(results = it.results.reversed())
                } else {
                    it
                }
            }
        }
    }

    override suspend fun keys(v: Value, pagination: Pagination, reversed: Boolean): PaginationResult<Key> {
        return locker.withReadAcquire {
            val keys: List<Key> = map.mapNotNull { (k, value) -> if (v == value) k else null }
            val actualPagination = if (reversed) pagination.reverse(keys.size) else pagination
            keys.paginate(actualPagination).let {
                if (reversed) {
                    it.copy(results = it.results.reversed())
                } else {
                    it
                }
            }
        }
    }

    override suspend fun getAll(): Map<Key, Value> = locker.withReadAcquire { map.toMap() }

    override suspend fun contains(key: Key): Boolean = locker.withReadAcquire { map.containsKey(key) }

    override suspend fun count(): Long = locker.withReadAcquire { map.size.toLong() }
}

/**
 * [MutableMap]-based [WriteKeyValueRepo]. All internal operations will be locked with [locker] (mostly with
 * [SmartRWLocker.withWriteLock])
 *
 * **Warning**: It is not recommended to use constructor with both [MutableMap] and [SmartRWLocker]. Besides, in case
 * you are using your own [MutableMap] as a [map] you should be careful with operations on this [map]
 */
class WriteMapKeyValueRepo<Key, Value>(
    private val map: MutableMap<Key, Value>,
    private val locker: SmartRWLocker
) : WriteKeyValueRepo<Key, Value> {
    private val _onNewValue: MutableSharedFlow<Pair<Key, Value>> = MapsReposDefaultMutableSharedFlow()
    override val onNewValue: Flow<Pair<Key, Value>> by lazy {
        _onNewValue.asSharedFlow()
    }
    private val _onValueRemoved: MutableSharedFlow<Key> = MapsReposDefaultMutableSharedFlow()
    override val onValueRemoved: Flow<Key> by lazy {
        _onValueRemoved.asSharedFlow()
    }
    constructor(map: MutableMap<Key, Value> = mutableMapOf()) : this(map, SmartRWLocker())

    override suspend fun set(toSet: Map<Key, Value>) {
        locker.withWriteLock { map.putAll(toSet) }
        toSet.forEach { (k, v) -> _onNewValue.emit(k to v) }
    }

    override suspend fun unset(toUnset: List<Key>) {
        locker.withWriteLock {
            toUnset.mapNotNull { k ->
                map.remove(k) ?.let { _ -> k }
            }
        }.forEach { _onValueRemoved.emit(it) }
    }

    override suspend fun unsetWithValues(toUnset: List<Value>) {
        locker.withWriteLock {
            map.mapNotNull { (k, v) ->
                k.takeIf { v in toUnset }
            }.forEach {
                map.remove(it)
                _onValueRemoved.emit(it)
            }
        }
    }
}

/**
 * [MutableMap]-based [KeyValueRepo]. All internal operations will be locked with [locker]
 *
 * **Warning**: It is not recommended to use constructor with both [MutableMap] and [SmartRWLocker]. Besides, in case
 * you are using your own [MutableMap] as a [map] you should be careful with operations on this [map]
 */
@Suppress("DELEGATED_MEMBER_HIDES_SUPERTYPE_OVERRIDE")
class MapKeyValueRepo<Key, Value>(
    private val map: MutableMap<Key, Value>,
    private val locker: SmartRWLocker
) : KeyValueRepo<Key, Value>,
    ReadKeyValueRepo<Key, Value> by ReadMapKeyValueRepo(map, locker),
    WriteKeyValueRepo<Key, Value> by WriteMapKeyValueRepo(map, locker) {
    constructor(map: MutableMap<Key, Value> = mutableMapOf()) : this(map, SmartRWLocker())
    override suspend fun clear() {
        locker.withWriteLock { map.clear() }
    }
}

/**
 * [MutableMap]-based [KeyValueRepo]. All internal operations will be locked with [locker]
 *
 * **Warning**: It is not recommended to use constructor with both [MutableMap] and [SmartRWLocker]. Besides, in case
 * you are using your own [MutableMap] as a [this] you should be careful with operations on this [this]
 */
fun <K, V> MutableMap<K, V>.asKeyValueRepo(locker: SmartRWLocker = SmartRWLocker()): KeyValueRepo<K, V> = MapKeyValueRepo(this, locker)
