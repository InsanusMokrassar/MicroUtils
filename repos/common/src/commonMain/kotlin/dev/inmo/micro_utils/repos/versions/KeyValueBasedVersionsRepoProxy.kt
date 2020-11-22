package dev.inmo.micro_utils.repos.versions

import dev.inmo.micro_utils.repos.StandardKeyValueRepo
import dev.inmo.micro_utils.repos.set

class KeyValueBasedVersionsRepoProxy<T>(
    private val keyValueStore: StandardKeyValueRepo<String, Int>,
    override val database: T
) : StandardVersionsRepoProxy<T> {
    override suspend fun getTableVersion(tableName: String): Int? = keyValueStore.get(tableName)

    override suspend fun updateTableVersion(tableName: String, version: Int) { keyValueStore.set(tableName, version) }
}
