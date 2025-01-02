package ir.irancell.application.services.interfaces

import ir.irancell.domain.events.BatchInsertEvent
import ir.irancell.domain.events.UserCreatedEvent
import ir.irancell.domain.models.UserDomain

interface IUserService {
   suspend fun createUser(userDomain: UserDomain) : UserCreatedEvent?
   suspend fun batchInsert(users : List<UserDomain>) : BatchInsertEvent?
   suspend fun findAll(): List<UserDomain>?
}