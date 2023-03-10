package dev.inmo.micro_utils.repos.transforms.kv

import dev.inmo.micro_utils.pagination.FirstPagePagination
import dev.inmo.micro_utils.pagination.Pagination
import dev.inmo.micro_utils.pagination.PaginationResult
import dev.inmo.micro_utils.pagination.changeResults
import dev.inmo.micro_utils.pagination.changeResultsUnchecked
import dev.inmo.micro_utils.pagination.utils.doForAllWithNextPaging
import dev.inmo.micro_utils.pagination.utils.optionallyReverse
import dev.inmo.micro_utils.pagination.utils.paginate
import dev.inmo.micro_utils.repos.ReadKeyValueRepo
import dev.inmo.micro_utils.repos.ReadKeyValuesRepo
import dev.inmo.micro_utils.repos.transforms.kvs.ReadKeyValuesFromKeyValueRepo

open class ReadKeyValueFromKeyValuesRepo<Key, Value>(
    private val original: ReadKeyValuesRepo<Key, Value>
) : ReadKeyValueRepo<Key, List<Value>> {
    override suspend fun get(k: Key): List<Value>? = original.getAll(k)

    override suspend fun values(
        pagination: Pagination,
        reversed: Boolean
    ): PaginationResult<List<Value>> {
        val keys = keys(pagination, reversed)
        return keys.changeResults(
            keys.results.mapNotNull {
                get(it)
            }
        )
    }

    override suspend fun keys(pagination: Pagination, reversed: Boolean): PaginationResult<Key> {
        return original.keys(pagination, reversed)
    }

    override suspend fun count(): Long {
        return original.count()
    }

    override suspend fun contains(key: Key): Boolean {
        return original.contains(key)
    }

    override suspend fun getAll(): Map<Key, List<Value>> = original.getAll()

    override suspend fun keys(v: List<Value>, pagination: Pagination, reversed: Boolean): PaginationResult<Key> {
        val keys = mutableSetOf<Key>()

        doForAllWithNextPaging(FirstPagePagination(count().toInt())) {
            original.keys(it).also {
                it.results.forEach {
                    val values = get(it) ?: return@forEach
                    if (values.containsAll(v) && v.containsAll(values)) {
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
