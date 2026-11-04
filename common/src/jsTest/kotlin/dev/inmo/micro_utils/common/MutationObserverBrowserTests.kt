package dev.inmo.micro_utils.common

import kotlinx.browser.document
import org.w3c.dom.MutationObserver
import org.w3c.dom.events.Event
import org.w3c.dom.events.EventListener
import kotlin.js.Promise
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertNull
import kotlin.test.assertSame
import kotlin.test.assertTrue

/** Runs only in the browser: jsNodeTest excludes this class rather than reporting DOM checks as passing. */
class MutationObserverBrowserTests {
    @Test
    fun childListSubtreeRecordsCanBeDrainedAndDisconnected() {
        val root = document.createElement("div")
        val nested = document.createElement("div")
        val child = document.createElement("span")
        root.appendChild(nested)
        val observer = MutationObserver { _, _ -> }
        try {
            observer.observe(root, MutationObserverOptions(childList = true, subtree = true))
            nested.appendChild(child)
            val record = observer.takeRecords().single()
            assertEquals("childList", record.type)
            assertSame(nested, record.target)
            assertSame(child, record.addedNodes.item(0))
            assertEquals(0, record.removedNodes.length)
            assertNull(record.attributeName)
            assertTrue(observer.takeRecords().isEmpty())
            observer.disconnect()
            nested.removeChild(child)
            assertTrue(observer.takeRecords().isEmpty())
        } finally {
            observer.disconnect()
        }
    }

    @Test
    fun attributeFilterEnablesObservationAndPreservesOldValues() {
        val target = document.createElement("div")
        target.setAttribute("data-state", "old")
        val observer = MutationObserver { _, _ -> }
        try {
            observer.observe(target, MutationObserverOptions(attributeFilter = arrayOf("data-state"), attributeOldValue = true))
            target.setAttribute("ignored", "value")
            target.setAttribute("data-state", "new")
            val record = observer.takeRecords().single()
            assertEquals("attributes", record.type)
            assertSame(target, record.target)
            assertEquals("data-state", record.attributeName)
            assertEquals("old", record.oldValue)
            assertNull(record.attributeNamespace)
        } finally {
            observer.disconnect()
        }
    }

    @Test
    fun characterDataOldValueEnablesObservation() {
        val target = document.createTextNode("old")
        val observer = MutationObserver { _, _ -> }
        try {
            observer.observe(target, MutationObserverOptions(characterDataOldValue = true))
            target.data = "new"
            val record = observer.takeRecords().single()
            assertEquals("characterData", record.type)
            assertSame(target, record.target)
            assertEquals("old", record.oldValue)
        } finally {
            observer.disconnect()
        }
    }

    @Test
    fun nullAttributeFilterIsOmittedInsteadOfConvertedToASequence() {
        val target = document.createElement("div")
        val observer = MutationObserver { _, _ -> }
        try {
            observer.observe(target, MutationObserverOptions(childList = true, attributeFilter = null))
            target.appendChild(document.createElement("span"))
            assertEquals("childList", observer.takeRecords().single().type)
        } finally {
            observer.disconnect()
        }
    }

    @Test
    fun emptyAttributeFilterDoesNotObserveAllAttributes() {
        val target = document.createElement("div")
        val observer = MutationObserver { _, _ -> }
        try {
            observer.observe(target, MutationObserverOptions(attributeFilter = emptyArray()))
            target.setAttribute("data-state", "new")
            assertTrue(observer.takeRecords().isEmpty())
        } finally {
            observer.disconnect()
        }
    }

    @Test
    fun nativeValidationRejectsContradictoryOptions() {
        val target = document.createElement("div")
        val observer = MutationObserver { _, _ -> }
        try {
            assertFails { observer.observe(target, MutationObserverOptions()) }
            assertFails {
                observer.observe(target, MutationObserverOptions(attributes = false, attributeFilter = arrayOf("class")))
            }
            assertFails {
                observer.observe(target, MutationObserverOptions(characterData = false, characterDataOldValue = true))
            }
        } finally {
            observer.disconnect()
        }
    }

    @Test
    fun onRemovedCallsBackOnceAndDisconnects(): Promise<Unit> {
        val parent = document.createElement("div")
        val child = document.createElement("span")
        var calls = 0
        var observer: MutationObserver? = null
        return Promise.resolve(Unit).then {
            parent.appendChild(child)
            document.body!!.appendChild(parent)
            observer = child.onRemoved { calls++ }
            parent.removeChild(child)
        }.then {
            assertEquals(1, calls)
            parent.appendChild(child)
            parent.removeChild(child)
        }.then {
            assertEquals(1, calls)
        }.finally {
            observer?.disconnect()
            parent.parentNode?.removeChild(parent)
        }
    }

    @Test
    fun onClickOutsideStopsListeningAfterRemoval(): Promise<Unit> {
        val target = document.createElement("div")
        var calls = 0
        var listener: EventListener? = null
        return Promise.resolve(Unit).then {
            document.body!!.appendChild(target)
            listener = target.onClickOutside { calls++ }
            document.dispatchEvent(Event("click"))
            assertEquals(1, calls)
            target.parentNode!!.removeChild(target)
        }.then {
            document.dispatchEvent(Event("click"))
            assertEquals(1, calls)
        }.finally {
            listener?.let { document.removeEventListener("click", it) }
            target.parentNode?.removeChild(target)
        }
    }
}
