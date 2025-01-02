    package ir.irancell.application

    import com.zaxxer.hikari.HikariDataSource
    import io.ktor.server.application.*
    import io.ktor.server.engine.*
    import io.ktor.server.netty.*
    import ir.irancell.application.configurations.*
    import ir.irancell.infrastructure.shared.KafkaCommandConsumer
    import ir.irancell.infrastructure.config.Config
    import kotlinx.coroutines.*
    import org.koin.core.context.stopKoin
    import org.koin.java.KoinJavaComponent.getKoin
    import org.koin.ktor.ext.inject
    import org.slf4j.Logger

    fun main(args: Array<String>) {
            // Validate configuration
            embeddedServer(Netty, port = Config.port, watchPaths = listOf("classes")) {
                module()
            }.start(wait = true)
        }

        fun Application.module() {
            // Create a CoroutineScope for the application
            val scope = CoroutineScope(Dispatchers.Default)

            // Inject dependencies using Koin
            val kafkaCommandConsumer: KafkaCommandConsumer by inject()
            val logger : Logger by inject()

            // Configure various aspects of Ktor
            configureSecurity()
            configureHTTP()
            configureSerialization()
            configureFrameworks()
            configureMonitoring()
            configureRouting(logger)
            configureKafka(scope, kafkaCommandConsumer)

            // Handle application stopping event
            monitor.subscribe(ApplicationStopping) {
                getKoin().getAll<HikariDataSource>().forEach { it.close() }
                stopKoin() // Stop Koin
                scope.cancel()
                kafkaCommandConsumer.kafkaConsumer.close()
            }
        }
