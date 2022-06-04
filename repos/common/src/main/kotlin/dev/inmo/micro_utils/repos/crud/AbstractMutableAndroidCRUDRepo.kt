package dev.inmo.micro_utils.repos.crud

import android.content.ContentValues
import dev.inmo.micro_utils.common.mapNotNullA
import dev.inmo.micro_utils.repos.*
import kotlinx.coroutines.flow.*

abstract class AbstractMutableAndroidCRUDRepo<ObjectType, IdType, InputValueType>(
    helper: StandardSQLHelper,
    replyInFlows: Int = 0,
    extraBufferCapacityInFlows: Int = 64
) : WriteCRUDRepo<ObjectType, IdType, InputValueType>,
    AbstractAndroidCRUDRepo<ObjectType, IdType>(helper),
    CRUDRepo<ObjectType, IdType, InputValueType> {
    protected val newObjectsChannel = MutableSharedFlow<ObjectType>(replyInFlows, extraBufferCapacityInFlows)
    protected val updateObjectsChannel = MutableSharedFlow<ObjectType>(replyInFlows, extraBufferCapacityInFlows)
    protected val deleteObjectsIdsChannel = MutableSharedFlow<IdType>(replyInFlows, extraBufferCapacityInFlows)
    override val newObjectsFlow: Flow<ObjectType> = newObjectsChannel.asSharedFlow()
    override val updatedObjectsFlow: Flow<ObjectType> = updateObjectsChannel.asSharedFlow()
    override val deletedObjectsIdsFlow: Flow<IdType> = deleteObjectsIdsChannel.asSharedFlow()

    protected abstract suspend fun InputValueType.asContentValues(id: IdType? = null): ContentValues

    override suspend fun create(values: List<InputValueType>): List<ObjectType> {
        val valuesContentValues = values.map { it.asContentValues() }
        val indexes = helper.blockingWritableTransaction {
            valuesContentValues.map {
                insert(tableName, null, it)
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
        helper.blockingWritableTransaction {
            for (id in ids) {
                delete(tableName, "$idColumnName=?", arrayOf(id.asId)).also {
                    if (it > 0) {
                        deleted.add(id)
                    }
                }
            }
        }
        for (deletedItem in deleted) {
            deleteObjectsIdsChannel.emit(deletedItem)
        }
    }

    override suspend fun update(id: IdType, value: InputValueType): ObjectType? {
        val asContentValues = value.asContentValues(id)
        if (asContentValues.keySet().isNotEmpty()) {
            helper.blockingWritableTransaction {
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
        val contentValues = values.map { (id, value) -> id to value.asContentValues(id) }
        helper.writableTransaction {
            contentValues.forEach { (id, contentValues) ->
                update(
                    tableName,
                    contentValues,
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

    override suspend fun count(): Long = helper.blockingReadableTransaction { select(tableName).use { it.count.toLong() } }
}
