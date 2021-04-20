package dev.inmo.micro_utils.repos.versions

import dev.inmo.micro_utils.repos.Repo

interface StandardVersionsRepoProxy<T> : Repo {
    val database: T

    suspend fun getTableVersion(tableName: String): Int?
    suspend fun updateTableVersion(tableName: String, version: Int)
}

class StandardVersionsRepo<T>(
    private val proxy: StandardVersionsRepoProxy<T>
) : VersionsRepo<T> {
    override suspend fun setTableVersion(
        tableName: String,
        version: Int,
        onCreate: suspend T.() -> Unit,
        onUpdate: suspend T.(from: Int, to: Int) -> Unit
    ) {
        var currentVersion = proxy.getTableVersion(tableName)
        if (currentVersion == null) {
            proxy.database.onCreate()
        }
        while (currentVersion == null || currentVersion < version) {
            val oldVersion = currentVersion ?: 0
            currentVersion = oldVersion + 1
            proxy.database.onUpdate(oldVersion, currentVersion)

            proxy.updateTableVersion(tableName, currentVersion)
        }
    }
}