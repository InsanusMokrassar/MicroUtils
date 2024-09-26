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

fun <T> Flow<T>.debouncedBy(timeout: Long, markerFactory: (T) -> Any?): Flow<T> = debouncedBy({ timeout }, markerFactory)
fun <T> Flow<T>.debouncedBy(timeout: Duration, markerFactory: (T) -> Any?): Flow<T> = debouncedBy({ timeout.inWholeMilliseconds }, markerFactory)
