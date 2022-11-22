package com.github.aidasutaj1.taxispringapp.service;

import com.github.aidasutaj1.taxispringapp.api.VehicleController;
import com.github.aidasutaj1.taxispringapp.kafka.producer.KafkaProducer;
import com.github.aidasutaj1.taxispringapp.model.VehicleData;
import com.github.aidasutaj1.taxispringapp.dto.Signal;
import com.github.aidasutaj1.taxispringapp.repository.VehicleDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class VehicleService {

    private static final Logger log = LoggerFactory.getLogger(VehicleController.class);

    @Value("${spring.kafka.topic1}")
    private String inputTopic;

    @Value("${spring.kafka.topic2}")
    private String outputTopic;

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private VehicleDataRepository repository;

    public void saveVehicleData(VehicleData newVehicleData) {
        repository.save(newVehicleData);
    }

    public void sendSignalToTopic(Signal signal) {
        kafkaProducer.publishMessage(inputTopic, signal.getVehicleId().toString(), signal);
    }

    public void publishAndSaveVehicleDataFrom(Signal signal) {
        Double distance = calculateDistancePassedByVehicle(signal);
        VehicleData newVehicleData = new VehicleData(signal.getVehicleId(), signal.getLongitude(), signal.getLatitude(), distance, LocalDateTime.now());
        kafkaProducer.publishMessage(outputTopic, newVehicleData.getVehicleId().toString(), newVehicleData);
        saveVehicleData(newVehicleData);
    }


    private Double calculateDistancePassedByVehicle(Signal signal) {
        Optional<VehicleData> optionalVehicleData = repository.findById(signal.getVehicleId());
        if (optionalVehicleData.isPresent()) {
            VehicleData vehicleData = optionalVehicleData.get();
            Double alreadyPassedDistance = vehicleData.getDistanceTravelled();
            return alreadyPassedDistance + calculateDistanceBetweenTwoCoordinates(vehicleData.getLastLatitude(), vehicleData.getLastLongitude(), signal.getLatitude(), signal.getLongitude());
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
