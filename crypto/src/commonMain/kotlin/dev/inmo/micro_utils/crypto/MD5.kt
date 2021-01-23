package dev.inmo.micro_utils.crypto

typealias MD5 = String

expect fun SourceBytes.md5(): MD5
fun SourceString.md5(): MD5 = encodeToByteArray().md5()
