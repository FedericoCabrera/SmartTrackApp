package com.isp.smarttrackapp.entities;

import android.icu.text.Transliterator;

import java.util.Date;

public class Incident {

    private String id;

    //private Date creationTime;
    private String base64Image;
    private String description;
    //private Traject traject;

    public java.lang.String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /*public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }*/

    /*public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }*/

    public String getBase64Image() {
        return base64Image;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /*public com.isp.smarttrackapp.entities.Traject getTraject() {
        return traject;
    }

    public void setTraject(com.isp.smarttrackapp.entities.Traject traject) {
        this.traject = traject;
    }*/
}
