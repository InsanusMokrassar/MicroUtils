@file:Suppress("SERIALIZER_TYPE_INCOMPATIBLE")

package dev.inmo.micro_utils.language_codes

import kotlinx.serialization.Serializable

/**
 * This class has been automatically generated using
 * https://github.com/InsanusMokrassar/MicroUtils/tree/master/language_codes/generator . This generator uses
 * https://datahub.io/core/language-codes/ files (base and tags) and create the whole hierarchy using it.
 */
@Serializable(IetfLangSerializer::class)
sealed interface IetfLang {
    val code: String
    val parentLang: IetfLang?
        get() = code.split("-").takeIf { it.size > 1 } ?.first() ?.let(::UnknownIetfLang)
    val withoutDialect: String
        get() = parentLang ?.code ?: code

    @Serializable(IetfLangSerializer::class)
    object Afar : IetfLang { override val code: String = "aa"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Abkhazian : IetfLang { override val code: String = "ab"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Achinese : IetfLang { override val code: String = "ace"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Acoli : IetfLang { override val code: String = "ach"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Adangme : IetfLang { override val code: String = "ada"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object AdygheAdygei : IetfLang { override val code: String = "ady"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object AfroAsiaticLanguages : IetfLang { override val code: String = "afa"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Afrihili : IetfLang { override val code: String = "afh"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface Afrikaans : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object NA : Afrikaans { override val code: String = "af-NA"; override val parentLang: Afrikaans get() = Afrikaans; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object ZA : Afrikaans { override val code: String = "af-ZA"; override val parentLang: Afrikaans get() = Afrikaans; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Afrikaans {
            override val code: String = "af"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object Ainu : IetfLang { override val code: String = "ain"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface Akan : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object GH : Akan { override val code: String = "ak-GH"; override val parentLang: Akan get() = Akan; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Akan {
            override val code: String = "ak"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object Akkadian : IetfLang { override val code: String = "akk"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface Albanian : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object AL : Albanian { override val code: String = "sq-AL"; override val parentLang: Albanian get() = Albanian; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object MK : Albanian { override val code: String = "sq-MK"; override val parentLang: Albanian get() = Albanian; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object XK : Albanian { override val code: String = "sq-XK"; override val parentLang: Albanian get() = Albanian; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Albanian {
            override val code: String = "sq"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object Aleut : IetfLang { override val code: String = "ale"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object AlgonquianLanguages : IetfLang { override val code: String = "alg"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object SouthernAltai : IetfLang { override val code: String = "alt"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface Amharic : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object ET : Amharic { override val code: String = "am-ET"; override val parentLang: Amharic get() = Amharic; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Amharic {
            override val code: String = "am"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object EnglishOldCa_4501100 : IetfLang { override val code: String = "ang"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Angika : IetfLang { override val code: String = "anp"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object ApacheLanguages : IetfLang { override val code: String = "apa"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface Arabic : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object L001 : Arabic { override val code: String = "ar-001"; override val parentLang: Arabic get() = Arabic; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object AE : Arabic { override val code: String = "ar-AE"; override val parentLang: Arabic get() = Arabic; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object BH : Arabic { override val code: String = "ar-BH"; override val parentLang: Arabic get() = Arabic; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object DJ : Arabic { override val code: String = "ar-DJ"; override val parentLang: Arabic get() = Arabic; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object DZ : Arabic { override val code: String = "ar-DZ"; override val parentLang: Arabic get() = Arabic; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object EG : Arabic { override val code: String = "ar-EG"; override val parentLang: Arabic get() = Arabic; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object EH : Arabic { override val code: String = "ar-EH"; override val parentLang: Arabic get() = Arabic; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object ER : Arabic { override val code: String = "ar-ER"; override val parentLang: Arabic get() = Arabic; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object IL : Arabic { override val code: String = "ar-IL"; override val parentLang: Arabic get() = Arabic; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object IQ : Arabic { override val code: String = "ar-IQ"; override val parentLang: Arabic get() = Arabic; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object JO : Arabic { override val code: String = "ar-JO"; override val parentLang: Arabic get() = Arabic; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object KM : Arabic { override val code: String = "ar-KM"; override val parentLang: Arabic get() = Arabic; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object KW : Arabic { override val code: String = "ar-KW"; override val parentLang: Arabic get() = Arabic; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object LB : Arabic { override val code: String = "ar-LB"; override val parentLang: Arabic get() = Arabic; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object LY : Arabic { override val code: String = "ar-LY"; override val parentLang: Arabic get() = Arabic; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object MA : Arabic { override val code: String = "ar-MA"; override val parentLang: Arabic get() = Arabic; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object MR : Arabic { override val code: String = "ar-MR"; override val parentLang: Arabic get() = Arabic; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object OM : Arabic { override val code: String = "ar-OM"; override val parentLang: Arabic get() = Arabic; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object PS : Arabic { override val code: String = "ar-PS"; override val parentLang: Arabic get() = Arabic; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object QA : Arabic { override val code: String = "ar-QA"; override val parentLang: Arabic get() = Arabic; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object SA : Arabic { override val code: String = "ar-SA"; override val parentLang: Arabic get() = Arabic; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object SD : Arabic { override val code: String = "ar-SD"; override val parentLang: Arabic get() = Arabic; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object SO : Arabic { override val code: String = "ar-SO"; override val parentLang: Arabic get() = Arabic; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object SS : Arabic { override val code: String = "ar-SS"; override val parentLang: Arabic get() = Arabic; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object SY : Arabic { override val code: String = "ar-SY"; override val parentLang: Arabic get() = Arabic; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object TD : Arabic { override val code: String = "ar-TD"; override val parentLang: Arabic get() = Arabic; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object TN : Arabic { override val code: String = "ar-TN"; override val parentLang: Arabic get() = Arabic; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object YE : Arabic { override val code: String = "ar-YE"; override val parentLang: Arabic get() = Arabic; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Arabic {
            override val code: String = "ar"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object OfficialAramaic700300BCEImperialAramaic700300BCE : IetfLang { override val code: String = "arc"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Aragonese : IetfLang { override val code: String = "an"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface Armenian : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object AM : Armenian { override val code: String = "hy-AM"; override val parentLang: Armenian get() = Armenian; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Armenian {
            override val code: String = "hy"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object MapudungunMapuche : IetfLang { override val code: String = "arn"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Arapaho : IetfLang { override val code: String = "arp"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object ArtificialLanguages : IetfLang { override val code: String = "art"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Arawak : IetfLang { override val code: String = "arw"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface Assamese : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object IN : Assamese { override val code: String = "as-IN"; override val parentLang: Assamese get() = Assamese; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Assamese {
            override val code: String = "as"
            override fun toString() = code
        }
    }


    @Serializable(IetfLangSerializer::class)
    sealed interface AsturianBableLeoneseAsturleonese : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object ES : AsturianBableLeoneseAsturleonese { override val code: String = "ast-ES"; override val parentLang: AsturianBableLeoneseAsturleonese get() = AsturianBableLeoneseAsturleonese; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : AsturianBableLeoneseAsturleonese {
            override val code: String = "ast"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object AthapascanLanguages : IetfLang { override val code: String = "ath"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object AustralianLanguages : IetfLang { override val code: String = "aus"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Avaric : IetfLang { override val code: String = "av"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Avestan : IetfLang { override val code: String = "ae"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Awadhi : IetfLang { override val code: String = "awa"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Aymara : IetfLang { override val code: String = "ay"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface Azerbaijani : IetfLang {


        @Serializable(IetfLangSerializer::class)
        sealed interface Cyrl : Azerbaijani {

            @Serializable(IetfLangSerializer::class)
            object AZ : Cyrl { override val code: String = "az-Cyrl-AZ"; override val parentLang: Cyrl get() = Cyrl; override fun toString() = code }

            @Serializable(IetfLangSerializer::class)
            companion object : Cyrl {
                override val code: String = "az-Cyrl"
                override val parentLang: Azerbaijani get() = Azerbaijani;
                override fun toString() = code
            }
        }


        @Serializable(IetfLangSerializer::class)
        sealed interface Latn : Azerbaijani {

            @Serializable(IetfLangSerializer::class)
            object AZ : Latn { override val code: String = "az-Latn-AZ"; override val parentLang: Latn get() = Latn; override fun toString() = code }

            @Serializable(IetfLangSerializer::class)
            companion object : Latn {
                override val code: String = "az-Latn"
                override val parentLang: Azerbaijani get() = Azerbaijani;
                override fun toString() = code
            }
        }


        @Serializable(IetfLangSerializer::class)
        companion object : Azerbaijani {
            override val code: String = "az"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object BandaLanguages : IetfLang { override val code: String = "bad"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object BamilekeLanguages : IetfLang { override val code: String = "bai"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Bashkir : IetfLang { override val code: String = "ba"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Baluchi : IetfLang { override val code: String = "bal"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface Bambara : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object ML : Bambara { override val code: String = "bm-ML"; override val parentLang: Bambara get() = Bambara; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Bambara {
            override val code: String = "bm"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object Balinese : IetfLang { override val code: String = "ban"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface Basque : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object ES : Basque { override val code: String = "eu-ES"; override val parentLang: Basque get() = Basque; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Basque {
            override val code: String = "eu"
            override fun toString() = code
        }
    }


    @Serializable(IetfLangSerializer::class)
    sealed interface Basa : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object CM : Basa { override val code: String = "bas-CM"; override val parentLang: Basa get() = Basa; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Basa {
            override val code: String = "bas"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object BalticLanguages : IetfLang { override val code: String = "bat"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object BejaBedawiyet : IetfLang { override val code: String = "bej"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface Belarusian : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object BY : Belarusian { override val code: String = "be-BY"; override val parentLang: Belarusian get() = Belarusian; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Belarusian {
            override val code: String = "be"
            override fun toString() = code
        }
    }


    @Serializable(IetfLangSerializer::class)
    sealed interface Bemba : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object ZM : Bemba { override val code: String = "bem-ZM"; override val parentLang: Bemba get() = Bemba; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Bemba {
            override val code: String = "bem"
            override fun toString() = code
        }
    }


    @Serializable(IetfLangSerializer::class)
    sealed interface Bengali : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object BD : Bengali { override val code: String = "bn-BD"; override val parentLang: Bengali get() = Bengali; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object IN : Bengali { override val code: String = "bn-IN"; override val parentLang: Bengali get() = Bengali; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Bengali {
            override val code: String = "bn"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object BerberLanguages : IetfLang { override val code: String = "ber"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Bhojpuri : IetfLang { override val code: String = "bho"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object BihariLanguages : IetfLang { override val code: String = "bh"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Bikol : IetfLang { override val code: String = "bik"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object BiniEdo : IetfLang { override val code: String = "bin"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Bislama : IetfLang { override val code: String = "bi"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Siksika : IetfLang { override val code: String = "bla"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object BantuOther : IetfLang { override val code: String = "bnt"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface Bosnian : IetfLang {


        @Serializable(IetfLangSerializer::class)
        sealed interface Cyrl : Bosnian {

            @Serializable(IetfLangSerializer::class)
            object BA : Cyrl { override val code: String = "bs-Cyrl-BA"; override val parentLang: Cyrl get() = Cyrl; override fun toString() = code }

            @Serializable(IetfLangSerializer::class)
            companion object : Cyrl {
                override val code: String = "bs-Cyrl"
                override val parentLang: Bosnian get() = Bosnian;
                override fun toString() = code
            }
        }


        @Serializable(IetfLangSerializer::class)
        sealed interface Latn : Bosnian {

            @Serializable(IetfLangSerializer::class)
            object BA : Latn { override val code: String = "bs-Latn-BA"; override val parentLang: Latn get() = Latn; override fun toString() = code }

            @Serializable(IetfLangSerializer::class)
            companion object : Latn {
                override val code: String = "bs-Latn"
                override val parentLang: Bosnian get() = Bosnian;
                override fun toString() = code
            }
        }


        @Serializable(IetfLangSerializer::class)
        companion object : Bosnian {
            override val code: String = "bs"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object Braj : IetfLang { override val code: String = "bra"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface Breton : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object FR : Breton { override val code: String = "br-FR"; override val parentLang: Breton get() = Breton; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Breton {
            override val code: String = "br"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object BatakLanguages : IetfLang { override val code: String = "btk"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Buriat : IetfLang { override val code: String = "bua"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Buginese : IetfLang { override val code: String = "bug"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface Bulgarian : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object BG : Bulgarian { override val code: String = "bg-BG"; override val parentLang: Bulgarian get() = Bulgarian; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Bulgarian {
            override val code: String = "bg"
            override fun toString() = code
        }
    }


    @Serializable(IetfLangSerializer::class)
    sealed interface Burmese : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object MM : Burmese { override val code: String = "my-MM"; override val parentLang: Burmese get() = Burmese; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Burmese {
            override val code: String = "my"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object BlinBilin : IetfLang { override val code: String = "byn"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Caddo : IetfLang { override val code: String = "cad"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object CentralAmericanIndianLanguages : IetfLang { override val code: String = "cai"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object GalibiCarib : IetfLang { override val code: String = "car"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface CatalanValencian : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object AD : CatalanValencian { override val code: String = "ca-AD"; override val parentLang: CatalanValencian get() = CatalanValencian; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        sealed interface ES : CatalanValencian {

            @Serializable(IetfLangSerializer::class)
            object VALENCIA : ES { override val code: String = "ca-ES-VALENCIA"; override val parentLang: ES get() = ES; override fun toString() = code }

            @Serializable(IetfLangSerializer::class)
            companion object : ES {
                override val code: String = "ca-ES"
                override val parentLang: CatalanValencian get() = CatalanValencian;
                override fun toString() = code
            }
        }

        @Serializable(IetfLangSerializer::class)
        object FR : CatalanValencian { override val code: String = "ca-FR"; override val parentLang: CatalanValencian get() = CatalanValencian; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object IT : CatalanValencian { override val code: String = "ca-IT"; override val parentLang: CatalanValencian get() = CatalanValencian; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : CatalanValencian {
            override val code: String = "ca"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object CaucasianLanguages : IetfLang { override val code: String = "cau"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Cebuano : IetfLang { override val code: String = "ceb"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object CelticLanguages : IetfLang { override val code: String = "cel"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Chamorro : IetfLang { override val code: String = "ch"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Chibcha : IetfLang { override val code: String = "chb"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface Chechen : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object RU : Chechen { override val code: String = "ce-RU"; override val parentLang: Chechen get() = Chechen; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Chechen {
            override val code: String = "ce"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object Chagatai : IetfLang { override val code: String = "chg"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface Chinese : IetfLang {


        @Serializable(IetfLangSerializer::class)
        sealed interface Hans : Chinese {

            @Serializable(IetfLangSerializer::class)
            object CN : Hans { override val code: String = "zh-Hans-CN"; override val parentLang: Hans get() = Hans; override fun toString() = code }
            @Serializable(IetfLangSerializer::class)
            object HK : Hans { override val code: String = "zh-Hans-HK"; override val parentLang: Hans get() = Hans; override fun toString() = code }
            @Serializable(IetfLangSerializer::class)
            object MO : Hans { override val code: String = "zh-Hans-MO"; override val parentLang: Hans get() = Hans; override fun toString() = code }
            @Serializable(IetfLangSerializer::class)
            object SG : Hans { override val code: String = "zh-Hans-SG"; override val parentLang: Hans get() = Hans; override fun toString() = code }

            @Serializable(IetfLangSerializer::class)
            companion object : Hans {
                override val code: String = "zh-Hans"
                override val parentLang: Chinese get() = Chinese;
                override fun toString() = code
            }
        }


        @Serializable(IetfLangSerializer::class)
        sealed interface Hant : Chinese {

            @Serializable(IetfLangSerializer::class)
            object HK : Hant { override val code: String = "zh-Hant-HK"; override val parentLang: Hant get() = Hant; override fun toString() = code }
            @Serializable(IetfLangSerializer::class)
            object MO : Hant { override val code: String = "zh-Hant-MO"; override val parentLang: Hant get() = Hant; override fun toString() = code }
            @Serializable(IetfLangSerializer::class)
            object TW : Hant { override val code: String = "zh-Hant-TW"; override val parentLang: Hant get() = Hant; override fun toString() = code }

            @Serializable(IetfLangSerializer::class)
            companion object : Hant {
                override val code: String = "zh-Hant"
                override val parentLang: Chinese get() = Chinese;
                override fun toString() = code
            }
        }


        @Serializable(IetfLangSerializer::class)
        companion object : Chinese {
            override val code: String = "zh"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object Chuukese : IetfLang { override val code: String = "chk"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Mari : IetfLang { override val code: String = "chm"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object ChinookJargon : IetfLang { override val code: String = "chn"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Choctaw : IetfLang { override val code: String = "cho"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object ChipewyanDeneSuline : IetfLang { override val code: String = "chp"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface Cherokee : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object US : Cherokee { override val code: String = "chr-US"; override val parentLang: Cherokee get() = Cherokee; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Cherokee {
            override val code: String = "chr"
            override fun toString() = code
        }
    }


    @Serializable(IetfLangSerializer::class)
    sealed interface ChurchSlavicOldSlavonicChurchSlavonicOldBulgarianOldChurchSlavonic : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object RU : ChurchSlavicOldSlavonicChurchSlavonicOldBulgarianOldChurchSlavonic { override val code: String = "cu-RU"; override val parentLang: ChurchSlavicOldSlavonicChurchSlavonicOldBulgarianOldChurchSlavonic get() = ChurchSlavicOldSlavonicChurchSlavonicOldBulgarianOldChurchSlavonic; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : ChurchSlavicOldSlavonicChurchSlavonicOldBulgarianOldChurchSlavonic {
            override val code: String = "cu"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object Chuvash : IetfLang { override val code: String = "cv"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Cheyenne : IetfLang { override val code: String = "chy"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object ChamicLanguages : IetfLang { override val code: String = "cmc"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Coptic : IetfLang { override val code: String = "cop"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface Cornish : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object GB : Cornish { override val code: String = "kw-GB"; override val parentLang: Cornish get() = Cornish; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Cornish {
            override val code: String = "kw"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object Corsican : IetfLang { override val code: String = "co"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object CreolesAndPidginsEnglishBased : IetfLang { override val code: String = "cpe"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object CreolesAndPidginsFrenchbased : IetfLang { override val code: String = "cpf"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object CreolesAndPidginsPortuguesebased : IetfLang { override val code: String = "cpp"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Cree : IetfLang { override val code: String = "cr"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object CrimeanTatarCrimeanTurkish : IetfLang { override val code: String = "crh"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object CreolesAndPidgins : IetfLang { override val code: String = "crp"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Kashubian : IetfLang { override val code: String = "csb"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object CushiticLanguages : IetfLang { override val code: String = "cus"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface Czech : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object CZ : Czech { override val code: String = "cs-CZ"; override val parentLang: Czech get() = Czech; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Czech {
            override val code: String = "cs"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object Dakota : IetfLang { override val code: String = "dak"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface Danish : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object DK : Danish { override val code: String = "da-DK"; override val parentLang: Danish get() = Danish; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object GL : Danish { override val code: String = "da-GL"; override val parentLang: Danish get() = Danish; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Danish {
            override val code: String = "da"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object Dargwa : IetfLang { override val code: String = "dar"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object LandDayakLanguages : IetfLang { override val code: String = "day"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Delaware : IetfLang { override val code: String = "del"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object SlaveAthapascan : IetfLang { override val code: String = "den"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Dogrib : IetfLang { override val code: String = "dgr"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Dinka : IetfLang { override val code: String = "din"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object DivehiDhivehiMaldivian : IetfLang { override val code: String = "dv"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Dogri : IetfLang { override val code: String = "doi"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object DravidianLanguages : IetfLang { override val code: String = "dra"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface LowerSorbian : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object DE : LowerSorbian { override val code: String = "dsb-DE"; override val parentLang: LowerSorbian get() = LowerSorbian; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : LowerSorbian {
            override val code: String = "dsb"
            override fun toString() = code
        }
    }


    @Serializable(IetfLangSerializer::class)
    sealed interface Duala : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object CM : Duala { override val code: String = "dua-CM"; override val parentLang: Duala get() = Duala; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Duala {
            override val code: String = "dua"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object DutchMiddleCa_10501350 : IetfLang { override val code: String = "dum"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface DutchFlemish : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object AW : DutchFlemish { override val code: String = "nl-AW"; override val parentLang: DutchFlemish get() = DutchFlemish; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object BE : DutchFlemish { override val code: String = "nl-BE"; override val parentLang: DutchFlemish get() = DutchFlemish; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object BQ : DutchFlemish { override val code: String = "nl-BQ"; override val parentLang: DutchFlemish get() = DutchFlemish; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object CW : DutchFlemish { override val code: String = "nl-CW"; override val parentLang: DutchFlemish get() = DutchFlemish; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object NL : DutchFlemish { override val code: String = "nl-NL"; override val parentLang: DutchFlemish get() = DutchFlemish; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object SR : DutchFlemish { override val code: String = "nl-SR"; override val parentLang: DutchFlemish get() = DutchFlemish; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object SX : DutchFlemish { override val code: String = "nl-SX"; override val parentLang: DutchFlemish get() = DutchFlemish; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : DutchFlemish {
            override val code: String = "nl"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object Dyula : IetfLang { override val code: String = "dyu"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface Dzongkha : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object BT : Dzongkha { override val code: String = "dz-BT"; override val parentLang: Dzongkha get() = Dzongkha; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Dzongkha {
            override val code: String = "dz"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object Efik : IetfLang { override val code: String = "efi"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object EgyptianAncient : IetfLang { override val code: String = "egy"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Ekajuk : IetfLang { override val code: String = "eka"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Elamite : IetfLang { override val code: String = "elx"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface English : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object L001 : English { override val code: String = "en-001"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object L150 : English { override val code: String = "en-150"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object AG : English { override val code: String = "en-AG"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object AI : English { override val code: String = "en-AI"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object AS : English { override val code: String = "en-AS"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object AT : English { override val code: String = "en-AT"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object AU : English { override val code: String = "en-AU"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object BB : English { override val code: String = "en-BB"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object BE : English { override val code: String = "en-BE"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object BI : English { override val code: String = "en-BI"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object BM : English { override val code: String = "en-BM"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object BS : English { override val code: String = "en-BS"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object BW : English { override val code: String = "en-BW"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object BZ : English { override val code: String = "en-BZ"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object CA : English { override val code: String = "en-CA"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object CC : English { override val code: String = "en-CC"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object CH : English { override val code: String = "en-CH"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object CK : English { override val code: String = "en-CK"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object CM : English { override val code: String = "en-CM"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object CX : English { override val code: String = "en-CX"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object CY : English { override val code: String = "en-CY"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object DE : English { override val code: String = "en-DE"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object DG : English { override val code: String = "en-DG"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object DK : English { override val code: String = "en-DK"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object DM : English { override val code: String = "en-DM"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object ER : English { override val code: String = "en-ER"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object FI : English { override val code: String = "en-FI"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object FJ : English { override val code: String = "en-FJ"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object FK : English { override val code: String = "en-FK"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object FM : English { override val code: String = "en-FM"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object GB : English { override val code: String = "en-GB"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object GD : English { override val code: String = "en-GD"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object GG : English { override val code: String = "en-GG"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object GH : English { override val code: String = "en-GH"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object GI : English { override val code: String = "en-GI"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object GM : English { override val code: String = "en-GM"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object GU : English { override val code: String = "en-GU"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object GY : English { override val code: String = "en-GY"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object HK : English { override val code: String = "en-HK"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object IE : English { override val code: String = "en-IE"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object IL : English { override val code: String = "en-IL"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object IM : English { override val code: String = "en-IM"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object IN : English { override val code: String = "en-IN"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object IO : English { override val code: String = "en-IO"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object JE : English { override val code: String = "en-JE"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object JM : English { override val code: String = "en-JM"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object KE : English { override val code: String = "en-KE"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object KI : English { override val code: String = "en-KI"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object KN : English { override val code: String = "en-KN"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object KY : English { override val code: String = "en-KY"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object LC : English { override val code: String = "en-LC"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object LR : English { override val code: String = "en-LR"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object LS : English { override val code: String = "en-LS"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object MG : English { override val code: String = "en-MG"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object MH : English { override val code: String = "en-MH"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object MO : English { override val code: String = "en-MO"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object MP : English { override val code: String = "en-MP"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object MS : English { override val code: String = "en-MS"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object MT : English { override val code: String = "en-MT"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object MU : English { override val code: String = "en-MU"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object MW : English { override val code: String = "en-MW"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object MY : English { override val code: String = "en-MY"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object NA : English { override val code: String = "en-NA"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object NF : English { override val code: String = "en-NF"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object NG : English { override val code: String = "en-NG"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object NL : English { override val code: String = "en-NL"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object NR : English { override val code: String = "en-NR"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object NU : English { override val code: String = "en-NU"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object NZ : English { override val code: String = "en-NZ"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object PG : English { override val code: String = "en-PG"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object PH : English { override val code: String = "en-PH"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object PK : English { override val code: String = "en-PK"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object PN : English { override val code: String = "en-PN"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object PR : English { override val code: String = "en-PR"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object PW : English { override val code: String = "en-PW"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object RW : English { override val code: String = "en-RW"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object SB : English { override val code: String = "en-SB"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object SC : English { override val code: String = "en-SC"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object SD : English { override val code: String = "en-SD"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object SE : English { override val code: String = "en-SE"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object SG : English { override val code: String = "en-SG"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object SH : English { override val code: String = "en-SH"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object SI : English { override val code: String = "en-SI"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object SL : English { override val code: String = "en-SL"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object SS : English { override val code: String = "en-SS"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object SX : English { override val code: String = "en-SX"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object SZ : English { override val code: String = "en-SZ"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object TC : English { override val code: String = "en-TC"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object TK : English { override val code: String = "en-TK"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object TO : English { override val code: String = "en-TO"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object TT : English { override val code: String = "en-TT"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object TV : English { override val code: String = "en-TV"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object TZ : English { override val code: String = "en-TZ"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object UG : English { override val code: String = "en-UG"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object UM : English { override val code: String = "en-UM"; override val parentLang: English get() = English; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        sealed interface US : English {

            @Serializable(IetfLangSerializer::class)
            object POSIX : US { override val code: String = "en-US-POSIX"; override val parentLang: US get() = US; override fun toString() = code }

            @Serializable(IetfLangSerializer::class)
            companion object : US {
                override val code: String = "en-US"
                override val parentLang: English get() = English;
                override fun toString() = code
            }
        }

        @Serializable(IetfLangSerializer::class)
        object VC : English { override val code: String = "en-VC"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object VG : English { override val code: String = "en-VG"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object VI : English { override val code: String = "en-VI"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object VU : English { override val code: String = "en-VU"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object WS : English { override val code: String = "en-WS"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object ZA : English { override val code: String = "en-ZA"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object ZM : English { override val code: String = "en-ZM"; override val parentLang: English get() = English; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object ZW : English { override val code: String = "en-ZW"; override val parentLang: English get() = English; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : English {
            override val code: String = "en"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object EnglishMiddle11001500 : IetfLang { override val code: String = "enm"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface Esperanto : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object L001 : Esperanto { override val code: String = "eo-001"; override val parentLang: Esperanto get() = Esperanto; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Esperanto {
            override val code: String = "eo"
            override fun toString() = code
        }
    }


    @Serializable(IetfLangSerializer::class)
    sealed interface Estonian : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object EE : Estonian { override val code: String = "et-EE"; override val parentLang: Estonian get() = Estonian; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Estonian {
            override val code: String = "et"
            override fun toString() = code
        }
    }


    @Serializable(IetfLangSerializer::class)
    sealed interface Ewe : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object GH : Ewe { override val code: String = "ee-GH"; override val parentLang: Ewe get() = Ewe; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object TG : Ewe { override val code: String = "ee-TG"; override val parentLang: Ewe get() = Ewe; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Ewe {
            override val code: String = "ee"
            override fun toString() = code
        }
    }


    @Serializable(IetfLangSerializer::class)
    sealed interface Ewondo : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object CM : Ewondo { override val code: String = "ewo-CM"; override val parentLang: Ewondo get() = Ewondo; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Ewondo {
            override val code: String = "ewo"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object Fang : IetfLang { override val code: String = "fan"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface Faroese : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object DK : Faroese { override val code: String = "fo-DK"; override val parentLang: Faroese get() = Faroese; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object FO : Faroese { override val code: String = "fo-FO"; override val parentLang: Faroese get() = Faroese; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Faroese {
            override val code: String = "fo"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object Fanti : IetfLang { override val code: String = "fat"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Fijian : IetfLang { override val code: String = "fj"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface FilipinoPilipino : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object PH : FilipinoPilipino { override val code: String = "fil-PH"; override val parentLang: FilipinoPilipino get() = FilipinoPilipino; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : FilipinoPilipino {
            override val code: String = "fil"
            override fun toString() = code
        }
    }


    @Serializable(IetfLangSerializer::class)
    sealed interface Finnish : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object FI : Finnish { override val code: String = "fi-FI"; override val parentLang: Finnish get() = Finnish; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Finnish {
            override val code: String = "fi"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object FinnoUgrianLanguages : IetfLang { override val code: String = "fiu"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Fon : IetfLang { override val code: String = "fon"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface French : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object BE : French { override val code: String = "fr-BE"; override val parentLang: French get() = French; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object BF : French { override val code: String = "fr-BF"; override val parentLang: French get() = French; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object BI : French { override val code: String = "fr-BI"; override val parentLang: French get() = French; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object BJ : French { override val code: String = "fr-BJ"; override val parentLang: French get() = French; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object BL : French { override val code: String = "fr-BL"; override val parentLang: French get() = French; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object CA : French { override val code: String = "fr-CA"; override val parentLang: French get() = French; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object CD : French { override val code: String = "fr-CD"; override val parentLang: French get() = French; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object CF : French { override val code: String = "fr-CF"; override val parentLang: French get() = French; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object CG : French { override val code: String = "fr-CG"; override val parentLang: French get() = French; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object CH : French { override val code: String = "fr-CH"; override val parentLang: French get() = French; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object CI : French { override val code: String = "fr-CI"; override val parentLang: French get() = French; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object CM : French { override val code: String = "fr-CM"; override val parentLang: French get() = French; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object DJ : French { override val code: String = "fr-DJ"; override val parentLang: French get() = French; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object DZ : French { override val code: String = "fr-DZ"; override val parentLang: French get() = French; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object FR : French { override val code: String = "fr-FR"; override val parentLang: French get() = French; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object GA : French { override val code: String = "fr-GA"; override val parentLang: French get() = French; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object GF : French { override val code: String = "fr-GF"; override val parentLang: French get() = French; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object GN : French { override val code: String = "fr-GN"; override val parentLang: French get() = French; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object GP : French { override val code: String = "fr-GP"; override val parentLang: French get() = French; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object GQ : French { override val code: String = "fr-GQ"; override val parentLang: French get() = French; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object HT : French { override val code: String = "fr-HT"; override val parentLang: French get() = French; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object KM : French { override val code: String = "fr-KM"; override val parentLang: French get() = French; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object LU : French { override val code: String = "fr-LU"; override val parentLang: French get() = French; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object MA : French { override val code: String = "fr-MA"; override val parentLang: French get() = French; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object MC : French { override val code: String = "fr-MC"; override val parentLang: French get() = French; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object MF : French { override val code: String = "fr-MF"; override val parentLang: French get() = French; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object MG : French { override val code: String = "fr-MG"; override val parentLang: French get() = French; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object ML : French { override val code: String = "fr-ML"; override val parentLang: French get() = French; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object MQ : French { override val code: String = "fr-MQ"; override val parentLang: French get() = French; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object MR : French { override val code: String = "fr-MR"; override val parentLang: French get() = French; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object MU : French { override val code: String = "fr-MU"; override val parentLang: French get() = French; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object NC : French { override val code: String = "fr-NC"; override val parentLang: French get() = French; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object NE : French { override val code: String = "fr-NE"; override val parentLang: French get() = French; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object PF : French { override val code: String = "fr-PF"; override val parentLang: French get() = French; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object PM : French { override val code: String = "fr-PM"; override val parentLang: French get() = French; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object RE : French { override val code: String = "fr-RE"; override val parentLang: French get() = French; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object RW : French { override val code: String = "fr-RW"; override val parentLang: French get() = French; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object SC : French { override val code: String = "fr-SC"; override val parentLang: French get() = French; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object SN : French { override val code: String = "fr-SN"; override val parentLang: French get() = French; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object SY : French { override val code: String = "fr-SY"; override val parentLang: French get() = French; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object TD : French { override val code: String = "fr-TD"; override val parentLang: French get() = French; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object TG : French { override val code: String = "fr-TG"; override val parentLang: French get() = French; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object TN : French { override val code: String = "fr-TN"; override val parentLang: French get() = French; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object VU : French { override val code: String = "fr-VU"; override val parentLang: French get() = French; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object WF : French { override val code: String = "fr-WF"; override val parentLang: French get() = French; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object YT : French { override val code: String = "fr-YT"; override val parentLang: French get() = French; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : French {
            override val code: String = "fr"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object FrenchMiddleCa_14001600 : IetfLang { override val code: String = "frm"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object FrenchOld842ca_1400 : IetfLang { override val code: String = "fro"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object NorthernFrisian : IetfLang { override val code: String = "frr"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object EasternFrisian : IetfLang { override val code: String = "frs"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface WesternFrisian : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object NL : WesternFrisian { override val code: String = "fy-NL"; override val parentLang: WesternFrisian get() = WesternFrisian; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : WesternFrisian {
            override val code: String = "fy"
            override fun toString() = code
        }
    }


    @Serializable(IetfLangSerializer::class)
    sealed interface Fulah : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object CM : Fulah { override val code: String = "ff-CM"; override val parentLang: Fulah get() = Fulah; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object GN : Fulah { override val code: String = "ff-GN"; override val parentLang: Fulah get() = Fulah; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object MR : Fulah { override val code: String = "ff-MR"; override val parentLang: Fulah get() = Fulah; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object SN : Fulah { override val code: String = "ff-SN"; override val parentLang: Fulah get() = Fulah; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Fulah {
            override val code: String = "ff"
            override fun toString() = code
        }
    }


    @Serializable(IetfLangSerializer::class)
    sealed interface Friulian : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object IT : Friulian { override val code: String = "fur-IT"; override val parentLang: Friulian get() = Friulian; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Friulian {
            override val code: String = "fur"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object Ga : IetfLang { override val code: String = "gaa"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Gayo : IetfLang { override val code: String = "gay"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Gbaya : IetfLang { override val code: String = "gba"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object GermanicLanguages : IetfLang { override val code: String = "gem"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface Georgian : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object GE : Georgian { override val code: String = "ka-GE"; override val parentLang: Georgian get() = Georgian; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Georgian {
            override val code: String = "ka"
            override fun toString() = code
        }
    }


    @Serializable(IetfLangSerializer::class)
    sealed interface German : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object AT : German { override val code: String = "de-AT"; override val parentLang: German get() = German; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object BE : German { override val code: String = "de-BE"; override val parentLang: German get() = German; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object CH : German { override val code: String = "de-CH"; override val parentLang: German get() = German; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object DE : German { override val code: String = "de-DE"; override val parentLang: German get() = German; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object IT : German { override val code: String = "de-IT"; override val parentLang: German get() = German; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object LI : German { override val code: String = "de-LI"; override val parentLang: German get() = German; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object LU : German { override val code: String = "de-LU"; override val parentLang: German get() = German; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : German {
            override val code: String = "de"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object Geez : IetfLang { override val code: String = "gez"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Gilbertese : IetfLang { override val code: String = "gil"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface GaelicScottishGaelic : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object GB : GaelicScottishGaelic { override val code: String = "gd-GB"; override val parentLang: GaelicScottishGaelic get() = GaelicScottishGaelic; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : GaelicScottishGaelic {
            override val code: String = "gd"
            override fun toString() = code
        }
    }


    @Serializable(IetfLangSerializer::class)
    sealed interface Irish : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object IE : Irish { override val code: String = "ga-IE"; override val parentLang: Irish get() = Irish; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Irish {
            override val code: String = "ga"
            override fun toString() = code
        }
    }


    @Serializable(IetfLangSerializer::class)
    sealed interface Galician : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object ES : Galician { override val code: String = "gl-ES"; override val parentLang: Galician get() = Galician; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Galician {
            override val code: String = "gl"
            override fun toString() = code
        }
    }


    @Serializable(IetfLangSerializer::class)
    sealed interface Manx : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object IM : Manx { override val code: String = "gv-IM"; override val parentLang: Manx get() = Manx; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Manx {
            override val code: String = "gv"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object GermanMiddleHighCa_10501500 : IetfLang { override val code: String = "gmh"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object GermanOldHighCa_7501050 : IetfLang { override val code: String = "goh"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Gondi : IetfLang { override val code: String = "gon"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Gorontalo : IetfLang { override val code: String = "gor"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Gothic : IetfLang { override val code: String = "got"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Grebo : IetfLang { override val code: String = "grb"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object GreekAncientTo1453 : IetfLang { override val code: String = "grc"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface GreekModern1453 : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object CY : GreekModern1453 { override val code: String = "el-CY"; override val parentLang: GreekModern1453 get() = GreekModern1453; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object GR : GreekModern1453 { override val code: String = "el-GR"; override val parentLang: GreekModern1453 get() = GreekModern1453; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : GreekModern1453 {
            override val code: String = "el"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object Guarani : IetfLang { override val code: String = "gn"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface SwissGermanAlemannicAlsatian : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object CH : SwissGermanAlemannicAlsatian { override val code: String = "gsw-CH"; override val parentLang: SwissGermanAlemannicAlsatian get() = SwissGermanAlemannicAlsatian; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object FR : SwissGermanAlemannicAlsatian { override val code: String = "gsw-FR"; override val parentLang: SwissGermanAlemannicAlsatian get() = SwissGermanAlemannicAlsatian; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object LI : SwissGermanAlemannicAlsatian { override val code: String = "gsw-LI"; override val parentLang: SwissGermanAlemannicAlsatian get() = SwissGermanAlemannicAlsatian; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : SwissGermanAlemannicAlsatian {
            override val code: String = "gsw"
            override fun toString() = code
        }
    }


    @Serializable(IetfLangSerializer::class)
    sealed interface Gujarati : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object IN : Gujarati { override val code: String = "gu-IN"; override val parentLang: Gujarati get() = Gujarati; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Gujarati {
            override val code: String = "gu"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object Gwich_in : IetfLang { override val code: String = "gwi"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Haida : IetfLang { override val code: String = "hai"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object HaitianHaitianCreole : IetfLang { override val code: String = "ht"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface Hausa : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object GH : Hausa { override val code: String = "ha-GH"; override val parentLang: Hausa get() = Hausa; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object NE : Hausa { override val code: String = "ha-NE"; override val parentLang: Hausa get() = Hausa; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object NG : Hausa { override val code: String = "ha-NG"; override val parentLang: Hausa get() = Hausa; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Hausa {
            override val code: String = "ha"
            override fun toString() = code
        }
    }


    @Serializable(IetfLangSerializer::class)
    sealed interface Hawaiian : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object US : Hawaiian { override val code: String = "haw-US"; override val parentLang: Hawaiian get() = Hawaiian; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Hawaiian {
            override val code: String = "haw"
            override fun toString() = code
        }
    }


    @Serializable(IetfLangSerializer::class)
    sealed interface Hebrew : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object IL : Hebrew { override val code: String = "he-IL"; override val parentLang: Hebrew get() = Hebrew; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Hebrew {
            override val code: String = "he"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object Herero : IetfLang { override val code: String = "hz"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Hiligaynon : IetfLang { override val code: String = "hil"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object HimachaliLanguagesWesternPahariLanguages : IetfLang { override val code: String = "him"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface Hindi : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object IN : Hindi { override val code: String = "hi-IN"; override val parentLang: Hindi get() = Hindi; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Hindi {
            override val code: String = "hi"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object Hittite : IetfLang { override val code: String = "hit"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object HmongMong : IetfLang { override val code: String = "hmn"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object HiriMotu : IetfLang { override val code: String = "ho"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface Croatian : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object BA : Croatian { override val code: String = "hr-BA"; override val parentLang: Croatian get() = Croatian; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object HR : Croatian { override val code: String = "hr-HR"; override val parentLang: Croatian get() = Croatian; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Croatian {
            override val code: String = "hr"
            override fun toString() = code
        }
    }


    @Serializable(IetfLangSerializer::class)
    sealed interface UpperSorbian : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object DE : UpperSorbian { override val code: String = "hsb-DE"; override val parentLang: UpperSorbian get() = UpperSorbian; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : UpperSorbian {
            override val code: String = "hsb"
            override fun toString() = code
        }
    }


    @Serializable(IetfLangSerializer::class)
    sealed interface Hungarian : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object HU : Hungarian { override val code: String = "hu-HU"; override val parentLang: Hungarian get() = Hungarian; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Hungarian {
            override val code: String = "hu"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object Hupa : IetfLang { override val code: String = "hup"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Iban : IetfLang { override val code: String = "iba"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface Igbo : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object NG : Igbo { override val code: String = "ig-NG"; override val parentLang: Igbo get() = Igbo; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Igbo {
            override val code: String = "ig"
            override fun toString() = code
        }
    }


    @Serializable(IetfLangSerializer::class)
    sealed interface Icelandic : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object IS : Icelandic { override val code: String = "is-IS"; override val parentLang: Icelandic get() = Icelandic; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Icelandic {
            override val code: String = "is"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object Ido : IetfLang { override val code: String = "io"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface SichuanYiNuosu : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object CN : SichuanYiNuosu { override val code: String = "ii-CN"; override val parentLang: SichuanYiNuosu get() = SichuanYiNuosu; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : SichuanYiNuosu {
            override val code: String = "ii"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object IjoLanguages : IetfLang { override val code: String = "ijo"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Inuktitut : IetfLang { override val code: String = "iu"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object InterlingueOccidental : IetfLang { override val code: String = "ie"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Iloko : IetfLang { override val code: String = "ilo"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object InterlinguaInternationalAuxiliaryLanguageAssociation : IetfLang { override val code: String = "ia"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object IndicLanguages : IetfLang { override val code: String = "inc"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface Indonesian : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object ID : Indonesian { override val code: String = "id-ID"; override val parentLang: Indonesian get() = Indonesian; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Indonesian {
            override val code: String = "id"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object IndoEuropeanLanguages : IetfLang { override val code: String = "ine"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Ingush : IetfLang { override val code: String = "inh"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Inupiaq : IetfLang { override val code: String = "ik"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object IranianLanguages : IetfLang { override val code: String = "ira"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object IroquoianLanguages : IetfLang { override val code: String = "iro"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface Italian : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object CH : Italian { override val code: String = "it-CH"; override val parentLang: Italian get() = Italian; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object IT : Italian { override val code: String = "it-IT"; override val parentLang: Italian get() = Italian; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object SM : Italian { override val code: String = "it-SM"; override val parentLang: Italian get() = Italian; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object VA : Italian { override val code: String = "it-VA"; override val parentLang: Italian get() = Italian; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Italian {
            override val code: String = "it"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object Javanese : IetfLang { override val code: String = "jv"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Lojban : IetfLang { override val code: String = "jbo"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface Japanese : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object JP : Japanese { override val code: String = "ja-JP"; override val parentLang: Japanese get() = Japanese; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Japanese {
            override val code: String = "ja"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object JudeoPersian : IetfLang { override val code: String = "jpr"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object JudeoArabic : IetfLang { override val code: String = "jrb"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object KaraKalpak : IetfLang { override val code: String = "kaa"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface Kabyle : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object DZ : Kabyle { override val code: String = "kab-DZ"; override val parentLang: Kabyle get() = Kabyle; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Kabyle {
            override val code: String = "kab"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object KachinJingpho : IetfLang { override val code: String = "kac"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface KalaallisutGreenlandic : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object GL : KalaallisutGreenlandic { override val code: String = "kl-GL"; override val parentLang: KalaallisutGreenlandic get() = KalaallisutGreenlandic; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : KalaallisutGreenlandic {
            override val code: String = "kl"
            override fun toString() = code
        }
    }


    @Serializable(IetfLangSerializer::class)
    sealed interface Kamba : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object KE : Kamba { override val code: String = "kam-KE"; override val parentLang: Kamba get() = Kamba; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Kamba {
            override val code: String = "kam"
            override fun toString() = code
        }
    }


    @Serializable(IetfLangSerializer::class)
    sealed interface Kannada : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object IN : Kannada { override val code: String = "kn-IN"; override val parentLang: Kannada get() = Kannada; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Kannada {
            override val code: String = "kn"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object KarenLanguages : IetfLang { override val code: String = "kar"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface Kashmiri : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object IN : Kashmiri { override val code: String = "ks-IN"; override val parentLang: Kashmiri get() = Kashmiri; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Kashmiri {
            override val code: String = "ks"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object Kanuri : IetfLang { override val code: String = "kr"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Kawi : IetfLang { override val code: String = "kaw"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface Kazakh : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object KZ : Kazakh { override val code: String = "kk-KZ"; override val parentLang: Kazakh get() = Kazakh; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Kazakh {
            override val code: String = "kk"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object Kabardian : IetfLang { override val code: String = "kbd"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Khasi : IetfLang { override val code: String = "kha"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object KhoisanLanguages : IetfLang { override val code: String = "khi"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface CentralKhmer : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object KH : CentralKhmer { override val code: String = "km-KH"; override val parentLang: CentralKhmer get() = CentralKhmer; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : CentralKhmer {
            override val code: String = "km"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object KhotaneseSakan : IetfLang { override val code: String = "kho"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface KikuyuGikuyu : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object KE : KikuyuGikuyu { override val code: String = "ki-KE"; override val parentLang: KikuyuGikuyu get() = KikuyuGikuyu; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : KikuyuGikuyu {
            override val code: String = "ki"
            override fun toString() = code
        }
    }


    @Serializable(IetfLangSerializer::class)
    sealed interface Kinyarwanda : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object RW : Kinyarwanda { override val code: String = "rw-RW"; override val parentLang: Kinyarwanda get() = Kinyarwanda; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Kinyarwanda {
            override val code: String = "rw"
            override fun toString() = code
        }
    }


    @Serializable(IetfLangSerializer::class)
    sealed interface KirghizKyrgyz : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object KG : KirghizKyrgyz { override val code: String = "ky-KG"; override val parentLang: KirghizKyrgyz get() = KirghizKyrgyz; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : KirghizKyrgyz {
            override val code: String = "ky"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object Kimbundu : IetfLang { override val code: String = "kmb"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface Konkani : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object IN : Konkani { override val code: String = "kok-IN"; override val parentLang: Konkani get() = Konkani; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Konkani {
            override val code: String = "kok"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object Komi : IetfLang { override val code: String = "kv"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Kongo : IetfLang { override val code: String = "kg"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface Korean : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object KP : Korean { override val code: String = "ko-KP"; override val parentLang: Korean get() = Korean; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object KR : Korean { override val code: String = "ko-KR"; override val parentLang: Korean get() = Korean; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Korean {
            override val code: String = "ko"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object Kosraean : IetfLang { override val code: String = "kos"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Kpelle : IetfLang { override val code: String = "kpe"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object KarachayBalkar : IetfLang { override val code: String = "krc"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Karelian : IetfLang { override val code: String = "krl"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object KruLanguages : IetfLang { override val code: String = "kro"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Kurukh : IetfLang { override val code: String = "kru"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object KuanyamaKwanyama : IetfLang { override val code: String = "kj"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Kumyk : IetfLang { override val code: String = "kum"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Kurdish : IetfLang { override val code: String = "ku"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Kutenai : IetfLang { override val code: String = "kut"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Ladino : IetfLang { override val code: String = "lad"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Lahnda : IetfLang { override val code: String = "lah"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Lamba : IetfLang { override val code: String = "lam"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface Lao : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object LA : Lao { override val code: String = "lo-LA"; override val parentLang: Lao get() = Lao; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Lao {
            override val code: String = "lo"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object Latin : IetfLang { override val code: String = "la"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface Latvian : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object LV : Latvian { override val code: String = "lv-LV"; override val parentLang: Latvian get() = Latvian; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Latvian {
            override val code: String = "lv"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object Lezghian : IetfLang { override val code: String = "lez"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object LimburganLimburgerLimburgish : IetfLang { override val code: String = "li"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface Lingala : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object AO : Lingala { override val code: String = "ln-AO"; override val parentLang: Lingala get() = Lingala; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object CD : Lingala { override val code: String = "ln-CD"; override val parentLang: Lingala get() = Lingala; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object CF : Lingala { override val code: String = "ln-CF"; override val parentLang: Lingala get() = Lingala; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object CG : Lingala { override val code: String = "ln-CG"; override val parentLang: Lingala get() = Lingala; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Lingala {
            override val code: String = "ln"
            override fun toString() = code
        }
    }


    @Serializable(IetfLangSerializer::class)
    sealed interface Lithuanian : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object LT : Lithuanian { override val code: String = "lt-LT"; override val parentLang: Lithuanian get() = Lithuanian; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Lithuanian {
            override val code: String = "lt"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object Mongo : IetfLang { override val code: String = "lol"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Lozi : IetfLang { override val code: String = "loz"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface LuxembourgishLetzeburgesch : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object LU : LuxembourgishLetzeburgesch { override val code: String = "lb-LU"; override val parentLang: LuxembourgishLetzeburgesch get() = LuxembourgishLetzeburgesch; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : LuxembourgishLetzeburgesch {
            override val code: String = "lb"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object LubaLulua : IetfLang { override val code: String = "lua"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface LubaKatanga : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object CD : LubaKatanga { override val code: String = "lu-CD"; override val parentLang: LubaKatanga get() = LubaKatanga; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : LubaKatanga {
            override val code: String = "lu"
            override fun toString() = code
        }
    }


    @Serializable(IetfLangSerializer::class)
    sealed interface Ganda : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object UG : Ganda { override val code: String = "lg-UG"; override val parentLang: Ganda get() = Ganda; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Ganda {
            override val code: String = "lg"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object Luiseno : IetfLang { override val code: String = "lui"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Lunda : IetfLang { override val code: String = "lun"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface LuoKenyaAndTanzania : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object KE : LuoKenyaAndTanzania { override val code: String = "luo-KE"; override val parentLang: LuoKenyaAndTanzania get() = LuoKenyaAndTanzania; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : LuoKenyaAndTanzania {
            override val code: String = "luo"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object Lushai : IetfLang { override val code: String = "lus"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface Macedonian : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object MK : Macedonian { override val code: String = "mk-MK"; override val parentLang: Macedonian get() = Macedonian; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Macedonian {
            override val code: String = "mk"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object Madurese : IetfLang { override val code: String = "mad"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Magahi : IetfLang { override val code: String = "mag"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Marshallese : IetfLang { override val code: String = "mh"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Maithili : IetfLang { override val code: String = "mai"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Makasar : IetfLang { override val code: String = "mak"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface Malayalam : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object IN : Malayalam { override val code: String = "ml-IN"; override val parentLang: Malayalam get() = Malayalam; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Malayalam {
            override val code: String = "ml"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object Mandingo : IetfLang { override val code: String = "man"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Maori : IetfLang { override val code: String = "mi"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object AustronesianLanguages : IetfLang { override val code: String = "map"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface Marathi : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object IN : Marathi { override val code: String = "mr-IN"; override val parentLang: Marathi get() = Marathi; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Marathi {
            override val code: String = "mr"
            override fun toString() = code
        }
    }


    @Serializable(IetfLangSerializer::class)
    sealed interface Masai : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object KE : Masai { override val code: String = "mas-KE"; override val parentLang: Masai get() = Masai; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object TZ : Masai { override val code: String = "mas-TZ"; override val parentLang: Masai get() = Masai; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Masai {
            override val code: String = "mas"
            override fun toString() = code
        }
    }


    @Serializable(IetfLangSerializer::class)
    sealed interface Malay : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object BN : Malay { override val code: String = "ms-BN"; override val parentLang: Malay get() = Malay; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object MY : Malay { override val code: String = "ms-MY"; override val parentLang: Malay get() = Malay; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object SG : Malay { override val code: String = "ms-SG"; override val parentLang: Malay get() = Malay; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Malay {
            override val code: String = "ms"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object Moksha : IetfLang { override val code: String = "mdf"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Mandar : IetfLang { override val code: String = "mdr"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Mende : IetfLang { override val code: String = "men"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object IrishMiddle9001200 : IetfLang { override val code: String = "mga"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Mi_kmaqMicmac : IetfLang { override val code: String = "mic"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Minangkabau : IetfLang { override val code: String = "min"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object UncodedLanguages : IetfLang { override val code: String = "mis"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object MonKhmerLanguages : IetfLang { override val code: String = "mkh"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface Malagasy : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object MG : Malagasy { override val code: String = "mg-MG"; override val parentLang: Malagasy get() = Malagasy; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Malagasy {
            override val code: String = "mg"
            override fun toString() = code
        }
    }


    @Serializable(IetfLangSerializer::class)
    sealed interface Maltese : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object MT : Maltese { override val code: String = "mt-MT"; override val parentLang: Maltese get() = Maltese; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Maltese {
            override val code: String = "mt"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object Manchu : IetfLang { override val code: String = "mnc"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Manipuri : IetfLang { override val code: String = "mni"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object ManoboLanguages : IetfLang { override val code: String = "mno"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Mohawk : IetfLang { override val code: String = "moh"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface Mongolian : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object MN : Mongolian { override val code: String = "mn-MN"; override val parentLang: Mongolian get() = Mongolian; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Mongolian {
            override val code: String = "mn"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object Mossi : IetfLang { override val code: String = "mos"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object MultipleLanguages : IetfLang { override val code: String = "mul"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object MundaLanguages : IetfLang { override val code: String = "mun"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Creek : IetfLang { override val code: String = "mus"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Mirandese : IetfLang { override val code: String = "mwl"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Marwari : IetfLang { override val code: String = "mwr"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object MayanLanguages : IetfLang { override val code: String = "myn"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Erzya : IetfLang { override val code: String = "myv"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object NahuatlLanguages : IetfLang { override val code: String = "nah"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object NorthAmericanIndianLanguages : IetfLang { override val code: String = "nai"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Neapolitan : IetfLang { override val code: String = "nap"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Nauru : IetfLang { override val code: String = "na"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object NavajoNavaho : IetfLang { override val code: String = "nv"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object NdebeleSouthSouthNdebele : IetfLang { override val code: String = "nr"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface NdebeleNorthNorthNdebele : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object ZW : NdebeleNorthNorthNdebele { override val code: String = "nd-ZW"; override val parentLang: NdebeleNorthNorthNdebele get() = NdebeleNorthNorthNdebele; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : NdebeleNorthNorthNdebele {
            override val code: String = "nd"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object Ndonga : IetfLang { override val code: String = "ng"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface LowGermanLowSaxonGermanLowSaxonLow : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object DE : LowGermanLowSaxonGermanLowSaxonLow { override val code: String = "nds-DE"; override val parentLang: LowGermanLowSaxonGermanLowSaxonLow get() = LowGermanLowSaxonGermanLowSaxonLow; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object NL : LowGermanLowSaxonGermanLowSaxonLow { override val code: String = "nds-NL"; override val parentLang: LowGermanLowSaxonGermanLowSaxonLow get() = LowGermanLowSaxonGermanLowSaxonLow; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : LowGermanLowSaxonGermanLowSaxonLow {
            override val code: String = "nds"
            override fun toString() = code
        }
    }


    @Serializable(IetfLangSerializer::class)
    sealed interface Nepali : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object IN : Nepali { override val code: String = "ne-IN"; override val parentLang: Nepali get() = Nepali; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object NP : Nepali { override val code: String = "ne-NP"; override val parentLang: Nepali get() = Nepali; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Nepali {
            override val code: String = "ne"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object NepalBhasaNewari : IetfLang { override val code: String = "new"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Nias : IetfLang { override val code: String = "nia"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object NigerKordofanianLanguages : IetfLang { override val code: String = "nic"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Niuean : IetfLang { override val code: String = "niu"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface NorwegianNynorskNynorskNorwegian : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object NO : NorwegianNynorskNynorskNorwegian { override val code: String = "nn-NO"; override val parentLang: NorwegianNynorskNynorskNorwegian get() = NorwegianNynorskNynorskNorwegian; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : NorwegianNynorskNynorskNorwegian {
            override val code: String = "nn"
            override fun toString() = code
        }
    }


    @Serializable(IetfLangSerializer::class)
    sealed interface BokmalNorwegianNorwegianBokmal : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object NO : BokmalNorwegianNorwegianBokmal { override val code: String = "nb-NO"; override val parentLang: BokmalNorwegianNorwegianBokmal get() = BokmalNorwegianNorwegianBokmal; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object SJ : BokmalNorwegianNorwegianBokmal { override val code: String = "nb-SJ"; override val parentLang: BokmalNorwegianNorwegianBokmal get() = BokmalNorwegianNorwegianBokmal; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : BokmalNorwegianNorwegianBokmal {
            override val code: String = "nb"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object Nogai : IetfLang { override val code: String = "nog"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object NorseOld : IetfLang { override val code: String = "non"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Norwegian : IetfLang { override val code: String = "no"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object N_Ko : IetfLang { override val code: String = "nqo"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object PediSepediNorthernSotho : IetfLang { override val code: String = "nso"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object NubianLanguages : IetfLang { override val code: String = "nub"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object ClassicalNewariOldNewariClassicalNepalBhasa : IetfLang { override val code: String = "nwc"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object ChichewaChewaNyanja : IetfLang { override val code: String = "ny"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Nyamwezi : IetfLang { override val code: String = "nym"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface Nyankole : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object UG : Nyankole { override val code: String = "nyn-UG"; override val parentLang: Nyankole get() = Nyankole; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Nyankole {
            override val code: String = "nyn"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object Nyoro : IetfLang { override val code: String = "nyo"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Nzima : IetfLang { override val code: String = "nzi"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object OccitanPost1500Provencal : IetfLang { override val code: String = "oc"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Ojibwa : IetfLang { override val code: String = "oj"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface Oriya : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object IN : Oriya { override val code: String = "or-IN"; override val parentLang: Oriya get() = Oriya; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Oriya {
            override val code: String = "or"
            override fun toString() = code
        }
    }


    @Serializable(IetfLangSerializer::class)
    sealed interface Oromo : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object ET : Oromo { override val code: String = "om-ET"; override val parentLang: Oromo get() = Oromo; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object KE : Oromo { override val code: String = "om-KE"; override val parentLang: Oromo get() = Oromo; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Oromo {
            override val code: String = "om"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object Osage : IetfLang { override val code: String = "osa"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface OssetianOssetic : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object GE : OssetianOssetic { override val code: String = "os-GE"; override val parentLang: OssetianOssetic get() = OssetianOssetic; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object RU : OssetianOssetic { override val code: String = "os-RU"; override val parentLang: OssetianOssetic get() = OssetianOssetic; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : OssetianOssetic {
            override val code: String = "os"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object TurkishOttoman15001928 : IetfLang { override val code: String = "ota"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object OtomianLanguages : IetfLang { override val code: String = "oto"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object PapuanLanguages : IetfLang { override val code: String = "paa"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Pangasinan : IetfLang { override val code: String = "pag"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Pahlavi : IetfLang { override val code: String = "pal"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object PampangaKapampangan : IetfLang { override val code: String = "pam"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface PanjabiPunjabi : IetfLang {


        @Serializable(IetfLangSerializer::class)
        sealed interface Arab : PanjabiPunjabi {

            @Serializable(IetfLangSerializer::class)
            object PK : Arab { override val code: String = "pa-Arab-PK"; override val parentLang: Arab get() = Arab; override fun toString() = code }

            @Serializable(IetfLangSerializer::class)
            companion object : Arab {
                override val code: String = "pa-Arab"
                override val parentLang: PanjabiPunjabi get() = PanjabiPunjabi;
                override fun toString() = code
            }
        }


        @Serializable(IetfLangSerializer::class)
        sealed interface Guru : PanjabiPunjabi {

            @Serializable(IetfLangSerializer::class)
            object IN : Guru { override val code: String = "pa-Guru-IN"; override val parentLang: Guru get() = Guru; override fun toString() = code }

            @Serializable(IetfLangSerializer::class)
            companion object : Guru {
                override val code: String = "pa-Guru"
                override val parentLang: PanjabiPunjabi get() = PanjabiPunjabi;
                override fun toString() = code
            }
        }


        @Serializable(IetfLangSerializer::class)
        companion object : PanjabiPunjabi {
            override val code: String = "pa"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object Papiamento : IetfLang { override val code: String = "pap"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Palauan : IetfLang { override val code: String = "pau"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object PersianOldCa_600400B_C_ : IetfLang { override val code: String = "peo"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface Persian : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object AF : Persian { override val code: String = "fa-AF"; override val parentLang: Persian get() = Persian; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object IR : Persian { override val code: String = "fa-IR"; override val parentLang: Persian get() = Persian; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Persian {
            override val code: String = "fa"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object PhilippineLanguages : IetfLang { override val code: String = "phi"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Phoenician : IetfLang { override val code: String = "phn"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Pali : IetfLang { override val code: String = "pi"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface Polish : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object PL : Polish { override val code: String = "pl-PL"; override val parentLang: Polish get() = Polish; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Polish {
            override val code: String = "pl"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object Pohnpeian : IetfLang { override val code: String = "pon"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface Portuguese : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object AO : Portuguese { override val code: String = "pt-AO"; override val parentLang: Portuguese get() = Portuguese; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object BR : Portuguese { override val code: String = "pt-BR"; override val parentLang: Portuguese get() = Portuguese; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object CH : Portuguese { override val code: String = "pt-CH"; override val parentLang: Portuguese get() = Portuguese; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object CV : Portuguese { override val code: String = "pt-CV"; override val parentLang: Portuguese get() = Portuguese; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object GQ : Portuguese { override val code: String = "pt-GQ"; override val parentLang: Portuguese get() = Portuguese; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object GW : Portuguese { override val code: String = "pt-GW"; override val parentLang: Portuguese get() = Portuguese; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object LU : Portuguese { override val code: String = "pt-LU"; override val parentLang: Portuguese get() = Portuguese; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object MO : Portuguese { override val code: String = "pt-MO"; override val parentLang: Portuguese get() = Portuguese; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object MZ : Portuguese { override val code: String = "pt-MZ"; override val parentLang: Portuguese get() = Portuguese; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object PT : Portuguese { override val code: String = "pt-PT"; override val parentLang: Portuguese get() = Portuguese; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object ST : Portuguese { override val code: String = "pt-ST"; override val parentLang: Portuguese get() = Portuguese; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object TL : Portuguese { override val code: String = "pt-TL"; override val parentLang: Portuguese get() = Portuguese; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Portuguese {
            override val code: String = "pt"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object PrakritLanguages : IetfLang { override val code: String = "pra"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object ProvencalOldTo1500 : IetfLang { override val code: String = "pro"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface PushtoPashto : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object AF : PushtoPashto { override val code: String = "ps-AF"; override val parentLang: PushtoPashto get() = PushtoPashto; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : PushtoPashto {
            override val code: String = "ps"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object ReservedForLocalUse : IetfLang { override val code: String = "qaa-qtz"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface Quechua : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object BO : Quechua { override val code: String = "qu-BO"; override val parentLang: Quechua get() = Quechua; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object EC : Quechua { override val code: String = "qu-EC"; override val parentLang: Quechua get() = Quechua; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object PE : Quechua { override val code: String = "qu-PE"; override val parentLang: Quechua get() = Quechua; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Quechua {
            override val code: String = "qu"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object Rajasthani : IetfLang { override val code: String = "raj"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Rapanui : IetfLang { override val code: String = "rap"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object RarotonganCookIslandsMaori : IetfLang { override val code: String = "rar"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object RomanceLanguages : IetfLang { override val code: String = "roa"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface Romansh : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object CH : Romansh { override val code: String = "rm-CH"; override val parentLang: Romansh get() = Romansh; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Romansh {
            override val code: String = "rm"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object Romany : IetfLang { override val code: String = "rom"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface RomanianMoldavianMoldovan : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object MD : RomanianMoldavianMoldovan { override val code: String = "ro-MD"; override val parentLang: RomanianMoldavianMoldovan get() = RomanianMoldavianMoldovan; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object RO : RomanianMoldavianMoldovan { override val code: String = "ro-RO"; override val parentLang: RomanianMoldavianMoldovan get() = RomanianMoldavianMoldovan; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : RomanianMoldavianMoldovan {
            override val code: String = "ro"
            override fun toString() = code
        }
    }


    @Serializable(IetfLangSerializer::class)
    sealed interface Rundi : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object BI : Rundi { override val code: String = "rn-BI"; override val parentLang: Rundi get() = Rundi; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Rundi {
            override val code: String = "rn"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object AromanianArumanianMacedoRomanian : IetfLang { override val code: String = "rup"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface Russian : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object BY : Russian { override val code: String = "ru-BY"; override val parentLang: Russian get() = Russian; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object KG : Russian { override val code: String = "ru-KG"; override val parentLang: Russian get() = Russian; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object KZ : Russian { override val code: String = "ru-KZ"; override val parentLang: Russian get() = Russian; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object MD : Russian { override val code: String = "ru-MD"; override val parentLang: Russian get() = Russian; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object RU : Russian { override val code: String = "ru-RU"; override val parentLang: Russian get() = Russian; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object UA : Russian { override val code: String = "ru-UA"; override val parentLang: Russian get() = Russian; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Russian {
            override val code: String = "ru"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object Sandawe : IetfLang { override val code: String = "sad"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface Sango : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object CF : Sango { override val code: String = "sg-CF"; override val parentLang: Sango get() = Sango; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Sango {
            override val code: String = "sg"
            override fun toString() = code
        }
    }


    @Serializable(IetfLangSerializer::class)
    sealed interface Yakut : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object RU : Yakut { override val code: String = "sah-RU"; override val parentLang: Yakut get() = Yakut; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Yakut {
            override val code: String = "sah"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object SouthAmericanIndianOther : IetfLang { override val code: String = "sai"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object SalishanLanguages : IetfLang { override val code: String = "sal"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object SamaritanAramaic : IetfLang { override val code: String = "sam"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Sanskrit : IetfLang { override val code: String = "sa"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Sasak : IetfLang { override val code: String = "sas"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Santali : IetfLang { override val code: String = "sat"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Sicilian : IetfLang { override val code: String = "scn"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Scots : IetfLang { override val code: String = "sco"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Selkup : IetfLang { override val code: String = "sel"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object SemiticLanguages : IetfLang { override val code: String = "sem"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object IrishOldTo900 : IetfLang { override val code: String = "sga"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object SignLanguages : IetfLang { override val code: String = "sgn"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Shan : IetfLang { override val code: String = "shn"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Sidamo : IetfLang { override val code: String = "sid"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface SinhalaSinhalese : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object LK : SinhalaSinhalese { override val code: String = "si-LK"; override val parentLang: SinhalaSinhalese get() = SinhalaSinhalese; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : SinhalaSinhalese {
            override val code: String = "si"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object SiouanLanguages : IetfLang { override val code: String = "sio"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object SinoTibetanLanguages : IetfLang { override val code: String = "sit"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object SlavicLanguages : IetfLang { override val code: String = "sla"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface Slovak : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object SK : Slovak { override val code: String = "sk-SK"; override val parentLang: Slovak get() = Slovak; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Slovak {
            override val code: String = "sk"
            override fun toString() = code
        }
    }


    @Serializable(IetfLangSerializer::class)
    sealed interface Slovenian : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object SI : Slovenian { override val code: String = "sl-SI"; override val parentLang: Slovenian get() = Slovenian; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Slovenian {
            override val code: String = "sl"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object SouthernSami : IetfLang { override val code: String = "sma"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface NorthernSami : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object FI : NorthernSami { override val code: String = "se-FI"; override val parentLang: NorthernSami get() = NorthernSami; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object NO : NorthernSami { override val code: String = "se-NO"; override val parentLang: NorthernSami get() = NorthernSami; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object SE : NorthernSami { override val code: String = "se-SE"; override val parentLang: NorthernSami get() = NorthernSami; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : NorthernSami {
            override val code: String = "se"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object SamiLanguages : IetfLang { override val code: String = "smi"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object LuleSami : IetfLang { override val code: String = "smj"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface InariSami : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object FI : InariSami { override val code: String = "smn-FI"; override val parentLang: InariSami get() = InariSami; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : InariSami {
            override val code: String = "smn"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object Samoan : IetfLang { override val code: String = "sm"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object SkoltSami : IetfLang { override val code: String = "sms"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface Shona : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object ZW : Shona { override val code: String = "sn-ZW"; override val parentLang: Shona get() = Shona; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Shona {
            override val code: String = "sn"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object Sindhi : IetfLang { override val code: String = "sd"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Soninke : IetfLang { override val code: String = "snk"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Sogdian : IetfLang { override val code: String = "sog"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface Somali : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object DJ : Somali { override val code: String = "so-DJ"; override val parentLang: Somali get() = Somali; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object ET : Somali { override val code: String = "so-ET"; override val parentLang: Somali get() = Somali; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object KE : Somali { override val code: String = "so-KE"; override val parentLang: Somali get() = Somali; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object SO : Somali { override val code: String = "so-SO"; override val parentLang: Somali get() = Somali; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Somali {
            override val code: String = "so"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object SonghaiLanguages : IetfLang { override val code: String = "son"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object SothoSouthern : IetfLang { override val code: String = "st"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface SpanishCastilian : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object L419 : SpanishCastilian { override val code: String = "es-419"; override val parentLang: SpanishCastilian get() = SpanishCastilian; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object AR : SpanishCastilian { override val code: String = "es-AR"; override val parentLang: SpanishCastilian get() = SpanishCastilian; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object BO : SpanishCastilian { override val code: String = "es-BO"; override val parentLang: SpanishCastilian get() = SpanishCastilian; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object BR : SpanishCastilian { override val code: String = "es-BR"; override val parentLang: SpanishCastilian get() = SpanishCastilian; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object BZ : SpanishCastilian { override val code: String = "es-BZ"; override val parentLang: SpanishCastilian get() = SpanishCastilian; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object CL : SpanishCastilian { override val code: String = "es-CL"; override val parentLang: SpanishCastilian get() = SpanishCastilian; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object CO : SpanishCastilian { override val code: String = "es-CO"; override val parentLang: SpanishCastilian get() = SpanishCastilian; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object CR : SpanishCastilian { override val code: String = "es-CR"; override val parentLang: SpanishCastilian get() = SpanishCastilian; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object CU : SpanishCastilian { override val code: String = "es-CU"; override val parentLang: SpanishCastilian get() = SpanishCastilian; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object DO : SpanishCastilian { override val code: String = "es-DO"; override val parentLang: SpanishCastilian get() = SpanishCastilian; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object EA : SpanishCastilian { override val code: String = "es-EA"; override val parentLang: SpanishCastilian get() = SpanishCastilian; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object EC : SpanishCastilian { override val code: String = "es-EC"; override val parentLang: SpanishCastilian get() = SpanishCastilian; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object ES : SpanishCastilian { override val code: String = "es-ES"; override val parentLang: SpanishCastilian get() = SpanishCastilian; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object GQ : SpanishCastilian { override val code: String = "es-GQ"; override val parentLang: SpanishCastilian get() = SpanishCastilian; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object GT : SpanishCastilian { override val code: String = "es-GT"; override val parentLang: SpanishCastilian get() = SpanishCastilian; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object HN : SpanishCastilian { override val code: String = "es-HN"; override val parentLang: SpanishCastilian get() = SpanishCastilian; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object IC : SpanishCastilian { override val code: String = "es-IC"; override val parentLang: SpanishCastilian get() = SpanishCastilian; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object MX : SpanishCastilian { override val code: String = "es-MX"; override val parentLang: SpanishCastilian get() = SpanishCastilian; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object NI : SpanishCastilian { override val code: String = "es-NI"; override val parentLang: SpanishCastilian get() = SpanishCastilian; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object PA : SpanishCastilian { override val code: String = "es-PA"; override val parentLang: SpanishCastilian get() = SpanishCastilian; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object PE : SpanishCastilian { override val code: String = "es-PE"; override val parentLang: SpanishCastilian get() = SpanishCastilian; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object PH : SpanishCastilian { override val code: String = "es-PH"; override val parentLang: SpanishCastilian get() = SpanishCastilian; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object PR : SpanishCastilian { override val code: String = "es-PR"; override val parentLang: SpanishCastilian get() = SpanishCastilian; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object PY : SpanishCastilian { override val code: String = "es-PY"; override val parentLang: SpanishCastilian get() = SpanishCastilian; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object SV : SpanishCastilian { override val code: String = "es-SV"; override val parentLang: SpanishCastilian get() = SpanishCastilian; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object US : SpanishCastilian { override val code: String = "es-US"; override val parentLang: SpanishCastilian get() = SpanishCastilian; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object UY : SpanishCastilian { override val code: String = "es-UY"; override val parentLang: SpanishCastilian get() = SpanishCastilian; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object VE : SpanishCastilian { override val code: String = "es-VE"; override val parentLang: SpanishCastilian get() = SpanishCastilian; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : SpanishCastilian {
            override val code: String = "es"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object Sardinian : IetfLang { override val code: String = "sc"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object SrananTongo : IetfLang { override val code: String = "srn"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface Serbian : IetfLang {


        @Serializable(IetfLangSerializer::class)
        sealed interface Cyrl : Serbian {

            @Serializable(IetfLangSerializer::class)
            object BA : Cyrl { override val code: String = "sr-Cyrl-BA"; override val parentLang: Cyrl get() = Cyrl; override fun toString() = code }
            @Serializable(IetfLangSerializer::class)
            object ME : Cyrl { override val code: String = "sr-Cyrl-ME"; override val parentLang: Cyrl get() = Cyrl; override fun toString() = code }
            @Serializable(IetfLangSerializer::class)
            object RS : Cyrl { override val code: String = "sr-Cyrl-RS"; override val parentLang: Cyrl get() = Cyrl; override fun toString() = code }
            @Serializable(IetfLangSerializer::class)
            object XK : Cyrl { override val code: String = "sr-Cyrl-XK"; override val parentLang: Cyrl get() = Cyrl; override fun toString() = code }

            @Serializable(IetfLangSerializer::class)
            companion object : Cyrl {
                override val code: String = "sr-Cyrl"
                override val parentLang: Serbian get() = Serbian;
                override fun toString() = code
            }
        }


        @Serializable(IetfLangSerializer::class)
        sealed interface Latn : Serbian {

            @Serializable(IetfLangSerializer::class)
            object BA : Latn { override val code: String = "sr-Latn-BA"; override val parentLang: Latn get() = Latn; override fun toString() = code }
            @Serializable(IetfLangSerializer::class)
            object ME : Latn { override val code: String = "sr-Latn-ME"; override val parentLang: Latn get() = Latn; override fun toString() = code }
            @Serializable(IetfLangSerializer::class)
            object RS : Latn { override val code: String = "sr-Latn-RS"; override val parentLang: Latn get() = Latn; override fun toString() = code }
            @Serializable(IetfLangSerializer::class)
            object XK : Latn { override val code: String = "sr-Latn-XK"; override val parentLang: Latn get() = Latn; override fun toString() = code }

            @Serializable(IetfLangSerializer::class)
            companion object : Latn {
                override val code: String = "sr-Latn"
                override val parentLang: Serbian get() = Serbian;
                override fun toString() = code
            }
        }


        @Serializable(IetfLangSerializer::class)
        companion object : Serbian {
            override val code: String = "sr"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object Serer : IetfLang { override val code: String = "srr"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object NiloSaharanLanguages : IetfLang { override val code: String = "ssa"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Swati : IetfLang { override val code: String = "ss"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Sukuma : IetfLang { override val code: String = "suk"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Sundanese : IetfLang { override val code: String = "su"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Susu : IetfLang { override val code: String = "sus"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Sumerian : IetfLang { override val code: String = "sux"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface Swahili : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object CD : Swahili { override val code: String = "sw-CD"; override val parentLang: Swahili get() = Swahili; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object KE : Swahili { override val code: String = "sw-KE"; override val parentLang: Swahili get() = Swahili; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object TZ : Swahili { override val code: String = "sw-TZ"; override val parentLang: Swahili get() = Swahili; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object UG : Swahili { override val code: String = "sw-UG"; override val parentLang: Swahili get() = Swahili; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Swahili {
            override val code: String = "sw"
            override fun toString() = code
        }
    }


    @Serializable(IetfLangSerializer::class)
    sealed interface Swedish : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object AX : Swedish { override val code: String = "sv-AX"; override val parentLang: Swedish get() = Swedish; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object FI : Swedish { override val code: String = "sv-FI"; override val parentLang: Swedish get() = Swedish; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object SE : Swedish { override val code: String = "sv-SE"; override val parentLang: Swedish get() = Swedish; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Swedish {
            override val code: String = "sv"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object ClassicalSyriac : IetfLang { override val code: String = "syc"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Syriac : IetfLang { override val code: String = "syr"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Tahitian : IetfLang { override val code: String = "ty"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object TaiLanguages : IetfLang { override val code: String = "tai"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface Tamil : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object IN : Tamil { override val code: String = "ta-IN"; override val parentLang: Tamil get() = Tamil; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object LK : Tamil { override val code: String = "ta-LK"; override val parentLang: Tamil get() = Tamil; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object MY : Tamil { override val code: String = "ta-MY"; override val parentLang: Tamil get() = Tamil; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object SG : Tamil { override val code: String = "ta-SG"; override val parentLang: Tamil get() = Tamil; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Tamil {
            override val code: String = "ta"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object Tatar : IetfLang { override val code: String = "tt"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface Telugu : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object IN : Telugu { override val code: String = "te-IN"; override val parentLang: Telugu get() = Telugu; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Telugu {
            override val code: String = "te"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object Timne : IetfLang { override val code: String = "tem"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Tereno : IetfLang { override val code: String = "ter"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Tetum : IetfLang { override val code: String = "tet"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Tajik : IetfLang { override val code: String = "tg"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Tagalog : IetfLang { override val code: String = "tl"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface Thai : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object TH : Thai { override val code: String = "th-TH"; override val parentLang: Thai get() = Thai; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Thai {
            override val code: String = "th"
            override fun toString() = code
        }
    }


    @Serializable(IetfLangSerializer::class)
    sealed interface Tibetan : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object CN : Tibetan { override val code: String = "bo-CN"; override val parentLang: Tibetan get() = Tibetan; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object IN : Tibetan { override val code: String = "bo-IN"; override val parentLang: Tibetan get() = Tibetan; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Tibetan {
            override val code: String = "bo"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object Tigre : IetfLang { override val code: String = "tig"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface Tigrinya : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object ER : Tigrinya { override val code: String = "ti-ER"; override val parentLang: Tigrinya get() = Tigrinya; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object ET : Tigrinya { override val code: String = "ti-ET"; override val parentLang: Tigrinya get() = Tigrinya; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Tigrinya {
            override val code: String = "ti"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object Tiv : IetfLang { override val code: String = "tiv"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Tokelau : IetfLang { override val code: String = "tkl"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object KlingonTlhInganHol : IetfLang { override val code: String = "tlh"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Tlingit : IetfLang { override val code: String = "tli"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Tamashek : IetfLang { override val code: String = "tmh"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object TongaNyasa : IetfLang { override val code: String = "tog"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface TongaTongaIslands : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object TO : TongaTongaIslands { override val code: String = "to-TO"; override val parentLang: TongaTongaIslands get() = TongaTongaIslands; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : TongaTongaIslands {
            override val code: String = "to"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object TokPisin : IetfLang { override val code: String = "tpi"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Tsimshian : IetfLang { override val code: String = "tsi"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Tswana : IetfLang { override val code: String = "tn"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Tsonga : IetfLang { override val code: String = "ts"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface Turkmen : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object TM : Turkmen { override val code: String = "tk-TM"; override val parentLang: Turkmen get() = Turkmen; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Turkmen {
            override val code: String = "tk"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object Tumbuka : IetfLang { override val code: String = "tum"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object TupiLanguages : IetfLang { override val code: String = "tup"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface Turkish : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object CY : Turkish { override val code: String = "tr-CY"; override val parentLang: Turkish get() = Turkish; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object TR : Turkish { override val code: String = "tr-TR"; override val parentLang: Turkish get() = Turkish; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Turkish {
            override val code: String = "tr"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object AltaicLanguages : IetfLang { override val code: String = "tut"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Tuvalu : IetfLang { override val code: String = "tvl"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Twi : IetfLang { override val code: String = "tw"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Tuvinian : IetfLang { override val code: String = "tyv"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Udmurt : IetfLang { override val code: String = "udm"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Ugaritic : IetfLang { override val code: String = "uga"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface UighurUyghur : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object CN : UighurUyghur { override val code: String = "ug-CN"; override val parentLang: UighurUyghur get() = UighurUyghur; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : UighurUyghur {
            override val code: String = "ug"
            override fun toString() = code
        }
    }


    @Serializable(IetfLangSerializer::class)
    sealed interface Ukrainian : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object UA : Ukrainian { override val code: String = "uk-UA"; override val parentLang: Ukrainian get() = Ukrainian; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Ukrainian {
            override val code: String = "uk"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object Umbundu : IetfLang { override val code: String = "umb"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Undetermined : IetfLang { override val code: String = "und"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface Urdu : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object IN : Urdu { override val code: String = "ur-IN"; override val parentLang: Urdu get() = Urdu; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object PK : Urdu { override val code: String = "ur-PK"; override val parentLang: Urdu get() = Urdu; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Urdu {
            override val code: String = "ur"
            override fun toString() = code
        }
    }


    @Serializable(IetfLangSerializer::class)
    sealed interface Uzbek : IetfLang {


        @Serializable(IetfLangSerializer::class)
        sealed interface Arab : Uzbek {

            @Serializable(IetfLangSerializer::class)
            object AF : Arab { override val code: String = "uz-Arab-AF"; override val parentLang: Arab get() = Arab; override fun toString() = code }

            @Serializable(IetfLangSerializer::class)
            companion object : Arab {
                override val code: String = "uz-Arab"
                override val parentLang: Uzbek get() = Uzbek;
                override fun toString() = code
            }
        }


        @Serializable(IetfLangSerializer::class)
        sealed interface Cyrl : Uzbek {

            @Serializable(IetfLangSerializer::class)
            object UZ : Cyrl { override val code: String = "uz-Cyrl-UZ"; override val parentLang: Cyrl get() = Cyrl; override fun toString() = code }

            @Serializable(IetfLangSerializer::class)
            companion object : Cyrl {
                override val code: String = "uz-Cyrl"
                override val parentLang: Uzbek get() = Uzbek;
                override fun toString() = code
            }
        }


        @Serializable(IetfLangSerializer::class)
        sealed interface Latn : Uzbek {

            @Serializable(IetfLangSerializer::class)
            object UZ : Latn { override val code: String = "uz-Latn-UZ"; override val parentLang: Latn get() = Latn; override fun toString() = code }

            @Serializable(IetfLangSerializer::class)
            companion object : Latn {
                override val code: String = "uz-Latn"
                override val parentLang: Uzbek get() = Uzbek;
                override fun toString() = code
            }
        }


        @Serializable(IetfLangSerializer::class)
        companion object : Uzbek {
            override val code: String = "uz"
            override fun toString() = code
        }
    }


    @Serializable(IetfLangSerializer::class)
    sealed interface Vai : IetfLang {


        @Serializable(IetfLangSerializer::class)
        sealed interface Latn : Vai {

            @Serializable(IetfLangSerializer::class)
            object LR : Latn { override val code: String = "vai-Latn-LR"; override val parentLang: Latn get() = Latn; override fun toString() = code }

            @Serializable(IetfLangSerializer::class)
            companion object : Latn {
                override val code: String = "vai-Latn"
                override val parentLang: Vai get() = Vai;
                override fun toString() = code
            }
        }


        @Serializable(IetfLangSerializer::class)
        sealed interface Vaii : Vai {

            @Serializable(IetfLangSerializer::class)
            object LR : Vaii { override val code: String = "vai-Vaii-LR"; override val parentLang: Vaii get() = Vaii; override fun toString() = code }

            @Serializable(IetfLangSerializer::class)
            companion object : Vaii {
                override val code: String = "vai-Vaii"
                override val parentLang: Vai get() = Vai;
                override fun toString() = code
            }
        }


        @Serializable(IetfLangSerializer::class)
        companion object : Vai {
            override val code: String = "vai"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object Venda : IetfLang { override val code: String = "ve"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface Vietnamese : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object VN : Vietnamese { override val code: String = "vi-VN"; override val parentLang: Vietnamese get() = Vietnamese; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Vietnamese {
            override val code: String = "vi"
            override fun toString() = code
        }
    }


    @Serializable(IetfLangSerializer::class)
    sealed interface Volapuk : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object L001 : Volapuk { override val code: String = "vo-001"; override val parentLang: Volapuk get() = Volapuk; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Volapuk {
            override val code: String = "vo"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object Votic : IetfLang { override val code: String = "vot"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object WakashanLanguages : IetfLang { override val code: String = "wak"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Walamo : IetfLang { override val code: String = "wal"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Waray : IetfLang { override val code: String = "war"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Washo : IetfLang { override val code: String = "was"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface Welsh : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object GB : Welsh { override val code: String = "cy-GB"; override val parentLang: Welsh get() = Welsh; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Welsh {
            override val code: String = "cy"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object SorbianLanguages : IetfLang { override val code: String = "wen"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Walloon : IetfLang { override val code: String = "wa"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Wolof : IetfLang { override val code: String = "wo"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object KalmykOirat : IetfLang { override val code: String = "xal"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Xhosa : IetfLang { override val code: String = "xh"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Yao : IetfLang { override val code: String = "yao"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Yapese : IetfLang { override val code: String = "yap"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface Yiddish : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object L001 : Yiddish { override val code: String = "yi-001"; override val parentLang: Yiddish get() = Yiddish; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Yiddish {
            override val code: String = "yi"
            override fun toString() = code
        }
    }


    @Serializable(IetfLangSerializer::class)
    sealed interface Yoruba : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object BJ : Yoruba { override val code: String = "yo-BJ"; override val parentLang: Yoruba get() = Yoruba; override fun toString() = code }
        @Serializable(IetfLangSerializer::class)
        object NG : Yoruba { override val code: String = "yo-NG"; override val parentLang: Yoruba get() = Yoruba; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Yoruba {
            override val code: String = "yo"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object YupikLanguages : IetfLang { override val code: String = "ypk"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Zapotec : IetfLang { override val code: String = "zap"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object BlissymbolsBlissymbolicsBliss : IetfLang { override val code: String = "zbl"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object Zenaga : IetfLang { override val code: String = "zen"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface StandardMoroccanTamazight : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object MA : StandardMoroccanTamazight { override val code: String = "zgh-MA"; override val parentLang: StandardMoroccanTamazight get() = StandardMoroccanTamazight; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : StandardMoroccanTamazight {
            override val code: String = "zgh"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object ZhuangChuang : IetfLang { override val code: String = "za"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object ZandeLanguages : IetfLang { override val code: String = "znd"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    sealed interface Zulu : IetfLang {

        @Serializable(IetfLangSerializer::class)
        object ZA : Zulu { override val code: String = "zu-ZA"; override val parentLang: Zulu get() = Zulu; override fun toString() = code }

        @Serializable(IetfLangSerializer::class)
        companion object : Zulu {
            override val code: String = "zu"
            override fun toString() = code
        }
    }

    @Serializable(IetfLangSerializer::class)
    object Zuni : IetfLang { override val code: String = "zun"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object NoLinguisticContentNotApplicable : IetfLang { override val code: String = "zxx"; override fun toString() = code }
    @Serializable(IetfLangSerializer::class)
    object ZazaDimiliDimliKirdkiKirmanjkiZazaki : IetfLang { override val code: String = "zza"; override fun toString() = code }

    @Serializable(IetfLangSerializer::class)
    data class UnknownIetfLang (override val code: String) : IetfLang {
        override val parentLang = code.dropLastWhile { it != '-' }.removeSuffix("-").takeIf { it.length > 0 } ?.let(::UnknownIetfLang)
    }
    @Deprecated("Renamed", ReplaceWith("IetfLang.UnknownIetfLang", "dev.inmo.micro_utils.language_codes.IetfLang.UnknownIetfLang"))
    val UnknownIetfLanguageCode
        get() = UnknownIetfLang
}
@Deprecated("Renamed", ReplaceWith("IetfLang", "dev.inmo.micro_utils.language_codes.IetfLang"))
typealias IetfLanguageCode = IetfLang