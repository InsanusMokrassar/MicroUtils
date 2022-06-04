package dev.inmo.micro_utils.repos.exposed

import dev.inmo.micro_utils.pagination.*
import dev.inmo.micro_utils.repos.ReadCRUDRepo
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

abstract class AbstractExposedReadCRUDRepo<ObjectType, IdType>(
    tableName: String
) :
    ReadCRUDRepo<ObjectType, IdType>,
    ExposedCRUDRepo<ObjectType, IdType>,
    Table(tableName)
{
    override suspend fun getByPagination(pagination: Pagination): PaginationResult<ObjectType> {
        return transaction(db = database) {
            selectAll().paginate(pagination).map {
                it.asObject
            }.createPaginationResult(
                pagination,
                selectAll().count()
            )
        }
    }
    override suspend fun getById(id: IdType): ObjectType? {
        return transaction(db = database) {
            select {
                selectById(id)
            }.limit(1).firstOrNull() ?.asObject
        }
    }

    override suspend fun contains(id: IdType): Boolean = transaction(db = database) {
        select { selectById(id) }.limit(1).any()
    }

    override suspend fun count(): Long = transaction(db = database) { selectAll().count() }
}
