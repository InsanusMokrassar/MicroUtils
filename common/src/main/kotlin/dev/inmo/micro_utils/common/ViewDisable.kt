@file:Suppress("NOTHING_TO_INLINE", "unused")

package dev.inmo.micro_utils.common

import android.view.View
import android.view.ViewGroup

inline val View.enabled
    get() = isEnabled

inline val View.disabled
    get() = !enabled

fun View.disable() {
    if (this is ViewGroup) {
        (0 until childCount).forEach { getChildAt(it).disable() }
    }
    isEnabled = false
}

fun View.enable() {
    if (this is ViewGroup) {
        (0 until childCount).forEach { getChildAt(it).enable() }
    }
    isEnabled = true
}

fun View.toggleEnabledState(enabled: Boolean) {
    if (enabled) {
        enable()
    } else {
        disable()
    }
}
