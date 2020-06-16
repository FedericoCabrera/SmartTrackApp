package com.isp.smarttrackapp.entities;

import java.time.LocalDateTime;

public class Position {

    private String Id;
    private double Latitude;
    private double Longitude;

    public String getId() {
        return Id;
    }

    public double getLatitude() {
        return Latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setId(String id) {
        Id = id;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }
}
