package ir.irancell.infrastructure.remotes.repository_impl

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import ir.irancell.domain.models.IpInfoDomain
import ir.irancell.domain.repositories.remote.IIpInfoRepository
import ir.irancell.infrastructure.remotes.dto.IpInfoDto
import ir.irancell.infrastructure.remotes.mapper.toDomainModel

class IpInfoRepositoryImpl(
    private val client: HttpClient,
    ) : IIpInfoRepository {
    override suspend fun getIpInfo(ip: String): IpInfoDomain? {
       return client.get("https://ipinfo.io/${ip}/json").body<IpInfoDto>().toDomainModel();
    }

    override suspend fun extractIpInfo(call: ApplicationCall): String? {
            // Check 'X-Forwarded-For' header first
            val xForwardedFor = call.request.headers["X-Forwarded-For"]
            if (!xForwardedFor.isNullOrBlank()) {
                // X-Forwarded-For can contain multiple IPs, the first is the client
                return xForwardedFor.split(",").firstOrNull()?.trim()
            }

            // Fallback to 'Forwarded' header
            val forwarded = call.request.headers["Forwarded"]
            if (!forwarded.isNullOrBlank()) {
                // Example: Forwarded: for=192.0.2.60;proto=http;by=203.0.113.43
                val forPart = forwarded.split(";")
                    .firstOrNull { it.trim().startsWith("for=") }
                    ?.substringAfter("for=")
                    ?.trim('"') // Remove quotes if present
                return forPart
            }

            // Fallback to remote host
            return call.request.origin.remoteHost
        }

}