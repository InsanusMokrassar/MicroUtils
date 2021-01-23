package dev.inmo.micro_utils.common

import kotlin.experimental.and

private const val BASE64_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
private const val BASE64_MASK: Byte = 0x3f
private const val BASE64_PAD = '='
private val BASE64_INVERSE_ALPHABET = IntArray(256) {
    BASE64_ALPHABET.indexOf(it.toChar())
}

internal fun Int.toBase64(): Char = BASE64_ALPHABET[this]
internal fun Byte.fromBase64(): Byte = BASE64_INVERSE_ALPHABET[toInt() and 0xff].toByte() and BASE64_MASK

const val replacedInCrypto = "Replaced in crypto subproject"

@Deprecated(replacedInCrypto, ReplaceWith("EncodedBase64String", "dev.inmo.micro_utils.crypto"))
typealias EncodedBase64String = String
@Deprecated(replacedInCrypto, ReplaceWith("EncodedByteArray", "dev.inmo.micro_utils.crypto"))
typealias EncodedByteArray = ByteArray

@Deprecated(replacedInCrypto, ReplaceWith("encodeBase64String()", "dev.inmo.micro_utils.crypto"))
fun String.encodeBase64String(): EncodedBase64String = encodeToByteArray().encodeBase64String()
@Deprecated(replacedInCrypto, ReplaceWith("encodeBase64()", "dev.inmo.micro_utils.crypto"))
fun String.encodeBase64(): EncodedByteArray = encodeToByteArray().encodeBase64()

@Deprecated(replacedInCrypto, ReplaceWith("encodeBase64String()", "dev.inmo.micro_utils.crypto"))
fun ByteArray.encodeBase64String(): EncodedBase64String = buildString {
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
@Deprecated(replacedInCrypto, ReplaceWith("encodeBase64()", "dev.inmo.micro_utils.crypto"))
fun ByteArray.encodeBase64(): EncodedByteArray = encodeBase64String().encodeToByteArray()

@Deprecated(replacedInCrypto, ReplaceWith("decodeBase64()", "dev.inmo.micro_utils.crypto"))
fun EncodedBase64String.decodeBase64() = dropLastWhile { it == BASE64_PAD }.encodeToByteArray().decodeBase64()
@Deprecated(replacedInCrypto, ReplaceWith("decodeBase64String()", "dev.inmo.micro_utils.crypto"))
fun EncodedBase64String.decodeBase64String() = decodeBase64().decodeToString()

@Deprecated(replacedInCrypto, ReplaceWith("decodeBase64()", "dev.inmo.micro_utils.crypto"))
fun EncodedByteArray.decodeBase64(): ByteArray {
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
@Deprecated(replacedInCrypto, ReplaceWith("decodeBase64String()", "dev.inmo.micro_utils.crypto"))
fun EncodedByteArray.decodeBase64String() = decodeBase64().decodeToString()
