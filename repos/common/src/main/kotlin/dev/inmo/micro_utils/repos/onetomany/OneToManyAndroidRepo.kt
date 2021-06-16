package dev.inmo.micro_utils.repos.onetomany

import android.database.sqlite.SQLiteOpenHelper
import androidx.core.content.contentValuesOf
import dev.inmo.micro_utils.common.mapNotNullA
import dev.inmo.micro_utils.pagination.*
import dev.inmo.micro_utils.pagination.utils.reverse
import dev.inmo.micro_utils.repos.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json

private val internalSerialFormat = Json {
    ignoreUnknownKeys = true
}

typealias KeyValuesAndroidRepo<Key, Value> = OneToManyAndroidRepo<Key, Value>

class OneToManyAndroidRepo<Key, Value>(
    private val tableName: String,
    private val keyAsString: Key.() -> String,
    private val valueAsString: Value.() -> String,
    private val keyFromString: String.() -> Key,
    private val valueFromString: String.() -> Value,
    private val helper: SQLiteOpenHelper
) : OneToManyKeyValueRepo<Key, Value> {
    private val _onNewValue: MutableSharedFlow<Pair<Key, Value>> = MutableSharedFlow()
    override val onNewValue: Flow<Pair<Key, Value>> = _onNewValue.asSharedFlow()
    private val _onValueRemoved: MutableSharedFlow<Pair<Key, Value>> = MutableSharedFlow()
    override val onValueRemoved: Flow<Pair<Key, Value>> = _onValueRemoved.asSharedFlow()
    private val _onDataCleared = MutableSharedFlow<Key>()
    override val onDataCleared: Flow<Key> = _onDataCleared.asSharedFlow()

    private val idColumnName = "id"
    private val idColumnArray = arrayOf(idColumnName)
    private val valueColumnName = "value"
    private val valueColumnArray = arrayOf(valueColumnName)

    init {
        helper.blockingWritableTransaction {
            createTable(
                tableName,
                internalId to internalIdType,
                idColumnName to ColumnType.Text.NOT_NULLABLE,
                valueColumnName to ColumnType.Text.NULLABLE
            )
        }
    }

    override suspend fun add(toAdd: Map<Key, List<Value>>) {
        val added = mutableListOf<Pair<Key, Value>>()
        helper.blockingWritableTransaction {
            for ((k, values) in toAdd) {
                values.forEach { v ->
                    val kAsString = k.keyAsString()
                    val vAsString = v.valueAsString()
                    val isThere = select(tableName,
                        null,
                        "$idColumnName=? AND $valueColumnName=?",
                        arrayOf(kAsString, vAsString),
                        limit = limitClause(1)
                    ).use { it.moveToFirst() }
                    if (isThere) {
                        return@forEach
                    }
                    insert(
                        tableName,
                        null,
                        contentValuesOf(
                            idColumnName to k.keyAsString(),
                            valueColumnName to v.valueAsString()
                        )
                    ).also {
                        if (it != -1L) {
                            added.add(k to v)
                        }
                    }
                }
            }
        }
        added.forEach { _onNewValue.emit(it) }
    }

    override suspend fun clear(k: Key) {
        helper.blockingWritableTransaction {
            delete(tableName, "$idColumnName=?", arrayOf(k.keyAsString()))
        }.also {
            if (it > 0) {
                _onDataCleared.emit(k)
            }
        }
    }

    override suspend fun set(toSet: Map<Key, List<Value>>) {
        val (clearedKeys, inserted) = helper.blockingWritableTransaction {
            toSet.mapNotNull { (k, _) ->
                if (delete(tableName, "$idColumnName=?", arrayOf(k.keyAsString())) > 0) {
                    k
                } else {
                    null
                }
            } to toSet.flatMap { (k, values) ->
                values.map { v ->
                    insert(
                        tableName,
                        null,
                        contentValuesOf(idColumnName to k.keyAsString(), valueColumnName to v.valueAsString())
                    )
                    k to v
                }
            }
        }
        clearedKeys.forEach { _onDataCleared.emit(it) }
        inserted.forEach { newPair -> _onNewValue.emit(newPair) }
    }

    override suspend fun contains(k: Key): Boolean = helper.blockingReadableTransaction {
        select(tableName, selection = "$idColumnName=?", selectionArgs = arrayOf(k.keyAsString()), limit = firstPageWithOneElementPagination.limitClause()).use {
            it.count > 0
        }
    }

    override suspend fun contains(k: Key, v: Value): Boolean = helper.blockingReadableTransaction {
        select(
            tableName,
            selection = "$idColumnName=? AND $valueColumnName=?",
            selectionArgs = arrayOf(k.keyAsString(), v.valueAsString()),
            limit = FirstPagePagination(1).limitClause()
        ).use {
            it.count > 0
        }
    }

    override suspend fun count(): Long = helper.blockingReadableTransaction {
        select(
            tableName
        ).use {
            it.count
        }
    }.toLong()

    override suspend fun count(k: Key): Long = helper.blockingReadableTransaction {
        selectDistinct(tableName, columns = valueColumnArray, selection = "$idColumnName=?", selectionArgs = arrayOf(k.keyAsString()), limit = FirstPagePagination(1).limitClause()).use {
            it.count
        }
    }.toLong()

    override suspend fun get(
        k: Key,
        pagination: Pagination,
        reversed: Boolean
    ): PaginationResult<Value> = count(k).let { count ->
        if (pagination.firstIndex >= count) {
            return@let emptyList<Value>().createPaginationResult(
                pagination,
                count
            )
        }
        val resultPagination = pagination.let { if (reversed) pagination.reverse(count) else pagination }
        helper.blockingReadableTransaction {
            select(
                tableName,
                valueColumnArray,
                selection = "$idColumnName=?",
                selectionArgs = arrayOf(k.keyAsString()),
                limit = resultPagination.limitClause()
            ).use { c ->
                mutableListOf<Value>().also {
                    if (c.moveToFirst()) {
                        do {
                            it.add(c.getString(valueColumnName).valueFromString())
                        } while (c.moveToNext())
                    }
                }
            }
        }.createPaginationResult(
            pagination,
            count
        )
    }

    override suspend fun keys(
        pagination: Pagination,
        reversed: Boolean
    ): PaginationResult<Key> = count().let { count ->
        if (pagination.firstIndex >= count) {
            return@let emptyList<Key>().createPaginationResult(
                pagination,
                count
            )
        }
        val resultPagination = pagination.let { if (reversed) pagination.reverse(count) else pagination }
        helper.blockingReadableTransaction {
            selectDistinct(
                tableName,
                idColumnArray,
                limit = resultPagination.limitClause()
            ).use { c ->
                mutableListOf<Key>().also {
                    if (c.moveToFirst()) {
                        do {
                            it.add(c.getString(idColumnName).keyFromString())
                        } while (c.moveToNext())
                    }
                }
            }
        }.createPaginationResult(
            pagination,
            count
        )
    }

    override suspend fun keys(
        v: Value,
        pagination: Pagination,
        reversed: Boolean
    ): PaginationResult<Key> = count().let { count ->
        val resultPagination = pagination.let { if (reversed) pagination.reverse(count) else pagination }
        helper.blockingReadableTransaction {
            selectDistinct(
                tableName,
                idColumnArray,
                selection = "$valueColumnName=?",
                selectionArgs = arrayOf(v.valueAsString()),
                limit = resultPagination.limitClause()
            ).use { c ->
                mutableListOf<Key>().also {
                    if (c.moveToFirst()) {
                        do {
                            it.add(c.getString(idColumnName).keyFromString())
                        } while (c.moveToNext())
                    }
                }
            }
        }.createPaginationResult(
            pagination,
            count
        )
    }

    override suspend fun remove(toRemove: Map<Key, List<Value>>) {
        helper.blockingWritableTransaction {
            toRemove.flatMap { (k, vs) ->
                vs.mapNotNullA { v ->
                    if (delete(tableName, "$idColumnName=? AND $valueColumnName=?", arrayOf(k.keyAsString(), v.valueAsString())) > 0) {
                        k to v
                    } else {
                        null
                    }
                }
            }
        }.forEach { (k, v) ->
            _onValueRemoved.emit(k to v)
        }
    }
}

fun <Key, Value> OneToManyAndroidRepo(
    tableName: String,
    keySerializer: KSerializer<Key>,
    valueSerializer: KSerializer<Value>,
    helper: SQLiteOpenHelper
) = OneToManyAndroidRepo(
    tableName,
    { internalSerialFormat.encodeToString(keySerializer, this) },
    { internalSerialFormat.encodeToString(valueSerializer, this) },
    { internalSerialFormat.decodeFromString(keySerializer, this) },
    { internalSerialFormat.decodeFromString(valueSerializer, this) },
    helper
)

fun <Key, Value> KeyValuesAndroidRepo(
    tableName: String,
    keySerializer: KSerializer<Key>,
    valueSerializer: KSerializer<Value>,
    helper: SQLiteOpenHelper
) = OneToManyAndroidRepo(tableName, keySerializer, valueSerializer, helper)
