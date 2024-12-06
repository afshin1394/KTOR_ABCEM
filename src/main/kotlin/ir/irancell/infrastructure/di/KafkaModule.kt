package ir.irancell.infrastructure.di

import ir.irancell.application.shared.KafkaCommandHandler
import ir.irancell.infrastructure.getKafkaConsumerConfig
import ir.irancell.infrastructure.getKafkaProducerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.producer.KafkaProducer
import org.koin.dsl.module

val kafkaModule = module {
    single { KafkaProducer<String, String>(getKafkaProducerConfig()) }
    single { KafkaConsumer<String, String>(getKafkaConsumerConfig()) }
    single {
        KafkaCommandHandler(
            kafkaConsumer = get(),
            commandDispatcher = get(),
        )
    }
}