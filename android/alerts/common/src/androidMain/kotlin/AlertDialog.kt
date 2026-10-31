/**
 * Utility functions for creating Android AlertDialogs with simplified API.
 */
@file:Suppress("NOTHING_TO_INLINE", "unused")

package dev.inmo.micro_utils.android.alerts.common

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface

/**
 * Type alias for alert dialog button callbacks.
 */
typealias AlertDialogCallback = (DialogInterface) -> Unit

/**
 * Creates an [AlertDialog.Builder] template with configurable title and buttons.
 * This provides a simplified API for creating alert dialogs with positive, negative, and neutral buttons.
 *
 * @param title Optional dialog title
 * @param positivePair Optional positive button as a pair of (text, callback)
 * @param neutralPair Optional neutral button as a pair of (text, callback)
 * @param negativePair Optional negative button as a pair of (text, callback)
 * @return An [AlertDialog.Builder] configured with the specified parameters
 */
inline fun Context.createAlertDialogTemplate(
    title: String? = null,
    positivePair: Pair<String, AlertDialogCallback?>? = null,
    neutralPair: Pair<String, AlertDialogCallback?>? = null,
    negativePair: Pair<String, AlertDialogCallback?>? = null
): AlertDialog.Builder {
    val builder = AlertDialog.Builder(this)

    title ?.let {
        builder.setTitle(title)
    }

    positivePair ?. let {
        builder.setPositiveButton(it.first) { di, _ -> it.second ?. invoke(di) }
    }
    negativePair ?. let {
        builder.setNegativeButton(it.first) { di, _ -> it.second ?. invoke(di) }
    }
    neutralPair ?. let {
        builder.setNeutralButton(it.first) { di, _ -> it.second ?. invoke(di) }
    }

    return builder
}

inline fun Context.createAlertDialogTemplateWithResources(
    title: Int? = null,
    positivePair: Pair<Int, AlertDialogCallback?>? = null,
    neutralPair: Pair<Int, AlertDialogCallback?>? = null,
    negativePair: Pair<Int, AlertDialogCallback?>? = null
): AlertDialog.Builder = createAlertDialogTemplate(
    title ?.let { getString(it) },
    positivePair ?.let { getString(it.first) to it.second },
    neutralPair ?.let { getString(it.first) to it.second },
    negativePair ?.let { getString(it.first) to it.second }
)

inline fun AlertDialog.setDismissChecker(noinline checker: () -> Boolean) : AlertDialog {
    setOnDismissListener {
        if (!checker()) {
            show()
        }
    }
    return this
}
