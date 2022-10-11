package dev.inmo.micro_utils.repos.exposed.onetomany

import dev.inmo.micro_utils.repos.ReadKeyValuesRepo
import dev.inmo.micro_utils.repos.exposed.*
import org.jetbrains.exposed.sql.*

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
    override val selectByValue: SqlExpressionBuilder.(Value) -> Op<Boolean> = { valueColumn.eq(it) }
    override val ResultRow.asObject: Value
        get() = get(valueColumn)
    override val selectById: SqlExpressionBuilder.(Key) -> Op<Boolean> = { keyColumn.eq(it) }
    val valueColumn: Column<Value> = valueColumnAllocator()

    init { initTable() }
}
