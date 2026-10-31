package dev.inmo.micro_utils.common

import kotlinx.coroutines.await
import org.khronos.webgl.ArrayBuffer
import org.w3c.dom.ErrorEvent
import org.w3c.files.*
import kotlin.js.Promise

/**
 * @suppress
 */
actual typealias MPPFile = File

private fun createJsError(message: String): JsAny = js("Error(message)")

fun MPPFile.readBytesPromise() = Promise { success, failure ->
    val reader = FileReader()
    reader.onload = {
        success((reader.result as ArrayBuffer))
    }
    reader.onerror = {
        val message = (it as ErrorEvent).message
        failure(createJsError(message))
    }
    reader.readAsArrayBuffer(this)
}

fun MPPFile.readBytes(): ByteArray {
    val reader = FileReaderSync()
    return reader.readAsArrayBuffer(this).toByteArray()
}

private suspend fun MPPFile.dirtyReadBytes(): ByteArray = readBytesPromise().await<ArrayBuffer>().toByteArray()

/**
 * @suppress
 */
actual val MPPFile.filename: FileName
    get() = FileName(name)

actual val MPPFilePathSeparator: Char
    get() = '/'

/**
 * @suppress
 */
actual val MPPFile.filesize: Long
    get() = jsNumberToBigInt(size).toLong()
/**
 * @suppress
 */
@Warning("That is not optimized version of bytes allocator. Use asyncBytesAllocator everywhere you can")
actual val MPPFile.bytesAllocatorSync: ByteArrayAllocator
    get() = ::readBytes
/**
 * @suppress
 */
@Warning("That is not optimized version of bytes allocator. Use asyncBytesAllocator everywhere you can")
actual val MPPFile.bytesAllocator: SuspendByteArrayAllocator
    get() = ::dirtyReadBytes

private fun jsNumberToBigInt(number: JsNumber): JsBigInt = js("BigInt(number)")
