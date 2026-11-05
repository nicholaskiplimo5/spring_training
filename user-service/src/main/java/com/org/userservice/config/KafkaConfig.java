package com.org.userservice.config;

import org.apache.kafka.common.TopicPartition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
@EnableKafka
public class KafkaConfig {
    @Bean
    public DefaultErrorHandler errorHandler(KafkaTemplate<Object, Object> kafkaTemplate) {

        // Retry 3 times with 2 sec delay
        FixedBackOff backOff = new FixedBackOff(2000L, 3);

        // DLQ topic: order-events.DLT
        DeadLetterPublishingRecoverer recoverer =
                new DeadLetterPublishingRecoverer(kafkaTemplate,
                        (record, ex) -> new TopicPartition(record.topic() + ".DLT", record.partition()));

        return new DefaultErrorHandler(recoverer, backOff);
    }
}
