package ir.irancell

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.testing.*
import io.ktor.server.routing.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import ir.irancell.application.mappers.toUserDomain
import ir.irancell.infrastructure.DatabaseFactory
import ir.irancell.infrastructure.DatabaseType
import ir.irancell.infrastructure.database.repository_impl.UserWriteRepositoryImpl

import ir.irancell.interfaces.dto.UserRequestDTO
import kotlin.test.Test
import kotlin.test.assertEquals

import kotlin.test.*

class ApplicationTest {
    @Test
    fun testRoot() = testApplication {
        application {
//            configureRouting()
        }
        client.get("/").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals("Hello World!", bodyAsText())
        }
    }
}





// Mock UserService


class UserDomainRoutesTest {

    @Test
    fun `POST users create returns Created`() = testApplication {

        // Mock the UserService


        val mockDatabase = DatabaseFactory.init(DatabaseType.PRIMARY,"jdbc:postgresql://localhost/abcem_database","org.postgresql.Driver","postgres","afshin1994")

        val userWriteRepository = UserWriteRepositoryImpl(mockDatabase)

        // Install necessary plugins
        application {
            install(ContentNegotiation) {
                json() // Use JSON serialization
            }

            routing {
                route("/users") {
                    post("/create") {
                        val user = call.receive<UserRequestDTO>()
                        val id = userWriteRepository.save(user.toUserDomain())
                        call.respond(HttpStatusCode.Created, id)
                    }
                }
            }
        }

        // Make the test request
        val client = createClient {
            install(io.ktor.client.plugins.contentnegotiation.ContentNegotiation) {
                json()
            }
        }

        val testUser = UserRequestDTO(name = "John Doe", age = 22)
        val response = client.post("/users/create") {
            contentType(ContentType.Application.Json)
            setBody(testUser)
        }

        // Assert the response
        assertEquals(HttpStatusCode.Created, response.status)

    }
}
