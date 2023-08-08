package dev.inmo.micro_utils.android.recyclerview

import android.content.Context
import android.widget.LinearLayout
import androidx.recyclerview.widget.DividerItemDecoration

val Context.recyclerViewItemsDecoration
    get() = DividerItemDecoration(this, LinearLayout.VERTICAL)

