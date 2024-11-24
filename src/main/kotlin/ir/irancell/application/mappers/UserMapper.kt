package ir.irancell.application.mappers

import ir.irancell.domain.models.UserDomain
import ir.irancell.interfaces.dto.UserRequestDTO

fun UserRequestDTO.toUserDomain() : UserDomain {
    return UserDomain(this.name,this.age);
}
fun List<UserRequestDTO>.toUserDomainList() : List<UserDomain> {
    return this.map { it.toUserDomain() }
}