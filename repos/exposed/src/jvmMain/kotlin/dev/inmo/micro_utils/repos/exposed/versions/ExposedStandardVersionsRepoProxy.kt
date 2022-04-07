package dev.inmo.micro_utils.repos.exposed.versions

import dev.inmo.micro_utils.repos.exposed.ExposedRepo
import dev.inmo.micro_utils.repos.exposed.initTable
import dev.inmo.micro_utils.repos.versions.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

/**
 * Use this method to create [StandardVersionsRepo] based on [Database] with [ExposedStandardVersionsRepoProxy] as
 * [StandardVersionsRepoProxy]
 */
@Suppress("NOTHING_TO_INLINE")
inline fun versionsRepo(database: Database): VersionsRepo<Database> = StandardVersionsRepo(
    ExposedStandardVersionsRepoProxy(database)
)

class ExposedStandardVersionsRepoProxy(
    override val database: Database
) : StandardVersionsRepoProxy<Database>, Table("ExposedVersionsProxy"), ExposedRepo {
    val tableNameColumn = text("tableName")
    val tableVersionColumn = integer("tableVersion")

    init {
        initTable()
    }

    override suspend fun getTableVersion(tableName: String): Int? = transaction(database) {
        select { tableNameColumn.eq(tableName) }.limit(1).firstOrNull() ?.getOrNull(tableVersionColumn)
    }

    override suspend fun updateTableVersion(tableName: String, version: Int) {
        transaction(database) {
            val updated = update(
                { tableNameColumn.eq(tableName) }
            ) {
                it[tableVersionColumn] = version
            } > 0
            if (!updated) {
                insert {
                    it[tableNameColumn] = tableName
                    it[tableVersionColumn] = version
                }
            }
        }
    }

}
