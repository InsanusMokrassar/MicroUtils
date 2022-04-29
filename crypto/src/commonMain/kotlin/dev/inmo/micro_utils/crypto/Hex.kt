package dev.inmo.micro_utils.crypto

val HEX_ARRAY = "0123456789abcdef".toCharArray()

fun SourceBytes.hex(): String {
    val hexChars = CharArray(size * 2)
    for (j in indices) {
        val v: Int = this[j].toInt() and 0xFF
        hexChars[j * 2] = HEX_ARRAY[v ushr 4]
        hexChars[j * 2 + 1] = HEX_ARRAY[v and 0x0F]
    }
    return hexChars.concatToString()
}

fun SourceString.hex(): String = encodeToByteArray().hex()
