package ir.irancell.interfaces

import io.ktor.server.routing.*
import ir.irancell.application.services.interfaces.IUserService
import ir.irancell.interfaces.routes.cityRoutes
import ir.irancell.interfaces.routes.userRoutes

fun Route.apiModule(userService: IUserService) {
    userRoutes(userService)
    cityRoutes()
}