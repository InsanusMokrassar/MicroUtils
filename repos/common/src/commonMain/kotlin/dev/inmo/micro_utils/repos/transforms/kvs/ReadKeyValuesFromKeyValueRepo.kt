package dev.inmo.micro_utils.repos.transforms.kvs

import dev.inmo.micro_utils.pagination.FirstPagePagination
import dev.inmo.micro_utils.pagination.Pagination
import dev.inmo.micro_utils.pagination.PaginationResult
import dev.inmo.micro_utils.pagination.changeResultsUnchecked
import dev.inmo.micro_utils.pagination.emptyPaginationResult
import dev.inmo.micro_utils.pagination.utils.doForAllWithNextPaging
import dev.inmo.micro_utils.pagination.utils.optionallyReverse
import dev.inmo.micro_utils.pagination.utils.paginate
import dev.inmo.micro_utils.repos.ReadKeyValueRepo
import dev.inmo.micro_utils.repos.ReadKeyValuesRepo

open class ReadKeyValuesFromKeyValueRepo<Key, Value, ValuesIterable : Iterable<Value>>(
    private val original: ReadKeyValueRepo<Key, ValuesIterable>
) : ReadKeyValuesRepo<Key, Value> {
    override suspend fun get(
        k: Key,
        pagination: Pagination,
        reversed: Boolean
    ): PaginationResult<Value> {
        val iterable = original.get(k) ?: return emptyPaginationResult(pagination)
        val paginated = iterable.paginate(
            pagination.optionallyReverse(iterable.count(), reversed)
        )
        return if (reversed) {
            paginated.changeResultsUnchecked(paginated.results.reversed())
        } else {
            paginated
        }
    }

    override suspend fun keys(
        pagination: Pagination,
        reversed: Boolean
    ): PaginationResult<Key> = original.keys(pagination, reversed)

    override suspend fun count(): Long = original.count()

    override suspend fun count(k: Key): Long = original.get(k) ?.count() ?.toLong() ?: 0L

    override suspend fun contains(k: Key, v: Value): Boolean = original.get(k) ?.contains(v) == true

    override suspend fun contains(k: Key): Boolean = original.contains(k)

    override suspend fun keys(
        v: Value,
        pagination: Pagination,
        reversed: Boolean
    ): PaginationResult<Key> {
        val keys = mutableSetOf<Key>()

        doForAllWithNextPaging(FirstPagePagination(count().toInt())) {
            original.keys(it).also {
                it.results.forEach {
                    if (contains(it, v)) {
                        keys.add(it)
                    }
                }
            }
        }

        val paginated = keys.paginate(
            pagination.optionallyReverse(keys.count(), reversed)
        )
        return if (reversed) {
            paginated.changeResultsUnchecked(paginated.results.reversed())
        } else {
            paginated
        }
    }
}
