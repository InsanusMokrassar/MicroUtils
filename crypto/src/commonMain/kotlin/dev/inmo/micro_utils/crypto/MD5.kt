package dev.inmo.micro_utils.crypto

import korlibs.crypto.md5

typealias MD5 = String

fun SourceBytes.md5(): MD5 = md5().hexLower
fun SourceString.md5(): MD5 = encodeToByteArray().md5().hexLower
