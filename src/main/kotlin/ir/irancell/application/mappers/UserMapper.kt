package ir.irancell.application.mappers

import ir.irancell.domain.models.UserDomain
import ir.irancell.interfaces.dto.UserDTO

fun UserDTO.toUserDomain() : UserDomain {
    return UserDomain(this.name,this.age);
}

fun UserDomain.toUserDTO() : UserDTO {
    return UserDTO(this.name,this.age);
}
fun List<UserDTO>.toUserDomainList() : List<UserDomain> {
    return this.map { it.toUserDomain() }
}

fun List<UserDomain>.toUserDTOList() : List<UserDTO> {
    return this.map { it.toUserDTO() }
}