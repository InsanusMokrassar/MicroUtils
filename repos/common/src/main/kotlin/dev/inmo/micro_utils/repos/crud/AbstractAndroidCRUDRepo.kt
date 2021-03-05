package dev.inmo.micro_utils.repos.crud

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import dev.inmo.micro_utils.pagination.Pagination
import dev.inmo.micro_utils.pagination.PaginationResult
import dev.inmo.micro_utils.pagination.createPaginationResult
import dev.inmo.micro_utils.repos.*

val <T> T.asId: String
    get() = (this as? String) ?: this!!.toString()

abstract class AbstractAndroidCRUDRepo<ObjectType, IdType>(
    protected val helper: StandardSQLHelper
) : ReadStandardCRUDRepo<ObjectType, IdType> {
    protected abstract val tableName: String
    protected abstract val idColumnName: String
    protected abstract suspend fun Cursor.toObject(): ObjectType
    protected fun SQLiteDatabase.count(): Long = select(tableName).use {
        it.count
    }.toLong()

    override suspend fun contains(id: IdType): Boolean = helper.blockingReadableTransaction {
        select(
            tableName,
            null,
            "$idColumnName=?",
            arrayOf(id.asId)
        ).use {
            it.count > 0
        }
    }

    override suspend fun getById(id: IdType): ObjectType? = helper.readableTransaction {
        select(
            tableName,
            selection = "$idColumnName=?",
            selectionArgs = arrayOf(id.asId),
            limit = limitClause(1)
        ).use { c ->
            if (c.moveToFirst()) {
                c.toObject()
            } else {
                null
            }
        }
    }

    override suspend fun getByPagination(pagination: Pagination): PaginationResult<ObjectType> {
        return helper.readableTransaction {
            select(
                tableName,
                limit = pagination.limitClause()
            ).use {
                if (it.moveToFirst()) {
                    val resultList = mutableListOf(it.toObject())
                    while (it.moveToNext()) {
                        resultList.add(it.toObject())
                    }
                    resultList.createPaginationResult(pagination, count())
                } else {
                    emptyList<ObjectType>().createPaginationResult(pagination, 0)
                }
            }
        }
    }
}