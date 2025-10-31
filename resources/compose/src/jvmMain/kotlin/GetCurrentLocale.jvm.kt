package dev.inmo.micro_utils.resources.compose

import androidx.compose.ui.text.intl.Locale
import dev.inmo.micro_utils.language_codes.IetfLang
import dev.inmo.micro_utils.language_codes.currentIetfLang

@androidx.compose.runtime.Composable
actual fun getCurrentLocale(): IetfLang? {
    return currentIetfLang
}