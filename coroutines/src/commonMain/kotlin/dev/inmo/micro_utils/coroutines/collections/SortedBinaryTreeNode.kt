package dev.inmo.micro_utils.coroutines.collections

import dev.inmo.micro_utils.coroutines.SmartRWLocker
import dev.inmo.micro_utils.coroutines.waitReadRelease
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
class SortedBinaryTreeNode<T>(
    val data: T,
    internal val comparator: Comparator<T>,
) : Iterable<SortedBinaryTreeNode<T>> {
    internal var leftNode: SortedBinaryTreeNode<T>? = null
    internal var rightNode: SortedBinaryTreeNode<T>? = null
    internal val locker: SmartRWLocker by lazy {
        SmartRWLocker()
    }

    suspend fun getLeftNode() = locker.withReadAcquire {
        leftNode
    }

    suspend fun getRightNode() = locker.withReadAcquire {
        rightNode
    }

    suspend fun getLeft() = getLeftNode() ?.data

    suspend fun getRight() = getRightNode() ?.data

    override fun equals(other: Any?): Boolean {
        return other === this || (other is SortedBinaryTreeNode<*> && other.data == data && other.rightNode == rightNode && other.leftNode == leftNode)
    }

    override fun hashCode(): Int {
        return data.hashCode() * 31 + rightNode.hashCode() + leftNode.hashCode()
    }

    suspend fun size(): Int {
        return locker.withReadAcquire {
            1 + (leftNode ?.size() ?: 0) + (rightNode ?.size() ?: 0)
        }
    }

    /**
     * This [Iterator] will run from less to greater values of nodes starting the
     * [dev.inmo.micro_utils.coroutines.collections.SortedBinaryTreeNode]-receiver. Due to non-suspending
     * nature of [iterator] builder, this [Iterator] **DO NOT** guarantee consistent content due to iterations. It
     * means, that tree can be changed during to iteration process
     */
    override fun iterator(): Iterator<SortedBinaryTreeNode<T>> = iterator {
        leftNode ?.let {
            it.iterator().forEach { yield(it) }
        }
        yield(this@SortedBinaryTreeNode)
        rightNode ?.let {
            it.iterator().forEach { yield(it) }
        }
    }

    override fun toString(): String {
        return "$data($leftNode;$rightNode)"
    }

    companion object {
        operator fun <T : Comparable<T>> invoke(
            data: T,
        ) = SortedBinaryTreeNode(
            data,
            data.createComparator()
        )
    }
}

/**
 * Will add subnode in tree if there are no any node with [newData]
 *
 * * If [newData] is greater than [SortedBinaryTreeNode.data] of currently checking node,
 * will be used [SortedBinaryTreeNode.rightNode]
 * * If [newData] is equal to [SortedBinaryTreeNode.data] of currently
 * checking node - will be returned currently checking node
 * * If [newData] is less than [SortedBinaryTreeNode.data] of currently
 * checking node - will be used [SortedBinaryTreeNode.leftNode]
 *
 * This process will continue until function will not find place to put [SortedBinaryTreeNode] with data or
 * [SortedBinaryTreeNode] with [SortedBinaryTreeNode.data] same as [newData] will be found
 */
