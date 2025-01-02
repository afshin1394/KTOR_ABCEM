import io.mockk.coEvery
import io.mockk.mockk
import ir.irancell.application.commands.create_user.CreateUserCommand
import ir.irancell.application.shared.CommandDispatcher
import ir.irancell.domain.events.Event
import ir.irancell.domain.events.UserCreatedEvent
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.runBlocking
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import java.util.concurrent.CompletableFuture
import kotlin.test.Test

class CommandDispatcherTest {

    private val kafkaProducer = mockk<KafkaProducer<String, String>>(relaxed = true)

    @Test
    fun `dispatch sends command and waits for event`() = runBlocking {
        val command = CreateUserCommand("TestUser", 30)
        val serializer = CreateUserCommand.serializer()
        val dispatcher = CommandDispatcher(kafkaProducer)

        // Add a pending request for the command's correlationId
        val completableDeferred = CompletableDeferred<Event>()
        dispatcher.pendingRequests[command.correlationId!!] = completableDeferred

        coEvery { kafkaProducer.send(any(), any()) } answers {
            val record = firstArg<ProducerRecord<String, String>>()

            // Assert the key and value
            assertEquals("CreateUserCommand", record.key())
            assertNotNull(record.value())

            // Simulate event dispatch
            dispatcher.onEvent(
                UserCreatedEvent(
                    correlationId = command.correlationId!!,
                    userName = command.username,
                    age = command.age
                )
            )

            CompletableFuture.completedFuture(mockk())
        }

        // Dispatch and await the event
        val event = dispatcher.dispatch<CreateUserCommand, UserCreatedEvent>(command, serializer)

        // Assertions
        assertNotNull(event)
        assertEquals(command.correlationId, event?.correlationId)
        assertEquals(command.username, event?.userName)
        assertEquals(command.age, event?.age)
    }
}
