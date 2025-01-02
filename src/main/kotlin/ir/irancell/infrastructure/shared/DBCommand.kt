package ir.irancell.infrastructure.shared

import ir.irancell.infrastructure.InMemoryCaching
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction


class DBCommand(private val database: Database) {

    suspend fun <T> exec( block: () -> T): T {
        return withContext(Dispatchers.IO) {
                transaction(database) {
                    block()
                }
        }
    }
}