package dev.inmo.micro_utils.strings

import dev.inmo.micro_utils.language_codes.toIetfLanguageCode
import java.util.Locale

fun StringResource.translation(locale: Locale): String {
    return translation(locale.toIetfLanguageCode())
}
