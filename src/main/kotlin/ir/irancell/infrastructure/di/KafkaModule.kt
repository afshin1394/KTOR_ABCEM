package ir.irancell.infrastructure.di

import ir.irancell.infrastructure.shared.KafkaCommandConsumer
import ir.irancell.infrastructure.getKafkaConsumerConfig
import ir.irancell.infrastructure.getKafkaProducerConfig
import ir.irancell.infrastructure.shared.ReplyConsumer
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.producer.KafkaProducer
import org.koin.dsl.module

val kafkaModule = module {
    single { KafkaProducer<String, String>(getKafkaProducerConfig()) }
    single { KafkaConsumer<String, String>(getKafkaConsumerConfig()) }
    single {
            KafkaCommandConsumer(
            kafkaProducer = get(),
            replyTopic = "replies",
            kafkaConsumer = get(),
            commandDispatcher = get(),
            inMemoryCaching = get(),
        )
    }

//    single {
//        ReplyConsumer(
//            replyTopic = "replies",
//            kafkaConsumer = get(),
//            commandDispatcher = get(),
//        )
//    }
}