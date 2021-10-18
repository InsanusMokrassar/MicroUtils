package dev.inmo.micro_utils.crypto

/**
 * @suppress
 */
actual fun SourceBytes.md5(): MD5 = CryptoJS.MD5(decodeToString())
