package full

import com.benasher44.uuid.uuid4
import dev.inmo.micro_utils.repos.MapCRUDRepo
import dev.inmo.micro_utils.repos.MapKeyValueRepo
import dev.inmo.micro_utils.repos.cache.full.FullCRUDCacheRepo
import dev.inmo.micro_utils.repos.create
import dev.inmo.micro_utils.repos.deleteById
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class FullCRUDCacheRepoTests {
    data class New(
        val data: String
    )
    data class Registered(
        val id: String,
        val data: String
    )
    @Test
    fun creatingWorksProperly() = runTest {
        val testData = (0 until 1000).map {
            ("$it-" + uuid4().toString())
        }
        val updatedTestData = (0 until 1000).map {
            ("$it-" + uuid4().toString())
        }
        val kvCache = MapKeyValueRepo<String, Registered>()
        val crudRepo = MapCRUDRepo<Registered, String, New>(
            { new, id, old ->
                Registered(id, new.data)
            }
        ) {
            val id = uuid4().toString()
            id to Registered(id, it.data)
        }

        val cacheRepo = FullCRUDCacheRepo(
            crudRepo,
            kvCache,
            idGetter = { it.id }
        )

        val registereds = testData.map {
            val created = cacheRepo.create(New(it)).first()
            assertEquals(it, created.data)
            assertEquals(kvCache.get(created.id), created)
            assertEquals(crudRepo.getById(created.id), created)
            created
        }

        cacheRepo.getAll().forEach { (id, value) ->
            assertTrue {
                registereds.first {
                    it.id == id
                } == value
            }
        }
        val updatedRegistereds = registereds.mapIndexed { i, it ->
            val updated = cacheRepo.update(it.id, New(updatedTestData[i])) ?: error("Unable to update data for $it")
            assertEquals(updatedTestData[i], updated.data)
            assertEquals(kvCache.get(updated.id), updated)
            assertEquals(crudRepo.getById(updated.id), updated)
            updated
        }
        cacheRepo.getAll().forEach { (id, value) ->
            assertTrue {
                updatedRegistereds.first {
                    it.id == id
                } == value
            }
        }
    }
    @Test
    fun precachingWorksProperly() = runTest {
        val testData = (0 until 1000).map {
            (it.toString() + uuid4().toString())
        }
        val kvCache = MapKeyValueRepo<String, Registered>()
        val crudRepo = MapCRUDRepo<Registered, String, New>(
            { new, id, old ->
                Registered(id, new.data)
            }
        ) {
            val id = uuid4().toString()
            id to Registered(id, it.data)
        }
        val registereds = crudRepo.create(testData.map { New(it) })

        val cacheRepo = FullCRUDCacheRepo(
            crudRepo,
            kvCache,
            idGetter = { it.id }
        )

        cacheRepo.getAll().forEach { (id, value) ->
            assertTrue {
                registereds.first {
                    it.id == id
                } == value
            }
        }
    }
    @Test
    fun removingWorksProperly() = runTest {
        val testData = (0 until 1000).map {
            (it.toString() + uuid4().toString())
        }
        val kvCache = MapKeyValueRepo<String, Registered>()
        val crudRepo = MapCRUDRepo<Registered, String, New>(
            { new, id, old ->
                Registered(id, new.data)
            }
        ) {
            val id = uuid4().toString()
            id to Registered(id, it.data)
        }
        val registereds = crudRepo.create(testData.map { New(it) })

        val cacheRepo = FullCRUDCacheRepo(
            crudRepo,
            kvCache,
            idGetter = { it.id }
        )

        registereds.forEach {
            val id = it.id
            assertTrue {
                cacheRepo.getAll()[id] == it
            }

            cacheRepo.deleteById(id)
            assertFalse {
                cacheRepo.contains(id)
            }
        }
        assertEquals(0, cacheRepo.count())
    }
}
