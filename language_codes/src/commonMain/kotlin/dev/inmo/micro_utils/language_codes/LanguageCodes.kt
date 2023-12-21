package dev.inmo.micro_utils.language_codes

import kotlinx.serialization.Serializable

/**
 * This class has been automatically generated using
 * https://github.com/InsanusMokrassar/MicroUtils/tree/master/language_codes/generator . This generator uses
 * https://datahub.io/core/language-codes/ files (base and tags) and create the whole hierarchy using it.
 */
@Serializable(IetfLangSerializer::class)
sealed class IetfLang {
    abstract val code: String
    open val parentLang: IetfLang?
        get() = null
    open val withoutDialect: String
        get() = parentLang ?.code ?: code

    @Serializable(IetfLangSerializer::class)
    object Afar : IetfLang() { override val code: String = "aa" }
    @Serializable(IetfLangSerializer::class)
    object Abkhazian : IetfLang() { override val code: String = "ab" }
    @Serializable(IetfLangSerializer::class)
    object Avestan : IetfLang() { override val code: String = "ae" }

    @Serializable(IetfLangSerializer::class)
    sealed class Afrikaans : IetfLang() {
        override val code: String = "af"

        @Serializable(IetfLangSerializer::class)
        object NA : Afrikaans() { override val code: String = "af-NA"; override val parentLang: Afrikaans get() = Afrikaans; }
        @Serializable(IetfLangSerializer::class)
        object ZA : Afrikaans() { override val code: String = "af-ZA"; override val parentLang: Afrikaans get() = Afrikaans; }

