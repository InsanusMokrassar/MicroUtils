package dev.inmo.micro_utils.mime_types

import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@JsExport
@Serializable(MimeTypeSerializer::class)
interface MimeType {
    val raw: String
}
@JsExport
data class CustomMimeType(override val raw: String) : MimeType

@JsExport
@Serializable(MimeTypeSerializer::class)
sealed class KnownMimeTypes(override val raw: String) : MimeType {
    object Any : MimeType, KnownMimeTypes("*/*")
    @Serializable(MimeTypeSerializer::class)
    sealed class Application(raw: String) : MimeType, KnownMimeTypes(raw) {
        @Serializable(MimeTypeSerializer::class)
        object Any: Application ("application/*")
        @Serializable(MimeTypeSerializer::class)
        object AndrewInset: Application ("application/andrew-inset")
        @Serializable(MimeTypeSerializer::class)
        object Applixware: Application ("application/applixware")
        @Serializable(MimeTypeSerializer::class)
        object AtomXml: Application ("application/atom+xml")
        @Serializable(MimeTypeSerializer::class)
        object AtomcatXml: Application ("application/atomcat+xml")
        @Serializable(MimeTypeSerializer::class)
        object AtomsvcXml: Application ("application/atomsvc+xml")
        @Serializable(MimeTypeSerializer::class)
        object CcxmlXml: Application ("application/ccxml+xml,")
        @Serializable(MimeTypeSerializer::class)
        object CdmiCapability: Application ("application/cdmi-capability")
        @Serializable(MimeTypeSerializer::class)
        object CdmiContainer: Application ("application/cdmi-container")
        @Serializable(MimeTypeSerializer::class)
        object CdmiDomain: Application ("application/cdmi-domain")
        @Serializable(MimeTypeSerializer::class)
        object CdmiObject: Application ("application/cdmi-object")
        @Serializable(MimeTypeSerializer::class)
        object CdmiQueue: Application ("application/cdmi-queue")
        @Serializable(MimeTypeSerializer::class)
        object CuSeeme: Application ("application/cu-seeme")
        @Serializable(MimeTypeSerializer::class)
        object DavmountXml: Application ("application/davmount+xml")
        @Serializable(MimeTypeSerializer::class)
        object DsscDer: Application ("application/dssc+der")
        @Serializable(MimeTypeSerializer::class)
        object DsscXml: Application ("application/dssc+xml")
        @Serializable(MimeTypeSerializer::class)
        object Ecmascript: Application ("application/ecmascript")
        @Serializable(MimeTypeSerializer::class)
        object EmmaXml: Application ("application/emma+xml")
        @Serializable(MimeTypeSerializer::class)
        object EpubZip: Application ("application/epub+zip")
        @Serializable(MimeTypeSerializer::class)
        object Exi: Application ("application/exi")
        @Serializable(MimeTypeSerializer::class)
        object FontTdpfr: Application ("application/font-tdpfr")
        @Serializable(MimeTypeSerializer::class)
        object Hyperstudio: Application ("application/hyperstudio")
        @Serializable(MimeTypeSerializer::class)
        object Ipfix: Application ("application/ipfix")
        @Serializable(MimeTypeSerializer::class)
        object JavaArchive: Application ("application/java-archive")
        @Serializable(MimeTypeSerializer::class)
        object JavaSerializedObject: Application ("application/java-serialized-object")
        @Serializable(MimeTypeSerializer::class)
        object JavaVm: Application ("application/java-vm")
        @Serializable(MimeTypeSerializer::class)
        object Javascript: Application ("application/javascript")
        @Serializable(MimeTypeSerializer::class)
        object Json: Application ("application/json")
        @Serializable(MimeTypeSerializer::class)
        object MacBinhex40: Application ("application/mac-binhex40")
        @Serializable(MimeTypeSerializer::class)
        object MacCompactpro: Application ("application/mac-compactpro")
        @Serializable(MimeTypeSerializer::class)
        object MadsXml: Application ("application/mads+xml")
        @Serializable(MimeTypeSerializer::class)
        object Marc: Application ("application/marc")
        @Serializable(MimeTypeSerializer::class)
        object MarcxmlXml: Application ("application/marcxml+xml")
        @Serializable(MimeTypeSerializer::class)
        object Mathematica: Application ("application/mathematica")
        @Serializable(MimeTypeSerializer::class)
        object MathmlXml: Application ("application/mathml+xml")
        @Serializable(MimeTypeSerializer::class)
        object Mbox: Application ("application/mbox")
        @Serializable(MimeTypeSerializer::class)
        object MediaservercontrolXml: Application ("application/mediaservercontrol+xml")
        @Serializable(MimeTypeSerializer::class)
        object Metalink4Xml: Application ("application/metalink4+xml")
        @Serializable(MimeTypeSerializer::class)
        object MetsXml: Application ("application/mets+xml")
        @Serializable(MimeTypeSerializer::class)
        object ModsXml: Application ("application/mods+xml")
        @Serializable(MimeTypeSerializer::class)
        object Mp21: Application ("application/mp21")
        @Serializable(MimeTypeSerializer::class)
        object Mp4: Application ("application/mp4")
        @Serializable(MimeTypeSerializer::class)
        object Msword: Application ("application/msword")
        @Serializable(MimeTypeSerializer::class)
        object Mxf: Application ("application/mxf")
        @Serializable(MimeTypeSerializer::class)
        object OctetStream: Application ("application/octet-stream")
        @Serializable(MimeTypeSerializer::class)
        object Oda: Application ("application/oda")
        @Serializable(MimeTypeSerializer::class)
        object OebpsPackageXml: Application ("application/oebps-package+xml")
        @Serializable(MimeTypeSerializer::class)
        object Ogg: Application ("application/ogg")
        @Serializable(MimeTypeSerializer::class)
        object Onenote: Application ("application/onenote")
        @Serializable(MimeTypeSerializer::class)
        object PatchOpsErrorXml: Application ("application/patch-ops-error+xml")
        @Serializable(MimeTypeSerializer::class)
        object Pdf: Application ("application/pdf")
        @Serializable(MimeTypeSerializer::class)
        object PgpEncrypted: Application ("application/pgp-encrypted")
        @Serializable(MimeTypeSerializer::class)
        object PgpSignature: Application ("application/pgp-signature")
        @Serializable(MimeTypeSerializer::class)
        object PicsRules: Application ("application/pics-rules")
        @Serializable(MimeTypeSerializer::class)
        object Pkcs10: Application ("application/pkcs10")
        @Serializable(MimeTypeSerializer::class)
        object Pkcs7Mime: Application ("application/pkcs7-mime")
        @Serializable(MimeTypeSerializer::class)
        object Pkcs7Signature: Application ("application/pkcs7-signature")
        @Serializable(MimeTypeSerializer::class)
        object Pkcs8: Application ("application/pkcs8")
        @Serializable(MimeTypeSerializer::class)
        object PkixAttrCert: Application ("application/pkix-attr-cert")
        @Serializable(MimeTypeSerializer::class)
        object PkixCert: Application ("application/pkix-cert")
        @Serializable(MimeTypeSerializer::class)
        object PkixCrl: Application ("application/pkix-crl")
        @Serializable(MimeTypeSerializer::class)
        object PkixPkipath: Application ("application/pkix-pkipath")
        @Serializable(MimeTypeSerializer::class)
        object Pkixcmp: Application ("application/pkixcmp")
        @Serializable(MimeTypeSerializer::class)
        object PlsXml: Application ("application/pls+xml")
        @Serializable(MimeTypeSerializer::class)
        object Postscript: Application ("application/postscript")
        @Serializable(MimeTypeSerializer::class)
        object PrsCww: Application ("application/prs.cww")
        @Serializable(MimeTypeSerializer::class)
        object PskcXml: Application ("application/pskc+xml")
        @Serializable(MimeTypeSerializer::class)
        object RdfXml: Application ("application/rdf+xml")
        @Serializable(MimeTypeSerializer::class)
        object ReginfoXml: Application ("application/reginfo+xml")
        @Serializable(MimeTypeSerializer::class)
        object RelaxNgCompactSyntax: Application ("application/relax-ng-compact-syntax")
        @Serializable(MimeTypeSerializer::class)
        object ResourceListsXml: Application ("application/resource-lists+xml")
        @Serializable(MimeTypeSerializer::class)
        object ResourceListsDiffXml: Application ("application/resource-lists-diff+xml")
        @Serializable(MimeTypeSerializer::class)
        object RlsServicesXml: Application ("application/rls-services+xml")
        @Serializable(MimeTypeSerializer::class)
        object RsdXml: Application ("application/rsd+xml")
        @Serializable(MimeTypeSerializer::class)
        object RssXml: Application ("application/rss+xml")
        @Serializable(MimeTypeSerializer::class)
        object Rtf: Application ("application/rtf")
        @Serializable(MimeTypeSerializer::class)
        object SbmlXml: Application ("application/sbml+xml")
        @Serializable(MimeTypeSerializer::class)
        object ScvpCvRequest: Application ("application/scvp-cv-request")
        @Serializable(MimeTypeSerializer::class)
        object ScvpCvResponse: Application ("application/scvp-cv-response")
        @Serializable(MimeTypeSerializer::class)
        object ScvpVpRequest: Application ("application/scvp-vp-request")
        @Serializable(MimeTypeSerializer::class)
        object ScvpVpResponse: Application ("application/scvp-vp-response")
        @Serializable(MimeTypeSerializer::class)
        object Sdp: Application ("application/sdp")
        @Serializable(MimeTypeSerializer::class)
        object SetPaymentInitiation: Application ("application/set-payment-initiation")
        @Serializable(MimeTypeSerializer::class)
        object SetRegistrationInitiation: Application ("application/set-registration-initiation")
        @Serializable(MimeTypeSerializer::class)
        object ShfXml: Application ("application/shf+xml")
        @Serializable(MimeTypeSerializer::class)
        object SmilXml: Application ("application/smil+xml")
        @Serializable(MimeTypeSerializer::class)
        object SparqlQuery: Application ("application/sparql-query")
        @Serializable(MimeTypeSerializer::class)
        object SparqlResultsXml: Application ("application/sparql-results+xml")
        @Serializable(MimeTypeSerializer::class)
        object Srgs: Application ("application/srgs")
        @Serializable(MimeTypeSerializer::class)
        object SrgsXml: Application ("application/srgs+xml")
        @Serializable(MimeTypeSerializer::class)
        object SruXml: Application ("application/sru+xml")
        @Serializable(MimeTypeSerializer::class)
        object SsmlXml: Application ("application/ssml+xml")
        @Serializable(MimeTypeSerializer::class)
        object TeiXml: Application ("application/tei+xml")
        @Serializable(MimeTypeSerializer::class)
        object ThraudXml: Application ("application/thraud+xml")
        @Serializable(MimeTypeSerializer::class)
        object TimestampedData: Application ("application/timestamped-data")
        @Serializable(MimeTypeSerializer::class)
        object Vnd3gppPicBwLarge: Application ("application/vnd.3gpp.pic-bw-large")
        @Serializable(MimeTypeSerializer::class)
        object Vnd3gppPicBwSmall: Application ("application/vnd.3gpp.pic-bw-small")
        @Serializable(MimeTypeSerializer::class)
        object Vnd3gppPicBwVar: Application ("application/vnd.3gpp.pic-bw-var")
        @Serializable(MimeTypeSerializer::class)
        object Vnd3gpp2Tcap: Application ("application/vnd.3gpp2.tcap")
        @Serializable(MimeTypeSerializer::class)
        object Vnd3mPostItNotes: Application ("application/vnd.3m.post-it-notes")
        @Serializable(MimeTypeSerializer::class)
        object VndAccpacSimplyAso: Application ("application/vnd.accpac.simply.aso")
        @Serializable(MimeTypeSerializer::class)
        object VndAccpacSimplyImp: Application ("application/vnd.accpac.simply.imp")
        @Serializable(MimeTypeSerializer::class)
        object VndAcucobol: Application ("application/vnd.acucobol")
        @Serializable(MimeTypeSerializer::class)
        object VndAcucorp: Application ("application/vnd.acucorp")
        @Serializable(MimeTypeSerializer::class)
        object VndAdobeAirApplicationInstallerPackageZip: Application ("application/vnd.adobe.air-application-installer-package+zip")
        @Serializable(MimeTypeSerializer::class)
        object VndAdobeFxp: Application ("application/vnd.adobe.fxp")
        @Serializable(MimeTypeSerializer::class)
        object VndAdobeXdpXml: Application ("application/vnd.adobe.xdp+xml")
        @Serializable(MimeTypeSerializer::class)
        object VndAdobeXfdf: Application ("application/vnd.adobe.xfdf")
        @Serializable(MimeTypeSerializer::class)
        object VndAheadSpace: Application ("application/vnd.ahead.space")
        @Serializable(MimeTypeSerializer::class)
        object VndAirzipFilesecureAzf: Application ("application/vnd.airzip.filesecure.azf")
        @Serializable(MimeTypeSerializer::class)
        object VndAirzipFilesecureAzs: Application ("application/vnd.airzip.filesecure.azs")
        @Serializable(MimeTypeSerializer::class)
        object VndAmazonEbook: Application ("application/vnd.amazon.ebook")
        @Serializable(MimeTypeSerializer::class)
        object VndAmericandynamicsAcc: Application ("application/vnd.americandynamics.acc")
        @Serializable(MimeTypeSerializer::class)
        object VndAmigaAmi: Application ("application/vnd.amiga.ami")
        @Serializable(MimeTypeSerializer::class)
        object VndAndroidPackageArchive: Application ("application/vnd.android.package-archive")
        @Serializable(MimeTypeSerializer::class)
        object VndAnserWebCertificateIssueInitiation: Application ("application/vnd.anser-web-certificate-issue-initiation")
        @Serializable(MimeTypeSerializer::class)
        object VndAnserWebFundsTransferInitiation: Application ("application/vnd.anser-web-funds-transfer-initiation")
        @Serializable(MimeTypeSerializer::class)
        object VndAntixGameComponent: Application ("application/vnd.antix.game-component")
        @Serializable(MimeTypeSerializer::class)
        object VndAppleInstallerXml: Application ("application/vnd.apple.installer+xml")
        @Serializable(MimeTypeSerializer::class)
        object VndAppleMpegurl: Application ("application/vnd.apple.mpegurl")
        @Serializable(MimeTypeSerializer::class)
        object VndAristanetworksSwi: Application ("application/vnd.aristanetworks.swi")
        @Serializable(MimeTypeSerializer::class)
        object VndAudiograph: Application ("application/vnd.audiograph")
        @Serializable(MimeTypeSerializer::class)
        object VndBlueiceMultipass: Application ("application/vnd.blueice.multipass")
        @Serializable(MimeTypeSerializer::class)
        object VndBmi: Application ("application/vnd.bmi")
        @Serializable(MimeTypeSerializer::class)
        object VndBusinessobjects: Application ("application/vnd.businessobjects")
        @Serializable(MimeTypeSerializer::class)
        object VndChemdrawXml: Application ("application/vnd.chemdraw+xml")
        @Serializable(MimeTypeSerializer::class)
        object VndChipnutsKaraokeMmd: Application ("application/vnd.chipnuts.karaoke-mmd")
        @Serializable(MimeTypeSerializer::class)
        object VndCinderella: Application ("application/vnd.cinderella")
        @Serializable(MimeTypeSerializer::class)
        object VndClaymore: Application ("application/vnd.claymore")
        @Serializable(MimeTypeSerializer::class)
        object VndCloantoRp9: Application ("application/vnd.cloanto.rp9")
        @Serializable(MimeTypeSerializer::class)
        object VndClonkC4group: Application ("application/vnd.clonk.c4group")
        @Serializable(MimeTypeSerializer::class)
        object VndCluetrustCartomobileConfig: Application ("application/vnd.cluetrust.cartomobile-config")
        @Serializable(MimeTypeSerializer::class)
        object VndCluetrustCartomobileConfigPkg: Application ("application/vnd.cluetrust.cartomobile-config-pkg")
        @Serializable(MimeTypeSerializer::class)
        object VndCommonspace: Application ("application/vnd.commonspace")
        @Serializable(MimeTypeSerializer::class)
        object VndContactCmsg: Application ("application/vnd.contact.cmsg")
        @Serializable(MimeTypeSerializer::class)
        object VndCosmocaller: Application ("application/vnd.cosmocaller")
        @Serializable(MimeTypeSerializer::class)
        object VndCrickClicker: Application ("application/vnd.crick.clicker")
        @Serializable(MimeTypeSerializer::class)
        object VndCrickClickerKeyboard: Application ("application/vnd.crick.clicker.keyboard")
        @Serializable(MimeTypeSerializer::class)
        object VndCrickClickerPalette: Application ("application/vnd.crick.clicker.palette")
        @Serializable(MimeTypeSerializer::class)
        object VndCrickClickerTemplate: Application ("application/vnd.crick.clicker.template")
        @Serializable(MimeTypeSerializer::class)
        object VndCrickClickerWordbank: Application ("application/vnd.crick.clicker.wordbank")
        @Serializable(MimeTypeSerializer::class)
        object VndCriticaltoolsWbsXml: Application ("application/vnd.criticaltools.wbs+xml")
        @Serializable(MimeTypeSerializer::class)
        object VndCtcPosml: Application ("application/vnd.ctc-posml")
        @Serializable(MimeTypeSerializer::class)
        object VndCupsPpd: Application ("application/vnd.cups-ppd")
        @Serializable(MimeTypeSerializer::class)
        object VndCurlCar: Application ("application/vnd.curl.car")
        @Serializable(MimeTypeSerializer::class)
        object VndCurlPcurl: Application ("application/vnd.curl.pcurl")
        @Serializable(MimeTypeSerializer::class)
        object VndDataVisionRdz: Application ("application/vnd.data-vision.rdz")
        @Serializable(MimeTypeSerializer::class)
        object VndDenovoFcselayoutLink: Application ("application/vnd.denovo.fcselayout-link")
        @Serializable(MimeTypeSerializer::class)
        object VndDna: Application ("application/vnd.dna")
        @Serializable(MimeTypeSerializer::class)
        object VndDolbyMlp: Application ("application/vnd.dolby.mlp")
        @Serializable(MimeTypeSerializer::class)
        object VndDpgraph: Application ("application/vnd.dpgraph")
        @Serializable(MimeTypeSerializer::class)
        object VndDreamfactory: Application ("application/vnd.dreamfactory")
        @Serializable(MimeTypeSerializer::class)
        object VndDvbAit: Application ("application/vnd.dvb.ait")
        @Serializable(MimeTypeSerializer::class)
        object VndDvbService: Application ("application/vnd.dvb.service")
        @Serializable(MimeTypeSerializer::class)
        object VndDynageo: Application ("application/vnd.dynageo")
        @Serializable(MimeTypeSerializer::class)
        object VndEcowinChart: Application ("application/vnd.ecowin.chart")
        @Serializable(MimeTypeSerializer::class)
        object VndEnliven: Application ("application/vnd.enliven")
        @Serializable(MimeTypeSerializer::class)
        object VndEpsonEsf: Application ("application/vnd.epson.esf")
        @Serializable(MimeTypeSerializer::class)
        object VndEpsonMsf: Application ("application/vnd.epson.msf")
        @Serializable(MimeTypeSerializer::class)
        object VndEpsonQuickanime: Application ("application/vnd.epson.quickanime")
        @Serializable(MimeTypeSerializer::class)
        object VndEpsonSalt: Application ("application/vnd.epson.salt")
        @Serializable(MimeTypeSerializer::class)
        object VndEpsonSsf: Application ("application/vnd.epson.ssf")
        @Serializable(MimeTypeSerializer::class)
        object VndEszigno3Xml: Application ("application/vnd.eszigno3+xml")
        @Serializable(MimeTypeSerializer::class)
        object VndEzpixAlbum: Application ("application/vnd.ezpix-album")
        @Serializable(MimeTypeSerializer::class)
        object VndEzpixPackage: Application ("application/vnd.ezpix-package")
        @Serializable(MimeTypeSerializer::class)
        object VndFdf: Application ("application/vnd.fdf")
        @Serializable(MimeTypeSerializer::class)
        object VndFdsnSeed: Application ("application/vnd.fdsn.seed")
        @Serializable(MimeTypeSerializer::class)
        object VndFlographit: Application ("application/vnd.flographit")
        @Serializable(MimeTypeSerializer::class)
        object VndFluxtimeClip: Application ("application/vnd.fluxtime.clip")
        @Serializable(MimeTypeSerializer::class)
        object VndFramemaker: Application ("application/vnd.framemaker")
        @Serializable(MimeTypeSerializer::class)
        object VndFrogansFnc: Application ("application/vnd.frogans.fnc")
        @Serializable(MimeTypeSerializer::class)
        object VndFrogansLtf: Application ("application/vnd.frogans.ltf")
        @Serializable(MimeTypeSerializer::class)
        object VndFscWeblaunch: Application ("application/vnd.fsc.weblaunch")
        @Serializable(MimeTypeSerializer::class)
        object VndFujitsuOasys: Application ("application/vnd.fujitsu.oasys")
        @Serializable(MimeTypeSerializer::class)
        object VndFujitsuOasys2: Application ("application/vnd.fujitsu.oasys2")
        @Serializable(MimeTypeSerializer::class)
        object VndFujitsuOasys3: Application ("application/vnd.fujitsu.oasys3")
        @Serializable(MimeTypeSerializer::class)
        object VndFujitsuOasysgp: Application ("application/vnd.fujitsu.oasysgp")
        @Serializable(MimeTypeSerializer::class)
        object VndFujitsuOasysprs: Application ("application/vnd.fujitsu.oasysprs")
        @Serializable(MimeTypeSerializer::class)
        object VndFujixeroxDdd: Application ("application/vnd.fujixerox.ddd")
        @Serializable(MimeTypeSerializer::class)
        object VndFujixeroxDocuworks: Application ("application/vnd.fujixerox.docuworks")
        @Serializable(MimeTypeSerializer::class)
        object VndFujixeroxDocuworksBinder: Application ("application/vnd.fujixerox.docuworks.binder")
        @Serializable(MimeTypeSerializer::class)
        object VndFuzzysheet: Application ("application/vnd.fuzzysheet")
        @Serializable(MimeTypeSerializer::class)
        object VndGenomatixTuxedo: Application ("application/vnd.genomatix.tuxedo")
        @Serializable(MimeTypeSerializer::class)
        object VndGeogebraFile: Application ("application/vnd.geogebra.file")
        @Serializable(MimeTypeSerializer::class)
        object VndGeogebraTool: Application ("application/vnd.geogebra.tool")
        @Serializable(MimeTypeSerializer::class)
        object VndGeometryExplorer: Application ("application/vnd.geometry-explorer")
        @Serializable(MimeTypeSerializer::class)
        object VndGeonext: Application ("application/vnd.geonext")
        @Serializable(MimeTypeSerializer::class)
        object VndGeoplan: Application ("application/vnd.geoplan")
        @Serializable(MimeTypeSerializer::class)
        object VndGeospace: Application ("application/vnd.geospace")
        @Serializable(MimeTypeSerializer::class)
        object VndGmx: Application ("application/vnd.gmx")
        @Serializable(MimeTypeSerializer::class)
        object VndGoogleEarthKmlXml: Application ("application/vnd.google-earth.kml+xml")
        @Serializable(MimeTypeSerializer::class)
        object VndGoogleEarthKmz: Application ("application/vnd.google-earth.kmz")
        @Serializable(MimeTypeSerializer::class)
        object VndGrafeq: Application ("application/vnd.grafeq")
        @Serializable(MimeTypeSerializer::class)
        object VndGrooveAccount: Application ("application/vnd.groove-account")
        @Serializable(MimeTypeSerializer::class)
        object VndGrooveHelp: Application ("application/vnd.groove-help")
        @Serializable(MimeTypeSerializer::class)
        object VndGrooveIdentityMessage: Application ("application/vnd.groove-identity-message")
        @Serializable(MimeTypeSerializer::class)
        object VndGrooveInjector: Application ("application/vnd.groove-injector")
        @Serializable(MimeTypeSerializer::class)
        object VndGrooveToolMessage: Application ("application/vnd.groove-tool-message")
        @Serializable(MimeTypeSerializer::class)
        object VndGrooveToolTemplate: Application ("application/vnd.groove-tool-template")
        @Serializable(MimeTypeSerializer::class)
        object VndGrooveVcard: Application ("application/vnd.groove-vcard")
        @Serializable(MimeTypeSerializer::class)
        object VndHalXml: Application ("application/vnd.hal+xml")
        @Serializable(MimeTypeSerializer::class)
        object VndHandheldEntertainmentXml: Application ("application/vnd.handheld-entertainment+xml")
        @Serializable(MimeTypeSerializer::class)
        object VndHbci: Application ("application/vnd.hbci")
        @Serializable(MimeTypeSerializer::class)
        object VndHheLessonPlayer: Application ("application/vnd.hhe.lesson-player")
        @Serializable(MimeTypeSerializer::class)
        object VndHpHpgl: Application ("application/vnd.hp-hpgl")
        @Serializable(MimeTypeSerializer::class)
        object VndHpHpid: Application ("application/vnd.hp-hpid")
        @Serializable(MimeTypeSerializer::class)
        object VndHpHps: Application ("application/vnd.hp-hps")
        @Serializable(MimeTypeSerializer::class)
        object VndHpJlyt: Application ("application/vnd.hp-jlyt")
        @Serializable(MimeTypeSerializer::class)
        object VndHpPcl: Application ("application/vnd.hp-pcl")
        @Serializable(MimeTypeSerializer::class)
        object VndHpPclxl: Application ("application/vnd.hp-pclxl")
        @Serializable(MimeTypeSerializer::class)
        object VndHydrostatixSofData: Application ("application/vnd.hydrostatix.sof-data")
        @Serializable(MimeTypeSerializer::class)
        object VndHzn3dCrossword: Application ("application/vnd.hzn-3d-crossword")
        @Serializable(MimeTypeSerializer::class)
        object VndIbmMinipay: Application ("application/vnd.ibm.minipay")
        @Serializable(MimeTypeSerializer::class)
        object VndIbmModcap: Application ("application/vnd.ibm.modcap")
        @Serializable(MimeTypeSerializer::class)
        object VndIbmRightsManagement: Application ("application/vnd.ibm.rights-management")
        @Serializable(MimeTypeSerializer::class)
        object VndIbmSecureContainer: Application ("application/vnd.ibm.secure-container")
        @Serializable(MimeTypeSerializer::class)
        object VndIccprofile: Application ("application/vnd.iccprofile")
        @Serializable(MimeTypeSerializer::class)
        object VndIgloader: Application ("application/vnd.igloader")
        @Serializable(MimeTypeSerializer::class)
        object VndImmervisionIvp: Application ("application/vnd.immervision-ivp")
        @Serializable(MimeTypeSerializer::class)
        object VndImmervisionIvu: Application ("application/vnd.immervision-ivu")
        @Serializable(MimeTypeSerializer::class)
        object VndInsorsIgm: Application ("application/vnd.insors.igm")
        @Serializable(MimeTypeSerializer::class)
        object VndInterconFormnet: Application ("application/vnd.intercon.formnet")
        @Serializable(MimeTypeSerializer::class)
        object VndIntergeo: Application ("application/vnd.intergeo")
        @Serializable(MimeTypeSerializer::class)
        object VndIntuQbo: Application ("application/vnd.intu.qbo")
        @Serializable(MimeTypeSerializer::class)
        object VndIntuQfx: Application ("application/vnd.intu.qfx")
        @Serializable(MimeTypeSerializer::class)
        object VndIpunpluggedRcprofile: Application ("application/vnd.ipunplugged.rcprofile")
        @Serializable(MimeTypeSerializer::class)
        object VndIrepositoryPackageXml: Application ("application/vnd.irepository.package+xml")
        @Serializable(MimeTypeSerializer::class)
        object VndIsXpr: Application ("application/vnd.is-xpr")
        @Serializable(MimeTypeSerializer::class)
        object VndIsacFcs: Application ("application/vnd.isac.fcs")
        @Serializable(MimeTypeSerializer::class)
        object VndJam: Application ("application/vnd.jam")
        @Serializable(MimeTypeSerializer::class)
        object VndJcpJavameMidletRms: Application ("application/vnd.jcp.javame.midlet-rms")
        @Serializable(MimeTypeSerializer::class)
        object VndJisp: Application ("application/vnd.jisp")
        @Serializable(MimeTypeSerializer::class)
        object VndJoostJodaArchive: Application ("application/vnd.joost.joda-archive")
        @Serializable(MimeTypeSerializer::class)
        object VndKahootz: Application ("application/vnd.kahootz")
        @Serializable(MimeTypeSerializer::class)
        object VndKdeKarbon: Application ("application/vnd.kde.karbon")
        @Serializable(MimeTypeSerializer::class)
        object VndKdeKchart: Application ("application/vnd.kde.kchart")
        @Serializable(MimeTypeSerializer::class)
        object VndKdeKformula: Application ("application/vnd.kde.kformula")
        @Serializable(MimeTypeSerializer::class)
        object VndKdeKivio: Application ("application/vnd.kde.kivio")
        @Serializable(MimeTypeSerializer::class)
        object VndKdeKontour: Application ("application/vnd.kde.kontour")
        @Serializable(MimeTypeSerializer::class)
        object VndKdeKpresenter: Application ("application/vnd.kde.kpresenter")
        @Serializable(MimeTypeSerializer::class)
        object VndKdeKspread: Application ("application/vnd.kde.kspread")
        @Serializable(MimeTypeSerializer::class)
        object VndKdeKword: Application ("application/vnd.kde.kword")
        @Serializable(MimeTypeSerializer::class)
        object VndKenameaapp: Application ("application/vnd.kenameaapp")
        @Serializable(MimeTypeSerializer::class)
        object VndKidspiration: Application ("application/vnd.kidspiration")
        @Serializable(MimeTypeSerializer::class)
        object VndKinar: Application ("application/vnd.kinar")
        @Serializable(MimeTypeSerializer::class)
        object VndKoan: Application ("application/vnd.koan")
        @Serializable(MimeTypeSerializer::class)
        object VndKodakDescriptor: Application ("application/vnd.kodak-descriptor")
        @Serializable(MimeTypeSerializer::class)
        object VndLasLasXml: Application ("application/vnd.las.las+xml")
        @Serializable(MimeTypeSerializer::class)
        object VndLlamagraphicsLifeBalanceDesktop: Application ("application/vnd.llamagraphics.life-balance.desktop")
        @Serializable(MimeTypeSerializer::class)
        object VndLlamagraphicsLifeBalanceExchangeXml: Application ("application/vnd.llamagraphics.life-balance.exchange+xml")
        @Serializable(MimeTypeSerializer::class)
        object VndLotus123: Application ("application/vnd.lotus-1-2-3")
        @Serializable(MimeTypeSerializer::class)
        object VndLotusApproach: Application ("application/vnd.lotus-approach")
        @Serializable(MimeTypeSerializer::class)
        object VndLotusFreelance: Application ("application/vnd.lotus-freelance")
        @Serializable(MimeTypeSerializer::class)
        object VndLotusNotes: Application ("application/vnd.lotus-notes")
        @Serializable(MimeTypeSerializer::class)
        object VndLotusOrganizer: Application ("application/vnd.lotus-organizer")
        @Serializable(MimeTypeSerializer::class)
        object VndLotusScreencam: Application ("application/vnd.lotus-screencam")
        @Serializable(MimeTypeSerializer::class)
        object VndLotusWordpro: Application ("application/vnd.lotus-wordpro")
        @Serializable(MimeTypeSerializer::class)
        object VndMacportsPortpkg: Application ("application/vnd.macports.portpkg")
        @Serializable(MimeTypeSerializer::class)
        object VndMcd: Application ("application/vnd.mcd")
        @Serializable(MimeTypeSerializer::class)
        object VndMedcalcdata: Application ("application/vnd.medcalcdata")
        @Serializable(MimeTypeSerializer::class)
        object VndMediastationCdkey: Application ("application/vnd.mediastation.cdkey")
        @Serializable(MimeTypeSerializer::class)
        object VndMfer: Application ("application/vnd.mfer")
        @Serializable(MimeTypeSerializer::class)
        object VndMfmp: Application ("application/vnd.mfmp")
        @Serializable(MimeTypeSerializer::class)
        object VndMicrografxFlo: Application ("application/vnd.micrografx.flo")
        @Serializable(MimeTypeSerializer::class)
        object VndMicrografxIgx: Application ("application/vnd.micrografx.igx")
        @Serializable(MimeTypeSerializer::class)
        object VndMif: Application ("application/vnd.mif")
        @Serializable(MimeTypeSerializer::class)
        object VndMobiusDaf: Application ("application/vnd.mobius.daf")
        @Serializable(MimeTypeSerializer::class)
        object VndMobiusDis: Application ("application/vnd.mobius.dis")
        @Serializable(MimeTypeSerializer::class)
        object VndMobiusMbk: Application ("application/vnd.mobius.mbk")
        @Serializable(MimeTypeSerializer::class)
        object VndMobiusMqy: Application ("application/vnd.mobius.mqy")
        @Serializable(MimeTypeSerializer::class)
        object VndMobiusMsl: Application ("application/vnd.mobius.msl")
        @Serializable(MimeTypeSerializer::class)
        object VndMobiusPlc: Application ("application/vnd.mobius.plc")
        @Serializable(MimeTypeSerializer::class)
        object VndMobiusTxf: Application ("application/vnd.mobius.txf")
        @Serializable(MimeTypeSerializer::class)
        object VndMophunApplication: Application ("application/vnd.mophun.application")
        @Serializable(MimeTypeSerializer::class)
        object VndMophunCertificate: Application ("application/vnd.mophun.certificate")
        @Serializable(MimeTypeSerializer::class)
        object VndMozillaXulXml: Application ("application/vnd.mozilla.xul+xml")
        @Serializable(MimeTypeSerializer::class)
        object VndMsArtgalry: Application ("application/vnd.ms-artgalry")
        @Serializable(MimeTypeSerializer::class)
        object VndMsCabCompressed: Application ("application/vnd.ms-cab-compressed")
        @Serializable(MimeTypeSerializer::class)
        object VndMsExcel: Application ("application/vnd.ms-excel")
        @Serializable(MimeTypeSerializer::class)
        object VndMsExcelAddinMacroenabled12: Application ("application/vnd.ms-excel.addin.macroenabled.12")
        @Serializable(MimeTypeSerializer::class)
        object VndMsExcelSheetBinaryMacroenabled12: Application ("application/vnd.ms-excel.sheet.binary.macroenabled.12")
        @Serializable(MimeTypeSerializer::class)
        object VndMsExcelSheetMacroenabled12: Application ("application/vnd.ms-excel.sheet.macroenabled.12")
        @Serializable(MimeTypeSerializer::class)
        object VndMsExcelTemplateMacroenabled12: Application ("application/vnd.ms-excel.template.macroenabled.12")
        @Serializable(MimeTypeSerializer::class)
        object VndMsFontobject: Application ("application/vnd.ms-fontobject")
        @Serializable(MimeTypeSerializer::class)
        object VndMsHtmlhelp: Application ("application/vnd.ms-htmlhelp")
        @Serializable(MimeTypeSerializer::class)
        object VndMsIms: Application ("application/vnd.ms-ims")
        @Serializable(MimeTypeSerializer::class)
        object VndMsLrm: Application ("application/vnd.ms-lrm")
        @Serializable(MimeTypeSerializer::class)
        object VndMsOfficetheme: Application ("application/vnd.ms-officetheme")
        @Serializable(MimeTypeSerializer::class)
        object VndMsPkiSeccat: Application ("application/vnd.ms-pki.seccat")
        @Serializable(MimeTypeSerializer::class)
        object VndMsPkiStl: Application ("application/vnd.ms-pki.stl")
        @Serializable(MimeTypeSerializer::class)
        object VndMsPowerpoint: Application ("application/vnd.ms-powerpoint")
        @Serializable(MimeTypeSerializer::class)
        object VndMsPowerpointAddinMacroenabled12: Application ("application/vnd.ms-powerpoint.addin.macroenabled.12")
        @Serializable(MimeTypeSerializer::class)
        object VndMsPowerpointPresentationMacroenabled12: Application ("application/vnd.ms-powerpoint.presentation.macroenabled.12")
        @Serializable(MimeTypeSerializer::class)
        object VndMsPowerpointSlideMacroenabled12: Application ("application/vnd.ms-powerpoint.slide.macroenabled.12")
        @Serializable(MimeTypeSerializer::class)
        object VndMsPowerpointSlideshowMacroenabled12: Application ("application/vnd.ms-powerpoint.slideshow.macroenabled.12")
        @Serializable(MimeTypeSerializer::class)
        object VndMsPowerpointTemplateMacroenabled12: Application ("application/vnd.ms-powerpoint.template.macroenabled.12")
        @Serializable(MimeTypeSerializer::class)
        object VndMsProject: Application ("application/vnd.ms-project")
        @Serializable(MimeTypeSerializer::class)
        object VndMsWordDocumentMacroenabled12: Application ("application/vnd.ms-word.document.macroenabled.12")
        @Serializable(MimeTypeSerializer::class)
        object VndMsWordTemplateMacroenabled12: Application ("application/vnd.ms-word.template.macroenabled.12")
        @Serializable(MimeTypeSerializer::class)
        object VndMsWorks: Application ("application/vnd.ms-works")
        @Serializable(MimeTypeSerializer::class)
        object VndMsWpl: Application ("application/vnd.ms-wpl")
        @Serializable(MimeTypeSerializer::class)
        object VndMsXpsdocument: Application ("application/vnd.ms-xpsdocument")
        @Serializable(MimeTypeSerializer::class)
        object VndMseq: Application ("application/vnd.mseq")
        @Serializable(MimeTypeSerializer::class)
        object VndMusician: Application ("application/vnd.musician")
        @Serializable(MimeTypeSerializer::class)
        object VndMuveeStyle: Application ("application/vnd.muvee.style")
        @Serializable(MimeTypeSerializer::class)
        object VndNeurolanguageNlu: Application ("application/vnd.neurolanguage.nlu")
        @Serializable(MimeTypeSerializer::class)
        object VndNoblenetDirectory: Application ("application/vnd.noblenet-directory")
        @Serializable(MimeTypeSerializer::class)
        object VndNoblenetSealer: Application ("application/vnd.noblenet-sealer")
        @Serializable(MimeTypeSerializer::class)
        object VndNoblenetWeb: Application ("application/vnd.noblenet-web")
        @Serializable(MimeTypeSerializer::class)
        object VndNokiaNGageData: Application ("application/vnd.nokia.n-gage.data")
        @Serializable(MimeTypeSerializer::class)
        object VndNokiaNGageSymbianInstall: Application ("application/vnd.nokia.n-gage.symbian.install")
        @Serializable(MimeTypeSerializer::class)
        object VndNokiaRadioPreset: Application ("application/vnd.nokia.radio-preset")
        @Serializable(MimeTypeSerializer::class)
        object VndNokiaRadioPresets: Application ("application/vnd.nokia.radio-presets")
        @Serializable(MimeTypeSerializer::class)
        object VndNovadigmEdm: Application ("application/vnd.novadigm.edm")
        @Serializable(MimeTypeSerializer::class)
        object VndNovadigmEdx: Application ("application/vnd.novadigm.edx")
        @Serializable(MimeTypeSerializer::class)
        object VndNovadigmExt: Application ("application/vnd.novadigm.ext")
        @Serializable(MimeTypeSerializer::class)
        object VndOasisOpendocumentChart: Application ("application/vnd.oasis.opendocument.chart")
        @Serializable(MimeTypeSerializer::class)
        object VndOasisOpendocumentChartTemplate: Application ("application/vnd.oasis.opendocument.chart-template")
        @Serializable(MimeTypeSerializer::class)
        object VndOasisOpendocumentDatabase: Application ("application/vnd.oasis.opendocument.database")
        @Serializable(MimeTypeSerializer::class)
        object VndOasisOpendocumentFormula: Application ("application/vnd.oasis.opendocument.formula")
        @Serializable(MimeTypeSerializer::class)
        object VndOasisOpendocumentFormulaTemplate: Application ("application/vnd.oasis.opendocument.formula-template")
        @Serializable(MimeTypeSerializer::class)
        object VndOasisOpendocumentGraphics: Application ("application/vnd.oasis.opendocument.graphics")
        @Serializable(MimeTypeSerializer::class)
        object VndOasisOpendocumentGraphicsTemplate: Application ("application/vnd.oasis.opendocument.graphics-template")
        @Serializable(MimeTypeSerializer::class)
        object VndOasisOpendocumentImage: Application ("application/vnd.oasis.opendocument.image")
        @Serializable(MimeTypeSerializer::class)
        object VndOasisOpendocumentImageTemplate: Application ("application/vnd.oasis.opendocument.image-template")
        @Serializable(MimeTypeSerializer::class)
        object VndOasisOpendocumentPresentation: Application ("application/vnd.oasis.opendocument.presentation")
        @Serializable(MimeTypeSerializer::class)
        object VndOasisOpendocumentPresentationTemplate: Application ("application/vnd.oasis.opendocument.presentation-template")
        @Serializable(MimeTypeSerializer::class)
        object VndOasisOpendocumentSpreadsheet: Application ("application/vnd.oasis.opendocument.spreadsheet")
        @Serializable(MimeTypeSerializer::class)
        object VndOasisOpendocumentSpreadsheetTemplate: Application ("application/vnd.oasis.opendocument.spreadsheet-template")
        @Serializable(MimeTypeSerializer::class)
        object VndOasisOpendocumentText: Application ("application/vnd.oasis.opendocument.text")
        @Serializable(MimeTypeSerializer::class)
        object VndOasisOpendocumentTextMaster: Application ("application/vnd.oasis.opendocument.text-master")
        @Serializable(MimeTypeSerializer::class)
        object VndOasisOpendocumentTextTemplate: Application ("application/vnd.oasis.opendocument.text-template")
        @Serializable(MimeTypeSerializer::class)
        object VndOasisOpendocumentTextWeb: Application ("application/vnd.oasis.opendocument.text-web")
        @Serializable(MimeTypeSerializer::class)
        object VndOlpcSugar: Application ("application/vnd.olpc-sugar")
        @Serializable(MimeTypeSerializer::class)
        object VndOmaDd2Xml: Application ("application/vnd.oma.dd2+xml")
        @Serializable(MimeTypeSerializer::class)
        object VndOpenofficeorgExtension: Application ("application/vnd.openofficeorg.extension")
        @Serializable(MimeTypeSerializer::class)
        object VndOpenxmlformatsOfficedocumentPresentationmlPresentation: Application ("application/vnd.openxmlformats-officedocument.presentationml.presentation")
        @Serializable(MimeTypeSerializer::class)
        object VndOpenxmlformatsOfficedocumentPresentationmlSlide: Application ("application/vnd.openxmlformats-officedocument.presentationml.slide")
        @Serializable(MimeTypeSerializer::class)
        object VndOpenxmlformatsOfficedocumentPresentationmlSlideshow: Application ("application/vnd.openxmlformats-officedocument.presentationml.slideshow")
        @Serializable(MimeTypeSerializer::class)
        object VndOpenxmlformatsOfficedocumentPresentationmlTemplate: Application ("application/vnd.openxmlformats-officedocument.presentationml.template")
        @Serializable(MimeTypeSerializer::class)
        object VndOpenxmlformatsOfficedocumentSpreadsheetmlSheet: Application ("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
        @Serializable(MimeTypeSerializer::class)
        object VndOpenxmlformatsOfficedocumentSpreadsheetmlTemplate: Application ("application/vnd.openxmlformats-officedocument.spreadsheetml.template")
        @Serializable(MimeTypeSerializer::class)
        object VndOpenxmlformatsOfficedocumentWordprocessingmlDocument: Application ("application/vnd.openxmlformats-officedocument.wordprocessingml.document")
        @Serializable(MimeTypeSerializer::class)
        object VndOpenxmlformatsOfficedocumentWordprocessingmlTemplate: Application ("application/vnd.openxmlformats-officedocument.wordprocessingml.template")
        @Serializable(MimeTypeSerializer::class)
        object VndOsgeoMapguidePackage: Application ("application/vnd.osgeo.mapguide.package")
        @Serializable(MimeTypeSerializer::class)
        object VndOsgiDp: Application ("application/vnd.osgi.dp")
        @Serializable(MimeTypeSerializer::class)
        object VndPalm: Application ("application/vnd.palm")
        @Serializable(MimeTypeSerializer::class)
        object VndPawaafile: Application ("application/vnd.pawaafile")
        @Serializable(MimeTypeSerializer::class)
        object VndPgFormat: Application ("application/vnd.pg.format")
        @Serializable(MimeTypeSerializer::class)
        object VndPgOsasli: Application ("application/vnd.pg.osasli")
        @Serializable(MimeTypeSerializer::class)
        object VndPicsel: Application ("application/vnd.picsel")
        @Serializable(MimeTypeSerializer::class)
        object VndPmiWidget: Application ("application/vnd.pmi.widget")
        @Serializable(MimeTypeSerializer::class)
        object VndPocketlearn: Application ("application/vnd.pocketlearn")
        @Serializable(MimeTypeSerializer::class)
        object VndPowerbuilder6: Application ("application/vnd.powerbuilder6")
        @Serializable(MimeTypeSerializer::class)
        object VndPreviewsystemsBox: Application ("application/vnd.previewsystems.box")
        @Serializable(MimeTypeSerializer::class)
        object VndProteusMagazine: Application ("application/vnd.proteus.magazine")
        @Serializable(MimeTypeSerializer::class)
        object VndPublishareDeltaTree: Application ("application/vnd.publishare-delta-tree")
        @Serializable(MimeTypeSerializer::class)
        object VndPviPtid1: Application ("application/vnd.pvi.ptid1")
        @Serializable(MimeTypeSerializer::class)
        object VndQuarkQuarkxpress: Application ("application/vnd.quark.quarkxpress")
        @Serializable(MimeTypeSerializer::class)
        object VndRealvncBed: Application ("application/vnd.realvnc.bed")
        @Serializable(MimeTypeSerializer::class)
        object VndRecordareMusicxml: Application ("application/vnd.recordare.musicxml")
        @Serializable(MimeTypeSerializer::class)
        object VndRecordareMusicxmlXml: Application ("application/vnd.recordare.musicxml+xml")
        @Serializable(MimeTypeSerializer::class)
        object VndRigCryptonote: Application ("application/vnd.rig.cryptonote")
        @Serializable(MimeTypeSerializer::class)
        object VndRimCod: Application ("application/vnd.rim.cod")
        @Serializable(MimeTypeSerializer::class)
        object VndRnRealmedia: Application ("application/vnd.rn-realmedia")
        @Serializable(MimeTypeSerializer::class)
        object VndRoute66Link66Xml: Application ("application/vnd.route66.link66+xml")
        @Serializable(MimeTypeSerializer::class)
        object VndSailingtrackerTrack: Application ("application/vnd.sailingtracker.track")
        @Serializable(MimeTypeSerializer::class)
        object VndSeemail: Application ("application/vnd.seemail")
        @Serializable(MimeTypeSerializer::class)
        object VndSema: Application ("application/vnd.sema")
        @Serializable(MimeTypeSerializer::class)
        object VndSemd: Application ("application/vnd.semd")
        @Serializable(MimeTypeSerializer::class)
        object VndSemf: Application ("application/vnd.semf")
        @Serializable(MimeTypeSerializer::class)
        object VndShanaInformedFormdata: Application ("application/vnd.shana.informed.formdata")
        @Serializable(MimeTypeSerializer::class)
        object VndShanaInformedFormtemplate: Application ("application/vnd.shana.informed.formtemplate")
        @Serializable(MimeTypeSerializer::class)
        object VndShanaInformedInterchange: Application ("application/vnd.shana.informed.interchange")
        @Serializable(MimeTypeSerializer::class)
        object VndShanaInformedPackage: Application ("application/vnd.shana.informed.package")
        @Serializable(MimeTypeSerializer::class)
        object VndSimtechMindmapper: Application ("application/vnd.simtech-mindmapper")
        @Serializable(MimeTypeSerializer::class)
        object VndSmaf: Application ("application/vnd.smaf")
        @Serializable(MimeTypeSerializer::class)
        object VndSmartTeacher: Application ("application/vnd.smart.teacher")
        @Serializable(MimeTypeSerializer::class)
        object VndSolentSdkmXml: Application ("application/vnd.solent.sdkm+xml")
        @Serializable(MimeTypeSerializer::class)
        object VndSpotfireDxp: Application ("application/vnd.spotfire.dxp")
        @Serializable(MimeTypeSerializer::class)
        object VndSpotfireSfs: Application ("application/vnd.spotfire.sfs")
        @Serializable(MimeTypeSerializer::class)
        object VndStardivisionCalc: Application ("application/vnd.stardivision.calc")
        @Serializable(MimeTypeSerializer::class)
        object VndStardivisionDraw: Application ("application/vnd.stardivision.draw")
        @Serializable(MimeTypeSerializer::class)
        object VndStardivisionImpress: Application ("application/vnd.stardivision.impress")
        @Serializable(MimeTypeSerializer::class)
        object VndStardivisionMath: Application ("application/vnd.stardivision.math")
        @Serializable(MimeTypeSerializer::class)
        object VndStardivisionWriter: Application ("application/vnd.stardivision.writer")
        @Serializable(MimeTypeSerializer::class)
        object VndStardivisionWriterGlobal: Application ("application/vnd.stardivision.writer-global")
        @Serializable(MimeTypeSerializer::class)
        object VndStepmaniaStepchart: Application ("application/vnd.stepmania.stepchart")
        @Serializable(MimeTypeSerializer::class)
        object VndSunXmlCalc: Application ("application/vnd.sun.xml.calc")
        @Serializable(MimeTypeSerializer::class)
        object VndSunXmlCalcTemplate: Application ("application/vnd.sun.xml.calc.template")
        @Serializable(MimeTypeSerializer::class)
        object VndSunXmlDraw: Application ("application/vnd.sun.xml.draw")
        @Serializable(MimeTypeSerializer::class)
        object VndSunXmlDrawTemplate: Application ("application/vnd.sun.xml.draw.template")
        @Serializable(MimeTypeSerializer::class)
        object VndSunXmlImpress: Application ("application/vnd.sun.xml.impress")
        @Serializable(MimeTypeSerializer::class)
        object VndSunXmlImpressTemplate: Application ("application/vnd.sun.xml.impress.template")
        @Serializable(MimeTypeSerializer::class)
        object VndSunXmlMath: Application ("application/vnd.sun.xml.math")
        @Serializable(MimeTypeSerializer::class)
        object VndSunXmlWriter: Application ("application/vnd.sun.xml.writer")
        @Serializable(MimeTypeSerializer::class)
        object VndSunXmlWriterGlobal: Application ("application/vnd.sun.xml.writer.global")
        @Serializable(MimeTypeSerializer::class)
        object VndSunXmlWriterTemplate: Application ("application/vnd.sun.xml.writer.template")
        @Serializable(MimeTypeSerializer::class)
        object VndSusCalendar: Application ("application/vnd.sus-calendar")
        @Serializable(MimeTypeSerializer::class)
        object VndSvd: Application ("application/vnd.svd")
        @Serializable(MimeTypeSerializer::class)
        object VndSymbianInstall: Application ("application/vnd.symbian.install")
        @Serializable(MimeTypeSerializer::class)
        object VndSyncmlXml: Application ("application/vnd.syncml+xml")
        @Serializable(MimeTypeSerializer::class)
        object VndSyncmlDmWbxml: Application ("application/vnd.syncml.dm+wbxml")
        @Serializable(MimeTypeSerializer::class)
        object VndSyncmlDmXml: Application ("application/vnd.syncml.dm+xml")
        @Serializable(MimeTypeSerializer::class)
        object VndTaoIntentModuleArchive: Application ("application/vnd.tao.intent-module-archive")
        @Serializable(MimeTypeSerializer::class)
        object VndTmobileLivetv: Application ("application/vnd.tmobile-livetv")
        @Serializable(MimeTypeSerializer::class)
        object VndTridTpt: Application ("application/vnd.trid.tpt")
        @Serializable(MimeTypeSerializer::class)
        object VndTriscapeMxs: Application ("application/vnd.triscape.mxs")
        @Serializable(MimeTypeSerializer::class)
        object VndTrueapp: Application ("application/vnd.trueapp")
        @Serializable(MimeTypeSerializer::class)
        object VndUfdl: Application ("application/vnd.ufdl")
        @Serializable(MimeTypeSerializer::class)
        object VndUiqTheme: Application ("application/vnd.uiq.theme")
        @Serializable(MimeTypeSerializer::class)
        object VndUmajin: Application ("application/vnd.umajin")
        @Serializable(MimeTypeSerializer::class)
        object VndUnity: Application ("application/vnd.unity")
        @Serializable(MimeTypeSerializer::class)
        object VndUomlXml: Application ("application/vnd.uoml+xml")
        @Serializable(MimeTypeSerializer::class)
        object VndVcx: Application ("application/vnd.vcx")
        @Serializable(MimeTypeSerializer::class)
        object VndVisio: Application ("application/vnd.visio")
        @Serializable(MimeTypeSerializer::class)
        object VndVisio2013: Application ("application/vnd.visio2013")
        @Serializable(MimeTypeSerializer::class)
        object VndVisionary: Application ("application/vnd.visionary")
        @Serializable(MimeTypeSerializer::class)
        object VndVsf: Application ("application/vnd.vsf")
        @Serializable(MimeTypeSerializer::class)
        object VndWapWbxml: Application ("application/vnd.wap.wbxml")
        @Serializable(MimeTypeSerializer::class)
        object VndWapWmlc: Application ("application/vnd.wap.wmlc")
        @Serializable(MimeTypeSerializer::class)
        object VndWapWmlscriptc: Application ("application/vnd.wap.wmlscriptc")
        @Serializable(MimeTypeSerializer::class)
        object VndWebturbo: Application ("application/vnd.webturbo")
        @Serializable(MimeTypeSerializer::class)
        object VndWolframPlayer: Application ("application/vnd.wolfram.player")
        @Serializable(MimeTypeSerializer::class)
        object VndWordperfect: Application ("application/vnd.wordperfect")
        @Serializable(MimeTypeSerializer::class)
        object VndWqd: Application ("application/vnd.wqd")
        @Serializable(MimeTypeSerializer::class)
        object VndWtStf: Application ("application/vnd.wt.stf")
        @Serializable(MimeTypeSerializer::class)
        object VndXara: Application ("application/vnd.xara")
        @Serializable(MimeTypeSerializer::class)
        object VndXfdl: Application ("application/vnd.xfdl")
        @Serializable(MimeTypeSerializer::class)
        object VndYamahaHvDic: Application ("application/vnd.yamaha.hv-dic")
        @Serializable(MimeTypeSerializer::class)
        object VndYamahaHvScript: Application ("application/vnd.yamaha.hv-script")
        @Serializable(MimeTypeSerializer::class)
        object VndYamahaHvVoice: Application ("application/vnd.yamaha.hv-voice")
        @Serializable(MimeTypeSerializer::class)
        object VndYamahaOpenscoreformat: Application ("application/vnd.yamaha.openscoreformat")
        @Serializable(MimeTypeSerializer::class)
        object VndYamahaOpenscoreformatOsfpvgXml: Application ("application/vnd.yamaha.openscoreformat.osfpvg+xml")
        @Serializable(MimeTypeSerializer::class)
        object VndYamahaSmafAudio: Application ("application/vnd.yamaha.smaf-audio")
        @Serializable(MimeTypeSerializer::class)
        object VndYamahaSmafPhrase: Application ("application/vnd.yamaha.smaf-phrase")
        @Serializable(MimeTypeSerializer::class)
        object VndYellowriverCustomMenu: Application ("application/vnd.yellowriver-custom-menu")
        @Serializable(MimeTypeSerializer::class)
        object VndZul: Application ("application/vnd.zul")
        @Serializable(MimeTypeSerializer::class)
        object VndZzazzDeckXml: Application ("application/vnd.zzazz.deck+xml")
        @Serializable(MimeTypeSerializer::class)
        object VoicexmlXml: Application ("application/voicexml+xml")
        @Serializable(MimeTypeSerializer::class)
        object Widget: Application ("application/widget")
        @Serializable(MimeTypeSerializer::class)
        object Winhlp: Application ("application/winhlp")
        @Serializable(MimeTypeSerializer::class)
        object WsdlXml: Application ("application/wsdl+xml")
        @Serializable(MimeTypeSerializer::class)
        object WspolicyXml: Application ("application/wspolicy+xml")
        @Serializable(MimeTypeSerializer::class)
        object X7zCompressed: Application ("application/x-7z-compressed")
        @Serializable(MimeTypeSerializer::class)
        object XAbiword: Application ("application/x-abiword")
        @Serializable(MimeTypeSerializer::class)
        object XAceCompressed: Application ("application/x-ace-compressed")
        @Serializable(MimeTypeSerializer::class)
        object XAuthorwareBin: Application ("application/x-authorware-bin")
        @Serializable(MimeTypeSerializer::class)
        object XAuthorwareMap: Application ("application/x-authorware-map")
        @Serializable(MimeTypeSerializer::class)
        object XAuthorwareSeg: Application ("application/x-authorware-seg")
        @Serializable(MimeTypeSerializer::class)
        object XBcpio: Application ("application/x-bcpio")
        @Serializable(MimeTypeSerializer::class)
        object XBittorrent: Application ("application/x-bittorrent")
        @Serializable(MimeTypeSerializer::class)
        object XBzip: Application ("application/x-bzip")
        @Serializable(MimeTypeSerializer::class)
        object XBzip2: Application ("application/x-bzip2")
        @Serializable(MimeTypeSerializer::class)
        object XCdlink: Application ("application/x-cdlink")
        @Serializable(MimeTypeSerializer::class)
        object XChat: Application ("application/x-chat")
        @Serializable(MimeTypeSerializer::class)
        object XChessPgn: Application ("application/x-chess-pgn")
        @Serializable(MimeTypeSerializer::class)
        object XCpio: Application ("application/x-cpio")
        @Serializable(MimeTypeSerializer::class)
        object XCsh: Application ("application/x-csh")
        @Serializable(MimeTypeSerializer::class)
        object XDebianPackage: Application ("application/x-debian-package")
        @Serializable(MimeTypeSerializer::class)
        object XDirector: Application ("application/x-director")
        @Serializable(MimeTypeSerializer::class)
        object XDoom: Application ("application/x-doom")
        @Serializable(MimeTypeSerializer::class)
        object XDtbncxXml: Application ("application/x-dtbncx+xml")
        @Serializable(MimeTypeSerializer::class)
        object XDtbookXml: Application ("application/x-dtbook+xml")
        @Serializable(MimeTypeSerializer::class)
        object XDtbresourceXml: Application ("application/x-dtbresource+xml")
        @Serializable(MimeTypeSerializer::class)
        object XDvi: Application ("application/x-dvi")
        @Serializable(MimeTypeSerializer::class)
        object XFontBdf: Application ("application/x-font-bdf")
        @Serializable(MimeTypeSerializer::class)
        object XFontGhostscript: Application ("application/x-font-ghostscript")
        @Serializable(MimeTypeSerializer::class)
        object XFontLinuxPsf: Application ("application/x-font-linux-psf")
        @Serializable(MimeTypeSerializer::class)
        object XFontOtf: Application ("application/x-font-otf")
        @Serializable(MimeTypeSerializer::class)
        object XFontPcf: Application ("application/x-font-pcf")
        @Serializable(MimeTypeSerializer::class)
        object XFontSnf: Application ("application/x-font-snf")
        @Serializable(MimeTypeSerializer::class)
        object XFontTtf: Application ("application/x-font-ttf")
        @Serializable(MimeTypeSerializer::class)
        object XFontType1: Application ("application/x-font-type1")
        @Serializable(MimeTypeSerializer::class)
        object XFontWoff: Application ("application/x-font-woff")
        @Serializable(MimeTypeSerializer::class)
        object XFuturesplash: Application ("application/x-futuresplash")
        @Serializable(MimeTypeSerializer::class)
        object XGnumeric: Application ("application/x-gnumeric")
        @Serializable(MimeTypeSerializer::class)
        object XGtar: Application ("application/x-gtar")
        @Serializable(MimeTypeSerializer::class)
        object XHdf: Application ("application/x-hdf")
        @Serializable(MimeTypeSerializer::class)
        object XJavaJnlpFile: Application ("application/x-java-jnlp-file")
        @Serializable(MimeTypeSerializer::class)
        object XLatex: Application ("application/x-latex")
        @Serializable(MimeTypeSerializer::class)
        object XMobipocketEbook: Application ("application/x-mobipocket-ebook")
        @Serializable(MimeTypeSerializer::class)
        object XMsApplication: Application ("application/x-ms-application")
        @Serializable(MimeTypeSerializer::class)
        object XMsWmd: Application ("application/x-ms-wmd")
        @Serializable(MimeTypeSerializer::class)
        object XMsWmz: Application ("application/x-ms-wmz")
        @Serializable(MimeTypeSerializer::class)
        object XMsXbap: Application ("application/x-ms-xbap")
        @Serializable(MimeTypeSerializer::class)
        object XMsaccess: Application ("application/x-msaccess")
        @Serializable(MimeTypeSerializer::class)
        object XMsbinder: Application ("application/x-msbinder")
        @Serializable(MimeTypeSerializer::class)
        object XMscardfile: Application ("application/x-mscardfile")
        @Serializable(MimeTypeSerializer::class)
        object XMsclip: Application ("application/x-msclip")
        @Serializable(MimeTypeSerializer::class)
        object XMsdownload: Application ("application/x-msdownload")
        @Serializable(MimeTypeSerializer::class)
        object XMsmediaview: Application ("application/x-msmediaview")
        @Serializable(MimeTypeSerializer::class)
        object XMsmetafile: Application ("application/x-msmetafile")
        @Serializable(MimeTypeSerializer::class)
        object XMsmoney: Application ("application/x-msmoney")
        @Serializable(MimeTypeSerializer::class)
        object XMspublisher: Application ("application/x-mspublisher")
        @Serializable(MimeTypeSerializer::class)
        object XMsschedule: Application ("application/x-msschedule")
        @Serializable(MimeTypeSerializer::class)
        object XMsterminal: Application ("application/x-msterminal")
        @Serializable(MimeTypeSerializer::class)
        object XMswrite: Application ("application/x-mswrite")
        @Serializable(MimeTypeSerializer::class)
        object XNetcdf: Application ("application/x-netcdf")
        @Serializable(MimeTypeSerializer::class)
        object XPkcs12: Application ("application/x-pkcs12")
        @Serializable(MimeTypeSerializer::class)
        object XPkcs7Certificates: Application ("application/x-pkcs7-certificates")
        @Serializable(MimeTypeSerializer::class)
        object XPkcs7Certreqresp: Application ("application/x-pkcs7-certreqresp")
        @Serializable(MimeTypeSerializer::class)
        object XRarCompressed: Application ("application/x-rar-compressed")
        @Serializable(MimeTypeSerializer::class)
        object XSh: Application ("application/x-sh")
        @Serializable(MimeTypeSerializer::class)
        object XShar: Application ("application/x-shar")
        @Serializable(MimeTypeSerializer::class)
        object XShockwaveFlash: Application ("application/x-shockwave-flash")
        @Serializable(MimeTypeSerializer::class)
        object XSilverlightApp: Application ("application/x-silverlight-app")
        @Serializable(MimeTypeSerializer::class)
        object XStuffit: Application ("application/x-stuffit")
        @Serializable(MimeTypeSerializer::class)
        object XStuffitx: Application ("application/x-stuffitx")
        @Serializable(MimeTypeSerializer::class)
        object XSv4cpio: Application ("application/x-sv4cpio")
        @Serializable(MimeTypeSerializer::class)
        object XSv4crc: Application ("application/x-sv4crc")
        @Serializable(MimeTypeSerializer::class)
        object XTar: Application ("application/x-tar")
        @Serializable(MimeTypeSerializer::class)
        object XTcl: Application ("application/x-tcl")
        @Serializable(MimeTypeSerializer::class)
        object XTex: Application ("application/x-tex")
        @Serializable(MimeTypeSerializer::class)
        object XTexTfm: Application ("application/x-tex-tfm")
        @Serializable(MimeTypeSerializer::class)
        object XTexinfo: Application ("application/x-texinfo")
        @Serializable(MimeTypeSerializer::class)
        object XUstar: Application ("application/x-ustar")
        @Serializable(MimeTypeSerializer::class)
        object XWaisSource: Application ("application/x-wais-source")
        @Serializable(MimeTypeSerializer::class)
        object XX509CaCert: Application ("application/x-x509-ca-cert")
        @Serializable(MimeTypeSerializer::class)
        object XXfig: Application ("application/x-xfig")
        @Serializable(MimeTypeSerializer::class)
        object XXpinstall: Application ("application/x-xpinstall")
        @Serializable(MimeTypeSerializer::class)
        object XcapDiffXml: Application ("application/xcap-diff+xml")
        @Serializable(MimeTypeSerializer::class)
        object XencXml: Application ("application/xenc+xml")
        @Serializable(MimeTypeSerializer::class)
        object XhtmlXml: Application ("application/xhtml+xml")
        @Serializable(MimeTypeSerializer::class)
        object Xml: Application ("application/xml")
        @Serializable(MimeTypeSerializer::class)
        object XmlDtd: Application ("application/xml-dtd")
        @Serializable(MimeTypeSerializer::class)
        object XopXml: Application ("application/xop+xml")
        @Serializable(MimeTypeSerializer::class)
        object XsltXml: Application ("application/xslt+xml")
        @Serializable(MimeTypeSerializer::class)
        object XspfXml: Application ("application/xspf+xml")
        @Serializable(MimeTypeSerializer::class)
        object XvXml: Application ("application/xv+xml")
        @Serializable(MimeTypeSerializer::class)
        object Yang: Application ("application/yang")
        @Serializable(MimeTypeSerializer::class)
        object YinXml: Application ("application/yin+xml")
        @Serializable(MimeTypeSerializer::class)
        object Zip: Application ("application/zip")
        @Serializable(MimeTypeSerializer::class)
        object XAppleDiskimage: Application ("application/x-apple-diskimage")
    }

