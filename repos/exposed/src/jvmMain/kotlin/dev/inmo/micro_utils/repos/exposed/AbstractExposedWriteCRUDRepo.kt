package dev.inmo.micro_utils.repos.exposed

import dev.inmo.micro_utils.repos.UpdatedValuePair
import dev.inmo.micro_utils.repos.WriteStandardCRUDRepo
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateStatement
import org.jetbrains.exposed.sql.transactions.transaction

abstract class AbstractExposedWriteCRUDRepo<ObjectType, IdType, InputValueType>(
    flowsChannelsSize: Int = 0,
    tableName: String = ""
) :
    AbstractExposedReadCRUDRepo<ObjectType, IdType>(tableName),
    ExposedCRUDRepo<ObjectType, IdType>,
    WriteStandardCRUDRepo<ObjectType, IdType, InputValueType>
{
    protected val newObjectsChannel = MutableSharedFlow<ObjectType>(flowsChannelsSize)
    protected val updateObjectsChannel = MutableSharedFlow<ObjectType>(flowsChannelsSize)
    protected val deleteObjectsIdsChannel = MutableSharedFlow<IdType>(flowsChannelsSize)

    override val newObjectsFlow: Flow<ObjectType> = newObjectsChannel.asSharedFlow()
    override val updatedObjectsFlow: Flow<ObjectType> = updateObjectsChannel.asSharedFlow()
    override val deletedObjectsIdsFlow: Flow<IdType> = deleteObjectsIdsChannel.asSharedFlow()

    @Deprecated("Will be removed in near major update. Override open fun with the same name instead")
    abstract val InsertStatement<Number>.asObject: ObjectType
    protected open fun InsertStatement<Number>.asObject(value: InputValueType): ObjectType = asObject
    abstract val selectByIds: SqlExpressionBuilder.(List<IdType>) -> Op<Boolean>

    protected abstract fun insert(value: InputValueType, it: InsertStatement<Number>)
    protected abstract fun update(id: IdType, value: InputValueType, it: UpdateStatement)

    protected open suspend fun onBeforeCreate(value: List<InputValueType>) {}
    private fun createWithoutNotification(value: InputValueType): ObjectType {
        return transaction(database) {
            insert { insert(value, it) }.asObject(value)
        }
    }

    override suspend fun create(values: List<InputValueType>): List<ObjectType> {
        onBeforeCreate(values)
        return transaction(db = database) {
            values.map { value -> createWithoutNotification(value) }
        }.onEach {
            newObjectsChannel.emit(it)
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
                updateObjectsChannel.emit(it)
            }
        }
    }
    override suspend fun update(values: List<UpdatedValuePair<IdType, InputValueType>>): List<ObjectType> {
        onBeforeUpdate(values)
        return (
            transaction(db = database) {
                values.map { (id, value) -> updateWithoutNotification(id, value) }
            }.filterNotNull()
        ).onEach {
            updateObjectsChannel.emit(it)
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
