package ir.irancell.domain.events

import kotlinx.serialization.Serializable

@Serializable
data class BatchInsertEvent(override val correlationId: String) : Event