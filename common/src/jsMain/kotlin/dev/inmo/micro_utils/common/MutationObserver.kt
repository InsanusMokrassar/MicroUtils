package dev.inmo.micro_utils.common

import org.w3c.dom.MutationObserverInit as NativeMutationObserverInit

/**
 * Creates options for [org.w3c.dom.MutationObserver] without adding unspecified properties.
 *
 * Unlike the Kotlin DOM factory, this factory leaves absent options out of the JavaScript object. In particular,
 * an absent [attributeFilter] must not become a null sequence, and absent [attributes] and [characterData] must not
 * override the browser defaults implied by their filters or old-value options.
 *
 * Null arguments are omitted. Explicit false values and empty arrays are preserved. The browser remains responsible
 * for validating the options passed to [org.w3c.dom.MutationObserver.observe].
 */
fun MutationObserverInit(
    childList: Boolean? = null,
    attributes: Boolean? = null,
    characterData: Boolean? = null,
    subtree: Boolean? = null,
    attributeOldValue: Boolean? = null,
    characterDataOldValue: Boolean? = null,
    attributeFilter: Array<String>? = null
): NativeMutationObserverInit = js("{}").unsafeCast<NativeMutationObserverInit>().apply {
    if (childList != null) this.childList = childList
    if (attributes != null) this.attributes = attributes
    if (characterData != null) this.characterData = characterData
    if (subtree != null) this.subtree = subtree
    if (attributeOldValue != null) this.attributeOldValue = attributeOldValue
    if (characterDataOldValue != null) this.characterDataOldValue = characterDataOldValue
    if (attributeFilter != null) this.attributeFilter = attributeFilter
}
