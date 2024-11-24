package ir.irancell.application.commands.batch_user

import ir.irancell.application.shared.Command
import ir.irancell.domain.models.UserDomain

data class BatchInsertCommand(val users : List<UserDomain>) : Command
