package dev.inmo.micro_utils.crypto

import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

@Deprecated("Deprecated due to incorrect of work sometimes and redundancy. Can be replaced by korlibs krypto")
actual fun SourceString.hmacSha256(key: String): String {
    val mac = Mac.getInstance("HmacSHA256")

    val secretKey = SecretKeySpec(key.toByteArray(), "HmacSHA256")
    mac.init(secretKey)

    return mac.doFinal(toByteArray()).hex()
}
