package com.github.aidasutaj1.taxispringapp.listeners;

import com.github.aidasutaj1.taxispringapp.api.VechileController;
import com.github.aidasutaj1.taxispringapp.documents.VechileData;
import com.github.aidasutaj1.taxispringapp.dto.Signal;
import com.github.aidasutaj1.taxispringapp.service.VechileService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {

    private static final Logger log = LoggerFactory.getLogger(VechileController.class);
    @Autowired
    private VechileService vechileService;

    @KafkaListener(
            id = "myGroup",
            topics = "home-task-topic",
            containerFactory = "kafkaListenerContainerSignalFactory"
    )
    void listener(ConsumerRecord<String, Signal> consumerRecord, Acknowledgment ack) {
        log.info("Listener received " + consumerRecord.value().toString());
        vechileService.sendVechileDataToTopic(consumerRecord.value());
        ack.acknowledge();
    }

    @KafkaListener(
            id = "group2",
            topics = "output2",
            containerFactory = "kafkaListenerContainerVechileDataFactory")
    void listen2(ConsumerRecord<String, VechileData> consumerRecord, Acknowledgment ack) {
        log.info("Listener2 received " + consumerRecord.value().toString());
        ack.acknowledge();
    }

}
