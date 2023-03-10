package dev.inmo.micro_utils.repos.transforms.kv

import dev.inmo.micro_utils.pagination.FirstPagePagination
import dev.inmo.micro_utils.pagination.Pagination
import dev.inmo.micro_utils.pagination.PaginationResult
import dev.inmo.micro_utils.pagination.changeResults
import dev.inmo.micro_utils.pagination.changeResultsUnchecked
import dev.inmo.micro_utils.pagination.utils.doForAllWithNextPaging
import dev.inmo.micro_utils.pagination.utils.optionallyReverse
import dev.inmo.micro_utils.pagination.utils.paginate
import dev.inmo.micro_utils.repos.ReadCRUDRepo
import dev.inmo.micro_utils.repos.ReadKeyValueRepo
import dev.inmo.micro_utils.repos.ReadKeyValuesRepo
import dev.inmo.micro_utils.repos.pagination.getAll
import dev.inmo.micro_utils.repos.transforms.kvs.ReadKeyValuesFromKeyValueRepo
import kotlin.jvm.JvmInline

@JvmInline
value class ReadKeyValueFromCRUDRepo<Key, Value>(
    private val original: ReadCRUDRepo<Value, Key>
) : ReadKeyValueRepo<Key, Value> {
    override suspend fun get(k: Key): Value? = original.getById(k)

    override suspend fun values(pagination: Pagination, reversed: Boolean): PaginationResult<Value> = original.getByPagination(
        pagination.optionallyReverse(count(), reversed)
    ).let {
        if (reversed) {
            it.changeResultsUnchecked(it.results.reversed())
        } else {
            it
        }
    }

    override suspend fun keys(pagination: Pagination, reversed: Boolean): PaginationResult<Key> = original.getIdsByPagination(
        pagination.optionallyReverse(count(), reversed)
    ).let {
        if (reversed) {
            it.changeResultsUnchecked(it.results.reversed())
        } else {
            it
        }
    }

    override suspend fun getAll(): Map<Key, Value> = original.getAll()

    override suspend fun count(): Long = original.count()

    override suspend fun contains(key: Key): Boolean = original.contains(key)
}
