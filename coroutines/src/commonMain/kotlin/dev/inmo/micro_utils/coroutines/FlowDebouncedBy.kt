package dev.inmo.micro_utils.coroutines

import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.jvm.JvmInline
import kotlin.time.Duration

@JvmInline
private value class DebouncedByData<T>(
    val millisToData: Pair<Long, T>
)

/**
 * Debounces a [Flow] with per-marker timeout control. Values with the same marker will be debounced independently.
 * For each marker, only the last value within the timeout period will be emitted.
 *
 * @param T The type of values emitted by the flow
 * @param timeout A function that determines the debounce timeout in milliseconds for each value
 * @param markerFactory A function that produces a marker for each value. Values with the same marker are debounced together
 * @return A [Flow] that emits debounced values
 */
fun <T> Flow<T>.debouncedBy(timeout: (T) -> Long, markerFactory: (T) -> Any?): Flow<T> = channelFlow {
    val jobs = mutableMapOf<Any?, Job>()
    val mutex = Mutex()
    subscribe(this) {
        mutex.withLock {
            val marker = markerFactory(it)
            lateinit var job: Job
            job = async {
                delay(timeout(it))
                mutex.withLock {
                    if (jobs[marker] === job) {
                        this@channelFlow.send(it)
                        jobs.remove(marker)
                    }
                }
            }
            jobs[marker] ?.cancel()
            jobs[marker] = job
        }
    }
}

/**
 * Debounces a [Flow] with a fixed timeout in milliseconds and per-marker control.
 * Values with the same marker will be debounced independently.
 *
 * @param T The type of values emitted by the flow
 * @param timeout The debounce timeout in milliseconds
 * @param markerFactory A function that produces a marker for each value. Values with the same marker are debounced together
 * @return A [Flow] that emits debounced values
 */
fun <T> Flow<T>.debouncedBy(timeout: Long, markerFactory: (T) -> Any?): Flow<T> = debouncedBy({ timeout }, markerFactory)

/**
 * Debounces a [Flow] with a fixed timeout as [Duration] and per-marker control.
 * Values with the same marker will be debounced independently.
 *
 * @param T The type of values emitted by the flow
 * @param timeout The debounce timeout as a [Duration]
 * @param markerFactory A function that produces a marker for each value. Values with the same marker are debounced together
 * @return A [Flow] that emits debounced values
 */
fun <T> Flow<T>.debouncedBy(timeout: Duration, markerFactory: (T) -> Any?): Flow<T> = debouncedBy({ timeout.inWholeMilliseconds }, markerFactory)
