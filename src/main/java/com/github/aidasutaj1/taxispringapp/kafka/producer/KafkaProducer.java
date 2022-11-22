package com.github.aidasutaj1.taxispringapp.kafka.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class KafkaProducer {

    @Value("${spring.kafka.topic1}")
    private String topic1Name;

    @Value("${spring.kafka.topic2}")
    private String topic2Name;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void publishMessage(String topicName, String key, Object value) {
        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topicName, key, value);
        future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {

            @Override
            public void onSuccess(SendResult<String, Object> result) {
                System.out.println("Sent message=[" + value.toString() +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");

            }

            @Override
            public void onFailure(Throwable ex) {
                System.out.println("Unable to send message=["
                        + value.toString() + "] due to : " + ex.getMessage());
            }
        });
        kafkaTemplate.flush();
    }
}
