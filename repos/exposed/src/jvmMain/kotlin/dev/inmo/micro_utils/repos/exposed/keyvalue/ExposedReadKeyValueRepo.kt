package dev.inmo.micro_utils.repos.exposed.keyvalue

import dev.inmo.micro_utils.pagination.*
import dev.inmo.micro_utils.repos.ReadKeyValueRepo
import dev.inmo.micro_utils.repos.exposed.*
import dev.inmo.micro_utils.repos.exposed.utils.selectPaginated
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.jetbrains.exposed.sql.transactions.transaction

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
    override val selectByValue: ISqlExpressionBuilder.(Value) -> Op<Boolean> = { valueColumn.eq(it) }
    override val ResultRow.asObject: Value
        get() = get(valueColumn)
    override val selectById: ISqlExpressionBuilder.(Key) -> Op<Boolean> = { keyColumn.eq(it) }
    override val primaryKey: Table.PrimaryKey
        get() = PrimaryKey(keyColumn, valueColumn)

    init { initTable() }
}
