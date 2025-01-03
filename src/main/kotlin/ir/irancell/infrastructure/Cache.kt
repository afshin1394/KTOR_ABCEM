package ir.irancell.infrastructure


import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json

import redis.clients.jedis.Jedis
import redis.clients.jedis.exceptions.JedisConnectionException

class InMemoryCaching(private val redisClient: Jedis) {

    fun <T> getOrPut(
        key: String,
        ttl: Long = 60,
        serializer: KSerializer<T>,
        query: () -> T
    ): T {
        try {
            val cachedData = redisClient.get(key)
            if (cachedData != null) {
                return Json.decodeFromString(serializer, cachedData)
            }
        } catch (e: JedisConnectionException) {
            print("$e")
        } catch (e: Exception) {
            print("$e")

        }

        // Proceed to execute the query if data is not in cache or if Redis is down
        val result = query()

        try {
            // Try storing the result back in Redis
            redisClient.setex(key, ttl, Json.encodeToString(serializer, result))
        } catch (e: JedisConnectionException) {
            print("$e")
        } catch (e: Exception) {
            print("$e")
        }

        return result
    }
    suspend fun invalidateCache(cacheKey: List<String>) {
        // Delete the cache key to invalidate it
        redisClient.del(*cacheKey.toTypedArray())
    }
}

