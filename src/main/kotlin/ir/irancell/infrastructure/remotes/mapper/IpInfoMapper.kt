package ir.irancell.infrastructure.remotes.mapper

import ir.irancell.domain.models.IpInfoDomain
import ir.irancell.infrastructure.remotes.dto.IpInfoDto

fun IpInfoDto.toDomainModel(): IpInfoDomain? {
    if (ip == null) return null // Ensure mandatory fields are present
    return IpInfoDomain(
        ip = ip,
        hostname = hostname,
        city = city,
        region = region,
        country = country,
        loc = loc,
        org = org,
        postal = postal,
        timezone = timezone
    )
}

