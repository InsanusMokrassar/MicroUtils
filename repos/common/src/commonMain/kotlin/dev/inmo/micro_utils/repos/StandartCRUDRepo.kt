package dev.inmo.micro_utils.repos

import dev.inmo.micro_utils.pagination.Pagination
import dev.inmo.micro_utils.pagination.PaginationResult
import dev.inmo.micro_utils.pagination.changeResultsUnchecked
import dev.inmo.micro_utils.pagination.utils.getAllWithCurrentPaging
import dev.inmo.micro_utils.repos.pagination.maxPagePagination
import kotlinx.coroutines.flow.Flow

/**
 * Read-only part of a standard CRUD repository.
 *
 * @param ObjectType The type of objects stored in the repository
 * @param IdType The type of identifiers used to reference stored objects
 */
interface ReadCRUDRepo<ObjectType, IdType> : Repo {
    /**
     * Returns a paginated list of all objects in the repository.
     *
     * @param pagination Pagination parameters (page number and size)
     * @return [PaginationResult] containing objects for the requested page
     */
    suspend fun getByPagination(pagination: Pagination): PaginationResult<ObjectType>

    /**
     * Returns a paginated list of all IDs in the repository.
     *
     * @param pagination Pagination parameters (page number and size)
     * @return [PaginationResult] containing IDs for the requested page
     */
    suspend fun getIdsByPagination(pagination: Pagination): PaginationResult<IdType>

    /**
     * Returns the object associated with the given [id], or null if not found.
     *
     * @param id The identifier of the object to retrieve
     * @return The object with the given [id], or null if absent
     */
    suspend fun getById(id: IdType): ObjectType?

    /**
     * Returns true if an object with the given [id] exists in the repository.
     *
     * @param id The identifier to check
     * @return true if the object exists, false otherwise
     */
    suspend fun contains(id: IdType): Boolean

    /**
     * Returns all objects in the repository as a map of ID to object.
     * Default implementation iterates all pages using [getIdsByPagination] and [getById].
     *
     * @return Map of all [IdType] to [ObjectType] entries in the repository
     */
    suspend fun getAll(): Map<IdType, ObjectType> = getAllWithCurrentPaging(maxPagePagination()) {
        getIdsByPagination(it).let {
            it.changeResultsUnchecked(
                it.results.mapNotNull { it to (getById(it) ?: return@mapNotNull null) }
            )
        }
    }.toMap()

    /**
     * Returns the total count of objects stored in the repository.
     *
     * @return Total number of objects
     */
    suspend fun count(): Long
}
typealias ReadStandardCRUDRepo<ObjectType, IdType> = ReadCRUDRepo<ObjectType, IdType>

/**
 * Type alias representing a pair of ID and updated value, used in batch update operations.
 *
 * @param IdType The type of the identifier
 * @param ValueType The type of the input value
 */
typealias UpdatedValuePair<IdType, ValueType> = Pair<IdType, ValueType>

/**
 * Returns the ID component of an [UpdatedValuePair].
 */
val <IdType> UpdatedValuePair<IdType, *>.id
    get() = first

/**
 * Returns the value component of an [UpdatedValuePair].
 */
val <ValueType> UpdatedValuePair<*, ValueType>.value
    get() = second

/**
 * Write part of a standard CRUD repository.
 * Provides create, update, and delete operations with reactive flows for change notifications.
 *
 * @param ObjectType The type of objects stored in the repository
 * @param IdType The type of identifiers used to reference stored objects
 * @param InputValueType The type of input data used to create or update objects
 */
interface WriteCRUDRepo<ObjectType, IdType, InputValueType> : Repo {
    /**
     * Flow that emits each newly created object after a successful [create] call.
     */
    val newObjectsFlow: Flow<ObjectType>

    /**
     * Flow that emits each updated object after a successful [update] call.
     */
    val updatedObjectsFlow: Flow<ObjectType>

    /**
     * Flow that emits the ID of each deleted object after a successful [deleteById] call.
     */
    val deletedObjectsIdsFlow: Flow<IdType>

    /**
     * Creates new objects from the given list of input values.
     * Successfully created objects must be emitted via [newObjectsFlow].
     *
     * @param values List of input values to create objects from
     * @return List of created [ObjectType] instances
     */
    suspend fun create(values: List<InputValueType>): List<ObjectType>

    /**
     * Updates the object identified by [id] with the given [value].
     * Successfully updated object must be emitted via [updatedObjectsFlow].
     *
     * @param id The identifier of the object to update
     * @param value The new input value
     * @return The updated [ObjectType], or null if the object was not found
     */
    suspend fun update(id: IdType, value: InputValueType): ObjectType?

