package dev.inmo.micro_utils.coroutines

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import kotlin.js.JsExport

@JsExport
val <T> T.asDeferred: Deferred<T>
    get() = CompletableDeferred(this)
