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
        var savedVersion = proxy.getTableVersion(tableName)
        if (savedVersion == null) {
            proxy.database.onCreate()
            proxy.updateTableVersion(tableName, version)
        } else {
            while (savedVersion != null && savedVersion < version) {
                val newVersion = savedVersion + 1

                proxy.database.onUpdate(savedVersion, newVersion)

                proxy.updateTableVersion(tableName, newVersion)
                savedVersion = newVersion
            }
        }
    }
}