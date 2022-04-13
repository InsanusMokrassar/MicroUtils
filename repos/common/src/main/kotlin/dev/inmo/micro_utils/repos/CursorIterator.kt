package dev.inmo.micro_utils.repos

import android.database.Cursor

class CursorIterator(
    private val c: Cursor
) : Iterator<Cursor> {
    private var i = 0

    init {
        c.moveToFirst()
    }
    override fun hasNext(): Boolean {
        return i < c.count
    }

    override fun next(): Cursor {
        i++
        return if (c.moveToNext()) {
            c
        } else {
            throw NoSuchElementException()
        }
    }
}

operator fun Cursor.iterator(): CursorIterator = CursorIterator(this)
