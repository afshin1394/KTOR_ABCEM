package ir.irancell.application.configurations

import io.ktor.server.application.*
import ir.irancell.infrastructure.di.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureFrameworks() {
    install(Koin) {
        slf4jLogger()
        modules(appModules)
    }
}




