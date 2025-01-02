package ir.irancell.infrastructure

import ir.irancell.infrastructure.config.Config
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import java.util.*

fun getKafkaProducerConfig(): Properties {
    return Properties().apply {
        put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, Config.kafkaBroker)
        put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, Config.kafkaSerialize)
        put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, Config.kafkaSerialize)
    }
}

fun getKafkaConsumerConfig(): Properties {
    return Properties().apply {
        put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, Config.kafkaBroker)
        put(ConsumerConfig.GROUP_ID_CONFIG, Config.kafkaGroupId)
        put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, Config.kafkaDeserialize)
        put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, Config.kafkaDeserialize)
        put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, Config.autoOffset)
    }
}

