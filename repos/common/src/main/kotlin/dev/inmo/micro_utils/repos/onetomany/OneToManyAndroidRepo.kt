package dev.inmo.micro_utils.repos.onetomany

import android.database.sqlite.SQLiteOpenHelper
import androidx.core.content.contentValuesOf
import dev.inmo.micro_utils.common.mapNotNullA
import dev.inmo.micro_utils.pagination.FirstPagePagination
import dev.inmo.micro_utils.pagination.Pagination
import dev.inmo.micro_utils.pagination.PaginationResult
import dev.inmo.micro_utils.pagination.createPaginationResult
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

class OneToManyAndroidRepo<Key, Value>(
    private val tableName: String,
    private val keySerializer: KSerializer<Key>,
    private val valueSerializer: KSerializer<Value>,
    private val helper: SQLiteOpenHelper
) : OneToManyKeyValueRepo<Key, Value> {
    private val _onNewValue: MutableSharedFlow<Pair<Key, Value>> = MutableSharedFlow()
    override val onNewValue: Flow<Pair<Key, Value>> = _onNewValue.asSharedFlow()
    private val _onValueRemoved: MutableSharedFlow<Pair<Key, Value>> = MutableSharedFlow()
    override val onValueRemoved: Flow<Pair<Key, Value>> = _onValueRemoved.asSharedFlow()
    private val _onDataCleared = MutableSharedFlow<Key>()
    override val onDataCleared: Flow<Key> = _onDataCleared.asSharedFlow()

    private val idColumnName = "id"
    private val valueColumnName = "value"

    private fun Key.asId() = internalSerialFormat.encodeToString(keySerializer, this)
    private fun Value.asValue() = internalSerialFormat.encodeToString(valueSerializer, this)
    private fun String.asValue(): Value = internalSerialFormat.decodeFromString(valueSerializer, this)
    private fun String.asKey(): Key = internalSerialFormat.decodeFromString(keySerializer, this)

    init {
        runBlocking(DatabaseCoroutineContext) {
            helper.writableTransaction {
                createTable(
                    tableName,
                    internalId to internalIdType,
                    idColumnName to ColumnType.Text.NOT_NULLABLE,
                    valueColumnName to ColumnType.Text.NULLABLE
                )
            }
        }
    }

    override suspend fun add(toAdd: Map<Key, List<Value>>) {
        val added = mutableListOf<Pair<Key, Value>>()
        helper.writableTransaction {
            toAdd.forEach { (k, values) ->
                values.forEach { v ->
                    insert(
                        tableName,
                        null,
                        contentValuesOf(
                            idColumnName to k.asId(),
                            valueColumnName to v.asValue()
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
        helper.writableTransaction {
            delete(tableName, "$idColumnName=?", arrayOf(k.asId()))
        }.also {
            if (it > 0) {
                _onDataCleared.emit(k)
            }
        }
    }

    override suspend fun contains(k: Key): Boolean = helper.readableTransaction {
        select(tableName, selection = "$idColumnName=?", selectionArgs = arrayOf(k.asId()), limit = FirstPagePagination(1).limitClause()).use {
            it.count > 0
        }
    }

    override suspend fun contains(k: Key, v: Value): Boolean = helper.readableTransaction {
        select(
            tableName,
            selection = "$idColumnName=? AND $valueColumnName=?",
            selectionArgs = arrayOf(k.asId(), v.asValue()),
            limit = FirstPagePagination(1).limitClause()
        ).use {
            it.count > 0
        }
    }

    override suspend fun count(): Long =helper.readableTransaction {
        select(
            tableName
        ).use {
            it.count
        }
    }.toLong()

    override suspend fun count(k: Key): Long = helper.readableTransaction {
        select(tableName, selection = "$idColumnName=?", selectionArgs = arrayOf(k.asId()), limit = FirstPagePagination(1).limitClause()).use {
            it.count
        }
    }.toLong()

    override suspend fun get(
        k: Key,
        pagination: Pagination,
        reversed: Boolean
    ): PaginationResult<Value> = count(k).let { count ->
        val resultPagination = pagination.let { if (reversed) pagination.reverse(count) else pagination }
        helper.readableTransaction {
            select(
                tableName,
                selection = "$idColumnName=?",
                selectionArgs = arrayOf(k.asId()),
                limit = resultPagination.limitClause()
            ).use { c ->
                mutableListOf<Value>().also {
                    if (c.moveToFirst()) {
                        do {
                            it.add(c.getString(valueColumnName).asValue())
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
        val resultPagination = pagination.let { if (reversed) pagination.reverse(count) else pagination }
        helper.readableTransaction {
            select(
                tableName,
                limit = resultPagination.limitClause()
            ).use { c ->
                mutableListOf<Key>().also {
                    if (c.moveToFirst()) {
                        do {
                            it.add(c.getString(idColumnName).asKey())
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
        helper.readableTransaction {
            select(
                tableName,
                selection = "$valueColumnName=?",
                selectionArgs = arrayOf(v.asValue()),
                limit = resultPagination.limitClause()
            ).use { c ->
                mutableListOf<Key>().also {
                    if (c.moveToFirst()) {
                        do {
                            it.add(c.getString(idColumnName).asKey())
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
        helper.writableTransaction {
            toRemove.flatMap { (k, vs) ->
                vs.mapNotNullA { v ->
                    if (delete(tableName, "$idColumnName=? AND $valueColumnName=?", arrayOf(k.asId(), v.asValue())) > 0) {
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
