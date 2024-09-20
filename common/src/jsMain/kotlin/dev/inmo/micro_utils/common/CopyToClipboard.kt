package dev.inmo.micro_utils.common

import kotlinx.browser.window

fun copyToClipboard(text: String): Boolean {
    return runCatching {
        window.navigator.clipboard.writeText(
            text
        )
    }.onFailure {
        it.printStackTrace()
    }.isSuccess
}
