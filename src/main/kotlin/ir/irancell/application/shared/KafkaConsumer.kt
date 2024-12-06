package ir.irancell.application.shared


import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.consumer.ConsumerRecord
import java.time.Duration

class KafkaCommandHandler(
    val kafkaConsumer: KafkaConsumer<String, String>,
    private val commandDispatcher: CommandDispatcher,
) {
    suspend fun startListening() {
        kafkaConsumer.subscribe(kafkaConsumer.listTopics().keys)
        while (true) {
            val records = kafkaConsumer.poll(Duration.ofMillis(100))
            for (record in records) {
                processRecord(record)
            }
        }
    }

    private suspend fun processRecord(record: ConsumerRecord<String, String>) {
        val commandType = record.key()
        val commandJson = record.value()

        val handler = resolveHandler(commandType)
            ?: throw IllegalArgumentException("No handler found for command type: $commandType")
        val command = handler.deserializeCommand(commandJson)
        handler.handle(command)
    }

    private  fun resolveHandler(commandType: String): CommandHandler<Command>? {
        return commandDispatcher.getHandler(commandType)
    }
}