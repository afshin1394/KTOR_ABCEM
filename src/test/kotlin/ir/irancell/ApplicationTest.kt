//package ir.irancell
//
//import io.ktor.client.call.*
//import io.ktor.client.request.*
//import io.ktor.client.statement.*
//import io.ktor.http.*
//import io.ktor.server.request.*
//import io.ktor.server.testing.*
//import io.ktor.server.routing.*
//import io.ktor.serialization.kotlinx.json.*
//import io.ktor.server.application.*
//import io.mockk.*
//
//import ir.irancell.application.commands.batch_user.BatchInsertCommand
//import ir.irancell.application.commands.batch_user.BatchInsertCommandHandler
//import ir.irancell.application.commands.create_user.CreateUserCommand
//import ir.irancell.application.shared.CommandDispatcher
//import ir.irancell.application.shared.CommandHandler
//import ir.irancell.domain.models.UserDomain
//import kotlinx.coroutines.runBlocking
//import kotlin.test.Test
//import kotlin.test.assertEquals
//
//
//class ApplicationTest {
//    @Test
//    fun testRoot() = testApplication {
//        application {
////            configureRouting()
//        }
//        client.get("/").apply {
//            assertEquals(HttpStatusCode.OK, status)
//            assertEquals("Hello World!", bodyAsText())
//        }
//
//    }
//
//
//    @Test
//    fun `should dispatch correct handler for command`() = runBlocking {
//        // Arrange
//
//
//        @Test
//        fun `should dispatch correct handler for command`() = runBlocking {
////            // Arrange
////            val createUserHandler = mockk<CommandHandler<CreateUserCommand>>()
////            val batchInsertHandler = mockk<CommandHandler<BatchInsertCommand>>()
////
////            // Explicitly mock the behavior of the handlers when handle is called
////            coEvery { createUserHandler.handle(any()) } just Runs
////            coEvery { batchInsertHandler.handle(any()) } just Runs  // Mock batchInsertHandler's behavior too
////
////            val commandDispatcher = CommandDispatcher(
////
////
////            )
////
////            val command = CreateUserCommand("afshin", 98)
////            val command2 = BatchInsertCommand(listOf(UserDomain("afshin", 98)))
////
////            // Act
////            commandDispatcher.dispatch(command)
////            commandDispatcher.dispatch(command2)
////
////            // Assert
////            coVerify(exactly = 1) { createUserHandler.handle(command) }
////            coVerify(exactly = 1) { batchInsertHandler.handle(command2) }
////
////            // Confirm verified interactions to ensure there are no unexpected calls
////            confirmVerified(createUserHandler)
////            confirmVerified(batchInsertHandler)
//
//        }
//    }
//}
//
//
//
//
//
//// Mock UserService
//
////
////class UserDomainRoutesTest {
////
////    @Test
////    fun `POST users create returns Created`() = testApplication {
////
////        // Mock the UserService
////
////
////        val mockDatabase = DatabaseFactory.init(DatabaseType.PRIMARY,"jdbc:postgresql://localhost/abcem_database","org.postgresql.Driver","postgres","afshin1994")
////
////        val userWriteRepository = UserWriteRepositoryImpl(mockDatabase)
////
////        // Install necessary plugins
////        application {
////            install(ContentNegotiation) {
////                json() // Use JSON serialization
////            }
////
////            routing {
////                route("/users") {
////                    post("/create") {
////                        val user = call.receive<UserRequestDTO>()
////                        val id = userWriteRepository.save(user.toUserDomain())
////                        call.respond(HttpStatusCode.Created, id)
////                    }
////                }
////            }
////        }
////
////        // Make the test request
////        val client = createClient {
////            install(io.ktor.client.plugins.contentnegotiation.ContentNegotiation) {
////                json()
////            }
////        }
////
////        val testUser = UserRequestDTO(name = "John Doe", age = 22)
////        val response = client.post("/users/create") {
////            contentType(ContentType.Application.Json)
////            setBody(testUser)
////        }
////
////        // Assert the response
////        assertEquals(HttpStatusCode.Created, response.status)
////
////    }
////
////
////}
