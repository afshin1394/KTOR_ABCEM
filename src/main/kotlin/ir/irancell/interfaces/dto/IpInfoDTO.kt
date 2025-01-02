package ir.irancell.interfaces.dto

import kotlinx.serialization.Serializable

@Serializable
data class IpInfoDTO(
    val ip: String? = null,
    val hostname: String? = null,
    val city: String? = null,
    val region: String? = null,
    val country: String? = null,
    val loc: String? = null,
    val org: String? = null,
    val postal: String? = null,
    val timezone: String? = null,
)
