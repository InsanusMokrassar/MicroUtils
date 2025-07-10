package dev.inmo.micro_utils.common

actual fun Float.fixed(signs: Int): Float = this.asDynamic().toFixed(signs.coerceIn(FixedSignsRange)).unsafeCast<String>().toFloat()
actual fun Double.fixed(signs: Int): Double = this.asDynamic().toFixed(signs.coerceIn(FixedSignsRange)).unsafeCast<String>().toDouble()
