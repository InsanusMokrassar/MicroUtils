package dev.inmo.micro_utils.crypto

external interface CryptoJs {
    fun MD5(data: String): String
}

@JsModule("crypto-js")
@JsNonModule
external val CryptoJS: CryptoJs

actual fun SourceString.hmacSha256(key: String): String {
    return CryptoJS.asDynamic().HmacSHA256(this, key).toString().unsafeCast<String>()
}
