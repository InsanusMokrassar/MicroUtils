package dev.inmo.micro_utils.repos

import dev.inmo.micro_utils.pagination.Pagination
import dev.inmo.micro_utils.pagination.PaginationResult
import dev.inmo.micro_utils.pagination.changeResultsUnchecked
import dev.inmo.micro_utils.pagination.utils.getAllWithCurrentPaging
import dev.inmo.micro_utils.repos.pagination.maxPagePagination
import kotlinx.coroutines.flow.Flow

interface ReadCRUDRepo<ObjectType, IdType> : Repo {
    suspend fun getByPagination(pagination: Pagination): PaginationResult<ObjectType>
    suspend fun getIdsByPagination(pagination: Pagination): PaginationResult<IdType>
    suspend fun getById(id: IdType): ObjectType?
    suspend fun contains(id: IdType): Boolean
    suspend fun getAll(): Map<IdType, ObjectType> = getAllWithCurrentPaging(maxPagePagination()) {
        getIdsByPagination(it).let {
            it.changeResultsUnchecked(
                it.results.mapNotNull { it to (getById(it) ?: return@mapNotNull null) }
            )
        }
    }.toMap()

    suspend fun count(): Long
}
typealias ReadStandardCRUDRepo<ObjectType, IdType> = ReadCRUDRepo<ObjectType, IdType>

typealias UpdatedValuePair<IdType, ValueType> = Pair<IdType, ValueType>
val <IdType> UpdatedValuePair<IdType, *>.id
    get() = first
val <ValueType> UpdatedValuePair<*, ValueType>.value
    get() = second

interface WriteCRUDRepo<ObjectType, IdType, InputValueType> : Repo {
    val newObjectsFlow: Flow<ObjectType>
    val updatedObjectsFlow: Flow<ObjectType>
    val deletedObjectsIdsFlow: Flow<IdType>

    suspend fun create(values: List<InputValueType>): List<ObjectType>
    suspend fun update(id: IdType, value: InputValueType): ObjectType?
    suspend fun update(values: List<UpdatedValuePair<IdType, InputValueType>>): List<ObjectType>
    suspend fun deleteById(ids: List<IdType>)
    suspend fun deleteById(vararg ids: IdType) = deleteById(ids.toList())
}
typealias WriteStandardCRUDRepo<ObjectType, IdType, InputValueType> = WriteCRUDRepo<ObjectType, IdType, InputValueType>

/**
 * Just mirroring [WriteCRUDRepo.newObjectsFlow] to be same as in KV repos
 */
val <ObjectType> WriteCRUDRepo<ObjectType, *, *>.onNewObjects: Flow<ObjectType>
    get() = newObjectsFlow
/**
 * Just mirroring [WriteCRUDRepo.updatedObjectsFlow] to be same as in KV repos
 */
val <ObjectType> WriteCRUDRepo<ObjectType, *, *>.onUpdatedObjects: Flow<ObjectType>
    get() = updatedObjectsFlow
/**
 * Just mirroring [WriteCRUDRepo.deletedObjectsIdsFlow] to be same as in KV repos
 */
val <IdType> WriteCRUDRepo<*, IdType, *>.onDeletedObjectsIds: Flow<IdType>
    get() = deletedObjectsIdsFlow

suspend fun <ObjectType, IdType, InputValueType> WriteCRUDRepo<ObjectType, IdType, InputValueType>.create(
    vararg values: InputValueType
): List<ObjectType> = create(values.toList())
suspend fun <ObjectType, IdType, InputValueType> WriteCRUDRepo<ObjectType, IdType, InputValueType>.update(
    vararg values: UpdatedValuePair<IdType, InputValueType>
): List<ObjectType> = update(values.toList())
suspend fun <ObjectType, IdType, InputValueType> WriteCRUDRepo<ObjectType, IdType, InputValueType>.deleteById(
    vararg ids: IdType
) = deleteById(ids.toList())

interface CRUDRepo<ObjectType, IdType, InputValueType> : ReadCRUDRepo<ObjectType, IdType>,
    WriteCRUDRepo<ObjectType, IdType, InputValueType>
typealias StandardCRUDRepo<ObjectType, IdType, InputValueType> = CRUDRepo<ObjectType, IdType, InputValueType>

class DelegateBasedCRUDRepo<ObjectType, IdType, InputValueType>(
    readDelegate: ReadCRUDRepo<ObjectType, IdType>,
    writeDelegate: WriteCRUDRepo<ObjectType, IdType, InputValueType>
) : CRUDRepo<ObjectType, IdType, InputValueType>,
    ReadCRUDRepo<ObjectType, IdType> by readDelegate,
    WriteCRUDRepo<ObjectType, IdType, InputValueType> by writeDelegate
