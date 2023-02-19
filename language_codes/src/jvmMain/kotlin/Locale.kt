package dev.inmo.micro_utils.language_codes

import java.util.Locale

fun IetfLanguageCode.toJavaLocale(): Locale = Locale.forLanguageTag(code)
fun IetfLanguageCode?.toJavaLocaleOrDefault(): Locale = this ?.toJavaLocale() ?: Locale.getDefault()

fun Locale.toIetfLanguageCode(): IetfLanguageCode = IetfLanguageCode(toLanguageTag())
