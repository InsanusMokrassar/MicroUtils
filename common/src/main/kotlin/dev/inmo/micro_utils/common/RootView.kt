package dev.inmo.micro_utils.common

import android.app.Activity
import android.view.View

val Activity.rootView: View?
    get() = findViewById<View?>(android.R.id.content) ?.rootView ?: window.decorView.findViewById<View?>(android.R.id.content) ?.rootView
