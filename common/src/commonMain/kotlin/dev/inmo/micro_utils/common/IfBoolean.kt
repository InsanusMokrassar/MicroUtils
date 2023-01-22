package dev.inmo.micro_utils.common

inline fun <T> Boolean.letIfTrue(block: () -> T): T? {
    return if (this) {
        block()
    } else {
        null
    }
}

inline fun <T> Boolean.letIfFalse(block: () -> T): T? {
    return if (this) {
        null
    } else {
        block()
    }
}

inline fun Boolean.alsoIfTrue(block: () -> Unit): Boolean {
    letIfTrue(block)
    return this
}

inline fun Boolean.alsoIfFalse(block: () -> Unit): Boolean {
    letIfFalse(block)
    return this
}

inline fun <T> Boolean.ifTrue(block: () -> T): T? {
    return if (this) {
        block()
    } else {
        null
    }
}

inline fun <T> Boolean.ifFalse(block: () -> T): T? {
    return if (this) {
        null
    } else {
        block()
    }
}
