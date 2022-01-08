package dev.inmo.micro_utils.repos.exposed.keyvalue

import dev.inmo.micro_utils.pagination.*
import dev.inmo.micro_utils.repos.ReadStandardKeyValueRepo
import dev.inmo.micro_utils.repos.exposed.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

open class ExposedReadKeyValueRepo<Key, Value>(
    override val database: Database,
    keyColumnAllocator: ColumnAllocator<Key>,
    valueColumnAllocator: ColumnAllocator<Value>,
    tableName: String? = null
) : ReadStandardKeyValueRepo<Key, Value>, ExposedRepo, Table(tableName ?: "") {
    val keyColumn: Column<Key> = keyColumnAllocator()
    val valueColumn: Column<Value> = valueColumnAllocator()
    override val primaryKey: PrimaryKey = PrimaryKey(keyColumn, valueColumn)

    init { initTable() }

    override suspend fun get(k: Key): Value? = transaction(database) {
        select { keyColumn.eq(k) }.limit(1).firstOrNull() ?.getOrNull(valueColumn)
    }

    override suspend fun contains(key: Key): Boolean = transaction(database) {
        select { keyColumn.eq(key) }.limit(1).any()
    }

    override suspend fun count(): Long = transaction(database) { selectAll().count() }

    override suspend fun keys(pagination: Pagination, reversed: Boolean): PaginationResult<Key> = transaction(database) {
        selectAll().paginate(pagination, keyColumn to if (reversed) SortOrder.DESC else SortOrder.ASC).map {
            it[keyColumn]
        }
    }.createPaginationResult(pagination, count())

    override suspend fun keys(v: Value, pagination: Pagination, reversed: Boolean): PaginationResult<Key> = transaction(database) {
        select { valueColumn.eq(v) }.let {
            it.count() to it.paginate(pagination, keyColumn to if (reversed) SortOrder.DESC else SortOrder.ASC).map {
                it[keyColumn]
            }
        }
    }.let { (count, list) ->
        list.createPaginationResult(pagination, count)
    }

    override suspend fun values(pagination: Pagination, reversed: Boolean): PaginationResult<Value> = transaction(database) {
        selectAll().paginate(pagination, keyColumn to if (reversed) SortOrder.DESC else SortOrder.ASC).map {
            it[valueColumn]
        }
    }.createPaginationResult(pagination, count())
}
