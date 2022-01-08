package dev.inmo.micro_utils.repos.exposed.onetomany

import dev.inmo.micro_utils.pagination.*
import dev.inmo.micro_utils.repos.ReadOneToManyKeyValueRepo
import dev.inmo.micro_utils.repos.exposed.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

open class ExposedReadOneToManyKeyValueRepo<Key, Value>(
    override val database: Database,
    keyColumnAllocator: ColumnAllocator<Key>,
    valueColumnAllocator: ColumnAllocator<Value>,
    tableName: String? = null
) : ReadOneToManyKeyValueRepo<Key, Value>, ExposedRepo, Table(tableName ?: "") {
    val keyColumn: Column<Key> = keyColumnAllocator()
    val valueColumn: Column<Value> = valueColumnAllocator()

    init { initTable() }

    override suspend fun count(k: Key): Long = transaction(database) { select { keyColumn.eq(k) }.count() }

    override suspend fun count(): Long = transaction(database) { selectAll().count() }

    override suspend fun get(
        k: Key,
        pagination: Pagination,
        reversed: Boolean
    ): PaginationResult<Value> = transaction(database) {
        select { keyColumn.eq(k) }.paginate(pagination, keyColumn, reversed).map { it[valueColumn] }
    }.createPaginationResult(
        pagination,
        count(k)
    )

    override suspend fun keys(pagination: Pagination, reversed: Boolean): PaginationResult<Key> = transaction(database) {
        selectAll().paginate(pagination, keyColumn, reversed).map { it[keyColumn] }
    }.createPaginationResult(
        pagination,
        count()
    )

    override suspend fun keys(
        v: Value,
        pagination: Pagination,
        reversed: Boolean
    ): PaginationResult<Key> = transaction(database) {
        select { valueColumn.eq(v) }.let {
            it.count() to it.paginate(pagination, keyColumn, reversed).map { it[keyColumn] }
        }
    }.let { (count, list) ->
        list.createPaginationResult(
            pagination,
            count
        )
    }

    override suspend fun contains(k: Key): Boolean = transaction(database) {
        select { keyColumn.eq(k) }.limit(1).any()
    }

    override suspend fun contains(k: Key, v: Value): Boolean = transaction(database) {
        select { keyColumn.eq(k).and(valueColumn.eq(v)) }.limit(1).any()
    }
}
