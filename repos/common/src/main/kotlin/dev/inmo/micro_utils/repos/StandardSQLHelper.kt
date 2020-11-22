package dev.inmo.micro_utils.repos

import android.content.Context
import android.database.DatabaseErrorHandler
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import dev.inmo.micro_utils.coroutines.safely
import dev.inmo.micro_utils.repos.versions.*
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

private data class CallbackContinuationPair<T> (
    val callback: suspend SQLiteDatabase.() -> T,
    val continuation: Continuation<T>
) {
    suspend fun SQLiteDatabase.execute() {
        safely(
            {
                continuation.resumeWithException(it)
            }
        ) {
            continuation.resume(callback())
        }
    }
}

class StandardSQLHelper(
    context: Context,
    name: String,
    factory: SQLiteDatabase.CursorFactory? = null,
    version: Int = 1,
    errorHandler: DatabaseErrorHandler? = null
) {
    val sqlOpenHelper = object : SQLiteOpenHelper(context, name, factory, version, errorHandler) {
        override fun onCreate(db: SQLiteDatabase?) {}

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}
    }
    val versionsRepo: VersionsRepo<SQLiteOpenHelper> by lazy {
        StandardVersionsRepo(AndroidSQLStandardVersionsRepoProxy(sqlOpenHelper))
    }

    suspend fun <T> writableTransaction(block: suspend SQLiteDatabase.() -> T): T = sqlOpenHelper.writableTransaction(block)

    suspend fun <T> readableTransaction(block: suspend SQLiteDatabase.() -> T): T = sqlOpenHelper.readableTransaction(block)
}

suspend fun <T> SQLiteOpenHelper.writableTransaction(block: suspend SQLiteDatabase.() -> T): T {
    return writableDatabase.transaction(block)
}

suspend fun <T> SQLiteOpenHelper.readableTransaction(block: suspend SQLiteDatabase.() -> T): T {
    return readableDatabase.transaction(block)
}
