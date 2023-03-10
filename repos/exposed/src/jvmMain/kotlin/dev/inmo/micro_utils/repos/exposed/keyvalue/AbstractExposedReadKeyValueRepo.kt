package dev.inmo.micro_utils.repos.exposed.keyvalue

import dev.inmo.micro_utils.pagination.*
import dev.inmo.micro_utils.repos.ReadKeyValueRepo
import dev.inmo.micro_utils.repos.exposed.*
import dev.inmo.micro_utils.repos.exposed.utils.selectPaginated
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

abstract class AbstractExposedReadKeyValueRepo<Key, Value>(
    override val database: Database,
    tableName: String? = null
) : ReadKeyValueRepo<Key, Value>,
    CommonExposedRepo<Key, Value>,
    Table(tableName ?: "") {
    abstract val keyColumn: Column<*>

    /**
     * Same as [asId] in context of KeyValue repo
     */
    abstract val ResultRow.asKey: Key
    override val ResultRow.asId: Key
        get() = asKey
    abstract val selectByValue: ISqlExpressionBuilder.(Value) -> Op<Boolean>

    override suspend fun get(k: Key): Value? = transaction(database) {
        select { selectById(k) }.limit(1).firstOrNull() ?.asObject
    }

    override suspend fun contains(key: Key): Boolean = transaction(database) {
        select { selectById(key) }.limit(1).any()
    }

    override suspend fun getAll(): Map<Key, Value> = transaction(database) { selectAll().associate { it.asKey to it.asObject } }

    override suspend fun count(): Long = transaction(database) { selectAll().count() }

    override suspend fun keys(pagination: Pagination, reversed: Boolean): PaginationResult<Key> = transaction(database) {
        selectAll().selectPaginated(
            pagination,
            keyColumn,
            reversed
        ) {
            it.asKey
        }
    }

    override suspend fun keys(v: Value, pagination: Pagination, reversed: Boolean): PaginationResult<Key> = transaction(database) {
        select { selectByValue(v) }.selectPaginated(
            pagination,
            keyColumn,
            reversed
        ) {
            it.asKey
        }
    }

    override suspend fun values(pagination: Pagination, reversed: Boolean): PaginationResult<Value> = transaction(database) {
        selectAll().selectPaginated(
            pagination,
            keyColumn,
            reversed
        ) {
            it.asObject
        }
    }
}
