package dev.inmo.micro_utils.resources.compose

import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import dev.inmo.micro_utils.language_codes.IetfLang
import dev.inmo.micro_utils.language_codes.toIetfLang

@Composable
actual fun getCurrentLocale(): IetfLang? {
    val configuration = LocalConfiguration.current

    val locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        if (configuration.locales.isEmpty) {
            return null
        }
        configuration.locales.get(0)
    } else {
        @Suppress("DEPRECATION")
        configuration.locale
    }

    return locale.toIetfLang()
}