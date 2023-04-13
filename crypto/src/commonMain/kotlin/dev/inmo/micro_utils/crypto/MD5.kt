package dev.inmo.micro_utils.crypto

import com.soywiz.krypto.md5

typealias MD5 = String

fun SourceBytes.md5(): MD5 = md5().hexLower
fun SourceString.md5(): MD5 = encodeToByteArray().md5().hexLower
