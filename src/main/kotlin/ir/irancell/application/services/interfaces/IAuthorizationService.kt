package ir.irancell.application.services.interfaces

import io.ktor.server.application.*
import ir.irancell.domain.models.IpInfoDomain

interface IAuthorizationService {
   suspend fun getIpInfo(ip : String) : IpInfoDomain?
   suspend fun extractIpInfo(call:ApplicationCall) : String?
}