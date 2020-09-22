package dev.inmo.micro_utils.repos.exposed

import com.insanusmokrassar.postssystem.utils.repos.UpdatedValuePair
import com.insanusmokrassar.postssystem.utils.repos.WriteStandardCRUDRepo
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateStatement
import org.jetbrains.exposed.sql.transactions.transaction

abstract class AbstractExposedWriteCRUDRepo<ObjectType, IdType, InputValueType>(
    flowsChannelsSize: Int = 64,
    databaseName: String = ""
) :
    AbstractExposedReadCRUDRepo<ObjectType, IdType>(databaseName),
    ExposedCRUDRepo<ObjectType, IdType>,
    WriteStandardCRUDRepo<ObjectType, IdType, InputValueType>
{
    protected val newObjectsChannel = BroadcastChannel<ObjectType>(flowsChannelsSize)
    protected val updateObjectsChannel = BroadcastChannel<ObjectType>(flowsChannelsSize)
    protected val deleteObjectsIdsChannel = BroadcastChannel<IdType>(flowsChannelsSize)

    override val newObjectsFlow: Flow<ObjectType> = newObjectsChannel.asFlow()
    override val updatedObjectsFlow: Flow<ObjectType> = updateObjectsChannel.asFlow()
    override val deletedObjectsIdsFlow: Flow<IdType> = deleteObjectsIdsChannel.asFlow()

    abstract val InsertStatement<Number>.asObject: ObjectType
    abstract val selectByIds: SqlExpressionBuilder.(List<out IdType>) -> Op<Boolean>

    protected abstract fun insert(value: InputValueType, it: InsertStatement<Number>)
    protected abstract fun update(id: IdType, value: InputValueType, it: UpdateStatement)

    protected open suspend fun onBeforeCreate(value: List<InputValueType>) {}
    private fun createWithoutNotification(value: InputValueType): ObjectType {
        return transaction(database) {
            insert { insert(value, it) }.asObject
        }
    }

    override suspend fun create(values: List<InputValueType>): List<ObjectType> {
        onBeforeCreate(values)
        return transaction(db = database) {
            values.map { value -> createWithoutNotification(value) }
        }.also {
            it.forEach {
                newObjectsChannel.send(it)
            }
        }
    }

    protected open suspend fun onBeforeUpdate(value: List<UpdatedValuePair<IdType, InputValueType>>) {}
    private fun updateWithoutNotification(id: IdType, value: InputValueType): ObjectType? {
        return transaction(db = database) {
            update(
                {
                    selectById(this, id)
                }
            ) {
                update(id, value, it)
            }
        }.let {
            if (it > 0) {
                select {
                    selectById(this, id)
                }.limit(1).firstOrNull() ?.asObject
            } else {
                null
            }
        }
    }

    override suspend fun update(id: IdType, value: InputValueType): ObjectType? {
        onBeforeUpdate(listOf(id to value))
        return updateWithoutNotification(id, value).also {
            if (it != null) {
                updateObjectsChannel.send(it)
            }
        }
    }
    override suspend fun update(values: List<UpdatedValuePair<IdType, InputValueType>>): List<ObjectType> {
        onBeforeUpdate(values)
        return (
            transaction(db = database) {
                values.map { (id, value) -> updateWithoutNotification(id, value) }
            }.filter {
                it != null
            } as List<ObjectType>
        ).also {
            it.forEach {
                updateObjectsChannel.send(it)
            }
        }
    }
    protected open suspend fun onBeforeDelete(ids: List<IdType>) {}
    override suspend fun deleteById(ids: List<IdType>) {
        onBeforeDelete(ids)
        transaction(db = database) {
            deleteWhere(null, null) {
                selectByIds(ids)
            }
        }
    }
}