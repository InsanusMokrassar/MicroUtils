package dev.inmo.micro_utils.strings

import dev.inmo.micro_utils.language_codes.IetfLang
import kotlinx.browser.window
import org.w3c.dom.NavigatorLanguage

fun StringResource.translation(language: NavigatorLanguage) = translation(
    language.language.unsafeCast<String?>() ?.let { IetfLang(it) }
)

fun StringResource.translation() = translation(window.navigator)
