package dev.inmo.micro_utils.repos.transforms.crud

import dev.inmo.micro_utils.pagination.Pagination
import dev.inmo.micro_utils.pagination.PaginationResult
import dev.inmo.micro_utils.repos.ReadCRUDRepo
import dev.inmo.micro_utils.repos.ReadKeyValueRepo

open class ReadCRUDFromKeyValueRepo<RegisteredType, IdType>(
    protected open val original: ReadKeyValueRepo<IdType, RegisteredType>
) : ReadCRUDRepo<RegisteredType, IdType> {
    override suspend fun contains(id: IdType): Boolean = original.contains(id)

    override suspend fun count(): Long = original.count()

    override suspend fun getAll(): Map<IdType, RegisteredType> = original.getAll()

    override suspend fun getByPagination(pagination: Pagination): PaginationResult<RegisteredType> = original.values(pagination)

    override suspend fun getIdsByPagination(pagination: Pagination): PaginationResult<IdType> = original.keys(pagination)

    override suspend fun getById(id: IdType): RegisteredType? = original.get(id)
}
