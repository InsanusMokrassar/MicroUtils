package dev.inmo.micro_utils.repos.versions

import dev.inmo.micro_utils.repos.KeyValueRepo
import dev.inmo.micro_utils.repos.set

/**
 * [StandardVersionsRepoProxy] implementation backed by a [KeyValueRepo] mapping table names to version numbers.
 * Stores and retrieves per-table version integers using [keyValueStore] with table names as keys.
 *
 * @param T The type of the underlying database or storage object
 * @param keyValueStore [KeyValueRepo] used to persist table-name-to-version mappings
 * @param database The underlying database or storage object exposed via [StandardVersionsRepoProxy.database]
 */
class KeyValueBasedVersionsRepoProxy<T>(
    private val keyValueStore: KeyValueRepo<String, Int>,
    override val database: T
) : StandardVersionsRepoProxy<T> {
    override suspend fun getTableVersion(tableName: String): Int? = keyValueStore.get(tableName)

    override suspend fun updateTableVersion(tableName: String, version: Int) { keyValueStore.set(tableName, version) }
}
