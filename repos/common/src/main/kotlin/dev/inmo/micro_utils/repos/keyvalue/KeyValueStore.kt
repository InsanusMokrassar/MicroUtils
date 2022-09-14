package dev.inmo.micro_utils.repos.keyvalue

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import dev.inmo.micro_utils.pagination.*
import dev.inmo.micro_utils.pagination.utils.paginate
import dev.inmo.micro_utils.pagination.utils.reverse
import dev.inmo.micro_utils.repos.KeyValueRepo
import kotlinx.coroutines.flow.*

private val cache = HashMap<String, KeyValueStore<*>>()

fun <T : Any> Context.keyValueStore(
    name: String = "default",
    cacheValues: Boolean = false
): KeyValueRepo<String, T> {
    @Suppress("UNCHECKED_CAST")
    return cache.getOrPut(name) {
        KeyValueStore<T>(c = this, preferencesName = name, useCache = cacheValues)
    } as KeyValueStore<T>
}

class KeyValueStore<T : Any> internal constructor (
    c: Context,
    preferencesName: String,
    useCache: Boolean = false
) : SharedPreferences.OnSharedPreferenceChangeListener, KeyValueRepo<String, T> {
    private val sharedPreferences = c.getSharedPreferences(preferencesName, Context.MODE_PRIVATE)

    private val cachedData = if (useCache) {
        mutableMapOf<String, Any>()
    } else {
        null
    }

    private val onNewValueChannel = MutableSharedFlow<Pair<String, T>>()
    private val _onValueRemovedFlow = MutableSharedFlow<String>()

    override val onNewValue: Flow<Pair<String, T>> = onNewValueChannel.asSharedFlow()
    override val onValueRemoved: Flow<String> = _onValueRemovedFlow.asSharedFlow()

    init {
        cachedData ?.let {
            for ((key, value) in sharedPreferences.all) {
                if (value != null) {
                    cachedData[key] = value
                }
            }
            sharedPreferences.registerOnSharedPreferenceChangeListener(this)
        }
    }

    override fun onSharedPreferenceChanged(sp: SharedPreferences, key: String) {
        val value = sp.all[key]
        cachedData ?: return
        if (value != null) {
            cachedData[key] = value
        } else {
            cachedData.remove(key)
        }
    }

    override suspend fun get(k: String): T? {
        @Suppress("UNCHECKED_CAST")
        return (cachedData ?. get(k) ?: sharedPreferences.all[k]) as? T
    }

    override suspend fun values(pagination: Pagination, reversed: Boolean): PaginationResult<T> {
        val resultPagination = if (reversed) pagination.reverse(count()) else pagination
        return sharedPreferences.all.values.paginate(
            resultPagination
        ).let {
            it.changeResultsUnchecked(
                it.results.map {
                    @Suppress("UNCHECKED_CAST")
                    it as T
                }.let { if (reversed) it.reversed() else it }
            )
        }
    }

    override suspend fun keys(pagination: Pagination, reversed: Boolean): PaginationResult<String> {
        val resultPagination = if (reversed) pagination.reverse(count()) else pagination
        return sharedPreferences.all.keys.paginate(
            resultPagination
        ).let {
            it.changeResultsUnchecked(
                it.results.let { if (reversed) it.reversed() else it }
            )
        }
    }

    override suspend fun keys(v: T, pagination: Pagination, reversed: Boolean): PaginationResult<String> {
        val resultPagination = if (reversed) pagination.reverse(count()) else pagination
        return sharedPreferences.all.mapNotNull { (k, value) -> if (value == v) k else null }.paginate(
            resultPagination
        ).let {
            it.changeResultsUnchecked(
                it.results.let { if (reversed) it.reversed() else it }
            )
        }
    }

    override suspend fun contains(key: String): Boolean = sharedPreferences.contains(key)

    override suspend fun count(): Long = sharedPreferences.all.size.toLong()

    override suspend fun set(toSet: Map<String, T>) {
        sharedPreferences.edit {
            for ((k, v) in toSet) {
                when(v) {
                    is Int -> putInt(k, v)
                    is Long -> putLong(k, v)
                    is Float -> putFloat(k, v)
                    is String -> putString(k, v)
                    is Boolean -> putBoolean(k, v)
                    is Set<*> -> putStringSet(k, v.map { (it as? String) ?: it.toString() }.toSet())
                    else -> error(
                        "Currently supported only primitive types and set for SharedPreferences KeyValue repos"
                    )
                }
            }
        }
        for ((k, v) in toSet) {
            onNewValueChannel.emit(k to v)
        }
    }

    override suspend fun unset(toUnset: List<String>) {
        sharedPreferences.edit {
            for (item in toUnset) {
                remove(item)
            }
        }
        for (it in toUnset) {
            _onValueRemovedFlow.emit(it)
        }
    }

    override suspend fun unsetWithValues(toUnset: List<T>) {
        val keysToRemove = sharedPreferences.all.mapNotNull { if (it.value in toUnset) it.key else null }
        sharedPreferences.edit {
            keysToRemove.map {
                remove(it)
            }
        }
        keysToRemove.forEach {
            _onValueRemovedFlow.emit(it)
        }
    }

    companion object {
        operator fun <T : Any> invoke(
            context: Context,
            name: String = "default",
            cacheValues: Boolean = false
        ) = context.keyValueStore<T>(name, cacheValues)
    }
}

inline fun <T : Any> SharedPreferencesKeyValueRepo(
    context: Context,
    name: String = "default",
    cacheValues: Boolean = false
) = context.keyValueStore<T>(name, cacheValues)

typealias KeyValueSPRepo<T> = KeyValueStore<T>
