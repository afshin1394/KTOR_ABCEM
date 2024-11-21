package ir.irancell.interfaces

import io.ktor.server.routing.*
import ir.irancell.interfaces.routes.cityRoutes
import ir.irancell.interfaces.routes.userRoutes

fun Route.apiModule() {
    userRoutes()
    cityRoutes()
}