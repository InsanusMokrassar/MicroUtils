package dev.inmo.micro_utils.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.coroutines.cancellation.CancellationException

private sealed interface AccumulatorFlowStep<T>
private data class DataRetrievedAccumulatorFlowStep<T>(val data: T) : AccumulatorFlowStep<T>
private data class SubscribeAccumulatorFlowStep<T>(val channel: Channel<T>) : AccumulatorFlowStep<T>
private data class UnsubscribeAccumulatorFlowStep<T>(val channel: Channel<T>) : AccumulatorFlowStep<T>

/**
 * This [Flow] will have behaviour very similar to [SharedFlow], but there are several differences:
 *
 * * All unhandled by [FlowCollector] data will not be removed from [AccumulatorFlow] and will be sent to new
 * [FlowCollector]s until anybody will handle it
 * * Here there are an [activeData] where data [T] will be stored until somebody will handle it
 */
class AccumulatorFlow<T>(
    sourceDataFlow: Flow<T>,
    scope: CoroutineScope
) : AbstractFlow<T>() {
    private val subscope = scope.LinkedSupervisorScope()
    private val activeData = ArrayDeque<T>()
    private val dataMutex = Mutex()
    private val channelsForBroadcast = mutableListOf<Channel<T>>()
    private val channelsMutex = Mutex()
    private val steps = subscope.actor<AccumulatorFlowStep<T>> { step ->
        when (step) {
            is DataRetrievedAccumulatorFlowStep -> {
                if (activeData.firstOrNull() === step.data) {
                    dataMutex.withLock {
                        activeData.removeFirst()
                    }
                }
            }
            is SubscribeAccumulatorFlowStep -> channelsMutex.withLock {
                channelsForBroadcast.add(step.channel)
                dataMutex.withLock {
                    val dataToSend = activeData.toList()
                    safelyWithoutExceptions {
                        dataToSend.forEach { step.channel.send(it) }
                    }
                }
            }
            is UnsubscribeAccumulatorFlowStep -> channelsMutex.withLock {
                channelsForBroadcast.remove(step.channel)
            }
        }
    }
    private val subscriptionJob = sourceDataFlow.subscribeSafelyWithoutExceptions(subscope) {
        dataMutex.withLock {
            activeData.addLast(it)
        }
        channelsMutex.withLock {
            channelsForBroadcast.forEach { channel ->
                safelyWithResult {
                    channel.send(it)
                }
            }
        }
    }

    override suspend fun collectSafely(collector: FlowCollector<T>) {
        val channel = Channel<T>(Channel.UNLIMITED, BufferOverflow.SUSPEND)
        steps.send(SubscribeAccumulatorFlowStep(channel))
        val result = runCatchingSafely {
            for (data in channel) {
                val emitResult = runCatchingSafely {
                    collector.emit(data)
                }
                if (emitResult.isSuccess || emitResult.exceptionOrNull() is CancellationException) {
                    steps.send(DataRetrievedAccumulatorFlowStep(data))
                }
                emitResult.getOrThrow()
            }
        }
        channel.cancel()
        steps.send(UnsubscribeAccumulatorFlowStep(channel))
        result.getOrThrow()
    }
}

/**
 * Creates [AccumulatorFlow] using [this] as base [Flow]
 */
fun <T> Flow<T>.accumulatorFlow(scope: CoroutineScope): Flow<T> {
    return AccumulatorFlow(this, scope)
}

/**
 * Creates [AccumulatorFlow] using [this] with [receiveAsFlow] to get
 */
fun <T> Channel<T>.accumulatorFlow(scope: CoroutineScope): Flow<T> {
    return receiveAsFlow().accumulatorFlow(scope)
}
