package ir.irancell.domain.repositories.remote

import io.ktor.server.application.*
import ir.irancell.domain.models.IpInfoDomain

interface IIpInfoRepository {
    suspend fun getIpInfo(ip: String): IpInfoDomain?
    suspend fun extractIpInfo(call: ApplicationCall): String?
}