package full

import com.benasher44.uuid.uuid4
import dev.inmo.micro_utils.repos.*
import dev.inmo.micro_utils.repos.cache.full.FullKeyValuesCacheRepo
import dev.inmo.micro_utils.repos.pagination.maxPagePagination
import korlibs.time.days
import korlibs.time.seconds
import korlibs.time.years
import kotlinx.coroutines.test.runTest
import kotlin.test.*

class FullKeyValuesCacheRepoTests {
    @Test
    fun creatingWorksProperly() = runTest(timeout = 120.seconds) {
        val testData = (0 until 1000).associate {
            ("$it-" + uuid4().toString()) to (0 until 1000).map {
                "$it-" + uuid4().toString()
            }.sorted()
        }
        val updatedTestData = testData.keys.associateWith {
            (0 until 1000).map {
                "$it-" + uuid4().toString()
            }.sorted()
        }
        val addedData = testData.keys.associateWith {
            "$it-" + uuid4().toString()
        }
        val kvCache = MapKeyValueRepo<String, List<String>>()
        val kvRepo = MapKeyValuesRepo<String, String>()

        val cacheRepo = FullKeyValuesCacheRepo(
            kvRepo,
            kvCache
        )

        testData.forEach {
            cacheRepo.set(it.key, it.value)
            assertContentEquals(it.value, cacheRepo.getAll(it.key))
            assertContentEquals(it.value, kvRepo.getAll(it.key))
            assertContentEquals(it.value, kvCache.get(it.key) ?.sorted())
        }

        updatedTestData.forEach {
            cacheRepo.set(it.key, it.value)
            assertContentEquals(it.value, cacheRepo.getAll(it.key))
            assertContentEquals(it.value, kvRepo.getAll(it.key))
            assertContentEquals(it.value, kvCache.get(it.key) ?.sorted())
        }

        addedData.forEach {
            cacheRepo.add(it.key, it.value)
            assertTrue(cacheRepo.contains(it.key, it.value))
            assertTrue(kvRepo.contains(it.key, it.value))
            assertTrue(kvCache.get(it.key) !!.contains(it.value))
        }
    }
}
