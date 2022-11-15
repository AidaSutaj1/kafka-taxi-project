package com.github.aidasutaj1.taxispringapp.service;

import com.github.aidasutaj1.taxispringapp.api.VechileController;
import com.github.aidasutaj1.taxispringapp.dto.Signal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class VechileService {

    private static final Logger log = LoggerFactory.getLogger(VechileController.class);
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void sendSignalToTopic(Signal signal) {
        ListenableFuture<SendResult<String, Object>> future =
                kafkaTemplate.send("home-task-topic", signal.getVechileId().toString(), signal);
        future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {

            @Override
            public void onSuccess(SendResult<String, Object> result) {
                System.out.println("Sent message=[" + signal.toString() +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");

            }

            @Override
            public void onFailure(Throwable ex) {
                System.out.println("Unable to send message=["
                        + signal.toString() + "] due to : " + ex.getMessage());
            }
        });
        kafkaTemplate.flush();

    }




}
