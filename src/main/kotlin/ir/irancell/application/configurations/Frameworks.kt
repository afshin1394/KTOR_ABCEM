package ir.irancell.application.configurations

import io.ktor.server.application.*
import ir.irancell.application.shared.CacheUtil
import ir.irancell.di.*
import ir.irancell.infrastructure.startRedisHealthChecker
import org.koin.dsl.module
import org.koin.ktor.ext.getKoin
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureFrameworks() {
    val appModules = listOf(
        jedisModule,
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




