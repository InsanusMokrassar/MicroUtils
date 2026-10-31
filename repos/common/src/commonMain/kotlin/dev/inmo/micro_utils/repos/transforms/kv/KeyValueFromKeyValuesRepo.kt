package dev.inmo.micro_utils.repos.transforms.kv

import dev.inmo.micro_utils.pagination.FirstPagePagination
import dev.inmo.micro_utils.pagination.Pagination
import dev.inmo.micro_utils.pagination.PaginationResult
import dev.inmo.micro_utils.pagination.changeResults
import dev.inmo.micro_utils.pagination.changeResultsUnchecked
import dev.inmo.micro_utils.pagination.utils.doForAllWithNextPaging
import dev.inmo.micro_utils.pagination.utils.optionallyReverse
import dev.inmo.micro_utils.pagination.utils.paginate
import dev.inmo.micro_utils.repos.KeyValueRepo
import dev.inmo.micro_utils.repos.KeyValuesRepo
import dev.inmo.micro_utils.repos.ReadKeyValueRepo
import dev.inmo.micro_utils.repos.ReadKeyValuesRepo
import dev.inmo.micro_utils.repos.transforms.kvs.ReadKeyValuesFromKeyValueRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.merge

/**
 * Full read-write adapter that exposes a [KeyValuesRepo] as a [KeyValueRepo] mapping each key to a [List] of values.
 * Extends [ReadKeyValueFromKeyValuesRepo] with write operations delegated to the underlying [KeyValuesRepo].
 * [onNewValue] merges [KeyValuesRepo.onNewValue] and [KeyValuesRepo.onValueRemoved] and emits the updated list per key;
 * [onValueRemoved] mirrors [KeyValuesRepo.onDataCleared].
 *
 * @param Key The type of keys
 * @param Value The type of individual values in the one-to-many repo
 * @param original The underlying [KeyValuesRepo] to delegate operations to
 */
open class KeyValueFromKeyValuesRepo<Key, Value>(
    private val original: KeyValuesRepo<Key, Value>
) : KeyValueRepo<Key, List<Value>>, ReadKeyValueFromKeyValuesRepo<Key, Value>(original) {
    override val onNewValue: Flow<Pair<Key, List<Value>>> = merge(
        original.onNewValue,
        original.onValueRemoved
    ).mapNotNull {
        it.first to (get(it.first) ?: return@mapNotNull null)
    }
    override val onValueRemoved: Flow<Key> = original.onDataCleared

    override suspend fun unset(toUnset: List<Key>) {
        toUnset.forEach {
            original.clear(it)
        }
    }

    override suspend fun set(toSet: Map<Key, List<Value>>) {
        original.set(toSet)
    }
}
