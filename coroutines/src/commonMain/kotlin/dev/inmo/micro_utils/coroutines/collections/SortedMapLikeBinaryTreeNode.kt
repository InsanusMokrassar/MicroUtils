package dev.inmo.micro_utils.coroutines.collections

import dev.inmo.micro_utils.coroutines.SmartRWLocker
import dev.inmo.micro_utils.coroutines.withReadAcquire
import dev.inmo.micro_utils.coroutines.withWriteLock
import kotlinx.coroutines.job
import kotlinx.serialization.Serializable
import kotlin.coroutines.coroutineContext

/**
 * Creates simple [Comparator] which will use [compareTo] of [T] for both objects
 */
private fun <T : Comparable<C>, C : T> T.createComparator() = Comparator<C> { o1, o2 -> o1.compareTo(o2) }

@Serializable
class SortedMapLikeBinaryTreeNode<K, V>(
    val key: K,
    val value: V,
    internal val comparator: Comparator<K>,
) : Iterable<SortedMapLikeBinaryTreeNode<K, V>> {
    internal var leftNode: SortedMapLikeBinaryTreeNode<K, V>? = null
    internal var rightNode: SortedMapLikeBinaryTreeNode<K, V>? = null
    internal val locker: SmartRWLocker by lazy {
        SmartRWLocker()
    }

    suspend fun getLeftNode() = locker.withReadAcquire {
        leftNode
    }

    suspend fun getRightNode() = locker.withReadAcquire {
        rightNode
    }

    suspend fun getLeftKey() = getLeftNode() ?.key
    suspend fun getLeftValue() = getLeftNode() ?.value

    suspend fun getRightKey() = getRightNode() ?.value
    suspend fun getRightValue() = getRightNode() ?.value

    override fun equals(other: Any?): Boolean {
        return other === this || (other is SortedMapLikeBinaryTreeNode<*, *> && other.key == key && other.rightNode == rightNode && other.leftNode == leftNode)
    }

    override fun hashCode(): Int {
        return key.hashCode() * 31 + rightNode.hashCode() + leftNode.hashCode()
    }

    suspend fun size(): Int {
        return locker.withReadAcquire {
            1 + (leftNode ?.size() ?: 0) + (rightNode ?.size() ?: 0)
        }
    }

    /**
     * This [Iterator] will run from less to greater values of nodes starting the
     * [dev.inmo.micro_utils.coroutines.collections.SortedMapLikeBinaryTreeNode]-receiver. Due to non-suspending
     * nature of [iterator] builder, this [Iterator] **DO NOT** guarantee consistent content due to iterations. It
     * means, that tree can be changed during to iteration process
     */
    override fun iterator(): Iterator<SortedMapLikeBinaryTreeNode<K, V>> = iterator {
        leftNode ?.let {
            it.iterator().forEach { yield(it) }
        }
        yield(this@SortedMapLikeBinaryTreeNode)
        rightNode ?.let {
            it.iterator().forEach { yield(it) }
        }
    }

    override fun toString(): String {
        return "$key($leftNode;$rightNode)"
    }

    companion object {
        operator fun <K : Comparable<K>, V> invoke(
            key: K,
            value: V
        ) = SortedMapLikeBinaryTreeNode(
            key,
            value,
            key.createComparator()
        )
    }
}

/**
 * Will add subnode in tree if there are no any node with [newData]
 *
 * * If [newData] is greater than [SortedMapLikeBinaryTreeNode.key] of currently checking node,
 * will be used [SortedMapLikeBinaryTreeNode.rightNode]
 * * If [newData] is equal to [SortedMapLikeBinaryTreeNode.key] of currently
 * checking node - will be returned currently checking node
 * * If [newData] is less than [SortedMapLikeBinaryTreeNode.key] of currently
 * checking node - will be used [SortedMapLikeBinaryTreeNode.leftNode]
 *
 * This process will continue until function will not find place to put [SortedMapLikeBinaryTreeNode] with data or
 * [SortedMapLikeBinaryTreeNode] with [SortedMapLikeBinaryTreeNode.key] same as [newData] will be found
 *
 * @param replaceMode Will replace only value if node already exists
 */
