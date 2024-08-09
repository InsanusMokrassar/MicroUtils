import com.benasher44.uuid.uuid4
import dev.inmo.micro_utils.repos.CRUDRepo
import dev.inmo.micro_utils.repos.create
import dev.inmo.micro_utils.repos.deleteById
import korlibs.time.seconds
import kotlinx.coroutines.test.runTest
import kotlin.test.*

abstract class CommonCRUDRepoTests : CommonRepoTests<CRUDRepo<CommonCRUDRepoTests.Registered, String, CommonCRUDRepoTests.New>>() {
    data class New(
        val data: String
    )
    data class Registered(
        val id: String,
        val data: String
    )

    open fun creatingWorksProperly() = runTest(timeout = 120.seconds) {
        val crudRepo = repoCreator()
        val testData = (0 until testSequencesSize).map {
            ("$it-" + uuid4().toString())
        }
        val updatedTestData = (0 until 1000).map {
            ("$it-" + uuid4().toString())
        }

        val registereds = testData.map {
            val created = crudRepo.create(New(it)).first()
            assertEquals(it, created.data)
            assertEquals(crudRepo.getById(created.id), created)
            created
        }

        crudRepo.getAll().forEach { (id, value) ->
            assertTrue {
                registereds.first {
                    it.id == id
                } == value
            }
        }
        val updatedRegistereds = registereds.mapIndexed { i, it ->
            val updated = crudRepo.update(it.id, New(updatedTestData[i])) ?: error("Unable to update data for $it")
            assertEquals(updatedTestData[i], updated.data)
            assertEquals(crudRepo.getById(updated.id), updated)
            updated
        }
        crudRepo.getAll().forEach { (id, value) ->
            assertTrue {
                updatedRegistereds.first {
                    it.id == id
                } == value
            }
        }
    }
    @Test
    open fun removingWorksProperly() = runTest {
        val crudRepo = repoCreator()
        val testData = (0 until testSequencesSize).map {
            (it.toString() + uuid4().toString())
        }
        val registereds = crudRepo.create(testData.map { New(it) })

        registereds.forEach {
            val id = it.id
            assertTrue {
                crudRepo.getAll()[id] == it
            }

            crudRepo.deleteById(id)
            assertFalse {
                crudRepo.contains(id)
            }
        }
        assertEquals(0, crudRepo.count())
    }
}
