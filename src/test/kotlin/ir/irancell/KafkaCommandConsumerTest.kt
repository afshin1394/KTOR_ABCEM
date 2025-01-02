package ir.irancell.infrastructure.shared

import io.mockk.*
import ir.irancell.application.shared.CommandDispatcher
import ir.irancell.application.shared.CommandHandler
import ir.irancell.domain.events.UserCreatedEvent
import ir.irancell.application.commands.create_user.CreateUserCommand
import ir.irancell.infrastructure.InMemoryCaching
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.KSerializer
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class KafkaCommandConsumerTest {

    private lateinit var kafkaProducer: KafkaProducer<String, String>
    private lateinit var commandDispatcher: CommandDispatcher
    private lateinit var inMemoryCaching: InMemoryCaching
    private lateinit var consumer: KafkaCommandConsumer

    @BeforeEach
    fun setUp() {
        kafkaProducer = mockk(relaxed = true)
        commandDispatcher = mockk(relaxed = true)
        inMemoryCaching = mockk(relaxed = true)
        consumer = KafkaCommandConsumer(
            kafkaConsumer = mockk(relaxed = true),
            kafkaProducer = kafkaProducer,
            commandDispatcher = commandDispatcher,
            inMemoryCaching = inMemoryCaching,
            replyTopic = "replies"
        )
    }

    @Test
    fun `processRecord processes CreateUserCommand and sends UserCreatedEvent`() = runBlocking {
        // Mocking the ConsumerRecord
        val record = mockk<ConsumerRecord<String, String>>()
        coEvery { record.topic() } returns "Command"
        coEvery { record.key() } returns "CreateUserCommand"
        coEvery { record.value() } returns """{"username":"TestUser","age":30}"""

        // Mocking the CommandHandler
        val handler = mockk<CommandHandler<CreateUserCommand, UserCreatedEvent>>()
        coEvery { commandDispatcher.getHandler<CreateUserCommand, UserCreatedEvent>("CreateUserCommand") } returns handler
        coEvery { handler.deserializeCommand(any()) } returns CreateUserCommand("TestUser", 30)
        coEvery { handler.handle(any()) } returns UserCreatedEvent("corr-id-123", "TestUser", 30)

        // Mocking the SerializationRegistry
        mockkObject(ir.irancell.infrastructure.serializer.SerializationRegistry)
        coEvery { ir.irancell.infrastructure.serializer.SerializationRegistry.eventSerializers["UserCreatedEvent"] } returns UserCreatedEvent.serializer()

        // Mocking the JSON serializer
        val json = mockk<kotlinx.serialization.json.Json>(relaxed = true)
        coEvery { json.encodeToString(any<KSerializer<UserCreatedEvent>>(), any<UserCreatedEvent>()) } returns """{"correlationId":"corr-id-123","userName":"TestUser","age":30}"""
        runBlocking {
            // Act
            consumer.processRecord(record)
        }

        // Assert
        coVerify { handler.deserializeCommand("""{"username":"TestUser","age":30}""") }
        coVerify { handler.handle(CreateUserCommand("TestUser", 30)) }
        coVerify(exactly = 1) { inMemoryCaching.invalidateCache(listOf("findAllUsersTable", "findByIdUsersTable")) }
        verify {
            kafkaProducer.send(
                match<ProducerRecord<String, String>> {
                    it.topic() == "replies" &&
                            it.key() == "corr-id-123" &&
                            it.value() == """{"correlationId":"corr-id-123","userName":"TestUser","age":30}""" &&
                            it.headers().lastHeader("eventType")?.value()?.let { String(it) } == "UserCreatedEvent"
                },
                any()
            )
        }
    }
}
