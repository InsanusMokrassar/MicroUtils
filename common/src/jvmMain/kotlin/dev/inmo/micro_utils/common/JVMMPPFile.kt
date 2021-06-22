package dev.inmo.micro_utils.common

import dev.inmo.micro_utils.coroutines.doInIO
import dev.inmo.micro_utils.coroutines.doOutsideOfCoroutine
import java.io.File

actual typealias MPPFile = File

actual val MPPFile.filename: FileName
    get() = FileName(name)
actual val MPPFile.filesize: Long
    get() = length()
actual val MPPFile.bytesAllocator: SuspendByteArrayAllocator
    get() = {
        doInIO {
            doOutsideOfCoroutine {
                readBytes()
            }
        }
    }
