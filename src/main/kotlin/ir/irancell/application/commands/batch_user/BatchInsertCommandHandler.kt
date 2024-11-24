package ir.irancell.application.commands.batch_user

import ir.irancell.application.shared.CommandHandler
import ir.irancell.domain.repositories.write.IUserWriteRepository

class BatchInsertCommandHandler(private val iUserWriteRepository: IUserWriteRepository) :
    CommandHandler<BatchInsertCommand> {
    override suspend fun handle(command: BatchInsertCommand) {
        // Business logic for creating a user
        iUserWriteRepository.batchInsert(command.users)
        println("batch insert users: ")
    }
}