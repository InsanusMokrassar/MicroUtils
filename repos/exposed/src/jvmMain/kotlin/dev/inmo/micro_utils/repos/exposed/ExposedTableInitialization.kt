package dev.inmo.micro_utils.repos.exposed

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SchemaUtils.addMissingColumnsStatements
import org.jetbrains.exposed.sql.SchemaUtils.checkMappingConsistence
import org.jetbrains.exposed.sql.SchemaUtils.createStatements
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction

/**
 * Code in this function mostly duplicates Exposed [SchemaUtils.createMissingTablesAndColumns]. It made due to deprecation
 * status of the last one and potential lost of it in future updates.
 *
 * Code doing:
 *
 * * Creating missed tables
 * * Altering missed tables (where possible)
 * * Calculate problems with [checkMappingConsistence] and add them in execution too
 *
 * All changes made in [transaction] with [database] as its `db` argument
 */
fun initTablesInTransaction(vararg tables: Table, database: Database, inBatch: Boolean = false, withLogs: Boolean = true) {
    transaction(database) {
        fun <R> logTimeSpent(message: String, withLogs: Boolean, block: () -> R): R {
            return if (withLogs) {
                val start = System.currentTimeMillis()
                val answer = block()
                exposedLogger.info(message + " took " + (System.currentTimeMillis() - start) + "ms")
                answer
            } else {
                block()
            }
        }
        fun Transaction.execStatements(inBatch: Boolean, statements: List<String>) {
            if (inBatch) {
                execInBatch(statements)
            } else {
                for (statement in statements) {
                    exec(statement)
                }
            }
        }
        with(TransactionManager.current()) {
            db.dialect.resetCaches()
            val createStatements = logTimeSpent("Preparing create tables statements", withLogs) {
                createStatements(*tables)
            }
            logTimeSpent("Executing create tables statements", withLogs) {
                execStatements(inBatch, createStatements)
                commit()
            }

            val alterStatements = logTimeSpent("Preparing alter table statements", withLogs) {
                addMissingColumnsStatements(tables = tables, withLogs)
            }
            logTimeSpent("Executing alter table statements", withLogs) {
                execStatements(inBatch, alterStatements)
                commit()
            }
            val executedStatements = createStatements + alterStatements
            logTimeSpent("Checking mapping consistence", withLogs) {
                val modifyTablesStatements = checkMappingConsistence(
                    tables = tables,
                    withLogs
                ).filter { it !in executedStatements }
                execStatements(inBatch, modifyTablesStatements)
                commit()
            }
            db.dialect.resetCaches()
        }
    }
}

fun Table.initTable(database: Database, inBatch: Boolean, withLogs: Boolean) {
    initTablesInTransaction(this, database = database, inBatch = inBatch, withLogs = withLogs)
}
fun Table.initTable(database: Database) {
    initTable(database = database, inBatch = false, withLogs = true)
}

fun <T> T.initTable() where T: ExposedRepo, T: Table = initTable(database)
