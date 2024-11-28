package ir.irancell.application

import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.*

import ir.irancell.application.configurations.*
import ir.irancell.di.jedisModule
import ir.irancell.infrastructure.startRedisHealthChecker

import org.koin.core.context.GlobalContext.stopKoin
import org.koin.java.KoinJavaComponent.getKoin


fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)

}

fun Application.module() {

    configureSecurity ()
    configureHTTP()
    configureSerialization()
    configureFrameworks()
    configureMonitoring()
    configureRouting()
    startRedisHealthChecker(jedisModule)

    environment.monitor.subscribe(ApplicationStopping) {
        getKoin().getAll<HikariDataSource>().forEach { it.close() }
        stopKoin() // Stop Koin
    }
}
