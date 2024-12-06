package ir.irancell.application

import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.*
import io.ktor.server.metrics.micrometer.*
import io.micrometer.prometheus.PrometheusConfig
import io.micrometer.prometheus.PrometheusMeterRegistry

import ir.irancell.application.configurations.*
import ir.irancell.application.shared.KafkaCommandHandler
import kotlinx.coroutines.*


import org.koin.core.context.GlobalContext.stopKoin
import org.koin.java.KoinJavaComponent.getKoin
import org.koin.ktor.ext.inject


fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)

}

fun Application.module() {
    val kafkaCommandHandler: KafkaCommandHandler by inject()
    val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    configureSecurity ()
    configureHTTP()
    configureSerialization()
    configureFrameworks()
    configureMonitoring()
    configureRouting()
    configureKafka(scope,kafkaCommandHandler)

    environment.monitor.subscribe(ApplicationStopping) {
        getKoin().getAll<HikariDataSource>().forEach { it.close() }
        stopKoin() // Stop Koin
        scope.cancel()
        kafkaCommandHandler.kafkaConsumer.close()
    }
}
