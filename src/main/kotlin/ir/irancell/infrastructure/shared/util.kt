package ir.irancell.infrastructure.shared

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ColumnType
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.PreparedStatement

fun Table.nvarchar(name: String, length: Int): Column<String> =
    registerColumn(name, NVarcharColumnType(length))

class NVarcharColumnType(private val length: Int) : ColumnType<String>() {
    override fun sqlType(): String = "NVARCHAR($length)"

    override fun valueFromDB(value: Any): String {
        return when (value) {
            is String -> value
            is java.sql.Clob -> value.characterStream.readText() // Handle CLOB if NVARCHAR is mapped to CLOB in some DBs
            else -> value.toString()
        }
    }
}
suspend fun <T> dbTransaction(database: Database,block: () -> T): T {
    // Run the database operation in a transaction
    return transaction(database) { block() }
}


suspend fun <T> dbQuery(database: Database,block: () -> T): T {
    // Run the database operation in a transaction
    return transaction(database) { block() }
}


