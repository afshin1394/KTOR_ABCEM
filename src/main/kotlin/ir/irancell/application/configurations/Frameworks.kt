package ir.irancell.application.configurations

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.openapi.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.reflect.*
import ir.irancell.application.commands.create_user.CreateUserCommand
import ir.irancell.application.services.interfaces.IUserService
import ir.irancell.application.shared.CommandHandler
import ir.irancell.di.cqrsModule
import ir.irancell.di.databaseModule
import ir.irancell.di.repositoryModule
import ir.irancell.di.serviceModule
import ir.irancell.domain.repositories.IWriteRepository
import ir.irancell.domain.repositories.write.IUserWriteRepository
import ir.irancell.interfaces.apiModule
import org.jetbrains.exposed.sql.Database
import org.koin.core.annotation.KoinInternalApi
import org.koin.core.error.NoBeanDefFoundException
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger
import org.koin.mp.KoinPlatform.getKoin

fun Application.configureFrameworks() {
    val appModules = listOf(
        module {
            single { this@configureFrameworks }
        },
        serviceModule,
        cqrsModule,
        repositoryModule,
        databaseModule
    )

    install(Koin) {
        slf4jLogger()
        modules(appModules)
    }
    checkSpecificKoinObject()
    checkSpecificKoinObject2()


}




fun Application.checkSpecificKoinObject() {
    val koin = getKoin()

    try {
        val readDatabase = koin.get<Database>(qualifier = named("readDatabase"))
        log.info("Read Database exists and is configured: $readDatabase")
    } catch (e: NoBeanDefFoundException) {
        log.error("Read Database object not found in Koin.")
    }
}

fun Application.checkSpecificKoinObject2() {
    val koin = getKoin()

    try {
        val readDatabase = koin.get<IUserService>()
        log.info("Read Database exists and is configured: $readDatabase")
    } catch (e: NoBeanDefFoundException) {
        log.error("Read Database object not found in Koin.")
    }
}
