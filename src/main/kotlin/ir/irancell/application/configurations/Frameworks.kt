package ir.irancell.application.configurations

import io.ktor.server.application.*
import ir.irancell.infrastructure.di.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureFrameworks() {
    val appModules = listOf(
        jedisModule,
        kafkaModule,
        transactionModule,
        module {
            single { this@configureFrameworks }
        },
        serviceModule,
        cqrsModule,
        repositoryModule,
        logModule,
        databaseModule,
        )

    install(Koin) {
        slf4jLogger()
        modules(appModules)
    }


}




