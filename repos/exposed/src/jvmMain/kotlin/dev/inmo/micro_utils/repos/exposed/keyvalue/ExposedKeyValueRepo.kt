package dev.inmo.micro_utils.repos.exposed.keyvalue

import dev.inmo.micro_utils.repos.StandardKeyValueRepo
import dev.inmo.micro_utils.repos.exposed.*
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
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
    private val onNewValueChannel = BroadcastChannel<Pair<Key, Value>>(Channel.BUFFERED)
    private val onValueRemovedChannel = BroadcastChannel<Key>(Channel.BUFFERED)

    override val onNewValue: Flow<Pair<Key, Value>> = onNewValueChannel.asFlow()
    override val onValueRemoved: Flow<Key> = onValueRemovedChannel.asFlow()

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
        onNewValueChannel.send(k to v)
    }

    override suspend fun unset(k: Key) {
        transaction(database) {
            deleteWhere { keyColumn.eq(k) }
        }
        onValueRemovedChannel.send(k)
    }
}

@Deprecated("Renamed", ReplaceWith("ExposedKeyValueRepo", "dev.inmo.micro_utils.repos.exposed.keyvalue.ExposedKeyValueRepo"))
typealias AbstractExposedKeyValueRepo<Key, Value> = ExposedKeyValueRepo<Key, Value>
