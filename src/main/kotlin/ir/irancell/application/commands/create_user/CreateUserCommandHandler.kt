package ir.irancell.application.commands.create_user

import ir.irancell.application.shared.CommandHandler
import ir.irancell.domain.events.UserCreatedEvent
import ir.irancell.domain.models.UserDomain
import ir.irancell.domain.repositories.write.IUserWriteRepository
import kotlinx.serialization.json.Json

    class CreateUserCommandHandler(private val iUserWriteRepository: IUserWriteRepository) : CommandHandler<CreateUserCommand,UserCreatedEvent> {
        val json = Json { ignoreUnknownKeys = true }

        override suspend fun handle(command: CreateUserCommand) : UserCreatedEvent? {
            iUserWriteRepository.save(UserDomain(command.username, command.age))
            println("Creating user: ${command.username}")
           return command.correlationId?.let {
                 UserCreatedEvent(
                    correlationId = command.correlationId,
                    userName = command.username, // Assuming UserDomain has an 'id' field
                    age = command.age,
                )
            }
        }

        override suspend fun deserializeCommand(json: String): CreateUserCommand {
            return this.json.decodeFromString(CreateUserCommand.serializer(),json)
        }
    }