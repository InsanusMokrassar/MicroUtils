package dev.inmo.micro_utils.common

import java.math.BigDecimal
import java.math.RoundingMode

actual fun Float.fixed(signs: Int): Float = BigDecimal.valueOf(this.toDouble())
    .setScale(signs.coerceIn(FixedSignsRange), RoundingMode.HALF_UP)
    .toFloat();

actual fun Double.fixed(signs: Int): Double = BigDecimal.valueOf(this)
    .setScale(signs.coerceIn(FixedSignsRange), RoundingMode.HALF_UP)
    .toDouble();
