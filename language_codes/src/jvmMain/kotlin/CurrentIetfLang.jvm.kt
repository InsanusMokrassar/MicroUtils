package dev.inmo.micro_utils.language_codes

import java.util.Locale

actual val currentIetfLang: IetfLang?
    get() = Locale.getDefault() ?.toIetfLang()