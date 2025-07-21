package dev.inmo.micro_utils.coroutines

import dev.inmo.kslog.common.KSLog
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

private class SubscribeAsyncReceiver<T>(
    val scope: CoroutineScope,
    val logger: KSLog,
    output: suspend SubscribeAsyncReceiver<T>.(T) -> Unit
) {
    private val dataChannel: Channel<T> = Channel(Channel.UNLIMITED)
    val channel: SendChannel<T>
        get() = dataChannel

    init {
        scope.launchLoggingDropExceptions(logger = logger) {
            for (data in dataChannel) {
                output(data)
            }
        }
    }

    fun isEmpty(): Boolean = dataChannel.isEmpty
}

private sealed interface AsyncSubscriptionCommand<T, M> {
    suspend operator fun invoke(markersMap: MutableMap<M, SubscribeAsyncReceiver<T>>)
}
private data class AsyncSubscriptionCommandData<T, M>(
    val data: T,
    val scope: CoroutineScope,
    val markerFactory: suspend (T) -> M,
    val block: suspend (T) -> Unit,
    val logger: KSLog,
    val onEmpty: suspend (M) -> Unit
) : AsyncSubscriptionCommand<T, M> {
    override suspend fun invoke(markersMap: MutableMap<M, SubscribeAsyncReceiver<T>>) {
        val marker = markerFactory(data)
        markersMap.getOrPut(marker) {
            SubscribeAsyncReceiver(scope.LinkedSupervisorScope(), logger) {
                runCatchingLogging(logger = logger) {
                    block(it)
                }
                if (isEmpty()) {
                    onEmpty(marker)
                }
            }
        }.channel.send(data)
    }
}

private data class AsyncSubscriptionCommandClearReceiver<T, M>(
    val marker: M
) : AsyncSubscriptionCommand<T, M> {
    override suspend fun invoke(markersMap: MutableMap<M, SubscribeAsyncReceiver<T>>) {
        val receiver = markersMap[marker]
        if (receiver ?.isEmpty() == true) {
            markersMap.remove(marker)
            receiver.scope.cancel()
        }
    }
}

fun <T, M> Flow<T>.subscribeAsync(
    scope: CoroutineScope,
    markerFactory: suspend (T) -> M,
    logger:  KSLog = KSLog,
    block: suspend (T) -> Unit
): Job {
    val subscope = scope.LinkedSupervisorScope()
    val markersMap = mutableMapOf<M, SubscribeAsyncReceiver<T>>()
    val actor = subscope.actor<AsyncSubscriptionCommand<T, M>>(Channel.UNLIMITED) {
        it.invoke(markersMap)
    }

    val job = subscribeLoggingDropExceptions(subscope, logger = logger) { data ->
        val dataCommand = AsyncSubscriptionCommandData(
            data = data,
            scope = subscope,
            markerFactory = markerFactory,
            block = block,
            logger = logger
        ) { marker ->
            actor.send(
                AsyncSubscriptionCommandClearReceiver(marker)
            )
        }
        actor.send(dataCommand)
    }

    job.invokeOnCompletion { if (subscope.isActive) subscope.cancel() }

    return job
}

@Deprecated("Renamed", ReplaceWith("subscribeLoggingDropExceptionsAsync(scope, markerFactory, block = block)", "dev.inmo.micro_utils.coroutines.subscribeLoggingDropExceptionsAsync"))
fun <T, M> Flow<T>.subscribeSafelyAsync(
    scope: CoroutineScope,
    markerFactory: suspend (T) -> M,
    onException: ExceptionHandler<Unit> = defaultSafelyExceptionHandler,
    logger:  KSLog = KSLog,
    block: suspend (T) -> Unit
) = subscribeAsync(scope, markerFactory, logger) {
    safely(onException) {
        block(it)
    }
}

@Deprecated("Renamed", ReplaceWith("subscribeLoggingDropExceptionsAsync(scope, markerFactory, block = block)", "dev.inmo.micro_utils.coroutines.subscribeLoggingDropExceptionsAsync"))
fun <T, M> Flow<T>.subscribeSafelyWithoutExceptionsAsync(
    scope: CoroutineScope,
    markerFactory: suspend (T) -> M,
    onException: ExceptionHandler<T?> = defaultSafelyWithoutExceptionHandlerWithNull,
    block: suspend (T) -> Unit
) = subscribeAsync(scope, markerFactory) {
    safelyWithoutExceptions(onException) {
        block(it)
    }
}

fun <T, M> Flow<T>.subscribeLoggingDropExceptionsAsync(
    scope: CoroutineScope,
    markerFactory: suspend (T) -> M,
    logger:  KSLog = KSLog,
    block: suspend (T) -> Unit
) = subscribeAsync(scope, markerFactory, logger) {
    block(it)
}

@Deprecated("Renamed", ReplaceWith("subscribeLoggingDropExceptionsAsync(scope, markerFactory, logger, block = block)", "dev.inmo.micro_utils.coroutines.subscribeLoggingDropExceptionsAsync"))
fun <T, M> Flow<T>.subscribeSafelySkippingExceptionsAsync(
    scope: CoroutineScope,
    markerFactory: suspend (T) -> M,
    logger:  KSLog = KSLog,
    block: suspend (T) -> Unit
) = subscribeAsync(scope, markerFactory, logger) {
    safelyWithoutExceptions({ /* do nothing */}) {
        block(it)
    }
}
