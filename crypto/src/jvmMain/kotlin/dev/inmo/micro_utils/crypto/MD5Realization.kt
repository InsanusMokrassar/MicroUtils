package dev.inmo.micro_utils.crypto

import java.math.BigInteger
import java.security.MessageDigest

/**
 * @suppress
 */
actual fun SourceBytes.md5(): MD5 = BigInteger(
    1,
    MessageDigest.getInstance("MD5").digest(this)
).toString(16)
