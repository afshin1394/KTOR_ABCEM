package ir.irancell.application.commands.batch_user

import ir.irancell.application.shared.Command
import ir.irancell.application.shared.CommandHandler
import ir.irancell.domain.repositories.write.IUserWriteRepository
import kotlinx.serialization.json.Json

class BatchInsertCommandHandler(private val iUserWriteRepository: IUserWriteRepository) :
    CommandHandler<BatchInsertCommand> {
    val json = Json { ignoreUnknownKeys = true }
    override suspend fun handle(command: BatchInsertCommand) {
        // Business logic for creating a user
        iUserWriteRepository.batchInsert(command.users)
        println("batch insert users: ")
    }

    override suspend fun deserializeCommand(json: String): BatchInsertCommand {
        return this.json.decodeFromString(json)
    }

}