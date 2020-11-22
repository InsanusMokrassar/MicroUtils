package dev.inmo.micro_utils.repos.versions

import dev.inmo.micro_utils.repos.Repo

/**
 * This interface has been created due to requirement to work with different versions of databases and make some
 * migrations between versions
 *
 * @param T It is a type of database, which will be used by this repo to retrieve current table version and update it
 */
interface VersionsRepo<T> : Repo {
    /**
     * By default, instance of this interface will check that version of table with name [tableName] is less than
     * [version] or is absent
     *
     * * In case if [tableName] didn't found, will be called [onCreate] and version of table will be set up to [version]
     * * In case if [tableName] have version less than parameter [version], it will increase version one-by-one
     * until database version will be equal to [version]
     *
     * @param version Current version of table
     * @param onCreate This callback will be called in case when table have no information about table
     * @param onUpdate This callback will be called after **iterative** changing of version. It is expected that parameter
     * "to" will always be greater than "from"
     */
    suspend fun setTableVersion(
        tableName: String,
        version: Int,
        onCreate: suspend T.() -> Unit,
        onUpdate: suspend T.(from: Int, to: Int) -> Unit
    )
}
