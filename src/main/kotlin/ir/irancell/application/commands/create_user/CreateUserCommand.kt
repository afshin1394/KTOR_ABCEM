package ir.irancell.application.commands.create_user

import ir.irancell.application.shared.Command

data class CreateUserCommand(val username: String, val age: Int) : Command
