package dev.inmo.micro_utils.repos

import android.database.Cursor

inline fun <T> Cursor.map(
    block: (Cursor) -> T
): List<T> {
    val result = mutableListOf<T>()
    if (moveToFirst()) {
        do {
            result.add(block(this))
        } while (moveToNext())
    }
    return result
}

fun Cursor.firstOrNull(): Cursor? = if (moveToFirst()) {
    this
} else {
    null
}
