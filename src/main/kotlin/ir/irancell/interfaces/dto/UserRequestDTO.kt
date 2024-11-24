package ir.irancell.interfaces.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserRequestDTO(
    val name: String,
    val age: Int
)
