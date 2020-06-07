package com.isp.smarttrackapp.entities;

import android.location.Location;

import java.time.LocalDateTime;

public class Traject {

    private String trajectId;
    private double distance;
    private double duration;
    private boolean isFinished ;
    private LocalDateTime startDate;
    private Boolean isAdmin;
    private Position LocationInitial;
    private Position LocationFinal;


    public String getTrajectId() {
        return trajectId;
    }

    public void setTrajectId(String trajectId) {
        this.trajectId = trajectId;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public Position getLocationInitial() {
        return LocationInitial;
    }

    public void setLocationInitial(Position locationInitial) {
        LocationInitial = locationInitial;
    }

    public Position getLocationFinal() {
        return LocationFinal;
    }

    public void setLocationFinal(Position locationFinal) {
        LocationFinal = locationFinal;
    }
}
