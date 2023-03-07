package dev.inmo.micro_utils.common

val FixedSignsRange = 0 .. 100

expect fun Float.fixed(signs: Int): Float
expect fun Double.fixed(signs: Int): Double