    @Serializable(MimeTypeSerializer::class)
    sealed class Audio(raw: String) : MimeType, KnownMimeTypes(raw) {
        @Serializable(MimeTypeSerializer::class)
        object Any: Audio ("audio/*")
        @Serializable(MimeTypeSerializer::class)
        object Adpcm: Audio ("audio/adpcm")
        @Serializable(MimeTypeSerializer::class)
        object Basic: Audio ("audio/basic")
        @Serializable(MimeTypeSerializer::class)
        object Midi: Audio ("audio/midi")
        @Serializable(MimeTypeSerializer::class)
        object Mp4: Audio ("audio/mp4")
        @Serializable(MimeTypeSerializer::class)
        object Mpeg: Audio ("audio/mpeg")
        @Serializable(MimeTypeSerializer::class)
        object Ogg: Audio ("audio/ogg")
        @Serializable(MimeTypeSerializer::class)
        object VndDeceAudio: Audio ("audio/vnd.dece.audio")
        @Serializable(MimeTypeSerializer::class)
        object VndDigitalWinds: Audio ("audio/vnd.digital-winds")
        @Serializable(MimeTypeSerializer::class)
        object VndDra: Audio ("audio/vnd.dra")
        @Serializable(MimeTypeSerializer::class)
        object VndDts: Audio ("audio/vnd.dts")
        @Serializable(MimeTypeSerializer::class)
        object VndDtsHd: Audio ("audio/vnd.dts.hd")
        @Serializable(MimeTypeSerializer::class)
        object VndLucentVoice: Audio ("audio/vnd.lucent.voice")
        @Serializable(MimeTypeSerializer::class)
        object VndMsPlayreadyMediaPya: Audio ("audio/vnd.ms-playready.media.pya")
        @Serializable(MimeTypeSerializer::class)
        object VndNueraEcelp4800: Audio ("audio/vnd.nuera.ecelp4800")
        @Serializable(MimeTypeSerializer::class)
        object VndNueraEcelp7470: Audio ("audio/vnd.nuera.ecelp7470")
        @Serializable(MimeTypeSerializer::class)
        object VndNueraEcelp9600: Audio ("audio/vnd.nuera.ecelp9600")
        @Serializable(MimeTypeSerializer::class)
        object VndRip: Audio ("audio/vnd.rip")
        @Serializable(MimeTypeSerializer::class)
        object Webm: Audio ("audio/webm")
        @Serializable(MimeTypeSerializer::class)
        object XAac: Audio ("audio/x-aac")
        @Serializable(MimeTypeSerializer::class)
        object XAiff: Audio ("audio/x-aiff")
        @Serializable(MimeTypeSerializer::class)
        object XMpegurl: Audio ("audio/x-mpegurl")
        @Serializable(MimeTypeSerializer::class)
        object XMsWax: Audio ("audio/x-ms-wax")
        @Serializable(MimeTypeSerializer::class)
        object XMsWma: Audio ("audio/x-ms-wma")
        @Serializable(MimeTypeSerializer::class)
        object XPnRealaudio: Audio ("audio/x-pn-realaudio")
        @Serializable(MimeTypeSerializer::class)
        object XPnRealaudioPlugin: Audio ("audio/x-pn-realaudio-plugin")
        @Serializable(MimeTypeSerializer::class)
        object XWav: Audio ("audio/x-wav")
    }

