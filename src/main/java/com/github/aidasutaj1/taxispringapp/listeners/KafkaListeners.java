package com.github.aidasutaj1.taxispringapp.listeners;

import com.github.aidasutaj1.taxispringapp.documents.VechileData;
import com.github.aidasutaj1.taxispringapp.dto.Signal;
import com.github.aidasutaj1.taxispringapp.service.VechileService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {

    @Autowired
    private VechileService vechileService;

    @KafkaListener(
            id = "myGroup",
            topics = "home-task-topic",
            containerFactory = "kafkaListenerContainerSignalFactory"
    )
    void listener(ConsumerRecord<String, Signal> consumerRecord, Acknowledgment ack) {
        System.out.println("Listener received " + consumerRecord.value().toString());
        vechileService.sendVechileDataToTopic(consumerRecord.value());
        ack.acknowledge();
    }

    @KafkaListener(
            id = "group2",
            topics = "output2",
            containerFactory = "kafkaListenerContainerVechileDataFactory")
    void listen2(ConsumerRecord<String, VechileData> consumerRecord, Acknowledgment ack) {
        System.out.println("Listener2 received " + consumerRecord.value().toString());
        ack.acknowledge();
    }

}
