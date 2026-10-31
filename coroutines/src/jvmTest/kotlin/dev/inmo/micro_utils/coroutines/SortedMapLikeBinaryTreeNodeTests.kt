package dev.inmo.micro_utils.coroutines

import dev.inmo.micro_utils.coroutines.collections.*
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.seconds

class SortedMapLikeBinaryTreeNodeTests {
    @Test
    fun insertOnZeroLevelWorks() = runTest {
        val zeroNode = SortedMapLikeBinaryTreeNode(0, 0)
        zeroNode.upsertSubNode(1, 1)
        zeroNode.upsertSubNode(-1, -1)

        assertEquals(0, zeroNode.key)
        assertEquals(1, zeroNode.getRightNode() ?.key)
        assertEquals(-1, zeroNode.getLeftNode() ?.key)

        assertEquals(0, zeroNode.findNode(0) ?.value)
        assertEquals(1, zeroNode.findNode(1) ?.value)
        assertEquals(-1, zeroNode.findNode(-1) ?.value)
    }
    @Test
    fun searchOnZeroLevelWorks() = runTest {
        val zeroNode = SortedMapLikeBinaryTreeNode(0, 0)
        val oneNode = zeroNode.upsertSubNode(1, 1)
        val minusOneNode = zeroNode.upsertSubNode(-1, -1)

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
    fun deepReInsertOnWorks() = runTest(timeout = 300.seconds) {
        var zeroNode = SortedMapLikeBinaryTreeNode(0, 0)
        val rangeRadius = 500
        val nodes = mutableMapOf<Int, SortedMapLikeBinaryTreeNode<Int, Int>>()
        for (i in -rangeRadius .. rangeRadius) {
            nodes[i] = zeroNode.upsertSubNode(i, i)
            if (i == zeroNode.key) {
                zeroNode = nodes.getValue(i)
            }
        }

        for (i in -rangeRadius .. rangeRadius) {
            val expectedNode = nodes.getValue(i)
            val foundNode = zeroNode.findNode(i)

            assertEquals(expectedNode, foundNode)

            if (expectedNode === zeroNode) continue

            val parentNode = zeroNode.findParentNode(i)
            assertTrue(
                parentNode ?.getLeftNode() === expectedNode || parentNode ?.getRightNode() === expectedNode,
                "It is expected, that parent node with data ${parentNode ?.key} will be parent of ${expectedNode.key}, but its left subnode is ${parentNode ?.getLeftNode() ?.key} and right one is ${parentNode ?.getRightNode() ?.key}"
            )
            assertTrue(
                foundNode != null && expectedNode.deepEquals(foundNode)
            )

            zeroNode.upsertSubNode(i, -i)
            val foundModifiedNode = zeroNode.findNode(i)
            assertEquals(foundNode ?.value, foundModifiedNode ?.value ?.times(-1))
        }
    }
    @Test
    fun deepInsertOnWorks() = runTest(timeout = 240.seconds) {
        val zeroNode = SortedMapLikeBinaryTreeNode(0, 0)
        val rangeRadius = 500
        val nodes = mutableMapOf<Int, SortedMapLikeBinaryTreeNode<Int, Int>>()
        for (i in -rangeRadius .. rangeRadius) {
            if (zeroNode.key != i) {
                nodes[i] = zeroNode.upsertSubNode(i, i)
            }
        }
        nodes[zeroNode.key] = zeroNode

        for (i in -rangeRadius .. rangeRadius) {
            val expectedNode = nodes.getValue(i)
            val foundNode = zeroNode.findNode(i)

            assertTrue(expectedNode === foundNode)

            if (expectedNode === zeroNode) continue

            val parentNode = zeroNode.findParentNode(i)
            assertTrue(
                parentNode ?.getLeftNode() === expectedNode || parentNode ?.getRightNode() === expectedNode,
                "It is expected, that parent node with data ${parentNode ?.key} will be parent of ${expectedNode.key}, but its left subnode is ${parentNode ?.getLeftNode() ?.key} and right one is ${parentNode ?.getRightNode() ?.key}"
            )
        }

        val sourceTreeSize = zeroNode.size()

        var previousData = -rangeRadius - 1
        for (node in zeroNode) {
            assertTrue(nodes[node.key] === node)
            assertTrue(previousData == node.key - 1)
            previousData = node.key
        }

        assertTrue(sourceTreeSize == zeroNode.size())
    }
}