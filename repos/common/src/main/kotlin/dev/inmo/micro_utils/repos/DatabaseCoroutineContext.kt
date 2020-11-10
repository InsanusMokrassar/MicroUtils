package dev.inmo.micro_utils.repos

import kotlinx.coroutines.newSingleThreadContext
import kotlin.coroutines.CoroutineContext

val DatabaseCoroutineContext: CoroutineContext = newSingleThreadContext("db-context")