private suspend fun <K, V> SortedMapLikeBinaryTreeNode<K, V>.upsertSubNode(
    subNode: SortedMapLikeBinaryTreeNode<K, V>,
    skipLockers: Set<SmartRWLocker> = emptySet(),
    replaceMode: Boolean
): SortedMapLikeBinaryTreeNode<K, V> {
    var currentlyChecking = this
    var latestParent: SortedMapLikeBinaryTreeNode<K, V>? = null
    val lockedLockers = mutableSetOf<SmartRWLocker>()
    try {
        while (coroutineContext.job.isActive) {
            if (currentlyChecking.locker !in lockedLockers && currentlyChecking.locker !in skipLockers) {
                currentlyChecking.locker.lockWrite()
                lockedLockers.add(currentlyChecking.locker)
            }
            val left = currentlyChecking.leftNode
            val right = currentlyChecking.rightNode
            val comparingResult = currentlyChecking.comparator.compare(subNode.key, currentlyChecking.key)
            val isGreater = comparingResult > 0
            when {
                comparingResult == 0 -> {
                    val resultNode = if (replaceMode) {
                        subNode
                    } else {
                        val newNode = SortedMapLikeBinaryTreeNode(
                            subNode.key,
                            subNode.value,
                            currentlyChecking.comparator,
                        )
                        newNode.leftNode = currentlyChecking.leftNode
                        newNode.rightNode = currentlyChecking.rightNode
                        newNode
                    }

                    latestParent ?.let {
                        when {
                            it.leftNode === currentlyChecking -> it.leftNode = resultNode
                            it.rightNode === currentlyChecking -> it.rightNode = resultNode
                        }
                    }

                    return resultNode
                }
                isGreater && right == null -> {
                    currentlyChecking.rightNode = subNode
                    return subNode
                }
                isGreater && right != null -> {
                    latestParent = currentlyChecking
                    currentlyChecking = right
                }
                left == null -> {
                    currentlyChecking.leftNode = subNode
                    return subNode
                }
                else -> {
                    latestParent = currentlyChecking
                    currentlyChecking = left
                }
            }
        }
    } finally {
        lockedLockers.forEach {
            runCatching { it.unlockWrite() }
        }
    }
    error("Unable to add node")
}

/**
 * Will add subnode in tree if there are no any node with [key]
 *
 * * If [key] is greater than [SortedMapLikeBinaryTreeNode.key] of currently checking node,
 * will be used [SortedMapLikeBinaryTreeNode.rightNode]
 * * If [key] is equal to [SortedMapLikeBinaryTreeNode.key] of currently
 * checking node - will be returned currently checking node
 * * If [key] is less than [SortedMapLikeBinaryTreeNode.key] of currently
 * checking node - will be used [SortedMapLikeBinaryTreeNode.leftNode]
 *
 * This process will continue until function will not find place to put [SortedMapLikeBinaryTreeNode] with data or
 * [SortedMapLikeBinaryTreeNode] with [SortedMapLikeBinaryTreeNode.key] same as [key] will be found
 */
suspend fun <K, V> SortedMapLikeBinaryTreeNode<K, V>.upsertSubNode(
    key: K,
    value: V
): SortedMapLikeBinaryTreeNode<K, V> {
    return upsertSubNode(
        SortedMapLikeBinaryTreeNode(key, value, comparator),
        replaceMode = false
    )
}

suspend fun <K, V> SortedMapLikeBinaryTreeNode<K, V>.findParentNode(data: K): SortedMapLikeBinaryTreeNode<K, V>? {
    var currentParent: SortedMapLikeBinaryTreeNode<K, V>? = null
    var currentlyChecking: SortedMapLikeBinaryTreeNode<K, V>? = this
    val lockedLockers = mutableSetOf<SmartRWLocker>()
    try {
        while (coroutineContext.job.isActive) {
            if (currentlyChecking == null) {
                return null
            }
            if (currentlyChecking.locker !in lockedLockers) {
                currentlyChecking.locker.acquireRead()
                lockedLockers.add(currentlyChecking.locker)
            }
            val comparingResult = currentlyChecking.comparator.compare(data, currentlyChecking.key)
            when {
                comparingResult > 0 -> {
                    currentParent = currentlyChecking
                    currentlyChecking = currentlyChecking.rightNode
                    continue
                }
                comparingResult < 0 -> {
                    currentParent = currentlyChecking
                    currentlyChecking = currentlyChecking.leftNode
                    continue
                }
                else -> return currentParent
            }
        }
    } finally {
        lockedLockers.forEach {
            runCatching { it.releaseRead() }
        }
    }
    error("Unable to find node")
}

