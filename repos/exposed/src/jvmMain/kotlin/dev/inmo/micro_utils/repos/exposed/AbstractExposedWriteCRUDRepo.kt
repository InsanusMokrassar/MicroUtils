package dev.inmo.micro_utils.repos.exposed

import dev.inmo.micro_utils.repos.UpdatedValuePair
import dev.inmo.micro_utils.repos.WriteCRUDRepo
import kotlinx.coroutines.flow.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateStatement
import org.jetbrains.exposed.sql.transactions.transaction

abstract class AbstractExposedWriteCRUDRepo<ObjectType, IdType, InputValueType>(
    flowsChannelsSize: Int = 0,
    tableName: String = "",
    replyCacheInFlows: Int = 0
) :
    AbstractExposedReadCRUDRepo<ObjectType, IdType>(tableName),
    ExposedCRUDRepo<ObjectType, IdType>,
    WriteCRUDRepo<ObjectType, IdType, InputValueType>
{
    protected val _newObjectsFlow = MutableSharedFlow<ObjectType>(replyCacheInFlows, flowsChannelsSize)
    protected val _updatedObjectsFlow = MutableSharedFlow<ObjectType>(replyCacheInFlows, flowsChannelsSize)
    protected val _deletedObjectsIdsFlow = MutableSharedFlow<IdType>(replyCacheInFlows, flowsChannelsSize)
    @Deprecated("Renamed", ReplaceWith("_newObjectsFlow"))
    protected val newObjectsChannel = _newObjectsFlow
    @Deprecated("Renamed", ReplaceWith("_updatedObjectsFlow"))
    protected val updateObjectsChannel = _updatedObjectsFlow
    @Deprecated("Renamed", ReplaceWith("_deletedObjectsIdsFlow"))
    protected val deleteObjectsIdsChannel = _deletedObjectsIdsFlow

    override val newObjectsFlow: Flow<ObjectType> = _newObjectsFlow.asSharedFlow()
    override val updatedObjectsFlow: Flow<ObjectType> = _updatedObjectsFlow.asSharedFlow()
    override val deletedObjectsIdsFlow: Flow<IdType> = _deletedObjectsIdsFlow.asSharedFlow()

    protected abstract fun InsertStatement<Number>.asObject(value: InputValueType): ObjectType
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
            _newObjectsFlow.emit(it)
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
                transaction(db = database) {
                    select {
                        selectById(this, id)
                    }.limit(1).firstOrNull() ?.asObject
                }
            } else {
                null
            }
        }
    }

    override suspend fun update(id: IdType, value: InputValueType): ObjectType? {
        onBeforeUpdate(listOf(id to value))
        return updateWithoutNotification(id, value).also {
            if (it != null) {
                _updatedObjectsFlow.emit(it)
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
            _updatedObjectsFlow.emit(it)
        }
    }
    protected open suspend fun onBeforeDelete(ids: List<IdType>) {}
    override suspend fun deleteById(ids: List<IdType>) {
        onBeforeDelete(ids)
        transaction(db = database) {
            val deleted = deleteWhere(null, null) {
                selectByIds(ids)
            }
            if (deleted == ids.size) {
                ids
            } else {
                ids.filter {
                    select { selectById(it) }.limit(1).none()
                }
            }
        }.forEach {
            _deletedObjectsIdsFlow.emit(it)
        }
    }
}
