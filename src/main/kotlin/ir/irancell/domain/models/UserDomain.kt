package ir.irancell.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class UserDomain(val name: String, val age: Int)
