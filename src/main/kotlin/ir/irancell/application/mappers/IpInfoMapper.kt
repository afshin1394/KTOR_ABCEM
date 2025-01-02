package ir.irancell.application.mappers

import ir.irancell.domain.models.IpInfoDomain
import ir.irancell.interfaces.dto.IpInfoDTO


fun IpInfoDTO.toIpInfoDomain() : IpInfoDomain {
    return IpInfoDomain(this.ip,this.hostname,this.city,this.country,this.loc,this.org,this.postal,this.region,this.timezone)
}

fun IpInfoDomain.toIpInfoDTO() : IpInfoDTO {
    return IpInfoDTO(this.ip,this.hostname,this.city,this.country,this.loc,this.org,this.postal,this.region,this.timezone);
}
