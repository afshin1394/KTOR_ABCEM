package ir.irancell.infrastructure


import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.core.context.GlobalContext
import org.koin.core.module.Module
import redis.clients.jedis.Jedis
import redis.clients.jedis.exceptions.JedisConnectionException

fun createRedisClient(): Jedis {
    return Jedis("localhost", 6379) // Assuming Redis is running locally on port 6379
}

fun startRedisHealthChecker(module: Module) {
     val logger = java.util.logging.Logger.getLogger("RedisHealthChecker")

    CoroutineScope(Dispatchers.IO).launch {
        while (true) {
            try {
                val redisClient = GlobalContext.get().get<Jedis>()
                redisClient.ping() // Ping to check the health of Redis
            } catch (e: JedisConnectionException) {
                logger.warning("Redis server is down. Attempting to recreate the Redis connection...")
                recreateRedisClient(module)
            }
            delay(5000) // Check Redis health every 5 seconds
        }
    }
}

fun recreateRedisClient(module: Module) {
    val logger = java.util.logging.Logger.getLogger("RedisHealthChecker")

    try {
        // Stop Koin
        GlobalContext.stopKoin()
        // Restart Koin with updated Redis module
        GlobalContext.startKoin {
            modules(module)
        }
        logger.info("Redis connection successfully recreated.")
    } catch (e: Exception) {
        logger.severe("Failed to recreate Redis connection: ${e.message}")
    }
}