package dev.inmo.micro_utils.repos.exposed

import com.insanusmokrassar.postssystem.exposed.commons.paginate
import com.insanusmokrassar.postssystem.utils.common.pagination.*
import com.insanusmokrassar.postssystem.utils.repos.ReadStandardCRUDRepo
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

abstract class AbstractExposedReadCRUDRepo<ObjectType, IdType>(
    tableName: String
) :
    ReadStandardCRUDRepo<ObjectType, IdType>,
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
}