/**
 * Will remove (detach) node from tree starting with [this] [SortedMapLikeBinaryTreeNode]
 *
 * @return If data were found, [Pair] where [Pair.first] is the parent node where from [Pair.second] has been detached;
 *         null otherwise
 */
suspend fun <K, V> SortedMapLikeBinaryTreeNode<K, V>.removeSubNode(data: K): Pair<SortedMapLikeBinaryTreeNode<K, V>, SortedMapLikeBinaryTreeNode<K, V>>? {
    val onFoundToRemoveCallback: suspend SortedMapLikeBinaryTreeNode<K, V>.(left: SortedMapLikeBinaryTreeNode<K, V>?, right: SortedMapLikeBinaryTreeNode<K, V>?) -> Unit = { left, right ->
        left ?.also { leftNode -> upsertSubNode(leftNode, setOf(locker), replaceMode = true) }
        right ?.also { rightNode -> upsertSubNode(rightNode, setOf(locker), replaceMode = true) }
    }
    while (coroutineContext.job.isActive) {
        val foundParentNode = findParentNode(data) ?: return null
        foundParentNode.locker.withWriteLock {
            val left = foundParentNode.leftNode
            val right = foundParentNode.rightNode
            when {
                left != null && left.comparator.compare(data, left.key) == 0 -> {
                    foundParentNode.leftNode = null
                    foundParentNode.onFoundToRemoveCallback(left.leftNode, left.rightNode)
                    return foundParentNode to left
                }
                right != null && right.comparator.compare(data, right.key) == 0 -> {
                    foundParentNode.rightNode = null
                    foundParentNode.onFoundToRemoveCallback(right.leftNode, right.rightNode)
                    return foundParentNode to right
                }
                else -> {
                    return@withWriteLock // data has been changed, new search required
                }
            }
        }
    }
    error("Unable to remove node")
}
suspend fun <K, V> SortedMapLikeBinaryTreeNode<K, V>.findNode(key: K): SortedMapLikeBinaryTreeNode<K, V>? {
    var currentlyChecking: SortedMapLikeBinaryTreeNode<K, V>? = this
    val lockedLockers = mutableSetOf<SmartRWLocker>()
    try {
        while (coroutineContext.job.isActive) {
            if (currentlyChecking == null) {
                return null
            }
            if (currentlyChecking.locker !in lockedLockers) {
                currentlyChecking.locker.acquireRead()
                lockedLockers.add(currentlyChecking.locker)
            }
            val comparingResult = currentlyChecking.comparator.compare(key, currentlyChecking.key)
            when {
                comparingResult > 0 -> {
                    currentlyChecking = currentlyChecking.rightNode
                    continue
                }
                comparingResult < 0 -> {
                    currentlyChecking = currentlyChecking.leftNode
                    continue
                }
                else -> return currentlyChecking
            }
        }
    } finally {
        lockedLockers.forEach {
            runCatching { it.releaseRead() }
        }
    }
    error("Unable to find node")
}
suspend fun <K, V> SortedMapLikeBinaryTreeNode<K, V>.contains(data: K): Boolean = findNode(data) != null

