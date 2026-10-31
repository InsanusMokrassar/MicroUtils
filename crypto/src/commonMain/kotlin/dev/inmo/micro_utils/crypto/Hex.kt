package dev.inmo.micro_utils.crypto

/**
 * Character array used for hexadecimal encoding (lowercase).
 */
val HEX_ARRAY = "0123456789abcdef".toCharArray()

/**
 * Converts this byte array to a hexadecimal string representation (lowercase).
 * Each byte is represented as two hex characters.
 *
 * @return A lowercase hex string (e.g., "48656c6c6f" for "Hello")
 */
fun SourceBytes.hex(): String {
    val hexChars = CharArray(size * 2)
    for (j in indices) {
        val v: Int = this[j].toInt() and 0xFF
        hexChars[j * 2] = HEX_ARRAY[v ushr 4]
        hexChars[j * 2 + 1] = HEX_ARRAY[v and 0x0F]
    }
    return hexChars.concatToString()
}

/**
 * Converts this string to a hexadecimal representation by first encoding it as UTF-8 bytes.
 *
 * @return A lowercase hex string representation of the UTF-8 encoded bytes
 */
fun SourceString.hex(): String = encodeToByteArray().hex()
