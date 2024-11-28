package ir.irancell.application.services.interfaces

import ir.irancell.domain.models.UserDomain

interface IUserService {
   suspend fun createUser(userDomain: UserDomain)
   suspend fun batchInsert(users : List<UserDomain>)
   suspend fun findAll(): List<UserDomain>?
}