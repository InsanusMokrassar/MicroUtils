package dev.inmo.micro_utils.coroutines.compose

import androidx.compose.runtime.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.job
import kotlin.coroutines.CoroutineContext

fun Composition.linkWithJob(job: Job) {
    job.invokeOnCompletion {
        this@linkWithJob.dispose()
    }
}

fun Composition.linkWithContext(coroutineContext: CoroutineContext) = linkWithJob(coroutineContext.job)
