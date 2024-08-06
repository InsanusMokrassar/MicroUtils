package full

import com.benasher44.uuid.uuid4
import dev.inmo.micro_utils.repos.*
import dev.inmo.micro_utils.repos.cache.full.FullKeyValueCacheRepo
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class FullKeyValueCacheRepoTests {
    @Test
    fun creatingWorksProperly() = runTest {
        val testData = (0 until 1000).associate {
            ("$it-" + uuid4().toString()) to "$it-" + uuid4().toString()
        }
        val updatedTestData = testData.keys.associateWith {
            "$it-" + uuid4().toString()
        }
        val kvCache = MapKeyValueRepo<String, String>()
        val kvRepo = MapKeyValueRepo<String, String>()

        val cacheRepo = FullKeyValueCacheRepo(
            kvRepo,
            kvCache,
            skipStartInvalidate = true
        )

        testData.forEach {
            cacheRepo.set(it.key, it.value)
            assertEquals(it.value, cacheRepo.get(it.key))
            assertEquals(it.value, kvRepo.get(it.key))
            assertEquals(it.value, kvCache.get(it.key))
        }

        updatedTestData.forEach {
            cacheRepo.set(it.key, it.value)
            assertEquals(cacheRepo.get(it.key), it.value)
            assertEquals(kvRepo.get(it.key), it.value)
            assertEquals(kvCache.get(it.key), it.value)
        }
    }
    @Test
    fun precachingWorksProperly() = runTest {
        val testData = (0 until 1000).associate {
            (it.toString() + uuid4().toString()) to uuid4().toString()
        }
        val kvCache = MapKeyValueRepo<String, String>()
        val kvRepo = MapKeyValueRepo<String, String>()
        kvRepo.set(testData)

        val cacheRepo = FullKeyValueCacheRepo(
            kvRepo,
            kvCache
        )


        cacheRepo.getAll().forEach { (id, value) ->
            assertTrue {
                testData[id] == value
            }
        }
    }
    @Test
    fun unsettingWorksProperly() = runTest {
        val testData = (0 until 1000).associate {
            (it.toString() + uuid4().toString()) to uuid4().toString()
        }
        val kvCache = MapKeyValueRepo<String, String>()
        val kvRepo = MapKeyValueRepo<String, String>()
        kvRepo.set(testData)

        val cacheRepo = FullKeyValueCacheRepo(
            kvRepo,
            kvCache
        )

        testData.forEach {
            val id = it.key
            assertTrue {
                cacheRepo.getAll()[id] == it.value
            }

            cacheRepo.unset(id)
            assertFalse {
                cacheRepo.contains(id)
            }
        }
        assertEquals(0, cacheRepo.count())
    }
}
