package full

import dev.inmo.micro_utils.repos.KeyValuesRepo
import dev.inmo.micro_utils.repos.MapKeyValuesRepo
import dev.inmo.micro_utils.repos.common.tests.CommonKeyValuesRepoTests

class InMemoryKeyValuesRepoTests : CommonKeyValuesRepoTests() {
    override val repoCreator: suspend () -> KeyValuesRepo<String, String> = { MapKeyValuesRepo() }
}
