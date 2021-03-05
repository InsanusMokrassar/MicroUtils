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

fun <T> SQLiteOpenHelper.blockingWritableTransaction(block: SQLiteDatabase.() -> T): T {
    return writableDatabase.blockingTransaction(block)
}
fun <T> SQLiteOpenHelper.blockingReadableTransaction(block: SQLiteDatabase.() -> T): T {
    return readableDatabase.blockingTransaction(block)
}

fun <T> StandardSQLHelper.blockingWritableTransaction(block: SQLiteDatabase.() -> T): T {
    return sqlOpenHelper.blockingWritableTransaction(block)
}
fun <T> StandardSQLHelper.blockingReadableTransaction(block: SQLiteDatabase.() -> T): T {
    return sqlOpenHelper.blockingReadableTransaction(block)
}

suspend fun <T> SQLiteOpenHelper.writableTransaction(block: suspend SQLiteDatabase.() -> T): T {
    return writableDatabase.transaction(block)
}

suspend fun <T> SQLiteOpenHelper.readableTransaction(block: suspend SQLiteDatabase.() -> T): T {
    return readableDatabase.transaction(block)
}
