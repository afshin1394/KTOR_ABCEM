package ir.irancell.application
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ir.irancell.application.configurations.*
import ir.irancell.infrastructure.DatabaseFactory
import org.koin.core.context.GlobalContext.stopKoin
import org.koin.java.KoinJavaComponent.getKoin


    fun main(args: Array<String>) {
        io.ktor.server.netty.EngineMain.main(args)
    }

    fun Application.module() {
        configureSecurity()
        configureHTTP()
        configureMonitoring()
        configureSerialization()
        configureFrameworks()
        configureRouting()

        environment.monitor.subscribe(ApplicationStopping) {
            val databaseFactory: DatabaseFactory = getKoin().get()
            getKoin().getAll<HikariDataSource>().forEach { it.close() }
            stopKoin() // Stop Koin
        }
    }
