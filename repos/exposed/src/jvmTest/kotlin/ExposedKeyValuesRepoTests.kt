package full

import dev.inmo.micro_utils.repos.KeyValuesRepo
import dev.inmo.micro_utils.repos.common.tests.CommonKeyValuesRepoTests
import dev.inmo.micro_utils.repos.exposed.initTable
import dev.inmo.micro_utils.repos.exposed.onetomany.AbstractExposedKeyValuesRepo
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.ISqlExpressionBuilder
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import java.io.File
import kotlin.test.AfterTest
import kotlin.test.BeforeTest

class ExposedKeyValuesRepoTests : CommonKeyValuesRepoTests() {
    override val testSequencesSize: Int = 100
    class Repo(override val database: Database) : AbstractExposedKeyValuesRepo<String, String>(database) {

        override val keyColumn = text("_id")
        val dataColumn = text("data")

        override val ResultRow.asKey: String
            get() = get(keyColumn)
        override val selectByValue: ISqlExpressionBuilder.(String) -> Op<Boolean> = { dataColumn.eq(it) }
        override val ResultRow.asObject: String
            get() = get(dataColumn)
        override val selectById: ISqlExpressionBuilder.(String) -> Op<Boolean> = { keyColumn.eq(it) }

        init {
            initTable()
        }

        override fun insert(k: String, v: String, it: UpdateBuilder<Int>) {
            it[keyColumn] = k
            it[dataColumn] = v
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

    override val repoCreator: suspend () -> KeyValuesRepo<String, String> = { Repo(database!!) }
}
