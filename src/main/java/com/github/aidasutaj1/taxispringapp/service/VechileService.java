package com.github.aidasutaj1.taxispringapp.service;

import com.github.aidasutaj1.taxispringapp.api.VechileController;
import com.github.aidasutaj1.taxispringapp.documents.VechileData;
import com.github.aidasutaj1.taxispringapp.dto.Signal;
import com.github.aidasutaj1.taxispringapp.repository.VechileDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

@Service
public class VechileService {

    private static final Logger log = LoggerFactory.getLogger(VechileController.class);
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    private VechileDataRepository repository;

    public void sendSignalToTopic(Signal signal) {
        sendMessageToTopic("home-task-topic", signal.getVechileId().toString(), signal);
    }

    public void sendVechileDataToTopic(Signal signal) {
        Double distance = calculateDistancePassedByVechile(signal);
        VechileData newVechileData = new VechileData(signal.getVechileId(), signal.getLongitude(), signal.getLatitude(), distance, LocalDateTime.now());
        sendMessageToTopic("output2", newVechileData.getVechileId().toString(), newVechileData);
        repository.save(newVechileData);
    }

    private void sendMessageToTopic(String topicName, String key, Object value) {
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

    private Double calculateDistancePassedByVechile(Signal signal) {
        Optional<VechileData> optionalVechileData = repository.findById(signal.getVechileId());
        if (optionalVechileData.isPresent()) {
            VechileData vechileData = optionalVechileData.get();
            Double alreadyPassedDistance = vechileData.getDistanceTravelled();
            return  alreadyPassedDistance + calculateDistanceBetweenTwoCoordinates(vechileData.getLastLatitude(), vechileData.getLastLongitude(), signal.getLatitude(), signal.getLongitude());
        }
        return 0.0;
    }

    private Double calculateDistanceBetweenTwoCoordinates(Double lat1, Double lon1, Double lat2, Double lon2) {
        if ((lat1.equals(lat2)) && (lon1.equals(lon2))) {
            return 0.0;
        } else {
            Double theta = lon1 - lon2;
            Double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;
            //N taken for unit
            dist = dist * 0.8684;
            return dist;
        }
    }


}
