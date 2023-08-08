@file:Suppress("NOTHING_TO_INLINE", "unused")

package dev.inmo.micro_utils.common

import android.view.View

inline val View.gone
    get() = visibility == View.GONE
inline fun View.gone() {
    visibility = View.GONE
}

inline val View.hidden
    get() = visibility == View.INVISIBLE
inline fun View.hide() {
    visibility = View.INVISIBLE
}

inline val View.shown
    get() = visibility == View.VISIBLE
inline fun View.show() {
    visibility = View.VISIBLE
}

fun View.toggleVisibility(goneOnHide: Boolean = true) {
    if (isShown) {
        if (goneOnHide) {
            gone()
        } else {
            hide()
        }
    } else {
        show()
    }
}

fun View.changeVisibility(show: Boolean = !isShown, goneOnHide: Boolean = true) {
    if (show) {
        show()
    } else {
        if (goneOnHide) {
            gone()
        } else {
            hide()
        }
    }
}
