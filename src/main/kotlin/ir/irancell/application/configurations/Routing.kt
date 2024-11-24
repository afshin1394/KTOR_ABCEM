package ir.irancell.application.configurations

import io.ktor.server.application.*
import io.ktor.server.plugins.openapi.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.routing.*
import ir.irancell.application.services.interfaces.IUserService
import ir.irancell.interfaces.apiModule
import ir.irancell.interfaces.routes.cityRoutes
import ir.irancell.interfaces.routes.userRoutes
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val userService: IUserService by inject()

    routing {
        openAPI(path = "openapi")
        swaggerUI(path = "/swagger")
        apiModule(userService)
    }
}