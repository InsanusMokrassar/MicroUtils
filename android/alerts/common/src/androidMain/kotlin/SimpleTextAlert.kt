@file:Suppress("NOTHING_TO_INLINE", "unused")

package dev.inmo.micro_utils.android.alerts.common

import android.app.AlertDialog
import android.content.Context
import androidx.annotation.StringRes

inline fun Context.createSimpleTextAlertDialog(
    text: String,
    title: String? = null,
    positivePair: Pair<String, AlertDialogCallback?>? = null,
    neutralPair: Pair<String, AlertDialogCallback?>? = null,
    negativePair: Pair<String, AlertDialogCallback?>? = null,
    show: Boolean = true
): AlertDialog = createAlertDialogTemplate(
    title,
    positivePair,
    neutralPair,
    negativePair
).apply {
    setMessage(text)
}.create().apply {
    if (show) {
        show()
    }
}

inline fun Context.createSimpleTextAlertDialog(
    @StringRes
    text: Int,
    @StringRes
    title: Int? = null,
    positivePair: Pair<Int, AlertDialogCallback?>? = null,
    neutralPair: Pair<Int, AlertDialogCallback?>? = null,
    negativePair: Pair<Int, AlertDialogCallback?>? = null,
    show: Boolean = true
): AlertDialog = createSimpleTextAlertDialog(
    getString(text),
    title ?.let { getString(it) },
    positivePair ?.let { getString(it.first) to it.second },
    neutralPair ?.let { getString(it.first) to it.second },
    negativePair ?.let { getString(it.first) to it.second },
    show
)
