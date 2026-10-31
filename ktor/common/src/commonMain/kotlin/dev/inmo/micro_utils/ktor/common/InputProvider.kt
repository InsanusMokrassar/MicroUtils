package dev.inmo.micro_utils.ktor.common

import io.ktor.utils.io.core.Input

/**
 * A function type that provides an [Input] instance.
 * This is useful for lazy or deferred input creation in Ktor operations.
 */
typealias LambdaInputProvider = () -> Input
