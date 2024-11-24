package ir.irancell.infrastructure

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ir.irancell.infrastructure.database.UsersTable
import java.sql.Connection
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

enum class DatabaseType {
      READONLY,
      PRIMARY
  }
object DatabaseFactory {

    fun init(
        jdbcUrl: String,
        driverClassName: String,
        username: String,
        password: String,
        maxPoolSize: Int = 10
    ): HikariDataSource {
        return HikariConfig().apply {
            this.jdbcUrl = jdbcUrl
            this.driverClassName = driverClassName
            this.username = username
            this.password = password
            this.maximumPoolSize = maxPoolSize
            this.isAutoCommit = false
            this.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        }.let { HikariDataSource(it) }
    }

    fun connect(dataSource: HikariDataSource): Database {
        return Database.connect(dataSource).apply {
            transaction {
                SchemaUtils.create(UsersTable)
            }
        }
    }

}