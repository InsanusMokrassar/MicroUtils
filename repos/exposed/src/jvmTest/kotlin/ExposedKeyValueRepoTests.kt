package full

import CommonKeyValueRepoTests
import com.benasher44.uuid.uuid4
import dev.inmo.micro_utils.repos.*
import dev.inmo.micro_utils.repos.exposed.initTable
import dev.inmo.micro_utils.repos.exposed.keyvalue.AbstractExposedKeyValueRepo
import korlibs.time.seconds
import kotlinx.coroutines.test.TestResult
import kotlinx.coroutines.test.runTest
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import java.io.File
import kotlin.test.*

class ExposedKeyValueRepoTests : CommonKeyValueRepoTests() {
    class Repo(override val database: Database) : AbstractExposedKeyValueRepo<String, String>(database) {
        override val keyColumn = text("_id")
        val dataColumn = text("data")

        override val primaryKey: PrimaryKey = PrimaryKey(keyColumn)

        override val ResultRow.asKey: String
            get() = get(keyColumn)
        override val selectByValue: ISqlExpressionBuilder.(String) -> Op<Boolean> = { dataColumn.eq(it) }
        override val ResultRow.asObject: String
            get() = get(dataColumn)
        override val selectById: ISqlExpressionBuilder.(String) -> Op<Boolean> = { keyColumn.eq(it) }

        init {
            initTable()
        }

        override fun update(k: String, v: String, it: UpdateBuilder<Int>) {
            it[keyColumn] = k
            it[dataColumn] = v
        }

        override fun insertKey(k: String, v: String, it: UpdateBuilder<Int>) {
            it[keyColumn] = k
        }
    }
    val filename = filename()
    var database: Database? = null
    @BeforeTest
    fun beforeTest() {
        database = createDatabase(filename)
    }
    @AfterTest
    fun afterTest() {
        database = null
        File(filename).delete()
    }

    override val repoCreator: suspend () -> KeyValueRepo<String, String> = { Repo(database!!) }

    @Test
    override fun creatingWorksProperly(): TestResult {
        return super.creatingWorksProperly()
    }

    @Test
    override fun unsettingWorksProperly(): TestResult {
        return super.unsettingWorksProperly()
    }
}
