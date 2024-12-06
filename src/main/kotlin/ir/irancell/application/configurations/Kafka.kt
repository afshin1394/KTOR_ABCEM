package ir.irancell.application.configurations

import io.ktor.server.application.*
import ir.irancell.application.shared.KafkaCommandHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun Application.configureKafka(scope: CoroutineScope,kafkaCommandHandler: KafkaCommandHandler) {

// Start Kafka listener in a coroutine
    scope.launch {
        kafkaCommandHandler.startListening()
    }
}