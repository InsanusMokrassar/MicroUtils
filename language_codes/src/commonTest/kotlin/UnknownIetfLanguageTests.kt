package dev.inmo.micro_utils.language_codes

import kotlin.test.Test
import kotlin.test.assertEquals

class UnknownIetfLanguageTests {
    @Test
    fun commonTestOfParentCode() {
        knownLanguageCodes.forEach {
            val language = IetfLang.UnknownIetfLang(it.code)
            assertEquals(it.code, language.code)
            assertEquals(it.withoutDialect, language.withoutDialect)
            assertEquals(it.parentLang ?.code, language.parentLang ?.code)
        }
    }
}