package dev.inmo.micro_utils.common

import kotlinx.cinterop.pointed
import kotlinx.cinterop.toKString
import platform.posix.closedir
import platform.posix.dirent
import platform.posix.opendir
import platform.posix.readdir
import kotlin.native.internal.createCleaner

class FileIterator internal constructor(private val file: MPPFile) : Iterator<MPPFile> {
    init {
        if (!file.isDirectory)
            error("\"${file.path}\" is not direction")
    }

    private val handler = opendir(file.path)
    private var next: dirent? = null
    private var end = false

    override fun hasNext(): Boolean {
        while (true) {
            if (end)
                return false

            if (next == null) {
                next = readdir(handler)?.pointed
                if (next == null) {
                    end = true
                    return false
                }
                val name = next!!.d_name.toKString()
                if (name == "." || name == "..") {
                    next = null
                    continue
                }
                return true
            }
            return true
        }
    }

    override fun next(): MPPFile {
        if (!hasNext())
            throw NoSuchElementException()
        val result = MPPFile(file, next!!.d_name.toKString())
        next = null
        return result
    }

    @OptIn(ExperimentalStdlibApi::class)
    private val cleaner = createCleaner(handler) {
        closedir(it)
    }
}

