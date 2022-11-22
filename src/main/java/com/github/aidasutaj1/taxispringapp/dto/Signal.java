package com.github.aidasutaj1.taxispringapp.dto;

import javax.validation.constraints.*;
import java.util.Objects;

public class Signal {

    @NotNull(message = "vehicle id cannot be null")
    private Long vehicleId;

    @NotNull(message = "Longitude cannot be null")
    @Min(value = -180, message = "Longitude must be equal or greater than -180")
    @Max(value = 180, message = "Longitude must be equal or less than 180")
    private Double longitude;

    @NotNull(message = "Latitude cannot be null")
    @Min(value = -90, message = "Latitude must be equal or greater than -90")
    @Max(value = 90, message = "Latitude must be equal or less than 90")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Signal signal = (Signal) o;
        return vehicleId.equals(signal.vehicleId) && longitude.equals(signal.longitude) && latitude.equals(signal.latitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vehicleId, longitude, latitude);
    }
}
