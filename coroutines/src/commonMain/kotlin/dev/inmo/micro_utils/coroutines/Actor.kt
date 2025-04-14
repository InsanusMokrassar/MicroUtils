package dev.inmo.micro_utils.coroutines

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow

/**
 * Creates an actor using coroutines that processes incoming messages of type [T].
 * An actor is a computational entity that processes messages sequentially in response to messages it receives.
 *
 * @param T The type of messages this actor will process
 * @param channelCapacity The capacity of the [Channel] used for message delibery to the actor. Defaults to [Channel.UNLIMITED]
 * @param block The processing logic to be applied to each received message
 * @return A [Channel] that can be used to send messages to this actor or cancel it
 */
fun <T> CoroutineScope.actor(
    channelCapacity: Int = Channel.UNLIMITED,
    block: suspend (T) -> Unit
): Channel<T> {
    val channel = Channel<T>(channelCapacity)
    channel.consumeAsFlow().subscribe(this, block)
    return channel
}

/**
 * Creates a safe actor that catches and handles exceptions during message processing.
 * This variant wraps the processing logic in a safety mechanism to prevent actor failure due to exceptions.
 *
 * @param T The type of messages this actor will process
 * @param channelCapacity The capacity of the [Channel] used for message processing. Defaults to [Channel.UNLIMITED]
 * @param onException Handler for exceptions that occur during message processing. Defaults to [defaultSafelyExceptionHandler]
 * @param block The processing logic to be applied to each received message
 * @return A [Channel] that can be used to send messages to this actor
 */
inline fun <T> CoroutineScope.safeActor(
    channelCapacity: Int = Channel.UNLIMITED,
    noinline onException: ExceptionHandler<Unit> = defaultSafelyExceptionHandler,
    crossinline block: suspend (T) -> Unit
): Channel<T> = actor(
    channelCapacity
) {
    safely(onException) {
        block(it)
    }
}

