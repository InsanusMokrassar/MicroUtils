package full

import com.benasher44.uuid.uuid4
import dev.inmo.micro_utils.repos.CRUDRepo
import dev.inmo.micro_utils.repos.MapCRUDRepo
import dev.inmo.micro_utils.repos.common.tests.CommonCRUDRepoTests

class InMemoryCRUDRepoTests : CommonCRUDRepoTests() {
    override val repoCreator: suspend () -> CRUDRepo<Registered, String, New> = {
        MapCRUDRepo(
            { new, id, old ->
                Registered(id, new.data)
            }
        ) {
            val id = uuid4().toString()
            id to Registered(id, it.data)
        }
    }
}
