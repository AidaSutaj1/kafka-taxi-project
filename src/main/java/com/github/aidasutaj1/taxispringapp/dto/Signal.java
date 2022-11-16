package com.github.aidasutaj1.taxispringapp.dto;

import javax.validation.constraints.NotNull;

public class Signal {

    @NotNull
    private Long vehicleId;
    @NotNull
    private Double longitude;
    @NotNull
    private Double latitude;

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
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
                "vehicleId=" + vehicleId +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}
