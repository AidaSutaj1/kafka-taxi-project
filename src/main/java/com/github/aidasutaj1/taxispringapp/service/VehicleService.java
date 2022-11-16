package com.github.aidasutaj1.taxispringapp.service;

import com.github.aidasutaj1.taxispringapp.api.VehicleController;
import com.github.aidasutaj1.taxispringapp.documents.VehicleData;
import com.github.aidasutaj1.taxispringapp.dto.Signal;
import com.github.aidasutaj1.taxispringapp.repository.VehicleDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class VehicleService {

    private static final Logger log = LoggerFactory.getLogger(VehicleController.class);

    @Value("${spring.kafka.topic1}")
    private String topic1Name;

    @Value("${spring.kafka.topic2}")
    private String topic2Name;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    private VehicleDataRepository repository;

    public void sendSignalToTopic(Signal signal) {
        sendMessageToTopic(topic1Name, signal.getVehicleId().toString(), signal);
    }

    public void sendVehicleDataToTopic(Signal signal) {
        Double distance = calculateDistancePassedByVehicle(signal);
        VehicleData newVehicleData = new VehicleData(signal.getVehicleId(), signal.getLongitude(), signal.getLatitude(), distance, LocalDateTime.now());
        sendMessageToTopic(topic2Name, newVehicleData.getVehicleId().toString(), newVehicleData);
        repository.save(newVehicleData);
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

    private Double calculateDistancePassedByVehicle(Signal signal) {
        Optional<VehicleData> optionalVehicleData = repository.findById(signal.getVehicleId());
        if (optionalVehicleData.isPresent()) {
            VehicleData vehicleData = optionalVehicleData.get();
            Double alreadyPassedDistance = vehicleData.getDistanceTravelled();
            return  alreadyPassedDistance + calculateDistanceBetweenTwoCoordinates(vehicleData.getLastLatitude(), vehicleData.getLastLongitude(), signal.getLatitude(), signal.getLongitude());
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
