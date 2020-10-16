package dev.inmo.micro_utils.repos

import dev.inmo.micro_utils.coroutines.BroadcastFlow
import dev.inmo.micro_utils.pagination.*
import kotlinx.coroutines.flow.Flow

class ReadMapCRUDRepo<ObjectType, IdType>(
    private val map: Map<IdType, ObjectType> = emptyMap()
) : ReadStandardCRUDRepo<ObjectType, IdType> {
    override suspend fun getByPagination(pagination: Pagination): PaginationResult<ObjectType> {
        return map.keys.drop(pagination.firstIndex).take(pagination.size).mapNotNull {
            map[it]
        }.createPaginationResult(
            pagination,
            count()
        )
    }

    override suspend fun getById(id: IdType): ObjectType? = map[id]

    override suspend fun contains(id: IdType): Boolean = map.containsKey(id)

    override suspend fun count(): Long = map.size.toLong()
}

abstract class WriteMapCRUDRepo<ObjectType, IdType, InputValueType>(
    private val map: MutableMap<IdType, ObjectType> = mutableMapOf()
) : WriteStandardCRUDRepo<ObjectType, IdType, InputValueType> {
    private val _newObjectsFlow: BroadcastFlow<ObjectType> = BroadcastFlow()
    override val newObjectsFlow: Flow<ObjectType>
        get() = _newObjectsFlow
    private val _updatedObjectsFlow: BroadcastFlow<ObjectType> = BroadcastFlow()
    override val updatedObjectsFlow: Flow<ObjectType>
        get() = _updatedObjectsFlow
    private val _deletedObjectsIdsFlow: BroadcastFlow<IdType> = BroadcastFlow()
    override val deletedObjectsIdsFlow: Flow<IdType>
        get() = _deletedObjectsIdsFlow

    protected abstract suspend fun updateObject(newValue: InputValueType, id: IdType, old: ObjectType): ObjectType
    protected abstract suspend fun createObject(newValue: InputValueType): Pair<IdType, ObjectType>

    override suspend fun create(values: List<InputValueType>): List<ObjectType> {
        return values.map {
            val (id, newObject) = createObject(it)
            map[id] = newObject
            newObject.also { _ ->
                _newObjectsFlow.send(newObject)
            }
        }
    }

    override suspend fun update(id: IdType, value: InputValueType): ObjectType? {
        val newValue = updateObject(value, id, map[id] ?: return null)

        return newValue.also {
            map[id] = it
            _updatedObjectsFlow.send(it)
        }
    }

    override suspend fun update(values: List<UpdatedValuePair<IdType, InputValueType>>): List<ObjectType> {
        return values.mapNotNull { update(it.first, it.second) }
    }

    override suspend fun deleteById(ids: List<IdType>) {
        ids.forEach {
            map.remove(it) ?.also { _ -> _deletedObjectsIdsFlow.send(it) }
        }
    }

}

abstract class MapCRUDRepo<ObjectType, IdType, InputValueType>(
    map: MutableMap<IdType, ObjectType>
) : StandardCRUDRepo<ObjectType, IdType, InputValueType>,
    ReadStandardCRUDRepo<ObjectType, IdType> by ReadMapCRUDRepo(map),
    WriteMapCRUDRepo<ObjectType, IdType, InputValueType>(map)

fun <ObjectType, IdType, InputValueType> MapCRUDRepo(
    map: MutableMap<IdType, ObjectType>,
    updateCallback: suspend (newValue: InputValueType, id: IdType, old: ObjectType) -> ObjectType,
    createCallback: suspend (newValue: InputValueType) -> Pair<IdType, ObjectType>
) = object : MapCRUDRepo<ObjectType, IdType, InputValueType>(map) {
    override suspend fun updateObject(
        newValue: InputValueType,
        id: IdType,
        old: ObjectType
    ): ObjectType = updateCallback(newValue, id, old)

    override suspend fun createObject(newValue: InputValueType): Pair<IdType, ObjectType> = createCallback(newValue)
}

fun <ObjectType, IdType, InputValueType> MutableMap<IdType, ObjectType>.asCrudRepo(
    updateCallback: suspend (newValue: InputValueType, id: IdType, old: ObjectType) -> ObjectType,
    createCallback: suspend (newValue: InputValueType) -> Pair<IdType, ObjectType>
) = MapCRUDRepo(this, updateCallback, createCallback)
