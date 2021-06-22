package dev.inmo.micro_utils.common

import org.khronos.webgl.ArrayBuffer
import org.w3c.dom.ErrorEvent
import org.w3c.files.File
import org.w3c.files.FileReader
import kotlin.js.Promise

actual typealias MPPFile = File

fun MPPFile.readBytesPromise() = Promise<ByteArray> { success, failure ->
    val reader = FileReader()
    reader.onload = {
        success((reader.result as ArrayBuffer).toByteArray())
        Unit
    }
    reader.onerror = {
        failure(Exception((it as ErrorEvent).message))
        Unit
    }
    reader.readAsArrayBuffer(this)
}

private suspend fun MPPFile.dirtyReadBytes(): ByteArray = readBytesPromise().await()

actual val MPPFile.filename: FileName
    get() = FileName(name)
actual val MPPFile.filesize: Long
    get() = size.toLong()
@Warning("That is not optimized version of bytes allocator. Use asyncBytesAllocator everywhere you can")
actual val MPPFile.bytesAllocator: SuspendByteArrayAllocator
    get() = ::dirtyReadBytes
