package ir.irancell.infrastructure.di

import ir.irancell.application.commands.batch_user.BatchInsertCommand
import ir.irancell.application.commands.batch_user.BatchInsertCommandHandler
import ir.irancell.application.commands.create_user.CreateUserCommand
import ir.irancell.application.commands.create_user.CreateUserCommandHandler
import ir.irancell.application.queries.GetAllUsersQuery
import ir.irancell.application.queries.GetAllUsersQueryHandler
import ir.irancell.application.shared.CommandDispatcher
import ir.irancell.application.shared.QueryDispatcher
import org.koin.dsl.module


val cqrsModule = module {
    // Registering Command Handlers
    single { CommandDispatcher(get()).apply {
        registerHandler(CreateUserCommand::class, CreateUserCommandHandler(get()))
        registerHandler(BatchInsertCommand::class, BatchInsertCommandHandler(get()))
    } }

    single { QueryDispatcher().apply {
        registerHandler(GetAllUsersQuery::class, GetAllUsersQueryHandler(get()))
    } }
}