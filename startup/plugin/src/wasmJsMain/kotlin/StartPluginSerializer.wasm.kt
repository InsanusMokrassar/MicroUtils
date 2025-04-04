package dev.inmo.micro_utils.startup.plugin

import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

internal actual fun alternativeDeserialize(decoder: Decoder): StartPlugin? {
    return null
}

internal actual fun alternativeSerialize(
    encoder: Encoder,
    value: StartPlugin
): Boolean = false