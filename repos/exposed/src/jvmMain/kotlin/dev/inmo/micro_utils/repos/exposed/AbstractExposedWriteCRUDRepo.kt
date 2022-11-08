package dev.inmo.micro_utils.repos.exposed

import dev.inmo.micro_utils.repos.UpdatedValuePair
import dev.inmo.micro_utils.repos.WriteCRUDRepo
import kotlinx.coroutines.flow.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.statements.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.Objects

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

    override val newObjectsFlow: Flow<ObjectType> = _newObjectsFlow.asSharedFlow()
    override val updatedObjectsFlow: Flow<ObjectType> = _updatedObjectsFlow.asSharedFlow()
    override val deletedObjectsIdsFlow: Flow<IdType> = _deletedObjectsIdsFlow.asSharedFlow()

    protected abstract fun InsertStatement<Number>.asObject(value: InputValueType): ObjectType

    /**
     * @param id Can be null only if [createAndInsertId] have returned null (it can be useful when you have
     * autoincrement identifier)
     * @param it Will be [UpdateStatement] when it is called from [update] method or [InsertStatement] from the [create]
     * one. Anyway, it is main method where you should put the logic of table filling by [value] data
     *
     * @see createAndInsertId
     */
    protected abstract fun update(id: IdType?, value: InputValueType, it: UpdateBuilder<Int>)

    /**
     * Override this method to interact with [it] ([InsertStatement]) and put there new id with [IdType].
     *
     * By default, have null value due to the fact that in the most cases users have [autoIncrement]ing id columns
     *
     * @return In case when id for the model has been created new [IdType] should be returned
     */
    protected open fun createAndInsertId(value: InputValueType, it: InsertStatement<Number>): IdType? = null

    protected open fun insert(value: InputValueType, it: InsertStatement<Number>) {
        val id = createAndInsertId(value, it)
        update(id, value, it as UpdateBuilder<Int>)
    }

    protected open suspend fun onBeforeCreate(value: List<InputValueType>) {}

    /**
     * Use this method to do the something with [values]. You may change and output values in that list and return
     * changed list of pairs
     */
    protected open suspend fun onAfterCreate(
        values: List<Pair<InputValueType, ObjectType>>
    ): List<ObjectType> = values.map { it.second }
    private fun createWithoutNotification(value: InputValueType): ObjectType {
        return transaction(database) {
            insert { insert(value, it) }.asObject(value)
        }
    }

    override suspend fun create(values: List<InputValueType>): List<ObjectType> {
        onBeforeCreate(values)
        return transaction(db = database) {
            values.map { value -> value to createWithoutNotification(value) }
        }.let {
            onAfterCreate(it)
        }.onEach {
            _newObjectsFlow.emit(it)
        }
    }

    protected open suspend fun onBeforeUpdate(value: List<UpdatedValuePair<IdType, InputValueType>>) {}
    protected open suspend fun onAfterUpdate(
        value: List<UpdatedValuePair<InputValueType, ObjectType>>
    ): List<ObjectType> = value.map { it.second }
    private fun updateWithoutNotification(id: IdType, value: InputValueType): ObjectType? {
        return transaction(db = database) {
            update(
                {
                    selectById(this, id)
                }
            ) {
                update(id, value, it as UpdateBuilder<Int>)
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
        return updateWithoutNotification(id, value).let {
            onAfterUpdate(listOf(value to (it ?: return@let emptyList())))
        }.firstOrNull().also {
            if (it != null) {
                _updatedObjectsFlow.emit(it)
            }
        }
    }
    override suspend fun update(values: List<UpdatedValuePair<IdType, InputValueType>>): List<ObjectType> {
        onBeforeUpdate(values)
        return (
            transaction(db = database) {
                values.mapNotNull { (id, value) -> value to (updateWithoutNotification(id, value) ?: return@mapNotNull null) }
            }
        ).let {
            onAfterUpdate(it)
        }.onEach {
            _updatedObjectsFlow.emit(it)
        }
    }
    protected open suspend fun onBeforeDelete(ids: List<IdType>) {}
    override suspend fun deleteById(ids: List<IdType>) {
        onBeforeDelete(ids)
        transaction(db = database) {
            val deleted = deleteWhere(null, null) { selectByIds(it, ids) }
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
