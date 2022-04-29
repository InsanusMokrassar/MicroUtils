package dev.inmo.micro_utils.crypto

external interface CryptoJs {
    fun MD5(data: String): String
}

@JsModule("crypto-js")
@JsNonModule
external val CryptoJS: CryptoJs

actual fun SourceString.hmacSha256(key: String) = CryptoJS.asDynamic().HmacSHA256(this, key).unsafeCast<String>()

actual fun SourceString.hex() = CryptoJS.asDynamic().format.Hex(this).unsafeCast<String>()
