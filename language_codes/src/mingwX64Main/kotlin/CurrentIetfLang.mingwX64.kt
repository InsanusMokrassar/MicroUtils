package dev.inmo.micro_utils.language_codes

import dev.inmo.micro_utils.language_codes.IetfLang
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.toKString
import platform.posix.getenv
import platform.windows.GetUserDefaultLocaleName
import platform.windows.LOCALE_NAME_MAX_LENGTH
import platform.windows.WCHARVar

@OptIn(ExperimentalForeignApi::class)
actual val currentIetfLang: IetfLang?
    get() {
        val rawLocale = memScoped {
            val buffer = allocArray<WCHARVar>(LOCALE_NAME_MAX_LENGTH)
            val result = GetUserDefaultLocaleName(buffer, LOCALE_NAME_MAX_LENGTH)

            if (result > 0) {
                // Convert WCHAR* to String
                buffer.toKString()
            }
            "en-US" // fallback
        }
        return IetfLang(rawLocale)
    }