package dev.inmo.micro_utils.repos

import android.database.sqlite.SQLiteDatabase
import dev.inmo.micro_utils.coroutines.safely
import kotlinx.coroutines.*
import java.util.concurrent.Executors
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

class TransactionContext(
    val databaseContext: CoroutineContext
): CoroutineContext.Element {
    override val key: CoroutineContext.Key<TransactionContext> = TransactionContext

    companion object : CoroutineContext.Key<TransactionContext>
}

suspend fun <T> SQLiteDatabase.transaction(block: suspend SQLiteDatabase.() -> T): T {
    return coroutineContext[TransactionContext] ?.let {
        withContext(it.databaseContext) {
            block()
        }
    } ?: Executors.newSingleThreadExecutor().asCoroutineDispatcher().let { context ->
        withContext(TransactionContext(context) + context) {
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
