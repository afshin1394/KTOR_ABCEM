package ir.irancell.infrastructure.di

import com.zaxxer.hikari.HikariDataSource
import ir.irancell.infrastructure.DatabaseFactory
import org.jetbrains.exposed.sql.Database

import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.dsl.onClose


val databaseModule = module {
    // Write Database (Primary)
    val url = "jdbc:postgresql://localhost/abcem_database"
    val user = "postgres"
    val password = "afshin1994"
    val driver = "org.postgresql.Driver"
    single<HikariDataSource>(qualifier = named("writeDatabase")) {
//        val environment = get<Application>().environment
//        val jdbcUrl = environment.config.property("postgres.command.url").getString()
//        val driverClassName = environment.config.property("postgres.command.driver").getString()
//        val username = environment.config.property("postgres.command.user").getString()
//        val password = environment.config.property("postgres.command.password").getString()

        DatabaseFactory.init(
            jdbcUrl = url,
            driverClassName = driver,
            username = user,
            password = password,
        )
    }.also { factory ->
        factory.onClose { it?.close() }
    }

    single<Database>(qualifier = named("writeDatabase")) {
        DatabaseFactory.connect(get(named("writeDatabase")))
    }

    // Read Database (Read-only)
    single<HikariDataSource>(qualifier = named("readDatabase")) {
//        val environment = get<Application>().environment
//        val jdbcUrl = environment.config.property("postgres.query.url").getString()
//        val driverClassName = environment.config.property("postgres.query.driver").getString()
//        val username = environment.config.property("postgres.query.user").getString()
//        val password = environment.config.property("postgres.query.password").getString()
        DatabaseFactory.init(
            jdbcUrl = url,
            driverClassName = driver,
            username = user,
            password = password,
        )
    }.also { factory ->
        factory.onClose { it?.close() }
    }

    single<Database>(qualifier = named("readDatabase")) {
        DatabaseFactory.connect(get(named("readDatabase")))
    }

}



