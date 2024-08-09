package full

import CommonKeyValuesRepoTests
import dev.inmo.micro_utils.repos.*
import kotlinx.coroutines.test.TestResult
import kotlin.test.*

class InMemoryKeyValuesRepoTests : CommonKeyValuesRepoTests() {
    override val repoCreator: suspend () -> KeyValuesRepo<String, String> = { MapKeyValuesRepo() }
    @Test
    override fun creatingWorksProperly(): TestResult {
        return super.creatingWorksProperly()
    }
}