    @Serializable(MimeTypeSerializer::class)
    sealed class Chemical(raw: String) : MimeType, KnownMimeTypes(raw) {
        @Serializable(MimeTypeSerializer::class)
        object Any: Chemical ("chemical/*")
        @Serializable(MimeTypeSerializer::class)
        object XCdx: Chemical ("chemical/x-cdx")
        @Serializable(MimeTypeSerializer::class)
        object XCif: Chemical ("chemical/x-cif")
        @Serializable(MimeTypeSerializer::class)
        object XCmdf: Chemical ("chemical/x-cmdf")
        @Serializable(MimeTypeSerializer::class)
        object XCml: Chemical ("chemical/x-cml")
        @Serializable(MimeTypeSerializer::class)
        object XCsml: Chemical ("chemical/x-csml")
        @Serializable(MimeTypeSerializer::class)
        object XXyz: Chemical ("chemical/x-xyz")
    }

    @Serializable(MimeTypeSerializer::class)
    sealed class Image(raw: String) : MimeType, KnownMimeTypes(raw) {
        @Serializable(MimeTypeSerializer::class)
        object Any: Image ("image/*")
        @Serializable(MimeTypeSerializer::class)
        object Bmp: Image ("image/bmp")
        @Serializable(MimeTypeSerializer::class)
        object Cgm: Image ("image/cgm")
        @Serializable(MimeTypeSerializer::class)
        object G3fax: Image ("image/g3fax")
        @Serializable(MimeTypeSerializer::class)
        object Gif: Image ("image/gif")
        @Serializable(MimeTypeSerializer::class)
        object Ief: Image ("image/ief")
        @Serializable(MimeTypeSerializer::class)
        object Jpeg: Image ("image/jpeg")
        @Serializable(MimeTypeSerializer::class)
        object Pjpeg: Image ("image/pjpeg")
        @Serializable(MimeTypeSerializer::class)
        object XCitrixJpeg: Image ("image/x-citrix-jpeg")
        @Serializable(MimeTypeSerializer::class)
        object Ktx: Image ("image/ktx")
        @Serializable(MimeTypeSerializer::class)
        object Png: Image ("image/png")
        @Serializable(MimeTypeSerializer::class)
        object XPng: Image ("image/x-png")
        @Serializable(MimeTypeSerializer::class)
        object XCitrixPng: Image ("image/x-citrix-png")
        @Serializable(MimeTypeSerializer::class)
        object PrsBtif: Image ("image/prs.btif")
        @Serializable(MimeTypeSerializer::class)
        object SvgXml: Image ("image/svg+xml")
        @Serializable(MimeTypeSerializer::class)
        object Tiff: Image ("image/tiff")
        @Serializable(MimeTypeSerializer::class)
        object VndAdobePhotoshop: Image ("image/vnd.adobe.photoshop")
        @Serializable(MimeTypeSerializer::class)
        object VndDeceGraphic: Image ("image/vnd.dece.graphic")
        @Serializable(MimeTypeSerializer::class)
        object VndDvbSubtitle: Image ("image/vnd.dvb.subtitle")
        @Serializable(MimeTypeSerializer::class)
        object VndDjvu: Image ("image/vnd.djvu")
        @Serializable(MimeTypeSerializer::class)
        object VndDwg: Image ("image/vnd.dwg")
        @Serializable(MimeTypeSerializer::class)
        object VndDxf: Image ("image/vnd.dxf")
        @Serializable(MimeTypeSerializer::class)
        object VndFastbidsheet: Image ("image/vnd.fastbidsheet")
        @Serializable(MimeTypeSerializer::class)
        object VndFpx: Image ("image/vnd.fpx")
        @Serializable(MimeTypeSerializer::class)
        object VndFst: Image ("image/vnd.fst")
        @Serializable(MimeTypeSerializer::class)
        object VndFujixeroxEdmicsMmr: Image ("image/vnd.fujixerox.edmics-mmr")
        @Serializable(MimeTypeSerializer::class)
        object VndFujixeroxEdmicsRlc: Image ("image/vnd.fujixerox.edmics-rlc")
        @Serializable(MimeTypeSerializer::class)
        object VndMsModi: Image ("image/vnd.ms-modi")
        @Serializable(MimeTypeSerializer::class)
        object VndNetFpx: Image ("image/vnd.net-fpx")
        @Serializable(MimeTypeSerializer::class)
        object VndWapWbmp: Image ("image/vnd.wap.wbmp")
        @Serializable(MimeTypeSerializer::class)
        object VndXiff: Image ("image/vnd.xiff")
        @Serializable(MimeTypeSerializer::class)
        object Webp: Image ("image/webp")
        @Serializable(MimeTypeSerializer::class)
        object XCmuRaster: Image ("image/x-cmu-raster")
        @Serializable(MimeTypeSerializer::class)
        object XCmx: Image ("image/x-cmx")
        @Serializable(MimeTypeSerializer::class)
        object XFreehand: Image ("image/x-freehand")
        @Serializable(MimeTypeSerializer::class)
        object XIcon: Image ("image/x-icon")
        @Serializable(MimeTypeSerializer::class)
        object XPcx: Image ("image/x-pcx")
        @Serializable(MimeTypeSerializer::class)
        object XPict: Image ("image/x-pict")
        @Serializable(MimeTypeSerializer::class)
        object XPortableAnymap: Image ("image/x-portable-anymap")
        @Serializable(MimeTypeSerializer::class)
        object XPortableBitmap: Image ("image/x-portable-bitmap")
        @Serializable(MimeTypeSerializer::class)
        object XPortableGraymap: Image ("image/x-portable-graymap")
        @Serializable(MimeTypeSerializer::class)
        object XPortablePixmap: Image ("image/x-portable-pixmap")
        @Serializable(MimeTypeSerializer::class)
        object XRgb: Image ("image/x-rgb")
        @Serializable(MimeTypeSerializer::class)
        object XXbitmap: Image ("image/x-xbitmap")
        @Serializable(MimeTypeSerializer::class)
        object XXpixmap: Image ("image/x-xpixmap")
        @Serializable(MimeTypeSerializer::class)
        object XXwindowdump: Image ("image/x-xwindowdump")
    }

