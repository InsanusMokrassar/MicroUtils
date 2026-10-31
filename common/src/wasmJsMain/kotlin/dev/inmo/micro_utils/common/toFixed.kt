package dev.inmo.micro_utils.common

actual fun Float.fixed(signs: Int): Float {
    return jsToFixed(toDouble().toJsNumber(), signs.coerceIn(FixedSignsRange)).toString().toFloat()
}

actual fun Double.fixed(signs: Int): Double {
    return jsToFixed(toJsNumber(), signs.coerceIn(FixedSignsRange)).toString().toDouble()
}

private fun jsToFixed(number: JsNumber, signs: Int): JsString = js("number.toFixed(signs)")
