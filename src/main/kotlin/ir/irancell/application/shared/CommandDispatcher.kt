package ir.irancell.application.shared

import kotlinx.serialization.json.Json
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import kotlin.reflect.KClass

class CommandDispatcher(
    private val kafkaProducer: KafkaProducer<String, String>,
) {

    private val handlers: MutableMap<String, CommandHandler<Command>> = mutableMapOf()

    fun <C : Command> registerHandler(commandType: KClass<C>, handler: CommandHandler<C>) {
        handlers[commandType.simpleName ?: error("Invalid command class name")] = handler
    }

    fun getHandler(commandType: String): CommandHandler<Command>? {
        return handlers[commandType]
    }


    suspend fun <C : Command> dispatch(command: C) {
        val json = Json { ignoreUnknownKeys = true }
        val jsonCommand = json.encodeToString(Command.serializer(), command)
        kafkaProducer.send(ProducerRecord(Command::class.simpleName, command::class.simpleName, jsonCommand))

    }
}