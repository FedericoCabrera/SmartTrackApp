package com.isp.smarttrackapp.entities;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Traject {

    @SerializedName("id")
    private String trajectId;
    private double distance;
    private double duration;
    private boolean isFinished ;
    private Date startDate;
    private Position locationInitial;
    private Position locationFinal;

    public Position getLocationInitial() {
        return locationInitial;
    }

    public void setLocationInitial(Position locationInitial) {
        this.locationInitial = locationInitial;
    }

    public Position getLocationFinal() {
        return locationFinal;
    }

    public void setLocationFinal(Position locationFinal) {
        this.locationFinal = locationFinal;
    }

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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }


}
