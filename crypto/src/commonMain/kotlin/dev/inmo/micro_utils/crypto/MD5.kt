package dev.inmo.micro_utils.crypto

import korlibs.crypto.md5

/**
 * Type alias representing an MD5 hash as a hex-encoded string (32 characters).
 */
typealias MD5 = String

/**
 * Computes the MD5 hash of this byte array and returns it as a lowercase hex string.
 *
 * @return The MD5 hash as a 32-character lowercase hex string
 */
fun SourceBytes.md5(): MD5 = md5().hexLower

/**
 * Computes the MD5 hash of this string (encoded as UTF-8 bytes) and returns it as a lowercase hex string.
 *
 * @return The MD5 hash as a 32-character lowercase hex string
 */
fun SourceString.md5(): MD5 = encodeToByteArray().md5().hexLower
