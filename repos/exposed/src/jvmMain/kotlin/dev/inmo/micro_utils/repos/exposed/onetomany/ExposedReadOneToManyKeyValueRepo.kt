package dev.inmo.micro_utils.repos.exposed.onetomany

import dev.inmo.micro_utils.pagination.*
import dev.inmo.micro_utils.repos.ReadOneToManyKeyValueRepo
import dev.inmo.micro_utils.repos.exposed.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

open class ExposedReadOneToManyKeyValueRepo<Key, Value>(
    override val database: Database,
    keyColumnAllocator: ColumnAllocator<Key>,
    valueColumnAllocator: ColumnAllocator<Value>
) : ReadOneToManyKeyValueRepo<Key, Value>, ExposedRepo, Table() {
    protected val keyColumn: Column<Key> = keyColumnAllocator()
    protected val valueColumn: Column<Value> = valueColumnAllocator()

    override fun onInit() { initTable() }

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

    override suspend fun contains(k: Key): Boolean = transaction(database) {
        select { keyColumn.eq(k) }.limit(1).any()
    }

    override suspend fun contains(k: Key, v: Value): Boolean = transaction(database) {
        select { keyColumn.eq(k).and(valueColumn.eq(v)) }.limit(1).any()
    }
}

@Deprecated("Renamed", ReplaceWith("ExposedReadOneToManyKeyValueRepo", "dev.inmo.micro_utils.repos.exposed.onetomany.ExposedReadOneToManyKeyValueRepo"))
typealias AbstractOneToManyExposedReadKeyValueRepo<Key, Value> = ExposedReadOneToManyKeyValueRepo<Key, Value>

@Deprecated("Renamed", ReplaceWith("ExposedReadOneToManyKeyValueRepo", "dev.inmo.micro_utils.repos.exposed.onetomany.ExposedReadOneToManyKeyValueRepo"))
typealias AbstractExposedReadOneToManyKeyValueRepo<Key, Value> = ExposedReadOneToManyKeyValueRepo<Key, Value>
