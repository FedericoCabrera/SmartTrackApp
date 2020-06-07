package com.isp.smarttrackapp.entities;

import android.location.Location;

import java.time.LocalDateTime;

public class Incident {

    private String incidentId;
    private Position Location ;
    private LocalDateTime CreationTime;
    private String Base64Image;
    private String Description;
    private Traject Traject;

    public String getIncidentId() {
        return incidentId;
    }

    public void setIncidentId(String incidentId) {
        this.incidentId = incidentId;
    }

    public Position getLocation() {
        return Location;
    }

    public void setLocation(Position location) {
        Location = location;
    }

    public LocalDateTime getCreationTime() {
        return CreationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        CreationTime = creationTime;
    }

    public String getBase64Image() {
        return Base64Image;
    }

    public void setBase64Image(String base64Image) {
        Base64Image = base64Image;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public com.isp.smarttrackapp.entities.Traject getTraject() {
        return Traject;
    }

    public void setTraject(com.isp.smarttrackapp.entities.Traject traject) {
        Traject = traject;
    }
}
