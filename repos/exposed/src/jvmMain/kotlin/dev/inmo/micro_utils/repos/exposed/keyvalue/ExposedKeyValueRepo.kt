package dev.inmo.micro_utils.repos.exposed.keyvalue

import dev.inmo.micro_utils.repos.KeyValueRepo
import dev.inmo.micro_utils.repos.exposed.ColumnAllocator
import kotlinx.coroutines.flow.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

open class ExposedKeyValueRepo<Key, Value>(
    database: Database,
    keyColumnAllocator: ColumnAllocator<Key>,
    valueColumnAllocator: ColumnAllocator<Value>,
    tableName: String? = null
) : KeyValueRepo<Key, Value>, ExposedReadKeyValueRepo<Key, Value>(
    database,
    keyColumnAllocator,
    valueColumnAllocator,
    tableName
) {
    protected val _onNewValue = MutableSharedFlow<Pair<Key, Value>>()
    protected val _onValueRemoved = MutableSharedFlow<Key>()

    override val onNewValue: Flow<Pair<Key, Value>> = _onNewValue.asSharedFlow()
    override val onValueRemoved: Flow<Key> = _onValueRemoved.asSharedFlow()

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

    override suspend fun unsetWithValues(toUnset: List<Value>) {
        transaction(database) {
            toUnset.flatMap {
                val keys = select { valueColumn.eq(it) }.mapNotNull { it[keyColumn] }
                deleteWhere { keyColumn.inList(keys) }
                keys
            }
        }.distinct().forEach {
            _onValueRemoved.emit(it)
        }
    }
}
