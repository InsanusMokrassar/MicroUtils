package dev.inmo.micro_utils.language_codes

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toKString
import platform.posix.getenv

@OptIn(ExperimentalForeignApi::class)
actual val currentIetfLang: IetfLang?
    get() {
        val localeStr = getenv("LANG") ?.toKString() ?.replace("_", "-") ?: "en-US"
        return IetfLang(localeStr)
    }