    @Serializable(MimeTypeSerializer::class)
    sealed class Message(raw: String) : MimeType, KnownMimeTypes(raw) {
        @Serializable(MimeTypeSerializer::class)
        object Any: Message ("message/*")
        @Serializable(MimeTypeSerializer::class)
        object Rfc822: Message ("message/rfc822")
    }

    @Serializable(MimeTypeSerializer::class)
    sealed class Model(raw: String) : MimeType, KnownMimeTypes(raw) {
        @Serializable(MimeTypeSerializer::class)
        object Any: Model ("model/*")
        @Serializable(MimeTypeSerializer::class)
        object Iges: Model ("model/iges")
        @Serializable(MimeTypeSerializer::class)
        object Mesh: Model ("model/mesh")
        @Serializable(MimeTypeSerializer::class)
        object VndColladaXml: Model ("model/vnd.collada+xml")
        @Serializable(MimeTypeSerializer::class)
        object VndDwf: Model ("model/vnd.dwf")
        @Serializable(MimeTypeSerializer::class)
        object VndGdl: Model ("model/vnd.gdl")
        @Serializable(MimeTypeSerializer::class)
        object VndGtw: Model ("model/vnd.gtw")
        @Serializable(MimeTypeSerializer::class)
        object VndMts: Model ("model/vnd.mts")
        @Serializable(MimeTypeSerializer::class)
        object VndVtu: Model ("model/vnd.vtu")
        @Serializable(MimeTypeSerializer::class)
        object Vrml: Model ("model/vrml")
    }

