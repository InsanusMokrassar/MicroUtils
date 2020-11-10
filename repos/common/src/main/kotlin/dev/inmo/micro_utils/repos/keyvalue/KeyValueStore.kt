package dev.inmo.micro_utils.repos.keyvalue

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import dev.inmo.micro_utils.pagination.Pagination
import dev.inmo.micro_utils.pagination.PaginationResult
import dev.inmo.micro_utils.pagination.utils.paginate
import dev.inmo.micro_utils.pagination.utils.reverse
import dev.inmo.micro_utils.repos.StandardKeyValueRepo
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

private val cache = HashMap<String, KeyValueStore<*>>()

fun <T : Any> Context.keyValueStore(
    name: String = "default",
    cacheValues: Boolean = false
): StandardKeyValueRepo<String, T> {
    return cache.getOrPut(name) {
        KeyValueStore<T>(this, name, cacheValues)
    } as KeyValueStore<T>
}

class KeyValueStore<T : Any> internal constructor (
    c: Context,
    preferencesName: String,
    useCache: Boolean = false
) : SharedPreferences.OnSharedPreferenceChangeListener, StandardKeyValueRepo<String, T> {
    private val sharedPreferences = c.getSharedPreferences(preferencesName, Context.MODE_PRIVATE)

    private val cachedData = if (useCache) {
        mutableMapOf<String, Any>()
    } else {
        null
    }

    private val onNewValueChannel = BroadcastChannel<Pair<String, T>>(Channel.BUFFERED)
    private val onValueRemovedChannel = BroadcastChannel<String>(Channel.BUFFERED)

    override val onNewValue: Flow<Pair<String, T>> = onNewValueChannel.asFlow()
    override val onValueRemoved: Flow<String> = onValueRemovedChannel.asFlow()

    init {
        cachedData ?.let {
            sharedPreferences.all.forEach {
                if (it.value != null) {
                    cachedData[it.key] = it.value as Any
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
        return (cachedData ?. get(k) ?: sharedPreferences.all[k]) as? T
    }

    override suspend fun values(pagination: Pagination, reversed: Boolean): PaginationResult<T> {
        val resultPagination = if (reversed) pagination.reverse(count()) else pagination
        return sharedPreferences.all.values.paginate(
            resultPagination
        ).let {
            PaginationResult(
                it.page,
                it.pagesNumber,
                it.results.map { it as T }.let { if (reversed) it.reversed() else it },
                it.size
            )
        }
    }

    override suspend fun keys(pagination: Pagination, reversed: Boolean): PaginationResult<String> {
        val resultPagination = if (reversed) pagination.reverse(count()) else pagination
        return sharedPreferences.all.keys.paginate(
            resultPagination
        ).let {
            PaginationResult(
                it.page,
                it.pagesNumber,
                it.results.let { if (reversed) it.reversed() else it },
                it.size
            )
        }
    }

    override suspend fun contains(key: String): Boolean = sharedPreferences.contains(key)

    override suspend fun count(): Long = sharedPreferences.all.size.toLong()

    override suspend fun set(toSet: Map<String, T>) {
        sharedPreferences.edit {
            toSet.forEach { (k, v) ->
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
        toSet.forEach { (k, v) ->
            onNewValueChannel.send(k to v)
        }
    }

    override suspend fun unset(toUnset: List<String>) {
        sharedPreferences.edit {
            toUnset.forEach { remove(it) }
        }
        toUnset.forEach { onValueRemovedChannel.send(it) }
    }
}
