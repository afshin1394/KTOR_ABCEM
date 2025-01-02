package ir.irancell.infrastructure.shared

import ir.irancell.application.shared.CommandDispatcher
import ir.irancell.domain.events.Event
import kotlinx.coroutines.*
import kotlinx.serialization.json.Json
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.koin.core.component.KoinComponent
import java.time.Duration

class ReplyConsumer(
    private val kafkaConsumer: KafkaConsumer<String, String>,
    private val commandDispatcher: CommandDispatcher,
    private val replyTopic: String
) : KoinComponent {

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val json = Json { ignoreUnknownKeys = true }

    fun start() {
        kafkaConsumer.subscribe(listOf(replyTopic))
        scope.launch {
            try {
                while (isActive) {
                    val records = kafkaConsumer.poll(Duration.ofMillis(100))
                    for (record in records) {
                        processRecord(record)
                    }
                }
            } catch (e: Exception) {
                println("Error in ReplyConsumer: ${e.message}")
                // Handle exceptions as needed
            } finally {
                kafkaConsumer.close()
            }
        }
    }

    private suspend fun processRecord(record: ConsumerRecord<String, String>) {
        try {
            val eventJson = record.value()
            val event: Event = json.decodeFromString(eventJson)
            commandDispatcher.onEvent(event)
        } catch (e: Exception) {
            println("Failed to process reply record with key ${record.key()}: ${e.message}")
            // Optionally, implement retry logic or other error handling
        }
    }

    fun stop() {
        scope.cancel()
    }
}
