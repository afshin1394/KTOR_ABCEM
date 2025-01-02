package ir.irancell.domain.events

import kotlinx.serialization.Serializable

@Serializable
sealed interface Event {
    val correlationId: String
}