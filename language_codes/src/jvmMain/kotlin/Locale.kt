package dev.inmo.micro_utils.language_codes

import java.util.Locale

fun IetfLang.toJavaLocale(): Locale = Locale.forLanguageTag(code)
fun IetfLang?.toJavaLocaleOrDefault(): Locale = this?.toJavaLocale() ?: Locale.getDefault()

fun Locale.toIetfLang(): IetfLang = IetfLang(toLanguageTag())
@Deprecated("Renamed", ReplaceWith("this.toIetfLang()", "dev.inmo.micro_utils.language_codes.toIetfLang"))
fun Locale.toIetfLanguageCode(): IetfLang = toIetfLang()
