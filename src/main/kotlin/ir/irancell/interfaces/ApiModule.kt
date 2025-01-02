package ir.irancell.interfaces

import io.ktor.server.routing.*
import ir.irancell.application.services.interfaces.IAuthorizationService
import ir.irancell.application.services.interfaces.IUserService
import ir.irancell.interfaces.routes.authorizationRoute
import ir.irancell.interfaces.routes.cityRoutes
import ir.irancell.interfaces.routes.userRoutes
import org.slf4j.Logger

fun Route.apiModule( logger: Logger,iAuthorizationService: IAuthorizationService,userService: IUserService) {
    authorizationRoute(logger,iAuthorizationService)
    userRoutes(userService,logger)
    cityRoutes()
}