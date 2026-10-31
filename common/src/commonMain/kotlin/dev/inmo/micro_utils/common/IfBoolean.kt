package dev.inmo.micro_utils.common

/**
 * Executes the given [block] and returns its result if this Boolean is true, otherwise returns null.
 *
 * @param T The return type of the block
 * @param block The function to execute if this Boolean is true
 * @return The result of [block] if true, null otherwise
 */
inline fun <T> Boolean.letIfTrue(block: () -> T): T? {
    return if (this) {
        block()
    } else {
        null
    }
}

/**
 * Executes the given [block] and returns its result if this Boolean is false, otherwise returns null.
 *
 * @param T The return type of the block
 * @param block The function to execute if this Boolean is false
 * @return The result of [block] if false, null otherwise
 */
inline fun <T> Boolean.letIfFalse(block: () -> T): T? {
    return if (this) {
        null
    } else {
        block()
    }
}

/**
 * Executes the given [block] if this Boolean is true and returns this Boolean.
 * Similar to [also], but only executes the block when the Boolean is true.
 *
 * @param block The function to execute if this Boolean is true
 * @return This Boolean value
 */
inline fun Boolean.alsoIfTrue(block: () -> Unit): Boolean {
    letIfTrue(block)
    return this
}

/**
 * Executes the given [block] if this Boolean is false and returns this Boolean.
 * Similar to [also], but only executes the block when the Boolean is false.
 *
 * @param block The function to execute if this Boolean is false
 * @return This Boolean value
 */
inline fun Boolean.alsoIfFalse(block: () -> Unit): Boolean {
    letIfFalse(block)
    return this
}

/**
 * Alias for [letIfTrue]. Executes the given [block] and returns its result if this Boolean is true.
 *
 * @param T The return type of the block
 * @param block The function to execute if this Boolean is true
 * @return The result of [block] if true, null otherwise
 */
inline fun <T> Boolean.ifTrue(block: () -> T): T? {
    return if (this) {
        block()
    } else {
        null
    }
}

/**
 * Alias for [letIfFalse]. Executes the given [block] and returns its result if this Boolean is false.
 *
 * @param T The return type of the block
 * @param block The function to execute if this Boolean is false
 * @return The result of [block] if false, null otherwise
 */
inline fun <T> Boolean.ifFalse(block: () -> T): T? {
    return if (this) {
        null
    } else {
        block()
    }
}
