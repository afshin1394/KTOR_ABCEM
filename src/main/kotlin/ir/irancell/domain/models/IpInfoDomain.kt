package ir.irancell.domain.models

data class IpInfoDomain(
    val ip: String?,
    val hostname: String?,
    val city: String?,
    val region: String?,
    val country: String?,
    val loc: String?,
    val org: String?,
    val postal: String?,
    val timezone: String?
)
