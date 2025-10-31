package dev.inmo.micro_utils.language_codes

external interface Navigator {
    val language: String
}

external val navigator: Navigator

actual val currentIetfLang: IetfLang?
    get() = IetfLang(navigator.language)