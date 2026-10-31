@file:Suppress("NOTHING_TO_INLINE", "unused")

package dev.inmo.micro_utils.android.alerts.common

import android.app.AlertDialog
import android.content.Context
import android.view.View

inline fun <T: View> Context.createCustomViewAlertDialog(
    title: String? = null,
    positivePair: Pair<String, AlertDialogCallback?>? = null,
    neutralPair: Pair<String, AlertDialogCallback?>? = null,
    negativePair: Pair<String, AlertDialogCallback?>? = null,
    show: Boolean = true,
    viewCreator: (Context) -> T
): AlertDialog = createAlertDialogTemplate(
    title, positivePair, neutralPair, negativePair
).apply {
    setView(viewCreator(this@createCustomViewAlertDialog))
}.create().apply {
    if (show) show()
}

inline fun <T: View> Context.createCustomViewAlertDialogWithResources(
    title: Int? = null,
    positivePair: Pair<Int, AlertDialogCallback?>? = null,
    neutralPair: Pair<Int, AlertDialogCallback?>? = null,
    negativePair: Pair<Int, AlertDialogCallback?>? = null,
    show: Boolean = true,
    viewCreator: (Context) -> T
): AlertDialog = createCustomViewAlertDialog(
    title ?.let { getString(it) },
    positivePair ?.let { getString(it.first) to it.second },
    neutralPair ?.let { getString(it.first) to it.second },
    negativePair ?.let { getString(it.first) to it.second },
    show,
    viewCreator
)
