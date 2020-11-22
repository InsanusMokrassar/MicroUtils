package dev.inmo.micro_utils.repos

import android.database.sqlite.SQLiteDatabase
import dev.inmo.micro_utils.coroutines.safely
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

object InTransaction: CoroutineContext.Element, CoroutineContext.Key<InTransaction> {
    override val key: CoroutineContext.Key<InTransaction> = InTransaction
}

suspend fun <T> SQLiteDatabase.transaction(block: suspend SQLiteDatabase.() -> T): T {
    return when {
        coroutineContext[InTransaction] == InTransaction -> {
            block()
        }
        else -> {
            withContext(InTransaction) {
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
