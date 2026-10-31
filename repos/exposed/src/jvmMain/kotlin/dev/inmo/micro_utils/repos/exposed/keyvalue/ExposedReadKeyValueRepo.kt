package dev.inmo.micro_utils.repos.exposed.keyvalue

import dev.inmo.micro_utils.repos.ReadKeyValueRepo
import dev.inmo.micro_utils.repos.exposed.*
import org.jetbrains.exposed.v1.core.Column
import org.jetbrains.exposed.v1.core.Op
import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.Database

open class ExposedReadKeyValueRepo<Key, Value>(
    database: Database,
    keyColumnAllocator: ColumnAllocator<Key>,
    valueColumnAllocator: ColumnAllocator<Value>,
    tableName: String? = null
) : ReadKeyValueRepo<Key, Value>, ExposedRepo, AbstractExposedReadKeyValueRepo<Key, Value>(database, tableName) {

    override val keyColumn: Column<Key> = keyColumnAllocator()
    val valueColumn: Column<Value> = valueColumnAllocator()
    override val ResultRow.asKey: Key
        get() = get(keyColumn)
    override val selectByValue: (Value) -> Op<Boolean> = { valueColumn.eq(it) }
    override val ResultRow.asObject: Value
        get() = get(valueColumn)
    override val selectById: (Key) -> Op<Boolean> = { keyColumn.eq(it) }
    override val primaryKey: Table.PrimaryKey
        get() = PrimaryKey(keyColumn, valueColumn)

    init { initTable() }
}
