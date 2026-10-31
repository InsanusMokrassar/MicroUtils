package dev.inmo.micro_utils.repos.versions

import dev.inmo.micro_utils.repos.Repo

/**
 * Proxy interface providing low-level access to a versioned database [T].
 * Implementations store and retrieve per-table version numbers using a backing storage.
 *
 * @param T The type of the underlying database or storage object
 */
interface StandardVersionsRepoProxy<T> : Repo {
    /**
     * The underlying database or storage object used for version tracking.
     */
    val database: T

    /**
     * Returns the current version number for the given [tableName], or null if no version is stored.
     *
     * @param tableName Name of the table whose version to retrieve
     * @return Stored version number, or null if the table has not been versioned yet
     */
    suspend fun getTableVersion(tableName: String): Int?

    /**
     * Persists the given [version] number for the given [tableName].
     *
     * @param tableName Name of the table whose version to update
     * @param version New version number to store
     */
    suspend fun updateTableVersion(tableName: String, version: Int)
}

/**
 * Standard implementation of [VersionsRepo] that delegates version storage to a [StandardVersionsRepoProxy].
 * On [setTableVersion]: calls [StandardVersionsRepoProxy.database].[onCreate] if the table has no version yet,
 * then iterates [onUpdate] for each version step until the target [version] is reached.
 *
 * @param T The type of the underlying database or storage object
 * @param proxy The [StandardVersionsRepoProxy] used to read and write version numbers
 */
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