    @Serializable(MimeTypeSerializer::class)
    sealed class Text(raw: String) : MimeType, KnownMimeTypes(raw) {
        @Serializable(MimeTypeSerializer::class)
        object Any: Text ("text/*")
        @Serializable(MimeTypeSerializer::class)
        object Calendar: Text ("text/calendar")
        @Serializable(MimeTypeSerializer::class)
        object Css: Text ("text/css")
        @Serializable(MimeTypeSerializer::class)
        object Csv: Text ("text/csv")
        @Serializable(MimeTypeSerializer::class)
        object Html: Text ("text/html")
        @Serializable(MimeTypeSerializer::class)
        object N3: Text ("text/n3")
        @Serializable(MimeTypeSerializer::class)
        object Plain: Text ("text/plain")
        @Serializable(MimeTypeSerializer::class)
        object PrsLinesTag: Text ("text/prs.lines.tag")
        @Serializable(MimeTypeSerializer::class)
        object Richtext: Text ("text/richtext")
        @Serializable(MimeTypeSerializer::class)
        object Sgml: Text ("text/sgml")
        @Serializable(MimeTypeSerializer::class)
        object TabSeparatedValues: Text ("text/tab-separated-values")
        @Serializable(MimeTypeSerializer::class)
        object Troff: Text ("text/troff")
        @Serializable(MimeTypeSerializer::class)
        object Turtle: Text ("text/turtle")
        @Serializable(MimeTypeSerializer::class)
        object UriList: Text ("text/uri-list")
        @Serializable(MimeTypeSerializer::class)
        object VndCurl: Text ("text/vnd.curl")
        @Serializable(MimeTypeSerializer::class)
        object VndCurlDcurl: Text ("text/vnd.curl.dcurl")
        @Serializable(MimeTypeSerializer::class)
        object VndCurlScurl: Text ("text/vnd.curl.scurl")
        @Serializable(MimeTypeSerializer::class)
        object VndCurlMcurl: Text ("text/vnd.curl.mcurl")
        @Serializable(MimeTypeSerializer::class)
        object VndFly: Text ("text/vnd.fly")
        @Serializable(MimeTypeSerializer::class)
        object VndFmiFlexstor: Text ("text/vnd.fmi.flexstor")
        @Serializable(MimeTypeSerializer::class)
        object VndGraphviz: Text ("text/vnd.graphviz")
        @Serializable(MimeTypeSerializer::class)
        object VndIn3d3dml: Text ("text/vnd.in3d.3dml")
        @Serializable(MimeTypeSerializer::class)
        object VndIn3dSpot: Text ("text/vnd.in3d.spot")
        @Serializable(MimeTypeSerializer::class)
        object VndSunJ2meAppDescriptor: Text ("text/vnd.sun.j2me.app-descriptor")
        @Serializable(MimeTypeSerializer::class)
        object VndWapWml: Text ("text/vnd.wap.wml")
        @Serializable(MimeTypeSerializer::class)
        object VndWapWmlscript: Text ("text/vnd.wap.wmlscript")
        @Serializable(MimeTypeSerializer::class)
        object XAsm: Text ("text/x-asm")
        @Serializable(MimeTypeSerializer::class)
        object XC: Text ("text/x-c")
        @Serializable(MimeTypeSerializer::class)
        object XFortran: Text ("text/x-fortran")
        @Serializable(MimeTypeSerializer::class)
        object XPascal: Text ("text/x-pascal")
        @Serializable(MimeTypeSerializer::class)
        object XJavaSourceJava: Text ("text/x-java-source,java")
        @Serializable(MimeTypeSerializer::class)
        object XSetext: Text ("text/x-setext")
        @Serializable(MimeTypeSerializer::class)
        object XUuencode: Text ("text/x-uuencode")
        @Serializable(MimeTypeSerializer::class)
        object XVcalendar: Text ("text/x-vcalendar")
        @Serializable(MimeTypeSerializer::class)
        object XVcard: Text ("text/x-vcard")
        @Serializable(MimeTypeSerializer::class)
        object PlainBas: Text ("text/plain-bas")
        @Serializable(MimeTypeSerializer::class)
        object Yaml: Text ("text/yaml")
    }

