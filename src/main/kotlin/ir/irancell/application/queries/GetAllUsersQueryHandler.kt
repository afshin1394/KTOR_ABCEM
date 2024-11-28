package ir.irancell.application.queries

import ir.irancell.application.shared.QueryHandler
import ir.irancell.domain.models.UserDomain
import ir.irancell.domain.repositories.write.IUserReadRepository
import org.h2.engine.User

class GetAllUsersQueryHandler(private val userReadRepository: IUserReadRepository) : QueryHandler<GetAllUsersQuery,List<UserDomain>> {
    override suspend fun handle(query: GetAllUsersQuery): List<UserDomain> {
       return userReadRepository.findAll()
    }
}