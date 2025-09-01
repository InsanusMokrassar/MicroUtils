package dev.inmo.micro_utils.common

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@Serializable
@JvmInline
value class FileName(val string: String) {
    val name: String
        get() = withoutSlashAtTheEnd.takeLastWhile { it != MPPFilePathSeparator }
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
        get() = string.dropLastWhile { it == MPPFilePathSeparator }
    override fun toString(): String = string
}


expect class MPPFile

expect val MPPFile.filename: FileName
expect val MPPFilePathSeparator: Char
expect val MPPFile.filesize: Long
expect val MPPFile.bytesAllocatorSync: ByteArrayAllocator
expect val MPPFile.bytesAllocator: SuspendByteArrayAllocator
fun MPPFile.bytesSync() = bytesAllocatorSync()
suspend fun MPPFile.bytes() = bytesAllocator()

