package dev.inmo.micro_utils.ktor.common

import dev.inmo.micro_utils.common.MPPFile
import io.ktor.utils.io.bits.Memory
import io.ktor.utils.io.bits.loadByteArray
import io.ktor.utils.io.core.Input
import okio.FileSystem
import okio.Path

private class FileInput(
    private val path: Path
) : Input() {
    private val openedFile = FileSystem.SYSTEM.openReadOnly(path)
    override fun closeSource() {
        openedFile.close()
    }

    override fun fill(destination: Memory, offset: Int, length: Int): Int {
        val byteArray = ByteArray(length)
        val read = openedFile.read(offset.toLong(), byteArray, 0, length)
        destination.loadByteArray(
            offset,
            byteArray,
            count = length
        )
        return read
    }
}

actual fun MPPFile.input(): Input {
    return FileInput(this)
}

