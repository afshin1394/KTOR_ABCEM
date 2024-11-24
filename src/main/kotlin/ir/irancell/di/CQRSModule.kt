package ir.irancell.di

import ir.irancell.application.commands.batch_user.BatchInsertCommand
import ir.irancell.application.commands.batch_user.BatchInsertCommandHandler
import ir.irancell.application.commands.create_user.CreateUserCommand
import ir.irancell.application.commands.create_user.CreateUserCommandHandler
import ir.irancell.application.shared.CommandDispatcher
import ir.irancell.application.shared.CommandHandler
import org.koin.dsl.module


val cqrsModule = module {

    single<CommandHandler<CreateUserCommand>> { CreateUserCommandHandler(get()) }
    single<CommandHandler<BatchInsertCommand>> { BatchInsertCommandHandler(get()) }


    single {
        CommandDispatcher(
            mapOf(
                CreateUserCommand::class to get<CommandHandler<CreateUserCommand>>(),
                BatchInsertCommand::class to get<CommandHandler<BatchInsertCommand>>(),
            )
        )
    }
}