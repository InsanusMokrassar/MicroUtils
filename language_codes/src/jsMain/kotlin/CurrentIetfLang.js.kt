package dev.inmo.micro_utils.language_codes

import kotlinx.browser.window

actual val currentIetfLang: IetfLang?
    get() = window.navigator.language.unsafeCast<String?>() ?.let { IetfLang(it) }