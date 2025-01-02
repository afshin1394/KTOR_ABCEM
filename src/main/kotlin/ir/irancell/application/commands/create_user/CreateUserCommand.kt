package ir.irancell.application.commands.create_user

import ir.irancell.application.shared.Command
import ir.irancell.infrastructure.database.UsersTable
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class CreateUserCommand(
    val username: String, val age: Int
) : Command(correlationId =  UUID.randomUUID().toString(), invalidateCacheKeys =  listOf("findAll${UsersTable.tableName}", "findById${UsersTable.tableName}"))
