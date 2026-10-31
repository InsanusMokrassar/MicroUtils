package dev.inmo.micro_utils.common

import kotlinx.browser.window
import kotlinx.coroutines.await
import org.w3c.fetch.Response
import org.w3c.files.Blob
import org.w3c.files.BlobPropertyBag

external class ClipboardItem(data: JsAny?) : JsAny

fun createBlobData(blob: Blob): JsAny = js("""({[blob.type]: blob})""")

inline fun Blob.convertToClipboardItem(): ClipboardItem {
    return ClipboardItem(createBlobData(this))
}

suspend fun copyImageURLToClipboard(imageUrl: String): Boolean {
    return runCatching {
        val response = window.fetch(imageUrl).await<Response>()
        val blob = response.blob().await<Blob>()
        val data = arrayOf(
            Blob(
                arrayOf(blob).toJsArray().unsafeCast(),
                BlobPropertyBag("image/png")
            ).convertToClipboardItem()
        ).toJsArray()
        window.navigator.clipboard.write(data.unsafeCast())
    }.onFailure {
        it.printStackTrace()
    }.isSuccess
}
