package dev.inmo.micro_utils.startup.plugin

import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

internal actual fun alternativeDeserialize(decoder: Decoder): StartPlugin? {
    val kclass = Class.forName(decoder.decodeString()).kotlin
    return (kclass.objectInstance ?: kclass.constructors.first { it.parameters.isEmpty() }.call()) as StartPlugin
}

internal actual fun alternativeSerialize(
    encoder: Encoder,
    value: StartPlugin
): Boolean {
    encoder.encodeString(value::class.java.canonicalName ?: return false)
    return true
}