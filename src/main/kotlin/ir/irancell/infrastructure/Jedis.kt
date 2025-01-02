package ir.irancell.infrastructure

import ir.irancell.infrastructure.config.Config
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.core.context.GlobalContext
import redis.clients.jedis.Jedis
import redis.clients.jedis.exceptions.JedisConnectionException
@Target(AnnotationTarget.CLASS)
annotation class InvalidateCache

 object JedisFactory {
     fun createRedisClient(): Jedis {
       return  Jedis(Config.jedisHost, Config.jedisPort)
     }

     fun startRedisHealthChecker(onNeedRestart : () -> Unit) {
         val logger = java.util.logging.Logger.getLogger("RedisHealthChecker")

         CoroutineScope(Dispatchers.IO).launch {
             while (true) {
                 try {
                     val redisClient = GlobalContext.get().get<Jedis>()
                     redisClient.ping() // Ping to check the health of Redis
                 } catch (e: JedisConnectionException) {
                     onNeedRestart()
                     logger.warning("Redis server is down. Attempting to recreate the Redis connection...")
                 }
                 delay(5000) // Check Redis health coEvery 5 seconds
             }
         }
     }


 }