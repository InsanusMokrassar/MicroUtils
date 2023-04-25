package dev.inmo.micro_utils.repos.transforms.kvs

import dev.inmo.micro_utils.pagination.FirstPagePagination
import dev.inmo.micro_utils.pagination.utils.doForAllWithNextPaging
import dev.inmo.micro_utils.repos.KeyValueRepo
import dev.inmo.micro_utils.repos.KeyValuesRepo
import dev.inmo.micro_utils.repos.set
import dev.inmo.micro_utils.repos.unset
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlin.js.JsName
import kotlin.jvm.JvmName

open class KeyValuesFromKeyValueRepo<Key, Value, ValuesIterable : Iterable<Value>>(
    private val original: KeyValueRepo<Key, ValuesIterable>,
    private val listToValuesIterable: suspend (List<Value>) -> ValuesIterable
) : KeyValuesRepo<Key, Value>, ReadKeyValuesFromKeyValueRepo<Key, Value, ValuesIterable>(original) {
    private val _onNewValue = MutableSharedFlow<Pair<Key, Value>>()
    private val _onValueRemoved = MutableSharedFlow<Pair<Key, Value>>()
    override val onNewValue: Flow<Pair<Key, Value>> = _onNewValue.asSharedFlow()
    override val onValueRemoved: Flow<Pair<Key, Value>> = _onValueRemoved.asSharedFlow()
    override val onDataCleared: Flow<Key> = original.onValueRemoved

    override suspend fun clearWithValue(v: Value) {
        val keys = mutableSetOf<Key>()

        doForAllWithNextPaging(FirstPagePagination(count().toInt())) {
            original.keys(it).also {
                it.results.forEach {
                    if (contains(it, v)) {
                        keys.add(it)
                    }
                }
            }
        }

        original.unset(keys.toList())
    }

    override suspend fun clear(k: Key) {
        original.unset(k)
    }

    override suspend fun remove(toRemove: Map<Key, List<Value>>) {
        original.set(
            toRemove.mapNotNull { (k, removing) ->
                val exists = original.get(k) ?: return@mapNotNull null
                k to listToValuesIterable(exists - removing).also {
                    if (it.firstOrNull() == null) {
                        original.unset(k)
                        return@mapNotNull null
                    }
                }
            }.toMap()
        )
        toRemove.forEach { (k, v) ->
            v.forEach {
                _onValueRemoved.emit(k to it)
            }
        }
    }

    override suspend fun removeWithValue(v: Value) {
        val toRemove = mutableMapOf<Key, List<Value>>()

        doForAllWithNextPaging {
            original.keys(it).also {
                it.results.forEach {
                    val data = original.get(it) ?: return@forEach

                    if (v in data) {
                        toRemove[it] = listOf(v)
                    }
                }
            }
        }

        remove(toRemove)
    }

    override suspend fun add(toAdd: Map<Key, List<Value>>) {
        original.set(
            toAdd.mapNotNull { (k, adding) ->
                val exists = original.get(k) ?: emptyList()
                k to listToValuesIterable(exists + adding)
            }.toMap()
        )
        toAdd.forEach { (k, v) ->
            v.forEach {
                _onNewValue.emit(k to it)
            }
        }
    }

}
