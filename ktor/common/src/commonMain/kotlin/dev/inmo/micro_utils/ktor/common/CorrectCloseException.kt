package dev.inmo.micro_utils.ktor.common

/**
 * An exception used to indicate a correct/normal closure of a connection or stream.
 * This is typically used in WebSocket or network communication scenarios where a
 * clean shutdown needs to be distinguished from error conditions.
 */
object CorrectCloseException : Exception()
