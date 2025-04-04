package dev.inmo.micro_utils.common

import kotlinx.browser.window
import org.w3c.dom.Element
import org.w3c.dom.css.CSSStyleDeclaration

sealed class Visibility
data object Visible : Visibility()
data object Invisible : Visibility()
data object Gone : Visibility()

var CSSStyleDeclaration.visibilityState: Visibility
    get() = when {
        display == "none" -> Gone
        visibility == "hidden" -> Invisible
        else -> Visible
    }
    set(value) {
        when (value) {
            Visible -> {
                if (display == "none") {
                    display = "initial"
                }
                visibility = "visible"
            }
            Invisible -> {
                if (display == "none") {
                    display = "initial"
                }
                visibility = "hidden"
            }
            Gone -> {
                display = "none"
            }
        }
    }
inline var Element.visibilityState: Visibility
    get() = window.getComputedStyle(this).visibilityState
    set(value) {
        window.getComputedStyle(this).visibilityState = value
    }

inline val Element.isVisible: Boolean
    get() = visibilityState == Visible
inline val Element.isInvisible: Boolean
    get() = visibilityState == Invisible
inline val Element.isGone: Boolean
    get() = visibilityState == Gone
