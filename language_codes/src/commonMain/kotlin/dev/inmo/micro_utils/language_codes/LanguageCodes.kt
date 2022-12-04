@file:Suppress("SERIALIZER_TYPE_INCOMPATIBLE")

package dev.inmo.micro_utils.language_codes

import kotlinx.serialization.Serializable

/**
 * This class has been automatically generated using
 * https://github.com/InsanusMokrassar/MicroUtils/tree/master/language_codes/generator . This generator uses
 * https://datahub.io/core/language-codes/ files (base and tags) and create the whole hierarchy using it.
 */
@Serializable(IetfLanguageCodeSerializer::class)
sealed class IetfLanguageCode {
    abstract val code: String
    abstract val withoutDialect: String

    @Serializable(IetfLanguageCodeSerializer::class)
    object Afar : IetfLanguageCode() { override val code: String = "aa"; override val withoutDialect: String get() = code }
    @Serializable(IetfLanguageCodeSerializer::class)
    object Abkhazian : IetfLanguageCode() { override val code: String = "ab"; override val withoutDialect: String get() = code }
    @Serializable(IetfLanguageCodeSerializer::class)
    object Avestan : IetfLanguageCode() { override val code: String = "ae"; override val withoutDialect: String get() = code }

    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Afrikaans : IetfLanguageCode() {
        override val code: String = "af"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object NA : Afrikaans() { override val code: String = "af-NA"; override val withoutDialect: String get() = Afrikaans.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object ZA : Afrikaans() { override val code: String = "af-ZA"; override val withoutDialect: String get() = Afrikaans.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Afrikaans()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Akan : IetfLanguageCode() {
        override val code: String = "ak"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object GH : Akan() { override val code: String = "ak-GH"; override val withoutDialect: String get() = Akan.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Akan()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Amharic : IetfLanguageCode() {
        override val code: String = "am"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object ET : Amharic() { override val code: String = "am-ET"; override val withoutDialect: String get() = Amharic.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Amharic()
    }

    @Serializable(IetfLanguageCodeSerializer::class)
    object Aragonese : IetfLanguageCode() { override val code: String = "an"; override val withoutDialect: String get() = code }

    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Arabic : IetfLanguageCode() {
        override val code: String = "ar"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object L001 : Arabic() { override val code: String = "ar-001"; override val withoutDialect: String get() = Arabic.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object AE : Arabic() { override val code: String = "ar-AE"; override val withoutDialect: String get() = Arabic.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object BH : Arabic() { override val code: String = "ar-BH"; override val withoutDialect: String get() = Arabic.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object DJ : Arabic() { override val code: String = "ar-DJ"; override val withoutDialect: String get() = Arabic.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object DZ : Arabic() { override val code: String = "ar-DZ"; override val withoutDialect: String get() = Arabic.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object EG : Arabic() { override val code: String = "ar-EG"; override val withoutDialect: String get() = Arabic.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object EH : Arabic() { override val code: String = "ar-EH"; override val withoutDialect: String get() = Arabic.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object ER : Arabic() { override val code: String = "ar-ER"; override val withoutDialect: String get() = Arabic.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object IL : Arabic() { override val code: String = "ar-IL"; override val withoutDialect: String get() = Arabic.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object IQ : Arabic() { override val code: String = "ar-IQ"; override val withoutDialect: String get() = Arabic.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object JO : Arabic() { override val code: String = "ar-JO"; override val withoutDialect: String get() = Arabic.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object KM : Arabic() { override val code: String = "ar-KM"; override val withoutDialect: String get() = Arabic.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object KW : Arabic() { override val code: String = "ar-KW"; override val withoutDialect: String get() = Arabic.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object LB : Arabic() { override val code: String = "ar-LB"; override val withoutDialect: String get() = Arabic.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object LY : Arabic() { override val code: String = "ar-LY"; override val withoutDialect: String get() = Arabic.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object MA : Arabic() { override val code: String = "ar-MA"; override val withoutDialect: String get() = Arabic.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object MR : Arabic() { override val code: String = "ar-MR"; override val withoutDialect: String get() = Arabic.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object OM : Arabic() { override val code: String = "ar-OM"; override val withoutDialect: String get() = Arabic.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object PS : Arabic() { override val code: String = "ar-PS"; override val withoutDialect: String get() = Arabic.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object QA : Arabic() { override val code: String = "ar-QA"; override val withoutDialect: String get() = Arabic.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object SA : Arabic() { override val code: String = "ar-SA"; override val withoutDialect: String get() = Arabic.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object SD : Arabic() { override val code: String = "ar-SD"; override val withoutDialect: String get() = Arabic.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object SO : Arabic() { override val code: String = "ar-SO"; override val withoutDialect: String get() = Arabic.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object SS : Arabic() { override val code: String = "ar-SS"; override val withoutDialect: String get() = Arabic.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object SY : Arabic() { override val code: String = "ar-SY"; override val withoutDialect: String get() = Arabic.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object TD : Arabic() { override val code: String = "ar-TD"; override val withoutDialect: String get() = Arabic.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object TN : Arabic() { override val code: String = "ar-TN"; override val withoutDialect: String get() = Arabic.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object YE : Arabic() { override val code: String = "ar-YE"; override val withoutDialect: String get() = Arabic.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Arabic()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Assamese : IetfLanguageCode() {
        override val code: String = "as"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object IN : Assamese() { override val code: String = "as-IN"; override val withoutDialect: String get() = Assamese.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Assamese()
    }

    @Serializable(IetfLanguageCodeSerializer::class)
    object Avaric : IetfLanguageCode() { override val code: String = "av"; override val withoutDialect: String get() = code }
    @Serializable(IetfLanguageCodeSerializer::class)
    object Aymara : IetfLanguageCode() { override val code: String = "ay"; override val withoutDialect: String get() = code }

    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Azerbaijani : IetfLanguageCode() {
        override val code: String = "az"
        override val withoutDialect: String
            get() = code


        @Serializable(IetfLanguageCodeSerializer::class)
        sealed class Cyrl : Azerbaijani() {
            override val code: String = "az-Cyrl"
            override val withoutDialect: String
                get() = code

            @Serializable(IetfLanguageCodeSerializer::class)
            object AZ : Cyrl() { override val code: String = "az-Cyrl-AZ"; override val withoutDialect: String get() = Cyrl.code }

            @Serializable(IetfLanguageCodeSerializer::class)
            companion object : Cyrl()
        }


        @Serializable(IetfLanguageCodeSerializer::class)
        sealed class Latn : Azerbaijani() {
            override val code: String = "az-Latn"
            override val withoutDialect: String
                get() = code

            @Serializable(IetfLanguageCodeSerializer::class)
            object AZ : Latn() { override val code: String = "az-Latn-AZ"; override val withoutDialect: String get() = Latn.code }

            @Serializable(IetfLanguageCodeSerializer::class)
            companion object : Latn()
        }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Azerbaijani()
    }

    @Serializable(IetfLanguageCodeSerializer::class)
    object Bashkir : IetfLanguageCode() { override val code: String = "ba"; override val withoutDialect: String get() = code }

    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Belarusian : IetfLanguageCode() {
        override val code: String = "be"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object BY : Belarusian() { override val code: String = "be-BY"; override val withoutDialect: String get() = Belarusian.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Belarusian()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Bulgarian : IetfLanguageCode() {
        override val code: String = "bg"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object BG : Bulgarian() { override val code: String = "bg-BG"; override val withoutDialect: String get() = Bulgarian.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Bulgarian()
    }

    @Serializable(IetfLanguageCodeSerializer::class)
    object BihariLanguages : IetfLanguageCode() { override val code: String = "bh"; override val withoutDialect: String get() = code }
    @Serializable(IetfLanguageCodeSerializer::class)
    object Bislama : IetfLanguageCode() { override val code: String = "bi"; override val withoutDialect: String get() = code }

    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Bambara : IetfLanguageCode() {
        override val code: String = "bm"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object ML : Bambara() { override val code: String = "bm-ML"; override val withoutDialect: String get() = Bambara.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Bambara()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Bengali : IetfLanguageCode() {
        override val code: String = "bn"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object BD : Bengali() { override val code: String = "bn-BD"; override val withoutDialect: String get() = Bengali.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object IN : Bengali() { override val code: String = "bn-IN"; override val withoutDialect: String get() = Bengali.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Bengali()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Tibetan : IetfLanguageCode() {
        override val code: String = "bo"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object CN : Tibetan() { override val code: String = "bo-CN"; override val withoutDialect: String get() = Tibetan.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object IN : Tibetan() { override val code: String = "bo-IN"; override val withoutDialect: String get() = Tibetan.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Tibetan()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Breton : IetfLanguageCode() {
        override val code: String = "br"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object FR : Breton() { override val code: String = "br-FR"; override val withoutDialect: String get() = Breton.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Breton()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Bosnian : IetfLanguageCode() {
        override val code: String = "bs"
        override val withoutDialect: String
            get() = code


        @Serializable(IetfLanguageCodeSerializer::class)
        sealed class Cyrl : Bosnian() {
            override val code: String = "bs-Cyrl"
            override val withoutDialect: String
                get() = code

            @Serializable(IetfLanguageCodeSerializer::class)
            object BA : Cyrl() { override val code: String = "bs-Cyrl-BA"; override val withoutDialect: String get() = Cyrl.code }

            @Serializable(IetfLanguageCodeSerializer::class)
            companion object : Cyrl()
        }


        @Serializable(IetfLanguageCodeSerializer::class)
        sealed class Latn : Bosnian() {
            override val code: String = "bs-Latn"
            override val withoutDialect: String
                get() = code

            @Serializable(IetfLanguageCodeSerializer::class)
            object BA : Latn() { override val code: String = "bs-Latn-BA"; override val withoutDialect: String get() = Latn.code }

            @Serializable(IetfLanguageCodeSerializer::class)
            companion object : Latn()
        }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Bosnian()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class CatalanValencian : IetfLanguageCode() {
        override val code: String = "ca"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object AD : CatalanValencian() { override val code: String = "ca-AD"; override val withoutDialect: String get() = CatalanValencian.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        sealed class ES : CatalanValencian() {
            override val code: String = "ca-ES"
            override val withoutDialect: String
                get() = code

            @Serializable(IetfLanguageCodeSerializer::class)
            object VALENCIA : ES() { override val code: String = "ca-ES-VALENCIA"; override val withoutDialect: String get() = ES.code }

            @Serializable(IetfLanguageCodeSerializer::class)
            companion object : ES()
        }

        @Serializable(IetfLanguageCodeSerializer::class)
        object FR : CatalanValencian() { override val code: String = "ca-FR"; override val withoutDialect: String get() = CatalanValencian.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object IT : CatalanValencian() { override val code: String = "ca-IT"; override val withoutDialect: String get() = CatalanValencian.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : CatalanValencian()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Chechen : IetfLanguageCode() {
        override val code: String = "ce"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object RU : Chechen() { override val code: String = "ce-RU"; override val withoutDialect: String get() = Chechen.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Chechen()
    }

    @Serializable(IetfLanguageCodeSerializer::class)
    object Chamorro : IetfLanguageCode() { override val code: String = "ch"; override val withoutDialect: String get() = code }
    @Serializable(IetfLanguageCodeSerializer::class)
    object Corsican : IetfLanguageCode() { override val code: String = "co"; override val withoutDialect: String get() = code }
    @Serializable(IetfLanguageCodeSerializer::class)
    object Cree : IetfLanguageCode() { override val code: String = "cr"; override val withoutDialect: String get() = code }

    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Czech : IetfLanguageCode() {
        override val code: String = "cs"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object CZ : Czech() { override val code: String = "cs-CZ"; override val withoutDialect: String get() = Czech.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Czech()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class ChurchSlavicOldSlavonicChurchSlavonicOldBulgarianOldChurchSlavonic : IetfLanguageCode() {
        override val code: String = "cu"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object RU : ChurchSlavicOldSlavonicChurchSlavonicOldBulgarianOldChurchSlavonic() { override val code: String = "cu-RU"; override val withoutDialect: String get() = ChurchSlavicOldSlavonicChurchSlavonicOldBulgarianOldChurchSlavonic.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : ChurchSlavicOldSlavonicChurchSlavonicOldBulgarianOldChurchSlavonic()
    }

    @Serializable(IetfLanguageCodeSerializer::class)
    object Chuvash : IetfLanguageCode() { override val code: String = "cv"; override val withoutDialect: String get() = code }

    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Welsh : IetfLanguageCode() {
        override val code: String = "cy"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object GB : Welsh() { override val code: String = "cy-GB"; override val withoutDialect: String get() = Welsh.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Welsh()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Danish : IetfLanguageCode() {
        override val code: String = "da"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object DK : Danish() { override val code: String = "da-DK"; override val withoutDialect: String get() = Danish.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object GL : Danish() { override val code: String = "da-GL"; override val withoutDialect: String get() = Danish.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Danish()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class German : IetfLanguageCode() {
        override val code: String = "de"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object AT : German() { override val code: String = "de-AT"; override val withoutDialect: String get() = German.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object BE : German() { override val code: String = "de-BE"; override val withoutDialect: String get() = German.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object CH : German() { override val code: String = "de-CH"; override val withoutDialect: String get() = German.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object DE : German() { override val code: String = "de-DE"; override val withoutDialect: String get() = German.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object IT : German() { override val code: String = "de-IT"; override val withoutDialect: String get() = German.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object LI : German() { override val code: String = "de-LI"; override val withoutDialect: String get() = German.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object LU : German() { override val code: String = "de-LU"; override val withoutDialect: String get() = German.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : German()
    }

    @Serializable(IetfLanguageCodeSerializer::class)
    object DivehiDhivehiMaldivian : IetfLanguageCode() { override val code: String = "dv"; override val withoutDialect: String get() = code }

    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Dzongkha : IetfLanguageCode() {
        override val code: String = "dz"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object BT : Dzongkha() { override val code: String = "dz-BT"; override val withoutDialect: String get() = Dzongkha.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Dzongkha()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Ewe : IetfLanguageCode() {
        override val code: String = "ee"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object GH : Ewe() { override val code: String = "ee-GH"; override val withoutDialect: String get() = Ewe.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object TG : Ewe() { override val code: String = "ee-TG"; override val withoutDialect: String get() = Ewe.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Ewe()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class GreekModern1453 : IetfLanguageCode() {
        override val code: String = "el"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object CY : GreekModern1453() { override val code: String = "el-CY"; override val withoutDialect: String get() = GreekModern1453.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object GR : GreekModern1453() { override val code: String = "el-GR"; override val withoutDialect: String get() = GreekModern1453.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : GreekModern1453()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class English : IetfLanguageCode() {
        override val code: String = "en"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object L001 : English() { override val code: String = "en-001"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object L150 : English() { override val code: String = "en-150"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object AE : English() { override val code: String = "en-AE"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object AG : English() { override val code: String = "en-AG"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object AI : English() { override val code: String = "en-AI"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object AS : English() { override val code: String = "en-AS"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object AT : English() { override val code: String = "en-AT"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object AU : English() { override val code: String = "en-AU"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object BB : English() { override val code: String = "en-BB"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object BE : English() { override val code: String = "en-BE"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object BI : English() { override val code: String = "en-BI"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object BM : English() { override val code: String = "en-BM"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object BS : English() { override val code: String = "en-BS"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object BW : English() { override val code: String = "en-BW"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object BZ : English() { override val code: String = "en-BZ"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object CA : English() { override val code: String = "en-CA"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object CC : English() { override val code: String = "en-CC"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object CH : English() { override val code: String = "en-CH"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object CK : English() { override val code: String = "en-CK"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object CM : English() { override val code: String = "en-CM"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object CX : English() { override val code: String = "en-CX"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object CY : English() { override val code: String = "en-CY"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object DE : English() { override val code: String = "en-DE"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object DG : English() { override val code: String = "en-DG"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object DK : English() { override val code: String = "en-DK"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object DM : English() { override val code: String = "en-DM"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object ER : English() { override val code: String = "en-ER"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object FI : English() { override val code: String = "en-FI"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object FJ : English() { override val code: String = "en-FJ"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object FK : English() { override val code: String = "en-FK"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object FM : English() { override val code: String = "en-FM"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object GB : English() { override val code: String = "en-GB"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object GD : English() { override val code: String = "en-GD"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object GG : English() { override val code: String = "en-GG"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object GH : English() { override val code: String = "en-GH"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object GI : English() { override val code: String = "en-GI"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object GM : English() { override val code: String = "en-GM"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object GU : English() { override val code: String = "en-GU"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object GY : English() { override val code: String = "en-GY"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object HK : English() { override val code: String = "en-HK"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object IE : English() { override val code: String = "en-IE"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object IL : English() { override val code: String = "en-IL"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object IM : English() { override val code: String = "en-IM"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object IN : English() { override val code: String = "en-IN"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object IO : English() { override val code: String = "en-IO"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object JE : English() { override val code: String = "en-JE"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object JM : English() { override val code: String = "en-JM"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object KE : English() { override val code: String = "en-KE"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object KI : English() { override val code: String = "en-KI"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object KN : English() { override val code: String = "en-KN"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object KY : English() { override val code: String = "en-KY"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object LC : English() { override val code: String = "en-LC"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object LR : English() { override val code: String = "en-LR"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object LS : English() { override val code: String = "en-LS"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object MG : English() { override val code: String = "en-MG"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object MH : English() { override val code: String = "en-MH"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object MO : English() { override val code: String = "en-MO"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object MP : English() { override val code: String = "en-MP"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object MS : English() { override val code: String = "en-MS"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object MT : English() { override val code: String = "en-MT"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object MU : English() { override val code: String = "en-MU"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object MW : English() { override val code: String = "en-MW"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object MY : English() { override val code: String = "en-MY"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object NA : English() { override val code: String = "en-NA"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object NF : English() { override val code: String = "en-NF"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object NG : English() { override val code: String = "en-NG"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object NL : English() { override val code: String = "en-NL"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object NR : English() { override val code: String = "en-NR"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object NU : English() { override val code: String = "en-NU"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object NZ : English() { override val code: String = "en-NZ"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object PG : English() { override val code: String = "en-PG"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object PH : English() { override val code: String = "en-PH"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object PK : English() { override val code: String = "en-PK"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object PN : English() { override val code: String = "en-PN"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object PR : English() { override val code: String = "en-PR"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object PW : English() { override val code: String = "en-PW"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object RW : English() { override val code: String = "en-RW"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object SB : English() { override val code: String = "en-SB"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object SC : English() { override val code: String = "en-SC"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object SD : English() { override val code: String = "en-SD"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object SE : English() { override val code: String = "en-SE"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object SG : English() { override val code: String = "en-SG"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object SH : English() { override val code: String = "en-SH"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object SI : English() { override val code: String = "en-SI"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object SL : English() { override val code: String = "en-SL"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object SS : English() { override val code: String = "en-SS"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object SX : English() { override val code: String = "en-SX"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object SZ : English() { override val code: String = "en-SZ"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object TC : English() { override val code: String = "en-TC"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object TK : English() { override val code: String = "en-TK"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object TO : English() { override val code: String = "en-TO"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object TT : English() { override val code: String = "en-TT"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object TV : English() { override val code: String = "en-TV"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object TZ : English() { override val code: String = "en-TZ"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object UG : English() { override val code: String = "en-UG"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object UM : English() { override val code: String = "en-UM"; override val withoutDialect: String get() = English.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        sealed class US : English() {
            override val code: String = "en-US"
            override val withoutDialect: String
                get() = code

            @Serializable(IetfLanguageCodeSerializer::class)
            object POSIX : US() { override val code: String = "en-US-POSIX"; override val withoutDialect: String get() = US.code }

            @Serializable(IetfLanguageCodeSerializer::class)
            companion object : US()
        }

        @Serializable(IetfLanguageCodeSerializer::class)
        object VC : English() { override val code: String = "en-VC"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object VG : English() { override val code: String = "en-VG"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object VI : English() { override val code: String = "en-VI"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object VU : English() { override val code: String = "en-VU"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object WS : English() { override val code: String = "en-WS"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object ZA : English() { override val code: String = "en-ZA"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object ZM : English() { override val code: String = "en-ZM"; override val withoutDialect: String get() = English.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object ZW : English() { override val code: String = "en-ZW"; override val withoutDialect: String get() = English.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : English()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Esperanto : IetfLanguageCode() {
        override val code: String = "eo"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object L001 : Esperanto() { override val code: String = "eo-001"; override val withoutDialect: String get() = Esperanto.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Esperanto()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class SpanishCastilian : IetfLanguageCode() {
        override val code: String = "es"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object L419 : SpanishCastilian() { override val code: String = "es-419"; override val withoutDialect: String get() = SpanishCastilian.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object AR : SpanishCastilian() { override val code: String = "es-AR"; override val withoutDialect: String get() = SpanishCastilian.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object BO : SpanishCastilian() { override val code: String = "es-BO"; override val withoutDialect: String get() = SpanishCastilian.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object BR : SpanishCastilian() { override val code: String = "es-BR"; override val withoutDialect: String get() = SpanishCastilian.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object BZ : SpanishCastilian() { override val code: String = "es-BZ"; override val withoutDialect: String get() = SpanishCastilian.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object CL : SpanishCastilian() { override val code: String = "es-CL"; override val withoutDialect: String get() = SpanishCastilian.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object CO : SpanishCastilian() { override val code: String = "es-CO"; override val withoutDialect: String get() = SpanishCastilian.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object CR : SpanishCastilian() { override val code: String = "es-CR"; override val withoutDialect: String get() = SpanishCastilian.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object CU : SpanishCastilian() { override val code: String = "es-CU"; override val withoutDialect: String get() = SpanishCastilian.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object DO : SpanishCastilian() { override val code: String = "es-DO"; override val withoutDialect: String get() = SpanishCastilian.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object EA : SpanishCastilian() { override val code: String = "es-EA"; override val withoutDialect: String get() = SpanishCastilian.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object EC : SpanishCastilian() { override val code: String = "es-EC"; override val withoutDialect: String get() = SpanishCastilian.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object ES : SpanishCastilian() { override val code: String = "es-ES"; override val withoutDialect: String get() = SpanishCastilian.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object GQ : SpanishCastilian() { override val code: String = "es-GQ"; override val withoutDialect: String get() = SpanishCastilian.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object GT : SpanishCastilian() { override val code: String = "es-GT"; override val withoutDialect: String get() = SpanishCastilian.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object HN : SpanishCastilian() { override val code: String = "es-HN"; override val withoutDialect: String get() = SpanishCastilian.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object IC : SpanishCastilian() { override val code: String = "es-IC"; override val withoutDialect: String get() = SpanishCastilian.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object MX : SpanishCastilian() { override val code: String = "es-MX"; override val withoutDialect: String get() = SpanishCastilian.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object NI : SpanishCastilian() { override val code: String = "es-NI"; override val withoutDialect: String get() = SpanishCastilian.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object PA : SpanishCastilian() { override val code: String = "es-PA"; override val withoutDialect: String get() = SpanishCastilian.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object PE : SpanishCastilian() { override val code: String = "es-PE"; override val withoutDialect: String get() = SpanishCastilian.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object PH : SpanishCastilian() { override val code: String = "es-PH"; override val withoutDialect: String get() = SpanishCastilian.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object PR : SpanishCastilian() { override val code: String = "es-PR"; override val withoutDialect: String get() = SpanishCastilian.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object PY : SpanishCastilian() { override val code: String = "es-PY"; override val withoutDialect: String get() = SpanishCastilian.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object SV : SpanishCastilian() { override val code: String = "es-SV"; override val withoutDialect: String get() = SpanishCastilian.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object US : SpanishCastilian() { override val code: String = "es-US"; override val withoutDialect: String get() = SpanishCastilian.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object UY : SpanishCastilian() { override val code: String = "es-UY"; override val withoutDialect: String get() = SpanishCastilian.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object VE : SpanishCastilian() { override val code: String = "es-VE"; override val withoutDialect: String get() = SpanishCastilian.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : SpanishCastilian()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Estonian : IetfLanguageCode() {
        override val code: String = "et"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object EE : Estonian() { override val code: String = "et-EE"; override val withoutDialect: String get() = Estonian.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Estonian()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Basque : IetfLanguageCode() {
        override val code: String = "eu"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object ES : Basque() { override val code: String = "eu-ES"; override val withoutDialect: String get() = Basque.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Basque()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Persian : IetfLanguageCode() {
        override val code: String = "fa"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object AF : Persian() { override val code: String = "fa-AF"; override val withoutDialect: String get() = Persian.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object IR : Persian() { override val code: String = "fa-IR"; override val withoutDialect: String get() = Persian.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Persian()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Fulah : IetfLanguageCode() {
        override val code: String = "ff"
        override val withoutDialect: String
            get() = code


        @Serializable(IetfLanguageCodeSerializer::class)
        sealed class Adlm : Fulah() {
            override val code: String = "ff-Adlm"
            override val withoutDialect: String
                get() = code

            @Serializable(IetfLanguageCodeSerializer::class)
            object BF : Adlm() { override val code: String = "ff-Adlm-BF"; override val withoutDialect: String get() = Adlm.code }
            @Serializable(IetfLanguageCodeSerializer::class)
            object CM : Adlm() { override val code: String = "ff-Adlm-CM"; override val withoutDialect: String get() = Adlm.code }
            @Serializable(IetfLanguageCodeSerializer::class)
            object GH : Adlm() { override val code: String = "ff-Adlm-GH"; override val withoutDialect: String get() = Adlm.code }
            @Serializable(IetfLanguageCodeSerializer::class)
            object GM : Adlm() { override val code: String = "ff-Adlm-GM"; override val withoutDialect: String get() = Adlm.code }
            @Serializable(IetfLanguageCodeSerializer::class)
            object GN : Adlm() { override val code: String = "ff-Adlm-GN"; override val withoutDialect: String get() = Adlm.code }
            @Serializable(IetfLanguageCodeSerializer::class)
            object GW : Adlm() { override val code: String = "ff-Adlm-GW"; override val withoutDialect: String get() = Adlm.code }
            @Serializable(IetfLanguageCodeSerializer::class)
            object LR : Adlm() { override val code: String = "ff-Adlm-LR"; override val withoutDialect: String get() = Adlm.code }
            @Serializable(IetfLanguageCodeSerializer::class)
            object MR : Adlm() { override val code: String = "ff-Adlm-MR"; override val withoutDialect: String get() = Adlm.code }
            @Serializable(IetfLanguageCodeSerializer::class)
            object NE : Adlm() { override val code: String = "ff-Adlm-NE"; override val withoutDialect: String get() = Adlm.code }
            @Serializable(IetfLanguageCodeSerializer::class)
            object NG : Adlm() { override val code: String = "ff-Adlm-NG"; override val withoutDialect: String get() = Adlm.code }
            @Serializable(IetfLanguageCodeSerializer::class)
            object SL : Adlm() { override val code: String = "ff-Adlm-SL"; override val withoutDialect: String get() = Adlm.code }
            @Serializable(IetfLanguageCodeSerializer::class)
            object SN : Adlm() { override val code: String = "ff-Adlm-SN"; override val withoutDialect: String get() = Adlm.code }

            @Serializable(IetfLanguageCodeSerializer::class)
            companion object : Adlm()
        }


        @Serializable(IetfLanguageCodeSerializer::class)
        sealed class Latn : Fulah() {
            override val code: String = "ff-Latn"
            override val withoutDialect: String
                get() = code

            @Serializable(IetfLanguageCodeSerializer::class)
            object BF : Latn() { override val code: String = "ff-Latn-BF"; override val withoutDialect: String get() = Latn.code }
            @Serializable(IetfLanguageCodeSerializer::class)
            object CM : Latn() { override val code: String = "ff-Latn-CM"; override val withoutDialect: String get() = Latn.code }
            @Serializable(IetfLanguageCodeSerializer::class)
            object GH : Latn() { override val code: String = "ff-Latn-GH"; override val withoutDialect: String get() = Latn.code }
            @Serializable(IetfLanguageCodeSerializer::class)
            object GM : Latn() { override val code: String = "ff-Latn-GM"; override val withoutDialect: String get() = Latn.code }
            @Serializable(IetfLanguageCodeSerializer::class)
            object GN : Latn() { override val code: String = "ff-Latn-GN"; override val withoutDialect: String get() = Latn.code }
            @Serializable(IetfLanguageCodeSerializer::class)
            object GW : Latn() { override val code: String = "ff-Latn-GW"; override val withoutDialect: String get() = Latn.code }
            @Serializable(IetfLanguageCodeSerializer::class)
            object LR : Latn() { override val code: String = "ff-Latn-LR"; override val withoutDialect: String get() = Latn.code }
            @Serializable(IetfLanguageCodeSerializer::class)
            object MR : Latn() { override val code: String = "ff-Latn-MR"; override val withoutDialect: String get() = Latn.code }
            @Serializable(IetfLanguageCodeSerializer::class)
            object NE : Latn() { override val code: String = "ff-Latn-NE"; override val withoutDialect: String get() = Latn.code }
            @Serializable(IetfLanguageCodeSerializer::class)
            object NG : Latn() { override val code: String = "ff-Latn-NG"; override val withoutDialect: String get() = Latn.code }
            @Serializable(IetfLanguageCodeSerializer::class)
            object SL : Latn() { override val code: String = "ff-Latn-SL"; override val withoutDialect: String get() = Latn.code }
            @Serializable(IetfLanguageCodeSerializer::class)
            object SN : Latn() { override val code: String = "ff-Latn-SN"; override val withoutDialect: String get() = Latn.code }

            @Serializable(IetfLanguageCodeSerializer::class)
            companion object : Latn()
        }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Fulah()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Finnish : IetfLanguageCode() {
        override val code: String = "fi"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object FI : Finnish() { override val code: String = "fi-FI"; override val withoutDialect: String get() = Finnish.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Finnish()
    }

    @Serializable(IetfLanguageCodeSerializer::class)
    object Fijian : IetfLanguageCode() { override val code: String = "fj"; override val withoutDialect: String get() = code }

    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Faroese : IetfLanguageCode() {
        override val code: String = "fo"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object DK : Faroese() { override val code: String = "fo-DK"; override val withoutDialect: String get() = Faroese.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object FO : Faroese() { override val code: String = "fo-FO"; override val withoutDialect: String get() = Faroese.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Faroese()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class French : IetfLanguageCode() {
        override val code: String = "fr"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object BE : French() { override val code: String = "fr-BE"; override val withoutDialect: String get() = French.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object BF : French() { override val code: String = "fr-BF"; override val withoutDialect: String get() = French.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object BI : French() { override val code: String = "fr-BI"; override val withoutDialect: String get() = French.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object BJ : French() { override val code: String = "fr-BJ"; override val withoutDialect: String get() = French.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object BL : French() { override val code: String = "fr-BL"; override val withoutDialect: String get() = French.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object CA : French() { override val code: String = "fr-CA"; override val withoutDialect: String get() = French.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object CD : French() { override val code: String = "fr-CD"; override val withoutDialect: String get() = French.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object CF : French() { override val code: String = "fr-CF"; override val withoutDialect: String get() = French.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object CG : French() { override val code: String = "fr-CG"; override val withoutDialect: String get() = French.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object CH : French() { override val code: String = "fr-CH"; override val withoutDialect: String get() = French.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object CI : French() { override val code: String = "fr-CI"; override val withoutDialect: String get() = French.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object CM : French() { override val code: String = "fr-CM"; override val withoutDialect: String get() = French.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object DJ : French() { override val code: String = "fr-DJ"; override val withoutDialect: String get() = French.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object DZ : French() { override val code: String = "fr-DZ"; override val withoutDialect: String get() = French.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object FR : French() { override val code: String = "fr-FR"; override val withoutDialect: String get() = French.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object GA : French() { override val code: String = "fr-GA"; override val withoutDialect: String get() = French.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object GF : French() { override val code: String = "fr-GF"; override val withoutDialect: String get() = French.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object GN : French() { override val code: String = "fr-GN"; override val withoutDialect: String get() = French.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object GP : French() { override val code: String = "fr-GP"; override val withoutDialect: String get() = French.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object GQ : French() { override val code: String = "fr-GQ"; override val withoutDialect: String get() = French.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object HT : French() { override val code: String = "fr-HT"; override val withoutDialect: String get() = French.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object KM : French() { override val code: String = "fr-KM"; override val withoutDialect: String get() = French.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object LU : French() { override val code: String = "fr-LU"; override val withoutDialect: String get() = French.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object MA : French() { override val code: String = "fr-MA"; override val withoutDialect: String get() = French.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object MC : French() { override val code: String = "fr-MC"; override val withoutDialect: String get() = French.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object MF : French() { override val code: String = "fr-MF"; override val withoutDialect: String get() = French.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object MG : French() { override val code: String = "fr-MG"; override val withoutDialect: String get() = French.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object ML : French() { override val code: String = "fr-ML"; override val withoutDialect: String get() = French.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object MQ : French() { override val code: String = "fr-MQ"; override val withoutDialect: String get() = French.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object MR : French() { override val code: String = "fr-MR"; override val withoutDialect: String get() = French.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object MU : French() { override val code: String = "fr-MU"; override val withoutDialect: String get() = French.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object NC : French() { override val code: String = "fr-NC"; override val withoutDialect: String get() = French.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object NE : French() { override val code: String = "fr-NE"; override val withoutDialect: String get() = French.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object PF : French() { override val code: String = "fr-PF"; override val withoutDialect: String get() = French.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object PM : French() { override val code: String = "fr-PM"; override val withoutDialect: String get() = French.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object RE : French() { override val code: String = "fr-RE"; override val withoutDialect: String get() = French.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object RW : French() { override val code: String = "fr-RW"; override val withoutDialect: String get() = French.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object SC : French() { override val code: String = "fr-SC"; override val withoutDialect: String get() = French.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object SN : French() { override val code: String = "fr-SN"; override val withoutDialect: String get() = French.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object SY : French() { override val code: String = "fr-SY"; override val withoutDialect: String get() = French.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object TD : French() { override val code: String = "fr-TD"; override val withoutDialect: String get() = French.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object TG : French() { override val code: String = "fr-TG"; override val withoutDialect: String get() = French.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object TN : French() { override val code: String = "fr-TN"; override val withoutDialect: String get() = French.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object VU : French() { override val code: String = "fr-VU"; override val withoutDialect: String get() = French.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object WF : French() { override val code: String = "fr-WF"; override val withoutDialect: String get() = French.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object YT : French() { override val code: String = "fr-YT"; override val withoutDialect: String get() = French.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : French()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class WesternFrisian : IetfLanguageCode() {
        override val code: String = "fy"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object NL : WesternFrisian() { override val code: String = "fy-NL"; override val withoutDialect: String get() = WesternFrisian.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : WesternFrisian()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Irish : IetfLanguageCode() {
        override val code: String = "ga"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object GB : Irish() { override val code: String = "ga-GB"; override val withoutDialect: String get() = Irish.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object IE : Irish() { override val code: String = "ga-IE"; override val withoutDialect: String get() = Irish.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Irish()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class GaelicScottishGaelic : IetfLanguageCode() {
        override val code: String = "gd"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object GB : GaelicScottishGaelic() { override val code: String = "gd-GB"; override val withoutDialect: String get() = GaelicScottishGaelic.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : GaelicScottishGaelic()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Galician : IetfLanguageCode() {
        override val code: String = "gl"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object ES : Galician() { override val code: String = "gl-ES"; override val withoutDialect: String get() = Galician.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Galician()
    }

    @Serializable(IetfLanguageCodeSerializer::class)
    object Guarani : IetfLanguageCode() { override val code: String = "gn"; override val withoutDialect: String get() = code }

    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Gujarati : IetfLanguageCode() {
        override val code: String = "gu"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object IN : Gujarati() { override val code: String = "gu-IN"; override val withoutDialect: String get() = Gujarati.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Gujarati()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Manx : IetfLanguageCode() {
        override val code: String = "gv"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object IM : Manx() { override val code: String = "gv-IM"; override val withoutDialect: String get() = Manx.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Manx()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Hausa : IetfLanguageCode() {
        override val code: String = "ha"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object GH : Hausa() { override val code: String = "ha-GH"; override val withoutDialect: String get() = Hausa.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object NE : Hausa() { override val code: String = "ha-NE"; override val withoutDialect: String get() = Hausa.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object NG : Hausa() { override val code: String = "ha-NG"; override val withoutDialect: String get() = Hausa.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Hausa()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Hebrew : IetfLanguageCode() {
        override val code: String = "he"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object IL : Hebrew() { override val code: String = "he-IL"; override val withoutDialect: String get() = Hebrew.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Hebrew()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Hindi : IetfLanguageCode() {
        override val code: String = "hi"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object IN : Hindi() { override val code: String = "hi-IN"; override val withoutDialect: String get() = Hindi.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Hindi()
    }

    @Serializable(IetfLanguageCodeSerializer::class)
    object HiriMotu : IetfLanguageCode() { override val code: String = "ho"; override val withoutDialect: String get() = code }

    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Croatian : IetfLanguageCode() {
        override val code: String = "hr"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object BA : Croatian() { override val code: String = "hr-BA"; override val withoutDialect: String get() = Croatian.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object HR : Croatian() { override val code: String = "hr-HR"; override val withoutDialect: String get() = Croatian.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Croatian()
    }

    @Serializable(IetfLanguageCodeSerializer::class)
    object HaitianHaitianCreole : IetfLanguageCode() { override val code: String = "ht"; override val withoutDialect: String get() = code }

    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Hungarian : IetfLanguageCode() {
        override val code: String = "hu"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object HU : Hungarian() { override val code: String = "hu-HU"; override val withoutDialect: String get() = Hungarian.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Hungarian()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Armenian : IetfLanguageCode() {
        override val code: String = "hy"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object AM : Armenian() { override val code: String = "hy-AM"; override val withoutDialect: String get() = Armenian.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Armenian()
    }

    @Serializable(IetfLanguageCodeSerializer::class)
    object Herero : IetfLanguageCode() { override val code: String = "hz"; override val withoutDialect: String get() = code }

    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class InterlinguaInternationalAuxiliaryLanguageAssociation : IetfLanguageCode() {
        override val code: String = "ia"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object L001 : InterlinguaInternationalAuxiliaryLanguageAssociation() { override val code: String = "ia-001"; override val withoutDialect: String get() = InterlinguaInternationalAuxiliaryLanguageAssociation.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : InterlinguaInternationalAuxiliaryLanguageAssociation()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Indonesian : IetfLanguageCode() {
        override val code: String = "id"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object ID : Indonesian() { override val code: String = "id-ID"; override val withoutDialect: String get() = Indonesian.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Indonesian()
    }

    @Serializable(IetfLanguageCodeSerializer::class)
    object InterlingueOccidental : IetfLanguageCode() { override val code: String = "ie"; override val withoutDialect: String get() = code }

    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Igbo : IetfLanguageCode() {
        override val code: String = "ig"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object NG : Igbo() { override val code: String = "ig-NG"; override val withoutDialect: String get() = Igbo.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Igbo()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class SichuanYiNuosu : IetfLanguageCode() {
        override val code: String = "ii"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object CN : SichuanYiNuosu() { override val code: String = "ii-CN"; override val withoutDialect: String get() = SichuanYiNuosu.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : SichuanYiNuosu()
    }

    @Serializable(IetfLanguageCodeSerializer::class)
    object Inupiaq : IetfLanguageCode() { override val code: String = "ik"; override val withoutDialect: String get() = code }
    @Serializable(IetfLanguageCodeSerializer::class)
    object Ido : IetfLanguageCode() { override val code: String = "io"; override val withoutDialect: String get() = code }

    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Icelandic : IetfLanguageCode() {
        override val code: String = "is"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object IS : Icelandic() { override val code: String = "is-IS"; override val withoutDialect: String get() = Icelandic.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Icelandic()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Italian : IetfLanguageCode() {
        override val code: String = "it"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object CH : Italian() { override val code: String = "it-CH"; override val withoutDialect: String get() = Italian.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object IT : Italian() { override val code: String = "it-IT"; override val withoutDialect: String get() = Italian.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object SM : Italian() { override val code: String = "it-SM"; override val withoutDialect: String get() = Italian.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object VA : Italian() { override val code: String = "it-VA"; override val withoutDialect: String get() = Italian.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Italian()
    }

    @Serializable(IetfLanguageCodeSerializer::class)
    object Inuktitut : IetfLanguageCode() { override val code: String = "iu"; override val withoutDialect: String get() = code }

    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Japanese : IetfLanguageCode() {
        override val code: String = "ja"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object JP : Japanese() { override val code: String = "ja-JP"; override val withoutDialect: String get() = Japanese.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Japanese()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Javanese : IetfLanguageCode() {
        override val code: String = "jv"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object ID : Javanese() { override val code: String = "jv-ID"; override val withoutDialect: String get() = Javanese.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Javanese()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Georgian : IetfLanguageCode() {
        override val code: String = "ka"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object GE : Georgian() { override val code: String = "ka-GE"; override val withoutDialect: String get() = Georgian.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Georgian()
    }

    @Serializable(IetfLanguageCodeSerializer::class)
    object Kongo : IetfLanguageCode() { override val code: String = "kg"; override val withoutDialect: String get() = code }

    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class KikuyuGikuyu : IetfLanguageCode() {
        override val code: String = "ki"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object KE : KikuyuGikuyu() { override val code: String = "ki-KE"; override val withoutDialect: String get() = KikuyuGikuyu.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : KikuyuGikuyu()
    }

    @Serializable(IetfLanguageCodeSerializer::class)
    object KuanyamaKwanyama : IetfLanguageCode() { override val code: String = "kj"; override val withoutDialect: String get() = code }

    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Kazakh : IetfLanguageCode() {
        override val code: String = "kk"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object KZ : Kazakh() { override val code: String = "kk-KZ"; override val withoutDialect: String get() = Kazakh.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Kazakh()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class KalaallisutGreenlandic : IetfLanguageCode() {
        override val code: String = "kl"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object GL : KalaallisutGreenlandic() { override val code: String = "kl-GL"; override val withoutDialect: String get() = KalaallisutGreenlandic.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : KalaallisutGreenlandic()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class CentralKhmer : IetfLanguageCode() {
        override val code: String = "km"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object KH : CentralKhmer() { override val code: String = "km-KH"; override val withoutDialect: String get() = CentralKhmer.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : CentralKhmer()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Kannada : IetfLanguageCode() {
        override val code: String = "kn"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object IN : Kannada() { override val code: String = "kn-IN"; override val withoutDialect: String get() = Kannada.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Kannada()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Korean : IetfLanguageCode() {
        override val code: String = "ko"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object KP : Korean() { override val code: String = "ko-KP"; override val withoutDialect: String get() = Korean.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object KR : Korean() { override val code: String = "ko-KR"; override val withoutDialect: String get() = Korean.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Korean()
    }

    @Serializable(IetfLanguageCodeSerializer::class)
    object Kanuri : IetfLanguageCode() { override val code: String = "kr"; override val withoutDialect: String get() = code }

    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Kashmiri : IetfLanguageCode() {
        override val code: String = "ks"
        override val withoutDialect: String
            get() = code


        @Serializable(IetfLanguageCodeSerializer::class)
        sealed class Arab : Kashmiri() {
            override val code: String = "ks-Arab"
            override val withoutDialect: String
                get() = code

            @Serializable(IetfLanguageCodeSerializer::class)
            object IN : Arab() { override val code: String = "ks-Arab-IN"; override val withoutDialect: String get() = Arab.code }

            @Serializable(IetfLanguageCodeSerializer::class)
            companion object : Arab()
        }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Kashmiri()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Kurdish : IetfLanguageCode() {
        override val code: String = "ku"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object TR : Kurdish() { override val code: String = "ku-TR"; override val withoutDialect: String get() = Kurdish.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Kurdish()
    }

    @Serializable(IetfLanguageCodeSerializer::class)
    object Komi : IetfLanguageCode() { override val code: String = "kv"; override val withoutDialect: String get() = code }

    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Cornish : IetfLanguageCode() {
        override val code: String = "kw"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object GB : Cornish() { override val code: String = "kw-GB"; override val withoutDialect: String get() = Cornish.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Cornish()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class KirghizKyrgyz : IetfLanguageCode() {
        override val code: String = "ky"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object KG : KirghizKyrgyz() { override val code: String = "ky-KG"; override val withoutDialect: String get() = KirghizKyrgyz.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : KirghizKyrgyz()
    }

    @Serializable(IetfLanguageCodeSerializer::class)
    object Latin : IetfLanguageCode() { override val code: String = "la"; override val withoutDialect: String get() = code }

    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class LuxembourgishLetzeburgesch : IetfLanguageCode() {
        override val code: String = "lb"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object LU : LuxembourgishLetzeburgesch() { override val code: String = "lb-LU"; override val withoutDialect: String get() = LuxembourgishLetzeburgesch.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : LuxembourgishLetzeburgesch()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Ganda : IetfLanguageCode() {
        override val code: String = "lg"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object UG : Ganda() { override val code: String = "lg-UG"; override val withoutDialect: String get() = Ganda.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Ganda()
    }

    @Serializable(IetfLanguageCodeSerializer::class)
    object LimburganLimburgerLimburgish : IetfLanguageCode() { override val code: String = "li"; override val withoutDialect: String get() = code }

    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Lingala : IetfLanguageCode() {
        override val code: String = "ln"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object AO : Lingala() { override val code: String = "ln-AO"; override val withoutDialect: String get() = Lingala.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object CD : Lingala() { override val code: String = "ln-CD"; override val withoutDialect: String get() = Lingala.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object CF : Lingala() { override val code: String = "ln-CF"; override val withoutDialect: String get() = Lingala.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object CG : Lingala() { override val code: String = "ln-CG"; override val withoutDialect: String get() = Lingala.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Lingala()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Lao : IetfLanguageCode() {
        override val code: String = "lo"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object LA : Lao() { override val code: String = "lo-LA"; override val withoutDialect: String get() = Lao.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Lao()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Lithuanian : IetfLanguageCode() {
        override val code: String = "lt"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object LT : Lithuanian() { override val code: String = "lt-LT"; override val withoutDialect: String get() = Lithuanian.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Lithuanian()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class LubaKatanga : IetfLanguageCode() {
        override val code: String = "lu"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object CD : LubaKatanga() { override val code: String = "lu-CD"; override val withoutDialect: String get() = LubaKatanga.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : LubaKatanga()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Latvian : IetfLanguageCode() {
        override val code: String = "lv"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object LV : Latvian() { override val code: String = "lv-LV"; override val withoutDialect: String get() = Latvian.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Latvian()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Malagasy : IetfLanguageCode() {
        override val code: String = "mg"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object MG : Malagasy() { override val code: String = "mg-MG"; override val withoutDialect: String get() = Malagasy.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Malagasy()
    }

    @Serializable(IetfLanguageCodeSerializer::class)
    object Marshallese : IetfLanguageCode() { override val code: String = "mh"; override val withoutDialect: String get() = code }

    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Maori : IetfLanguageCode() {
        override val code: String = "mi"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object NZ : Maori() { override val code: String = "mi-NZ"; override val withoutDialect: String get() = Maori.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Maori()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Macedonian : IetfLanguageCode() {
        override val code: String = "mk"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object MK : Macedonian() { override val code: String = "mk-MK"; override val withoutDialect: String get() = Macedonian.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Macedonian()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Malayalam : IetfLanguageCode() {
        override val code: String = "ml"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object IN : Malayalam() { override val code: String = "ml-IN"; override val withoutDialect: String get() = Malayalam.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Malayalam()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Mongolian : IetfLanguageCode() {
        override val code: String = "mn"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object MN : Mongolian() { override val code: String = "mn-MN"; override val withoutDialect: String get() = Mongolian.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Mongolian()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Marathi : IetfLanguageCode() {
        override val code: String = "mr"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object IN : Marathi() { override val code: String = "mr-IN"; override val withoutDialect: String get() = Marathi.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Marathi()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Malay : IetfLanguageCode() {
        override val code: String = "ms"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object BN : Malay() { override val code: String = "ms-BN"; override val withoutDialect: String get() = Malay.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object ID : Malay() { override val code: String = "ms-ID"; override val withoutDialect: String get() = Malay.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object MY : Malay() { override val code: String = "ms-MY"; override val withoutDialect: String get() = Malay.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object SG : Malay() { override val code: String = "ms-SG"; override val withoutDialect: String get() = Malay.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Malay()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Maltese : IetfLanguageCode() {
        override val code: String = "mt"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object MT : Maltese() { override val code: String = "mt-MT"; override val withoutDialect: String get() = Maltese.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Maltese()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Burmese : IetfLanguageCode() {
        override val code: String = "my"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object MM : Burmese() { override val code: String = "my-MM"; override val withoutDialect: String get() = Burmese.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Burmese()
    }

    @Serializable(IetfLanguageCodeSerializer::class)
    object Nauru : IetfLanguageCode() { override val code: String = "na"; override val withoutDialect: String get() = code }

    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class BokmalNorwegianNorwegianBokmal : IetfLanguageCode() {
        override val code: String = "nb"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object NO : BokmalNorwegianNorwegianBokmal() { override val code: String = "nb-NO"; override val withoutDialect: String get() = BokmalNorwegianNorwegianBokmal.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object SJ : BokmalNorwegianNorwegianBokmal() { override val code: String = "nb-SJ"; override val withoutDialect: String get() = BokmalNorwegianNorwegianBokmal.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : BokmalNorwegianNorwegianBokmal()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class NdebeleNorthNorthNdebele : IetfLanguageCode() {
        override val code: String = "nd"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object ZW : NdebeleNorthNorthNdebele() { override val code: String = "nd-ZW"; override val withoutDialect: String get() = NdebeleNorthNorthNdebele.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : NdebeleNorthNorthNdebele()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Nepali : IetfLanguageCode() {
        override val code: String = "ne"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object IN : Nepali() { override val code: String = "ne-IN"; override val withoutDialect: String get() = Nepali.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object NP : Nepali() { override val code: String = "ne-NP"; override val withoutDialect: String get() = Nepali.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Nepali()
    }

    @Serializable(IetfLanguageCodeSerializer::class)
    object Ndonga : IetfLanguageCode() { override val code: String = "ng"; override val withoutDialect: String get() = code }

    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class DutchFlemish : IetfLanguageCode() {
        override val code: String = "nl"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object AW : DutchFlemish() { override val code: String = "nl-AW"; override val withoutDialect: String get() = DutchFlemish.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object BE : DutchFlemish() { override val code: String = "nl-BE"; override val withoutDialect: String get() = DutchFlemish.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object BQ : DutchFlemish() { override val code: String = "nl-BQ"; override val withoutDialect: String get() = DutchFlemish.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object CW : DutchFlemish() { override val code: String = "nl-CW"; override val withoutDialect: String get() = DutchFlemish.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object NL : DutchFlemish() { override val code: String = "nl-NL"; override val withoutDialect: String get() = DutchFlemish.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object SR : DutchFlemish() { override val code: String = "nl-SR"; override val withoutDialect: String get() = DutchFlemish.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object SX : DutchFlemish() { override val code: String = "nl-SX"; override val withoutDialect: String get() = DutchFlemish.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : DutchFlemish()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class NorwegianNynorskNynorskNorwegian : IetfLanguageCode() {
        override val code: String = "nn"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object NO : NorwegianNynorskNynorskNorwegian() { override val code: String = "nn-NO"; override val withoutDialect: String get() = NorwegianNynorskNynorskNorwegian.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : NorwegianNynorskNynorskNorwegian()
    }

    @Serializable(IetfLanguageCodeSerializer::class)
    object Norwegian : IetfLanguageCode() { override val code: String = "no"; override val withoutDialect: String get() = code }
    @Serializable(IetfLanguageCodeSerializer::class)
    object NdebeleSouthSouthNdebele : IetfLanguageCode() { override val code: String = "nr"; override val withoutDialect: String get() = code }
    @Serializable(IetfLanguageCodeSerializer::class)
    object NavajoNavaho : IetfLanguageCode() { override val code: String = "nv"; override val withoutDialect: String get() = code }
    @Serializable(IetfLanguageCodeSerializer::class)
    object ChichewaChewaNyanja : IetfLanguageCode() { override val code: String = "ny"; override val withoutDialect: String get() = code }
    @Serializable(IetfLanguageCodeSerializer::class)
    object OccitanPost1500 : IetfLanguageCode() { override val code: String = "oc"; override val withoutDialect: String get() = code }
    @Serializable(IetfLanguageCodeSerializer::class)
    object Ojibwa : IetfLanguageCode() { override val code: String = "oj"; override val withoutDialect: String get() = code }

    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Oromo : IetfLanguageCode() {
        override val code: String = "om"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object ET : Oromo() { override val code: String = "om-ET"; override val withoutDialect: String get() = Oromo.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object KE : Oromo() { override val code: String = "om-KE"; override val withoutDialect: String get() = Oromo.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Oromo()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Oriya : IetfLanguageCode() {
        override val code: String = "or"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object IN : Oriya() { override val code: String = "or-IN"; override val withoutDialect: String get() = Oriya.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Oriya()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class OssetianOssetic : IetfLanguageCode() {
        override val code: String = "os"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object GE : OssetianOssetic() { override val code: String = "os-GE"; override val withoutDialect: String get() = OssetianOssetic.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object RU : OssetianOssetic() { override val code: String = "os-RU"; override val withoutDialect: String get() = OssetianOssetic.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : OssetianOssetic()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class PanjabiPunjabi : IetfLanguageCode() {
        override val code: String = "pa"
        override val withoutDialect: String
            get() = code


        @Serializable(IetfLanguageCodeSerializer::class)
        sealed class Arab : PanjabiPunjabi() {
            override val code: String = "pa-Arab"
            override val withoutDialect: String
                get() = code

            @Serializable(IetfLanguageCodeSerializer::class)
            object PK : Arab() { override val code: String = "pa-Arab-PK"; override val withoutDialect: String get() = Arab.code }

            @Serializable(IetfLanguageCodeSerializer::class)
            companion object : Arab()
        }


        @Serializable(IetfLanguageCodeSerializer::class)
        sealed class Guru : PanjabiPunjabi() {
            override val code: String = "pa-Guru"
            override val withoutDialect: String
                get() = code

            @Serializable(IetfLanguageCodeSerializer::class)
            object IN : Guru() { override val code: String = "pa-Guru-IN"; override val withoutDialect: String get() = Guru.code }

            @Serializable(IetfLanguageCodeSerializer::class)
            companion object : Guru()
        }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : PanjabiPunjabi()
    }

    @Serializable(IetfLanguageCodeSerializer::class)
    object Pali : IetfLanguageCode() { override val code: String = "pi"; override val withoutDialect: String get() = code }

    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Polish : IetfLanguageCode() {
        override val code: String = "pl"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object PL : Polish() { override val code: String = "pl-PL"; override val withoutDialect: String get() = Polish.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Polish()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class PushtoPashto : IetfLanguageCode() {
        override val code: String = "ps"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object AF : PushtoPashto() { override val code: String = "ps-AF"; override val withoutDialect: String get() = PushtoPashto.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object PK : PushtoPashto() { override val code: String = "ps-PK"; override val withoutDialect: String get() = PushtoPashto.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : PushtoPashto()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Portuguese : IetfLanguageCode() {
        override val code: String = "pt"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object AO : Portuguese() { override val code: String = "pt-AO"; override val withoutDialect: String get() = Portuguese.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object BR : Portuguese() { override val code: String = "pt-BR"; override val withoutDialect: String get() = Portuguese.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object CH : Portuguese() { override val code: String = "pt-CH"; override val withoutDialect: String get() = Portuguese.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object CV : Portuguese() { override val code: String = "pt-CV"; override val withoutDialect: String get() = Portuguese.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object GQ : Portuguese() { override val code: String = "pt-GQ"; override val withoutDialect: String get() = Portuguese.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object GW : Portuguese() { override val code: String = "pt-GW"; override val withoutDialect: String get() = Portuguese.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object LU : Portuguese() { override val code: String = "pt-LU"; override val withoutDialect: String get() = Portuguese.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object MO : Portuguese() { override val code: String = "pt-MO"; override val withoutDialect: String get() = Portuguese.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object MZ : Portuguese() { override val code: String = "pt-MZ"; override val withoutDialect: String get() = Portuguese.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object PT : Portuguese() { override val code: String = "pt-PT"; override val withoutDialect: String get() = Portuguese.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object ST : Portuguese() { override val code: String = "pt-ST"; override val withoutDialect: String get() = Portuguese.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object TL : Portuguese() { override val code: String = "pt-TL"; override val withoutDialect: String get() = Portuguese.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Portuguese()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Quechua : IetfLanguageCode() {
        override val code: String = "qu"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object BO : Quechua() { override val code: String = "qu-BO"; override val withoutDialect: String get() = Quechua.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object EC : Quechua() { override val code: String = "qu-EC"; override val withoutDialect: String get() = Quechua.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object PE : Quechua() { override val code: String = "qu-PE"; override val withoutDialect: String get() = Quechua.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Quechua()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Romansh : IetfLanguageCode() {
        override val code: String = "rm"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object CH : Romansh() { override val code: String = "rm-CH"; override val withoutDialect: String get() = Romansh.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Romansh()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Rundi : IetfLanguageCode() {
        override val code: String = "rn"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object BI : Rundi() { override val code: String = "rn-BI"; override val withoutDialect: String get() = Rundi.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Rundi()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class RomanianMoldavianMoldovan : IetfLanguageCode() {
        override val code: String = "ro"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object MD : RomanianMoldavianMoldovan() { override val code: String = "ro-MD"; override val withoutDialect: String get() = RomanianMoldavianMoldovan.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object RO : RomanianMoldavianMoldovan() { override val code: String = "ro-RO"; override val withoutDialect: String get() = RomanianMoldavianMoldovan.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : RomanianMoldavianMoldovan()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Russian : IetfLanguageCode() {
        override val code: String = "ru"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object BY : Russian() { override val code: String = "ru-BY"; override val withoutDialect: String get() = Russian.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object KG : Russian() { override val code: String = "ru-KG"; override val withoutDialect: String get() = Russian.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object KZ : Russian() { override val code: String = "ru-KZ"; override val withoutDialect: String get() = Russian.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object MD : Russian() { override val code: String = "ru-MD"; override val withoutDialect: String get() = Russian.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object RU : Russian() { override val code: String = "ru-RU"; override val withoutDialect: String get() = Russian.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object UA : Russian() { override val code: String = "ru-UA"; override val withoutDialect: String get() = Russian.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Russian()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Kinyarwanda : IetfLanguageCode() {
        override val code: String = "rw"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object RW : Kinyarwanda() { override val code: String = "rw-RW"; override val withoutDialect: String get() = Kinyarwanda.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Kinyarwanda()
    }

    @Serializable(IetfLanguageCodeSerializer::class)
    object Sanskrit : IetfLanguageCode() { override val code: String = "sa"; override val withoutDialect: String get() = code }
    @Serializable(IetfLanguageCodeSerializer::class)
    object Sardinian : IetfLanguageCode() { override val code: String = "sc"; override val withoutDialect: String get() = code }

    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Sindhi : IetfLanguageCode() {
        override val code: String = "sd"
        override val withoutDialect: String
            get() = code


        @Serializable(IetfLanguageCodeSerializer::class)
        sealed class Arab : Sindhi() {
            override val code: String = "sd-Arab"
            override val withoutDialect: String
                get() = code

            @Serializable(IetfLanguageCodeSerializer::class)
            object PK : Arab() { override val code: String = "sd-Arab-PK"; override val withoutDialect: String get() = Arab.code }

            @Serializable(IetfLanguageCodeSerializer::class)
            companion object : Arab()
        }


        @Serializable(IetfLanguageCodeSerializer::class)
        sealed class Deva : Sindhi() {
            override val code: String = "sd-Deva"
            override val withoutDialect: String
                get() = code

            @Serializable(IetfLanguageCodeSerializer::class)
            object IN : Deva() { override val code: String = "sd-Deva-IN"; override val withoutDialect: String get() = Deva.code }

            @Serializable(IetfLanguageCodeSerializer::class)
            companion object : Deva()
        }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Sindhi()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class NorthernSami : IetfLanguageCode() {
        override val code: String = "se"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object FI : NorthernSami() { override val code: String = "se-FI"; override val withoutDialect: String get() = NorthernSami.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object NO : NorthernSami() { override val code: String = "se-NO"; override val withoutDialect: String get() = NorthernSami.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object SE : NorthernSami() { override val code: String = "se-SE"; override val withoutDialect: String get() = NorthernSami.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : NorthernSami()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Sango : IetfLanguageCode() {
        override val code: String = "sg"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object CF : Sango() { override val code: String = "sg-CF"; override val withoutDialect: String get() = Sango.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Sango()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class SinhalaSinhalese : IetfLanguageCode() {
        override val code: String = "si"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object LK : SinhalaSinhalese() { override val code: String = "si-LK"; override val withoutDialect: String get() = SinhalaSinhalese.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : SinhalaSinhalese()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Slovak : IetfLanguageCode() {
        override val code: String = "sk"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object SK : Slovak() { override val code: String = "sk-SK"; override val withoutDialect: String get() = Slovak.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Slovak()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Slovenian : IetfLanguageCode() {
        override val code: String = "sl"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object SI : Slovenian() { override val code: String = "sl-SI"; override val withoutDialect: String get() = Slovenian.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Slovenian()
    }

    @Serializable(IetfLanguageCodeSerializer::class)
    object Samoan : IetfLanguageCode() { override val code: String = "sm"; override val withoutDialect: String get() = code }

    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Shona : IetfLanguageCode() {
        override val code: String = "sn"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object ZW : Shona() { override val code: String = "sn-ZW"; override val withoutDialect: String get() = Shona.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Shona()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Somali : IetfLanguageCode() {
        override val code: String = "so"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object DJ : Somali() { override val code: String = "so-DJ"; override val withoutDialect: String get() = Somali.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object ET : Somali() { override val code: String = "so-ET"; override val withoutDialect: String get() = Somali.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object KE : Somali() { override val code: String = "so-KE"; override val withoutDialect: String get() = Somali.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object SO : Somali() { override val code: String = "so-SO"; override val withoutDialect: String get() = Somali.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Somali()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Albanian : IetfLanguageCode() {
        override val code: String = "sq"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object AL : Albanian() { override val code: String = "sq-AL"; override val withoutDialect: String get() = Albanian.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object MK : Albanian() { override val code: String = "sq-MK"; override val withoutDialect: String get() = Albanian.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object XK : Albanian() { override val code: String = "sq-XK"; override val withoutDialect: String get() = Albanian.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Albanian()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Serbian : IetfLanguageCode() {
        override val code: String = "sr"
        override val withoutDialect: String
            get() = code


        @Serializable(IetfLanguageCodeSerializer::class)
        sealed class Cyrl : Serbian() {
            override val code: String = "sr-Cyrl"
            override val withoutDialect: String
                get() = code

            @Serializable(IetfLanguageCodeSerializer::class)
            object BA : Cyrl() { override val code: String = "sr-Cyrl-BA"; override val withoutDialect: String get() = Cyrl.code }
            @Serializable(IetfLanguageCodeSerializer::class)
            object ME : Cyrl() { override val code: String = "sr-Cyrl-ME"; override val withoutDialect: String get() = Cyrl.code }
            @Serializable(IetfLanguageCodeSerializer::class)
            object RS : Cyrl() { override val code: String = "sr-Cyrl-RS"; override val withoutDialect: String get() = Cyrl.code }
            @Serializable(IetfLanguageCodeSerializer::class)
            object XK : Cyrl() { override val code: String = "sr-Cyrl-XK"; override val withoutDialect: String get() = Cyrl.code }

            @Serializable(IetfLanguageCodeSerializer::class)
            companion object : Cyrl()
        }


        @Serializable(IetfLanguageCodeSerializer::class)
        sealed class Latn : Serbian() {
            override val code: String = "sr-Latn"
            override val withoutDialect: String
                get() = code

            @Serializable(IetfLanguageCodeSerializer::class)
            object BA : Latn() { override val code: String = "sr-Latn-BA"; override val withoutDialect: String get() = Latn.code }
            @Serializable(IetfLanguageCodeSerializer::class)
            object ME : Latn() { override val code: String = "sr-Latn-ME"; override val withoutDialect: String get() = Latn.code }
            @Serializable(IetfLanguageCodeSerializer::class)
            object RS : Latn() { override val code: String = "sr-Latn-RS"; override val withoutDialect: String get() = Latn.code }
            @Serializable(IetfLanguageCodeSerializer::class)
            object XK : Latn() { override val code: String = "sr-Latn-XK"; override val withoutDialect: String get() = Latn.code }

            @Serializable(IetfLanguageCodeSerializer::class)
            companion object : Latn()
        }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Serbian()
    }

    @Serializable(IetfLanguageCodeSerializer::class)
    object Swati : IetfLanguageCode() { override val code: String = "ss"; override val withoutDialect: String get() = code }
    @Serializable(IetfLanguageCodeSerializer::class)
    object SothoSouthern : IetfLanguageCode() { override val code: String = "st"; override val withoutDialect: String get() = code }

    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Sundanese : IetfLanguageCode() {
        override val code: String = "su"
        override val withoutDialect: String
            get() = code


        @Serializable(IetfLanguageCodeSerializer::class)
        sealed class Latn : Sundanese() {
            override val code: String = "su-Latn"
            override val withoutDialect: String
                get() = code

            @Serializable(IetfLanguageCodeSerializer::class)
            object ID : Latn() { override val code: String = "su-Latn-ID"; override val withoutDialect: String get() = Latn.code }

            @Serializable(IetfLanguageCodeSerializer::class)
            companion object : Latn()
        }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Sundanese()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Swedish : IetfLanguageCode() {
        override val code: String = "sv"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object AX : Swedish() { override val code: String = "sv-AX"; override val withoutDialect: String get() = Swedish.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object FI : Swedish() { override val code: String = "sv-FI"; override val withoutDialect: String get() = Swedish.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object SE : Swedish() { override val code: String = "sv-SE"; override val withoutDialect: String get() = Swedish.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Swedish()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Swahili : IetfLanguageCode() {
        override val code: String = "sw"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object CD : Swahili() { override val code: String = "sw-CD"; override val withoutDialect: String get() = Swahili.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object KE : Swahili() { override val code: String = "sw-KE"; override val withoutDialect: String get() = Swahili.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object TZ : Swahili() { override val code: String = "sw-TZ"; override val withoutDialect: String get() = Swahili.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object UG : Swahili() { override val code: String = "sw-UG"; override val withoutDialect: String get() = Swahili.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Swahili()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Tamil : IetfLanguageCode() {
        override val code: String = "ta"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object IN : Tamil() { override val code: String = "ta-IN"; override val withoutDialect: String get() = Tamil.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object LK : Tamil() { override val code: String = "ta-LK"; override val withoutDialect: String get() = Tamil.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object MY : Tamil() { override val code: String = "ta-MY"; override val withoutDialect: String get() = Tamil.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object SG : Tamil() { override val code: String = "ta-SG"; override val withoutDialect: String get() = Tamil.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Tamil()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Telugu : IetfLanguageCode() {
        override val code: String = "te"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object IN : Telugu() { override val code: String = "te-IN"; override val withoutDialect: String get() = Telugu.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Telugu()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Tajik : IetfLanguageCode() {
        override val code: String = "tg"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object TJ : Tajik() { override val code: String = "tg-TJ"; override val withoutDialect: String get() = Tajik.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Tajik()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Thai : IetfLanguageCode() {
        override val code: String = "th"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object TH : Thai() { override val code: String = "th-TH"; override val withoutDialect: String get() = Thai.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Thai()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Tigrinya : IetfLanguageCode() {
        override val code: String = "ti"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object ER : Tigrinya() { override val code: String = "ti-ER"; override val withoutDialect: String get() = Tigrinya.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object ET : Tigrinya() { override val code: String = "ti-ET"; override val withoutDialect: String get() = Tigrinya.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Tigrinya()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Turkmen : IetfLanguageCode() {
        override val code: String = "tk"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object TM : Turkmen() { override val code: String = "tk-TM"; override val withoutDialect: String get() = Turkmen.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Turkmen()
    }

    @Serializable(IetfLanguageCodeSerializer::class)
    object Tagalog : IetfLanguageCode() { override val code: String = "tl"; override val withoutDialect: String get() = code }
    @Serializable(IetfLanguageCodeSerializer::class)
    object Tswana : IetfLanguageCode() { override val code: String = "tn"; override val withoutDialect: String get() = code }

    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class TongaTongaIslands : IetfLanguageCode() {
        override val code: String = "to"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object TO : TongaTongaIslands() { override val code: String = "to-TO"; override val withoutDialect: String get() = TongaTongaIslands.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : TongaTongaIslands()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Turkish : IetfLanguageCode() {
        override val code: String = "tr"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object CY : Turkish() { override val code: String = "tr-CY"; override val withoutDialect: String get() = Turkish.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object TR : Turkish() { override val code: String = "tr-TR"; override val withoutDialect: String get() = Turkish.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Turkish()
    }

    @Serializable(IetfLanguageCodeSerializer::class)
    object Tsonga : IetfLanguageCode() { override val code: String = "ts"; override val withoutDialect: String get() = code }

    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Tatar : IetfLanguageCode() {
        override val code: String = "tt"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object RU : Tatar() { override val code: String = "tt-RU"; override val withoutDialect: String get() = Tatar.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Tatar()
    }

    @Serializable(IetfLanguageCodeSerializer::class)
    object Twi : IetfLanguageCode() { override val code: String = "tw"; override val withoutDialect: String get() = code }
    @Serializable(IetfLanguageCodeSerializer::class)
    object Tahitian : IetfLanguageCode() { override val code: String = "ty"; override val withoutDialect: String get() = code }

    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class UighurUyghur : IetfLanguageCode() {
        override val code: String = "ug"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object CN : UighurUyghur() { override val code: String = "ug-CN"; override val withoutDialect: String get() = UighurUyghur.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : UighurUyghur()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Ukrainian : IetfLanguageCode() {
        override val code: String = "uk"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object UA : Ukrainian() { override val code: String = "uk-UA"; override val withoutDialect: String get() = Ukrainian.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Ukrainian()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Urdu : IetfLanguageCode() {
        override val code: String = "ur"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object IN : Urdu() { override val code: String = "ur-IN"; override val withoutDialect: String get() = Urdu.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object PK : Urdu() { override val code: String = "ur-PK"; override val withoutDialect: String get() = Urdu.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Urdu()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Uzbek : IetfLanguageCode() {
        override val code: String = "uz"
        override val withoutDialect: String
            get() = code


        @Serializable(IetfLanguageCodeSerializer::class)
        sealed class Arab : Uzbek() {
            override val code: String = "uz-Arab"
            override val withoutDialect: String
                get() = code

            @Serializable(IetfLanguageCodeSerializer::class)
            object AF : Arab() { override val code: String = "uz-Arab-AF"; override val withoutDialect: String get() = Arab.code }

            @Serializable(IetfLanguageCodeSerializer::class)
            companion object : Arab()
        }


        @Serializable(IetfLanguageCodeSerializer::class)
        sealed class Cyrl : Uzbek() {
            override val code: String = "uz-Cyrl"
            override val withoutDialect: String
                get() = code

            @Serializable(IetfLanguageCodeSerializer::class)
            object UZ : Cyrl() { override val code: String = "uz-Cyrl-UZ"; override val withoutDialect: String get() = Cyrl.code }

            @Serializable(IetfLanguageCodeSerializer::class)
            companion object : Cyrl()
        }


        @Serializable(IetfLanguageCodeSerializer::class)
        sealed class Latn : Uzbek() {
            override val code: String = "uz-Latn"
            override val withoutDialect: String
                get() = code

            @Serializable(IetfLanguageCodeSerializer::class)
            object UZ : Latn() { override val code: String = "uz-Latn-UZ"; override val withoutDialect: String get() = Latn.code }

            @Serializable(IetfLanguageCodeSerializer::class)
            companion object : Latn()
        }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Uzbek()
    }

    @Serializable(IetfLanguageCodeSerializer::class)
    object Venda : IetfLanguageCode() { override val code: String = "ve"; override val withoutDialect: String get() = code }

    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Vietnamese : IetfLanguageCode() {
        override val code: String = "vi"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object VN : Vietnamese() { override val code: String = "vi-VN"; override val withoutDialect: String get() = Vietnamese.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Vietnamese()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Volapuk : IetfLanguageCode() {
        override val code: String = "vo"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object L001 : Volapuk() { override val code: String = "vo-001"; override val withoutDialect: String get() = Volapuk.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Volapuk()
    }

    @Serializable(IetfLanguageCodeSerializer::class)
    object Walloon : IetfLanguageCode() { override val code: String = "wa"; override val withoutDialect: String get() = code }

    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Wolof : IetfLanguageCode() {
        override val code: String = "wo"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object SN : Wolof() { override val code: String = "wo-SN"; override val withoutDialect: String get() = Wolof.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Wolof()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Xhosa : IetfLanguageCode() {
        override val code: String = "xh"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object ZA : Xhosa() { override val code: String = "xh-ZA"; override val withoutDialect: String get() = Xhosa.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Xhosa()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Yiddish : IetfLanguageCode() {
        override val code: String = "yi"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object L001 : Yiddish() { override val code: String = "yi-001"; override val withoutDialect: String get() = Yiddish.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Yiddish()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Yoruba : IetfLanguageCode() {
        override val code: String = "yo"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object BJ : Yoruba() { override val code: String = "yo-BJ"; override val withoutDialect: String get() = Yoruba.code }
        @Serializable(IetfLanguageCodeSerializer::class)
        object NG : Yoruba() { override val code: String = "yo-NG"; override val withoutDialect: String get() = Yoruba.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Yoruba()
    }

    @Serializable(IetfLanguageCodeSerializer::class)
    object ZhuangChuang : IetfLanguageCode() { override val code: String = "za"; override val withoutDialect: String get() = code }

    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Chinese : IetfLanguageCode() {
        override val code: String = "zh"
        override val withoutDialect: String
            get() = code


        @Serializable(IetfLanguageCodeSerializer::class)
        sealed class Hans : Chinese() {
            override val code: String = "zh-Hans"
            override val withoutDialect: String
                get() = code

            @Serializable(IetfLanguageCodeSerializer::class)
            object CN : Hans() { override val code: String = "zh-Hans-CN"; override val withoutDialect: String get() = Hans.code }
            @Serializable(IetfLanguageCodeSerializer::class)
            object HK : Hans() { override val code: String = "zh-Hans-HK"; override val withoutDialect: String get() = Hans.code }
            @Serializable(IetfLanguageCodeSerializer::class)
            object MO : Hans() { override val code: String = "zh-Hans-MO"; override val withoutDialect: String get() = Hans.code }
            @Serializable(IetfLanguageCodeSerializer::class)
            object SG : Hans() { override val code: String = "zh-Hans-SG"; override val withoutDialect: String get() = Hans.code }

            @Serializable(IetfLanguageCodeSerializer::class)
            companion object : Hans()
        }


        @Serializable(IetfLanguageCodeSerializer::class)
        sealed class Hant : Chinese() {
            override val code: String = "zh-Hant"
            override val withoutDialect: String
                get() = code

            @Serializable(IetfLanguageCodeSerializer::class)
            object HK : Hant() { override val code: String = "zh-Hant-HK"; override val withoutDialect: String get() = Hant.code }
            @Serializable(IetfLanguageCodeSerializer::class)
            object MO : Hant() { override val code: String = "zh-Hant-MO"; override val withoutDialect: String get() = Hant.code }
            @Serializable(IetfLanguageCodeSerializer::class)
            object TW : Hant() { override val code: String = "zh-Hant-TW"; override val withoutDialect: String get() = Hant.code }

            @Serializable(IetfLanguageCodeSerializer::class)
            companion object : Hant()
        }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Chinese()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Zulu : IetfLanguageCode() {
        override val code: String = "zu"
        override val withoutDialect: String
            get() = code

        @Serializable(IetfLanguageCodeSerializer::class)
        object ZA : Zulu() { override val code: String = "zu-ZA"; override val withoutDialect: String get() = Zulu.code }

        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Zulu()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    data class UnknownIetfLanguageCode (override val code: String) : IetfLanguageCode() {
        override val withoutDialect: String = code.takeWhile { it != '-' }
    }

    override fun toString() = code
}
