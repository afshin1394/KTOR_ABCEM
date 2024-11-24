package ir.irancell.application.commands.create_user

import ir.irancell.application.shared.CommandHandler
import ir.irancell.domain.models.UserDomain
import ir.irancell.domain.repositories.write.IUserWriteRepository

class CreateUserCommandHandler(private val iUserWriteRepository: IUserWriteRepository) : CommandHandler<CreateUserCommand> {
    override suspend fun handle(command: CreateUserCommand) {
        // Business logic for creating a user
        iUserWriteRepository.save(UserDomain(command.username, command.age))
        println("Creating user: ${command.username}")
    }
}