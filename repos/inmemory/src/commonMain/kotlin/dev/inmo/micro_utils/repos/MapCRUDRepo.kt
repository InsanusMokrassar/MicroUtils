package dev.inmo.micro_utils.repos

import dev.inmo.micro_utils.coroutines.SmartRWLocker
import dev.inmo.micro_utils.coroutines.withReadAcquire
import dev.inmo.micro_utils.coroutines.withWriteLock
import dev.inmo.micro_utils.pagination.*
import kotlinx.coroutines.flow.*

/**
 * [Map]-based [ReadMapCRUDRepo]. All internal operations will be locked with [locker] (mostly with
 * [SmartRWLocker.withReadAcquire])
 *
 * **Warning**: It is not recommended to use constructor with both [Map] and [SmartRWLocker]. Besides, in case
 * you are using your own [Map] as a [map] you should be careful with operations on this [map]
 */
class ReadMapCRUDRepo<ObjectType, IdType>(
    private val map: Map<IdType, ObjectType> = emptyMap(),
    private val locker: SmartRWLocker = SmartRWLocker()
) : ReadCRUDRepo<ObjectType, IdType> {
    override suspend fun getByPagination(pagination: Pagination): PaginationResult<ObjectType> {
        return locker.withReadAcquire {
            map.keys.drop(pagination.firstIndex).take(pagination.size).mapNotNull {
                map[it]
            }.createPaginationResult(
                pagination,
                count()
            )
        }
    }

    override suspend fun getIdsByPagination(pagination: Pagination): PaginationResult<IdType> {
        return locker.withReadAcquire {
            map.keys.drop(pagination.firstIndex).take(pagination.size).createPaginationResult(
                pagination,
                count()
            )
        }
    }

    override suspend fun getById(id: IdType): ObjectType? = locker.withReadAcquire {
        map[id]
    }

    override suspend fun contains(id: IdType): Boolean = locker.withReadAcquire {
        map.containsKey(id)
    }

    override suspend fun getAll(): Map<IdType, ObjectType> = locker.withReadAcquire {
        map.toMap()
    }

    override suspend fun count(): Long = locker.withReadAcquire {
        map.size.toLong()
    }
}

/**
 * [MutableMap]-based [WriteMapCRUDRepo]. All internal operations will be locked with [locker] (mostly with
 * [SmartRWLocker.withWriteLock])
 *
 * **Warning**: It is not recommended to use constructor with both [MutableMap] and [SmartRWLocker]. Besides, in case
 * you are using your own [MutableMap] as a [map] you should be careful with operations on this [map]
 */
abstract class WriteMapCRUDRepo<ObjectType, IdType, InputValueType>(
    protected val map: MutableMap<IdType, ObjectType> = mutableMapOf(),
    protected val locker: SmartRWLocker = SmartRWLocker()
) : WriteCRUDRepo<ObjectType, IdType, InputValueType> {
    protected open val _newObjectsFlow: MutableSharedFlow<ObjectType> = MapsReposDefaultMutableSharedFlow()
    override val newObjectsFlow: Flow<ObjectType> by lazy { _newObjectsFlow.asSharedFlow() }
    protected open val _updatedObjectsFlow: MutableSharedFlow<ObjectType> = MapsReposDefaultMutableSharedFlow()
    override val updatedObjectsFlow: Flow<ObjectType> by lazy { _updatedObjectsFlow.asSharedFlow() }
    protected open val _deletedObjectsIdsFlow: MutableSharedFlow<IdType> = MapsReposDefaultMutableSharedFlow()
    override val deletedObjectsIdsFlow: Flow<IdType> by lazy { _deletedObjectsIdsFlow.asSharedFlow() }

    protected abstract suspend fun updateObject(newValue: InputValueType, id: IdType, old: ObjectType): ObjectType
    protected abstract suspend fun createObject(newValue: InputValueType): Pair<IdType, ObjectType>

    override suspend fun create(values: List<InputValueType>): List<ObjectType> {
        return locker.withWriteLock {
            values.map {
                val (id, newObject) = createObject(it)
                map[id] = newObject
                newObject
            }
        }.onEach {
            _newObjectsFlow.emit(it)
        }
    }

    override suspend fun update(id: IdType, value: InputValueType): ObjectType? {
        return locker.withWriteLock {
            val newValue = updateObject(value, id, map[id] ?: return@withWriteLock null)

            newValue.also {
                map[id] = it
            }
        } ?.also {
            _updatedObjectsFlow.emit(it)
        }
    }

    override suspend fun update(values: List<UpdatedValuePair<IdType, InputValueType>>): List<ObjectType> {
        return values.mapNotNull { update(it.first, it.second) }
    }

    override suspend fun deleteById(ids: List<IdType>) {
        locker.withWriteLock {
            ids.mapNotNull {
                it.takeIf { map.remove(it) != null }
            }
        }.onEach { _deletedObjectsIdsFlow.emit(it) }
    }

}

