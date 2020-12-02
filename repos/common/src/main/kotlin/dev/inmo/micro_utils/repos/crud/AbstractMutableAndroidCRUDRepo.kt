package dev.inmo.micro_utils.repos.crud

import android.content.ContentValues
import dev.inmo.micro_utils.common.mapNotNullA
import dev.inmo.micro_utils.repos.*
import kotlinx.coroutines.flow.*

abstract class AbstractMutableAndroidCRUDRepo<ObjectType, IdType, InputValueType>(
    helper: StandardSQLHelper
) : WriteStandardCRUDRepo<ObjectType, IdType, InputValueType>,
    AbstractAndroidCRUDRepo<ObjectType, IdType>(helper) {
    protected val newObjectsChannel = MutableSharedFlow<ObjectType>(64)
    protected val updateObjectsChannel = MutableSharedFlow<ObjectType>(64)
    protected val deleteObjectsIdsChannel = MutableSharedFlow<IdType>(64)
    override val newObjectsFlow: Flow<ObjectType> = newObjectsChannel.asSharedFlow()
    override val updatedObjectsFlow: Flow<ObjectType> = updateObjectsChannel.asSharedFlow()
    override val deletedObjectsIdsFlow: Flow<IdType> = deleteObjectsIdsChannel.asSharedFlow()

    protected abstract suspend fun InputValueType.asContentValues(id: IdType? = null): ContentValues

    override suspend fun create(values: List<InputValueType>): List<ObjectType> {
        val indexes = helper.writableTransaction {
            values.map {
                insert(tableName, null, it.asContentValues())
            }
        }
        return helper.readableTransaction {
            indexes.mapNotNullA { i ->
                select(
                    tableName,
                    selection = "$internalId=?",
                    selectionArgs = arrayOf(i.toString())
                ).use { c ->
                    if (c.moveToFirst()) {
                        c.toObject()
                    } else {
                        null
                    }
                }
            }
        }.also {
            it.forEach {
                newObjectsChannel.emit(it)
            }
        }
    }

    override suspend fun deleteById(ids: List<IdType>) {
        val deleted = mutableListOf<IdType>()
        helper.writableTransaction {
            ids.forEach { id ->
                delete(tableName, "$idColumnName=?", arrayOf(id.asId)).also {
                    if (it > 0) {
                        deleted.add(id)
                    }
                }
            }
        }
        deleted.forEach {
            deleteObjectsIdsChannel.emit(it)
        }
    }

    override suspend fun update(id: IdType, value: InputValueType): ObjectType? {
        val asContentValues = value.asContentValues(id)
        if (asContentValues.keySet().isNotEmpty()) {
            helper.writableTransaction {
                update(
                    tableName,
                    asContentValues,
                    "$idColumnName=?",
                    arrayOf(id.asId)
                )
            }
        }
        return getById(id) ?.also {
            updateObjectsChannel.emit(it)
        }
    }

    override suspend fun update(values: List<UpdatedValuePair<IdType, InputValueType>>): List<ObjectType> {
        helper.writableTransaction {
            values.forEach { (id, value) ->
                update(
                    tableName,
                    value.asContentValues(id),
                    "$idColumnName=?",
                    arrayOf(id.asId)
                )
            }
        }
        return values.mapNotNullA {
            getById(it.first)
        }.also {
            it.forEach {
                updateObjectsChannel.emit(it)
            }
        }
    }

    override suspend fun count(): Long = helper.readableTransaction { select(tableName).use { it.count.toLong() } }
}