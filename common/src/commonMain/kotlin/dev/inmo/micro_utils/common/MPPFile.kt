package dev.inmo.micro_utils.common

import kotlinx.io.RawSource
import kotlinx.io.Source
import kotlinx.io.buffered
import kotlinx.io.readByteArray
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@Serializable
@JvmInline
value class FileName(val string: String) {
    val name: String
        get() = withoutSlashAtTheEnd.takeLastWhile { it != '/' }
    val extension: String
        get() = name.takeLastWhile { it != '.' }
    val nameWithoutExtension: String
        get() {
            val filename = name
            return filename.indexOfLast { it == '.' }.takeIf { it > -1 } ?.let {
                filename.substring(0, it)
            } ?: filename
        }
    val withoutSlashAtTheEnd: String
        get() = string.dropLastWhile { it == '/' }
    override fun toString(): String = string
}


expect class MPPFile = File

expect val MPPFile.rawSource: RawSource
val MPPFile.source: Source
    get() = rawSource.buffered()
expect val MPPFile.filename: FileName
expect val MPPFile.filesize: Long
val MPPFile.bytesAllocatorSync: ByteArrayAllocator
    get() = {
        source.readByteArray()
    }
val MPPFile.bytesAllocator: SuspendByteArrayAllocator
    get() = {
        source.readByteArray()
    }
fun MPPFile.bytesSync() = bytesAllocatorSync()
suspend fun MPPFile.bytes() = bytesAllocator()

