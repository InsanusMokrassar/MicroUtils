package full

import com.benasher44.uuid.uuid4
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transactionManager
import org.sqlite.JDBC
import java.io.File
import java.sql.Connection

fun filename() = "${uuid4()}.local.sql".also {
    val file = File(it)
    file.createNewFile()
    file.deleteOnExit()
}
fun createDatabase(filename: String) = Database.connect(
    url = "jdbc:sqlite:$filename",
    driver = JDBC::class.qualifiedName!!
).also {
    it.transactionManager.defaultIsolationLevel = Connection.TRANSACTION_SERIALIZABLE
    it.connector().close()
}
