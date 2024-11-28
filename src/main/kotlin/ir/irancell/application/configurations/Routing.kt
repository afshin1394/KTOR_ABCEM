package ir.irancell.application.configurations

import io.ktor.server.application.*
import io.ktor.server.plugins.openapi.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.routing.*
import ir.irancell.application.services.interfaces.IUserService
import ir.irancell.interfaces.apiModule

import org.koin.ktor.ext.inject

fun Application.configureRouting() {


    val userService: IUserService by inject()

    routing() {
        openAPI(path = "/openapi", swaggerFile = "openapi/documentation.yaml")
        swaggerUI(path = "/api", swaggerFile = "openapi/documentation.yaml")
        route("/api") {
            apiModule(userService)
        }

    }
}