        @Serializable(IetfLangSerializer::class)
        companion object : Afrikaans()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class Akan : IetfLang() {
        override val code: String = "ak"

        @Serializable(IetfLangSerializer::class)
        object GH : Akan() { override val code: String = "ak-GH"; override val parentLang: Akan get() = Akan; }

        @Serializable(IetfLangSerializer::class)
        companion object : Akan()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class Amharic : IetfLang() {
        override val code: String = "am"

        @Serializable(IetfLangSerializer::class)
        object ET : Amharic() { override val code: String = "am-ET"; override val parentLang: Amharic get() = Amharic; }

        @Serializable(IetfLangSerializer::class)
        companion object : Amharic()
    }

    @Serializable(IetfLangSerializer::class)
    object Aragonese : IetfLang() { override val code: String = "an" }

    @Serializable(IetfLangSerializer::class)
    sealed class Arabic : IetfLang() {
        override val code: String = "ar"

        @Serializable(IetfLangSerializer::class)
        object L001 : Arabic() { override val code: String = "ar-001"; override val parentLang: Arabic get() = Arabic; }
        @Serializable(IetfLangSerializer::class)
        object AE : Arabic() { override val code: String = "ar-AE"; override val parentLang: Arabic get() = Arabic; }
        @Serializable(IetfLangSerializer::class)
        object BH : Arabic() { override val code: String = "ar-BH"; override val parentLang: Arabic get() = Arabic; }
        @Serializable(IetfLangSerializer::class)
        object DJ : Arabic() { override val code: String = "ar-DJ"; override val parentLang: Arabic get() = Arabic; }
        @Serializable(IetfLangSerializer::class)
        object DZ : Arabic() { override val code: String = "ar-DZ"; override val parentLang: Arabic get() = Arabic; }
        @Serializable(IetfLangSerializer::class)
        object EG : Arabic() { override val code: String = "ar-EG"; override val parentLang: Arabic get() = Arabic; }
        @Serializable(IetfLangSerializer::class)
        object EH : Arabic() { override val code: String = "ar-EH"; override val parentLang: Arabic get() = Arabic; }
        @Serializable(IetfLangSerializer::class)
        object ER : Arabic() { override val code: String = "ar-ER"; override val parentLang: Arabic get() = Arabic; }
        @Serializable(IetfLangSerializer::class)
        object IL : Arabic() { override val code: String = "ar-IL"; override val parentLang: Arabic get() = Arabic; }
        @Serializable(IetfLangSerializer::class)
        object IQ : Arabic() { override val code: String = "ar-IQ"; override val parentLang: Arabic get() = Arabic; }
        @Serializable(IetfLangSerializer::class)
        object JO : Arabic() { override val code: String = "ar-JO"; override val parentLang: Arabic get() = Arabic; }
        @Serializable(IetfLangSerializer::class)
        object KM : Arabic() { override val code: String = "ar-KM"; override val parentLang: Arabic get() = Arabic; }
        @Serializable(IetfLangSerializer::class)
        object KW : Arabic() { override val code: String = "ar-KW"; override val parentLang: Arabic get() = Arabic; }
        @Serializable(IetfLangSerializer::class)
        object LB : Arabic() { override val code: String = "ar-LB"; override val parentLang: Arabic get() = Arabic; }
        @Serializable(IetfLangSerializer::class)
        object LY : Arabic() { override val code: String = "ar-LY"; override val parentLang: Arabic get() = Arabic; }
        @Serializable(IetfLangSerializer::class)
        object MA : Arabic() { override val code: String = "ar-MA"; override val parentLang: Arabic get() = Arabic; }
        @Serializable(IetfLangSerializer::class)
        object MR : Arabic() { override val code: String = "ar-MR"; override val parentLang: Arabic get() = Arabic; }
        @Serializable(IetfLangSerializer::class)
        object OM : Arabic() { override val code: String = "ar-OM"; override val parentLang: Arabic get() = Arabic; }
        @Serializable(IetfLangSerializer::class)
        object PS : Arabic() { override val code: String = "ar-PS"; override val parentLang: Arabic get() = Arabic; }
        @Serializable(IetfLangSerializer::class)
        object QA : Arabic() { override val code: String = "ar-QA"; override val parentLang: Arabic get() = Arabic; }
        @Serializable(IetfLangSerializer::class)
        object SA : Arabic() { override val code: String = "ar-SA"; override val parentLang: Arabic get() = Arabic; }
        @Serializable(IetfLangSerializer::class)
        object SD : Arabic() { override val code: String = "ar-SD"; override val parentLang: Arabic get() = Arabic; }
        @Serializable(IetfLangSerializer::class)
        object SO : Arabic() { override val code: String = "ar-SO"; override val parentLang: Arabic get() = Arabic; }
        @Serializable(IetfLangSerializer::class)
        object SS : Arabic() { override val code: String = "ar-SS"; override val parentLang: Arabic get() = Arabic; }
        @Serializable(IetfLangSerializer::class)
        object SY : Arabic() { override val code: String = "ar-SY"; override val parentLang: Arabic get() = Arabic; }
        @Serializable(IetfLangSerializer::class)
        object TD : Arabic() { override val code: String = "ar-TD"; override val parentLang: Arabic get() = Arabic; }
        @Serializable(IetfLangSerializer::class)
        object TN : Arabic() { override val code: String = "ar-TN"; override val parentLang: Arabic get() = Arabic; }
        @Serializable(IetfLangSerializer::class)
        object YE : Arabic() { override val code: String = "ar-YE"; override val parentLang: Arabic get() = Arabic; }

        @Serializable(IetfLangSerializer::class)
        companion object : Arabic()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class Assamese : IetfLang() {
        override val code: String = "as"

        @Serializable(IetfLangSerializer::class)
        object IN : Assamese() { override val code: String = "as-IN"; override val parentLang: Assamese get() = Assamese; }

        @Serializable(IetfLangSerializer::class)
        companion object : Assamese()
    }

    @Serializable(IetfLangSerializer::class)
    object Avaric : IetfLang() { override val code: String = "av" }
    @Serializable(IetfLangSerializer::class)
    object Aymara : IetfLang() { override val code: String = "ay" }

    @Serializable(IetfLangSerializer::class)
    sealed class Azerbaijani : IetfLang() {
        override val code: String = "az"


        @Serializable(IetfLangSerializer::class)
        sealed class Cyrl : Azerbaijani() {
            override val code: String = "az-Cyrl"
            override val parentLang: Azerbaijani get() = Azerbaijani;

            @Serializable(IetfLangSerializer::class)
            object AZ : Cyrl() { override val code: String = "az-Cyrl-AZ"; override val parentLang: Cyrl get() = Cyrl; }

            @Serializable(IetfLangSerializer::class)
            companion object : Cyrl()
        }


        @Serializable(IetfLangSerializer::class)
        sealed class Latn : Azerbaijani() {
            override val code: String = "az-Latn"
            override val parentLang: Azerbaijani get() = Azerbaijani;

            @Serializable(IetfLangSerializer::class)
            object AZ : Latn() { override val code: String = "az-Latn-AZ"; override val parentLang: Latn get() = Latn; }

            @Serializable(IetfLangSerializer::class)
            companion object : Latn()
        }


        @Serializable(IetfLangSerializer::class)
        companion object : Azerbaijani()
    }

    @Serializable(IetfLangSerializer::class)
    object Bashkir : IetfLang() { override val code: String = "ba" }

    @Serializable(IetfLangSerializer::class)
    sealed class Belarusian : IetfLang() {
        override val code: String = "be"

        @Serializable(IetfLangSerializer::class)
        object BY : Belarusian() { override val code: String = "be-BY"; override val parentLang: Belarusian get() = Belarusian; }

        @Serializable(IetfLangSerializer::class)
        companion object : Belarusian()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class Bulgarian : IetfLang() {
        override val code: String = "bg"

        @Serializable(IetfLangSerializer::class)
        object BG : Bulgarian() { override val code: String = "bg-BG"; override val parentLang: Bulgarian get() = Bulgarian; }

        @Serializable(IetfLangSerializer::class)
        companion object : Bulgarian()
    }

    @Serializable(IetfLangSerializer::class)
    object BihariLanguages : IetfLang() { override val code: String = "bh" }
    @Serializable(IetfLangSerializer::class)
    object Bislama : IetfLang() { override val code: String = "bi" }

    @Serializable(IetfLangSerializer::class)
    sealed class Bambara : IetfLang() {
        override val code: String = "bm"

        @Serializable(IetfLangSerializer::class)
        object ML : Bambara() { override val code: String = "bm-ML"; override val parentLang: Bambara get() = Bambara; }

        @Serializable(IetfLangSerializer::class)
        companion object : Bambara()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class Bengali : IetfLang() {
        override val code: String = "bn"

        @Serializable(IetfLangSerializer::class)
        object BD : Bengali() { override val code: String = "bn-BD"; override val parentLang: Bengali get() = Bengali; }
        @Serializable(IetfLangSerializer::class)
        object IN : Bengali() { override val code: String = "bn-IN"; override val parentLang: Bengali get() = Bengali; }

        @Serializable(IetfLangSerializer::class)
        companion object : Bengali()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class Tibetan : IetfLang() {
        override val code: String = "bo"

        @Serializable(IetfLangSerializer::class)
        object CN : Tibetan() { override val code: String = "bo-CN"; override val parentLang: Tibetan get() = Tibetan; }
        @Serializable(IetfLangSerializer::class)
        object IN : Tibetan() { override val code: String = "bo-IN"; override val parentLang: Tibetan get() = Tibetan; }

        @Serializable(IetfLangSerializer::class)
        companion object : Tibetan()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class Breton : IetfLang() {
        override val code: String = "br"

        @Serializable(IetfLangSerializer::class)
        object FR : Breton() { override val code: String = "br-FR"; override val parentLang: Breton get() = Breton; }

        @Serializable(IetfLangSerializer::class)
        companion object : Breton()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class Bosnian : IetfLang() {
        override val code: String = "bs"


        @Serializable(IetfLangSerializer::class)
        sealed class Cyrl : Bosnian() {
            override val code: String = "bs-Cyrl"
            override val parentLang: Bosnian get() = Bosnian;

            @Serializable(IetfLangSerializer::class)
            object BA : Cyrl() { override val code: String = "bs-Cyrl-BA"; override val parentLang: Cyrl get() = Cyrl; }

            @Serializable(IetfLangSerializer::class)
            companion object : Cyrl()
        }


        @Serializable(IetfLangSerializer::class)
        sealed class Latn : Bosnian() {
            override val code: String = "bs-Latn"
            override val parentLang: Bosnian get() = Bosnian;

            @Serializable(IetfLangSerializer::class)
            object BA : Latn() { override val code: String = "bs-Latn-BA"; override val parentLang: Latn get() = Latn; }

            @Serializable(IetfLangSerializer::class)
            companion object : Latn()
        }


        @Serializable(IetfLangSerializer::class)
        companion object : Bosnian()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class CatalanValencian : IetfLang() {
        override val code: String = "ca"

        @Serializable(IetfLangSerializer::class)
        object AD : CatalanValencian() { override val code: String = "ca-AD"; override val parentLang: CatalanValencian get() = CatalanValencian; }

        @Serializable(IetfLangSerializer::class)
        sealed class ES : CatalanValencian() {
            override val code: String = "ca-ES"
            override val parentLang: CatalanValencian get() = CatalanValencian;

            @Serializable(IetfLangSerializer::class)
            object VALENCIA : ES() { override val code: String = "ca-ES-VALENCIA"; override val parentLang: ES get() = ES; }

            @Serializable(IetfLangSerializer::class)
            companion object : ES()
        }

        @Serializable(IetfLangSerializer::class)
        object FR : CatalanValencian() { override val code: String = "ca-FR"; override val parentLang: CatalanValencian get() = CatalanValencian; }
        @Serializable(IetfLangSerializer::class)
        object IT : CatalanValencian() { override val code: String = "ca-IT"; override val parentLang: CatalanValencian get() = CatalanValencian; }

        @Serializable(IetfLangSerializer::class)
        companion object : CatalanValencian()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class Chechen : IetfLang() {
        override val code: String = "ce"

        @Serializable(IetfLangSerializer::class)
        object RU : Chechen() { override val code: String = "ce-RU"; override val parentLang: Chechen get() = Chechen; }

        @Serializable(IetfLangSerializer::class)
        companion object : Chechen()
    }

    @Serializable(IetfLangSerializer::class)
    object Chamorro : IetfLang() { override val code: String = "ch" }
    @Serializable(IetfLangSerializer::class)
    object Corsican : IetfLang() { override val code: String = "co" }
    @Serializable(IetfLangSerializer::class)
    object Cree : IetfLang() { override val code: String = "cr" }

    @Serializable(IetfLangSerializer::class)
    sealed class Czech : IetfLang() {
        override val code: String = "cs"

        @Serializable(IetfLangSerializer::class)
        object CZ : Czech() { override val code: String = "cs-CZ"; override val parentLang: Czech get() = Czech; }

        @Serializable(IetfLangSerializer::class)
        companion object : Czech()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class ChurchSlavicOldSlavonicChurchSlavonicOldBulgarianOldChurchSlavonic : IetfLang() {
        override val code: String = "cu"

        @Serializable(IetfLangSerializer::class)
        object RU : ChurchSlavicOldSlavonicChurchSlavonicOldBulgarianOldChurchSlavonic() { override val code: String = "cu-RU"; override val parentLang: ChurchSlavicOldSlavonicChurchSlavonicOldBulgarianOldChurchSlavonic get() = ChurchSlavicOldSlavonicChurchSlavonicOldBulgarianOldChurchSlavonic; }

        @Serializable(IetfLangSerializer::class)
        companion object : ChurchSlavicOldSlavonicChurchSlavonicOldBulgarianOldChurchSlavonic()
    }

    @Serializable(IetfLangSerializer::class)
    object Chuvash : IetfLang() { override val code: String = "cv" }

    @Serializable(IetfLangSerializer::class)
    sealed class Welsh : IetfLang() {
        override val code: String = "cy"

        @Serializable(IetfLangSerializer::class)
        object GB : Welsh() { override val code: String = "cy-GB"; override val parentLang: Welsh get() = Welsh; }

        @Serializable(IetfLangSerializer::class)
        companion object : Welsh()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class Danish : IetfLang() {
        override val code: String = "da"

        @Serializable(IetfLangSerializer::class)
        object DK : Danish() { override val code: String = "da-DK"; override val parentLang: Danish get() = Danish; }
        @Serializable(IetfLangSerializer::class)
        object GL : Danish() { override val code: String = "da-GL"; override val parentLang: Danish get() = Danish; }

        @Serializable(IetfLangSerializer::class)
        companion object : Danish()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class German : IetfLang() {
        override val code: String = "de"

        @Serializable(IetfLangSerializer::class)
        object AT : German() { override val code: String = "de-AT"; override val parentLang: German get() = German; }
        @Serializable(IetfLangSerializer::class)
        object BE : German() { override val code: String = "de-BE"; override val parentLang: German get() = German; }
        @Serializable(IetfLangSerializer::class)
        object CH : German() { override val code: String = "de-CH"; override val parentLang: German get() = German; }
        @Serializable(IetfLangSerializer::class)
        object DE : German() { override val code: String = "de-DE"; override val parentLang: German get() = German; }
        @Serializable(IetfLangSerializer::class)
        object IT : German() { override val code: String = "de-IT"; override val parentLang: German get() = German; }
        @Serializable(IetfLangSerializer::class)
        object LI : German() { override val code: String = "de-LI"; override val parentLang: German get() = German; }
        @Serializable(IetfLangSerializer::class)
        object LU : German() { override val code: String = "de-LU"; override val parentLang: German get() = German; }

        @Serializable(IetfLangSerializer::class)
        companion object : German()
    }

    @Serializable(IetfLangSerializer::class)
    object DivehiDhivehiMaldivian : IetfLang() { override val code: String = "dv" }

    @Serializable(IetfLangSerializer::class)
    sealed class Dzongkha : IetfLang() {
        override val code: String = "dz"

        @Serializable(IetfLangSerializer::class)
        object BT : Dzongkha() { override val code: String = "dz-BT"; override val parentLang: Dzongkha get() = Dzongkha; }

        @Serializable(IetfLangSerializer::class)
        companion object : Dzongkha()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class Ewe : IetfLang() {
        override val code: String = "ee"

        @Serializable(IetfLangSerializer::class)
        object GH : Ewe() { override val code: String = "ee-GH"; override val parentLang: Ewe get() = Ewe; }
        @Serializable(IetfLangSerializer::class)
        object TG : Ewe() { override val code: String = "ee-TG"; override val parentLang: Ewe get() = Ewe; }

        @Serializable(IetfLangSerializer::class)
        companion object : Ewe()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class GreekModern1453 : IetfLang() {
        override val code: String = "el"

        @Serializable(IetfLangSerializer::class)
        object CY : GreekModern1453() { override val code: String = "el-CY"; override val parentLang: GreekModern1453 get() = GreekModern1453; }
        @Serializable(IetfLangSerializer::class)
        object GR : GreekModern1453() { override val code: String = "el-GR"; override val parentLang: GreekModern1453 get() = GreekModern1453; }

        @Serializable(IetfLangSerializer::class)
        companion object : GreekModern1453()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class English : IetfLang() {
        override val code: String = "en"

        @Serializable(IetfLangSerializer::class)
        object L001 : English() { override val code: String = "en-001"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object L150 : English() { override val code: String = "en-150"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object AE : English() { override val code: String = "en-AE"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object AG : English() { override val code: String = "en-AG"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object AI : English() { override val code: String = "en-AI"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object AS : English() { override val code: String = "en-AS"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object AT : English() { override val code: String = "en-AT"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object AU : English() { override val code: String = "en-AU"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object BB : English() { override val code: String = "en-BB"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object BE : English() { override val code: String = "en-BE"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object BI : English() { override val code: String = "en-BI"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object BM : English() { override val code: String = "en-BM"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object BS : English() { override val code: String = "en-BS"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object BW : English() { override val code: String = "en-BW"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object BZ : English() { override val code: String = "en-BZ"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object CA : English() { override val code: String = "en-CA"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object CC : English() { override val code: String = "en-CC"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object CH : English() { override val code: String = "en-CH"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object CK : English() { override val code: String = "en-CK"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object CM : English() { override val code: String = "en-CM"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object CX : English() { override val code: String = "en-CX"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object CY : English() { override val code: String = "en-CY"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object DE : English() { override val code: String = "en-DE"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object DG : English() { override val code: String = "en-DG"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object DK : English() { override val code: String = "en-DK"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object DM : English() { override val code: String = "en-DM"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object ER : English() { override val code: String = "en-ER"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object FI : English() { override val code: String = "en-FI"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object FJ : English() { override val code: String = "en-FJ"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object FK : English() { override val code: String = "en-FK"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object FM : English() { override val code: String = "en-FM"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object GB : English() { override val code: String = "en-GB"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object GD : English() { override val code: String = "en-GD"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object GG : English() { override val code: String = "en-GG"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object GH : English() { override val code: String = "en-GH"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object GI : English() { override val code: String = "en-GI"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object GM : English() { override val code: String = "en-GM"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object GU : English() { override val code: String = "en-GU"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object GY : English() { override val code: String = "en-GY"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object HK : English() { override val code: String = "en-HK"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object IE : English() { override val code: String = "en-IE"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object IL : English() { override val code: String = "en-IL"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object IM : English() { override val code: String = "en-IM"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object IN : English() { override val code: String = "en-IN"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object IO : English() { override val code: String = "en-IO"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object JE : English() { override val code: String = "en-JE"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object JM : English() { override val code: String = "en-JM"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object KE : English() { override val code: String = "en-KE"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object KI : English() { override val code: String = "en-KI"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object KN : English() { override val code: String = "en-KN"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object KY : English() { override val code: String = "en-KY"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object LC : English() { override val code: String = "en-LC"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object LR : English() { override val code: String = "en-LR"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object LS : English() { override val code: String = "en-LS"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object MG : English() { override val code: String = "en-MG"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object MH : English() { override val code: String = "en-MH"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object MO : English() { override val code: String = "en-MO"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object MP : English() { override val code: String = "en-MP"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object MS : English() { override val code: String = "en-MS"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object MT : English() { override val code: String = "en-MT"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object MU : English() { override val code: String = "en-MU"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object MW : English() { override val code: String = "en-MW"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object MY : English() { override val code: String = "en-MY"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object NA : English() { override val code: String = "en-NA"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object NF : English() { override val code: String = "en-NF"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object NG : English() { override val code: String = "en-NG"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object NL : English() { override val code: String = "en-NL"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object NR : English() { override val code: String = "en-NR"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object NU : English() { override val code: String = "en-NU"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object NZ : English() { override val code: String = "en-NZ"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object PG : English() { override val code: String = "en-PG"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object PH : English() { override val code: String = "en-PH"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object PK : English() { override val code: String = "en-PK"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object PN : English() { override val code: String = "en-PN"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object PR : English() { override val code: String = "en-PR"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object PW : English() { override val code: String = "en-PW"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object RW : English() { override val code: String = "en-RW"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object SB : English() { override val code: String = "en-SB"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object SC : English() { override val code: String = "en-SC"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object SD : English() { override val code: String = "en-SD"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object SE : English() { override val code: String = "en-SE"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object SG : English() { override val code: String = "en-SG"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object SH : English() { override val code: String = "en-SH"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object SI : English() { override val code: String = "en-SI"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object SL : English() { override val code: String = "en-SL"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object SS : English() { override val code: String = "en-SS"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object SX : English() { override val code: String = "en-SX"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object SZ : English() { override val code: String = "en-SZ"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object TC : English() { override val code: String = "en-TC"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object TK : English() { override val code: String = "en-TK"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object TO : English() { override val code: String = "en-TO"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object TT : English() { override val code: String = "en-TT"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object TV : English() { override val code: String = "en-TV"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object TZ : English() { override val code: String = "en-TZ"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object UG : English() { override val code: String = "en-UG"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object UM : English() { override val code: String = "en-UM"; override val parentLang: English get() = English; }

        @Serializable(IetfLangSerializer::class)
        sealed class US : English() {
            override val code: String = "en-US"
            override val parentLang: English get() = English;

            @Serializable(IetfLangSerializer::class)
            object POSIX : US() { override val code: String = "en-US-POSIX"; override val parentLang: US get() = US; }

            @Serializable(IetfLangSerializer::class)
            companion object : US()
        }

        @Serializable(IetfLangSerializer::class)
        object VC : English() { override val code: String = "en-VC"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object VG : English() { override val code: String = "en-VG"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object VI : English() { override val code: String = "en-VI"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object VU : English() { override val code: String = "en-VU"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object WS : English() { override val code: String = "en-WS"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object ZA : English() { override val code: String = "en-ZA"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object ZM : English() { override val code: String = "en-ZM"; override val parentLang: English get() = English; }
        @Serializable(IetfLangSerializer::class)
        object ZW : English() { override val code: String = "en-ZW"; override val parentLang: English get() = English; }

        @Serializable(IetfLangSerializer::class)
        companion object : English()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class Esperanto : IetfLang() {
        override val code: String = "eo"

        @Serializable(IetfLangSerializer::class)
        object L001 : Esperanto() { override val code: String = "eo-001"; override val parentLang: Esperanto get() = Esperanto; }

        @Serializable(IetfLangSerializer::class)
        companion object : Esperanto()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class SpanishCastilian : IetfLang() {
        override val code: String = "es"

        @Serializable(IetfLangSerializer::class)
        object L419 : SpanishCastilian() { override val code: String = "es-419"; override val parentLang: SpanishCastilian get() = SpanishCastilian; }
        @Serializable(IetfLangSerializer::class)
        object AR : SpanishCastilian() { override val code: String = "es-AR"; override val parentLang: SpanishCastilian get() = SpanishCastilian; }
        @Serializable(IetfLangSerializer::class)
        object BO : SpanishCastilian() { override val code: String = "es-BO"; override val parentLang: SpanishCastilian get() = SpanishCastilian; }
        @Serializable(IetfLangSerializer::class)
        object BR : SpanishCastilian() { override val code: String = "es-BR"; override val parentLang: SpanishCastilian get() = SpanishCastilian; }
        @Serializable(IetfLangSerializer::class)
        object BZ : SpanishCastilian() { override val code: String = "es-BZ"; override val parentLang: SpanishCastilian get() = SpanishCastilian; }
        @Serializable(IetfLangSerializer::class)
        object CL : SpanishCastilian() { override val code: String = "es-CL"; override val parentLang: SpanishCastilian get() = SpanishCastilian; }
        @Serializable(IetfLangSerializer::class)
        object CO : SpanishCastilian() { override val code: String = "es-CO"; override val parentLang: SpanishCastilian get() = SpanishCastilian; }
        @Serializable(IetfLangSerializer::class)
        object CR : SpanishCastilian() { override val code: String = "es-CR"; override val parentLang: SpanishCastilian get() = SpanishCastilian; }
        @Serializable(IetfLangSerializer::class)
        object CU : SpanishCastilian() { override val code: String = "es-CU"; override val parentLang: SpanishCastilian get() = SpanishCastilian; }
        @Serializable(IetfLangSerializer::class)
        object DO : SpanishCastilian() { override val code: String = "es-DO"; override val parentLang: SpanishCastilian get() = SpanishCastilian; }
        @Serializable(IetfLangSerializer::class)
        object EA : SpanishCastilian() { override val code: String = "es-EA"; override val parentLang: SpanishCastilian get() = SpanishCastilian; }
        @Serializable(IetfLangSerializer::class)
        object EC : SpanishCastilian() { override val code: String = "es-EC"; override val parentLang: SpanishCastilian get() = SpanishCastilian; }
        @Serializable(IetfLangSerializer::class)
        object ES : SpanishCastilian() { override val code: String = "es-ES"; override val parentLang: SpanishCastilian get() = SpanishCastilian; }
        @Serializable(IetfLangSerializer::class)
        object GQ : SpanishCastilian() { override val code: String = "es-GQ"; override val parentLang: SpanishCastilian get() = SpanishCastilian; }
        @Serializable(IetfLangSerializer::class)
        object GT : SpanishCastilian() { override val code: String = "es-GT"; override val parentLang: SpanishCastilian get() = SpanishCastilian; }
        @Serializable(IetfLangSerializer::class)
        object HN : SpanishCastilian() { override val code: String = "es-HN"; override val parentLang: SpanishCastilian get() = SpanishCastilian; }
        @Serializable(IetfLangSerializer::class)
        object IC : SpanishCastilian() { override val code: String = "es-IC"; override val parentLang: SpanishCastilian get() = SpanishCastilian; }
        @Serializable(IetfLangSerializer::class)
        object MX : SpanishCastilian() { override val code: String = "es-MX"; override val parentLang: SpanishCastilian get() = SpanishCastilian; }
        @Serializable(IetfLangSerializer::class)
        object NI : SpanishCastilian() { override val code: String = "es-NI"; override val parentLang: SpanishCastilian get() = SpanishCastilian; }
        @Serializable(IetfLangSerializer::class)
        object PA : SpanishCastilian() { override val code: String = "es-PA"; override val parentLang: SpanishCastilian get() = SpanishCastilian; }
        @Serializable(IetfLangSerializer::class)
        object PE : SpanishCastilian() { override val code: String = "es-PE"; override val parentLang: SpanishCastilian get() = SpanishCastilian; }
        @Serializable(IetfLangSerializer::class)
        object PH : SpanishCastilian() { override val code: String = "es-PH"; override val parentLang: SpanishCastilian get() = SpanishCastilian; }
        @Serializable(IetfLangSerializer::class)
        object PR : SpanishCastilian() { override val code: String = "es-PR"; override val parentLang: SpanishCastilian get() = SpanishCastilian; }
        @Serializable(IetfLangSerializer::class)
        object PY : SpanishCastilian() { override val code: String = "es-PY"; override val parentLang: SpanishCastilian get() = SpanishCastilian; }
        @Serializable(IetfLangSerializer::class)
        object SV : SpanishCastilian() { override val code: String = "es-SV"; override val parentLang: SpanishCastilian get() = SpanishCastilian; }
        @Serializable(IetfLangSerializer::class)
        object US : SpanishCastilian() { override val code: String = "es-US"; override val parentLang: SpanishCastilian get() = SpanishCastilian; }
        @Serializable(IetfLangSerializer::class)
        object UY : SpanishCastilian() { override val code: String = "es-UY"; override val parentLang: SpanishCastilian get() = SpanishCastilian; }
        @Serializable(IetfLangSerializer::class)
        object VE : SpanishCastilian() { override val code: String = "es-VE"; override val parentLang: SpanishCastilian get() = SpanishCastilian; }

        @Serializable(IetfLangSerializer::class)
        companion object : SpanishCastilian()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class Estonian : IetfLang() {
        override val code: String = "et"

        @Serializable(IetfLangSerializer::class)
        object EE : Estonian() { override val code: String = "et-EE"; override val parentLang: Estonian get() = Estonian; }

        @Serializable(IetfLangSerializer::class)
        companion object : Estonian()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class Basque : IetfLang() {
        override val code: String = "eu"

        @Serializable(IetfLangSerializer::class)
        object ES : Basque() { override val code: String = "eu-ES"; override val parentLang: Basque get() = Basque; }

        @Serializable(IetfLangSerializer::class)
        companion object : Basque()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class Persian : IetfLang() {
        override val code: String = "fa"

        @Serializable(IetfLangSerializer::class)
        object AF : Persian() { override val code: String = "fa-AF"; override val parentLang: Persian get() = Persian; }
        @Serializable(IetfLangSerializer::class)
        object IR : Persian() { override val code: String = "fa-IR"; override val parentLang: Persian get() = Persian; }

        @Serializable(IetfLangSerializer::class)
        companion object : Persian()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class Fulah : IetfLang() {
        override val code: String = "ff"


        @Serializable(IetfLangSerializer::class)
        sealed class Adlm : Fulah() {
            override val code: String = "ff-Adlm"
            override val parentLang: Fulah get() = Fulah;

            @Serializable(IetfLangSerializer::class)
            object BF : Adlm() { override val code: String = "ff-Adlm-BF"; override val parentLang: Adlm get() = Adlm; }
            @Serializable(IetfLangSerializer::class)
            object CM : Adlm() { override val code: String = "ff-Adlm-CM"; override val parentLang: Adlm get() = Adlm; }
            @Serializable(IetfLangSerializer::class)
            object GH : Adlm() { override val code: String = "ff-Adlm-GH"; override val parentLang: Adlm get() = Adlm; }
            @Serializable(IetfLangSerializer::class)
            object GM : Adlm() { override val code: String = "ff-Adlm-GM"; override val parentLang: Adlm get() = Adlm; }
            @Serializable(IetfLangSerializer::class)
            object GN : Adlm() { override val code: String = "ff-Adlm-GN"; override val parentLang: Adlm get() = Adlm; }
            @Serializable(IetfLangSerializer::class)
            object GW : Adlm() { override val code: String = "ff-Adlm-GW"; override val parentLang: Adlm get() = Adlm; }
            @Serializable(IetfLangSerializer::class)
            object LR : Adlm() { override val code: String = "ff-Adlm-LR"; override val parentLang: Adlm get() = Adlm; }
            @Serializable(IetfLangSerializer::class)
            object MR : Adlm() { override val code: String = "ff-Adlm-MR"; override val parentLang: Adlm get() = Adlm; }
            @Serializable(IetfLangSerializer::class)
            object NE : Adlm() { override val code: String = "ff-Adlm-NE"; override val parentLang: Adlm get() = Adlm; }
            @Serializable(IetfLangSerializer::class)
            object NG : Adlm() { override val code: String = "ff-Adlm-NG"; override val parentLang: Adlm get() = Adlm; }
            @Serializable(IetfLangSerializer::class)
            object SL : Adlm() { override val code: String = "ff-Adlm-SL"; override val parentLang: Adlm get() = Adlm; }
            @Serializable(IetfLangSerializer::class)
            object SN : Adlm() { override val code: String = "ff-Adlm-SN"; override val parentLang: Adlm get() = Adlm; }

            @Serializable(IetfLangSerializer::class)
            companion object : Adlm()
        }


        @Serializable(IetfLangSerializer::class)
        sealed class Latn : Fulah() {
            override val code: String = "ff-Latn"
            override val parentLang: Fulah get() = Fulah;

            @Serializable(IetfLangSerializer::class)
            object BF : Latn() { override val code: String = "ff-Latn-BF"; override val parentLang: Latn get() = Latn; }
            @Serializable(IetfLangSerializer::class)
            object CM : Latn() { override val code: String = "ff-Latn-CM"; override val parentLang: Latn get() = Latn; }
            @Serializable(IetfLangSerializer::class)
            object GH : Latn() { override val code: String = "ff-Latn-GH"; override val parentLang: Latn get() = Latn; }
            @Serializable(IetfLangSerializer::class)
            object GM : Latn() { override val code: String = "ff-Latn-GM"; override val parentLang: Latn get() = Latn; }
            @Serializable(IetfLangSerializer::class)
            object GN : Latn() { override val code: String = "ff-Latn-GN"; override val parentLang: Latn get() = Latn; }
            @Serializable(IetfLangSerializer::class)
            object GW : Latn() { override val code: String = "ff-Latn-GW"; override val parentLang: Latn get() = Latn; }
            @Serializable(IetfLangSerializer::class)
            object LR : Latn() { override val code: String = "ff-Latn-LR"; override val parentLang: Latn get() = Latn; }
            @Serializable(IetfLangSerializer::class)
            object MR : Latn() { override val code: String = "ff-Latn-MR"; override val parentLang: Latn get() = Latn; }
            @Serializable(IetfLangSerializer::class)
            object NE : Latn() { override val code: String = "ff-Latn-NE"; override val parentLang: Latn get() = Latn; }
            @Serializable(IetfLangSerializer::class)
            object NG : Latn() { override val code: String = "ff-Latn-NG"; override val parentLang: Latn get() = Latn; }
            @Serializable(IetfLangSerializer::class)
            object SL : Latn() { override val code: String = "ff-Latn-SL"; override val parentLang: Latn get() = Latn; }
            @Serializable(IetfLangSerializer::class)
            object SN : Latn() { override val code: String = "ff-Latn-SN"; override val parentLang: Latn get() = Latn; }

            @Serializable(IetfLangSerializer::class)
            companion object : Latn()
        }


        @Serializable(IetfLangSerializer::class)
        companion object : Fulah()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class Finnish : IetfLang() {
        override val code: String = "fi"

        @Serializable(IetfLangSerializer::class)
        object FI : Finnish() { override val code: String = "fi-FI"; override val parentLang: Finnish get() = Finnish; }

        @Serializable(IetfLangSerializer::class)
        companion object : Finnish()
    }

    @Serializable(IetfLangSerializer::class)
    object Fijian : IetfLang() { override val code: String = "fj" }

    @Serializable(IetfLangSerializer::class)
    sealed class Faroese : IetfLang() {
        override val code: String = "fo"

        @Serializable(IetfLangSerializer::class)
        object DK : Faroese() { override val code: String = "fo-DK"; override val parentLang: Faroese get() = Faroese; }
        @Serializable(IetfLangSerializer::class)
        object FO : Faroese() { override val code: String = "fo-FO"; override val parentLang: Faroese get() = Faroese; }

        @Serializable(IetfLangSerializer::class)
        companion object : Faroese()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class French : IetfLang() {
        override val code: String = "fr"

        @Serializable(IetfLangSerializer::class)
        object BE : French() { override val code: String = "fr-BE"; override val parentLang: French get() = French; }
        @Serializable(IetfLangSerializer::class)
        object BF : French() { override val code: String = "fr-BF"; override val parentLang: French get() = French; }
        @Serializable(IetfLangSerializer::class)
        object BI : French() { override val code: String = "fr-BI"; override val parentLang: French get() = French; }
        @Serializable(IetfLangSerializer::class)
        object BJ : French() { override val code: String = "fr-BJ"; override val parentLang: French get() = French; }
        @Serializable(IetfLangSerializer::class)
        object BL : French() { override val code: String = "fr-BL"; override val parentLang: French get() = French; }
        @Serializable(IetfLangSerializer::class)
        object CA : French() { override val code: String = "fr-CA"; override val parentLang: French get() = French; }
        @Serializable(IetfLangSerializer::class)
        object CD : French() { override val code: String = "fr-CD"; override val parentLang: French get() = French; }
        @Serializable(IetfLangSerializer::class)
        object CF : French() { override val code: String = "fr-CF"; override val parentLang: French get() = French; }
        @Serializable(IetfLangSerializer::class)
        object CG : French() { override val code: String = "fr-CG"; override val parentLang: French get() = French; }
        @Serializable(IetfLangSerializer::class)
        object CH : French() { override val code: String = "fr-CH"; override val parentLang: French get() = French; }
        @Serializable(IetfLangSerializer::class)
        object CI : French() { override val code: String = "fr-CI"; override val parentLang: French get() = French; }
        @Serializable(IetfLangSerializer::class)
        object CM : French() { override val code: String = "fr-CM"; override val parentLang: French get() = French; }
        @Serializable(IetfLangSerializer::class)
        object DJ : French() { override val code: String = "fr-DJ"; override val parentLang: French get() = French; }
        @Serializable(IetfLangSerializer::class)
        object DZ : French() { override val code: String = "fr-DZ"; override val parentLang: French get() = French; }
        @Serializable(IetfLangSerializer::class)
        object FR : French() { override val code: String = "fr-FR"; override val parentLang: French get() = French; }
        @Serializable(IetfLangSerializer::class)
        object GA : French() { override val code: String = "fr-GA"; override val parentLang: French get() = French; }
        @Serializable(IetfLangSerializer::class)
        object GF : French() { override val code: String = "fr-GF"; override val parentLang: French get() = French; }
        @Serializable(IetfLangSerializer::class)
        object GN : French() { override val code: String = "fr-GN"; override val parentLang: French get() = French; }
        @Serializable(IetfLangSerializer::class)
        object GP : French() { override val code: String = "fr-GP"; override val parentLang: French get() = French; }
        @Serializable(IetfLangSerializer::class)
        object GQ : French() { override val code: String = "fr-GQ"; override val parentLang: French get() = French; }
        @Serializable(IetfLangSerializer::class)
        object HT : French() { override val code: String = "fr-HT"; override val parentLang: French get() = French; }
        @Serializable(IetfLangSerializer::class)
        object KM : French() { override val code: String = "fr-KM"; override val parentLang: French get() = French; }
        @Serializable(IetfLangSerializer::class)
        object LU : French() { override val code: String = "fr-LU"; override val parentLang: French get() = French; }
        @Serializable(IetfLangSerializer::class)
        object MA : French() { override val code: String = "fr-MA"; override val parentLang: French get() = French; }
        @Serializable(IetfLangSerializer::class)
        object MC : French() { override val code: String = "fr-MC"; override val parentLang: French get() = French; }
        @Serializable(IetfLangSerializer::class)
        object MF : French() { override val code: String = "fr-MF"; override val parentLang: French get() = French; }
        @Serializable(IetfLangSerializer::class)
        object MG : French() { override val code: String = "fr-MG"; override val parentLang: French get() = French; }
        @Serializable(IetfLangSerializer::class)
        object ML : French() { override val code: String = "fr-ML"; override val parentLang: French get() = French; }
        @Serializable(IetfLangSerializer::class)
        object MQ : French() { override val code: String = "fr-MQ"; override val parentLang: French get() = French; }
        @Serializable(IetfLangSerializer::class)
        object MR : French() { override val code: String = "fr-MR"; override val parentLang: French get() = French; }
        @Serializable(IetfLangSerializer::class)
        object MU : French() { override val code: String = "fr-MU"; override val parentLang: French get() = French; }
        @Serializable(IetfLangSerializer::class)
        object NC : French() { override val code: String = "fr-NC"; override val parentLang: French get() = French; }
        @Serializable(IetfLangSerializer::class)
        object NE : French() { override val code: String = "fr-NE"; override val parentLang: French get() = French; }
        @Serializable(IetfLangSerializer::class)
        object PF : French() { override val code: String = "fr-PF"; override val parentLang: French get() = French; }
        @Serializable(IetfLangSerializer::class)
        object PM : French() { override val code: String = "fr-PM"; override val parentLang: French get() = French; }
        @Serializable(IetfLangSerializer::class)
        object RE : French() { override val code: String = "fr-RE"; override val parentLang: French get() = French; }
        @Serializable(IetfLangSerializer::class)
        object RW : French() { override val code: String = "fr-RW"; override val parentLang: French get() = French; }
        @Serializable(IetfLangSerializer::class)
        object SC : French() { override val code: String = "fr-SC"; override val parentLang: French get() = French; }
        @Serializable(IetfLangSerializer::class)
        object SN : French() { override val code: String = "fr-SN"; override val parentLang: French get() = French; }
        @Serializable(IetfLangSerializer::class)
        object SY : French() { override val code: String = "fr-SY"; override val parentLang: French get() = French; }
        @Serializable(IetfLangSerializer::class)
        object TD : French() { override val code: String = "fr-TD"; override val parentLang: French get() = French; }
        @Serializable(IetfLangSerializer::class)
        object TG : French() { override val code: String = "fr-TG"; override val parentLang: French get() = French; }
        @Serializable(IetfLangSerializer::class)
        object TN : French() { override val code: String = "fr-TN"; override val parentLang: French get() = French; }
        @Serializable(IetfLangSerializer::class)
        object VU : French() { override val code: String = "fr-VU"; override val parentLang: French get() = French; }
        @Serializable(IetfLangSerializer::class)
        object WF : French() { override val code: String = "fr-WF"; override val parentLang: French get() = French; }
        @Serializable(IetfLangSerializer::class)
        object YT : French() { override val code: String = "fr-YT"; override val parentLang: French get() = French; }

        @Serializable(IetfLangSerializer::class)
        companion object : French()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class WesternFrisian : IetfLang() {
        override val code: String = "fy"

        @Serializable(IetfLangSerializer::class)
        object NL : WesternFrisian() { override val code: String = "fy-NL"; override val parentLang: WesternFrisian get() = WesternFrisian; }

        @Serializable(IetfLangSerializer::class)
        companion object : WesternFrisian()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class Irish : IetfLang() {
        override val code: String = "ga"

        @Serializable(IetfLangSerializer::class)
        object GB : Irish() { override val code: String = "ga-GB"; override val parentLang: Irish get() = Irish; }
        @Serializable(IetfLangSerializer::class)
        object IE : Irish() { override val code: String = "ga-IE"; override val parentLang: Irish get() = Irish; }

        @Serializable(IetfLangSerializer::class)
        companion object : Irish()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class GaelicScottishGaelic : IetfLang() {
        override val code: String = "gd"

        @Serializable(IetfLangSerializer::class)
        object GB : GaelicScottishGaelic() { override val code: String = "gd-GB"; override val parentLang: GaelicScottishGaelic get() = GaelicScottishGaelic; }

        @Serializable(IetfLangSerializer::class)
        companion object : GaelicScottishGaelic()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class Galician : IetfLang() {
        override val code: String = "gl"

        @Serializable(IetfLangSerializer::class)
        object ES : Galician() { override val code: String = "gl-ES"; override val parentLang: Galician get() = Galician; }

        @Serializable(IetfLangSerializer::class)
        companion object : Galician()
    }

    @Serializable(IetfLangSerializer::class)
    object Guarani : IetfLang() { override val code: String = "gn" }

    @Serializable(IetfLangSerializer::class)
    sealed class Gujarati : IetfLang() {
        override val code: String = "gu"

        @Serializable(IetfLangSerializer::class)
        object IN : Gujarati() { override val code: String = "gu-IN"; override val parentLang: Gujarati get() = Gujarati; }

        @Serializable(IetfLangSerializer::class)
        companion object : Gujarati()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class Manx : IetfLang() {
        override val code: String = "gv"

        @Serializable(IetfLangSerializer::class)
        object IM : Manx() { override val code: String = "gv-IM"; override val parentLang: Manx get() = Manx; }

        @Serializable(IetfLangSerializer::class)
        companion object : Manx()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class Hausa : IetfLang() {
        override val code: String = "ha"

        @Serializable(IetfLangSerializer::class)
        object GH : Hausa() { override val code: String = "ha-GH"; override val parentLang: Hausa get() = Hausa; }
        @Serializable(IetfLangSerializer::class)
        object NE : Hausa() { override val code: String = "ha-NE"; override val parentLang: Hausa get() = Hausa; }
        @Serializable(IetfLangSerializer::class)
        object NG : Hausa() { override val code: String = "ha-NG"; override val parentLang: Hausa get() = Hausa; }

        @Serializable(IetfLangSerializer::class)
        companion object : Hausa()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class Hebrew : IetfLang() {
        override val code: String = "he"

        @Serializable(IetfLangSerializer::class)
        object IL : Hebrew() { override val code: String = "he-IL"; override val parentLang: Hebrew get() = Hebrew; }

        @Serializable(IetfLangSerializer::class)
        companion object : Hebrew()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class Hindi : IetfLang() {
        override val code: String = "hi"

        @Serializable(IetfLangSerializer::class)
        object IN : Hindi() { override val code: String = "hi-IN"; override val parentLang: Hindi get() = Hindi; }

        @Serializable(IetfLangSerializer::class)
        companion object : Hindi()
    }

    @Serializable(IetfLangSerializer::class)
    object HiriMotu : IetfLang() { override val code: String = "ho" }

    @Serializable(IetfLangSerializer::class)
    sealed class Croatian : IetfLang() {
        override val code: String = "hr"

        @Serializable(IetfLangSerializer::class)
        object BA : Croatian() { override val code: String = "hr-BA"; override val parentLang: Croatian get() = Croatian; }
        @Serializable(IetfLangSerializer::class)
        object HR : Croatian() { override val code: String = "hr-HR"; override val parentLang: Croatian get() = Croatian; }

        @Serializable(IetfLangSerializer::class)
        companion object : Croatian()
    }

    @Serializable(IetfLangSerializer::class)
    object HaitianHaitianCreole : IetfLang() { override val code: String = "ht" }

    @Serializable(IetfLangSerializer::class)
    sealed class Hungarian : IetfLang() {
        override val code: String = "hu"

        @Serializable(IetfLangSerializer::class)
        object HU : Hungarian() { override val code: String = "hu-HU"; override val parentLang: Hungarian get() = Hungarian; }

        @Serializable(IetfLangSerializer::class)
        companion object : Hungarian()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class Armenian : IetfLang() {
        override val code: String = "hy"

        @Serializable(IetfLangSerializer::class)
        object AM : Armenian() { override val code: String = "hy-AM"; override val parentLang: Armenian get() = Armenian; }

        @Serializable(IetfLangSerializer::class)
        companion object : Armenian()
    }

    @Serializable(IetfLangSerializer::class)
    object Herero : IetfLang() { override val code: String = "hz" }

    @Serializable(IetfLangSerializer::class)
    sealed class InterlinguaInternationalAuxiliaryLanguageAssociation : IetfLang() {
        override val code: String = "ia"

        @Serializable(IetfLangSerializer::class)
        object L001 : InterlinguaInternationalAuxiliaryLanguageAssociation() { override val code: String = "ia-001"; override val parentLang: InterlinguaInternationalAuxiliaryLanguageAssociation get() = InterlinguaInternationalAuxiliaryLanguageAssociation; }

        @Serializable(IetfLangSerializer::class)
        companion object : InterlinguaInternationalAuxiliaryLanguageAssociation()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class Indonesian : IetfLang() {
        override val code: String = "id"

        @Serializable(IetfLangSerializer::class)
        object ID : Indonesian() { override val code: String = "id-ID"; override val parentLang: Indonesian get() = Indonesian; }

        @Serializable(IetfLangSerializer::class)
        companion object : Indonesian()
    }

    @Serializable(IetfLangSerializer::class)
    object InterlingueOccidental : IetfLang() { override val code: String = "ie" }

    @Serializable(IetfLangSerializer::class)
    sealed class Igbo : IetfLang() {
        override val code: String = "ig"

        @Serializable(IetfLangSerializer::class)
        object NG : Igbo() { override val code: String = "ig-NG"; override val parentLang: Igbo get() = Igbo; }

        @Serializable(IetfLangSerializer::class)
        companion object : Igbo()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class SichuanYiNuosu : IetfLang() {
        override val code: String = "ii"

        @Serializable(IetfLangSerializer::class)
        object CN : SichuanYiNuosu() { override val code: String = "ii-CN"; override val parentLang: SichuanYiNuosu get() = SichuanYiNuosu; }

        @Serializable(IetfLangSerializer::class)
        companion object : SichuanYiNuosu()
    }

    @Serializable(IetfLangSerializer::class)
    object Inupiaq : IetfLang() { override val code: String = "ik" }
    @Serializable(IetfLangSerializer::class)
    object Ido : IetfLang() { override val code: String = "io" }

    @Serializable(IetfLangSerializer::class)
    sealed class Icelandic : IetfLang() {
        override val code: String = "is"

        @Serializable(IetfLangSerializer::class)
        object IS : Icelandic() { override val code: String = "is-IS"; override val parentLang: Icelandic get() = Icelandic; }

        @Serializable(IetfLangSerializer::class)
        companion object : Icelandic()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class Italian : IetfLang() {
        override val code: String = "it"

        @Serializable(IetfLangSerializer::class)
        object CH : Italian() { override val code: String = "it-CH"; override val parentLang: Italian get() = Italian; }
        @Serializable(IetfLangSerializer::class)
        object IT : Italian() { override val code: String = "it-IT"; override val parentLang: Italian get() = Italian; }
        @Serializable(IetfLangSerializer::class)
        object SM : Italian() { override val code: String = "it-SM"; override val parentLang: Italian get() = Italian; }
        @Serializable(IetfLangSerializer::class)
        object VA : Italian() { override val code: String = "it-VA"; override val parentLang: Italian get() = Italian; }

        @Serializable(IetfLangSerializer::class)
        companion object : Italian()
    }

    @Serializable(IetfLangSerializer::class)
    object Inuktitut : IetfLang() { override val code: String = "iu" }

    @Serializable(IetfLangSerializer::class)
    sealed class Japanese : IetfLang() {
        override val code: String = "ja"

        @Serializable(IetfLangSerializer::class)
        object JP : Japanese() { override val code: String = "ja-JP"; override val parentLang: Japanese get() = Japanese; }

        @Serializable(IetfLangSerializer::class)
        companion object : Japanese()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class Javanese : IetfLang() {
        override val code: String = "jv"

        @Serializable(IetfLangSerializer::class)
        object ID : Javanese() { override val code: String = "jv-ID"; override val parentLang: Javanese get() = Javanese; }

        @Serializable(IetfLangSerializer::class)
        companion object : Javanese()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class Georgian : IetfLang() {
        override val code: String = "ka"

        @Serializable(IetfLangSerializer::class)
        object GE : Georgian() { override val code: String = "ka-GE"; override val parentLang: Georgian get() = Georgian; }

        @Serializable(IetfLangSerializer::class)
        companion object : Georgian()
    }

    @Serializable(IetfLangSerializer::class)
    object Kongo : IetfLang() { override val code: String = "kg" }

    @Serializable(IetfLangSerializer::class)
    sealed class KikuyuGikuyu : IetfLang() {
        override val code: String = "ki"

        @Serializable(IetfLangSerializer::class)
        object KE : KikuyuGikuyu() { override val code: String = "ki-KE"; override val parentLang: KikuyuGikuyu get() = KikuyuGikuyu; }

        @Serializable(IetfLangSerializer::class)
        companion object : KikuyuGikuyu()
    }

    @Serializable(IetfLangSerializer::class)
    object KuanyamaKwanyama : IetfLang() { override val code: String = "kj" }

    @Serializable(IetfLangSerializer::class)
    sealed class Kazakh : IetfLang() {
        override val code: String = "kk"

        @Serializable(IetfLangSerializer::class)
        object KZ : Kazakh() { override val code: String = "kk-KZ"; override val parentLang: Kazakh get() = Kazakh; }

        @Serializable(IetfLangSerializer::class)
        companion object : Kazakh()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class KalaallisutGreenlandic : IetfLang() {
        override val code: String = "kl"

        @Serializable(IetfLangSerializer::class)
        object GL : KalaallisutGreenlandic() { override val code: String = "kl-GL"; override val parentLang: KalaallisutGreenlandic get() = KalaallisutGreenlandic; }

        @Serializable(IetfLangSerializer::class)
        companion object : KalaallisutGreenlandic()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class CentralKhmer : IetfLang() {
        override val code: String = "km"

        @Serializable(IetfLangSerializer::class)
        object KH : CentralKhmer() { override val code: String = "km-KH"; override val parentLang: CentralKhmer get() = CentralKhmer; }

        @Serializable(IetfLangSerializer::class)
        companion object : CentralKhmer()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class Kannada : IetfLang() {
        override val code: String = "kn"

        @Serializable(IetfLangSerializer::class)
        object IN : Kannada() { override val code: String = "kn-IN"; override val parentLang: Kannada get() = Kannada; }

        @Serializable(IetfLangSerializer::class)
        companion object : Kannada()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class Korean : IetfLang() {
        override val code: String = "ko"

        @Serializable(IetfLangSerializer::class)
        object KP : Korean() { override val code: String = "ko-KP"; override val parentLang: Korean get() = Korean; }
        @Serializable(IetfLangSerializer::class)
        object KR : Korean() { override val code: String = "ko-KR"; override val parentLang: Korean get() = Korean; }

        @Serializable(IetfLangSerializer::class)
        companion object : Korean()
    }

    @Serializable(IetfLangSerializer::class)
    object Kanuri : IetfLang() { override val code: String = "kr" }

    @Serializable(IetfLangSerializer::class)
    sealed class Kashmiri : IetfLang() {
        override val code: String = "ks"


        @Serializable(IetfLangSerializer::class)
        sealed class Arab : Kashmiri() {
            override val code: String = "ks-Arab"
            override val parentLang: Kashmiri get() = Kashmiri;

            @Serializable(IetfLangSerializer::class)
            object IN : Arab() { override val code: String = "ks-Arab-IN"; override val parentLang: Arab get() = Arab; }

            @Serializable(IetfLangSerializer::class)
            companion object : Arab()
        }


        @Serializable(IetfLangSerializer::class)
        companion object : Kashmiri()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class Kurdish : IetfLang() {
        override val code: String = "ku"

        @Serializable(IetfLangSerializer::class)
        object TR : Kurdish() { override val code: String = "ku-TR"; override val parentLang: Kurdish get() = Kurdish; }

        @Serializable(IetfLangSerializer::class)
        companion object : Kurdish()
    }

    @Serializable(IetfLangSerializer::class)
    object Komi : IetfLang() { override val code: String = "kv" }

    @Serializable(IetfLangSerializer::class)
    sealed class Cornish : IetfLang() {
        override val code: String = "kw"

        @Serializable(IetfLangSerializer::class)
        object GB : Cornish() { override val code: String = "kw-GB"; override val parentLang: Cornish get() = Cornish; }

        @Serializable(IetfLangSerializer::class)
        companion object : Cornish()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class KirghizKyrgyz : IetfLang() {
        override val code: String = "ky"

        @Serializable(IetfLangSerializer::class)
        object KG : KirghizKyrgyz() { override val code: String = "ky-KG"; override val parentLang: KirghizKyrgyz get() = KirghizKyrgyz; }

        @Serializable(IetfLangSerializer::class)
        companion object : KirghizKyrgyz()
    }

    @Serializable(IetfLangSerializer::class)
    object Latin : IetfLang() { override val code: String = "la" }

    @Serializable(IetfLangSerializer::class)
    sealed class LuxembourgishLetzeburgesch : IetfLang() {
        override val code: String = "lb"

        @Serializable(IetfLangSerializer::class)
        object LU : LuxembourgishLetzeburgesch() { override val code: String = "lb-LU"; override val parentLang: LuxembourgishLetzeburgesch get() = LuxembourgishLetzeburgesch; }

        @Serializable(IetfLangSerializer::class)
        companion object : LuxembourgishLetzeburgesch()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class Ganda : IetfLang() {
        override val code: String = "lg"

        @Serializable(IetfLangSerializer::class)
        object UG : Ganda() { override val code: String = "lg-UG"; override val parentLang: Ganda get() = Ganda; }

        @Serializable(IetfLangSerializer::class)
        companion object : Ganda()
    }

    @Serializable(IetfLangSerializer::class)
    object LimburganLimburgerLimburgish : IetfLang() { override val code: String = "li" }

    @Serializable(IetfLangSerializer::class)
    sealed class Lingala : IetfLang() {
        override val code: String = "ln"

        @Serializable(IetfLangSerializer::class)
        object AO : Lingala() { override val code: String = "ln-AO"; override val parentLang: Lingala get() = Lingala; }
        @Serializable(IetfLangSerializer::class)
        object CD : Lingala() { override val code: String = "ln-CD"; override val parentLang: Lingala get() = Lingala; }
        @Serializable(IetfLangSerializer::class)
        object CF : Lingala() { override val code: String = "ln-CF"; override val parentLang: Lingala get() = Lingala; }
        @Serializable(IetfLangSerializer::class)
        object CG : Lingala() { override val code: String = "ln-CG"; override val parentLang: Lingala get() = Lingala; }

        @Serializable(IetfLangSerializer::class)
        companion object : Lingala()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class Lao : IetfLang() {
        override val code: String = "lo"

        @Serializable(IetfLangSerializer::class)
        object LA : Lao() { override val code: String = "lo-LA"; override val parentLang: Lao get() = Lao; }

        @Serializable(IetfLangSerializer::class)
        companion object : Lao()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class Lithuanian : IetfLang() {
        override val code: String = "lt"

        @Serializable(IetfLangSerializer::class)
        object LT : Lithuanian() { override val code: String = "lt-LT"; override val parentLang: Lithuanian get() = Lithuanian; }

        @Serializable(IetfLangSerializer::class)
        companion object : Lithuanian()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class LubaKatanga : IetfLang() {
        override val code: String = "lu"

        @Serializable(IetfLangSerializer::class)
        object CD : LubaKatanga() { override val code: String = "lu-CD"; override val parentLang: LubaKatanga get() = LubaKatanga; }

        @Serializable(IetfLangSerializer::class)
        companion object : LubaKatanga()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class Latvian : IetfLang() {
        override val code: String = "lv"

        @Serializable(IetfLangSerializer::class)
        object LV : Latvian() { override val code: String = "lv-LV"; override val parentLang: Latvian get() = Latvian; }

        @Serializable(IetfLangSerializer::class)
        companion object : Latvian()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class Malagasy : IetfLang() {
        override val code: String = "mg"

        @Serializable(IetfLangSerializer::class)
        object MG : Malagasy() { override val code: String = "mg-MG"; override val parentLang: Malagasy get() = Malagasy; }

        @Serializable(IetfLangSerializer::class)
        companion object : Malagasy()
    }

    @Serializable(IetfLangSerializer::class)
    object Marshallese : IetfLang() { override val code: String = "mh" }

    @Serializable(IetfLangSerializer::class)
    sealed class Maori : IetfLang() {
        override val code: String = "mi"

        @Serializable(IetfLangSerializer::class)
        object NZ : Maori() { override val code: String = "mi-NZ"; override val parentLang: Maori get() = Maori; }

        @Serializable(IetfLangSerializer::class)
        companion object : Maori()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class Macedonian : IetfLang() {
        override val code: String = "mk"

        @Serializable(IetfLangSerializer::class)
        object MK : Macedonian() { override val code: String = "mk-MK"; override val parentLang: Macedonian get() = Macedonian; }

        @Serializable(IetfLangSerializer::class)
        companion object : Macedonian()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class Malayalam : IetfLang() {
        override val code: String = "ml"

        @Serializable(IetfLangSerializer::class)
        object IN : Malayalam() { override val code: String = "ml-IN"; override val parentLang: Malayalam get() = Malayalam; }

        @Serializable(IetfLangSerializer::class)
        companion object : Malayalam()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class Mongolian : IetfLang() {
        override val code: String = "mn"

        @Serializable(IetfLangSerializer::class)
        object MN : Mongolian() { override val code: String = "mn-MN"; override val parentLang: Mongolian get() = Mongolian; }

        @Serializable(IetfLangSerializer::class)
        companion object : Mongolian()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class Marathi : IetfLang() {
        override val code: String = "mr"

        @Serializable(IetfLangSerializer::class)
        object IN : Marathi() { override val code: String = "mr-IN"; override val parentLang: Marathi get() = Marathi; }

        @Serializable(IetfLangSerializer::class)
        companion object : Marathi()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class Malay : IetfLang() {
        override val code: String = "ms"

        @Serializable(IetfLangSerializer::class)
        object BN : Malay() { override val code: String = "ms-BN"; override val parentLang: Malay get() = Malay; }
        @Serializable(IetfLangSerializer::class)
        object ID : Malay() { override val code: String = "ms-ID"; override val parentLang: Malay get() = Malay; }
        @Serializable(IetfLangSerializer::class)
        object MY : Malay() { override val code: String = "ms-MY"; override val parentLang: Malay get() = Malay; }
        @Serializable(IetfLangSerializer::class)
        object SG : Malay() { override val code: String = "ms-SG"; override val parentLang: Malay get() = Malay; }

        @Serializable(IetfLangSerializer::class)
        companion object : Malay()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class Maltese : IetfLang() {
        override val code: String = "mt"

        @Serializable(IetfLangSerializer::class)
        object MT : Maltese() { override val code: String = "mt-MT"; override val parentLang: Maltese get() = Maltese; }

        @Serializable(IetfLangSerializer::class)
        companion object : Maltese()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class Burmese : IetfLang() {
        override val code: String = "my"

        @Serializable(IetfLangSerializer::class)
        object MM : Burmese() { override val code: String = "my-MM"; override val parentLang: Burmese get() = Burmese; }

        @Serializable(IetfLangSerializer::class)
        companion object : Burmese()
    }

    @Serializable(IetfLangSerializer::class)
    object Nauru : IetfLang() { override val code: String = "na" }

    @Serializable(IetfLangSerializer::class)
    sealed class BokmalNorwegianNorwegianBokmal : IetfLang() {
        override val code: String = "nb"

        @Serializable(IetfLangSerializer::class)
        object NO : BokmalNorwegianNorwegianBokmal() { override val code: String = "nb-NO"; override val parentLang: BokmalNorwegianNorwegianBokmal get() = BokmalNorwegianNorwegianBokmal; }
        @Serializable(IetfLangSerializer::class)
        object SJ : BokmalNorwegianNorwegianBokmal() { override val code: String = "nb-SJ"; override val parentLang: BokmalNorwegianNorwegianBokmal get() = BokmalNorwegianNorwegianBokmal; }

        @Serializable(IetfLangSerializer::class)
        companion object : BokmalNorwegianNorwegianBokmal()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class NdebeleNorthNorthNdebele : IetfLang() {
        override val code: String = "nd"

        @Serializable(IetfLangSerializer::class)
        object ZW : NdebeleNorthNorthNdebele() { override val code: String = "nd-ZW"; override val parentLang: NdebeleNorthNorthNdebele get() = NdebeleNorthNorthNdebele; }

        @Serializable(IetfLangSerializer::class)
        companion object : NdebeleNorthNorthNdebele()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class Nepali : IetfLang() {
        override val code: String = "ne"

        @Serializable(IetfLangSerializer::class)
        object IN : Nepali() { override val code: String = "ne-IN"; override val parentLang: Nepali get() = Nepali; }
        @Serializable(IetfLangSerializer::class)
        object NP : Nepali() { override val code: String = "ne-NP"; override val parentLang: Nepali get() = Nepali; }

        @Serializable(IetfLangSerializer::class)
        companion object : Nepali()
    }

    @Serializable(IetfLangSerializer::class)
    object Ndonga : IetfLang() { override val code: String = "ng" }

    @Serializable(IetfLangSerializer::class)
    sealed class DutchFlemish : IetfLang() {
        override val code: String = "nl"

        @Serializable(IetfLangSerializer::class)
        object AW : DutchFlemish() { override val code: String = "nl-AW"; override val parentLang: DutchFlemish get() = DutchFlemish; }
        @Serializable(IetfLangSerializer::class)
        object BE : DutchFlemish() { override val code: String = "nl-BE"; override val parentLang: DutchFlemish get() = DutchFlemish; }
        @Serializable(IetfLangSerializer::class)
        object BQ : DutchFlemish() { override val code: String = "nl-BQ"; override val parentLang: DutchFlemish get() = DutchFlemish; }
        @Serializable(IetfLangSerializer::class)
        object CW : DutchFlemish() { override val code: String = "nl-CW"; override val parentLang: DutchFlemish get() = DutchFlemish; }
        @Serializable(IetfLangSerializer::class)
        object NL : DutchFlemish() { override val code: String = "nl-NL"; override val parentLang: DutchFlemish get() = DutchFlemish; }
        @Serializable(IetfLangSerializer::class)
        object SR : DutchFlemish() { override val code: String = "nl-SR"; override val parentLang: DutchFlemish get() = DutchFlemish; }
        @Serializable(IetfLangSerializer::class)
        object SX : DutchFlemish() { override val code: String = "nl-SX"; override val parentLang: DutchFlemish get() = DutchFlemish; }

        @Serializable(IetfLangSerializer::class)
        companion object : DutchFlemish()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class NorwegianNynorskNynorskNorwegian : IetfLang() {
        override val code: String = "nn"

        @Serializable(IetfLangSerializer::class)
        object NO : NorwegianNynorskNynorskNorwegian() { override val code: String = "nn-NO"; override val parentLang: NorwegianNynorskNynorskNorwegian get() = NorwegianNynorskNynorskNorwegian; }

        @Serializable(IetfLangSerializer::class)
        companion object : NorwegianNynorskNynorskNorwegian()
    }

    @Serializable(IetfLangSerializer::class)
    object Norwegian : IetfLang() { override val code: String = "no" }
    @Serializable(IetfLangSerializer::class)
    object NdebeleSouthSouthNdebele : IetfLang() { override val code: String = "nr" }
    @Serializable(IetfLangSerializer::class)
    object NavajoNavaho : IetfLang() { override val code: String = "nv" }
    @Serializable(IetfLangSerializer::class)
    object ChichewaChewaNyanja : IetfLang() { override val code: String = "ny" }
    @Serializable(IetfLangSerializer::class)
    object OccitanPost1500 : IetfLang() { override val code: String = "oc" }
    @Serializable(IetfLangSerializer::class)
    object Ojibwa : IetfLang() { override val code: String = "oj" }

    @Serializable(IetfLangSerializer::class)
    sealed class Oromo : IetfLang() {
        override val code: String = "om"

        @Serializable(IetfLangSerializer::class)
        object ET : Oromo() { override val code: String = "om-ET"; override val parentLang: Oromo get() = Oromo; }
        @Serializable(IetfLangSerializer::class)
        object KE : Oromo() { override val code: String = "om-KE"; override val parentLang: Oromo get() = Oromo; }

        @Serializable(IetfLangSerializer::class)
        companion object : Oromo()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class Oriya : IetfLang() {
        override val code: String = "or"

        @Serializable(IetfLangSerializer::class)
        object IN : Oriya() { override val code: String = "or-IN"; override val parentLang: Oriya get() = Oriya; }

        @Serializable(IetfLangSerializer::class)
        companion object : Oriya()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class OssetianOssetic : IetfLang() {
        override val code: String = "os"

        @Serializable(IetfLangSerializer::class)
        object GE : OssetianOssetic() { override val code: String = "os-GE"; override val parentLang: OssetianOssetic get() = OssetianOssetic; }
        @Serializable(IetfLangSerializer::class)
        object RU : OssetianOssetic() { override val code: String = "os-RU"; override val parentLang: OssetianOssetic get() = OssetianOssetic; }

        @Serializable(IetfLangSerializer::class)
        companion object : OssetianOssetic()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class PanjabiPunjabi : IetfLang() {
        override val code: String = "pa"


        @Serializable(IetfLangSerializer::class)
        sealed class Arab : PanjabiPunjabi() {
            override val code: String = "pa-Arab"
            override val parentLang: PanjabiPunjabi get() = PanjabiPunjabi;

            @Serializable(IetfLangSerializer::class)
            object PK : Arab() { override val code: String = "pa-Arab-PK"; override val parentLang: Arab get() = Arab; }

            @Serializable(IetfLangSerializer::class)
            companion object : Arab()
        }


        @Serializable(IetfLangSerializer::class)
        sealed class Guru : PanjabiPunjabi() {
            override val code: String = "pa-Guru"
            override val parentLang: PanjabiPunjabi get() = PanjabiPunjabi;

            @Serializable(IetfLangSerializer::class)
            object IN : Guru() { override val code: String = "pa-Guru-IN"; override val parentLang: Guru get() = Guru; }

            @Serializable(IetfLangSerializer::class)
            companion object : Guru()
        }


        @Serializable(IetfLangSerializer::class)
        companion object : PanjabiPunjabi()
    }

    @Serializable(IetfLangSerializer::class)
    object Pali : IetfLang() { override val code: String = "pi" }

    @Serializable(IetfLangSerializer::class)
    sealed class Polish : IetfLang() {
        override val code: String = "pl"

        @Serializable(IetfLangSerializer::class)
        object PL : Polish() { override val code: String = "pl-PL"; override val parentLang: Polish get() = Polish; }

        @Serializable(IetfLangSerializer::class)
        companion object : Polish()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class PushtoPashto : IetfLang() {
        override val code: String = "ps"

        @Serializable(IetfLangSerializer::class)
        object AF : PushtoPashto() { override val code: String = "ps-AF"; override val parentLang: PushtoPashto get() = PushtoPashto; }
        @Serializable(IetfLangSerializer::class)
        object PK : PushtoPashto() { override val code: String = "ps-PK"; override val parentLang: PushtoPashto get() = PushtoPashto; }

        @Serializable(IetfLangSerializer::class)
        companion object : PushtoPashto()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class Portuguese : IetfLang() {
        override val code: String = "pt"

        @Serializable(IetfLangSerializer::class)
        object AO : Portuguese() { override val code: String = "pt-AO"; override val parentLang: Portuguese get() = Portuguese; }
        @Serializable(IetfLangSerializer::class)
        object BR : Portuguese() { override val code: String = "pt-BR"; override val parentLang: Portuguese get() = Portuguese; }
        @Serializable(IetfLangSerializer::class)
        object CH : Portuguese() { override val code: String = "pt-CH"; override val parentLang: Portuguese get() = Portuguese; }
        @Serializable(IetfLangSerializer::class)
        object CV : Portuguese() { override val code: String = "pt-CV"; override val parentLang: Portuguese get() = Portuguese; }
        @Serializable(IetfLangSerializer::class)
        object GQ : Portuguese() { override val code: String = "pt-GQ"; override val parentLang: Portuguese get() = Portuguese; }
        @Serializable(IetfLangSerializer::class)
        object GW : Portuguese() { override val code: String = "pt-GW"; override val parentLang: Portuguese get() = Portuguese; }
        @Serializable(IetfLangSerializer::class)
        object LU : Portuguese() { override val code: String = "pt-LU"; override val parentLang: Portuguese get() = Portuguese; }
        @Serializable(IetfLangSerializer::class)
        object MO : Portuguese() { override val code: String = "pt-MO"; override val parentLang: Portuguese get() = Portuguese; }
        @Serializable(IetfLangSerializer::class)
        object MZ : Portuguese() { override val code: String = "pt-MZ"; override val parentLang: Portuguese get() = Portuguese; }
        @Serializable(IetfLangSerializer::class)
        object PT : Portuguese() { override val code: String = "pt-PT"; override val parentLang: Portuguese get() = Portuguese; }
        @Serializable(IetfLangSerializer::class)
        object ST : Portuguese() { override val code: String = "pt-ST"; override val parentLang: Portuguese get() = Portuguese; }
        @Serializable(IetfLangSerializer::class)
        object TL : Portuguese() { override val code: String = "pt-TL"; override val parentLang: Portuguese get() = Portuguese; }

        @Serializable(IetfLangSerializer::class)
        companion object : Portuguese()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class Quechua : IetfLang() {
        override val code: String = "qu"

        @Serializable(IetfLangSerializer::class)
        object BO : Quechua() { override val code: String = "qu-BO"; override val parentLang: Quechua get() = Quechua; }
        @Serializable(IetfLangSerializer::class)
        object EC : Quechua() { override val code: String = "qu-EC"; override val parentLang: Quechua get() = Quechua; }
        @Serializable(IetfLangSerializer::class)
        object PE : Quechua() { override val code: String = "qu-PE"; override val parentLang: Quechua get() = Quechua; }

        @Serializable(IetfLangSerializer::class)
        companion object : Quechua()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class Romansh : IetfLang() {
        override val code: String = "rm"

        @Serializable(IetfLangSerializer::class)
        object CH : Romansh() { override val code: String = "rm-CH"; override val parentLang: Romansh get() = Romansh; }

        @Serializable(IetfLangSerializer::class)
        companion object : Romansh()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class Rundi : IetfLang() {
        override val code: String = "rn"

        @Serializable(IetfLangSerializer::class)
        object BI : Rundi() { override val code: String = "rn-BI"; override val parentLang: Rundi get() = Rundi; }

        @Serializable(IetfLangSerializer::class)
        companion object : Rundi()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class RomanianMoldavianMoldovan : IetfLang() {
        override val code: String = "ro"

        @Serializable(IetfLangSerializer::class)
        object MD : RomanianMoldavianMoldovan() { override val code: String = "ro-MD"; override val parentLang: RomanianMoldavianMoldovan get() = RomanianMoldavianMoldovan; }
        @Serializable(IetfLangSerializer::class)
        object RO : RomanianMoldavianMoldovan() { override val code: String = "ro-RO"; override val parentLang: RomanianMoldavianMoldovan get() = RomanianMoldavianMoldovan; }

        @Serializable(IetfLangSerializer::class)
        companion object : RomanianMoldavianMoldovan()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class Russian : IetfLang() {
        override val code: String = "ru"

        @Serializable(IetfLangSerializer::class)
        object BY : Russian() { override val code: String = "ru-BY"; override val parentLang: Russian get() = Russian; }
        @Serializable(IetfLangSerializer::class)
        object KG : Russian() { override val code: String = "ru-KG"; override val parentLang: Russian get() = Russian; }
        @Serializable(IetfLangSerializer::class)
        object KZ : Russian() { override val code: String = "ru-KZ"; override val parentLang: Russian get() = Russian; }
        @Serializable(IetfLangSerializer::class)
        object MD : Russian() { override val code: String = "ru-MD"; override val parentLang: Russian get() = Russian; }
        @Serializable(IetfLangSerializer::class)
        object RU : Russian() { override val code: String = "ru-RU"; override val parentLang: Russian get() = Russian; }
        @Serializable(IetfLangSerializer::class)
        object UA : Russian() { override val code: String = "ru-UA"; override val parentLang: Russian get() = Russian; }

        @Serializable(IetfLangSerializer::class)
        companion object : Russian()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class Kinyarwanda : IetfLang() {
        override val code: String = "rw"

        @Serializable(IetfLangSerializer::class)
        object RW : Kinyarwanda() { override val code: String = "rw-RW"; override val parentLang: Kinyarwanda get() = Kinyarwanda; }

        @Serializable(IetfLangSerializer::class)
        companion object : Kinyarwanda()
    }

    @Serializable(IetfLangSerializer::class)
    object Sanskrit : IetfLang() { override val code: String = "sa" }
    @Serializable(IetfLangSerializer::class)
    object Sardinian : IetfLang() { override val code: String = "sc" }

    @Serializable(IetfLangSerializer::class)
    sealed class Sindhi : IetfLang() {
        override val code: String = "sd"


        @Serializable(IetfLangSerializer::class)
        sealed class Arab : Sindhi() {
            override val code: String = "sd-Arab"
            override val parentLang: Sindhi get() = Sindhi;

            @Serializable(IetfLangSerializer::class)
            object PK : Arab() { override val code: String = "sd-Arab-PK"; override val parentLang: Arab get() = Arab; }

            @Serializable(IetfLangSerializer::class)
            companion object : Arab()
        }


        @Serializable(IetfLangSerializer::class)
        sealed class Deva : Sindhi() {
            override val code: String = "sd-Deva"
            override val parentLang: Sindhi get() = Sindhi;

            @Serializable(IetfLangSerializer::class)
            object IN : Deva() { override val code: String = "sd-Deva-IN"; override val parentLang: Deva get() = Deva; }

            @Serializable(IetfLangSerializer::class)
            companion object : Deva()
        }


        @Serializable(IetfLangSerializer::class)
        companion object : Sindhi()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class NorthernSami : IetfLang() {
        override val code: String = "se"

        @Serializable(IetfLangSerializer::class)
        object FI : NorthernSami() { override val code: String = "se-FI"; override val parentLang: NorthernSami get() = NorthernSami; }
        @Serializable(IetfLangSerializer::class)
        object NO : NorthernSami() { override val code: String = "se-NO"; override val parentLang: NorthernSami get() = NorthernSami; }
        @Serializable(IetfLangSerializer::class)
        object SE : NorthernSami() { override val code: String = "se-SE"; override val parentLang: NorthernSami get() = NorthernSami; }

        @Serializable(IetfLangSerializer::class)
        companion object : NorthernSami()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class Sango : IetfLang() {
        override val code: String = "sg"

        @Serializable(IetfLangSerializer::class)
        object CF : Sango() { override val code: String = "sg-CF"; override val parentLang: Sango get() = Sango; }

        @Serializable(IetfLangSerializer::class)
        companion object : Sango()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class SinhalaSinhalese : IetfLang() {
        override val code: String = "si"

        @Serializable(IetfLangSerializer::class)
        object LK : SinhalaSinhalese() { override val code: String = "si-LK"; override val parentLang: SinhalaSinhalese get() = SinhalaSinhalese; }

        @Serializable(IetfLangSerializer::class)
        companion object : SinhalaSinhalese()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class Slovak : IetfLang() {
        override val code: String = "sk"

        @Serializable(IetfLangSerializer::class)
        object SK : Slovak() { override val code: String = "sk-SK"; override val parentLang: Slovak get() = Slovak; }

        @Serializable(IetfLangSerializer::class)
        companion object : Slovak()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class Slovenian : IetfLang() {
        override val code: String = "sl"

        @Serializable(IetfLangSerializer::class)
        object SI : Slovenian() { override val code: String = "sl-SI"; override val parentLang: Slovenian get() = Slovenian; }

        @Serializable(IetfLangSerializer::class)
        companion object : Slovenian()
    }

    @Serializable(IetfLangSerializer::class)
    object Samoan : IetfLang() { override val code: String = "sm" }

    @Serializable(IetfLangSerializer::class)
    sealed class Shona : IetfLang() {
        override val code: String = "sn"

        @Serializable(IetfLangSerializer::class)
        object ZW : Shona() { override val code: String = "sn-ZW"; override val parentLang: Shona get() = Shona; }

        @Serializable(IetfLangSerializer::class)
        companion object : Shona()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class Somali : IetfLang() {
        override val code: String = "so"

        @Serializable(IetfLangSerializer::class)
        object DJ : Somali() { override val code: String = "so-DJ"; override val parentLang: Somali get() = Somali; }
        @Serializable(IetfLangSerializer::class)
        object ET : Somali() { override val code: String = "so-ET"; override val parentLang: Somali get() = Somali; }
        @Serializable(IetfLangSerializer::class)
        object KE : Somali() { override val code: String = "so-KE"; override val parentLang: Somali get() = Somali; }
        @Serializable(IetfLangSerializer::class)
        object SO : Somali() { override val code: String = "so-SO"; override val parentLang: Somali get() = Somali; }

        @Serializable(IetfLangSerializer::class)
        companion object : Somali()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class Albanian : IetfLang() {
        override val code: String = "sq"

        @Serializable(IetfLangSerializer::class)
        object AL : Albanian() { override val code: String = "sq-AL"; override val parentLang: Albanian get() = Albanian; }
        @Serializable(IetfLangSerializer::class)
        object MK : Albanian() { override val code: String = "sq-MK"; override val parentLang: Albanian get() = Albanian; }
        @Serializable(IetfLangSerializer::class)
        object XK : Albanian() { override val code: String = "sq-XK"; override val parentLang: Albanian get() = Albanian; }

        @Serializable(IetfLangSerializer::class)
        companion object : Albanian()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class Serbian : IetfLang() {
        override val code: String = "sr"


        @Serializable(IetfLangSerializer::class)
        sealed class Cyrl : Serbian() {
            override val code: String = "sr-Cyrl"
            override val parentLang: Serbian get() = Serbian;

            @Serializable(IetfLangSerializer::class)
            object BA : Cyrl() { override val code: String = "sr-Cyrl-BA"; override val parentLang: Cyrl get() = Cyrl; }
            @Serializable(IetfLangSerializer::class)
            object ME : Cyrl() { override val code: String = "sr-Cyrl-ME"; override val parentLang: Cyrl get() = Cyrl; }
            @Serializable(IetfLangSerializer::class)
            object RS : Cyrl() { override val code: String = "sr-Cyrl-RS"; override val parentLang: Cyrl get() = Cyrl; }
            @Serializable(IetfLangSerializer::class)
            object XK : Cyrl() { override val code: String = "sr-Cyrl-XK"; override val parentLang: Cyrl get() = Cyrl; }

            @Serializable(IetfLangSerializer::class)
            companion object : Cyrl()
        }


        @Serializable(IetfLangSerializer::class)
        sealed class Latn : Serbian() {
            override val code: String = "sr-Latn"
            override val parentLang: Serbian get() = Serbian;

            @Serializable(IetfLangSerializer::class)
            object BA : Latn() { override val code: String = "sr-Latn-BA"; override val parentLang: Latn get() = Latn; }
            @Serializable(IetfLangSerializer::class)
            object ME : Latn() { override val code: String = "sr-Latn-ME"; override val parentLang: Latn get() = Latn; }
            @Serializable(IetfLangSerializer::class)
            object RS : Latn() { override val code: String = "sr-Latn-RS"; override val parentLang: Latn get() = Latn; }
            @Serializable(IetfLangSerializer::class)
            object XK : Latn() { override val code: String = "sr-Latn-XK"; override val parentLang: Latn get() = Latn; }

            @Serializable(IetfLangSerializer::class)
            companion object : Latn()
        }


        @Serializable(IetfLangSerializer::class)
        companion object : Serbian()
    }

    @Serializable(IetfLangSerializer::class)
    object Swati : IetfLang() { override val code: String = "ss" }
    @Serializable(IetfLangSerializer::class)
    object SothoSouthern : IetfLang() { override val code: String = "st" }

    @Serializable(IetfLangSerializer::class)
    sealed class Sundanese : IetfLang() {
        override val code: String = "su"


        @Serializable(IetfLangSerializer::class)
        sealed class Latn : Sundanese() {
            override val code: String = "su-Latn"
            override val parentLang: Sundanese get() = Sundanese;

            @Serializable(IetfLangSerializer::class)
            object ID : Latn() { override val code: String = "su-Latn-ID"; override val parentLang: Latn get() = Latn; }

            @Serializable(IetfLangSerializer::class)
            companion object : Latn()
        }


        @Serializable(IetfLangSerializer::class)
        companion object : Sundanese()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class Swedish : IetfLang() {
        override val code: String = "sv"

        @Serializable(IetfLangSerializer::class)
        object AX : Swedish() { override val code: String = "sv-AX"; override val parentLang: Swedish get() = Swedish; }
        @Serializable(IetfLangSerializer::class)
        object FI : Swedish() { override val code: String = "sv-FI"; override val parentLang: Swedish get() = Swedish; }
        @Serializable(IetfLangSerializer::class)
        object SE : Swedish() { override val code: String = "sv-SE"; override val parentLang: Swedish get() = Swedish; }

        @Serializable(IetfLangSerializer::class)
        companion object : Swedish()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class Swahili : IetfLang() {
        override val code: String = "sw"

        @Serializable(IetfLangSerializer::class)
        object CD : Swahili() { override val code: String = "sw-CD"; override val parentLang: Swahili get() = Swahili; }
        @Serializable(IetfLangSerializer::class)
        object KE : Swahili() { override val code: String = "sw-KE"; override val parentLang: Swahili get() = Swahili; }
        @Serializable(IetfLangSerializer::class)
        object TZ : Swahili() { override val code: String = "sw-TZ"; override val parentLang: Swahili get() = Swahili; }
        @Serializable(IetfLangSerializer::class)
        object UG : Swahili() { override val code: String = "sw-UG"; override val parentLang: Swahili get() = Swahili; }

        @Serializable(IetfLangSerializer::class)
        companion object : Swahili()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class Tamil : IetfLang() {
        override val code: String = "ta"

        @Serializable(IetfLangSerializer::class)
        object IN : Tamil() { override val code: String = "ta-IN"; override val parentLang: Tamil get() = Tamil; }
        @Serializable(IetfLangSerializer::class)
        object LK : Tamil() { override val code: String = "ta-LK"; override val parentLang: Tamil get() = Tamil; }
        @Serializable(IetfLangSerializer::class)
        object MY : Tamil() { override val code: String = "ta-MY"; override val parentLang: Tamil get() = Tamil; }
        @Serializable(IetfLangSerializer::class)
        object SG : Tamil() { override val code: String = "ta-SG"; override val parentLang: Tamil get() = Tamil; }

        @Serializable(IetfLangSerializer::class)
        companion object : Tamil()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class Telugu : IetfLang() {
        override val code: String = "te"

        @Serializable(IetfLangSerializer::class)
        object IN : Telugu() { override val code: String = "te-IN"; override val parentLang: Telugu get() = Telugu; }

        @Serializable(IetfLangSerializer::class)
        companion object : Telugu()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class Tajik : IetfLang() {
        override val code: String = "tg"

        @Serializable(IetfLangSerializer::class)
        object TJ : Tajik() { override val code: String = "tg-TJ"; override val parentLang: Tajik get() = Tajik; }

        @Serializable(IetfLangSerializer::class)
        companion object : Tajik()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class Thai : IetfLang() {
        override val code: String = "th"

        @Serializable(IetfLangSerializer::class)
        object TH : Thai() { override val code: String = "th-TH"; override val parentLang: Thai get() = Thai; }

        @Serializable(IetfLangSerializer::class)
        companion object : Thai()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class Tigrinya : IetfLang() {
        override val code: String = "ti"

        @Serializable(IetfLangSerializer::class)
        object ER : Tigrinya() { override val code: String = "ti-ER"; override val parentLang: Tigrinya get() = Tigrinya; }
        @Serializable(IetfLangSerializer::class)
        object ET : Tigrinya() { override val code: String = "ti-ET"; override val parentLang: Tigrinya get() = Tigrinya; }

        @Serializable(IetfLangSerializer::class)
        companion object : Tigrinya()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class Turkmen : IetfLang() {
        override val code: String = "tk"

        @Serializable(IetfLangSerializer::class)
        object TM : Turkmen() { override val code: String = "tk-TM"; override val parentLang: Turkmen get() = Turkmen; }

        @Serializable(IetfLangSerializer::class)
        companion object : Turkmen()
    }

    @Serializable(IetfLangSerializer::class)
    object Tagalog : IetfLang() { override val code: String = "tl" }
    @Serializable(IetfLangSerializer::class)
    object Tswana : IetfLang() { override val code: String = "tn" }

    @Serializable(IetfLangSerializer::class)
    sealed class TongaTongaIslands : IetfLang() {
        override val code: String = "to"

        @Serializable(IetfLangSerializer::class)
        object TO : TongaTongaIslands() { override val code: String = "to-TO"; override val parentLang: TongaTongaIslands get() = TongaTongaIslands; }

        @Serializable(IetfLangSerializer::class)
        companion object : TongaTongaIslands()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class Turkish : IetfLang() {
        override val code: String = "tr"

        @Serializable(IetfLangSerializer::class)
        object CY : Turkish() { override val code: String = "tr-CY"; override val parentLang: Turkish get() = Turkish; }
        @Serializable(IetfLangSerializer::class)
        object TR : Turkish() { override val code: String = "tr-TR"; override val parentLang: Turkish get() = Turkish; }

        @Serializable(IetfLangSerializer::class)
        companion object : Turkish()
    }

    @Serializable(IetfLangSerializer::class)
    object Tsonga : IetfLang() { override val code: String = "ts" }

    @Serializable(IetfLangSerializer::class)
    sealed class Tatar : IetfLang() {
        override val code: String = "tt"

        @Serializable(IetfLangSerializer::class)
        object RU : Tatar() { override val code: String = "tt-RU"; override val parentLang: Tatar get() = Tatar; }

        @Serializable(IetfLangSerializer::class)
        companion object : Tatar()
    }

    @Serializable(IetfLangSerializer::class)
    object Twi : IetfLang() { override val code: String = "tw" }
    @Serializable(IetfLangSerializer::class)
    object Tahitian : IetfLang() { override val code: String = "ty" }

    @Serializable(IetfLangSerializer::class)
    sealed class UighurUyghur : IetfLang() {
        override val code: String = "ug"

        @Serializable(IetfLangSerializer::class)
        object CN : UighurUyghur() { override val code: String = "ug-CN"; override val parentLang: UighurUyghur get() = UighurUyghur; }

        @Serializable(IetfLangSerializer::class)
        companion object : UighurUyghur()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class Ukrainian : IetfLang() {
        override val code: String = "uk"

        @Serializable(IetfLangSerializer::class)
        object UA : Ukrainian() { override val code: String = "uk-UA"; override val parentLang: Ukrainian get() = Ukrainian; }

        @Serializable(IetfLangSerializer::class)
        companion object : Ukrainian()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class Urdu : IetfLang() {
        override val code: String = "ur"

        @Serializable(IetfLangSerializer::class)
        object IN : Urdu() { override val code: String = "ur-IN"; override val parentLang: Urdu get() = Urdu; }
        @Serializable(IetfLangSerializer::class)
        object PK : Urdu() { override val code: String = "ur-PK"; override val parentLang: Urdu get() = Urdu; }

        @Serializable(IetfLangSerializer::class)
        companion object : Urdu()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class Uzbek : IetfLang() {
        override val code: String = "uz"


        @Serializable(IetfLangSerializer::class)
        sealed class Arab : Uzbek() {
            override val code: String = "uz-Arab"
            override val parentLang: Uzbek get() = Uzbek;

            @Serializable(IetfLangSerializer::class)
            object AF : Arab() { override val code: String = "uz-Arab-AF"; override val parentLang: Arab get() = Arab; }

            @Serializable(IetfLangSerializer::class)
            companion object : Arab()
        }


        @Serializable(IetfLangSerializer::class)
        sealed class Cyrl : Uzbek() {
            override val code: String = "uz-Cyrl"
            override val parentLang: Uzbek get() = Uzbek;

            @Serializable(IetfLangSerializer::class)
            object UZ : Cyrl() { override val code: String = "uz-Cyrl-UZ"; override val parentLang: Cyrl get() = Cyrl; }

            @Serializable(IetfLangSerializer::class)
            companion object : Cyrl()
        }


        @Serializable(IetfLangSerializer::class)
        sealed class Latn : Uzbek() {
            override val code: String = "uz-Latn"
            override val parentLang: Uzbek get() = Uzbek;

            @Serializable(IetfLangSerializer::class)
            object UZ : Latn() { override val code: String = "uz-Latn-UZ"; override val parentLang: Latn get() = Latn; }

            @Serializable(IetfLangSerializer::class)
            companion object : Latn()
        }


        @Serializable(IetfLangSerializer::class)
        companion object : Uzbek()
    }

    @Serializable(IetfLangSerializer::class)
    object Venda : IetfLang() { override val code: String = "ve" }

    @Serializable(IetfLangSerializer::class)
    sealed class Vietnamese : IetfLang() {
        override val code: String = "vi"

        @Serializable(IetfLangSerializer::class)
        object VN : Vietnamese() { override val code: String = "vi-VN"; override val parentLang: Vietnamese get() = Vietnamese; }

        @Serializable(IetfLangSerializer::class)
        companion object : Vietnamese()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class Volapuk : IetfLang() {
        override val code: String = "vo"

        @Serializable(IetfLangSerializer::class)
        object L001 : Volapuk() { override val code: String = "vo-001"; override val parentLang: Volapuk get() = Volapuk; }

        @Serializable(IetfLangSerializer::class)
        companion object : Volapuk()
    }

    @Serializable(IetfLangSerializer::class)
    object Walloon : IetfLang() { override val code: String = "wa" }

    @Serializable(IetfLangSerializer::class)
    sealed class Wolof : IetfLang() {
        override val code: String = "wo"

        @Serializable(IetfLangSerializer::class)
        object SN : Wolof() { override val code: String = "wo-SN"; override val parentLang: Wolof get() = Wolof; }

        @Serializable(IetfLangSerializer::class)
        companion object : Wolof()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class Xhosa : IetfLang() {
        override val code: String = "xh"

        @Serializable(IetfLangSerializer::class)
        object ZA : Xhosa() { override val code: String = "xh-ZA"; override val parentLang: Xhosa get() = Xhosa; }

        @Serializable(IetfLangSerializer::class)
        companion object : Xhosa()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class Yiddish : IetfLang() {
        override val code: String = "yi"

        @Serializable(IetfLangSerializer::class)
        object L001 : Yiddish() { override val code: String = "yi-001"; override val parentLang: Yiddish get() = Yiddish; }

        @Serializable(IetfLangSerializer::class)
        companion object : Yiddish()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class Yoruba : IetfLang() {
        override val code: String = "yo"

        @Serializable(IetfLangSerializer::class)
        object BJ : Yoruba() { override val code: String = "yo-BJ"; override val parentLang: Yoruba get() = Yoruba; }
        @Serializable(IetfLangSerializer::class)
        object NG : Yoruba() { override val code: String = "yo-NG"; override val parentLang: Yoruba get() = Yoruba; }

        @Serializable(IetfLangSerializer::class)
        companion object : Yoruba()
    }

    @Serializable(IetfLangSerializer::class)
    object ZhuangChuang : IetfLang() { override val code: String = "za" }

    @Serializable(IetfLangSerializer::class)
    sealed class Chinese : IetfLang() {
        override val code: String = "zh"


        @Serializable(IetfLangSerializer::class)
        sealed class Hans : Chinese() {
            override val code: String = "zh-Hans"
            override val parentLang: Chinese get() = Chinese;

            @Serializable(IetfLangSerializer::class)
            object CN : Hans() { override val code: String = "zh-Hans-CN"; override val parentLang: Hans get() = Hans; }
            @Serializable(IetfLangSerializer::class)
            object HK : Hans() { override val code: String = "zh-Hans-HK"; override val parentLang: Hans get() = Hans; }
            @Serializable(IetfLangSerializer::class)
            object MO : Hans() { override val code: String = "zh-Hans-MO"; override val parentLang: Hans get() = Hans; }
            @Serializable(IetfLangSerializer::class)
            object SG : Hans() { override val code: String = "zh-Hans-SG"; override val parentLang: Hans get() = Hans; }

            @Serializable(IetfLangSerializer::class)
            companion object : Hans()
        }


        @Serializable(IetfLangSerializer::class)
        sealed class Hant : Chinese() {
            override val code: String = "zh-Hant"
            override val parentLang: Chinese get() = Chinese;

            @Serializable(IetfLangSerializer::class)
            object HK : Hant() { override val code: String = "zh-Hant-HK"; override val parentLang: Hant get() = Hant; }
            @Serializable(IetfLangSerializer::class)
            object MO : Hant() { override val code: String = "zh-Hant-MO"; override val parentLang: Hant get() = Hant; }
            @Serializable(IetfLangSerializer::class)
            object TW : Hant() { override val code: String = "zh-Hant-TW"; override val parentLang: Hant get() = Hant; }

            @Serializable(IetfLangSerializer::class)
            companion object : Hant()
        }


        @Serializable(IetfLangSerializer::class)
        companion object : Chinese()
    }


    @Serializable(IetfLangSerializer::class)
    sealed class Zulu : IetfLang() {
        override val code: String = "zu"

        @Serializable(IetfLangSerializer::class)
        object ZA : Zulu() { override val code: String = "zu-ZA"; override val parentLang: Zulu get() = Zulu; }

        @Serializable(IetfLangSerializer::class)
        companion object : Zulu()
    }


    @Serializable(IetfLangSerializer::class)
    data class UnknownIetfLang (override val code: String) : IetfLang() {
        override val parentLang = code.dropLastWhile { it != '-' }.removeSuffix("-").takeIf { it.length > 0 } ?.let(::UnknownIetfLang)
    }
    @Deprecated("Renamed", ReplaceWith("UnknownIetfLang", "dev.inmo.micro_utils.language_codes.IetfLang.UnknownIetfLang"))
    val UnknownIetfLanguageCode = UnknownIetfLang

    override fun toString() = code
}
@Deprecated("Renamed", ReplaceWith("IetfLang", "dev.inmo.micro_utils.language_codes.IetfLang"))
typealias IetfLanguageCode = IetfLang