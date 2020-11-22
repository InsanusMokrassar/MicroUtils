package dev.inmo.micro_utils.repos.versions

import android.database.sqlite.SQLiteOpenHelper
import androidx.core.content.contentValuesOf
import dev.inmo.micro_utils.repos.*
import kotlinx.coroutines.runBlocking

/**
 * Will create [VersionsRepo] based on [SQLiteOpenHelper] with table inside of [database]
 */
@Suppress("NOTHING_TO_INLINE")
inline fun versionsRepo(database: SQLiteOpenHelper): VersionsRepo<SQLiteOpenHelper> = StandardVersionsRepo(
    AndroidSQLStandardVersionsRepoProxy(database)
)

class AndroidSQLStandardVersionsRepoProxy(
    override val database: SQLiteOpenHelper
) : StandardVersionsRepoProxy<SQLiteOpenHelper> {
    private val tableName: String = "AndroidSQLStandardVersionsRepo"
    private val tableNameColumnName = "tableName"
    private val tableVersionColumnName = "version"

    init {
        runBlocking(DatabaseCoroutineContext) {
            database.writableTransaction {
                createTable(
                    tableName,
                    tableNameColumnName to ColumnType.Text.NOT_NULLABLE,
                    tableVersionColumnName to ColumnType.Numeric.INTEGER()
                )
            }
        }
    }

    override suspend fun getTableVersion(tableName: String): Int? = database.writableTransaction {
        select(
            tableName,
            selection = "$tableNameColumnName=?",
            selectionArgs = arrayOf(tableName),
            limit = limitClause(1)
        ).use {
            if (it.moveToFirst()) {
                it.getInt(tableVersionColumnName)
            } else {
                null
            }
        }
    }

    override suspend fun updateTableVersion(tableName: String, version: Int) {
        database.writableTransaction {
            val updated = update(
                tableName,
                contentValuesOf(tableVersionColumnName to version),
                "$tableNameColumnName=?",
                arrayOf(version.toString())
            ) > 0
            if (!updated) {
                insert(tableName, null, contentValuesOf(tableNameColumnName to tableName, tableVersionColumnName to version))
            }
        }
    }
}
