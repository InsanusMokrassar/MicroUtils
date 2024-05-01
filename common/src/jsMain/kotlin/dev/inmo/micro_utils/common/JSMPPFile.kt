package dev.inmo.micro_utils.common

import kotlinx.io.RawSource
import org.khronos.webgl.ArrayBuffer
import org.w3c.dom.ErrorEvent
import org.w3c.files.*
import kotlin.js.Promise

/**
 * @suppress
 */
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

fun MPPFile.readBytes(): ByteArray {
    val reader = FileReaderSync()
    return reader.readAsArrayBuffer(this).toByteArray()
}

private suspend fun MPPFile.dirtyReadBytes(): ByteArray = readBytesPromise().await()

/**
 * @suppress
 */
actual val MPPFile.filename: FileName
    get() = FileName(name)
/**
 * @suppress
 */
actual val MPPFile.filesize: Long
    get() = size.toLong()

actual val MPPFile.rawSource: RawSource
    get() =
