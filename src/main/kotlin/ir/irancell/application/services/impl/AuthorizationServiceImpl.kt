package ir.irancell.application.services.impl

import io.ktor.server.application.*
import ir.irancell.application.services.interfaces.IAuthorizationService
import ir.irancell.domain.models.IpInfoDomain
import ir.irancell.domain.repositories.remote.IIpInfoRepository

class AuthorizationServiceImpl(private  val getIIpInfoRepository: IIpInfoRepository) : IAuthorizationService{
    override suspend fun getIpInfo(ip: String): IpInfoDomain? {
       return getIIpInfoRepository.getIpInfo(ip)
    }

    override suspend fun extractIpInfo(call: ApplicationCall): String? {
       return getIIpInfoRepository.extractIpInfo(call)
    }
}