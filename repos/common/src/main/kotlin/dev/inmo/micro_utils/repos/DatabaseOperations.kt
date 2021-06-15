package dev.inmo.micro_utils.repos

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

fun createTableQuery(
    tableName: String,
    vararg columnsToTypes: Pair<String, ColumnType>
) = "create table $tableName (${columnsToTypes.joinToString(", ") { "${it.first} ${it.second}" }});"

fun SQLiteDatabase.createTable(
    tableName: String,
    vararg columnsToTypes: Pair<String, ColumnType>,
    onInit: (SQLiteDatabase.() -> Unit)? = null
): Boolean {
    val existing = rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='$tableName'", null).use {
        it.count > 0
    }
    return if (existing) {
        false
        // TODO:: add upgrade opportunity
    } else {
        execSQL(createTableQuery(tableName, *columnsToTypes))
        onInit ?.invoke(this)
        true
    }
}

/**
 * @throws IllegalArgumentException
 */
fun Cursor.getString(columnName: String): String = getString(
    getColumnIndexOrThrow(columnName)
)

/**
 * @throws IllegalArgumentException
 */
fun Cursor.getShort(columnName: String): Short = getShort(
    getColumnIndexOrThrow(columnName)
)

/**
 * @throws IllegalArgumentException
 */
fun Cursor.getLong(columnName: String): Long = getLong(
    getColumnIndexOrThrow(columnName)
)

/**
 * @throws IllegalArgumentException
 */
fun Cursor.getInt(columnName: String): Int = getInt(
    getColumnIndexOrThrow(columnName)
)

/**
 * @throws IllegalArgumentException
 */
fun Cursor.getDouble(columnName: String): Double = getDouble(
    getColumnIndexOrThrow(columnName)
)

fun SQLiteDatabase.select(
    table: String,
    columns: Array<String>? = null,
    selection: String? = null,
    selectionArgs: Array<String>? = null,
    groupBy: String? = null,
    having: String? = null,
    orderBy: String? = null,
    limit: String? = null
) = query(
    table, columns, selection, selectionArgs, groupBy, having, orderBy, limit
)

fun SQLiteDatabase.selectDistinct(
    table: String,
    columns: Array<String>? = null,
    selection: String? = null,
    selectionArgs: Array<String>? = null,
    groupBy: String? = null,
    having: String? = null,
    orderBy: String? = null,
    limit: String? = null
) = query(
    true, table, columns, selection, selectionArgs, groupBy, having, orderBy, limit
)

fun makePlaceholders(count: Int): String {
    return (0 until count).joinToString { "?" }
}

fun makeStringPlaceholders(count: Int): String {
    return (0 until count).joinToString { "\"?\"" }
}
