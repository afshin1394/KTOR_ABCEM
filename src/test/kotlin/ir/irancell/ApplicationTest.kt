package ir.irancell

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.testing.*
import io.ktor.http.*
import io.ktor.server.testing.*
import io.ktor.client.request.*
import io.ktor.server.routing.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.mockk.coEvery
import io.mockk.mockk
import ir.irancell.infrastructure.DatabaseFactory
import ir.irancell.infrastructure.orm.ExposedUser
import ir.irancell.infrastructure.orm.UserService
import kotlin.test.Test
import kotlin.test.assertEquals

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Database
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


class UserRoutesTest {

    @Test
    fun `POST users create returns Created`() = testApplication {

        // Mock the UserService


        val mockUserService = UserService(DatabaseFactory.init("jdbc:postgresql://localhost/abcem_database","org.postgresql.Driver","postgres","afshin1994"))


        // Install necessary plugins
        application {
            install(ContentNegotiation) {
                json() // Use JSON serialization
            }

            routing {
                route("/users") {
                    post("/create") {
                        val user = call.receive<ExposedUser>()
                        val id = mockUserService.create(user)
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

        val testUser = ExposedUser(name = "John Doe", age = 22)
        val response = client.post("/users/create") {
            contentType(ContentType.Application.Json)
            setBody(testUser)
        }

        // Assert the response
        assertEquals(HttpStatusCode.Created, response.status)
        val responseBody = response.body<String>()
    }
}
