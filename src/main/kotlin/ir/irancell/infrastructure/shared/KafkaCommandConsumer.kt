package ir.irancell.infrastructure.shared

import ir.irancell.application.shared.Command
import ir.irancell.application.shared.CommandDispatcher
import ir.irancell.application.shared.CommandHandler
import ir.irancell.domain.events.Event
import ir.irancell.infrastructure.InMemoryCaching
import ir.irancell.infrastructure.serializer.SerializationRegistry
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.header.internals.RecordHeader
import java.time.Duration

class KafkaCommandConsumer(
    val kafkaConsumer: KafkaConsumer<String, String>,
    private val kafkaProducer: KafkaProducer<String, String>,
    private val commandDispatcher: CommandDispatcher,
    private val inMemoryCaching: InMemoryCaching,
    private val replyTopic: String
) {
    private val json = Json { ignoreUnknownKeys = true }

    suspend fun startListening() {
        kafkaConsumer.subscribe(listOf("Command", replyTopic))
        while (true) {
            val records = kafkaConsumer.poll(Duration.ofMillis(100))
            for (record in records) {
                when (record.topic()) {
                    replyTopic->{
                        processReplies(record)
                    }
                    else->{
                        processRecord(record)

                    }

                }
            }
        }

    }

     suspend fun processRecord(record: ConsumerRecord<String, String>) {
        val commandType = record.key()           // e.g., "CreateUserCommand"
        val commandJson = record.value()
        println("commandJson $commandJson")
        //retrieve handler
        val handler = resolveHandler<Command,Event>(commandType)
            ?: throw IllegalArgumentException("No handler found for command type: $commandType")
        // Deserialize and handle
        val command = handler.deserializeCommand(commandJson)
        val event  = handler.handle(command)
        inMemoryCaching.invalidateCache(command.invalidateCacheKeys)

        // Send the event to replyTopic, etc...
        event?.let {
            val eventSerializer = SerializationRegistry.eventSerializers[it::class.simpleName]
                ?: throw IllegalArgumentException("No serializer found for event type: ${it::class.simpleName}")


            val eventJson =  json.encodeToString(eventSerializer as KSerializer<Event>, it)
            val replyRecord = ProducerRecord(replyTopic, it.correlationId, eventJson).apply {
                headers().add(RecordHeader("eventType", it::class.simpleName?.toByteArray()))
            }
            kafkaProducer.send(replyRecord) { metadata, exception ->
                if (exception != null) {
                    println("Failed to send event to reply topic: ${exception.message}")
                } else {
                    println("Event sent to partition ${metadata.partition()} with offset ${metadata.offset()}")
                }
            }
        }
    }


    private suspend fun processReplies(record: ConsumerRecord<String, String>) {
        try {
            val eventJson = record.value()

            val eventType = record.headers()
                .headers("eventType")
                .firstOrNull()
                ?.value()
                ?.let { String(it) }
                ?: throw IllegalArgumentException("No eventType header found in record")

            // Find the appropriate serializer using the event type
            val eventSerializer = SerializationRegistry.eventSerializers[eventType]
                ?: throw IllegalArgumentException("No serializer found for event type: $eventType")

            // Deserialize the event
            val event = json.decodeFromString(eventSerializer, eventJson)

            // Process the event
            commandDispatcher.onEvent(event)
        } catch (e: Exception) {
            println("Failed to process reply record with key ${record.key()}: ${e.message}")
            // Optionally, implement retry logic or other error handling
        }
    }



    private fun <C : Command, E : Event>  resolveHandler(commandType: String): CommandHandler<C, E>? {
        return commandDispatcher.getHandler(commandType)
    }


}