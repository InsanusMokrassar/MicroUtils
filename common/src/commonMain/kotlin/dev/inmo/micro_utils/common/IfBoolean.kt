package dev.inmo.micro_utils.common

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
