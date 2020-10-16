package dev.inmo.micro_utils.coroutines

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred

val <T> T.asDeferred: Deferred<T>
    get() = CompletableDeferred(this)
