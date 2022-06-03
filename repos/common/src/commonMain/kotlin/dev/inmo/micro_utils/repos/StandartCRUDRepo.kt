package dev.inmo.micro_utils.repos

import dev.inmo.micro_utils.pagination.Pagination
import dev.inmo.micro_utils.pagination.PaginationResult
import kotlinx.coroutines.flow.Flow

interface ReadStandardCRUDRepo<ObjectType, IdType> : Repo {
    suspend fun getByPagination(pagination: Pagination): PaginationResult<ObjectType>
    suspend fun getById(id: IdType): ObjectType?
    suspend fun contains(id: IdType): Boolean
    suspend fun count(): Long
}
typealias ReadCRUDRepo<ObjectType, IdType> = ReadStandardCRUDRepo<ObjectType, IdType>

typealias UpdatedValuePair<IdType, ValueType> = Pair<IdType, ValueType>
val <IdType> UpdatedValuePair<IdType, *>.id
    get() = first
val <ValueType> UpdatedValuePair<*, ValueType>.value
    get() = second

interface WriteStandardCRUDRepo<ObjectType, IdType, InputValueType> : Repo {
    val newObjectsFlow: Flow<ObjectType>
    val updatedObjectsFlow: Flow<ObjectType>
    val deletedObjectsIdsFlow: Flow<IdType>

    suspend fun create(values: List<InputValueType>): List<ObjectType>
    suspend fun update(id: IdType, value: InputValueType): ObjectType?
    suspend fun update(values: List<UpdatedValuePair<IdType, InputValueType>>): List<ObjectType>
    suspend fun deleteById(ids: List<IdType>)
}
typealias WriteCRUDRepo<ObjectType, IdType, InputValueType> = WriteStandardCRUDRepo<ObjectType, IdType, InputValueType>

suspend fun <ObjectType, IdType, InputValueType> WriteStandardCRUDRepo<ObjectType, IdType, InputValueType>.create(
    vararg values: InputValueType
): List<ObjectType> = create(values.toList())
suspend fun <ObjectType, IdType, InputValueType> WriteStandardCRUDRepo<ObjectType, IdType, InputValueType>.update(
    vararg values: UpdatedValuePair<IdType, InputValueType>
): List<ObjectType> = update(values.toList())
suspend fun <ObjectType, IdType, InputValueType> WriteStandardCRUDRepo<ObjectType, IdType, InputValueType>.deleteById(
    vararg ids: IdType
) = deleteById(ids.toList())

interface StandardCRUDRepo<ObjectType, IdType, InputValueType> : ReadStandardCRUDRepo<ObjectType, IdType>,
    WriteStandardCRUDRepo<ObjectType, IdType, InputValueType>
typealias CRUDRepo<ObjectType, IdType, InputValueType> = StandardCRUDRepo<ObjectType, IdType, InputValueType>

class DelegateBasedStandardCRUDRepo<ObjectType, IdType, InputValueType>(
    readDelegate: ReadStandardCRUDRepo<ObjectType, IdType>,
    writeDelegate: WriteStandardCRUDRepo<ObjectType, IdType, InputValueType>
) : StandardCRUDRepo<ObjectType, IdType, InputValueType>,
    ReadStandardCRUDRepo<ObjectType, IdType> by readDelegate,
    WriteStandardCRUDRepo<ObjectType, IdType, InputValueType> by writeDelegate
