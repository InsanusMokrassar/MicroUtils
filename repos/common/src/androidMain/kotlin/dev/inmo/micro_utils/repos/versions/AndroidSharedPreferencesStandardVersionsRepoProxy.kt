@file:Suppress("NOTHING_TO_INLINE")

package dev.inmo.micro_utils.repos.versions

import android.content.Context
import android.database.sqlite.SQLiteOpenHelper
import dev.inmo.micro_utils.repos.*
import dev.inmo.micro_utils.repos.keyvalue.keyValueStore

/**
 * Will create [VersionsRepo] based on [T], but versions will be stored in [KeyValueRepo]
 *
 * @receiver Will be used to create [KeyValueBasedVersionsRepoProxy] via [keyValueStore] and pass it to [StandardVersionsRepo]
 *
 * @see [KeyValueBasedVersionsRepoProxy]
 * @see [keyValueStore]
 */
inline fun <T> Context.versionsKeyValueRepo(
    database: T
): VersionsRepo<T> = StandardVersionsRepo(
    KeyValueBasedVersionsRepoProxy(
        keyValueStore("SPVersionsRepo"),
        database
    )
)
/**
 * Will create [VersionsRepo] based on [SQLiteOpenHelper], but versions will be stored in [KeyValueRepo]
 *
 * @receiver Will be used to create [KeyValueRepo] via [keyValueStore] and pass it to [StandardVersionsRepo]
 *
 * @see [keyValueStore]
 */
inline fun Context.versionsKeyValueRepoForSQL(
    database: SQLiteOpenHelper
) = versionsKeyValueRepo(database)

/**
 * Will create [VersionsRepo] based on [SQLiteOpenHelper], but versions will be stored in [KeyValueRepo]
 *
 * @param context Will be used to create [KeyValueRepo] via [keyValueStore] and pass it to [StandardVersionsRepo]
 *
 * @see [keyValueStore]
 */
inline fun versionsRepo(
    context: Context,
    database: SQLiteOpenHelper
) = context.versionsKeyValueRepoForSQL(database)
