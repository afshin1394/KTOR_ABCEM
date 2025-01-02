package ir.irancell.application.shared

import ir.irancell.domain.events.Event
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.withTimeout
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KClass

class CommandDispatcher(
    private val kafkaProducer: KafkaProducer<String, String>,
) {

    private val handlers: MutableMap<String, CommandHandler<out Command, out Event>> = mutableMapOf()
    val pendingRequests: ConcurrentHashMap<String, CompletableDeferred<Event>> = ConcurrentHashMap()

    fun <C : Command, E : Event> registerHandler(commandType: KClass<C>, handler: CommandHandler<C, E>) {
        handlers[commandType.simpleName ?: error("Invalid command class name")] = handler
    }
    @Suppress("UNCHECKED_CAST")
    fun <C : Command, E : Event> getHandler(commandType: String): CommandHandler<C, E>? {
        return handlers[commandType] as? CommandHandler<C, E>
    }



    @Suppress("UNCHECKED_CAST")
    suspend  fun <C : Command, E : Event?> dispatch(command: C, serializer: KSerializer<C>): E? {
        val json = Json { ignoreUnknownKeys = true }
        val jsonCommand = json.encodeToString(serializer, command)
        val record = ProducerRecord<String, String>(
            "Command",
            command::class.simpleName,
            jsonCommand
        )

        // correlationId logic unchanged...
        command.correlationId?.let { correlationId ->
            val deferred = CompletableDeferred<Event>()
            pendingRequests[correlationId] = deferred
            kafkaProducer.send(record) { metadata, exception ->
                if (exception != null) {
                    deferred.completeExceptionally(exception)
                    pendingRequests.remove(correlationId)
                } else {
                    println("Command sent to partition ${metadata.partition()} with offset ${metadata.offset()}")
                }
            }
            return try {
                withTimeout(50000) {
                    // Wait for the eventual event (which we stored as `Event`).
                    // We'll cast to E because we assume that the event matches
                    // the type that the command expects.
                    deferred.await() as E
                }
            } catch (e: Exception) {
                pendingRequests.remove(correlationId)
                throw e
            }
        } ?: run {
            // If no correlation ID, just send and return null
            kafkaProducer.send(record)
            return null
        }
    }


    fun onEvent(event: Event)  {
        val deferred = pendingRequests[event.correlationId]
        if (deferred != null) {
            deferred.complete(event)
            pendingRequests.remove(event.correlationId)
        }
    }



}