package dev.inmo.micro_utils.language_codes

import kotlinx.serialization.Serializable

@Serializable(IetfLanguageCodeSerializer::class)
sealed interface IetfLanguageCode {
    val code: String


    @Serializable(IetfLanguageCodeSerializer::class)
    object Afar : IetfLanguageCode { override val code: String = "aa" }


    @Serializable(IetfLanguageCodeSerializer::class)
    object Abkhazian : IetfLanguageCode { override val code: String = "ab" }


    @Serializable(IetfLanguageCodeSerializer::class)
    object Avestan : IetfLanguageCode { override val code: String = "ae" }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Afrikaans : IetfLanguageCode {
        override val code: String = "af"


        @Serializable(IetfLanguageCodeSerializer::class)
        object NA : Afrikaans() { override val code: String = "af-NA" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object ZA : Afrikaans() { override val code: String = "af-ZA" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Afrikaans()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Akan : IetfLanguageCode {
        override val code: String = "ak"


        @Serializable(IetfLanguageCodeSerializer::class)
        object GH : Akan() { override val code: String = "ak-GH" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Akan()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Amharic : IetfLanguageCode {
        override val code: String = "am"


        @Serializable(IetfLanguageCodeSerializer::class)
        object ET : Amharic() { override val code: String = "am-ET" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Amharic()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    object Aragonese : IetfLanguageCode { override val code: String = "an" }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Arabic : IetfLanguageCode {
        override val code: String = "ar"


        @Serializable(IetfLanguageCodeSerializer::class)
        object L001 : Arabic() { override val code: String = "ar-001" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object AE : Arabic() { override val code: String = "ar-AE" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object BH : Arabic() { override val code: String = "ar-BH" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object DJ : Arabic() { override val code: String = "ar-DJ" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object DZ : Arabic() { override val code: String = "ar-DZ" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object EG : Arabic() { override val code: String = "ar-EG" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object EH : Arabic() { override val code: String = "ar-EH" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object ER : Arabic() { override val code: String = "ar-ER" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object IL : Arabic() { override val code: String = "ar-IL" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object IQ : Arabic() { override val code: String = "ar-IQ" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object JO : Arabic() { override val code: String = "ar-JO" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object KM : Arabic() { override val code: String = "ar-KM" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object KW : Arabic() { override val code: String = "ar-KW" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object LB : Arabic() { override val code: String = "ar-LB" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object LY : Arabic() { override val code: String = "ar-LY" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object MA : Arabic() { override val code: String = "ar-MA" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object MR : Arabic() { override val code: String = "ar-MR" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object OM : Arabic() { override val code: String = "ar-OM" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object PS : Arabic() { override val code: String = "ar-PS" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object QA : Arabic() { override val code: String = "ar-QA" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object SA : Arabic() { override val code: String = "ar-SA" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object SD : Arabic() { override val code: String = "ar-SD" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object SO : Arabic() { override val code: String = "ar-SO" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object SS : Arabic() { override val code: String = "ar-SS" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object SY : Arabic() { override val code: String = "ar-SY" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object TD : Arabic() { override val code: String = "ar-TD" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object TN : Arabic() { override val code: String = "ar-TN" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object YE : Arabic() { override val code: String = "ar-YE" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Arabic()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Assamese : IetfLanguageCode {
        override val code: String = "as"


        @Serializable(IetfLanguageCodeSerializer::class)
        object IN : Assamese() { override val code: String = "as-IN" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Assamese()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    object Avaric : IetfLanguageCode { override val code: String = "av" }


    @Serializable(IetfLanguageCodeSerializer::class)
    object Aymara : IetfLanguageCode { override val code: String = "ay" }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Azerbaijani : IetfLanguageCode {
        override val code: String = "az"


        @Serializable(IetfLanguageCodeSerializer::class)
        sealed class Cyrl : Azerbaijani() {
            override val code: String = "az-Cyrl"


            @Serializable(IetfLanguageCodeSerializer::class)
            object AZ : Cyrl() { override val code: String = "az-Cyrl-AZ" }


            @Serializable(IetfLanguageCodeSerializer::class)
            companion object : Cyrl()
        }


        @Serializable(IetfLanguageCodeSerializer::class)
        sealed class Latn : Azerbaijani() {
            override val code: String = "az-Latn"


            @Serializable(IetfLanguageCodeSerializer::class)
            object AZ : Latn() { override val code: String = "az-Latn-AZ" }


