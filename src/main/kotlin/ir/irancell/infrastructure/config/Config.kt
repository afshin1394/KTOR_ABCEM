package ir.irancell.infrastructure.config
import io.github.cdimascio.dotenv.dotenv

object Config {
    private val dotenv = dotenv {
        directory = "./" // Adjust the path as needed
        filename = System.getenv("KTOR_CONFIG")?.let {
            when (it) {
                "application.conf" -> ".env.docker"
//                "application.conf" -> ".env.docker"
                else -> ".env"
            }
        } ?: ".env"
        ignoreIfMalformed = true
        ignoreIfMissing = true
    }
    val port = dotenv["port"]?.toIntOrNull() ?: 80

    val jedisHost: String = dotenv["JEDIS_HOST"] ?: "localhost"
    val jedisPort: Int =  dotenv["JEDIS_PORT"]?.toIntOrNull() ?: 8080


    val databaseUrlPrimary: String = dotenv["DATABASE_URL_PRIMARY"] ?: "jdbc:postgresql://localhost:5432/abcem_database"
    val databaseUrlStandBy: String = dotenv["DATABASE_URL_PRIMARY"] ?: "jdbc:postgresql://localhost:5432/abcem_readonly_database"

    val driver : String = dotenv["DATABASE_DRIVER"] ?: "org.postgresql.Driver"

    val dbUser: String = dotenv["DB_USER"] ?: "postgres"
    val dbPassword: String = dotenv["DB_PASSWORD"] ?: "afshin1994"

    val kafkaBroker: String = dotenv["KAFKA_BROKER"] ?: "localhost:9094"
    val kafkaGroupId: String = dotenv["KAFKA_GROUP_ID"] ?: "cqrs-group"
    val autoOffset: String = dotenv["AUTO_OFFSET_RESET_CONFIG"] ?: "latest"
    val kafkaSerialize: String = dotenv["KAFKA_KEYSERVER_CLASS"] ?: "org.apache.kafka.common.serialization.StringSerializer"
    val kafkaDeserialize: String =  dotenv["KAFKA_VALUE_SERVER_CLASS"] ?: "org.apache.kafka.common.serialization.StringDeserializer"

}
