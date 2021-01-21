package dev.inmo.micro_utils.serialization.encapsulator

import kotlinx.serialization.KSerializer
import kotlinx.serialization.encoding.Encoder
import kotlin.reflect.KClass

data class Encapsulator<T : Any>(
    val klass: KClass<T>,
    val serializer: KSerializer<T>
) {
    fun <O> encapsulate(
        value: Any,
        callback: KSerializer<T>.(T) -> O
    ): O? = if (klass.isInstance(value)) {
        callback(serializer, value as T)
    } else {
        null
    }
}

fun <T : Any> Encapsulator<T>.tryEncode(encoder: Encoder, value: Any) = encapsulate(value) {
    encoder.encodeSerializableValue(this, it)
}
