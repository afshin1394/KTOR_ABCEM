package ir.irancell.application.services.impl

import ir.irancell.application.commands.batch_user.BatchInsertCommand
import ir.irancell.application.commands.create_user.CreateUserCommand
import ir.irancell.application.queries.GetAllUsersQuery
import ir.irancell.application.services.interfaces.IUserService
import ir.irancell.application.shared.CommandDispatcher
import ir.irancell.application.shared.QueryDispatcher
import ir.irancell.domain.models.UserDomain

    class IUserServiceImpl(
        private val commandDispatcher: CommandDispatcher,
        private val queryDispatcher: QueryDispatcher
    ) : IUserService {
        override suspend fun createUser(userDomain: UserDomain) {
            commandDispatcher.dispatch(CreateUserCommand(userDomain.name, userDomain.age))
        }

        override suspend fun batchInsert(users: List<UserDomain>) {
            commandDispatcher.dispatch(BatchInsertCommand(users))
        }

        override suspend fun findAll(): List<UserDomain>? {
          return  queryDispatcher.dispatch(GetAllUsersQuery())
        }
    }