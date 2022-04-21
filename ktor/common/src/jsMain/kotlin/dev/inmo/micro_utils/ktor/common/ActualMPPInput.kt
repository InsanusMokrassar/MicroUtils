package dev.inmo.micro_utils.ktor.common

import dev.inmo.micro_utils.common.*
import io.ktor.utils.io.core.ByteReadPacket
import io.ktor.utils.io.core.Input

actual fun MPPFile.input(): Input = ByteReadPacket(bytesSync())
