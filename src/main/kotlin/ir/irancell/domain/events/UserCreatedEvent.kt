package ir.irancell.domain.events

import kotlinx.serialization.Serializable

@Serializable
data class UserCreatedEvent(
    override val correlationId: String,
    val userName: String,
    val age: Int
) : Event
