package ir.irancell.application.commands.create_user

import ir.irancell.application.shared.Command
import kotlinx.serialization.Serializable

@Serializable
data class CreateUserCommand(val username: String, val age: Int) : Command()
