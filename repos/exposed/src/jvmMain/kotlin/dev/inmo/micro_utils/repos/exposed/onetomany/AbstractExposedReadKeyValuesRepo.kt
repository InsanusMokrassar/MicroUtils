package dev.inmo.micro_utils.repos.exposed.onetomany

import dev.inmo.micro_utils.pagination.*
import dev.inmo.micro_utils.repos.ReadKeyValuesRepo
import dev.inmo.micro_utils.repos.exposed.*
import dev.inmo.micro_utils.repos.exposed.utils.selectPaginated
import org.jetbrains.exposed.v1.core.Column
import org.jetbrains.exposed.v1.core.Op
import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.core.SortOrder
import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.core.and
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

abstract class AbstractExposedReadKeyValuesRepo<Key, Value>(
    override val database: Database,
    tableName: String? = null
) : ReadKeyValuesRepo<Key, Value>,
    CommonExposedRepo<Key, Value>,
    Table(tableName ?: "") {
    abstract val keyColumn: Column<*>
    abstract val ResultRow.asKey: Key
    override val ResultRow.asId: Key
        get() = asKey
    abstract val selectByValue: (Value) -> Op<Boolean>

    override suspend fun count(k: Key): Long = transaction(database) { selectAll().where { selectById(k) }.count() }

    override suspend fun count(): Long = transaction(database) { selectAll().count() }

    override suspend fun get(
        k: Key,
        pagination: Pagination,
        reversed: Boolean
    ): PaginationResult<Value> = transaction(database) {
        selectAll().where { selectById(k) }.selectPaginated(
            pagination,
            keyColumn,
            reversed
        ) {
            it.asObject
        }
    }

    override suspend fun keys(
        pagination: Pagination,
        reversed: Boolean
    ): PaginationResult<Key> = transaction(database) {
        selectAll().selectPaginated(
            pagination,
            keyColumn,
            reversed
        ) {
            it.asKey
        }
    }

    override suspend fun keys(
        v: Value,
        pagination: Pagination,
        reversed: Boolean
    ): PaginationResult<Key> = transaction(database) {
        selectAll().where { selectByValue(v) }.selectPaginated(
            pagination,
            keyColumn,
            reversed
        ) {
            it.asKey
        }
    }

    override suspend fun contains(k: Key): Boolean = transaction(database) {
        selectAll().where { selectById(k) }.limit(1).any()
    }

    override suspend fun contains(k: Key, v: Value): Boolean = transaction(database) {
        selectAll().where { selectById(k).and(selectByValue(v)) }.limit(1).any()
    }

    override suspend fun getAll(reverseLists: Boolean): Map<Key, List<Value>> = transaction(database) {
        val query = if (reverseLists) {
            selectAll().orderBy(keyColumn, SortOrder.DESC)
        } else {
            selectAll()
        }
        query.asSequence().map { it.asKey to it.asObject }.groupBy { it.first }.mapValues {
            it.value.map { it.second }
        }
    }

    override suspend fun getAll(k: Key, reversed: Boolean): List<Value> = transaction(database) {
        val query = if (reversed) {
            selectAll().where { selectById(k) }.orderBy(keyColumn, SortOrder.DESC)
        } else {
            selectAll().where { selectById(k) }
        }
        query.map {
            it.asObject
        }
    }
}