    @Serializable(MimeTypeSerializer::class)
    sealed class Video(raw: String) : MimeType, KnownMimeTypes(raw) {
        @Serializable(MimeTypeSerializer::class)
        object Any: Video ("video/*")
        @Serializable(MimeTypeSerializer::class)
        object V3gpp: Video ("video/3gpp")
        @Serializable(MimeTypeSerializer::class)
        object V3gpp2: Video ("video/3gpp2")
        @Serializable(MimeTypeSerializer::class)
        object H261: Video ("video/h261")
        @Serializable(MimeTypeSerializer::class)
        object H263: Video ("video/h263")
        @Serializable(MimeTypeSerializer::class)
        object H264: Video ("video/h264")
        @Serializable(MimeTypeSerializer::class)
        object Jpeg: Video ("video/jpeg")
        @Serializable(MimeTypeSerializer::class)
        object Jpm: Video ("video/jpm")
        @Serializable(MimeTypeSerializer::class)
        object Mj2: Video ("video/mj2")
        @Serializable(MimeTypeSerializer::class)
        object Mp4: Video ("video/mp4")
        @Serializable(MimeTypeSerializer::class)
        object Mpeg: Video ("video/mpeg")
        @Serializable(MimeTypeSerializer::class)
        object Ogg: Video ("video/ogg")
        @Serializable(MimeTypeSerializer::class)
        object Quicktime: Video ("video/quicktime")
        @Serializable(MimeTypeSerializer::class)
        object VndDeceHd: Video ("video/vnd.dece.hd")
        @Serializable(MimeTypeSerializer::class)
        object VndDeceMobile: Video ("video/vnd.dece.mobile")
        @Serializable(MimeTypeSerializer::class)
        object VndDecePd: Video ("video/vnd.dece.pd")
        @Serializable(MimeTypeSerializer::class)
        object VndDeceSd: Video ("video/vnd.dece.sd")
        @Serializable(MimeTypeSerializer::class)
        object VndDeceVideo: Video ("video/vnd.dece.video")
        @Serializable(MimeTypeSerializer::class)
        object VndFvt: Video ("video/vnd.fvt")
        @Serializable(MimeTypeSerializer::class)
        object VndMpegurl: Video ("video/vnd.mpegurl")
        @Serializable(MimeTypeSerializer::class)
        object VndMsPlayreadyMediaPyv: Video ("video/vnd.ms-playready.media.pyv")
        @Serializable(MimeTypeSerializer::class)
        object VndUvvuMp4: Video ("video/vnd.uvvu.mp4")
        @Serializable(MimeTypeSerializer::class)
        object VndVivo: Video ("video/vnd.vivo")
        @Serializable(MimeTypeSerializer::class)
        object Webm: Video ("video/webm")
        @Serializable(MimeTypeSerializer::class)
        object XF4v: Video ("video/x-f4v")
        @Serializable(MimeTypeSerializer::class)
        object XFli: Video ("video/x-fli")
        @Serializable(MimeTypeSerializer::class)
        object XFlv: Video ("video/x-flv")
        @Serializable(MimeTypeSerializer::class)
        object XM4v: Video ("video/x-m4v")
        @Serializable(MimeTypeSerializer::class)
        object XMsAsf: Video ("video/x-ms-asf")
        @Serializable(MimeTypeSerializer::class)
        object XMsWm: Video ("video/x-ms-wm")
        @Serializable(MimeTypeSerializer::class)
        object XMsWmv: Video ("video/x-ms-wmv")
        @Serializable(MimeTypeSerializer::class)
        object XMsWmx: Video ("video/x-ms-wmx")
        @Serializable(MimeTypeSerializer::class)
        object XMsWvx: Video ("video/x-ms-wvx")
        @Serializable(MimeTypeSerializer::class)
        object XMsvideo: Video ("video/x-msvideo")
        @Serializable(MimeTypeSerializer::class)
        object XSgiMovie: Video ("video/x-sgi-movie")
    }

