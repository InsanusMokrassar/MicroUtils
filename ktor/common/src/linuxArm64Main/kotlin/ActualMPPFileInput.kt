package dev.inmo.micro_utils.ktor.common

import dev.inmo.micro_utils.common.MPPFile
import dev.inmo.micro_utils.common.bytesAllocatorSync
import io.ktor.utils.io.core.ByteReadPacket
import io.ktor.utils.io.core.Input

actual fun MPPFile.input(): Input {
    return ByteReadPacket(bytesAllocatorSync())
}

