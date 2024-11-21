package ir.irancell.application.configurations

import io.ktor.server.application.*
import io.ktor.server.routing.*
import ir.irancell.di.databaseModule
import ir.irancell.di.serviceModule
import ir.irancell.interfaces.apiModule
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureFrameworks() {
    install(Koin) {

        slf4jLogger()
        modules(databaseModule, serviceModule) // Load Koin module

        routing {
            apiModule()
        }
    }
}
