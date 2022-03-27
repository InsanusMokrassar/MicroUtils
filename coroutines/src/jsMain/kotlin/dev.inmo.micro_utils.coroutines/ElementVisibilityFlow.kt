package dev.inmo.micro_utils.coroutines

import dev.inmo.micro_utils.common.onRemoved
import dev.inmo.micro_utils.common.onVisibilityChanged
import kotlinx.coroutines.flow.*
import org.w3c.dom.Element

fun Element.visibilityFlow(): Flow<Boolean> = channelFlow {
    var previousData: Boolean? = null

    val observer = onVisibilityChanged { intersectionRatio, _ ->
        val currentData = intersectionRatio > 0
        if (currentData != previousData) {
            trySend(currentData)
        }
        previousData = currentData
    }

    val removeObserver = onRemoved {
        observer.disconnect()
        close()
    }

    invokeOnClose {
        observer.disconnect()
        removeObserver.disconnect()
    }
}
