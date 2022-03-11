package dev.inmo.micro_utils.common

import kotlinx.browser.document
import kotlinx.dom.createElement
import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLInputElement
import org.w3c.files.get

fun selectFile(
    inputSetup: (HTMLInputElement) -> Unit = {},
    onFailure: (Throwable) -> Unit = {},
    onFile: (MPPFile) -> Unit
) {
    (document.createElement("input") {
        (this as HTMLInputElement).apply {
            type = "file"
            inputSetup(this)
            onchange = {
                runCatching {
                    files ?.get(0) ?: error("File must not be null")
                }.onSuccess {
                    onFile(it)
                }.onFailure {
                    onFailure(it)
                }
            }
        }
    } as HTMLElement).click()
}

