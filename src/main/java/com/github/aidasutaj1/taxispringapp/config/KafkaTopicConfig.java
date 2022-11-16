package com.github.aidasutaj1.taxispringapp.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.support.TopicForRetryable;

public class KafkaTopicConfig {

    @Bean
    public NewTopic topic1() {
        return TopicBuilder.name("home-task-topic").replicas(2).build();
    }

    @Bean
    public NewTopic topic2() {
        return TopicBuilder.name("output2").replicas(1).build();
    }

}
