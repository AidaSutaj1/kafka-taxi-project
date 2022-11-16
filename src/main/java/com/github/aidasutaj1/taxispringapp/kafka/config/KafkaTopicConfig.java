package com.github.aidasutaj1.taxispringapp.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;

public class KafkaTopicConfig {

    @Value("${spring.kafka.topic1}")
    private String inputTopic;

    @Value("${spring.kafka.topic2}")
    private String outputTopic;

    @Bean
    public NewTopic inputTopic() {
        return TopicBuilder.name(inputTopic).replicas(2).build();
    }

    @Bean
    public NewTopic outputTopic() {
        return TopicBuilder.name(outputTopic).replicas(1).build();
    }

}
