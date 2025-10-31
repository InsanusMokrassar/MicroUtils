package dev.inmo.micro_utils.resources.compose

import androidx.compose.runtime.Composable
import dev.inmo.micro_utils.strings.StringResource

@Suppress("unused")
@Composable
fun StringResource.composeTranslation(): String {
    return translation(getCurrentLocale())
}
