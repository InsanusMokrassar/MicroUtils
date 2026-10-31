package dev.inmo.micro_utils.common

import kotlinx.browser.window
import org.w3c.files.Blob
import org.w3c.files.BlobPropertyBag
import kotlin.js.json

external class ClipboardItem(data: dynamic)

inline fun Blob.convertToClipboardItem(): ClipboardItem {
    val itemData: dynamic = json(this.type to this)
    return ClipboardItem(itemData)
}

suspend fun copyImageURLToClipboard(imageUrl: String): Boolean {
    return runCatching {
        val response = window.fetch(imageUrl).await()
        val blob = response.blob().await()
        val data = arrayOf(
            Blob(
                arrayOf(blob),
                BlobPropertyBag("image/png")
            ).convertToClipboardItem()
        ).asDynamic()
        window.navigator.clipboard.write(data)
    }.onFailure {
        it.printStackTrace()
    }.isSuccess
}
