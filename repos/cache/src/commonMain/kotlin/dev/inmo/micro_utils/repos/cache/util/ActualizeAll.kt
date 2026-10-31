package dev.inmo.micro_utils.repos.cache.util

import dev.inmo.micro_utils.coroutines.SmartRWLocker
import dev.inmo.micro_utils.coroutines.withWriteLock
import dev.inmo.micro_utils.repos.*
import kotlinx.serialization.Serializable
import kotlin.js.JsName
import kotlin.jvm.JvmName

/**
 * `invalidate`/`actualizeAll` clearing mode. Declare when data in original repo will be cleared
 */
@Serializable
sealed interface ActualizeAllClearMode {
    /**
     * Instruct user of this mode to clear internal data __before load__ of external data
     */
    @Serializable
    data object BeforeLoad : ActualizeAllClearMode
    /**
     * Instruct user of this mode to clear internal data __after load__ of external data and __before set__ of internal data
     */
    @Serializable
    data object BeforeSet : ActualizeAllClearMode
    /**
     * Instruct user of this mode to never clear internal data
     */
    @Serializable
    data object Never : ActualizeAllClearMode
}

suspend inline fun <K, V> KeyValueRepo<K, V>.actualizeAllWithClearBeforeLoad(
    getAll: () -> Map<K, V>
) {
    clear()
    val newData = getAll()
    set(newData)
}

suspend inline fun <K, V> KeyValueRepo<K, V>.actualizeAllWithClearBeforeSet(
    getAll: () -> Map<K, V>
) {
    val newData = getAll()
    clear()
    set(newData)
}

suspend inline fun <K, V> KeyValueRepo<K, V>.actualizeAllWithoutClear(
    getAll: () -> Map<K, V>
) {
    val newData = getAll()
    set(newData)
}

@JvmName("actualizeAllWithClearBeforeLoadWithLocker")
@JsName("actualizeAllWithClearBeforeLoadWithLocker")
suspend inline fun <K, V> KeyValueRepo<K, V>.actualizeAllWithClearBeforeLoad(
    locker: SmartRWLocker,
    getAll: () -> Map<K, V>
) {
    locker.withWriteLock {
        clear()
    }

    val newData = getAll()
    locker.withWriteLock {
        set(newData)
    }
}

@JvmName("actualizeAllWithClearBeforeSetWithLocker")
@JsName("actualizeAllWithClearBeforeSetWithLocker")
suspend inline fun <K, V> KeyValueRepo<K, V>.actualizeAllWithClearBeforeSet(
    locker: SmartRWLocker,
    getAll: () -> Map<K, V>
) {
    val newData = getAll()
    locker.withWriteLock {
        clear()
        set(newData)
    }
}

@JvmName("actualizeAllWithoutClearWithLocker")
@JsName("actualizeAllWithoutClearWithLocker")
suspend inline fun <K, V> KeyValueRepo<K, V>.actualizeAllWithoutClear(
    locker: SmartRWLocker,
    getAll: () -> Map<K, V>
) {
    val newData = getAll()
    locker.withWriteLock {
        set(newData)
    }
}

suspend inline fun <K, V> KeyValueRepo<K, V>.actualizeAllWithClearBeforeLoad(
    locker: SmartRWLocker? = null,
    getAll: () -> Map<K, V>
) {
    locker ?.let {
        actualizeAllWithClearBeforeLoad(locker = locker, getAll)
    } ?: actualizeAllWithClearBeforeLoad(getAll)
}

suspend inline fun <K, V> KeyValueRepo<K, V>.actualizeAllWithClearBeforeSet(
    locker: SmartRWLocker? = null,
    getAll: () -> Map<K, V>
) {
    locker ?.let {
        actualizeAllWithClearBeforeSet(locker = locker, getAll)
    } ?: actualizeAllWithClearBeforeSet(getAll)
}

suspend inline fun <K, V> KeyValueRepo<K, V>.actualizeAllWithoutClear(
    locker: SmartRWLocker? = null,
    getAll: () -> Map<K, V>
) {
    locker ?.let {
        actualizeAllWithoutClear(locker = locker, getAll)
    } ?: actualizeAllWithoutClear(getAll)
}

suspend inline fun <K, V> KeyValueRepo<K, V>.actualizeAll(
    locker: SmartRWLocker? = null,
    clearMode: ActualizeAllClearMode = ActualizeAllClearMode.BeforeSet,
    getAll: () -> Map<K, V>
) {
    when (clearMode) {
        ActualizeAllClearMode.BeforeLoad -> locker ?.let {
            actualizeAllWithClearBeforeLoad(locker = locker, getAll)
        } ?: actualizeAllWithClearBeforeLoad(getAll)
        ActualizeAllClearMode.BeforeSet -> locker ?.let {
            actualizeAllWithClearBeforeSet(locker = locker, getAll)
        } ?: actualizeAllWithClearBeforeSet(getAll)
        ActualizeAllClearMode.Never -> locker ?.let {
            actualizeAllWithoutClear(locker = locker, getAll)
        } ?: actualizeAllWithoutClear(getAll)
    }
}

suspend inline fun <K, V> KeyValueRepo<K, V>.actualizeAll(
    parentRepo: ReadKeyValueRepo<K, V>,
    locker: SmartRWLocker? = null,
    clearMode: ActualizeAllClearMode = ActualizeAllClearMode.BeforeSet,
) {
    actualizeAll(locker, clearMode) {
        parentRepo.getAll()
    }
}

suspend inline fun <K, V> KeyValueRepo<K, List<V>>.actualizeAll(
    parentRepo: ReadKeyValuesRepo<K, V>,
    locker: SmartRWLocker? = null,
    clearMode: ActualizeAllClearMode = ActualizeAllClearMode.BeforeSet,
) {
    actualizeAll(locker, clearMode) {
        parentRepo.getAll()
    }
}

suspend inline fun <K, V> KeyValueRepo<K, V>.actualizeAll(
    parentRepo: ReadCRUDRepo<V, K>,
    locker: SmartRWLocker? = null,
    clearMode: ActualizeAllClearMode = ActualizeAllClearMode.BeforeSet,
) {
    actualizeAll(locker, clearMode) {
        parentRepo.getAll()
    }
}
