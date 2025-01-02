package ir.irancell.application.commands.batch_user

import ir.irancell.application.shared.Command
import ir.irancell.domain.models.UserDomain
import ir.irancell.infrastructure.database.UsersTable
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class BatchInsertCommand(val users : List<UserDomain>) : Command(correlationId =  UUID.randomUUID().toString(),invalidateCacheKeys =  listOf("findAll${UsersTable.tableName}","findById${UsersTable.tableName}"))
