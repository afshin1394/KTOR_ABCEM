package ir.irancell.domain.repositories.write

import ir.irancell.domain.models.UserDomain
import ir.irancell.domain.repositories.IWriteRepository
import java.util.UUID

interface IUserWriteRepository : IWriteRepository<UserDomain, UUID>{
   suspend fun batchInsert(users : List<UserDomain>)
}