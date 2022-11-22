package com.github.aidasutaj1.taxispringapp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Objects;

@Document
public class VehicleData {

    @Id
    private Long vehicleId;
    private Double lastLongitude;
    private Double lastLatitude;
    private Double distanceTravelled = 0.0;

    private LocalDateTime timestamp;

    public VehicleData(Long vehicleId, Double lastLongitude, Double lastLatitude, Double distanceTravelled, LocalDateTime timestamp) {
        this.vehicleId = vehicleId;
        this.lastLongitude = lastLongitude;
        this.lastLatitude = lastLatitude;
        this.distanceTravelled = distanceTravelled;
        this.timestamp = timestamp;
    }

    public VehicleData() {
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Double getLastLongitude() {
        return lastLongitude;
    }

    public void setLastLongitude(Double lastLongitude) {
        this.lastLongitude = lastLongitude;
    }

    public Double getLastLatitude() {
        return lastLatitude;
    }

    public void setLastLatitude(Double lastLatitude) {
        this.lastLatitude = lastLatitude;
    }

    public Double getDistanceTravelled() {
        return distanceTravelled;
    }

    public void setDistanceTravelled(Double distanceTravelled) {
        this.distanceTravelled = distanceTravelled;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "VehicleData{" +
                "vehicleId=" + vehicleId +
                ", lastLongitude=" + lastLongitude +
                ", lastLatitude=" + lastLatitude +
                ", distanceTravelled=" + distanceTravelled +
                ", timestamp=" + timestamp +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VehicleData that = (VehicleData) o;
        return vehicleId.equals(that.vehicleId) && lastLongitude.equals(that.lastLongitude) && lastLatitude.equals(that.lastLatitude) && distanceTravelled.equals(that.distanceTravelled) && timestamp.equals(that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vehicleId, lastLongitude, lastLatitude, distanceTravelled, timestamp);
    }
}
