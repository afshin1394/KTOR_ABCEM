package ir.irancell.infrastructure.shared

import io.ktor.websocket.*
import ir.irancell.application.shared.CacheUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.KSerializer
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction

class DBQuery(private val database: Database, private val cacheUtil: CacheUtil, private val ttlCache: Long) {

    suspend fun <T> exec(serializer: KSerializer<T>, cacheKey: String, block: () -> T): T {
        // Run the database operation in a transaction
        return withContext(Dispatchers.IO) {
            cacheUtil.getOrPut(cacheKey, ttlCache, serializer) {
                transaction(database) {
                    block()
                }
            }
        }
    }
}

