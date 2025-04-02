package dev.inmo.micro_utils.transactions

typealias TransactionDSLRollbackLambda = suspend (Throwable) -> Unit
class TransactionsDSL internal constructor() {
    internal val rollbackActions = ArrayList<TransactionDSLRollbackLambda>()

    internal fun addRollbackAction(rollbackAction: TransactionDSLRollbackLambda) {
        rollbackActions.add(rollbackAction)
    }
}
class RollbackContext<T> internal constructor (
    val actionResult: T,
    val error: Throwable
)

/**
 * Calls [action] and, if it succeeded - saving [rollback] action for future usage for cases when some other
 * action or even main one throwing an error
 *
 * @param rollback Will be called if
 */
suspend fun <T> TransactionsDSL.rollableBackOperation(
    rollback: suspend RollbackContext<T>.() -> Unit,
    action: suspend () -> T
): T {
    return runCatching { action() }
        .onSuccess {
            addRollbackAction { e ->
                val context = RollbackContext(it, e)
                context.rollback()
            }
        }
        .getOrThrow()
}

/**
 * Starts transaction with opportunity to add actions [rollableBackOperation]. How to use:
 *
 * ```kotlin
 * doSuspendTransaction {
 *     println("start of action")
 *
 *     withRollback({ // it - result of action
 *         // some rollback action
 *     }) {
 *         // Some action with rollback
 *     }
 *
 *     withRollback({
 *          repository.delete(it) // it - result of createSomething, if it completes successfully
 *     }) {
 *          repository.createSomething()
 *     }
 *
 *     withRollback({
 *         // will not be triggered due to error in action
 *     }) {
 *         error("It is just a simple error") // Will trigger rolling back previously successfully completed actions
 *     }
 * }
 * ```
 *
 * @param onRollbackStepError Will be called if rollback action throwing some error
 */
suspend fun <T> doSuspendTransaction(
    onRollbackStepError: suspend (Throwable) -> Unit = { },
    block: suspend TransactionsDSL.() -> T
): Result<T> {
    val transactionsDSL = TransactionsDSL()

    return runCatching {
        transactionsDSL.block()
    }.onFailure { e ->
        for (i in transactionsDSL.rollbackActions.lastIndex downTo 0) {
            val rollbackAction = transactionsDSL.rollbackActions[i]
            runCatching {
                rollbackAction.invoke(e)
            }.onFailure { ee ->
                onRollbackStepError(ee)
            }
        }
    }
}
