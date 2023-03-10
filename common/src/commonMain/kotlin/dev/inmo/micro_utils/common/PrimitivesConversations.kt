package dev.inmo.micro_utils.common

/**
 * Convert [this] [Long] to [Int] with bounds of [Int.MIN_VALUE] and [Int.MAX_VALUE]
 */
fun Long.toCoercedInt(): Int = coerceIn(Int.MIN_VALUE.toLong(), Int.MAX_VALUE.toLong()).toInt()
/**
 * Convert [this] [Long] to [Short] with bounds of [Short.MIN_VALUE] and [Short.MAX_VALUE]
 */
fun Long.toCoercedShort(): Short = coerceIn(Short.MIN_VALUE.toLong(), Short.MAX_VALUE.toLong()).toShort()
/**
 * Convert [this] [Long] to [Byte] with bounds of [Byte.MIN_VALUE] and [Byte.MAX_VALUE]
 */
fun Long.toCoercedByte(): Byte = coerceIn(Byte.MIN_VALUE.toLong(), Byte.MAX_VALUE.toLong()).toByte()

/**
 * Convert [this] [Int] to [Short] with bounds of [Short.MIN_VALUE] and [Short.MAX_VALUE]
 */
fun Int.toCoercedShort(): Short = coerceIn(Short.MIN_VALUE.toInt(), Short.MAX_VALUE.toInt()).toShort()
/**
 * Convert [this] [Int] to [Byte] with bounds of [Byte.MIN_VALUE] and [Byte.MAX_VALUE]
 */
fun Int.toCoercedByte(): Byte = coerceIn(Byte.MIN_VALUE.toInt(), Byte.MAX_VALUE.toInt()).toByte()

/**
 * Convert [this] [Short] to [Byte] with bounds of [Byte.MIN_VALUE] and [Byte.MAX_VALUE]
 */
fun Short.toCoercedByte(): Byte = coerceIn(Byte.MIN_VALUE.toShort(), Byte.MAX_VALUE.toShort()).toByte()
