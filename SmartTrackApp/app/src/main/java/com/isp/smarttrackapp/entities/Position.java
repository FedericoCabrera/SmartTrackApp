package com.isp.smarttrackapp.entities;

public class Position {

    private String id;
    private double latitude;
    private double longitude;
    public String address;

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }

    public String getId() {
        return id;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setId(String id) {
        id = id;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
