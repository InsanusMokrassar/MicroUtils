@file:Suppress("unused")

package dev.inmo.micro_utils.android.alerts.recyclerview

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.ViewGroup
import android.widget.TextView
import dev.inmo.micro_utils.android.alerts.common.AlertDialogCallback
import dev.inmo.micro_utils.android.recyclerview.*

data class AlertAction(
    val title: String,
    val callback: (DialogInterface) -> Unit
)

private class ActionViewHolder(
    container: ViewGroup, dialogInterfaceGetter: () -> DialogInterface
) : AbstractStandardViewHolder<AlertAction>(container, android.R.layout.simple_list_item_1) {
    private lateinit var action: AlertAction
    private val textView: TextView
        get() = itemView.findViewById(android.R.id.text1)

    init {
        itemView.setOnClickListener {
            action.callback(dialogInterfaceGetter())
        }
    }

    override fun onBind(item: AlertAction) {
        action = item
        textView.text = item.title
    }
}

private class ActionsRecyclerViewAdapter(
    data: List<AlertAction>,
    private val dialogInterfaceGetter: () -> DialogInterface
) : RecyclerViewAdapter<AlertAction>(data) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbstractViewHolder<AlertAction> = ActionViewHolder(
        parent, dialogInterfaceGetter
    )
}

fun Context.createActionsAlertDialog(
    actions: List<AlertAction>,
    title: Int? = null,
    positivePair: Pair<Int, AlertDialogCallback?>? = null,
    neutralPair: Pair<Int, AlertDialogCallback?>? = null,
    negativePair: Pair<Int, AlertDialogCallback?>? = null,
    show: Boolean = true
): AlertDialog {
    lateinit var dialogInterface: DialogInterface

    return createRecyclerViewDialog(
        title, positivePair, neutralPair, negativePair, show
    ) {
        ActionsRecyclerViewAdapter(
            actions
        ) {
            dialogInterface
        }
    }.also { dialogInterface = it }
}
