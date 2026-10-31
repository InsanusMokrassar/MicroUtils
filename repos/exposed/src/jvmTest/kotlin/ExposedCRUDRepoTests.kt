package full

import com.benasher44.uuid.uuid4
import dev.inmo.micro_utils.repos.CRUDRepo
import dev.inmo.micro_utils.repos.common.tests.CommonCRUDRepoTests
import dev.inmo.micro_utils.repos.exposed.AbstractExposedCRUDRepo
import dev.inmo.micro_utils.repos.exposed.initTable
import org.jetbrains.exposed.v1.core.Op
import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.core.statements.InsertStatement
import org.jetbrains.exposed.v1.core.statements.UpdateBuilder
import org.jetbrains.exposed.v1.jdbc.Database
import java.io.File
import kotlin.test.AfterTest
import kotlin.test.BeforeTest

class ExposedCRUDRepoTests : CommonCRUDRepoTests() {
    class Repo(override val database: Database) : AbstractExposedCRUDRepo<Registered, String, New>() {
        val idColumn = text("_id")
        val dataColumn = text("data")

        override val primaryKey: PrimaryKey = PrimaryKey(idColumn)

        override val ResultRow.asId: String
            get() = get(idColumn)
        override val ResultRow.asObject: Registered
            get() = Registered(
                asId,
                get(dataColumn)
            )
        override val selectById: (String) -> Op<Boolean> = { idColumn.eq(it) }

        init {
            initTable()
        }

        override fun update(id: String?, value: New, it: UpdateBuilder<Int>) {
            it[idColumn] = id ?: uuid4().toString()
            it[dataColumn] = value.data
        }

        override fun InsertStatement<Number>.asObject(value: New): Registered {
            return Registered(
                get(idColumn),
                get(dataColumn)
            )
        }
    }
    val filename = filename()
    var database: Database? = null
    override val repoCreator: suspend () -> CRUDRepo<Registered, String, New> = { Repo(database!!) }
    @BeforeTest
    fun beforeTest() {
        database = createDatabase(filename)
    }
    @AfterTest
    fun afterTest() {
        database = null
        File(filename).delete()
    }
}
