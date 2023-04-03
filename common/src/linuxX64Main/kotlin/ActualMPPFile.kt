package dev.inmo.micro_utils.common

import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.alloc
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.convert
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.readBytes
import platform.linux.statvfs
import platform.posix.ACCESSPERMS
import platform.posix.FILE
import platform.posix.F_OK
import platform.posix.SEEK_END
import platform.posix.S_IFDIR
import platform.posix.S_IFMT
import platform.posix.access
import platform.posix.fgets
import platform.posix.fopen
import platform.posix.fseek
import platform.posix.ftell
import platform.posix.remove
import platform.posix.rename
import platform.posix.rmdir
import platform.posix.stat

actual data class MPPFile(internal val filename: FileName) {
    val path = filename.string
    val isFile: Boolean
        get() = memScoped {
            val stat = alloc<stat>()
            if (stat(path, stat.ptr) != 0)
                return@memScoped false
            (S_IFDIR != (stat.st_mode and S_IFMT.convert()).convert<Int>())
        }

    val isDirectory: Boolean
        get() = memScoped {
            val stat = alloc<stat>()
            if (stat(path, stat.ptr) != 0)
                return@memScoped false
            S_IFDIR == (stat.st_mode and S_IFMT.convert()).convert<Int>()
        }
    val size: Long
        get() = memScoped {
            val stat = alloc<stat>()
            if (stat(path, stat.ptr) != 0)
                return@memScoped 0
            return stat.st_size.convert()
        }
    val lastModified: Long
        get() = memScoped {
            val stat = alloc<stat>()
            if (stat(path, stat.ptr) != 0)
                return@memScoped 0
            return stat.st_ctim.tv_nsec / 1000L
        }

    val freeSpace: Long
        get() =
            memScoped {
                val stat = alloc<statvfs>()
                statvfs(path, stat.ptr)
                (stat.f_bfree.toULong() * stat.f_bsize.toULong()).toLong()
            }

    val availableSpace: Long
        get() =
            memScoped {
                val stat = alloc<statvfs>()
                statvfs(path, stat.ptr)
                (stat.f_bavail.toULong() * stat.f_bsize.toULong()).toLong()
            }

    val totalSpace: Long
        get() = memScoped {
            val stat = alloc<statvfs>()
            statvfs(path, stat.ptr)
            (stat.f_blocks.toULong() * stat.f_frsize.toULong()).toLong()
        }

    constructor(vararg path: String) : this(FileName(path.map { it.removeSuffix(SEPARATOR_STRING) }.joinToString(SEPARATOR_STRING)))
    constructor(parent: MPPFile, subpath: String) : this("${parent.filename.withoutSlashAtTheEnd}$SEPARATOR${subpath}")

    fun createPointer(mode: String = "r") = fopen(filename.name, mode)

    fun delete(): Boolean {
        return when {
            isDirectory -> rmdir(path) == 0
            isFile -> remove(path) == 0
            else -> false
        }
    }

    fun mkdir(): Boolean = platform.posix.mkdir(path, ACCESSPERMS) == 0

    override fun toString(): String = path
    override fun equals(other: Any?): Boolean {
        if (other !is MPPFile) return false
        return path == other.path
    }

    override fun hashCode(): Int = 31 + path.hashCode()

    fun renameTo(newPath: MPPFile): Boolean = rename(path, newPath.path) == 0

    fun list(): List<MPPFile> {
        val out = ArrayList<MPPFile>()
        FileIterator(this).forEach { file ->
            out += file
        }
        return out
    }

    fun fileChannel(mode: String = "r") = FileChannel(this, mode)

    companion object {
        val SEPARATOR: Char = '/'
        val SEPARATOR_STRING: String = SEPARATOR.toString()
        val temporalDirectory: MPPFile?
            get() = MPPFile("/tmp").takeIf {
                it.isDirectory
            }
    }
}

actual val MPPFile.filename: FileName
    get() = this.filename
actual val MPPFile.filesize: Long
    get() = memScoped {
        val pointer = createPointer()
        fseek(pointer, 0L, SEEK_END)
        ftell(pointer)
    }
actual val MPPFile.bytesAllocatorSync: ByteArrayAllocator
    get() = {
        memScoped {
            fileChannel().readFully()
        }
    }
actual val MPPFile.bytesAllocator: SuspendByteArrayAllocator
    get() = {
        bytesAllocatorSync()
    }

