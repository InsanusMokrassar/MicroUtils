package dev.inmo.micro_utils.repos.exposed.keyvalue

import dev.inmo.micro_utils.repos.KeyValueRepo
import kotlinx.coroutines.flow.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.statements.*
import org.jetbrains.exposed.sql.transactions.transaction

abstract class AbstractExposedKeyValueRepo<Key, Value>(
    override val database: Database,
    tableName: String? = null
) : KeyValueRepo<Key, Value>, AbstractExposedReadKeyValueRepo<Key, Value>(
    database,
    tableName
) {
    protected val _onNewValue = MutableSharedFlow<Pair<Key, Value>>()
    protected val _onValueRemoved = MutableSharedFlow<Key>()

    override val onNewValue: Flow<Pair<Key, Value>> = _onNewValue.asSharedFlow()
    override val onValueRemoved: Flow<Key> = _onValueRemoved.asSharedFlow()

    protected abstract fun update(k: Key, v: Value, it: UpdateBuilder<Int>)
    protected abstract fun insertKey(k: Key, v: Value, it: InsertStatement<Number>)

    protected open fun insert(k: Key, v: Value, it: InsertStatement<Number>) {
        insertKey(k, v, it)
        update(k, v, it as UpdateBuilder<Int>)
    }

    override suspend fun set(toSet: Map<Key, Value>) {
        transaction(database) {
            toSet.mapNotNull { (k, v) ->
                if (update({ selectById(k) }) { update(k, v, it as UpdateBuilder<Int>) } > 0) {
                    k to v
                } else {
                    val inserted = insert {
                        insert(k, v, it)
                    }.getOrNull(keyColumn) != null
                    if (inserted) {
                        k to v
                    } else {
                        null
                    }
                }
            }
        }.forEach {
            _onNewValue.emit(it)
        }
    }

    override suspend fun unset(toUnset: List<Key>) {
        transaction(database) {
            toUnset.mapNotNull { item ->
                if (deleteWhere { selectById(it, item) } > 0) {
                    item
                } else {
                    null
                }
            }
        }.forEach {
            _onValueRemoved.emit(it)
        }
    }

    override suspend fun unsetWithValues(toUnset: List<Value>) {
        transaction(database) {
            toUnset.flatMap {
                val keys = selectAll().where { selectByValue(it) }.mapNotNull { it.asKey }
                deleteWhere { selectByIds(it, keys) }
                keys
            }
        }.distinct().forEach {
            _onValueRemoved.emit(it)
        }
    }

    override suspend fun clear() {
        transaction(database) {
            val keys = selectAll().map { it.asKey }

            deleteAll()

            keys
        }.also {
            it.forEach {
                _onValueRemoved.emit(it)
            }
        }
    }
}
