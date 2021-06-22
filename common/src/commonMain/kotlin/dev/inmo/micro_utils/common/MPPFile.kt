package dev.inmo.micro_utils.common

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@Serializable
@JvmInline
value class FileName(val string: String) {
    val name: String
        get() = string.takeLastWhile { it != '/' }
    val extension: String
        get() = name.takeLastWhile { it != '.' }
    val nameWithoutExtension: String
        get() {
            val filename = name
            return filename.indexOfLast { it == '.' }.takeIf { it > -1 } ?.let {
                filename.substring(0, it)
            } ?: filename
        }
    override fun toString(): String = string
}


@PreviewFeature
expect class MPPFile

expect val MPPFile.filename: FileName
expect val MPPFile.filesize: Long
expect val MPPFile.bytesAllocator: SuspendByteArrayAllocator
suspend fun MPPFile.bytes() = bytesAllocator()

