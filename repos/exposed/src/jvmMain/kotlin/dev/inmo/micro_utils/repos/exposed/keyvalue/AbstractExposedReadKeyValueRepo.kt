package dev.inmo.micro_utils.repos.exposed.keyvalue

import com.insanusmokrassar.postssystem.exposed.commons.paginate
import com.insanusmokrassar.postssystem.utils.common.pagination.*
import com.insanusmokrassar.postssystem.utils.repos.StandardReadKeyValueRepo
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

abstract class AbstractExposedReadKeyValueRepo<Key, Value>(
    protected val database: Database,
    protected val keyColumn: Column<Key>,
    protected val valueColumn: Column<Value>
) : StandardReadKeyValueRepo<Key, Value>, Table() {
    override val primaryKey: PrimaryKey = PrimaryKey(keyColumn, valueColumn)

    override suspend fun get(k: Key): Value? = transaction(db = database) {
        select { keyColumn.eq(k) }.limit(1).firstOrNull() ?.getOrNull(valueColumn)
    }

    override suspend fun contains(key: Key): Boolean = transaction(db = database) {
        select { keyColumn.eq(key) }.limit(1).any()
    }

    override suspend fun count(): Long = transaction(db = database) { selectAll().count() }

    override suspend fun keys(pagination: Pagination, reversed: Boolean): PaginationResult<Key> = transaction(db = database) {
        selectAll().paginate(pagination, keyColumn to if (reversed) SortOrder.DESC else SortOrder.ASC).map {
            it[keyColumn]
        }
    }.createPaginationResult(pagination, count())

    override suspend fun values(pagination: Pagination, reversed: Boolean): PaginationResult<Value> = transaction(db = database) {
        selectAll().paginate(pagination, keyColumn to if (reversed) SortOrder.DESC else SortOrder.ASC).map {
            it[valueColumn]
        }
    }.createPaginationResult(pagination, count())
}
