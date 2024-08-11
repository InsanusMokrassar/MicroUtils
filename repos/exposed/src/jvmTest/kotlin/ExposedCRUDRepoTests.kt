package full

import com.benasher44.uuid.uuid4
import dev.inmo.micro_utils.repos.CRUDRepo
import dev.inmo.micro_utils.repos.common.tests.CommonCRUDRepoTests
import dev.inmo.micro_utils.repos.exposed.AbstractExposedCRUDRepo
import dev.inmo.micro_utils.repos.exposed.initTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.ISqlExpressionBuilder
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateBuilder
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
        override val selectById: ISqlExpressionBuilder.(String) -> Op<Boolean> = { idColumn.eq(it) }

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
