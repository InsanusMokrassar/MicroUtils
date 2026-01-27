package dev.inmo.micro_utils.repos.exposed.onetomany

import dev.inmo.micro_utils.repos.ReadKeyValuesRepo
import dev.inmo.micro_utils.repos.exposed.*
import org.jetbrains.exposed.v1.core.Column
import org.jetbrains.exposed.v1.core.Op
import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.Database

typealias ExposedReadOneToManyKeyValueRepo<Key, Value> = ExposedReadKeyValuesRepo<Key, Value>

open class ExposedReadKeyValuesRepo<Key, Value>(
    override val database: Database,
    keyColumnAllocator: ColumnAllocator<Key>,
    valueColumnAllocator: ColumnAllocator<Value>,
    tableName: String? = null
) : ReadKeyValuesRepo<Key, Value>, ExposedRepo, AbstractExposedReadKeyValuesRepo<Key, Value>(database, tableName) {
    override val keyColumn: Column<Key> = keyColumnAllocator()
    override val ResultRow.asKey: Key
        get() = get(keyColumn)
    override val selectByValue: (Value) -> Op<Boolean> = { valueColumn.eq(it) }
    override val ResultRow.asObject: Value
        get() = get(valueColumn)
    override val selectById: (Key) -> Op<Boolean> = { keyColumn.eq(it) }
    val valueColumn: Column<Value> = valueColumnAllocator()

    init { initTable() }
}
