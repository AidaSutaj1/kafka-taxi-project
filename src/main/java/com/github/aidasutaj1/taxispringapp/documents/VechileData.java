package com.github.aidasutaj1.taxispringapp.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
public class VechileData {

    @Id
    private Long vechileId;
    private Double lastLongitude;
    private Double lastLatitude;
    private Double distanceTravelled = 0.0;

    private LocalDateTime timestamp;

    public VechileData(Long vechileId, Double lastLongitude, Double lastLatitude, Double distanceTravelled, LocalDateTime timestamp) {
        this.vechileId = vechileId;
        this.lastLongitude = lastLongitude;
        this.lastLatitude = lastLatitude;
        this.distanceTravelled = distanceTravelled;
        this.timestamp = timestamp;
    }

    public VechileData() {
    }

    public Long getVechileId() {
        return vechileId;
    }

    public void setVechileId(Long vechileId) {
        this.vechileId = vechileId;
    }

    public Double getLastLongitude() {
        return lastLongitude;
    }

    public void setLastLongitude(Double lastLongtitude) {
        this.lastLongitude = lastLongtitude;
    }

    public Double getLastLatitude() {
        return lastLatitude;
    }

    public void setLastLatitude(Double lastLantitude) {
        this.lastLatitude = lastLantitude;
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
        return "VechileData{" +
                "vechileId=" + vechileId +
                ", lastLongitude=" + lastLongitude +
                ", lastLatitude=" + lastLatitude +
                ", distanceTravelled=" + distanceTravelled +
                ", timestamp=" + timestamp +
                '}';
    }
}
