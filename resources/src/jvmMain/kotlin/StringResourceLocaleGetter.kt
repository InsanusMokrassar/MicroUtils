package dev.inmo.micro_utils.strings

import dev.inmo.micro_utils.language_codes.toIetfLang
import java.util.Locale

fun StringResource.translation(locale: Locale = Locale.getDefault()): String {
    return translation(locale.toIetfLang())
}

fun Locale.translation(resource: StringResource): String = resource.translation(this)