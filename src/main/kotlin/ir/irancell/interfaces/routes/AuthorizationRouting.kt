package ir.irancell.interfaces.routes

import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ir.irancell.application.mappers.toIpInfoDTO
import ir.irancell.application.services.interfaces.IAuthorizationService
import org.slf4j.Logger

fun Route.authorizationRoute(
    logger: Logger,
    iAuthorizationService: IAuthorizationService
) {
    route("/authorization") {
        get("/ipInfo") {
            val ip = iAuthorizationService.extractIpInfo(call)
            logger.info(ip)
            ip?.let {
                val ipInfoResponse = iAuthorizationService.getIpInfo(ip)?.toIpInfoDTO()
                ipInfoResponse?.let {
                    call.respond(HttpStatusCode.OK, ipInfoResponse)
                }?: call.respond(HttpStatusCode.NotFound,"details not available")

            } ?: run {
                call.respond(HttpStatusCode.NotFound, "no ip associated with address")
            }
        }
    }
}