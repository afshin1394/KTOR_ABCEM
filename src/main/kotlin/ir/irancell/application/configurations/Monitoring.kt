package ir.irancell.application.configurations

import io.ktor.server.application.*
import io.ktor.server.plugins.calllogging.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.koin.ktor.ext.inject
import org.slf4j.Logger
import org.slf4j.event.*

fun Application.configureMonitoring() {
    val logger by inject<Logger>()

    install(CallLogging) {
        level = Level.INFO
        filter { call -> call.request.path().startsWith("/") }
        format { call ->
            val xRealIp = call.request.headers["X-Real-IP"] ?: "N/A"
            val xForwardedFor = call.request.headers["X-Forwarded-For"] ?: "N/A"
            val xForwardedProto = call.request.headers["X-Forwarded-Proto"] ?: "N/A"
            val xForwardedHost = call.request.headers["X-Forwarded-Host"] ?: "N/A"

            "Request Headers - X-Real-IP: $xRealIp, X-Forwarded-For: $xForwardedFor, X-Forwarded-Proto: $xForwardedProto, X-Forwarded-Host: $xForwardedHost"
        }

    }
//    val appMicrometerRegistry = PrometheusMeterRegistry(PrometheusConfig.DEFAULT)
//    install(MicrometerMetrics) {
//        registry = appMicrometerRegistry
//        meterBinders = listOf(
//            JvmMemoryMetrics(),
//            JvmGcMetrics(),
//            ProcessorMetrics()
//        )
//    }
//    routing {
//        get("/metrics") {
//            call.respond(appMicrometerRegistry.scrape())
//        }
//    }
    intercept(ApplicationCallPipeline.Monitoring) {
        // Record the start time
        val start = System.currentTimeMillis()

        // Proceed with handling the request
        proceed()

        // Calculate the duration
        val duration = System.currentTimeMillis() - start

        // Add the response time as a header
        call.response.pipeline.intercept(ApplicationSendPipeline.Before) {
            call.response.headers.append("X-Response-Time", "$duration ms")
        }

        // Log the response time
        logger.info("Request to ${call.request.uri} took $duration ms")
    }
}
