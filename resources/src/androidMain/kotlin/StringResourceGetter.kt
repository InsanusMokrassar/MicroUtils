package dev.inmo.micro_utils.strings

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import dev.inmo.micro_utils.language_codes.toIetfLanguageCode

fun StringResource.translation(configuration: Configuration): String {
    val locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        configuration.locales[0]
    } else {
        configuration.locale
    }

    return translation(locale.toIetfLanguageCode())
}
fun StringResource.translation(resources: Resources): String = translation(resources.configuration)
fun StringResource.translation(context: Context): String = translation(context.resources)

fun Configuration.translation(resource: StringResource): String = resource.translation(this)
fun Resources.translation(resource: StringResource): String = configuration.translation(resource)
fun Context.translation(resource: StringResource): String = resources.translation(resource)
