package full

import CommonKeyValueRepoTests
import dev.inmo.micro_utils.repos.*
import kotlinx.coroutines.test.TestResult
import kotlin.test.*

class InMemoryKeyValueRepoTests : CommonKeyValueRepoTests() {
    override val repoCreator: suspend () -> KeyValueRepo<String, String> = { MapKeyValueRepo() }

    @Test
    override fun creatingWorksProperly(): TestResult {
        return super.creatingWorksProperly()
    }

    @Test
    override fun unsettingWorksProperly(): TestResult {
        return super.unsettingWorksProperly()
    }
}
