package com.github.aidasutaj1.taxispringapp.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
public class VechileData {

    @Id
    private Long vechileId;
    private Double lastLongtitude;
    private Double lastLantitude;
    private Double distanceTravelled = 0.0;

    private LocalDateTime timestamp;

    public VechileData(Long vechileId, Double lastLongtitude, Double lastLantitude, Double distanceTravelled, LocalDateTime timestamp) {
        this.vechileId = vechileId;
        this.lastLongtitude = lastLongtitude;
        this.lastLantitude = lastLantitude;
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

    public Double getLastLongtitude() {
        return lastLongtitude;
    }

    public void setLastLongtitude(Double lastLongtitude) {
        this.lastLongtitude = lastLongtitude;
    }

    public Double getLastLantitude() {
        return lastLantitude;
    }

    public void setLastLantitude(Double lastLantitude) {
        this.lastLantitude = lastLantitude;
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
                ", lastLongtitude=" + lastLongtitude +
                ", lastLantitude=" + lastLantitude +
                ", distanceTravelled=" + distanceTravelled +
                ", timestamp=" + timestamp +
                '}';
    }
}
