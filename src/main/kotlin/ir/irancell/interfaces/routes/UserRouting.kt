package ir.irancell.interfaces.routes

import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ir.irancell.application.mappers.toUserDTOList
import ir.irancell.application.mappers.toUserDomain
import ir.irancell.application.mappers.toUserDomainList
import ir.irancell.application.services.interfaces.IUserService
import ir.irancell.interfaces.dto.UserDTO


fun Route.userRoutes(
    userService: IUserService
) {

    route("/users") {

        post("/create") {
            val user = call.receive<UserDTO>()
            val id = userService.createUser(user.toUserDomain())
            call.respond(HttpStatusCode.Created, id)
        }

        post("/insertAll"){
            try {
            val users = call.receive<List<UserDTO>>()
            val id = userService.batchInsert(users.toUserDomainList())
            call.respond(HttpStatusCode.Created, id)
            }catch (e: Exception){
                call.respond(HttpStatusCode.BadRequest, e.message ?: "Unknown error")
            }

        }

        get("/getAll") {
            val users = userService.findAll()?.toUserDTOList() ?: emptyList()
            call.respond(HttpStatusCode.OK, users)
        }


//        // Read user
//        get("/{id}") {
////            val id = call.parameters["id"]?.toInt() ?: throw IllegalArgumentException("Invalid ID")
////            val user = userService.read(id)
////            if (user != null) {
////                call.respond(HttpStatusCode.OK, user)
////            } else {
////                call.respond(HttpStatusCode.NotFound)
////            }
//        }
//
//        // Update user
//        put("/{id}") {
////            val id = call.parameters["id"]?.toInt() ?: throw IllegalArgumentException("Invalid ID")
////            val user = call.receive<ExposedUser>()
////            userService.update(id, user)
////            call.respond(HttpStatusCode.OK)
//        }
//
//        // Delete user
//        delete("/{id}") {
////            val id = call.parameters["id"]?.toInt() ?: throw IllegalArgumentException("Invalid ID")
////            userService.delete(id)
////            call.respond(HttpStatusCode.OK)
//        }
    }
}