package ir.irancell.infrastructure

import ir.irancell.infrastructure.DatabaseFactory.startDatabaseHealthCheck
import ir.irancell.infrastructure.JedisFactory.startRedisHealthChecker
import ir.irancell.infrastructure.healthcheck.IMonitorHealthCheck
import redis.clients.jedis.JedisFactory
sealed interface InfraElement{
    object REDIS : InfraElement
    object WriteDataBase : InfraElement
    object ReadDataBase : InfraElement
}
object MonitorHealthCheck : IMonitorHealthCheck {
    override fun checkHealth(onRetainState: (infraElement : InfraElement) -> Unit) {
        //Redis Health Check
        startRedisHealthChecker {
            print("redis onNeedRestart ")
            onRetainState(InfraElement.REDIS)
        }
        //Database Health Check
        startDatabaseHealthCheck{infraElement ->
            print("database onNeedRestart ")
            onRetainState(infraElement)
        }
    }

}