package ir.irancell.application.configurations

import io.ktor.server.application.*
import ir.irancell.infrastructure.shared.KafkaCommandConsumer
import ir.irancell.infrastructure.shared.ReplyConsumer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun Application.configureKafka(scope: CoroutineScope, kafkaCommandConsumer: KafkaCommandConsumer) {

// Start Kafka listener in a coroutine
    scope.launch {
        kafkaCommandConsumer.startListening()
    }
}