/**
 * [MutableMap]-based [MapCRUDRepo]. All internal operations will be locked with [locker]
 *
 * **Warning**: It is not recommended to use constructor with both [MutableMap] and [SmartRWLocker]. Besides, in case
 * you are using your own [MutableMap] as a [map] you should be careful with operations on this [map]
 */
abstract class MapCRUDRepo<ObjectType, IdType, InputValueType>(
    map: MutableMap<IdType, ObjectType>,
    locker: SmartRWLocker = SmartRWLocker()
) : CRUDRepo<ObjectType, IdType, InputValueType>,
    ReadCRUDRepo<ObjectType, IdType> by ReadMapCRUDRepo(map, locker),
    WriteMapCRUDRepo<ObjectType, IdType, InputValueType>(map, locker)

/**
 * [MutableMap]-based [MapCRUDRepo]. All internal operations will be locked with [locker]
 *
 * **Warning**: Besides, in case you are using your own [MutableMap] as a [map] you should be careful with operations
 * on this [map]
 */
fun <ObjectType, IdType, InputValueType> MapCRUDRepo(
    map: MutableMap<IdType, ObjectType>,
    updateCallback: suspend MutableMap<IdType, ObjectType>.(newValue: InputValueType, id: IdType, old: ObjectType) -> ObjectType,
    locker: SmartRWLocker = SmartRWLocker(),
    createCallback: suspend MutableMap<IdType, ObjectType>.(newValue: InputValueType) -> Pair<IdType, ObjectType>
) = object : MapCRUDRepo<ObjectType, IdType, InputValueType>(map, locker) {
    override suspend fun updateObject(
        newValue: InputValueType,
        id: IdType,
        old: ObjectType
    ): ObjectType = map.updateCallback(newValue, id, old)

    override suspend fun createObject(newValue: InputValueType): Pair<IdType, ObjectType> = map.createCallback(newValue)
}

/**
 * [MutableMap]-based [MapCRUDRepo]. All internal operations will be locked with [locker]
 */
fun <ObjectType, IdType, InputValueType> MapCRUDRepo(
    updateCallback: suspend MutableMap<IdType, ObjectType>.(newValue: InputValueType, id: IdType, old: ObjectType) -> ObjectType,
    locker: SmartRWLocker = SmartRWLocker(),
    createCallback: suspend MutableMap<IdType, ObjectType>.(newValue: InputValueType) -> Pair<IdType, ObjectType>
) = MapCRUDRepo(mutableMapOf(), updateCallback, locker, createCallback)

/**
 * [MutableMap]-based [MapCRUDRepo]. All internal operations will be locked with [locker]
 *
 * **Warning**: Besides, in case you are using your own [MutableMap] as a [this] you should be careful with operations
 * on this [this]
 */
fun <ObjectType, IdType, InputValueType> MutableMap<IdType, ObjectType>.asCrudRepo(
    updateCallback: suspend MutableMap<IdType, ObjectType>.(newValue: InputValueType, id: IdType, old: ObjectType) -> ObjectType,
    locker: SmartRWLocker = SmartRWLocker(),
    createCallback: suspend MutableMap<IdType, ObjectType>.(newValue: InputValueType) -> Pair<IdType, ObjectType>
) = MapCRUDRepo(this, updateCallback, locker, createCallback)
