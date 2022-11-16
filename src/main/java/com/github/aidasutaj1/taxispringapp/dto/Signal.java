package com.github.aidasutaj1.taxispringapp.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class Signal {

    @NotNull
    private Long vehicleId;
    @NotNull
    @Min(value=-180, message="Longitude must be equal or greater than -180")
    @Max(value=180, message="Longitude must be equal or less than 180")
    private Double longitude;
    @NotNull
    @Min(value=-90, message="Latitude must be equal or greater than -90")
    @Max(value=90, message="Latitude must be equal or less than 90")
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
