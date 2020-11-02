package dev.inmo.micro_utils.repos.exposed.onetomany

import dev.inmo.micro_utils.coroutines.BroadcastFlow
import dev.inmo.micro_utils.repos.OneToManyKeyValueRepo
import dev.inmo.micro_utils.repos.exposed.ColumnAllocator
import kotlinx.coroutines.flow.Flow
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

open class ExposedOneToManyKeyValueRepo<Key, Value>(
    database: Database,
    keyColumnAllocator: ColumnAllocator<Key>,
    valueColumnAllocator: ColumnAllocator<Value>,
    tableName: String? = null
) : OneToManyKeyValueRepo<Key, Value>, ExposedReadOneToManyKeyValueRepo<Key, Value>(
    database,
    keyColumnAllocator,
    valueColumnAllocator,
    tableName
) {
    protected val _onNewValue: BroadcastFlow<Pair<Key, Value>> = BroadcastFlow()
    override val onNewValue: Flow<Pair<Key, Value>>
        get() = _onNewValue
    protected val _onValueRemoved: BroadcastFlow<Pair<Key, Value>> = BroadcastFlow()
    override val onValueRemoved: Flow<Pair<Key, Value>>
        get() = _onValueRemoved
    protected val _onDataCleared: BroadcastFlow<Key> = BroadcastFlow()
    override val onDataCleared: Flow<Key>
        get() = _onDataCleared

    override suspend fun add(k: Key, v: Value) {
        transaction(database) {
            insert {
                it[keyColumn] = k
                it[valueColumn] = v
            }
        }.also { _onNewValue.send(k to v) }
    }

    override suspend fun remove(k: Key, v: Value) {
        transaction(database) {
            deleteWhere { keyColumn.eq(k).and(valueColumn.eq(v)) }
        }.also { _onValueRemoved.send(k to v) }
    }

    override suspend fun clear(k: Key) {
        transaction(database) {
            deleteWhere { keyColumn.eq(k) }
        }.also { _onDataCleared.send(k) }
    }
}

@Deprecated("Renamed", ReplaceWith("ExposedOneToManyKeyValueRepo", "dev.inmo.micro_utils.repos.exposed.onetomany.ExposedOneToManyKeyValueRepo"))
typealias AbstractOneToManyExposedKeyValueRepo<Key, Value> = ExposedOneToManyKeyValueRepo<Key, Value>

@Deprecated("Renamed", ReplaceWith("ExposedOneToManyKeyValueRepo", "dev.inmo.micro_utils.repos.exposed.onetomany.ExposedOneToManyKeyValueRepo"))
typealias AbstractExposedOneToManyKeyValueRepo<Key, Value> = ExposedOneToManyKeyValueRepo<Key, Value>
