package ir.irancell.application.commands.create_user

import ir.irancell.application.shared.Command
import ir.irancell.application.shared.CommandHandler
import ir.irancell.domain.models.UserDomain
import ir.irancell.domain.repositories.write.IUserWriteRepository
import kotlinx.serialization.json.Json

class CreateUserCommandHandler(private val iUserWriteRepository: IUserWriteRepository) : CommandHandler<CreateUserCommand> {
    val json = Json { ignoreUnknownKeys = true }

    override suspend fun handle(command: CreateUserCommand) {
        // Business logic for creating a user
        iUserWriteRepository.save(UserDomain(command.username, command.age))
        println("Creating user: ${command.username}")
    }

    override suspend fun deserializeCommand(json: String): CreateUserCommand {
        return this.json.decodeFromString(json)
    }
}