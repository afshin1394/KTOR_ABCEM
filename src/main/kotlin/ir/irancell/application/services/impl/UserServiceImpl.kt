package ir.irancell.application.services.impl

import ir.irancell.application.commands.batch_user.BatchInsertCommand
import ir.irancell.application.commands.create_user.CreateUserCommand
import ir.irancell.application.queries.GetAllUsersQuery
import ir.irancell.application.services.interfaces.IUserService
import ir.irancell.application.shared.CommandDispatcher
import ir.irancell.application.shared.QueryDispatcher
import ir.irancell.domain.events.BatchInsertEvent
import ir.irancell.domain.events.UserCreatedEvent
import ir.irancell.domain.models.UserDomain

class UserServiceImpl(
    private val commandDispatcher: CommandDispatcher,
    private val queryDispatcher: QueryDispatcher
) : IUserService {
    override suspend fun createUser(userDomain: UserDomain): UserCreatedEvent? {
        print("createUserCommand${CreateUserCommand(userDomain.name, userDomain.age)}")
        return commandDispatcher.dispatch<CreateUserCommand, UserCreatedEvent>(
            CreateUserCommand(
                userDomain.name,
                userDomain.age
            ),
            CreateUserCommand.serializer(),
        )
    }

    override suspend fun batchInsert(users: List<UserDomain>): BatchInsertEvent? {
        return commandDispatcher.dispatch<BatchInsertCommand, BatchInsertEvent>(BatchInsertCommand(users),BatchInsertCommand.serializer())
    }

    override suspend fun findAll(): List<UserDomain>? {
        return queryDispatcher.dispatch(GetAllUsersQuery())
    }
}