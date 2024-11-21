package ir.irancell.application
import io.ktor.server.application.*
import ir.irancell.application.configurations.*
import ir.irancell.infrastructure.DatabaseFactory


fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {






    configureSecurity()
    configureHTTP()
    configureMonitoring()
    configureSerialization()
    configureFrameworks()


    environment.monitor.subscribe(ApplicationStopped) {
        DatabaseFactory.shutdown() // Close connection pool on shutdown
    }
}
