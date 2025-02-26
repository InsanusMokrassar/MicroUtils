import dev.inmo.micro_utils.coroutines.collections.SortedBinaryTreeNode
import dev.inmo.micro_utils.coroutines.collections.addSubNode
import dev.inmo.micro_utils.coroutines.collections.findNode
import dev.inmo.micro_utils.coroutines.collections.findNodesInRange
import dev.inmo.micro_utils.coroutines.collections.findParentNode
import dev.inmo.micro_utils.coroutines.collections.removeSubNode
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SortedBinaryTreeNodeTests {
    @Test
    fun insertOnZeroLevelWorks() = runTest {
        val zeroNode = SortedBinaryTreeNode(0)
        zeroNode.addSubNode(1)
        zeroNode.addSubNode(-1)

        assertEquals(0, zeroNode.data)
        assertEquals(1, zeroNode.getRightNode() ?.data)
        assertEquals(-1, zeroNode.getLeftNode() ?.data)
    }
    @Test
    fun searchOnZeroLevelWorks() = runTest {
        val zeroNode = SortedBinaryTreeNode(0)
        val oneNode = zeroNode.addSubNode(1)
        val minusOneNode = zeroNode.addSubNode(-1)

        val assertingNodesToSearchQuery = mapOf(
            setOf(oneNode) to (1 .. 1),
            setOf(zeroNode, oneNode) to (0 .. 1),
            setOf(minusOneNode, zeroNode, oneNode) to (-1 .. 1),
            setOf(minusOneNode, zeroNode) to (-1 .. 0),
            setOf(minusOneNode) to (-1 .. -1),
            setOf(zeroNode) to (0 .. 0),
        )

        assertingNodesToSearchQuery.forEach {
            val foundData = zeroNode.findNodesInRange(it.value)
            assertTrue(foundData.containsAll(it.key))
            assertTrue(it.key.containsAll(foundData))
        }
    }
    @Test
    fun deepInsertOnWorks() = runTest {
        val zeroNode = SortedBinaryTreeNode(0)
        val rangeRadius = 500
        val nodes = mutableMapOf<Int, SortedBinaryTreeNode<Int>>()
        for (i in -rangeRadius .. rangeRadius) {
            nodes[i] = zeroNode.addSubNode(i)
        }

        for (i in -rangeRadius .. rangeRadius) {
            val expectedNode = nodes.getValue(i)
            val foundNode = zeroNode.findNode(i)

            assertTrue(expectedNode === foundNode)

            if (expectedNode === zeroNode) continue

            val parentNode = zeroNode.findParentNode(i)
            assertTrue(
                parentNode ?.getLeftNode() === expectedNode || parentNode ?.getRightNode() === expectedNode,
                "It is expected, that parent node with data ${parentNode ?.data} will be parent of ${expectedNode.data}, but its left subnode is ${parentNode ?.getLeftNode() ?.data} and right one is ${parentNode ?.getRightNode() ?.data}"
            )
        }

        val sourceTreeSize = zeroNode.size()
        assertTrue(sourceTreeSize == nodes.size)
        assertTrue(sourceTreeSize == (rangeRadius * 2 + 1))

        for (i in -rangeRadius .. rangeRadius) {
            val expectedNode = nodes.getValue(i)
            val parentNode = zeroNode.findParentNode(i)

            if (parentNode == null && i == zeroNode.data && expectedNode === zeroNode) continue

            assertTrue(parentNode != null, "It is expected, that parent node of ${expectedNode.data} will not be null")

            assertTrue(
                parentNode.getLeftNode() === expectedNode || parentNode.getRightNode() === expectedNode,
                "It is expected, that parent node with data ${parentNode ?.data} will be parent of ${expectedNode.data}, but its left subnode is ${parentNode ?.getLeftNode() ?.data} and right one is ${parentNode ?.getRightNode() ?.data}"
            )

            val removeResult = zeroNode.removeSubNode(i)
            assertTrue(removeResult ?.first === parentNode)
            assertTrue(removeResult.second === expectedNode)

            nodes[i] = zeroNode.addSubNode(i)
            assertTrue(nodes[i] != null)
            assertTrue(nodes[i] != expectedNode)
            assertTrue(nodes[i] ?.data == i)
        }

        assertTrue(sourceTreeSize == zeroNode.size())

        for (i in -rangeRadius .. rangeRadius) {
            val expectedNode = nodes.getValue(i)
            val foundNode = zeroNode.findNode(i)

            assertTrue(expectedNode === foundNode)

            if (expectedNode === zeroNode) continue

            val parentNode = zeroNode.findParentNode(i)
            assertTrue(
                parentNode ?.getLeftNode() === expectedNode || parentNode ?.getRightNode() === expectedNode,
                "It is expected, that parent node with data ${parentNode ?.data} will be parent of ${expectedNode.data}, but its left subnode is ${parentNode ?.getLeftNode() ?.data} and right one is ${parentNode ?.getRightNode() ?.data}"
            )
        }

        var previousData = -rangeRadius - 1
        for (node in zeroNode) {
            assertTrue(nodes[node.data] === node)
            assertTrue(previousData == node.data - 1)
            previousData = node.data
        }

        assertTrue(sourceTreeSize == zeroNode.size())
    }
    @Test
    fun deepInsertIteratorWorking() = runTest {
        val zeroNode = SortedBinaryTreeNode(0)
        val rangeRadius = 500
        val nodes = mutableMapOf<Int, SortedBinaryTreeNode<Int>>()
        for (i in -rangeRadius .. rangeRadius) {
            nodes[i] = zeroNode.addSubNode(i)
        }

        var previousData = -rangeRadius - 1
        for (node in zeroNode) {
            assertTrue(nodes[node.data] === node)
            assertTrue(previousData == node.data - 1)
            previousData = node.data
        }
        assertTrue(previousData == rangeRadius)
    }
}