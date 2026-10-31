package dev.inmo.micro_utils.common

import org.khronos.webgl.*

fun DataView.toByteArray() = ByteArray(this.byteLength) {
    getInt8(it)
}

fun ArrayBuffer.toByteArray() = Int8Array(this).toByteArray()

fun ByteArray.toDataView() = DataView(ArrayBuffer(size)).also {
    forEachIndexed { i, byte -> it.setInt8(i, byte) }
}

fun ByteArray.toArrayBuffer() = toDataView().buffer
