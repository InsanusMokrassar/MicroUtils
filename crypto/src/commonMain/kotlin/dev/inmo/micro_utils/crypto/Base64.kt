package dev.inmo.micro_utils.crypto

import kotlin.experimental.and

private const val BASE64_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
private const val BASE64_MASK: Byte = 0x3f
private const val BASE64_PAD = '='
private val BASE64_INVERSE_ALPHABET = IntArray(256) {
    BASE64_ALPHABET.indexOf(it.toChar())
}

internal fun Int.toBase64(): Char = BASE64_ALPHABET[this]
internal fun Byte.fromBase64(): Byte = BASE64_INVERSE_ALPHABET[toInt() and 0xff].toByte() and BASE64_MASK

/**
 * Type alias representing a Base64-encoded string.
 */
typealias EncodedBase64String = String

/**
 * Type alias representing a Base64-encoded byte array.
 */
typealias EncodedByteArray = ByteArray

/**
 * Encodes this string to Base64 format, returning the result as a string.
 *
 * @return A Base64-encoded string
 */
fun SourceString.encodeBase64String(): EncodedBase64String = encodeToByteArray().encodeBase64String()

/**
 * Encodes this string to Base64 format, returning the result as a byte array.
 *
 * @return A Base64-encoded byte array
 */
fun SourceString.encodeBase64(): EncodedByteArray = encodeToByteArray().encodeBase64()

/**
 * Encodes this byte array to Base64 format, returning the result as a string.
 *
 * @return A Base64-encoded string with padding ('=') characters
 */
fun SourceBytes.encodeBase64String(): EncodedBase64String = buildString {
    var i = 0
    while (this@encodeBase64String.size > i) {
        val read = kotlin.math.min(3, this@encodeBase64String.size - i)
        val data = ByteArray(3) {
            if (it < read) {
                this@encodeBase64String[it + i]
            } else {
                0
            }
        }

        val padSize = (data.size - read) * 8 / 6
        val chunk = ((data[0].toInt() and 0xFF) shl 16) or
            ((data[1].toInt() and 0xFF) shl 8) or
            (data[2].toInt() and 0xFF)

        for (index in data.size downTo padSize) {
            val char = (chunk shr (6 * index)) and BASE64_MASK.toInt()
            append(char.toBase64())
        }

        repeat(padSize) { append(BASE64_PAD) }

        i += read
    }
}

/**
 * Encodes this byte array to Base64 format, returning the result as a byte array.
 *
 * @return A Base64-encoded byte array
 */
fun SourceBytes.encodeBase64(): EncodedByteArray = encodeBase64String().encodeToByteArray()

/**
 * Decodes this Base64-encoded string back to the original byte array.
 *
 * @return The decoded byte array
 */
fun EncodedBase64String.decodeBase64(): SourceBytes = dropLastWhile { it == BASE64_PAD }.encodeToByteArray().decodeBase64()

/**
 * Decodes this Base64-encoded string back to the original string.
 *
 * @return The decoded string
 */
fun EncodedBase64String.decodeBase64String(): SourceString = decodeBase64().decodeToString()

/**
 * Decodes this Base64-encoded byte array back to the original byte array.
 *
 * @return The decoded byte array
 */
fun EncodedByteArray.decodeBase64(): SourceBytes {
    val result = mutableListOf<Byte>()
    val data = ByteArray(4)
    (0 until size step 4).forEach { i ->
        var read = 0
        for (j in 0 until 4) {
            if (j + i < size) {
                data[j] = get(j + i)
                read++
            } else {
                break
            }
        }
        val chunk = data.foldIndexed(0) { index, result, current ->
            result or (current.fromBase64().toInt() shl ((3 - index) * 6))
        }
        for (index in data.size - 2 downTo (data.size - read)) {
            val origin = (chunk shr (8 * index)) and 0xff
            result.add(origin.toByte())
        }
    }

    return result.toByteArray()
}

/**
 * Decodes this Base64-encoded byte array back to the original string.
 *
 * @return The decoded string
 */
fun EncodedByteArray.decodeBase64String(): SourceString = decodeBase64().decodeToString()