private suspend fun <T> SortedBinaryTreeNode<T>.addSubNode(
    subNode: SortedBinaryTreeNode<T>,
    skipLockers: Set<SmartRWLocker> = emptySet()
): SortedBinaryTreeNode<T> {
    var currentlyChecking = this
    val lockedLockers = mutableSetOf<SmartRWLocker>()
    try {
        while (coroutineContext.job.isActive) {
            if (currentlyChecking.locker !in lockedLockers && currentlyChecking.locker !in skipLockers) {
                currentlyChecking.locker.lockWrite()
                lockedLockers.add(currentlyChecking.locker)
            }
            val left = currentlyChecking.leftNode
            val right = currentlyChecking.rightNode
            val comparingResult = currentlyChecking.comparator.compare(subNode.data, currentlyChecking.data)
            val isGreater = comparingResult > 0
            when {
                comparingResult == 0 -> return currentlyChecking
                isGreater && right == null -> {
                    currentlyChecking.rightNode = subNode
                    return subNode
                }
                isGreater && right != null -> {
                    currentlyChecking = right
                }
                left == null -> {
                    currentlyChecking.leftNode = subNode
                    return subNode
                }
                else -> {
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
 * Will add subnode in tree if there are no any node with [newData]
 *
 * * If [newData] is greater than [SortedBinaryTreeNode.data] of currently checking node,
 * will be used [SortedBinaryTreeNode.rightNode]
 * * If [newData] is equal to [SortedBinaryTreeNode.data] of currently
 * checking node - will be returned currently checking node
 * * If [newData] is less than [SortedBinaryTreeNode.data] of currently
 * checking node - will be used [SortedBinaryTreeNode.leftNode]
 *
 * This process will continue until function will not find place to put [SortedBinaryTreeNode] with data or
 * [SortedBinaryTreeNode] with [SortedBinaryTreeNode.data] same as [newData] will be found
 */
suspend fun <T> SortedBinaryTreeNode<T>.addSubNode(newData: T): SortedBinaryTreeNode<T> {
    return addSubNode(
        SortedBinaryTreeNode(newData, comparator)
    )
}

suspend fun <T> SortedBinaryTreeNode<T>.findParentNode(data: T): SortedBinaryTreeNode<T>? {
    var currentParent: SortedBinaryTreeNode<T>? = null
    var currentlyChecking: SortedBinaryTreeNode<T>? = this
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
            val comparingResult = currentlyChecking.comparator.compare(data, currentlyChecking.data)
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
 * Will remove (detach) node from tree starting with [this] [SortedBinaryTreeNode]
 *
 * @return If data were found, [Pair] where [Pair.first] is the parent node where from [Pair.second] has been detached;
 *         null otherwise
 */
suspend fun <T> SortedBinaryTreeNode<T>.removeSubNode(data: T): Pair<SortedBinaryTreeNode<T>, SortedBinaryTreeNode<T>>? {
    val onFoundToRemoveCallback: suspend SortedBinaryTreeNode<T>.(left: SortedBinaryTreeNode<T>?, right: SortedBinaryTreeNode<T>?) -> Unit = { left, right ->
        left ?.also { leftNode -> addSubNode(leftNode, setOf(locker)) }
        right ?.also { rightNode -> addSubNode(rightNode, setOf(locker)) }
    }
    while (coroutineContext.job.isActive) {
        val foundParentNode = findParentNode(data) ?: return null
        foundParentNode.locker.withWriteLock {
            val left = foundParentNode.leftNode
            val right = foundParentNode.rightNode
            when {
                left != null && left.comparator.compare(data, left.data) == 0 -> {
                    foundParentNode.leftNode = null
                    foundParentNode.onFoundToRemoveCallback(left.leftNode, left.rightNode)
                    return foundParentNode to left
                }
                right != null && right.comparator.compare(data, right.data) == 0 -> {
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
suspend fun <T> SortedBinaryTreeNode<T>.findNode(data: T): SortedBinaryTreeNode<T>? {
    var currentlyChecking: SortedBinaryTreeNode<T>? = this
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
            val comparingResult = currentlyChecking.comparator.compare(data, currentlyChecking.data)
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
suspend fun <T> SortedBinaryTreeNode<T>.contains(data: T): Boolean = findNode(data) != null

suspend fun <T> SortedBinaryTreeNode<T>.findNodesInRange(from: T, to: T, fromInclusiveMode: Boolean, toInclusiveMode: Boolean): Set<SortedBinaryTreeNode<T>> {
    val results = mutableSetOf<SortedBinaryTreeNode<T>>()
    val leftToCheck = mutableSetOf(this)
    val lockedLockers = mutableSetOf<SmartRWLocker>()
    val fromComparingFun: (SortedBinaryTreeNode<T>) -> Boolean = if (fromInclusiveMode) {
        { it.comparator.compare(from, it.data) <= 0 }
    } else {
        { it.comparator.compare(from, it.data) < 0 }
    }
    val toComparingFun: (SortedBinaryTreeNode<T>) -> Boolean = if (toInclusiveMode) {
        { it.comparator.compare(to, it.data) >= 0 }
    } else {
        { it.comparator.compare(to, it.data) > 0 }
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
                currentlyChecking.comparator.compare(to, currentlyChecking.data) < 0 -> currentlyChecking.leftNode ?.let { leftToCheck.add(it) }
                currentlyChecking.comparator.compare(from, currentlyChecking.data) > 0 -> currentlyChecking.rightNode ?.let { leftToCheck.add(it) }
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
suspend fun <T> SortedBinaryTreeNode<T>.findNodesInRange(from: T, to: T): Set<SortedBinaryTreeNode<T>> = findNodesInRange(
    from = from,
    to = to,
    fromInclusiveMode = true,
    toInclusiveMode = true
)
suspend fun <T> SortedBinaryTreeNode<T>.findNodesInRangeExcluding(from: T, to: T): Set<SortedBinaryTreeNode<T>> = findNodesInRange(
    from = from,
    to = to,
    fromInclusiveMode = false,
    toInclusiveMode = false
)
suspend fun <T : Comparable<T>> SortedBinaryTreeNode<T>.findNodesInRange(range: ClosedRange<T>): Set<SortedBinaryTreeNode<T>> = findNodesInRange(
    from = range.start,
    to = range.endInclusive,
)