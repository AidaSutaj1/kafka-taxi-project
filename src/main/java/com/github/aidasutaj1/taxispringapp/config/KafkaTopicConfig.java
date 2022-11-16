package com.github.aidasutaj1.taxispringapp.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;

public class KafkaTopicConfig {

    @Value("${spring.kafka.topic1}")
    private String topic1Name;

    @Value("${spring.kafka.topic2}")
    private String topic2Name;

    @Bean
    public NewTopic topic1() {
        return TopicBuilder.name(topic1Name).replicas(2).build();
    }

    @Bean
    public NewTopic topic2() {
        return TopicBuilder.name(topic2Name).replicas(1).build();
    }

}
