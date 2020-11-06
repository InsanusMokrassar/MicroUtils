package dev.inmo.micro_utils.repos.exposed.keyvalue

import dev.inmo.micro_utils.repos.StandardKeyValueRepo
import dev.inmo.micro_utils.repos.exposed.ColumnAllocator
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

open class ExposedKeyValueRepo<Key, Value>(
    database: Database,
    keyColumnAllocator: ColumnAllocator<Key>,
    valueColumnAllocator: ColumnAllocator<Value>,
    tableName: String? = null
) : StandardKeyValueRepo<Key, Value>, ExposedReadKeyValueRepo<Key, Value>(
    database,
    keyColumnAllocator,
    valueColumnAllocator,
    tableName
) {
    private val _onNewValue = MutableSharedFlow<Pair<Key, Value>>(Channel.BUFFERED)
    private val _onValueRemoved = MutableSharedFlow<Key>(Channel.BUFFERED)

    override val onNewValue: Flow<Pair<Key, Value>> = _onNewValue.asSharedFlow()
    override val onValueRemoved: Flow<Key> = _onValueRemoved.asSharedFlow()

    override suspend fun set(k: Key, v: Value) {
        transaction(database) {
            if (select { keyColumn.eq(k) }.limit(1).any()) {
                update({ keyColumn.eq(k) }) {
                    it[valueColumn] = v
                }
            } else {
                insert {
                    it[keyColumn] = k
                    it[valueColumn] = v
                }
            }
        }
        _onNewValue.emit(k to v)
    }

    override suspend fun set(toSet: Map<Key, Value>) {
        transaction(database) {
            toSet.mapNotNull { (k, v) ->
                if (update({ keyColumn.eq(k) }) { it[valueColumn] = v } > 0) {
                    k to v
                } else {
                    val inserted = insert {
                        it[keyColumn] = k
                        it[valueColumn] = v
                    }.getOrNull(keyColumn) != null
                    if (inserted) {
                        k to v
                    } else {
                        null
                    }
                }
            }
        }.forEach {
            _onNewValue.emit(it)
        }
    }

    override suspend fun unset(k: Key) {
        transaction(database) {
            deleteWhere { keyColumn.eq(k) }
        }
        _onValueRemoved.emit(k)
    }

    override suspend fun unset(toUnset: List<Key>) {
        transaction(database) {
            toUnset.mapNotNull {
                if (deleteWhere { keyColumn.eq(it) } > 0) {
                    it
                } else {
                    null
                }
            }
        }.forEach {
            _onValueRemoved.emit(it)
        }
    }
}

@Deprecated("Renamed", ReplaceWith("ExposedKeyValueRepo", "dev.inmo.micro_utils.repos.exposed.keyvalue.ExposedKeyValueRepo"))
typealias AbstractExposedKeyValueRepo<Key, Value> = ExposedKeyValueRepo<Key, Value>
