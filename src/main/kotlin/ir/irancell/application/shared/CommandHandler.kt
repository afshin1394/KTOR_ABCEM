package ir.irancell.application.shared

import ir.irancell.application.commands.batch_user.BatchInsertCommand
import ir.irancell.application.commands.create_user.CreateUserCommand
import ir.irancell.domain.events.Event

interface CommandHandler<C : Command, E : Event?> {
    suspend fun handle(command: C): E?
    suspend fun deserializeCommand(json: String): C
}