suspend fun <K, V> SortedMapLikeBinaryTreeNode<K, V>.findNodesInRange(from: K, to: K, fromInclusiveMode: Boolean, toInclusiveMode: Boolean): Set<SortedMapLikeBinaryTreeNode<K, V>> {
    val results = mutableSetOf<SortedMapLikeBinaryTreeNode<K, V>>()
    val leftToCheck = mutableSetOf(this)
    val lockedLockers = mutableSetOf<SmartRWLocker>()
    val fromComparingFun: (SortedMapLikeBinaryTreeNode<K, V>) -> Boolean = if (fromInclusiveMode) {
        { it.comparator.compare(from, it.key) <= 0 }
    } else {
        { it.comparator.compare(from, it.key) < 0 }
    }
    val toComparingFun: (SortedMapLikeBinaryTreeNode<K, V>) -> Boolean = if (toInclusiveMode) {
        { it.comparator.compare(to, it.key) >= 0 }
    } else {
        { it.comparator.compare(to, it.key) > 0 }
    }
    try {
        while (coroutineContext.job.isActive && leftToCheck.isNotEmpty()) {
            val currentlyChecking = leftToCheck.first()
            leftToCheck.remove(currentlyChecking)
            if (currentlyChecking in results) {
                continue
            }
            currentlyChecking.locker.acquireRead()
            lockedLockers.add(currentlyChecking.locker)
            if (fromComparingFun(currentlyChecking) && toComparingFun(currentlyChecking)) {
                results.add(currentlyChecking)
                currentlyChecking.leftNode ?.let { leftToCheck.add(it) }
                currentlyChecking.rightNode ?.let { leftToCheck.add(it) }
                continue
            }
            when {
                currentlyChecking.comparator.compare(to, currentlyChecking.key) < 0 -> currentlyChecking.leftNode ?.let { leftToCheck.add(it) }
                currentlyChecking.comparator.compare(from, currentlyChecking.key) > 0 -> currentlyChecking.rightNode ?.let { leftToCheck.add(it) }
            }
        }
        return results.toSet()
    } finally {
        lockedLockers.forEach {
            runCatching { it.releaseRead() }
        }
    }
    error("Unable to find nodes range")
}
suspend fun <K, V> SortedMapLikeBinaryTreeNode<K, V>.deepEquals(other: SortedMapLikeBinaryTreeNode<K, V>): Boolean {
    val leftToCheck = mutableSetOf(this)
    val othersToCheck = mutableSetOf(other)
    val lockedLockers = mutableSetOf<SmartRWLocker>()
    try {
        while (leftToCheck.isNotEmpty() && othersToCheck.isNotEmpty()) {
            val thisToCheck = leftToCheck.first()
            leftToCheck.remove(thisToCheck)

            val otherToCheck = othersToCheck.first()
            othersToCheck.remove(otherToCheck)

            if (thisToCheck.locker !in lockedLockers) {
                thisToCheck.locker.acquireRead()
                lockedLockers.add(thisToCheck.locker)
            }
            if (otherToCheck.locker !in lockedLockers) {
                otherToCheck.locker.acquireRead()
                lockedLockers.add(otherToCheck.locker)
            }

            if (thisToCheck.key != otherToCheck.key || thisToCheck.value != otherToCheck.value) {
                return false
            }

            if ((thisToCheck.leftNode == null).xor(otherToCheck.leftNode == null)) {
                return false
            }
            if ((thisToCheck.rightNode == null).xor(otherToCheck.rightNode == null)) {
                return false
            }

            thisToCheck.leftNode?.let { leftToCheck.add(it) }
            thisToCheck.rightNode?.let { leftToCheck.add(it) }

            otherToCheck.leftNode?.let { othersToCheck.add(it) }
            otherToCheck.rightNode?.let { othersToCheck.add(it) }
        }
    } finally {
        lockedLockers.forEach {
            runCatching { it.releaseRead() }
        }
    }

    return leftToCheck.isEmpty() && othersToCheck.isEmpty()
}
suspend fun <K, V> SortedMapLikeBinaryTreeNode<K, V>.findNodesInRange(from: K, to: K): Set<SortedMapLikeBinaryTreeNode<K, V>> = findNodesInRange(
    from = from,
    to = to,
    fromInclusiveMode = true,
    toInclusiveMode = true
)
suspend fun <K, V> SortedMapLikeBinaryTreeNode<K, V>.findNodesInRangeExcluding(from: K, to: K): Set<SortedMapLikeBinaryTreeNode<K, V>> = findNodesInRange(
    from = from,
    to = to,
    fromInclusiveMode = false,
    toInclusiveMode = false
)
suspend fun <K : Comparable<K>, V> SortedMapLikeBinaryTreeNode<K, V>.findNodesInRange(range: ClosedRange<K>): Set<SortedMapLikeBinaryTreeNode<K, V>> = findNodesInRange(
    from = range.start,
    to = range.endInclusive,
)