    @Serializable(MimeTypeSerializer::class)
    sealed class XConference(raw: String) : MimeType, KnownMimeTypes(raw) {
        @Serializable(MimeTypeSerializer::class)
        object Any: XConference ("x-conference/*")
        @Serializable(MimeTypeSerializer::class)
        object XCooltalk: XConference ("x-conference/x-cooltalk")
    }
}

internal val knownMimeTypes: Set<MimeType> = setOf(
    KnownMimeTypes.Any,
    KnownMimeTypes.Application.AndrewInset,
    KnownMimeTypes.Application.Applixware,
    KnownMimeTypes.Application.AtomXml,
    KnownMimeTypes.Application.AtomcatXml,
    KnownMimeTypes.Application.AtomsvcXml,
    KnownMimeTypes.Application.CcxmlXml,
    KnownMimeTypes.Application.CdmiCapability,
    KnownMimeTypes.Application.CdmiContainer,
    KnownMimeTypes.Application.CdmiDomain,
    KnownMimeTypes.Application.CdmiObject,
    KnownMimeTypes.Application.CdmiQueue,
    KnownMimeTypes.Application.CuSeeme,
    KnownMimeTypes.Application.DavmountXml,
    KnownMimeTypes.Application.DsscDer,
    KnownMimeTypes.Application.DsscXml,
    KnownMimeTypes.Application.Ecmascript,
    KnownMimeTypes.Application.EmmaXml,
    KnownMimeTypes.Application.EpubZip,
    KnownMimeTypes.Application.Exi,
    KnownMimeTypes.Application.FontTdpfr,
    KnownMimeTypes.Application.Hyperstudio,
    KnownMimeTypes.Application.Ipfix,
    KnownMimeTypes.Application.JavaArchive,
    KnownMimeTypes.Application.JavaSerializedObject,
    KnownMimeTypes.Application.JavaVm,
    KnownMimeTypes.Application.Javascript,
    KnownMimeTypes.Application.Json,
    KnownMimeTypes.Application.MacBinhex40,
    KnownMimeTypes.Application.MacCompactpro,
    KnownMimeTypes.Application.MadsXml,
    KnownMimeTypes.Application.Marc,
    KnownMimeTypes.Application.MarcxmlXml,
    KnownMimeTypes.Application.Mathematica,
    KnownMimeTypes.Application.MathmlXml,
    KnownMimeTypes.Application.Mbox,
    KnownMimeTypes.Application.MediaservercontrolXml,
    KnownMimeTypes.Application.Metalink4Xml,
    KnownMimeTypes.Application.MetsXml,
    KnownMimeTypes.Application.ModsXml,
    KnownMimeTypes.Application.Mp21,
    KnownMimeTypes.Application.Mp4,
    KnownMimeTypes.Application.Msword,
    KnownMimeTypes.Application.Mxf,
    KnownMimeTypes.Application.OctetStream,
    KnownMimeTypes.Application.Oda,
    KnownMimeTypes.Application.OebpsPackageXml,
    KnownMimeTypes.Application.Ogg,
    KnownMimeTypes.Application.Onenote,
    KnownMimeTypes.Application.PatchOpsErrorXml,
    KnownMimeTypes.Application.Pdf,
    KnownMimeTypes.Application.PgpEncrypted,
    KnownMimeTypes.Application.PgpSignature,
    KnownMimeTypes.Application.PicsRules,
    KnownMimeTypes.Application.Pkcs10,
    KnownMimeTypes.Application.Pkcs7Mime,
    KnownMimeTypes.Application.Pkcs7Signature,
    KnownMimeTypes.Application.Pkcs8,
    KnownMimeTypes.Application.PkixAttrCert,
    KnownMimeTypes.Application.PkixCert,
    KnownMimeTypes.Application.PkixCrl,
    KnownMimeTypes.Application.PkixPkipath,
    KnownMimeTypes.Application.Pkixcmp,
    KnownMimeTypes.Application.PlsXml,
    KnownMimeTypes.Application.Postscript,
    KnownMimeTypes.Application.PrsCww,
    KnownMimeTypes.Application.PskcXml,
    KnownMimeTypes.Application.RdfXml,
    KnownMimeTypes.Application.ReginfoXml,
    KnownMimeTypes.Application.RelaxNgCompactSyntax,
    KnownMimeTypes.Application.ResourceListsXml,
    KnownMimeTypes.Application.ResourceListsDiffXml,
    KnownMimeTypes.Application.RlsServicesXml,
    KnownMimeTypes.Application.RsdXml,
    KnownMimeTypes.Application.RssXml,
    KnownMimeTypes.Application.Rtf,
    KnownMimeTypes.Application.SbmlXml,
    KnownMimeTypes.Application.ScvpCvRequest,
    KnownMimeTypes.Application.ScvpCvResponse,
    KnownMimeTypes.Application.ScvpVpRequest,
    KnownMimeTypes.Application.ScvpVpResponse,
    KnownMimeTypes.Application.Sdp,
    KnownMimeTypes.Application.SetPaymentInitiation,
    KnownMimeTypes.Application.SetRegistrationInitiation,
    KnownMimeTypes.Application.ShfXml,
    KnownMimeTypes.Application.SmilXml,
    KnownMimeTypes.Application.SparqlQuery,
    KnownMimeTypes.Application.SparqlResultsXml,
    KnownMimeTypes.Application.Srgs,
    KnownMimeTypes.Application.SrgsXml,
    KnownMimeTypes.Application.SruXml,
    KnownMimeTypes.Application.SsmlXml,
    KnownMimeTypes.Application.TeiXml,
    KnownMimeTypes.Application.ThraudXml,
    KnownMimeTypes.Application.TimestampedData,
    KnownMimeTypes.Application.Vnd3gppPicBwLarge,
    KnownMimeTypes.Application.Vnd3gppPicBwSmall,
    KnownMimeTypes.Application.Vnd3gppPicBwVar,
    KnownMimeTypes.Application.Vnd3gpp2Tcap,
    KnownMimeTypes.Application.Vnd3mPostItNotes,
    KnownMimeTypes.Application.VndAccpacSimplyAso,
    KnownMimeTypes.Application.VndAccpacSimplyImp,
    KnownMimeTypes.Application.VndAcucobol,
    KnownMimeTypes.Application.VndAcucorp,
    KnownMimeTypes.Application.VndAdobeAirApplicationInstallerPackageZip,
    KnownMimeTypes.Application.VndAdobeFxp,
    KnownMimeTypes.Application.VndAdobeXdpXml,
    KnownMimeTypes.Application.VndAdobeXfdf,
    KnownMimeTypes.Application.VndAheadSpace,
    KnownMimeTypes.Application.VndAirzipFilesecureAzf,
    KnownMimeTypes.Application.VndAirzipFilesecureAzs,
    KnownMimeTypes.Application.VndAmazonEbook,
    KnownMimeTypes.Application.VndAmericandynamicsAcc,
    KnownMimeTypes.Application.VndAmigaAmi,
    KnownMimeTypes.Application.VndAndroidPackageArchive,
    KnownMimeTypes.Application.VndAnserWebCertificateIssueInitiation,
    KnownMimeTypes.Application.VndAnserWebFundsTransferInitiation,
    KnownMimeTypes.Application.VndAntixGameComponent,
    KnownMimeTypes.Application.VndAppleInstallerXml,
    KnownMimeTypes.Application.VndAppleMpegurl,
    KnownMimeTypes.Application.VndAristanetworksSwi,
    KnownMimeTypes.Application.VndAudiograph,
    KnownMimeTypes.Application.VndBlueiceMultipass,
    KnownMimeTypes.Application.VndBmi,
    KnownMimeTypes.Application.VndBusinessobjects,
    KnownMimeTypes.Application.VndChemdrawXml,
    KnownMimeTypes.Application.VndChipnutsKaraokeMmd,
    KnownMimeTypes.Application.VndCinderella,
    KnownMimeTypes.Application.VndClaymore,
    KnownMimeTypes.Application.VndCloantoRp9,
    KnownMimeTypes.Application.VndClonkC4group,
    KnownMimeTypes.Application.VndCluetrustCartomobileConfig,
    KnownMimeTypes.Application.VndCluetrustCartomobileConfigPkg,
    KnownMimeTypes.Application.VndCommonspace,
    KnownMimeTypes.Application.VndContactCmsg,
    KnownMimeTypes.Application.VndCosmocaller,
    KnownMimeTypes.Application.VndCrickClicker,
    KnownMimeTypes.Application.VndCrickClickerKeyboard,
    KnownMimeTypes.Application.VndCrickClickerPalette,
    KnownMimeTypes.Application.VndCrickClickerTemplate,
    KnownMimeTypes.Application.VndCrickClickerWordbank,
    KnownMimeTypes.Application.VndCriticaltoolsWbsXml,
    KnownMimeTypes.Application.VndCtcPosml,
    KnownMimeTypes.Application.VndCupsPpd,
    KnownMimeTypes.Application.VndCurlCar,
    KnownMimeTypes.Application.VndCurlPcurl,
    KnownMimeTypes.Application.VndDataVisionRdz,
    KnownMimeTypes.Application.VndDenovoFcselayoutLink,
    KnownMimeTypes.Application.VndDna,
    KnownMimeTypes.Application.VndDolbyMlp,
    KnownMimeTypes.Application.VndDpgraph,
    KnownMimeTypes.Application.VndDreamfactory,
    KnownMimeTypes.Application.VndDvbAit,
    KnownMimeTypes.Application.VndDvbService,
    KnownMimeTypes.Application.VndDynageo,
    KnownMimeTypes.Application.VndEcowinChart,
    KnownMimeTypes.Application.VndEnliven,
    KnownMimeTypes.Application.VndEpsonEsf,
    KnownMimeTypes.Application.VndEpsonMsf,
    KnownMimeTypes.Application.VndEpsonQuickanime,
    KnownMimeTypes.Application.VndEpsonSalt,
    KnownMimeTypes.Application.VndEpsonSsf,
    KnownMimeTypes.Application.VndEszigno3Xml,
    KnownMimeTypes.Application.VndEzpixAlbum,
    KnownMimeTypes.Application.VndEzpixPackage,
    KnownMimeTypes.Application.VndFdf,
    KnownMimeTypes.Application.VndFdsnSeed,
    KnownMimeTypes.Application.VndFlographit,
    KnownMimeTypes.Application.VndFluxtimeClip,
    KnownMimeTypes.Application.VndFramemaker,
    KnownMimeTypes.Application.VndFrogansFnc,
    KnownMimeTypes.Application.VndFrogansLtf,
    KnownMimeTypes.Application.VndFscWeblaunch,
    KnownMimeTypes.Application.VndFujitsuOasys,
    KnownMimeTypes.Application.VndFujitsuOasys2,
    KnownMimeTypes.Application.VndFujitsuOasys3,
    KnownMimeTypes.Application.VndFujitsuOasysgp,
    KnownMimeTypes.Application.VndFujitsuOasysprs,
    KnownMimeTypes.Application.VndFujixeroxDdd,
    KnownMimeTypes.Application.VndFujixeroxDocuworks,
    KnownMimeTypes.Application.VndFujixeroxDocuworksBinder,
    KnownMimeTypes.Application.VndFuzzysheet,
    KnownMimeTypes.Application.VndGenomatixTuxedo,
    KnownMimeTypes.Application.VndGeogebraFile,
    KnownMimeTypes.Application.VndGeogebraTool,
    KnownMimeTypes.Application.VndGeometryExplorer,
    KnownMimeTypes.Application.VndGeonext,
    KnownMimeTypes.Application.VndGeoplan,
    KnownMimeTypes.Application.VndGeospace,
    KnownMimeTypes.Application.VndGmx,
    KnownMimeTypes.Application.VndGoogleEarthKmlXml,
    KnownMimeTypes.Application.VndGoogleEarthKmz,
    KnownMimeTypes.Application.VndGrafeq,
    KnownMimeTypes.Application.VndGrooveAccount,
    KnownMimeTypes.Application.VndGrooveHelp,
    KnownMimeTypes.Application.VndGrooveIdentityMessage,
    KnownMimeTypes.Application.VndGrooveInjector,
    KnownMimeTypes.Application.VndGrooveToolMessage,
    KnownMimeTypes.Application.VndGrooveToolTemplate,
    KnownMimeTypes.Application.VndGrooveVcard,
    KnownMimeTypes.Application.VndHalXml,
    KnownMimeTypes.Application.VndHandheldEntertainmentXml,
    KnownMimeTypes.Application.VndHbci,
    KnownMimeTypes.Application.VndHheLessonPlayer,
    KnownMimeTypes.Application.VndHpHpgl,
    KnownMimeTypes.Application.VndHpHpid,
    KnownMimeTypes.Application.VndHpHps,
    KnownMimeTypes.Application.VndHpJlyt,
    KnownMimeTypes.Application.VndHpPcl,
    KnownMimeTypes.Application.VndHpPclxl,
    KnownMimeTypes.Application.VndHydrostatixSofData,
    KnownMimeTypes.Application.VndHzn3dCrossword,
    KnownMimeTypes.Application.VndIbmMinipay,
    KnownMimeTypes.Application.VndIbmModcap,
    KnownMimeTypes.Application.VndIbmRightsManagement,
    KnownMimeTypes.Application.VndIbmSecureContainer,
    KnownMimeTypes.Application.VndIccprofile,
    KnownMimeTypes.Application.VndIgloader,
    KnownMimeTypes.Application.VndImmervisionIvp,
    KnownMimeTypes.Application.VndImmervisionIvu,
    KnownMimeTypes.Application.VndInsorsIgm,
    KnownMimeTypes.Application.VndInterconFormnet,
    KnownMimeTypes.Application.VndIntergeo,
    KnownMimeTypes.Application.VndIntuQbo,
    KnownMimeTypes.Application.VndIntuQfx,
    KnownMimeTypes.Application.VndIpunpluggedRcprofile,
    KnownMimeTypes.Application.VndIrepositoryPackageXml,
    KnownMimeTypes.Application.VndIsXpr,
    KnownMimeTypes.Application.VndIsacFcs,
    KnownMimeTypes.Application.VndJam,
    KnownMimeTypes.Application.VndJcpJavameMidletRms,
    KnownMimeTypes.Application.VndJisp,
    KnownMimeTypes.Application.VndJoostJodaArchive,
    KnownMimeTypes.Application.VndKahootz,
    KnownMimeTypes.Application.VndKdeKarbon,
    KnownMimeTypes.Application.VndKdeKchart,
    KnownMimeTypes.Application.VndKdeKformula,
    KnownMimeTypes.Application.VndKdeKivio,
    KnownMimeTypes.Application.VndKdeKontour,
    KnownMimeTypes.Application.VndKdeKpresenter,
    KnownMimeTypes.Application.VndKdeKspread,
    KnownMimeTypes.Application.VndKdeKword,
    KnownMimeTypes.Application.VndKenameaapp,
    KnownMimeTypes.Application.VndKidspiration,
    KnownMimeTypes.Application.VndKinar,
    KnownMimeTypes.Application.VndKoan,
    KnownMimeTypes.Application.VndKodakDescriptor,
    KnownMimeTypes.Application.VndLasLasXml,
    KnownMimeTypes.Application.VndLlamagraphicsLifeBalanceDesktop,
    KnownMimeTypes.Application.VndLlamagraphicsLifeBalanceExchangeXml,
    KnownMimeTypes.Application.VndLotus123,
    KnownMimeTypes.Application.VndLotusApproach,
    KnownMimeTypes.Application.VndLotusFreelance,
    KnownMimeTypes.Application.VndLotusNotes,
    KnownMimeTypes.Application.VndLotusOrganizer,
    KnownMimeTypes.Application.VndLotusScreencam,
    KnownMimeTypes.Application.VndLotusWordpro,
    KnownMimeTypes.Application.VndMacportsPortpkg,
    KnownMimeTypes.Application.VndMcd,
    KnownMimeTypes.Application.VndMedcalcdata,
    KnownMimeTypes.Application.VndMediastationCdkey,
    KnownMimeTypes.Application.VndMfer,
    KnownMimeTypes.Application.VndMfmp,
    KnownMimeTypes.Application.VndMicrografxFlo,
    KnownMimeTypes.Application.VndMicrografxIgx,
    KnownMimeTypes.Application.VndMif,
    KnownMimeTypes.Application.VndMobiusDaf,
    KnownMimeTypes.Application.VndMobiusDis,
    KnownMimeTypes.Application.VndMobiusMbk,
    KnownMimeTypes.Application.VndMobiusMqy,
    KnownMimeTypes.Application.VndMobiusMsl,
    KnownMimeTypes.Application.VndMobiusPlc,
    KnownMimeTypes.Application.VndMobiusTxf,
    KnownMimeTypes.Application.VndMophunApplication,
    KnownMimeTypes.Application.VndMophunCertificate,
    KnownMimeTypes.Application.VndMozillaXulXml,
    KnownMimeTypes.Application.VndMsArtgalry,
    KnownMimeTypes.Application.VndMsCabCompressed,
    KnownMimeTypes.Application.VndMsExcel,
    KnownMimeTypes.Application.VndMsExcelAddinMacroenabled12,
    KnownMimeTypes.Application.VndMsExcelSheetBinaryMacroenabled12,
    KnownMimeTypes.Application.VndMsExcelSheetMacroenabled12,
    KnownMimeTypes.Application.VndMsExcelTemplateMacroenabled12,
    KnownMimeTypes.Application.VndMsFontobject,
    KnownMimeTypes.Application.VndMsHtmlhelp,
    KnownMimeTypes.Application.VndMsIms,
    KnownMimeTypes.Application.VndMsLrm,
    KnownMimeTypes.Application.VndMsOfficetheme,
    KnownMimeTypes.Application.VndMsPkiSeccat,
    KnownMimeTypes.Application.VndMsPkiStl,
    KnownMimeTypes.Application.VndMsPowerpoint,
    KnownMimeTypes.Application.VndMsPowerpointAddinMacroenabled12,
    KnownMimeTypes.Application.VndMsPowerpointPresentationMacroenabled12,
    KnownMimeTypes.Application.VndMsPowerpointSlideMacroenabled12,
    KnownMimeTypes.Application.VndMsPowerpointSlideshowMacroenabled12,
    KnownMimeTypes.Application.VndMsPowerpointTemplateMacroenabled12,
    KnownMimeTypes.Application.VndMsProject,
    KnownMimeTypes.Application.VndMsWordDocumentMacroenabled12,
    KnownMimeTypes.Application.VndMsWordTemplateMacroenabled12,
    KnownMimeTypes.Application.VndMsWorks,
    KnownMimeTypes.Application.VndMsWpl,
    KnownMimeTypes.Application.VndMsXpsdocument,
    KnownMimeTypes.Application.VndMseq,
    KnownMimeTypes.Application.VndMusician,
    KnownMimeTypes.Application.VndMuveeStyle,
    KnownMimeTypes.Application.VndNeurolanguageNlu,
    KnownMimeTypes.Application.VndNoblenetDirectory,
    KnownMimeTypes.Application.VndNoblenetSealer,
    KnownMimeTypes.Application.VndNoblenetWeb,
    KnownMimeTypes.Application.VndNokiaNGageData,
    KnownMimeTypes.Application.VndNokiaNGageSymbianInstall,
    KnownMimeTypes.Application.VndNokiaRadioPreset,
    KnownMimeTypes.Application.VndNokiaRadioPresets,
    KnownMimeTypes.Application.VndNovadigmEdm,
    KnownMimeTypes.Application.VndNovadigmEdx,
    KnownMimeTypes.Application.VndNovadigmExt,
    KnownMimeTypes.Application.VndOasisOpendocumentChart,
    KnownMimeTypes.Application.VndOasisOpendocumentChartTemplate,
    KnownMimeTypes.Application.VndOasisOpendocumentDatabase,
    KnownMimeTypes.Application.VndOasisOpendocumentFormula,
    KnownMimeTypes.Application.VndOasisOpendocumentFormulaTemplate,
    KnownMimeTypes.Application.VndOasisOpendocumentGraphics,
    KnownMimeTypes.Application.VndOasisOpendocumentGraphicsTemplate,
    KnownMimeTypes.Application.VndOasisOpendocumentImage,
    KnownMimeTypes.Application.VndOasisOpendocumentImageTemplate,
    KnownMimeTypes.Application.VndOasisOpendocumentPresentation,
    KnownMimeTypes.Application.VndOasisOpendocumentPresentationTemplate,
    KnownMimeTypes.Application.VndOasisOpendocumentSpreadsheet,
    KnownMimeTypes.Application.VndOasisOpendocumentSpreadsheetTemplate,
    KnownMimeTypes.Application.VndOasisOpendocumentText,
    KnownMimeTypes.Application.VndOasisOpendocumentTextMaster,
    KnownMimeTypes.Application.VndOasisOpendocumentTextTemplate,
    KnownMimeTypes.Application.VndOasisOpendocumentTextWeb,
    KnownMimeTypes.Application.VndOlpcSugar,
    KnownMimeTypes.Application.VndOmaDd2Xml,
    KnownMimeTypes.Application.VndOpenofficeorgExtension,
    KnownMimeTypes.Application.VndOpenxmlformatsOfficedocumentPresentationmlPresentation,
    KnownMimeTypes.Application.VndOpenxmlformatsOfficedocumentPresentationmlSlide,
    KnownMimeTypes.Application.VndOpenxmlformatsOfficedocumentPresentationmlSlideshow,
    KnownMimeTypes.Application.VndOpenxmlformatsOfficedocumentPresentationmlTemplate,
    KnownMimeTypes.Application.VndOpenxmlformatsOfficedocumentSpreadsheetmlSheet,
    KnownMimeTypes.Application.VndOpenxmlformatsOfficedocumentSpreadsheetmlTemplate,
    KnownMimeTypes.Application.VndOpenxmlformatsOfficedocumentWordprocessingmlDocument,
    KnownMimeTypes.Application.VndOpenxmlformatsOfficedocumentWordprocessingmlTemplate,
    KnownMimeTypes.Application.VndOsgeoMapguidePackage,
    KnownMimeTypes.Application.VndOsgiDp,
    KnownMimeTypes.Application.VndPalm,
    KnownMimeTypes.Application.VndPawaafile,
    KnownMimeTypes.Application.VndPgFormat,
    KnownMimeTypes.Application.VndPgOsasli,
    KnownMimeTypes.Application.VndPicsel,
    KnownMimeTypes.Application.VndPmiWidget,
    KnownMimeTypes.Application.VndPocketlearn,
    KnownMimeTypes.Application.VndPowerbuilder6,
    KnownMimeTypes.Application.VndPreviewsystemsBox,
    KnownMimeTypes.Application.VndProteusMagazine,
    KnownMimeTypes.Application.VndPublishareDeltaTree,
    KnownMimeTypes.Application.VndPviPtid1,
    KnownMimeTypes.Application.VndQuarkQuarkxpress,
    KnownMimeTypes.Application.VndRealvncBed,
    KnownMimeTypes.Application.VndRecordareMusicxml,
    KnownMimeTypes.Application.VndRecordareMusicxmlXml,
    KnownMimeTypes.Application.VndRigCryptonote,
    KnownMimeTypes.Application.VndRimCod,
    KnownMimeTypes.Application.VndRnRealmedia,
    KnownMimeTypes.Application.VndRoute66Link66Xml,
    KnownMimeTypes.Application.VndSailingtrackerTrack,
    KnownMimeTypes.Application.VndSeemail,
    KnownMimeTypes.Application.VndSema,
    KnownMimeTypes.Application.VndSemd,
    KnownMimeTypes.Application.VndSemf,
    KnownMimeTypes.Application.VndShanaInformedFormdata,
    KnownMimeTypes.Application.VndShanaInformedFormtemplate,
    KnownMimeTypes.Application.VndShanaInformedInterchange,
    KnownMimeTypes.Application.VndShanaInformedPackage,
    KnownMimeTypes.Application.VndSimtechMindmapper,
    KnownMimeTypes.Application.VndSmaf,
    KnownMimeTypes.Application.VndSmartTeacher,
    KnownMimeTypes.Application.VndSolentSdkmXml,
    KnownMimeTypes.Application.VndSpotfireDxp,
    KnownMimeTypes.Application.VndSpotfireSfs,
    KnownMimeTypes.Application.VndStardivisionCalc,
    KnownMimeTypes.Application.VndStardivisionDraw,
    KnownMimeTypes.Application.VndStardivisionImpress,
    KnownMimeTypes.Application.VndStardivisionMath,
    KnownMimeTypes.Application.VndStardivisionWriter,
    KnownMimeTypes.Application.VndStardivisionWriterGlobal,
    KnownMimeTypes.Application.VndStepmaniaStepchart,
    KnownMimeTypes.Application.VndSunXmlCalc,
    KnownMimeTypes.Application.VndSunXmlCalcTemplate,
    KnownMimeTypes.Application.VndSunXmlDraw,
    KnownMimeTypes.Application.VndSunXmlDrawTemplate,
    KnownMimeTypes.Application.VndSunXmlImpress,
    KnownMimeTypes.Application.VndSunXmlImpressTemplate,
    KnownMimeTypes.Application.VndSunXmlMath,
    KnownMimeTypes.Application.VndSunXmlWriter,
    KnownMimeTypes.Application.VndSunXmlWriterGlobal,
    KnownMimeTypes.Application.VndSunXmlWriterTemplate,
    KnownMimeTypes.Application.VndSusCalendar,
    KnownMimeTypes.Application.VndSvd,
    KnownMimeTypes.Application.VndSymbianInstall,
    KnownMimeTypes.Application.VndSyncmlXml,
    KnownMimeTypes.Application.VndSyncmlDmWbxml,
    KnownMimeTypes.Application.VndSyncmlDmXml,
    KnownMimeTypes.Application.VndTaoIntentModuleArchive,
    KnownMimeTypes.Application.VndTmobileLivetv,
    KnownMimeTypes.Application.VndTridTpt,
    KnownMimeTypes.Application.VndTriscapeMxs,
    KnownMimeTypes.Application.VndTrueapp,
    KnownMimeTypes.Application.VndUfdl,
    KnownMimeTypes.Application.VndUiqTheme,
    KnownMimeTypes.Application.VndUmajin,
    KnownMimeTypes.Application.VndUnity,
    KnownMimeTypes.Application.VndUomlXml,
    KnownMimeTypes.Application.VndVcx,
    KnownMimeTypes.Application.VndVisio,
    KnownMimeTypes.Application.VndVisio2013,
    KnownMimeTypes.Application.VndVisionary,
    KnownMimeTypes.Application.VndVsf,
    KnownMimeTypes.Application.VndWapWbxml,
    KnownMimeTypes.Application.VndWapWmlc,
    KnownMimeTypes.Application.VndWapWmlscriptc,
    KnownMimeTypes.Application.VndWebturbo,
    KnownMimeTypes.Application.VndWolframPlayer,
    KnownMimeTypes.Application.VndWordperfect,
    KnownMimeTypes.Application.VndWqd,
    KnownMimeTypes.Application.VndWtStf,
    KnownMimeTypes.Application.VndXara,
    KnownMimeTypes.Application.VndXfdl,
    KnownMimeTypes.Application.VndYamahaHvDic,
    KnownMimeTypes.Application.VndYamahaHvScript,
    KnownMimeTypes.Application.VndYamahaHvVoice,
    KnownMimeTypes.Application.VndYamahaOpenscoreformat,
    KnownMimeTypes.Application.VndYamahaOpenscoreformatOsfpvgXml,
    KnownMimeTypes.Application.VndYamahaSmafAudio,
    KnownMimeTypes.Application.VndYamahaSmafPhrase,
    KnownMimeTypes.Application.VndYellowriverCustomMenu,
    KnownMimeTypes.Application.VndZul,
    KnownMimeTypes.Application.VndZzazzDeckXml,
    KnownMimeTypes.Application.VoicexmlXml,
    KnownMimeTypes.Application.Widget,
    KnownMimeTypes.Application.Winhlp,
    KnownMimeTypes.Application.WsdlXml,
    KnownMimeTypes.Application.WspolicyXml,
    KnownMimeTypes.Application.X7zCompressed,
    KnownMimeTypes.Application.XAbiword,
    KnownMimeTypes.Application.XAceCompressed,
    KnownMimeTypes.Application.XAuthorwareBin,
    KnownMimeTypes.Application.XAuthorwareMap,
    KnownMimeTypes.Application.XAuthorwareSeg,
    KnownMimeTypes.Application.XBcpio,
    KnownMimeTypes.Application.XBittorrent,
    KnownMimeTypes.Application.XBzip,
    KnownMimeTypes.Application.XBzip2,
    KnownMimeTypes.Application.XCdlink,
    KnownMimeTypes.Application.XChat,
    KnownMimeTypes.Application.XChessPgn,
    KnownMimeTypes.Application.XCpio,
    KnownMimeTypes.Application.XCsh,
    KnownMimeTypes.Application.XDebianPackage,
    KnownMimeTypes.Application.XDirector,
    KnownMimeTypes.Application.XDoom,
    KnownMimeTypes.Application.XDtbncxXml,
    KnownMimeTypes.Application.XDtbookXml,
    KnownMimeTypes.Application.XDtbresourceXml,
    KnownMimeTypes.Application.XDvi,
    KnownMimeTypes.Application.XFontBdf,
    KnownMimeTypes.Application.XFontGhostscript,
    KnownMimeTypes.Application.XFontLinuxPsf,
    KnownMimeTypes.Application.XFontOtf,
    KnownMimeTypes.Application.XFontPcf,
    KnownMimeTypes.Application.XFontSnf,
    KnownMimeTypes.Application.XFontTtf,
    KnownMimeTypes.Application.XFontType1,
    KnownMimeTypes.Application.XFontWoff,
    KnownMimeTypes.Application.XFuturesplash,
    KnownMimeTypes.Application.XGnumeric,
    KnownMimeTypes.Application.XGtar,
    KnownMimeTypes.Application.XHdf,
    KnownMimeTypes.Application.XJavaJnlpFile,
    KnownMimeTypes.Application.XLatex,
    KnownMimeTypes.Application.XMobipocketEbook,
    KnownMimeTypes.Application.XMsApplication,
    KnownMimeTypes.Application.XMsWmd,
    KnownMimeTypes.Application.XMsWmz,
    KnownMimeTypes.Application.XMsXbap,
    KnownMimeTypes.Application.XMsaccess,
    KnownMimeTypes.Application.XMsbinder,
    KnownMimeTypes.Application.XMscardfile,
    KnownMimeTypes.Application.XMsclip,
    KnownMimeTypes.Application.XMsdownload,
    KnownMimeTypes.Application.XMsmediaview,
    KnownMimeTypes.Application.XMsmetafile,
    KnownMimeTypes.Application.XMsmoney,
    KnownMimeTypes.Application.XMspublisher,
    KnownMimeTypes.Application.XMsschedule,
    KnownMimeTypes.Application.XMsterminal,
    KnownMimeTypes.Application.XMswrite,
    KnownMimeTypes.Application.XNetcdf,
    KnownMimeTypes.Application.XPkcs12,
    KnownMimeTypes.Application.XPkcs7Certificates,
    KnownMimeTypes.Application.XPkcs7Certreqresp,
    KnownMimeTypes.Application.XRarCompressed,
    KnownMimeTypes.Application.XSh,
    KnownMimeTypes.Application.XShar,
    KnownMimeTypes.Application.XShockwaveFlash,
    KnownMimeTypes.Application.XSilverlightApp,
    KnownMimeTypes.Application.XStuffit,
    KnownMimeTypes.Application.XStuffitx,
    KnownMimeTypes.Application.XSv4cpio,
    KnownMimeTypes.Application.XSv4crc,
    KnownMimeTypes.Application.XTar,
    KnownMimeTypes.Application.XTcl,
    KnownMimeTypes.Application.XTex,
    KnownMimeTypes.Application.XTexTfm,
    KnownMimeTypes.Application.XTexinfo,
    KnownMimeTypes.Application.XUstar,
    KnownMimeTypes.Application.XWaisSource,
    KnownMimeTypes.Application.XX509CaCert,
    KnownMimeTypes.Application.XXfig,
    KnownMimeTypes.Application.XXpinstall,
    KnownMimeTypes.Application.XcapDiffXml,
    KnownMimeTypes.Application.XencXml,
    KnownMimeTypes.Application.XhtmlXml,
    KnownMimeTypes.Application.Xml,
    KnownMimeTypes.Application.XmlDtd,
    KnownMimeTypes.Application.XopXml,
    KnownMimeTypes.Application.XsltXml,
    KnownMimeTypes.Application.XspfXml,
    KnownMimeTypes.Application.XvXml,
    KnownMimeTypes.Application.Yang,
    KnownMimeTypes.Application.YinXml,
    KnownMimeTypes.Application.Zip,
    KnownMimeTypes.Application.XAppleDiskimage,
    KnownMimeTypes.Audio.Adpcm,
    KnownMimeTypes.Audio.Basic,
    KnownMimeTypes.Audio.Midi,
    KnownMimeTypes.Audio.Mp4,
    KnownMimeTypes.Audio.Mpeg,
    KnownMimeTypes.Audio.Ogg,
    KnownMimeTypes.Audio.VndDeceAudio,
    KnownMimeTypes.Audio.VndDigitalWinds,
    KnownMimeTypes.Audio.VndDra,
    KnownMimeTypes.Audio.VndDts,
    KnownMimeTypes.Audio.VndDtsHd,
    KnownMimeTypes.Audio.VndLucentVoice,
    KnownMimeTypes.Audio.VndMsPlayreadyMediaPya,
    KnownMimeTypes.Audio.VndNueraEcelp4800,
    KnownMimeTypes.Audio.VndNueraEcelp7470,
    KnownMimeTypes.Audio.VndNueraEcelp9600,
    KnownMimeTypes.Audio.VndRip,
    KnownMimeTypes.Audio.Webm,
    KnownMimeTypes.Audio.XAac,
    KnownMimeTypes.Audio.XAiff,
    KnownMimeTypes.Audio.XMpegurl,
    KnownMimeTypes.Audio.XMsWax,
    KnownMimeTypes.Audio.XMsWma,
    KnownMimeTypes.Audio.XPnRealaudio,
    KnownMimeTypes.Audio.XPnRealaudioPlugin,
    KnownMimeTypes.Audio.XWav,
    KnownMimeTypes.Chemical.XCdx,
    KnownMimeTypes.Chemical.XCif,
    KnownMimeTypes.Chemical.XCmdf,
    KnownMimeTypes.Chemical.XCml,
    KnownMimeTypes.Chemical.XCsml,
    KnownMimeTypes.Chemical.XXyz,
    KnownMimeTypes.Image.Bmp,
    KnownMimeTypes.Image.Cgm,
    KnownMimeTypes.Image.G3fax,
    KnownMimeTypes.Image.Gif,
    KnownMimeTypes.Image.Ief,
    KnownMimeTypes.Image.Jpeg,
    KnownMimeTypes.Image.Pjpeg,
    KnownMimeTypes.Image.XCitrixJpeg,
    KnownMimeTypes.Image.Ktx,
    KnownMimeTypes.Image.Png,
    KnownMimeTypes.Image.XPng,
    KnownMimeTypes.Image.XCitrixPng,
    KnownMimeTypes.Image.PrsBtif,
    KnownMimeTypes.Image.SvgXml,
    KnownMimeTypes.Image.Tiff,
    KnownMimeTypes.Image.VndAdobePhotoshop,
    KnownMimeTypes.Image.VndDeceGraphic,
    KnownMimeTypes.Image.VndDvbSubtitle,
    KnownMimeTypes.Image.VndDjvu,
    KnownMimeTypes.Image.VndDwg,
    KnownMimeTypes.Image.VndDxf,
    KnownMimeTypes.Image.VndFastbidsheet,
    KnownMimeTypes.Image.VndFpx,
    KnownMimeTypes.Image.VndFst,
    KnownMimeTypes.Image.VndFujixeroxEdmicsMmr,
    KnownMimeTypes.Image.VndFujixeroxEdmicsRlc,
    KnownMimeTypes.Image.VndMsModi,
    KnownMimeTypes.Image.VndNetFpx,
    KnownMimeTypes.Image.VndWapWbmp,
    KnownMimeTypes.Image.VndXiff,
    KnownMimeTypes.Image.Webp,
    KnownMimeTypes.Image.XCmuRaster,
    KnownMimeTypes.Image.XCmx,
    KnownMimeTypes.Image.XFreehand,
    KnownMimeTypes.Image.XIcon,
    KnownMimeTypes.Image.XPcx,
    KnownMimeTypes.Image.XPict,
    KnownMimeTypes.Image.XPortableAnymap,
    KnownMimeTypes.Image.XPortableBitmap,
    KnownMimeTypes.Image.XPortableGraymap,
    KnownMimeTypes.Image.XPortablePixmap,
    KnownMimeTypes.Image.XRgb,
    KnownMimeTypes.Image.XXbitmap,
    KnownMimeTypes.Image.XXpixmap,
    KnownMimeTypes.Image.XXwindowdump,
    KnownMimeTypes.Message.Rfc822,
    KnownMimeTypes.Model.Iges,
    KnownMimeTypes.Model.Mesh,
    KnownMimeTypes.Model.VndColladaXml,
    KnownMimeTypes.Model.VndDwf,
    KnownMimeTypes.Model.VndGdl,
    KnownMimeTypes.Model.VndGtw,
    KnownMimeTypes.Model.VndMts,
    KnownMimeTypes.Model.VndVtu,
    KnownMimeTypes.Model.Vrml,
    KnownMimeTypes.Text.Calendar,
    KnownMimeTypes.Text.Css,
    KnownMimeTypes.Text.Csv,
    KnownMimeTypes.Text.Html,
    KnownMimeTypes.Text.N3,
    KnownMimeTypes.Text.Plain,
    KnownMimeTypes.Text.PrsLinesTag,
    KnownMimeTypes.Text.Richtext,
    KnownMimeTypes.Text.Sgml,
    KnownMimeTypes.Text.TabSeparatedValues,
    KnownMimeTypes.Text.Troff,
    KnownMimeTypes.Text.Turtle,
    KnownMimeTypes.Text.UriList,
    KnownMimeTypes.Text.VndCurl,
    KnownMimeTypes.Text.VndCurlDcurl,
    KnownMimeTypes.Text.VndCurlScurl,
    KnownMimeTypes.Text.VndCurlMcurl,
    KnownMimeTypes.Text.VndFly,
    KnownMimeTypes.Text.VndFmiFlexstor,
    KnownMimeTypes.Text.VndGraphviz,
    KnownMimeTypes.Text.VndIn3d3dml,
    KnownMimeTypes.Text.VndIn3dSpot,
    KnownMimeTypes.Text.VndSunJ2meAppDescriptor,
    KnownMimeTypes.Text.VndWapWml,
    KnownMimeTypes.Text.VndWapWmlscript,
    KnownMimeTypes.Text.XAsm,
    KnownMimeTypes.Text.XC,
    KnownMimeTypes.Text.XFortran,
    KnownMimeTypes.Text.XPascal,
    KnownMimeTypes.Text.XJavaSourceJava,
    KnownMimeTypes.Text.XSetext,
    KnownMimeTypes.Text.XUuencode,
    KnownMimeTypes.Text.XVcalendar,
    KnownMimeTypes.Text.XVcard,
    KnownMimeTypes.Text.PlainBas,
    KnownMimeTypes.Text.Yaml,
    KnownMimeTypes.Video.V3gpp,
    KnownMimeTypes.Video.V3gpp2,
    KnownMimeTypes.Video.H261,
    KnownMimeTypes.Video.H263,
    KnownMimeTypes.Video.H264,
    KnownMimeTypes.Video.Jpeg,
    KnownMimeTypes.Video.Jpm,
    KnownMimeTypes.Video.Mj2,
    KnownMimeTypes.Video.Mp4,
    KnownMimeTypes.Video.Mpeg,
    KnownMimeTypes.Video.Ogg,
    KnownMimeTypes.Video.Quicktime,
    KnownMimeTypes.Video.VndDeceHd,
    KnownMimeTypes.Video.VndDeceMobile,
    KnownMimeTypes.Video.VndDecePd,
    KnownMimeTypes.Video.VndDeceSd,
    KnownMimeTypes.Video.VndDeceVideo,
    KnownMimeTypes.Video.VndFvt,
    KnownMimeTypes.Video.VndMpegurl,
    KnownMimeTypes.Video.VndMsPlayreadyMediaPyv,
    KnownMimeTypes.Video.VndUvvuMp4,
    KnownMimeTypes.Video.VndVivo,
    KnownMimeTypes.Video.Webm,
    KnownMimeTypes.Video.XF4v,
    KnownMimeTypes.Video.XFli,
    KnownMimeTypes.Video.XFlv,
    KnownMimeTypes.Video.XM4v,
    KnownMimeTypes.Video.XMsAsf,
    KnownMimeTypes.Video.XMsWm,
    KnownMimeTypes.Video.XMsWmv,
    KnownMimeTypes.Video.XMsWmx,
    KnownMimeTypes.Video.XMsWvx,
    KnownMimeTypes.Video.XMsvideo,
    KnownMimeTypes.Video.XSgiMovie,
    KnownMimeTypes.XConference.Any,
    KnownMimeTypes.XConference.XCooltalk,
)

@JsExport
val knownMimeTypesMap by lazy {
    knownMimeTypes.associateBy { it.raw }
}

@JsExport
fun findBuiltinMimeType(from: String): MimeType? {
    return knownMimeTypesMap[from]
}
