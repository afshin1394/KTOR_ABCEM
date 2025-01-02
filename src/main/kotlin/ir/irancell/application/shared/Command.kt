package ir.irancell.application.shared

import kotlinx.serialization.Serializable
@Serializable
open class Command(val correlationId : String? = null,val invalidateCacheKeys:List<String>)
