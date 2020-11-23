package dev.inmo.micro_utils.android.recyclerview.alerts

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.inmo.micro_utils.android.alerts.AlertDialogCallback
import dev.inmo.micro_utils.android.alerts.createCustomViewAlertDialogWithResources

fun Context.createRecyclerViewDialog(
    title: Int? = null,
    positivePair: Pair<Int, AlertDialogCallback?>? = null,
    neutralPair: Pair<Int, AlertDialogCallback?>? = null,
    negativePair: Pair<Int, AlertDialogCallback?>? = null,
    show: Boolean = true,
    layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this),
    marginOfRecyclerView: Int = 8, // dp
    recyclerViewSetUp: RecyclerView.() -> Unit = {},
    adapterFactory: () -> RecyclerView.Adapter<*>
): AlertDialog {
    val recyclerView = RecyclerView(this).apply {
        layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            setMargins(marginOfRecyclerView, marginOfRecyclerView, marginOfRecyclerView, marginOfRecyclerView)
        }
        this.layoutManager = layoutManager
        adapter = adapterFactory()
        recyclerViewSetUp()
    }

    return createCustomViewAlertDialogWithResources(
        title,
        positivePair,
        neutralPair,
        negativePair,
        show
    ) {
        recyclerView
    }
}
