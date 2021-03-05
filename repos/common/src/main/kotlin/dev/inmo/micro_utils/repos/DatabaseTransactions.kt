package dev.inmo.micro_utils.repos

import android.database.sqlite.SQLiteDatabase
import dev.inmo.micro_utils.coroutines.safely
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.concurrent.Executors
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

private object ContextsPool {
    private val contexts = mutableListOf<CoroutineContext>()
    private val mutex = Mutex(locked = false)
    private val freeContexts = mutableListOf<CoroutineContext>()

    suspend fun acquireContext(): CoroutineContext {
        return mutex.withLock {
            freeContexts.removeFirstOrNull() ?: Executors.newSingleThreadExecutor().asCoroutineDispatcher().also {
                contexts.add(it)
            }
        }
    }

    suspend fun freeContext(context: CoroutineContext) {
        return mutex.withLock {
            if (context in contexts && context !in freeContexts) {
                freeContexts.add(context)
            }
        }
    }

    suspend fun <T> use(block: suspend (CoroutineContext) -> T): T = acquireContext().let {
        try {
            safely {
                block(it)
            }
        } finally {
            freeContext(it)
        }
    }
}

class TransactionContext(
    val databaseContext: CoroutineContext
): CoroutineContext.Element {
    override val key: CoroutineContext.Key<TransactionContext> = TransactionContext

    companion object : CoroutineContext.Key<TransactionContext>
}

suspend fun <T> SQLiteDatabase.transaction(block: suspend SQLiteDatabase.() -> T): T {
    coroutineContext[TransactionContext] ?.let {
        return withContext(it.databaseContext) {
            block()
        }
    }
    return ContextsPool.use { context ->
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

inline fun <T> SQLiteDatabase.inlineTransaction(crossinline block: SQLiteDatabase.() -> T): T {
    return when {
        inTransaction() -> block()
        else -> {
            beginTransaction()
            try {
                block().also { setTransactionSuccessful() }
            } finally {
                endTransaction()
            }
        }
    }
}

fun <T> SQLiteDatabase.blockingTransaction(block: SQLiteDatabase.() -> T): T = inlineTransaction(block)
