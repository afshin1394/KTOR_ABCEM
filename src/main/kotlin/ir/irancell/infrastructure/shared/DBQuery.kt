package ir.irancell.infrastructure.shared

import ir.irancell.application.shared.InMemoryCaching
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.KSerializer
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction

class DBQuery(private val database: Database, private val inMemoryCaching: InMemoryCaching) {

     suspend fun <T> exec(hasCaching : Boolean = true,serializer: KSerializer<T>, cacheKey: String, ttlCache: Long, block: () -> T): T {
        return withContext(Dispatchers.IO) {
            if(hasCaching) {
                inMemoryCaching.getOrPut(cacheKey, ttlCache, serializer) {
                    transaction(database) {
                        block()
                    }
                }
            }else{
                transaction(database) {
                    block()
                }
            }
        }

    }
}

