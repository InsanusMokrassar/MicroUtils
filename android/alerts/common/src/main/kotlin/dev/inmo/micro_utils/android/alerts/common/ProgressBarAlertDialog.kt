package dev.inmo.micro_utils.android.alerts.common

import android.content.Context
import android.view.LayoutInflater
import android.widget.TextView
import androidx.annotation.StringRes

fun Context.createProgressBarAlertDialog(
    text: String? = null,
    onClick: (() -> Unit)? = null
) = createCustomViewAlertDialog {
    (it.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(
        R.layout.alert_dialog_progress_bar, null, false
    ).apply {
        onClick ?.let {
            setOnClickListener { onClick() }
        } ?: setOnClickListener { /* do nothing */ }
        text ?.let {
            findViewById<TextView>(R.id.alertDialogProgressBarTitle).text = it
        }
    }
}

fun Context.createProgressBarAlertDialog(
    @StringRes textRes: Int,
    onClick: (() -> Unit)? = null
) = createProgressBarAlertDialog(getString(textRes), onClick)
