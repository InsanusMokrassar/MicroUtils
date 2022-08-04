package dev.inmo.micro_utils.crypto

external interface CryptoJs {
    fun MD5(data: String): String
}

@JsModule("crypto-js")
@JsNonModule
external val CryptoJS: CryptoJs
