import com.benasher44.uuid.uuid4
import dev.inmo.micro_utils.repos.*
import korlibs.time.seconds
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import kotlin.test.*

abstract class CommonKeyValuesRepoTests : CommonRepoTests<KeyValuesRepo<String, String>>() {
    open fun creatingWorksProperly() = runTest(timeout = 120.seconds) {
        val repo = repoCreator()
        val testData = (0 until testSequencesSize).associate {
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

        updatedTestData.map {
            launch {
                repo.set(it.key, it.value)
                assertContentEquals(it.value.sorted(), repo.getAll(it.key).sorted())
            }
        }.joinAll()

        updatedTestData.map {
            launch {
                repo.set(it.key, it.value)
                val all = repo.getAll(it.key)
                assertContentEquals(it.value.sorted(), all.sorted())
            }
        }.joinAll()

        addedData.forEach {
            repo.add(it.key, it.value)
            assertTrue(repo.contains(it.key, it.value))
        }
    }
}
