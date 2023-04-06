package dev.inmo.micro_utils.coroutines

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import org.w3c.dom.Image

suspend fun preloadImage(src: String): Image {
    val image = Image()
    image.src = src

    val job = Job()

    image.addEventListener("load", {
        runCatching { job.complete() }
    })

    runCatchingSafely {
        job.join()
    }.onFailure {
        if (it is CancellationException) {
            image.src = ""
        }
    }.getOrThrow()

    return image
}
