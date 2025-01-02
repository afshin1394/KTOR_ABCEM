package ir.irancell.application.commands.batch_user


import ir.irancell.application.shared.CommandHandler
import ir.irancell.domain.events.BatchInsertEvent
import ir.irancell.domain.repositories.write.IUserWriteRepository
import kotlinx.serialization.json.Json

class BatchInsertCommandHandler(private val iUserWriteRepository: IUserWriteRepository) :
    CommandHandler<BatchInsertCommand,BatchInsertEvent> {
    val json = Json { ignoreUnknownKeys = true }
    override suspend fun handle(command: BatchInsertCommand) : BatchInsertEvent? {
        // Business logic for creating a user
        iUserWriteRepository.batchInsert(command.users)
        println("batch insert users: ")
        return command.correlationId?.let {
            BatchInsertEvent(
                correlationId = command.correlationId,
            )
        }
    }

    override suspend fun deserializeCommand(json: String): BatchInsertCommand {
        return this.json.decodeFromString(BatchInsertCommand.serializer(),json)
    }



}