    /**
     * Batch-updates objects using the given list of ID-value pairs.
     * Successfully updated objects must be emitted via [updatedObjectsFlow].
     *
     * @param values List of [UpdatedValuePair] entries mapping IDs to new input values
     * @return List of successfully updated [ObjectType] instances
     */
    suspend fun update(values: List<UpdatedValuePair<IdType, InputValueType>>): List<ObjectType>

    /**
     * Deletes objects with the given list of IDs.
     * Successfully deleted IDs must be emitted via [deletedObjectsIdsFlow].
     *
     * @param ids List of identifiers of objects to delete
     */
    suspend fun deleteById(ids: List<IdType>)
}
typealias WriteStandardCRUDRepo<ObjectType, IdType, InputValueType> = WriteCRUDRepo<ObjectType, IdType, InputValueType>

/**
 * Mirrors [WriteCRUDRepo.newObjectsFlow] under the name [onNewObjects] for consistency with KV repos naming.
 */
val <ObjectType> WriteCRUDRepo<ObjectType, *, *>.onNewObjects: Flow<ObjectType>
    get() = newObjectsFlow

/**
 * Mirrors [WriteCRUDRepo.updatedObjectsFlow] under the name [onUpdatedObjects] for consistency with KV repos naming.
 */
val <ObjectType> WriteCRUDRepo<ObjectType, *, *>.onUpdatedObjects: Flow<ObjectType>
    get() = updatedObjectsFlow

/**
 * Mirrors [WriteCRUDRepo.deletedObjectsIdsFlow] under the name [onDeletedObjectsIds] for consistency with KV repos naming.
 */
val <IdType> WriteCRUDRepo<*, IdType, *>.onDeletedObjectsIds: Flow<IdType>
    get() = deletedObjectsIdsFlow

/**
 * Vararg overload of [WriteCRUDRepo.create] for convenience.
 *
 * @param values Input values to create objects from
 * @return List of created [ObjectType] instances
 */
suspend fun <ObjectType, IdType, InputValueType> WriteCRUDRepo<ObjectType, IdType, InputValueType>.create(
    vararg values: InputValueType
): List<ObjectType> = create(values.toList())

/**
 * Vararg overload of [WriteCRUDRepo.update] for convenience.
 *
 * @param values ID-value pairs to update
 * @return List of successfully updated [ObjectType] instances
 */
suspend fun <ObjectType, IdType, InputValueType> WriteCRUDRepo<ObjectType, IdType, InputValueType>.update(
    vararg values: UpdatedValuePair<IdType, InputValueType>
): List<ObjectType> = update(values.toList())

/**
 * Vararg overload of [WriteCRUDRepo.deleteById] for convenience.
 *
 * @param ids Identifiers of objects to delete
 */
suspend fun <ObjectType, IdType, InputValueType> WriteCRUDRepo<ObjectType, IdType, InputValueType>.deleteById(
    vararg ids: IdType
) = deleteById(ids.toList())

/**
 * Full CRUD repository combining read and write capabilities.
 *
 * @param ObjectType The type of objects stored in the repository
 * @param IdType The type of identifiers used to reference stored objects
 * @param InputValueType The type of input data used to create or update objects
 */
interface CRUDRepo<ObjectType, IdType, InputValueType> : ReadCRUDRepo<ObjectType, IdType>,
    WriteCRUDRepo<ObjectType, IdType, InputValueType>
typealias StandardCRUDRepo<ObjectType, IdType, InputValueType> = CRUDRepo<ObjectType, IdType, InputValueType>

/**
 * Delegate-based implementation of [CRUDRepo] that composes separate read and write delegates.
 *
 * @param ObjectType The type of objects stored in the repository
 * @param IdType The type of identifiers used to reference stored objects
 * @param InputValueType The type of input data used to create or update objects
 * @param readDelegate Delegate providing all [ReadCRUDRepo] operations
 * @param writeDelegate Delegate providing all [WriteCRUDRepo] operations
 */
class DelegateBasedCRUDRepo<ObjectType, IdType, InputValueType>(
    readDelegate: ReadCRUDRepo<ObjectType, IdType>,
    writeDelegate: WriteCRUDRepo<ObjectType, IdType, InputValueType>
) : CRUDRepo<ObjectType, IdType, InputValueType>,
    ReadCRUDRepo<ObjectType, IdType> by readDelegate,
    WriteCRUDRepo<ObjectType, IdType, InputValueType> by writeDelegate
