package ir.irancell.infrastructure

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import ir.irancell.infrastructure.database.UsersTable
import kotlinx.coroutines.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.core.context.GlobalContext.get
import org.koin.core.qualifier.named
import java.sql.SQLException


object DatabaseFactory  {

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
    fun startDatabaseHealthCheck(onNeedRestart: (infraElement : InfraElement) -> Unit){
        checkHealth(listOf(get().get<HikariDataSource>(named("writeDatabase")))){
            onNeedRestart(InfraElement.WriteDataBase)
        }
        checkHealth(listOf(get().get<HikariDataSource>(named("readDatabase")))){
            onNeedRestart(InfraElement.ReadDataBase)
        }
    }



    @OptIn(DelicateCoroutinesApi::class)
    fun checkHealth(hikariDataSources: List<HikariDataSource>,onNeedRestart : ()->Unit) {
        GlobalScope.launch {
            while (true) {
                delay(5000) // Check coEvery 5 seconds

                hikariDataSources.forEachIndexed { index, hikariDataSource ->
                    if (hikariDataSource.isAvailable()) {
                        println("Database $index is available.")
                    } else {
                        onNeedRestart()
                        println("Database $index not available. Attempting to reconnect...")
                        // Optionally, restart the Koin module or take any necessary action
                    }
                }

            }
        }
    }

   private suspend fun HikariDataSource.isAvailable(): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                // Attempt a simple query or connection test
                this@isAvailable.connection.use { connection ->
                    // A simple test query to ensure the connection is valid
                    return@use connection.isValid(2)  // Timeout 2 seconds
                }
            } catch (e: SQLException) {
                false
            }
        }
    }




}