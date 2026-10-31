package full

import dev.inmo.micro_utils.repos.KeyValuesRepo
import dev.inmo.micro_utils.repos.common.tests.CommonKeyValuesRepoTests
import dev.inmo.micro_utils.repos.exposed.initTable
import dev.inmo.micro_utils.repos.exposed.onetomany.AbstractExposedKeyValuesRepo
import org.jetbrains.exposed.v1.core.Op
import org.jetbrains.exposed.v1.core.ResultRow
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.core.statements.UpdateBuilder
import org.jetbrains.exposed.v1.jdbc.Database
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
        override val selectByValue: (String) -> Op<Boolean> = { dataColumn.eq(it) }
        override val ResultRow.asObject: String
            get() = get(dataColumn)
        override val selectById: (String) -> Op<Boolean> = { keyColumn.eq(it) }

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
