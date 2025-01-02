package ir.irancell.infrastructure.di

import com.zaxxer.hikari.HikariDataSource
import ir.irancell.infrastructure.DatabaseFactory
import ir.irancell.infrastructure.config.Config
import org.jetbrains.exposed.sql.Database
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.dsl.onClose

val databaseModule = module {
    // Read environment variables (with defaults)
    val writeUrl = Config.databaseUrlPrimary
    val readUrl  = Config.databaseUrlPrimary
    val dbUser   = Config.dbUser
    val dbPass   = Config.dbPassword
    // Always "org.postgresql.Driver" for Postgres
    val driver   = Config.driver



    // Write Database (Primary)
    single<HikariDataSource>(qualifier = named("writeDatabase")) {
        DatabaseFactory.init(
            jdbcUrl = writeUrl,
            driverClassName = driver,
            username = dbUser,
            password = dbPass
        )
    }.also { factory ->
        factory.onClose { it?.close() }
    }

    single<Database>(qualifier = named("writeDatabase")) {
        DatabaseFactory.connect(get(named("writeDatabase")))
    }

    // Read Database (Read-only)
    single<HikariDataSource>(qualifier = named("readDatabase")) {
        DatabaseFactory.init(
            jdbcUrl = readUrl,
            driverClassName = driver,
            username = dbUser,
            password = dbPass
        )
    }.also { factory ->
        factory.onClose { it?.close() }
    }

    single<Database>(qualifier = named("readDatabase")) {
        DatabaseFactory.connect(get(named("readDatabase")))
    }
}
