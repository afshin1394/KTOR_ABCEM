package ir.irancell.interfaces.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserDTO(
    val name: String,
    val age: Int
)
