package dev.inmo.micro_utils.repos

import android.database.sqlite.SQLiteDatabase
import dev.inmo.micro_utils.coroutines.safely
import kotlinx.coroutines.withContext

suspend fun <T> SQLiteDatabase.transaction(block: suspend SQLiteDatabase.() -> T): T {
    return withContext(DatabaseCoroutineContext) {
        when {
            inTransaction() -> {
                block()
            }
            else -> {
                beginTransaction()
                safely(
                    {
                        endTransaction()
                        throw it
                    }
                ) {
                    block().also {
                        setTransactionSuccessful()
                        endTransaction()
                    }
                }
            }
        }
    }
}

inline fun <T> SQLiteDatabase.inlineTransaction(block: SQLiteDatabase.() -> T): T {
    return when {
        inTransaction() -> block()
        else -> {
            beginTransaction()
            try {
                block().also {
                    setTransactionSuccessful()
                }
            } finally {
                endTransaction()
            }
        }
    }
}
