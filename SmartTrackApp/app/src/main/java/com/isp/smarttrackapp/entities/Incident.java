package com.isp.smarttrackapp.entities;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Incident {

    private String id;

    //private Date creationTime;
    private String base64Image;
    private String description;
    private String address;
    @SerializedName("location")
    private Position location;

    public Incident(String base64Image, String description, String address, Position location) {
        this.base64Image = base64Image;
        this.description = description;
        this.address = address;
        this.location = location;
    }

    public Position getLocation() { return location; }

    public void setLocation(Position location) { this.location = location; }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }

    public String getId() {
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
