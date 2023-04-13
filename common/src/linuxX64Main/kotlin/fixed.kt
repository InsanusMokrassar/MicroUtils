package dev.inmo.micro_utils.common

import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.toKString
import platform.posix.snprintf
import platform.posix.sprintf

actual fun Float.fixed(signs: Int): Float {
    return memScoped {
        val buff = allocArray<ByteVar>(Float.SIZE_BYTES * 2)

        sprintf(buff, "%.${signs}f", this@fixed)
        buff.toKString().toFloat()
    }
}

actual fun Double.fixed(signs: Int): Double {
    return memScoped {
        val buff = allocArray<ByteVar>(Double.SIZE_BYTES * 2)

        sprintf(buff, "%.${signs}f", this@fixed)
        buff.toKString().toDouble()
    }
}
