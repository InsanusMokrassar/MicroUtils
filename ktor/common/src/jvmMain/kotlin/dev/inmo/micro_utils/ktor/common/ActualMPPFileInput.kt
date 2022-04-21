package dev.inmo.micro_utils.ktor.common

import dev.inmo.micro_utils.common.MPPFile
import io.ktor.utils.io.core.Input
import io.ktor.utils.io.streams.asInput

actual fun MPPFile.input(): Input = inputStream().asInput()
