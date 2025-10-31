package dev.inmo.micro_utils.resources.compose

import androidx.compose.runtime.Composable
import dev.inmo.micro_utils.language_codes.IetfLang

@Composable
expect fun getCurrentLocale(): IetfLang?
