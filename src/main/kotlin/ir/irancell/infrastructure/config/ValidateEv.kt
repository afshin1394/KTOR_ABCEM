package ir.irancell.infrastructure.config

import com.typesafe.config.Config

object ConfigValidator {
    fun validate(config: Config) {
        val requiredPaths = listOf(
            "ktor.deployment.port",
            "redis.host",
            "kafka.broker",
            "kafka.groupId",
            "kafka.autoOffsetReset",
            "kafka.serializerClass",
            "kafka.deserializerClass",
            "replication.user",
            "replication.password",
            "postgres.command.jdbcUrl",
            "postgres.command.driver",
            "postgres.command.user",
            "postgres.command.password",
            "postgres.command.maximumPoolSize",
            "postgres.command.isAutoCommit",
            "postgres.command.transactionIsolation",
            "postgres.query.jdbcUrl",
            "postgres.query.driver",
            "postgres.query.user",
            "postgres.query.password",
            "postgres.query.maximumPoolSize",
            "postgres.query.isAutoCommit",
            "postgres.query.transactionIsolation"
        )

        val missingPaths = requiredPaths.filter { !config.hasPath(it) }

        if (missingPaths.isNotEmpty()) {
            throw IllegalStateException("Missing required configuration paths: $missingPaths")
        }

        // Additional validations (e.g., port ranges)
        val port = config.getInt("ktor.deployment.port")
        if (port !in 1..65535) {
            throw IllegalArgumentException("Port number $port is out of valid range (1-65535).")
        }
    }
}

