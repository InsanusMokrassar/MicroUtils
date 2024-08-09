import com.benasher44.uuid.uuid4
import dev.inmo.micro_utils.repos.*
import korlibs.time.seconds
import kotlinx.coroutines.test.runTest
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

abstract class CommonKeyValueRepoTests : CommonRepoTests<KeyValueRepo<String, String>>() {
    open fun creatingWorksProperly() = runTest(timeout = 120.seconds) {
        val repo = repoCreator()
        val testData = (0 until testSequencesSize).associate {
            ("$it-" + uuid4().toString()) to "$it-" + uuid4().toString()
        }
        val updatedTestData = testData.keys.associateWith {
            "$it-" + uuid4().toString()
        }

        testData.forEach {
            repo.set(it.key, it.value)
            assertEquals(it.value, repo.get(it.key))
        }

        updatedTestData.forEach {
            repo.set(it.key, it.value)
            assertEquals(repo.get(it.key), it.value)
        }
    }
    open fun unsettingWorksProperly() = runTest {
        val repo = repoCreator()
        val testData = (0 until testSequencesSize).associate {
            (it.toString() + uuid4().toString()) to uuid4().toString()
        }

        repo.set(testData)

        testData.forEach {
            val id = it.key
            assertTrue {
                repo.getAll()[id] == it.value
            }

            repo.unset(id)
            assertFalse {
                repo.contains(id)
            }
        }
        assertEquals(0, repo.count())
    }
}
