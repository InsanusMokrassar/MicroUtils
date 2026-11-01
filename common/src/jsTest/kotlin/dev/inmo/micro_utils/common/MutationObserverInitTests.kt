package dev.inmo.micro_utils.common

import org.w3c.dom.MutationObserverInit as NativeMutationObserverInit
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertSame

class MutationObserverInitTests {
    private fun keys(options: NativeMutationObserverInit): List<String> =
        js("Object.keys(options)").unsafeCast<Array<String>>().sorted()

    @Test
    fun emptyOptionsHaveNoProperties() {
        assertEquals(emptyList(), keys(MutationObserverInit()))
    }

    @Test
    fun childListOptionsOmitUnspecifiedProperties() {
        val options = MutationObserverInit(childList = true, subtree = true)
        assertEquals(listOf("childList", "subtree"), keys(options))
        assertEquals(true, options.childList)
        assertEquals(true, options.subtree)
    }

    @Test
    fun explicitNullOptionsAreOmitted() {
        val options = MutationObserverInit(
            childList = null,
            attributes = null,
            characterData = null,
            subtree = null,
            attributeOldValue = null,
            characterDataOldValue = null,
            attributeFilter = null
        )
        assertEquals(emptyList(), keys(options))
    }

    @Test
    fun explicitFalseAndEmptyFilterArePreserved() {
        val filter = emptyArray<String>()
        val options = MutationObserverInit(false, false, false, false, false, false, filter)
        assertEquals(
            listOf("attributeFilter", "attributeOldValue", "attributes", "characterData", "characterDataOldValue", "childList", "subtree"),
            keys(options)
        )
        assertEquals(false, options.childList)
        assertEquals(false, options.attributes)
        assertEquals(false, options.characterData)
        assertEquals(false, options.subtree)
        assertEquals(false, options.attributeOldValue)
        assertEquals(false, options.characterDataOldValue)
        assertSame(filter, options.attributeFilter)
        assertContentEquals(emptyArray(), options.attributeFilter)
    }

    @Test
    fun filtersAndOldValueOptionsDoNotForceObservationFlags() {
        val filter = arrayOf("class", "data-state")
        val options = MutationObserverInit(
            attributeFilter = filter,
            attributeOldValue = true,
            characterDataOldValue = true
        )
        assertEquals(listOf("attributeFilter", "attributeOldValue", "characterDataOldValue"), keys(options))
        assertSame(filter, options.attributeFilter)
        assertEquals(true, options.attributeOldValue)
        assertEquals(true, options.characterDataOldValue)
    }

    @Test
    fun eachCallCreatesIndependentOptions() {
        val first = MutationObserverInit(childList = true)
        val second = MutationObserverInit(attributes = true)
        first.subtree = true
        assertEquals(listOf("childList", "subtree"), keys(first))
        assertEquals(listOf("attributes"), keys(second))
    }
}
