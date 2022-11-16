package com.github.aidasutaj1.taxispringapp.listeners;

import com.github.aidasutaj1.taxispringapp.api.VehicleController;
import com.github.aidasutaj1.taxispringapp.documents.VehicleData;
import com.github.aidasutaj1.taxispringapp.dto.Signal;
import com.github.aidasutaj1.taxispringapp.service.VehicleService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {

    private static final Logger log = LoggerFactory.getLogger(VehicleController.class);

    @Autowired
    private VehicleService vehicleService;

    @KafkaListener(
            id = "${spring.kafka.consumer.group-id}",
            topics = "${spring.kafka.topic1}",
            containerFactory = "kafkaListenerContainerSignalFactory"
    )
    void listener(ConsumerRecord<String, Signal> consumerRecord, Acknowledgment ack) {
        log.info("Listener received " + consumerRecord.value().toString());
        vehicleService.sendVehicleDataToTopic(consumerRecord.value());
        ack.acknowledge();
    }

    @KafkaListener(
            id = "${spring.kafka.consumer.group-id2}",
            topics = "${spring.kafka.topic2}",
            containerFactory = "kafkaListenerContainerVehicleDataFactory")
    void listen2(ConsumerRecord<String, VehicleData> consumerRecord, Acknowledgment ack) {
        log.info("Listener2 received " + consumerRecord.value().toString());
        ack.acknowledge();
    }

}