            @Serializable(IetfLanguageCodeSerializer::class)
            companion object : Latn()
        }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Azerbaijani()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    object Bashkir : IetfLanguageCode { override val code: String = "ba" }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Belarusian : IetfLanguageCode {
        override val code: String = "be"


        @Serializable(IetfLanguageCodeSerializer::class)
        object BY : Belarusian() { override val code: String = "be-BY" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Belarusian()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Bulgarian : IetfLanguageCode {
        override val code: String = "bg"


        @Serializable(IetfLanguageCodeSerializer::class)
        object BG : Bulgarian() { override val code: String = "bg-BG" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Bulgarian()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    object BihariLanguages : IetfLanguageCode { override val code: String = "bh" }


    @Serializable(IetfLanguageCodeSerializer::class)
    object Bislama : IetfLanguageCode { override val code: String = "bi" }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Bambara : IetfLanguageCode {
        override val code: String = "bm"


        @Serializable(IetfLanguageCodeSerializer::class)
        object ML : Bambara() { override val code: String = "bm-ML" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Bambara()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Bengali : IetfLanguageCode {
        override val code: String = "bn"


        @Serializable(IetfLanguageCodeSerializer::class)
        object BD : Bengali() { override val code: String = "bn-BD" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object IN : Bengali() { override val code: String = "bn-IN" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Bengali()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Tibetan : IetfLanguageCode {
        override val code: String = "bo"


        @Serializable(IetfLanguageCodeSerializer::class)
        object CN : Tibetan() { override val code: String = "bo-CN" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object IN : Tibetan() { override val code: String = "bo-IN" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Tibetan()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Breton : IetfLanguageCode {
        override val code: String = "br"


        @Serializable(IetfLanguageCodeSerializer::class)
        object FR : Breton() { override val code: String = "br-FR" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Breton()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Bosnian : IetfLanguageCode {
        override val code: String = "bs"


        @Serializable(IetfLanguageCodeSerializer::class)
        sealed class Cyrl : Bosnian() {
            override val code: String = "bs-Cyrl"


            @Serializable(IetfLanguageCodeSerializer::class)
            object BA : Cyrl() { override val code: String = "bs-Cyrl-BA" }


            @Serializable(IetfLanguageCodeSerializer::class)
            companion object : Cyrl()
        }


        @Serializable(IetfLanguageCodeSerializer::class)
        sealed class Latn : Bosnian() {
            override val code: String = "bs-Latn"


            @Serializable(IetfLanguageCodeSerializer::class)
            object BA : Latn() { override val code: String = "bs-Latn-BA" }


            @Serializable(IetfLanguageCodeSerializer::class)
            companion object : Latn()
        }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Bosnian()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class CatalanValencian : IetfLanguageCode {
        override val code: String = "ca"


        @Serializable(IetfLanguageCodeSerializer::class)
        object AD : CatalanValencian() { override val code: String = "ca-AD" }


        @Serializable(IetfLanguageCodeSerializer::class)
        sealed class ES : CatalanValencian() {
            override val code: String = "ca-ES"


            @Serializable(IetfLanguageCodeSerializer::class)
            object VALENCIA : ES() { override val code: String = "ca-ES-VALENCIA" }


            @Serializable(IetfLanguageCodeSerializer::class)
            companion object : ES()
        }


        @Serializable(IetfLanguageCodeSerializer::class)
        object FR : CatalanValencian() { override val code: String = "ca-FR" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object IT : CatalanValencian() { override val code: String = "ca-IT" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : CatalanValencian()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Chechen : IetfLanguageCode {
        override val code: String = "ce"


        @Serializable(IetfLanguageCodeSerializer::class)
        object RU : Chechen() { override val code: String = "ce-RU" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Chechen()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    object Chamorro : IetfLanguageCode { override val code: String = "ch" }


    @Serializable(IetfLanguageCodeSerializer::class)
    object Corsican : IetfLanguageCode { override val code: String = "co" }


    @Serializable(IetfLanguageCodeSerializer::class)
    object Cree : IetfLanguageCode { override val code: String = "cr" }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Czech : IetfLanguageCode {
        override val code: String = "cs"


        @Serializable(IetfLanguageCodeSerializer::class)
        object CZ : Czech() { override val code: String = "cs-CZ" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Czech()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class ChurchSlavicOldSlavonicChurchSlavonicOldBulgarianOldChurchSlavonic : IetfLanguageCode {
        override val code: String = "cu"


        @Serializable(IetfLanguageCodeSerializer::class)
        object RU : ChurchSlavicOldSlavonicChurchSlavonicOldBulgarianOldChurchSlavonic() { override val code: String = "cu-RU" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : ChurchSlavicOldSlavonicChurchSlavonicOldBulgarianOldChurchSlavonic()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    object Chuvash : IetfLanguageCode { override val code: String = "cv" }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Welsh : IetfLanguageCode {
        override val code: String = "cy"


        @Serializable(IetfLanguageCodeSerializer::class)
        object GB : Welsh() { override val code: String = "cy-GB" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Welsh()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Danish : IetfLanguageCode {
        override val code: String = "da"


        @Serializable(IetfLanguageCodeSerializer::class)
        object DK : Danish() { override val code: String = "da-DK" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object GL : Danish() { override val code: String = "da-GL" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Danish()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class German : IetfLanguageCode {
        override val code: String = "de"


        @Serializable(IetfLanguageCodeSerializer::class)
        object AT : German() { override val code: String = "de-AT" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object BE : German() { override val code: String = "de-BE" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object CH : German() { override val code: String = "de-CH" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object DE : German() { override val code: String = "de-DE" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object IT : German() { override val code: String = "de-IT" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object LI : German() { override val code: String = "de-LI" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object LU : German() { override val code: String = "de-LU" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : German()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    object DivehiDhivehiMaldivian : IetfLanguageCode { override val code: String = "dv" }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Dzongkha : IetfLanguageCode {
        override val code: String = "dz"


        @Serializable(IetfLanguageCodeSerializer::class)
        object BT : Dzongkha() { override val code: String = "dz-BT" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Dzongkha()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Ewe : IetfLanguageCode {
        override val code: String = "ee"


        @Serializable(IetfLanguageCodeSerializer::class)
        object GH : Ewe() { override val code: String = "ee-GH" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object TG : Ewe() { override val code: String = "ee-TG" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Ewe()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class GreekModern1453 : IetfLanguageCode {
        override val code: String = "el"


        @Serializable(IetfLanguageCodeSerializer::class)
        object CY : GreekModern1453() { override val code: String = "el-CY" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object GR : GreekModern1453() { override val code: String = "el-GR" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : GreekModern1453()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class English : IetfLanguageCode {
        override val code: String = "en"


        @Serializable(IetfLanguageCodeSerializer::class)
        object L001 : English() { override val code: String = "en-001" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object L150 : English() { override val code: String = "en-150" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object AE : English() { override val code: String = "en-AE" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object AG : English() { override val code: String = "en-AG" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object AI : English() { override val code: String = "en-AI" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object AS : English() { override val code: String = "en-AS" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object AT : English() { override val code: String = "en-AT" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object AU : English() { override val code: String = "en-AU" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object BB : English() { override val code: String = "en-BB" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object BE : English() { override val code: String = "en-BE" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object BI : English() { override val code: String = "en-BI" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object BM : English() { override val code: String = "en-BM" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object BS : English() { override val code: String = "en-BS" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object BW : English() { override val code: String = "en-BW" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object BZ : English() { override val code: String = "en-BZ" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object CA : English() { override val code: String = "en-CA" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object CC : English() { override val code: String = "en-CC" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object CH : English() { override val code: String = "en-CH" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object CK : English() { override val code: String = "en-CK" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object CM : English() { override val code: String = "en-CM" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object CX : English() { override val code: String = "en-CX" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object CY : English() { override val code: String = "en-CY" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object DE : English() { override val code: String = "en-DE" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object DG : English() { override val code: String = "en-DG" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object DK : English() { override val code: String = "en-DK" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object DM : English() { override val code: String = "en-DM" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object ER : English() { override val code: String = "en-ER" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object FI : English() { override val code: String = "en-FI" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object FJ : English() { override val code: String = "en-FJ" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object FK : English() { override val code: String = "en-FK" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object FM : English() { override val code: String = "en-FM" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object GB : English() { override val code: String = "en-GB" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object GD : English() { override val code: String = "en-GD" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object GG : English() { override val code: String = "en-GG" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object GH : English() { override val code: String = "en-GH" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object GI : English() { override val code: String = "en-GI" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object GM : English() { override val code: String = "en-GM" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object GU : English() { override val code: String = "en-GU" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object GY : English() { override val code: String = "en-GY" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object HK : English() { override val code: String = "en-HK" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object IE : English() { override val code: String = "en-IE" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object IL : English() { override val code: String = "en-IL" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object IM : English() { override val code: String = "en-IM" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object IN : English() { override val code: String = "en-IN" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object IO : English() { override val code: String = "en-IO" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object JE : English() { override val code: String = "en-JE" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object JM : English() { override val code: String = "en-JM" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object KE : English() { override val code: String = "en-KE" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object KI : English() { override val code: String = "en-KI" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object KN : English() { override val code: String = "en-KN" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object KY : English() { override val code: String = "en-KY" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object LC : English() { override val code: String = "en-LC" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object LR : English() { override val code: String = "en-LR" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object LS : English() { override val code: String = "en-LS" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object MG : English() { override val code: String = "en-MG" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object MH : English() { override val code: String = "en-MH" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object MO : English() { override val code: String = "en-MO" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object MP : English() { override val code: String = "en-MP" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object MS : English() { override val code: String = "en-MS" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object MT : English() { override val code: String = "en-MT" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object MU : English() { override val code: String = "en-MU" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object MW : English() { override val code: String = "en-MW" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object MY : English() { override val code: String = "en-MY" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object NA : English() { override val code: String = "en-NA" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object NF : English() { override val code: String = "en-NF" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object NG : English() { override val code: String = "en-NG" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object NL : English() { override val code: String = "en-NL" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object NR : English() { override val code: String = "en-NR" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object NU : English() { override val code: String = "en-NU" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object NZ : English() { override val code: String = "en-NZ" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object PG : English() { override val code: String = "en-PG" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object PH : English() { override val code: String = "en-PH" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object PK : English() { override val code: String = "en-PK" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object PN : English() { override val code: String = "en-PN" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object PR : English() { override val code: String = "en-PR" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object PW : English() { override val code: String = "en-PW" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object RW : English() { override val code: String = "en-RW" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object SB : English() { override val code: String = "en-SB" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object SC : English() { override val code: String = "en-SC" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object SD : English() { override val code: String = "en-SD" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object SE : English() { override val code: String = "en-SE" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object SG : English() { override val code: String = "en-SG" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object SH : English() { override val code: String = "en-SH" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object SI : English() { override val code: String = "en-SI" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object SL : English() { override val code: String = "en-SL" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object SS : English() { override val code: String = "en-SS" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object SX : English() { override val code: String = "en-SX" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object SZ : English() { override val code: String = "en-SZ" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object TC : English() { override val code: String = "en-TC" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object TK : English() { override val code: String = "en-TK" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object TO : English() { override val code: String = "en-TO" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object TT : English() { override val code: String = "en-TT" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object TV : English() { override val code: String = "en-TV" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object TZ : English() { override val code: String = "en-TZ" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object UG : English() { override val code: String = "en-UG" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object UM : English() { override val code: String = "en-UM" }


        @Serializable(IetfLanguageCodeSerializer::class)
        sealed class US : English() {
            override val code: String = "en-US"


            @Serializable(IetfLanguageCodeSerializer::class)
            object POSIX : US() { override val code: String = "en-US-POSIX" }


            @Serializable(IetfLanguageCodeSerializer::class)
            companion object : US()
        }


        @Serializable(IetfLanguageCodeSerializer::class)
        object VC : English() { override val code: String = "en-VC" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object VG : English() { override val code: String = "en-VG" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object VI : English() { override val code: String = "en-VI" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object VU : English() { override val code: String = "en-VU" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object WS : English() { override val code: String = "en-WS" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object ZA : English() { override val code: String = "en-ZA" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object ZM : English() { override val code: String = "en-ZM" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object ZW : English() { override val code: String = "en-ZW" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : English()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Esperanto : IetfLanguageCode {
        override val code: String = "eo"


        @Serializable(IetfLanguageCodeSerializer::class)
        object L001 : Esperanto() { override val code: String = "eo-001" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Esperanto()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class SpanishCastilian : IetfLanguageCode {
        override val code: String = "es"


        @Serializable(IetfLanguageCodeSerializer::class)
        object L419 : SpanishCastilian() { override val code: String = "es-419" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object AR : SpanishCastilian() { override val code: String = "es-AR" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object BO : SpanishCastilian() { override val code: String = "es-BO" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object BR : SpanishCastilian() { override val code: String = "es-BR" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object BZ : SpanishCastilian() { override val code: String = "es-BZ" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object CL : SpanishCastilian() { override val code: String = "es-CL" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object CO : SpanishCastilian() { override val code: String = "es-CO" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object CR : SpanishCastilian() { override val code: String = "es-CR" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object CU : SpanishCastilian() { override val code: String = "es-CU" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object DO : SpanishCastilian() { override val code: String = "es-DO" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object EA : SpanishCastilian() { override val code: String = "es-EA" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object EC : SpanishCastilian() { override val code: String = "es-EC" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object ES : SpanishCastilian() { override val code: String = "es-ES" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object GQ : SpanishCastilian() { override val code: String = "es-GQ" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object GT : SpanishCastilian() { override val code: String = "es-GT" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object HN : SpanishCastilian() { override val code: String = "es-HN" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object IC : SpanishCastilian() { override val code: String = "es-IC" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object MX : SpanishCastilian() { override val code: String = "es-MX" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object NI : SpanishCastilian() { override val code: String = "es-NI" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object PA : SpanishCastilian() { override val code: String = "es-PA" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object PE : SpanishCastilian() { override val code: String = "es-PE" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object PH : SpanishCastilian() { override val code: String = "es-PH" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object PR : SpanishCastilian() { override val code: String = "es-PR" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object PY : SpanishCastilian() { override val code: String = "es-PY" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object SV : SpanishCastilian() { override val code: String = "es-SV" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object US : SpanishCastilian() { override val code: String = "es-US" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object UY : SpanishCastilian() { override val code: String = "es-UY" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object VE : SpanishCastilian() { override val code: String = "es-VE" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : SpanishCastilian()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Estonian : IetfLanguageCode {
        override val code: String = "et"


        @Serializable(IetfLanguageCodeSerializer::class)
        object EE : Estonian() { override val code: String = "et-EE" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Estonian()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Basque : IetfLanguageCode {
        override val code: String = "eu"


        @Serializable(IetfLanguageCodeSerializer::class)
        object ES : Basque() { override val code: String = "eu-ES" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Basque()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Persian : IetfLanguageCode {
        override val code: String = "fa"


        @Serializable(IetfLanguageCodeSerializer::class)
        object AF : Persian() { override val code: String = "fa-AF" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object IR : Persian() { override val code: String = "fa-IR" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Persian()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Fulah : IetfLanguageCode {
        override val code: String = "ff"


        @Serializable(IetfLanguageCodeSerializer::class)
        sealed class Adlm : Fulah() {
            override val code: String = "ff-Adlm"


            @Serializable(IetfLanguageCodeSerializer::class)
            object BF : Adlm() { override val code: String = "ff-Adlm-BF" }


            @Serializable(IetfLanguageCodeSerializer::class)
            object CM : Adlm() { override val code: String = "ff-Adlm-CM" }


            @Serializable(IetfLanguageCodeSerializer::class)
            object GH : Adlm() { override val code: String = "ff-Adlm-GH" }


            @Serializable(IetfLanguageCodeSerializer::class)
            object GM : Adlm() { override val code: String = "ff-Adlm-GM" }


            @Serializable(IetfLanguageCodeSerializer::class)
            object GN : Adlm() { override val code: String = "ff-Adlm-GN" }


            @Serializable(IetfLanguageCodeSerializer::class)
            object GW : Adlm() { override val code: String = "ff-Adlm-GW" }


            @Serializable(IetfLanguageCodeSerializer::class)
            object LR : Adlm() { override val code: String = "ff-Adlm-LR" }


            @Serializable(IetfLanguageCodeSerializer::class)
            object MR : Adlm() { override val code: String = "ff-Adlm-MR" }


            @Serializable(IetfLanguageCodeSerializer::class)
            object NE : Adlm() { override val code: String = "ff-Adlm-NE" }


            @Serializable(IetfLanguageCodeSerializer::class)
            object NG : Adlm() { override val code: String = "ff-Adlm-NG" }


            @Serializable(IetfLanguageCodeSerializer::class)
            object SL : Adlm() { override val code: String = "ff-Adlm-SL" }


            @Serializable(IetfLanguageCodeSerializer::class)
            object SN : Adlm() { override val code: String = "ff-Adlm-SN" }


            @Serializable(IetfLanguageCodeSerializer::class)
            companion object : Adlm()
        }


        @Serializable(IetfLanguageCodeSerializer::class)
        sealed class Latn : Fulah() {
            override val code: String = "ff-Latn"


            @Serializable(IetfLanguageCodeSerializer::class)
            object BF : Latn() { override val code: String = "ff-Latn-BF" }


            @Serializable(IetfLanguageCodeSerializer::class)
            object CM : Latn() { override val code: String = "ff-Latn-CM" }


            @Serializable(IetfLanguageCodeSerializer::class)
            object GH : Latn() { override val code: String = "ff-Latn-GH" }


            @Serializable(IetfLanguageCodeSerializer::class)
            object GM : Latn() { override val code: String = "ff-Latn-GM" }


            @Serializable(IetfLanguageCodeSerializer::class)
            object GN : Latn() { override val code: String = "ff-Latn-GN" }


            @Serializable(IetfLanguageCodeSerializer::class)
            object GW : Latn() { override val code: String = "ff-Latn-GW" }


            @Serializable(IetfLanguageCodeSerializer::class)
            object LR : Latn() { override val code: String = "ff-Latn-LR" }


            @Serializable(IetfLanguageCodeSerializer::class)
            object MR : Latn() { override val code: String = "ff-Latn-MR" }


            @Serializable(IetfLanguageCodeSerializer::class)
            object NE : Latn() { override val code: String = "ff-Latn-NE" }


            @Serializable(IetfLanguageCodeSerializer::class)
            object NG : Latn() { override val code: String = "ff-Latn-NG" }


            @Serializable(IetfLanguageCodeSerializer::class)
            object SL : Latn() { override val code: String = "ff-Latn-SL" }


            @Serializable(IetfLanguageCodeSerializer::class)
            object SN : Latn() { override val code: String = "ff-Latn-SN" }


            @Serializable(IetfLanguageCodeSerializer::class)
            companion object : Latn()
        }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Fulah()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Finnish : IetfLanguageCode {
        override val code: String = "fi"


        @Serializable(IetfLanguageCodeSerializer::class)
        object FI : Finnish() { override val code: String = "fi-FI" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Finnish()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    object Fijian : IetfLanguageCode { override val code: String = "fj" }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Faroese : IetfLanguageCode {
        override val code: String = "fo"


        @Serializable(IetfLanguageCodeSerializer::class)
        object DK : Faroese() { override val code: String = "fo-DK" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object FO : Faroese() { override val code: String = "fo-FO" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Faroese()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class French : IetfLanguageCode {
        override val code: String = "fr"


        @Serializable(IetfLanguageCodeSerializer::class)
        object BE : French() { override val code: String = "fr-BE" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object BF : French() { override val code: String = "fr-BF" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object BI : French() { override val code: String = "fr-BI" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object BJ : French() { override val code: String = "fr-BJ" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object BL : French() { override val code: String = "fr-BL" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object CA : French() { override val code: String = "fr-CA" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object CD : French() { override val code: String = "fr-CD" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object CF : French() { override val code: String = "fr-CF" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object CG : French() { override val code: String = "fr-CG" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object CH : French() { override val code: String = "fr-CH" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object CI : French() { override val code: String = "fr-CI" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object CM : French() { override val code: String = "fr-CM" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object DJ : French() { override val code: String = "fr-DJ" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object DZ : French() { override val code: String = "fr-DZ" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object FR : French() { override val code: String = "fr-FR" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object GA : French() { override val code: String = "fr-GA" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object GF : French() { override val code: String = "fr-GF" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object GN : French() { override val code: String = "fr-GN" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object GP : French() { override val code: String = "fr-GP" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object GQ : French() { override val code: String = "fr-GQ" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object HT : French() { override val code: String = "fr-HT" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object KM : French() { override val code: String = "fr-KM" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object LU : French() { override val code: String = "fr-LU" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object MA : French() { override val code: String = "fr-MA" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object MC : French() { override val code: String = "fr-MC" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object MF : French() { override val code: String = "fr-MF" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object MG : French() { override val code: String = "fr-MG" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object ML : French() { override val code: String = "fr-ML" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object MQ : French() { override val code: String = "fr-MQ" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object MR : French() { override val code: String = "fr-MR" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object MU : French() { override val code: String = "fr-MU" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object NC : French() { override val code: String = "fr-NC" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object NE : French() { override val code: String = "fr-NE" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object PF : French() { override val code: String = "fr-PF" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object PM : French() { override val code: String = "fr-PM" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object RE : French() { override val code: String = "fr-RE" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object RW : French() { override val code: String = "fr-RW" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object SC : French() { override val code: String = "fr-SC" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object SN : French() { override val code: String = "fr-SN" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object SY : French() { override val code: String = "fr-SY" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object TD : French() { override val code: String = "fr-TD" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object TG : French() { override val code: String = "fr-TG" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object TN : French() { override val code: String = "fr-TN" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object VU : French() { override val code: String = "fr-VU" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object WF : French() { override val code: String = "fr-WF" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object YT : French() { override val code: String = "fr-YT" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : French()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class WesternFrisian : IetfLanguageCode {
        override val code: String = "fy"


        @Serializable(IetfLanguageCodeSerializer::class)
        object NL : WesternFrisian() { override val code: String = "fy-NL" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : WesternFrisian()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Irish : IetfLanguageCode {
        override val code: String = "ga"


        @Serializable(IetfLanguageCodeSerializer::class)
        object GB : Irish() { override val code: String = "ga-GB" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object IE : Irish() { override val code: String = "ga-IE" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Irish()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class GaelicScottishGaelic : IetfLanguageCode {
        override val code: String = "gd"


        @Serializable(IetfLanguageCodeSerializer::class)
        object GB : GaelicScottishGaelic() { override val code: String = "gd-GB" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : GaelicScottishGaelic()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Galician : IetfLanguageCode {
        override val code: String = "gl"


        @Serializable(IetfLanguageCodeSerializer::class)
        object ES : Galician() { override val code: String = "gl-ES" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Galician()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    object Guarani : IetfLanguageCode { override val code: String = "gn" }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Gujarati : IetfLanguageCode {
        override val code: String = "gu"


        @Serializable(IetfLanguageCodeSerializer::class)
        object IN : Gujarati() { override val code: String = "gu-IN" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Gujarati()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Manx : IetfLanguageCode {
        override val code: String = "gv"


        @Serializable(IetfLanguageCodeSerializer::class)
        object IM : Manx() { override val code: String = "gv-IM" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Manx()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Hausa : IetfLanguageCode {
        override val code: String = "ha"


        @Serializable(IetfLanguageCodeSerializer::class)
        object GH : Hausa() { override val code: String = "ha-GH" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object NE : Hausa() { override val code: String = "ha-NE" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object NG : Hausa() { override val code: String = "ha-NG" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Hausa()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Hebrew : IetfLanguageCode {
        override val code: String = "he"


        @Serializable(IetfLanguageCodeSerializer::class)
        object IL : Hebrew() { override val code: String = "he-IL" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Hebrew()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Hindi : IetfLanguageCode {
        override val code: String = "hi"


        @Serializable(IetfLanguageCodeSerializer::class)
        object IN : Hindi() { override val code: String = "hi-IN" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Hindi()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    object HiriMotu : IetfLanguageCode { override val code: String = "ho" }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Croatian : IetfLanguageCode {
        override val code: String = "hr"


        @Serializable(IetfLanguageCodeSerializer::class)
        object BA : Croatian() { override val code: String = "hr-BA" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object HR : Croatian() { override val code: String = "hr-HR" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Croatian()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    object HaitianHaitianCreole : IetfLanguageCode { override val code: String = "ht" }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Hungarian : IetfLanguageCode {
        override val code: String = "hu"


        @Serializable(IetfLanguageCodeSerializer::class)
        object HU : Hungarian() { override val code: String = "hu-HU" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Hungarian()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Armenian : IetfLanguageCode {
        override val code: String = "hy"


        @Serializable(IetfLanguageCodeSerializer::class)
        object AM : Armenian() { override val code: String = "hy-AM" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Armenian()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    object Herero : IetfLanguageCode { override val code: String = "hz" }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class InterlinguaInternationalAuxiliaryLanguageAssociation : IetfLanguageCode {
        override val code: String = "ia"


        @Serializable(IetfLanguageCodeSerializer::class)
        object L001 : InterlinguaInternationalAuxiliaryLanguageAssociation() { override val code: String = "ia-001" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : InterlinguaInternationalAuxiliaryLanguageAssociation()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Indonesian : IetfLanguageCode {
        override val code: String = "id"


        @Serializable(IetfLanguageCodeSerializer::class)
        object ID : Indonesian() { override val code: String = "id-ID" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Indonesian()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    object InterlingueOccidental : IetfLanguageCode { override val code: String = "ie" }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Igbo : IetfLanguageCode {
        override val code: String = "ig"


        @Serializable(IetfLanguageCodeSerializer::class)
        object NG : Igbo() { override val code: String = "ig-NG" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Igbo()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class SichuanYiNuosu : IetfLanguageCode {
        override val code: String = "ii"


        @Serializable(IetfLanguageCodeSerializer::class)
        object CN : SichuanYiNuosu() { override val code: String = "ii-CN" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : SichuanYiNuosu()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    object Inupiaq : IetfLanguageCode { override val code: String = "ik" }


    @Serializable(IetfLanguageCodeSerializer::class)
    object Ido : IetfLanguageCode { override val code: String = "io" }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Icelandic : IetfLanguageCode {
        override val code: String = "is"


        @Serializable(IetfLanguageCodeSerializer::class)
        object IS : Icelandic() { override val code: String = "is-IS" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Icelandic()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Italian : IetfLanguageCode {
        override val code: String = "it"


        @Serializable(IetfLanguageCodeSerializer::class)
        object CH : Italian() { override val code: String = "it-CH" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object IT : Italian() { override val code: String = "it-IT" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object SM : Italian() { override val code: String = "it-SM" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object VA : Italian() { override val code: String = "it-VA" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Italian()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    object Inuktitut : IetfLanguageCode { override val code: String = "iu" }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Japanese : IetfLanguageCode {
        override val code: String = "ja"


        @Serializable(IetfLanguageCodeSerializer::class)
        object JP : Japanese() { override val code: String = "ja-JP" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Japanese()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Javanese : IetfLanguageCode {
        override val code: String = "jv"


        @Serializable(IetfLanguageCodeSerializer::class)
        object ID : Javanese() { override val code: String = "jv-ID" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Javanese()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Georgian : IetfLanguageCode {
        override val code: String = "ka"


        @Serializable(IetfLanguageCodeSerializer::class)
        object GE : Georgian() { override val code: String = "ka-GE" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Georgian()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    object Kongo : IetfLanguageCode { override val code: String = "kg" }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class KikuyuGikuyu : IetfLanguageCode {
        override val code: String = "ki"


        @Serializable(IetfLanguageCodeSerializer::class)
        object KE : KikuyuGikuyu() { override val code: String = "ki-KE" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : KikuyuGikuyu()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    object KuanyamaKwanyama : IetfLanguageCode { override val code: String = "kj" }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Kazakh : IetfLanguageCode {
        override val code: String = "kk"


        @Serializable(IetfLanguageCodeSerializer::class)
        object KZ : Kazakh() { override val code: String = "kk-KZ" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Kazakh()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class KalaallisutGreenlandic : IetfLanguageCode {
        override val code: String = "kl"


        @Serializable(IetfLanguageCodeSerializer::class)
        object GL : KalaallisutGreenlandic() { override val code: String = "kl-GL" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : KalaallisutGreenlandic()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class CentralKhmer : IetfLanguageCode {
        override val code: String = "km"


        @Serializable(IetfLanguageCodeSerializer::class)
        object KH : CentralKhmer() { override val code: String = "km-KH" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : CentralKhmer()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Kannada : IetfLanguageCode {
        override val code: String = "kn"


        @Serializable(IetfLanguageCodeSerializer::class)
        object IN : Kannada() { override val code: String = "kn-IN" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Kannada()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Korean : IetfLanguageCode {
        override val code: String = "ko"


        @Serializable(IetfLanguageCodeSerializer::class)
        object KP : Korean() { override val code: String = "ko-KP" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object KR : Korean() { override val code: String = "ko-KR" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Korean()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    object Kanuri : IetfLanguageCode { override val code: String = "kr" }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Kashmiri : IetfLanguageCode {
        override val code: String = "ks"


        @Serializable(IetfLanguageCodeSerializer::class)
        sealed class Arab : Kashmiri() {
            override val code: String = "ks-Arab"


            @Serializable(IetfLanguageCodeSerializer::class)
            object IN : Arab() { override val code: String = "ks-Arab-IN" }


            @Serializable(IetfLanguageCodeSerializer::class)
            companion object : Arab()
        }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Kashmiri()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Kurdish : IetfLanguageCode {
        override val code: String = "ku"


        @Serializable(IetfLanguageCodeSerializer::class)
        object TR : Kurdish() { override val code: String = "ku-TR" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Kurdish()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    object Komi : IetfLanguageCode { override val code: String = "kv" }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Cornish : IetfLanguageCode {
        override val code: String = "kw"


        @Serializable(IetfLanguageCodeSerializer::class)
        object GB : Cornish() { override val code: String = "kw-GB" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Cornish()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class KirghizKyrgyz : IetfLanguageCode {
        override val code: String = "ky"


        @Serializable(IetfLanguageCodeSerializer::class)
        object KG : KirghizKyrgyz() { override val code: String = "ky-KG" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : KirghizKyrgyz()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    object Latin : IetfLanguageCode { override val code: String = "la" }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class LuxembourgishLetzeburgesch : IetfLanguageCode {
        override val code: String = "lb"


        @Serializable(IetfLanguageCodeSerializer::class)
        object LU : LuxembourgishLetzeburgesch() { override val code: String = "lb-LU" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : LuxembourgishLetzeburgesch()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Ganda : IetfLanguageCode {
        override val code: String = "lg"


        @Serializable(IetfLanguageCodeSerializer::class)
        object UG : Ganda() { override val code: String = "lg-UG" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Ganda()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    object LimburganLimburgerLimburgish : IetfLanguageCode { override val code: String = "li" }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Lingala : IetfLanguageCode {
        override val code: String = "ln"


        @Serializable(IetfLanguageCodeSerializer::class)
        object AO : Lingala() { override val code: String = "ln-AO" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object CD : Lingala() { override val code: String = "ln-CD" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object CF : Lingala() { override val code: String = "ln-CF" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object CG : Lingala() { override val code: String = "ln-CG" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Lingala()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Lao : IetfLanguageCode {
        override val code: String = "lo"


        @Serializable(IetfLanguageCodeSerializer::class)
        object LA : Lao() { override val code: String = "lo-LA" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Lao()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Lithuanian : IetfLanguageCode {
        override val code: String = "lt"


        @Serializable(IetfLanguageCodeSerializer::class)
        object LT : Lithuanian() { override val code: String = "lt-LT" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Lithuanian()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class LubaKatanga : IetfLanguageCode {
        override val code: String = "lu"


        @Serializable(IetfLanguageCodeSerializer::class)
        object CD : LubaKatanga() { override val code: String = "lu-CD" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : LubaKatanga()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Latvian : IetfLanguageCode {
        override val code: String = "lv"


        @Serializable(IetfLanguageCodeSerializer::class)
        object LV : Latvian() { override val code: String = "lv-LV" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Latvian()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Malagasy : IetfLanguageCode {
        override val code: String = "mg"


        @Serializable(IetfLanguageCodeSerializer::class)
        object MG : Malagasy() { override val code: String = "mg-MG" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Malagasy()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    object Marshallese : IetfLanguageCode { override val code: String = "mh" }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Maori : IetfLanguageCode {
        override val code: String = "mi"


        @Serializable(IetfLanguageCodeSerializer::class)
        object NZ : Maori() { override val code: String = "mi-NZ" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Maori()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Macedonian : IetfLanguageCode {
        override val code: String = "mk"


        @Serializable(IetfLanguageCodeSerializer::class)
        object MK : Macedonian() { override val code: String = "mk-MK" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Macedonian()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Malayalam : IetfLanguageCode {
        override val code: String = "ml"


        @Serializable(IetfLanguageCodeSerializer::class)
        object IN : Malayalam() { override val code: String = "ml-IN" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Malayalam()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Mongolian : IetfLanguageCode {
        override val code: String = "mn"


        @Serializable(IetfLanguageCodeSerializer::class)
        object MN : Mongolian() { override val code: String = "mn-MN" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Mongolian()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Marathi : IetfLanguageCode {
        override val code: String = "mr"


        @Serializable(IetfLanguageCodeSerializer::class)
        object IN : Marathi() { override val code: String = "mr-IN" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Marathi()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Malay : IetfLanguageCode {
        override val code: String = "ms"


        @Serializable(IetfLanguageCodeSerializer::class)
        object BN : Malay() { override val code: String = "ms-BN" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object ID : Malay() { override val code: String = "ms-ID" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object MY : Malay() { override val code: String = "ms-MY" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object SG : Malay() { override val code: String = "ms-SG" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Malay()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Maltese : IetfLanguageCode {
        override val code: String = "mt"


        @Serializable(IetfLanguageCodeSerializer::class)
        object MT : Maltese() { override val code: String = "mt-MT" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Maltese()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Burmese : IetfLanguageCode {
        override val code: String = "my"


        @Serializable(IetfLanguageCodeSerializer::class)
        object MM : Burmese() { override val code: String = "my-MM" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Burmese()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    object Nauru : IetfLanguageCode { override val code: String = "na" }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class BokmålNorwegianNorwegianBokmål : IetfLanguageCode {
        override val code: String = "nb"


        @Serializable(IetfLanguageCodeSerializer::class)
        object NO : BokmålNorwegianNorwegianBokmål() { override val code: String = "nb-NO" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object SJ : BokmålNorwegianNorwegianBokmål() { override val code: String = "nb-SJ" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : BokmålNorwegianNorwegianBokmål()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class NdebeleNorthNorthNdebele : IetfLanguageCode {
        override val code: String = "nd"


        @Serializable(IetfLanguageCodeSerializer::class)
        object ZW : NdebeleNorthNorthNdebele() { override val code: String = "nd-ZW" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : NdebeleNorthNorthNdebele()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Nepali : IetfLanguageCode {
        override val code: String = "ne"


        @Serializable(IetfLanguageCodeSerializer::class)
        object IN : Nepali() { override val code: String = "ne-IN" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object NP : Nepali() { override val code: String = "ne-NP" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Nepali()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    object Ndonga : IetfLanguageCode { override val code: String = "ng" }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class DutchFlemish : IetfLanguageCode {
        override val code: String = "nl"


        @Serializable(IetfLanguageCodeSerializer::class)
        object AW : DutchFlemish() { override val code: String = "nl-AW" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object BE : DutchFlemish() { override val code: String = "nl-BE" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object BQ : DutchFlemish() { override val code: String = "nl-BQ" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object CW : DutchFlemish() { override val code: String = "nl-CW" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object NL : DutchFlemish() { override val code: String = "nl-NL" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object SR : DutchFlemish() { override val code: String = "nl-SR" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object SX : DutchFlemish() { override val code: String = "nl-SX" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : DutchFlemish()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class NorwegianNynorskNynorskNorwegian : IetfLanguageCode {
        override val code: String = "nn"


        @Serializable(IetfLanguageCodeSerializer::class)
        object NO : NorwegianNynorskNynorskNorwegian() { override val code: String = "nn-NO" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : NorwegianNynorskNynorskNorwegian()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    object Norwegian : IetfLanguageCode { override val code: String = "no" }


    @Serializable(IetfLanguageCodeSerializer::class)
    object NdebeleSouthSouthNdebele : IetfLanguageCode { override val code: String = "nr" }


    @Serializable(IetfLanguageCodeSerializer::class)
    object NavajoNavaho : IetfLanguageCode { override val code: String = "nv" }


    @Serializable(IetfLanguageCodeSerializer::class)
    object ChichewaChewaNyanja : IetfLanguageCode { override val code: String = "ny" }


    @Serializable(IetfLanguageCodeSerializer::class)
    object OccitanPost1500 : IetfLanguageCode { override val code: String = "oc" }


    @Serializable(IetfLanguageCodeSerializer::class)
    object Ojibwa : IetfLanguageCode { override val code: String = "oj" }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Oromo : IetfLanguageCode {
        override val code: String = "om"


        @Serializable(IetfLanguageCodeSerializer::class)
        object ET : Oromo() { override val code: String = "om-ET" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object KE : Oromo() { override val code: String = "om-KE" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Oromo()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Oriya : IetfLanguageCode {
        override val code: String = "or"


        @Serializable(IetfLanguageCodeSerializer::class)
        object IN : Oriya() { override val code: String = "or-IN" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Oriya()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class OssetianOssetic : IetfLanguageCode {
        override val code: String = "os"


        @Serializable(IetfLanguageCodeSerializer::class)
        object GE : OssetianOssetic() { override val code: String = "os-GE" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object RU : OssetianOssetic() { override val code: String = "os-RU" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : OssetianOssetic()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class PanjabiPunjabi : IetfLanguageCode {
        override val code: String = "pa"


        @Serializable(IetfLanguageCodeSerializer::class)
        sealed class Arab : PanjabiPunjabi() {
            override val code: String = "pa-Arab"


            @Serializable(IetfLanguageCodeSerializer::class)
            object PK : Arab() { override val code: String = "pa-Arab-PK" }


            @Serializable(IetfLanguageCodeSerializer::class)
            companion object : Arab()
        }


        @Serializable(IetfLanguageCodeSerializer::class)
        sealed class Guru : PanjabiPunjabi() {
            override val code: String = "pa-Guru"


            @Serializable(IetfLanguageCodeSerializer::class)
            object IN : Guru() { override val code: String = "pa-Guru-IN" }


            @Serializable(IetfLanguageCodeSerializer::class)
            companion object : Guru()
        }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : PanjabiPunjabi()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    object Pali : IetfLanguageCode { override val code: String = "pi" }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Polish : IetfLanguageCode {
        override val code: String = "pl"


        @Serializable(IetfLanguageCodeSerializer::class)
        object PL : Polish() { override val code: String = "pl-PL" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Polish()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class PushtoPashto : IetfLanguageCode {
        override val code: String = "ps"


        @Serializable(IetfLanguageCodeSerializer::class)
        object AF : PushtoPashto() { override val code: String = "ps-AF" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object PK : PushtoPashto() { override val code: String = "ps-PK" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : PushtoPashto()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Portuguese : IetfLanguageCode {
        override val code: String = "pt"


        @Serializable(IetfLanguageCodeSerializer::class)
        object AO : Portuguese() { override val code: String = "pt-AO" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object BR : Portuguese() { override val code: String = "pt-BR" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object CH : Portuguese() { override val code: String = "pt-CH" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object CV : Portuguese() { override val code: String = "pt-CV" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object GQ : Portuguese() { override val code: String = "pt-GQ" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object GW : Portuguese() { override val code: String = "pt-GW" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object LU : Portuguese() { override val code: String = "pt-LU" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object MO : Portuguese() { override val code: String = "pt-MO" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object MZ : Portuguese() { override val code: String = "pt-MZ" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object PT : Portuguese() { override val code: String = "pt-PT" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object ST : Portuguese() { override val code: String = "pt-ST" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object TL : Portuguese() { override val code: String = "pt-TL" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Portuguese()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Quechua : IetfLanguageCode {
        override val code: String = "qu"


        @Serializable(IetfLanguageCodeSerializer::class)
        object BO : Quechua() { override val code: String = "qu-BO" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object EC : Quechua() { override val code: String = "qu-EC" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object PE : Quechua() { override val code: String = "qu-PE" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Quechua()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Romansh : IetfLanguageCode {
        override val code: String = "rm"


        @Serializable(IetfLanguageCodeSerializer::class)
        object CH : Romansh() { override val code: String = "rm-CH" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Romansh()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Rundi : IetfLanguageCode {
        override val code: String = "rn"


        @Serializable(IetfLanguageCodeSerializer::class)
        object BI : Rundi() { override val code: String = "rn-BI" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Rundi()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class RomanianMoldavianMoldovan : IetfLanguageCode {
        override val code: String = "ro"


        @Serializable(IetfLanguageCodeSerializer::class)
        object MD : RomanianMoldavianMoldovan() { override val code: String = "ro-MD" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object RO : RomanianMoldavianMoldovan() { override val code: String = "ro-RO" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : RomanianMoldavianMoldovan()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Russian : IetfLanguageCode {
        override val code: String = "ru"


        @Serializable(IetfLanguageCodeSerializer::class)
        object BY : Russian() { override val code: String = "ru-BY" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object KG : Russian() { override val code: String = "ru-KG" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object KZ : Russian() { override val code: String = "ru-KZ" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object MD : Russian() { override val code: String = "ru-MD" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object RU : Russian() { override val code: String = "ru-RU" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object UA : Russian() { override val code: String = "ru-UA" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Russian()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Kinyarwanda : IetfLanguageCode {
        override val code: String = "rw"


        @Serializable(IetfLanguageCodeSerializer::class)
        object RW : Kinyarwanda() { override val code: String = "rw-RW" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Kinyarwanda()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    object Sanskrit : IetfLanguageCode { override val code: String = "sa" }


    @Serializable(IetfLanguageCodeSerializer::class)
    object Sardinian : IetfLanguageCode { override val code: String = "sc" }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Sindhi : IetfLanguageCode {
        override val code: String = "sd"


        @Serializable(IetfLanguageCodeSerializer::class)
        sealed class Arab : Sindhi() {
            override val code: String = "sd-Arab"


            @Serializable(IetfLanguageCodeSerializer::class)
            object PK : Arab() { override val code: String = "sd-Arab-PK" }


            @Serializable(IetfLanguageCodeSerializer::class)
            companion object : Arab()
        }


        @Serializable(IetfLanguageCodeSerializer::class)
        sealed class Deva : Sindhi() {
            override val code: String = "sd-Deva"


            @Serializable(IetfLanguageCodeSerializer::class)
            object IN : Deva() { override val code: String = "sd-Deva-IN" }


            @Serializable(IetfLanguageCodeSerializer::class)
            companion object : Deva()
        }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Sindhi()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class NorthernSami : IetfLanguageCode {
        override val code: String = "se"


        @Serializable(IetfLanguageCodeSerializer::class)
        object FI : NorthernSami() { override val code: String = "se-FI" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object NO : NorthernSami() { override val code: String = "se-NO" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object SE : NorthernSami() { override val code: String = "se-SE" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : NorthernSami()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Sango : IetfLanguageCode {
        override val code: String = "sg"


        @Serializable(IetfLanguageCodeSerializer::class)
        object CF : Sango() { override val code: String = "sg-CF" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Sango()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class SinhalaSinhalese : IetfLanguageCode {
        override val code: String = "si"


        @Serializable(IetfLanguageCodeSerializer::class)
        object LK : SinhalaSinhalese() { override val code: String = "si-LK" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : SinhalaSinhalese()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Slovak : IetfLanguageCode {
        override val code: String = "sk"


        @Serializable(IetfLanguageCodeSerializer::class)
        object SK : Slovak() { override val code: String = "sk-SK" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Slovak()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Slovenian : IetfLanguageCode {
        override val code: String = "sl"


        @Serializable(IetfLanguageCodeSerializer::class)
        object SI : Slovenian() { override val code: String = "sl-SI" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Slovenian()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    object Samoan : IetfLanguageCode { override val code: String = "sm" }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Shona : IetfLanguageCode {
        override val code: String = "sn"


        @Serializable(IetfLanguageCodeSerializer::class)
        object ZW : Shona() { override val code: String = "sn-ZW" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Shona()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Somali : IetfLanguageCode {
        override val code: String = "so"


        @Serializable(IetfLanguageCodeSerializer::class)
        object DJ : Somali() { override val code: String = "so-DJ" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object ET : Somali() { override val code: String = "so-ET" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object KE : Somali() { override val code: String = "so-KE" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object SO : Somali() { override val code: String = "so-SO" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Somali()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Albanian : IetfLanguageCode {
        override val code: String = "sq"


        @Serializable(IetfLanguageCodeSerializer::class)
        object AL : Albanian() { override val code: String = "sq-AL" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object MK : Albanian() { override val code: String = "sq-MK" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object XK : Albanian() { override val code: String = "sq-XK" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Albanian()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Serbian : IetfLanguageCode {
        override val code: String = "sr"


        @Serializable(IetfLanguageCodeSerializer::class)
        sealed class Cyrl : Serbian() {
            override val code: String = "sr-Cyrl"


            @Serializable(IetfLanguageCodeSerializer::class)
            object BA : Cyrl() { override val code: String = "sr-Cyrl-BA" }


            @Serializable(IetfLanguageCodeSerializer::class)
            object ME : Cyrl() { override val code: String = "sr-Cyrl-ME" }


            @Serializable(IetfLanguageCodeSerializer::class)
            object RS : Cyrl() { override val code: String = "sr-Cyrl-RS" }


            @Serializable(IetfLanguageCodeSerializer::class)
            object XK : Cyrl() { override val code: String = "sr-Cyrl-XK" }


            @Serializable(IetfLanguageCodeSerializer::class)
            companion object : Cyrl()
        }


        @Serializable(IetfLanguageCodeSerializer::class)
        sealed class Latn : Serbian() {
            override val code: String = "sr-Latn"


            @Serializable(IetfLanguageCodeSerializer::class)
            object BA : Latn() { override val code: String = "sr-Latn-BA" }


            @Serializable(IetfLanguageCodeSerializer::class)
            object ME : Latn() { override val code: String = "sr-Latn-ME" }


            @Serializable(IetfLanguageCodeSerializer::class)
            object RS : Latn() { override val code: String = "sr-Latn-RS" }


            @Serializable(IetfLanguageCodeSerializer::class)
            object XK : Latn() { override val code: String = "sr-Latn-XK" }


            @Serializable(IetfLanguageCodeSerializer::class)
            companion object : Latn()
        }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Serbian()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    object Swati : IetfLanguageCode { override val code: String = "ss" }


    @Serializable(IetfLanguageCodeSerializer::class)
    object SothoSouthern : IetfLanguageCode { override val code: String = "st" }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Sundanese : IetfLanguageCode {
        override val code: String = "su"


        @Serializable(IetfLanguageCodeSerializer::class)
        sealed class Latn : Sundanese() {
            override val code: String = "su-Latn"


            @Serializable(IetfLanguageCodeSerializer::class)
            object ID : Latn() { override val code: String = "su-Latn-ID" }


            @Serializable(IetfLanguageCodeSerializer::class)
            companion object : Latn()
        }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Sundanese()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Swedish : IetfLanguageCode {
        override val code: String = "sv"


        @Serializable(IetfLanguageCodeSerializer::class)
        object AX : Swedish() { override val code: String = "sv-AX" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object FI : Swedish() { override val code: String = "sv-FI" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object SE : Swedish() { override val code: String = "sv-SE" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Swedish()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Swahili : IetfLanguageCode {
        override val code: String = "sw"


        @Serializable(IetfLanguageCodeSerializer::class)
        object CD : Swahili() { override val code: String = "sw-CD" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object KE : Swahili() { override val code: String = "sw-KE" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object TZ : Swahili() { override val code: String = "sw-TZ" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object UG : Swahili() { override val code: String = "sw-UG" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Swahili()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Tamil : IetfLanguageCode {
        override val code: String = "ta"


        @Serializable(IetfLanguageCodeSerializer::class)
        object IN : Tamil() { override val code: String = "ta-IN" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object LK : Tamil() { override val code: String = "ta-LK" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object MY : Tamil() { override val code: String = "ta-MY" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object SG : Tamil() { override val code: String = "ta-SG" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Tamil()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Telugu : IetfLanguageCode {
        override val code: String = "te"


        @Serializable(IetfLanguageCodeSerializer::class)
        object IN : Telugu() { override val code: String = "te-IN" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Telugu()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Tajik : IetfLanguageCode {
        override val code: String = "tg"


        @Serializable(IetfLanguageCodeSerializer::class)
        object TJ : Tajik() { override val code: String = "tg-TJ" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Tajik()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Thai : IetfLanguageCode {
        override val code: String = "th"


        @Serializable(IetfLanguageCodeSerializer::class)
        object TH : Thai() { override val code: String = "th-TH" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Thai()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Tigrinya : IetfLanguageCode {
        override val code: String = "ti"


        @Serializable(IetfLanguageCodeSerializer::class)
        object ER : Tigrinya() { override val code: String = "ti-ER" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object ET : Tigrinya() { override val code: String = "ti-ET" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Tigrinya()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Turkmen : IetfLanguageCode {
        override val code: String = "tk"


        @Serializable(IetfLanguageCodeSerializer::class)
        object TM : Turkmen() { override val code: String = "tk-TM" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Turkmen()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    object Tagalog : IetfLanguageCode { override val code: String = "tl" }


    @Serializable(IetfLanguageCodeSerializer::class)
    object Tswana : IetfLanguageCode { override val code: String = "tn" }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class TongaTongaIslands : IetfLanguageCode {
        override val code: String = "to"


        @Serializable(IetfLanguageCodeSerializer::class)
        object TO : TongaTongaIslands() { override val code: String = "to-TO" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : TongaTongaIslands()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Turkish : IetfLanguageCode {
        override val code: String = "tr"


        @Serializable(IetfLanguageCodeSerializer::class)
        object CY : Turkish() { override val code: String = "tr-CY" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object TR : Turkish() { override val code: String = "tr-TR" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Turkish()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    object Tsonga : IetfLanguageCode { override val code: String = "ts" }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Tatar : IetfLanguageCode {
        override val code: String = "tt"


        @Serializable(IetfLanguageCodeSerializer::class)
        object RU : Tatar() { override val code: String = "tt-RU" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Tatar()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    object Twi : IetfLanguageCode { override val code: String = "tw" }


    @Serializable(IetfLanguageCodeSerializer::class)
    object Tahitian : IetfLanguageCode { override val code: String = "ty" }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class UighurUyghur : IetfLanguageCode {
        override val code: String = "ug"


        @Serializable(IetfLanguageCodeSerializer::class)
        object CN : UighurUyghur() { override val code: String = "ug-CN" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : UighurUyghur()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Ukrainian : IetfLanguageCode {
        override val code: String = "uk"


        @Serializable(IetfLanguageCodeSerializer::class)
        object UA : Ukrainian() { override val code: String = "uk-UA" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Ukrainian()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Urdu : IetfLanguageCode {
        override val code: String = "ur"


        @Serializable(IetfLanguageCodeSerializer::class)
        object IN : Urdu() { override val code: String = "ur-IN" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object PK : Urdu() { override val code: String = "ur-PK" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Urdu()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Uzbek : IetfLanguageCode {
        override val code: String = "uz"


        @Serializable(IetfLanguageCodeSerializer::class)
        sealed class Arab : Uzbek() {
            override val code: String = "uz-Arab"


            @Serializable(IetfLanguageCodeSerializer::class)
            object AF : Arab() { override val code: String = "uz-Arab-AF" }


            @Serializable(IetfLanguageCodeSerializer::class)
            companion object : Arab()
        }


        @Serializable(IetfLanguageCodeSerializer::class)
        sealed class Cyrl : Uzbek() {
            override val code: String = "uz-Cyrl"


            @Serializable(IetfLanguageCodeSerializer::class)
            object UZ : Cyrl() { override val code: String = "uz-Cyrl-UZ" }


            @Serializable(IetfLanguageCodeSerializer::class)
            companion object : Cyrl()
        }


        @Serializable(IetfLanguageCodeSerializer::class)
        sealed class Latn : Uzbek() {
            override val code: String = "uz-Latn"


            @Serializable(IetfLanguageCodeSerializer::class)
            object UZ : Latn() { override val code: String = "uz-Latn-UZ" }


            @Serializable(IetfLanguageCodeSerializer::class)
            companion object : Latn()
        }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Uzbek()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    object Venda : IetfLanguageCode { override val code: String = "ve" }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Vietnamese : IetfLanguageCode {
        override val code: String = "vi"


        @Serializable(IetfLanguageCodeSerializer::class)
        object VN : Vietnamese() { override val code: String = "vi-VN" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Vietnamese()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Volapük : IetfLanguageCode {
        override val code: String = "vo"


        @Serializable(IetfLanguageCodeSerializer::class)
        object L001 : Volapük() { override val code: String = "vo-001" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Volapük()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    object Walloon : IetfLanguageCode { override val code: String = "wa" }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Wolof : IetfLanguageCode {
        override val code: String = "wo"


        @Serializable(IetfLanguageCodeSerializer::class)
        object SN : Wolof() { override val code: String = "wo-SN" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Wolof()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Xhosa : IetfLanguageCode {
        override val code: String = "xh"


        @Serializable(IetfLanguageCodeSerializer::class)
        object ZA : Xhosa() { override val code: String = "xh-ZA" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Xhosa()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Yiddish : IetfLanguageCode {
        override val code: String = "yi"


        @Serializable(IetfLanguageCodeSerializer::class)
        object L001 : Yiddish() { override val code: String = "yi-001" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Yiddish()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Yoruba : IetfLanguageCode {
        override val code: String = "yo"


        @Serializable(IetfLanguageCodeSerializer::class)
        object BJ : Yoruba() { override val code: String = "yo-BJ" }


        @Serializable(IetfLanguageCodeSerializer::class)
        object NG : Yoruba() { override val code: String = "yo-NG" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Yoruba()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    object ZhuangChuang : IetfLanguageCode { override val code: String = "za" }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Chinese : IetfLanguageCode {
        override val code: String = "zh"


        @Serializable(IetfLanguageCodeSerializer::class)
        sealed class Hans : Chinese() {
            override val code: String = "zh-Hans"


            @Serializable(IetfLanguageCodeSerializer::class)
            object CN : Hans() { override val code: String = "zh-Hans-CN" }


            @Serializable(IetfLanguageCodeSerializer::class)
            object HK : Hans() { override val code: String = "zh-Hans-HK" }


            @Serializable(IetfLanguageCodeSerializer::class)
            object MO : Hans() { override val code: String = "zh-Hans-MO" }


            @Serializable(IetfLanguageCodeSerializer::class)
            object SG : Hans() { override val code: String = "zh-Hans-SG" }


            @Serializable(IetfLanguageCodeSerializer::class)
            companion object : Hans()
        }


        @Serializable(IetfLanguageCodeSerializer::class)
        sealed class Hant : Chinese() {
            override val code: String = "zh-Hant"


            @Serializable(IetfLanguageCodeSerializer::class)
            object HK : Hant() { override val code: String = "zh-Hant-HK" }


            @Serializable(IetfLanguageCodeSerializer::class)
            object MO : Hant() { override val code: String = "zh-Hant-MO" }


            @Serializable(IetfLanguageCodeSerializer::class)
            object TW : Hant() { override val code: String = "zh-Hant-TW" }


            @Serializable(IetfLanguageCodeSerializer::class)
            companion object : Hant()
        }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Chinese()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    sealed class Zulu : IetfLanguageCode {
        override val code: String = "zu"


        @Serializable(IetfLanguageCodeSerializer::class)
        object ZA : Zulu() { override val code: String = "zu-ZA" }


        @Serializable(IetfLanguageCodeSerializer::class)
        companion object : Zulu()
    }


    @Serializable(IetfLanguageCodeSerializer::class)
    data class UnknownIetfLanguageCode (override val code: String) : IetfLanguageCode
}
