package full

import dev.inmo.micro_utils.repos.KeyValueRepo
import dev.inmo.micro_utils.repos.MapKeyValueRepo
import dev.inmo.micro_utils.repos.common.tests.CommonKeyValueRepoTests

class InMemoryKeyValueRepoTests : CommonKeyValueRepoTests() {
    override val repoCreator: suspend () -> KeyValueRepo<String, String> = { MapKeyValueRepo() }
}
