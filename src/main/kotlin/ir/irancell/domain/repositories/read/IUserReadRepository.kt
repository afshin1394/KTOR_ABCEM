package ir.irancell.domain.repositories.read

import ir.irancell.domain.models.UserDomain
import ir.irancell.domain.repositories.IReadRepository
import java.util.*

interface IUserReadRepository : IReadRepository<UserDomain, UUID>