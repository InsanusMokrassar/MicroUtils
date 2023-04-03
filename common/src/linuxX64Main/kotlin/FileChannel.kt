package dev.inmo.micro_utils.common

import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.convert
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.toCValues
import kotlinx.cinterop.toKString
import platform.posix.SEEK_END
import platform.posix.SEEK_SET
import platform.posix.fclose
import platform.posix.feof
import platform.posix.fread
import platform.posix.fseek
import platform.posix.ftell
import platform.posix.fwrite
import platform.posix.size_t

class FileChannel (file: MPPFile, mode: String) : RandomAccess {
    internal val handler = file.createPointer(mode)

    private var position: ULong
        get() = ftell(handler).convert()
        set(value) {
            fseek(handler, value.convert(), SEEK_SET)
        }
    private val size: ULong
        get() {
            val pos = position
            gotoEnd()
            val result = position
            position = pos
            return result
        }

    fun skip(length: Long): Long {
        checkClosed()
        memScoped { }
        if (length == 0L)
            return 0L
        if (feof(handler) != 0)
            return 0L
        val endOfFile = size
        val position = minOf(endOfFile, this.position + length.toULong())
        this.position = position
        return (endOfFile - position).toLong()
    }

    fun read(dest: ByteArray): Int {
        checkClosed()
        if (feof(handler) != 0)
            return 0

        memScoped {
            val tmp = allocArray<ByteVar>(dest.size);
            fread(tmp, Byte.SIZE_BYTES.convert<size_t>(), dest.size.convert(), handler).convert<Int>()
            tmp.toKString()
        }
        return fread(dest.toCValues(), Byte.SIZE_BYTES.convert<size_t>(), dest.size.convert(), handler).convert<Int>()
    }

    private var closed = false

    private fun checkClosed() {
        if (closed) {
            error("File channel has been closed already")
        }
    }

    fun close() {
        checkClosed()
        fclose(handler)
        closed = true
    }

    fun write(data: ByteArray): Int {
        checkClosed()
        if (feof(handler) != 0)
            return 0

        return fwrite(data.toCValues(), 1.convert<size_t>(), data.size.convert<size_t>(), handler).convert<Int>()
    }

    fun flush() {
        checkClosed()
    }

    private fun gotoEnd() {
        fseek(handler, 0, SEEK_END)
    }

    fun readFully(): ByteArray {
        var result = ByteArray(0)
        memScoped {
            val tmp = ByteArray(64 * 1024)

            do {
                val read = read(tmp)
                result += tmp.take(read)
            } while (read > 0)
        }
        val tmp = ByteArray(64 * 1024)


        return result
    }
}
