package dev.inmo.micro_utils.common.compose

import org.jetbrains.compose.web.attributes.ATarget

fun openLink(link: String, mode: ATarget = ATarget.Blank, features: String = "") = dev.inmo.micro_utils.common.openLink(
    link,
    mode.targetStr,
    features
)

