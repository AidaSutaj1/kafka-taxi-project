package com.github.aidasutaj1.taxispringapp.listeners;

import com.github.aidasutaj1.taxispringapp.dto.Signal;
import com.github.aidasutaj1.taxispringapp.service.VechileService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {

    /*@Autowired
    private VechileService vechileService;*/

    @KafkaListener(
            id = "myGroup",
            topics = "home-task-topic",
            containerFactory = "kafkaListenerContainerFactory"
    )
    void listener(ConsumerRecord<String, Signal> consumerRecord, Acknowledgment ack) {
        System.out.println("Listener received " + consumerRecord.value().toString());
        ack.acknowledge();
    }

  /*  @KafkaListener(topics = "output2", groupId = "group2")
    public void listen2(String in) {
        System.out.println("Ovaj ne treba da slusa");
    }*/

}
