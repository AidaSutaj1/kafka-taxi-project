package com.github.aidasutaj1.taxispringapp.dto;

import javax.validation.constraints.NotNull;

public class Signal {

    @NotNull
    private Long vechileId;
    @NotNull
    private Double longitude;
    @NotNull
    private Double latitude;

    public Long getVechileId() {
        return vechileId;
    }

    public void setVechileId(Long vechileId) {
        this.vechileId = vechileId;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }


    @Override
    public String toString() {
        return "Signal{" +
                "vechileId=" + vechileId +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}
