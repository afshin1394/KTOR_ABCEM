package ir.irancell.domain.repositories.write

import ir.irancell.domain.models.UserDomain
import ir.irancell.domain.repositories.IReadRepository
import java.util.*

interface IUserReadRepository : IReadRepository<UserDomain, UUID>