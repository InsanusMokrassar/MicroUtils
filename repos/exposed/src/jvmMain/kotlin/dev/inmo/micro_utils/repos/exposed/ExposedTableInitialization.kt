package dev.inmo.micro_utils.repos.exposed

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

fun Table.initTable(database: Database) {
    transaction(database) { SchemaUtils.createMissingTablesAndColumns(this@initTable) }
}

fun <T> T.initTable() where T: ExposedRepo, T: Table = initTable(database)
