package dev.inmo.micro_utils.common

import okio.FileSystem
import okio.Path
import okio.use

actual typealias MPPFile = Path

/**
 * @suppress
 */
actual val MPPFile.filename: FileName
    get() = FileName(toString())
/**
 * @suppress
 */
actual val MPPFile.filesize: Long
    get() = FileSystem.SYSTEM.openReadOnly(this).use {
        it.size()
    }
/**
 * @suppress
 */
actual val MPPFile.bytesAllocatorSync: ByteArrayAllocator
    get() = {
        FileSystem.SYSTEM.read(this) {
            readByteArray()
        }
    }
/**
 * @suppress
 */
actual val MPPFile.bytesAllocator: SuspendByteArrayAllocator
    get() = {
        bytesAllocatorSync()
    }
