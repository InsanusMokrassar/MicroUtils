package dev.inmo.micro_utils.common

import dev.inmo.micro_utils.coroutines.doInIO
import dev.inmo.micro_utils.coroutines.doOutsideOfCoroutine
import java.io.File

/**
 * @suppress
 */
actual typealias MPPFile = File

/**
 * @suppress
 */
actual val MPPFile.filename: FileName
    get() = FileName(name)
/**
 * @suppress
 */
actual val MPPFile.filesize: Long
    get() = length()
/**
 * @suppress
 */
actual val MPPFile.bytesAllocatorSync: ByteArrayAllocator
    get() = ::readBytes
/**
 * @suppress
 */
actual val MPPFile.bytesAllocator: SuspendByteArrayAllocator
    get() = {
        doInIO {
            doOutsideOfCoroutine {
                readBytes()
            }
        }
    }
