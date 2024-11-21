package ir.irancell.di

import io.ktor.server.application.*
import ir.irancell.infrastructure.DatabaseFactory
import org.koin.dsl.module

val databaseModule = module {

    single{
        val environment = get<Application>().environment
        val jdbcUrl = environment.config.property("postgres.url").getString()
        val driverClassName = environment.config.property("postgres.driver").getString()
        val username = environment.config.property("postgres.user").getString()
        val password = environment.config.property("postgres.password").getString()

        DatabaseFactory.init(jdbcUrl, driverClassName, username, password) }
}