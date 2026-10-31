package dev.inmo.micro_utils.ktor.common

import dev.inmo.micro_utils.common.MPPFile
import io.ktor.utils.io.core.Input

/**
 * Creates a Ktor [Input] from this multiplatform file.
 * Platform-specific implementations handle file reading for each supported platform.
 *
 * @return An [Input] stream for reading this file
 */
expect fun MPPFile.input(): Input
