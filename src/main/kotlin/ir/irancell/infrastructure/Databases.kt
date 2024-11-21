package ir.irancell.infrastructure

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ir.irancell.infrastructure.orm.City
import ir.irancell.infrastructure.orm.CityService
import ir.irancell.infrastructure.orm.ExposedUser
import ir.irancell.infrastructure.orm.UserService
import java.sql.Connection
import java.sql.DriverManager
import org.jetbrains.exposed.sql.*

//fun Application.configureDatabases() {
//    val dbConnection: Connection = connectToPostgres(embedded = false)
//    val cityService = CityService(dbConnection)
//
//    routing {
//
//        // Create city
//        post("/cities") {
//            val city = call.receive<City>()
//            val id = cityService.create(city)
//            call.respond(HttpStatusCode.Created, id)
//        }
//
//        // Read city
//        get("/cities/{id}") {
//            val id = call.parameters["id"]?.toInt() ?: throw IllegalArgumentException("Invalid ID")
//            try {
//                val city = cityService.read(id)
//                call.respond(HttpStatusCode.OK, city)
//            } catch (e: Exception) {
//                call.respond(HttpStatusCode.NotFound)
//            }
//        }
//
//        // Update city
//        put("/cities/{id}") {
//            val id = call.parameters["id"]?.toInt() ?: throw IllegalArgumentException("Invalid ID")
//            val user = call.receive<City>()
//            cityService.update(id, user)
//            call.respond(HttpStatusCode.OK)
//        }
//
//        // Delete city
//        delete("/cities/{id}") {
//            val id = call.parameters["id"]?.toInt() ?: throw IllegalArgumentException("Invalid ID")
//            cityService.delete(id)
//            call.respond(HttpStatusCode.OK)
//        }
//    }
//    val database = Database.connect(
//        url = environment.config.property("postgres.url").getString(),
//        user = environment.config.property("postgres.user").getString(),
//        driver = environment.config.property("postgres.driver").getString(),
//        password =  environment.config.property("postgres.password").getString(),
//    )
//    val userService = UserService(database)
//    routing {
//        // Create user
//
//    }
//}

/**
 * Makes a connection to a Postgres database.
 *
 * In order to connect to your running Postgres process,
 * please specify the following parameters in your configuration file:
 * - postgres.url -- Url of your running database process.
 * - postgres.user -- Username for database connection
 * - postgres.password -- Password for database connection
 *
 * If you don't have a database process running yet, you may need to [download]((https://www.postgresql.org/download/))
 * and install Postgres and follow the instructions [here](https://postgresapp.com/).
 * Then, you would be able to edit your url,  which is usually "jdbc:postgresql://host:port/database", as well as
 * user and password values.
 *
 *
 * @param embedded -- if [true] defaults to an embedded database for tests that runs locally in the same process.
 * In this case you don't have to provide any parameters in configuration file, and you don't have to run a process.
 *
 * @return [Connection] that represent connection to the database. Please, don't forget to close this connection when
 * your application shuts down by calling [Connection.close]
 * */
//fun Application.connectToPostgres(embedded: Boolean): Connection {
//    Class.forName("org.postgresql.Driver")
//    if (embedded) {
//        log.info("Using embedded H2 database for testing; replace this flag to use postgres")
//        return DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "root", "")
//    } else {
//        val url = environment.config.property("postgres.url").getString()
//        log.info("Connecting to postgres database at $url")
//        val user = environment.config.property("postgres.user").getString()
//        val password = environment.config.property("postgres.password").getString()
//
//        return DriverManager.getConnection(url, user, password)
//    }
//}
object DatabaseFactory {

    private lateinit var dataSource: HikariDataSource

    fun init(  jdbcUrl: String,
                           driverClassName: String,
                           username: String,
                           password: String,
                           maxPoolSize: Int = 10) : Database {
        // Configure HikariCP
        val config = HikariConfig().apply {
            this.jdbcUrl = jdbcUrl
            this.driverClassName = driverClassName
            this.username = username
            this.password = password
            this.maximumPoolSize = maxPoolSize // Number of connections in the pool
            this.isAutoCommit = false // Transactions are explicitly committed
            this.transactionIsolation = "TRANSACTION_REPEATABLE_READ" // Isolation level
        }

        // Create HikariCP DataSource
        dataSource = HikariDataSource(config)

        // Connect Exposed to the DataSource
       return Database.connect(dataSource)

        // Initialize database schema
    }

    fun shutdown() {
        dataSource.close()
    }
}