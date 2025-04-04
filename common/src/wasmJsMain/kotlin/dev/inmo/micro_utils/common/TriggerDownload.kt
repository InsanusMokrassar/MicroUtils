package dev.inmo.micro_utils.common

import kotlinx.browser.document
import org.w3c.dom.HTMLAnchorElement

fun triggerDownloadFile(filename: String, fileLink: String) {
    val hiddenElement = document.createElement("a") as HTMLAnchorElement

    hiddenElement.href = fileLink
    hiddenElement.target = "_blank"
    hiddenElement.download = filename
    hiddenElement.click()
}

