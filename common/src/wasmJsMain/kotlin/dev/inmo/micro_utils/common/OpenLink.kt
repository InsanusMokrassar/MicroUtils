package dev.inmo.micro_utils.common

import kotlinx.browser.window

fun openLink(link: String, target: String = "_blank", features: String = "") {
    window.open(link, target, features) ?.focus()
}

